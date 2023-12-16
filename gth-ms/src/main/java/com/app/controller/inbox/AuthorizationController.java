/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.inbox;

import com.app.persistence.dao.NotificationDAO;
import com.app.domain.model.Notification;
import com.app.domain.model.V_Autorizar_Dgp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.app.persistence.dao.AutorizacionDAO;
import com.app.persistence.dao.DgpDAO;
import com.app.persistence.dao.UsuarioDAO;
import com.app.persistence.dao_imp.IAutorizacionDAO;
import com.app.persistence.dao_imp.IDgpDAO;
import com.app.persistence.dao_imp.INotificationDAO;
import com.app.persistence.dao_imp.IUsuarioDAO;

/**
 *
 * @author Alfa.sistemas
 */

@RequestMapping("imbox")
public class AuthorizationController {

    IAutorizacionDAO autorizacionDAO = new AutorizacionDAO();
    IDgpDAO dgp = new DgpDAO();

    public UserSession getUserSession(HttpSession httpSession){
        return UserSession.builder()
                .id((String) httpSession.getAttribute("IDUSER"))
                .puestoId((String) httpSession.getAttribute("PUESTO_ID"))
                .roId((String) httpSession.getAttribute("IDROL"))
                .depId((String) httpSession.getAttribute("DEPARTAMENTO_ID"))
                .dirId((String) httpSession.getAttribute("IDDIR"))
                .build();
    }

    @PostMapping("core")
    public ResponseEntity<?> coreProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        UserSession userSession = getUserSession(session);

        String opc = request.getParameter("opc");
        Map<String, Object> rpta = new HashMap<String, Object>();

        if (userSession.getId() != null) {
            if (opc != null) {
                //check Permissions
                Permission permission = new Permission().getPermissions(userSession.getRoId());

                if (opc.equals("Aceptar")) {
                    String iddgp = request.getParameter("IDDETALLE_DGP");
                    String estado = "1";

                    /*Cambiar con un trigger al momento de insertar*/
                    List<V_Autorizar_Dgp> autDGP = autorizacionDAO.List_id_Autorizacion(userSession.getDepId(), userSession.getId(), iddgp);
                    System.out.println("Call List_id_Autorizacion");
                    if (autDGP.size() == 1) {

                        System.out.println("Enter to Autorizacion DGP");

                        V_Autorizar_Dgp vAut = autDGP.get(0);

                        /*Cambiar con un trigger al momento de insertar (esta generando mucho retrazo)*/
                        //  dgp.VAL_DGP_PASOS();
                        /*Autorización*/
                        autorizacionDAO.insert("", iddgp, estado, vAut.getNu_pasos(), "", userSession.getId(), "", "", vAut.getCo_pasos(), userSession.getPuestoId(), vAut.getId_detalle_req_proceso(), vAut.getId_pasos());

                        session.setAttribute("List_id_Autorizacion", autorizacionDAO.List_id_Autorizacion(userSession.getDepId(), userSession.getId(), ""));
                        rpta.put("rpta", true);
//@RestController
                        /*Notificaciones*/
                        INotificationDAO notdao = new NotificationDAO();
                        Notification notification = new Notification();
                        IUsuarioDAO udao = new UsuarioDAO();
                        String username = udao.List_ID_User(userSession.getId()).get(0).getNo_usuario();
                        notification.setId_rol(userSession.getRoId());
                        notification.setEs_visualizado("0");
                        notification.setEs_leido("0");
                        notification.setTipo_notification("1");
                        notification.setDe_notification("Empleado autorizado por " + username);

                        notification.setDi_notification("trabajador?idtr=" + vAut.getId_trabajador() + "&opc=list");
                        notification.setTitulo(vAut.getNo_trabajador() + " " + vAut.getAp_paterno() + " " + vAut.getAp_materno());

                        List<String> ids = notdao.PrevSteps(iddgp);
                        for (String id : ids) {
                            notification.setId_usuario(id);
                            notdao.Registrar(notification);
                        }
                        /*End Notify*/
                        // sesion.setAttribute("List_id_Autorizados", a.List_Autorizados(userSession.getPuestoId()));
                    } else {
                        System.out.println("Enter to Autorizacion academico");
                        List<V_Autorizar_Dgp> autAcademico = autorizacionDAO.List_Autorizacion_Academico(userSession.getPuestoId(), userSession.getId(), iddgp);
                        if (autAcademico.size() == 1) {

                            V_Autorizar_Dgp vAutAcademico = autAcademico.get(0);

                            /*Autorización*/
                            autorizacionDAO.insert("", iddgp, estado, vAutAcademico.getNu_pasos(), "", userSession.getId(), "", "", vAutAcademico.getCo_pasos(), userSession.getPuestoId(), vAutAcademico.getId_detalle_req_proceso(), vAutAcademico.getId_pasos());
                            session.setAttribute("List_Autorizacion_Academico", autorizacionDAO.List_Autorizacion_Academico(userSession.getPuestoId(), userSession.getId(), ""));
                            rpta.put("rpta", true);
                        } else {
                            rpta.put("rpta", false);
                            rpta.put("message", "No se obtuvo valores de Autorización");
                        }

                    }
                }
                if (opc.equals("HDGP")) {

                    String iddgp = request.getParameter("iddgp");
                    //out.print(iddgp);
                    dgp.enableDGP(iddgp);
                    if (permission.isDepartFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(userSession.getDepId(), "", "", false,false));
                    }
                    if (permission.isDireccionFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", userSession.getDirId(), "", false,false));
                    }
                    if (permission.isPuestoFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", userSession.getPuestoId(), false,false));
                    } else {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(userSession.getDepId(), "", "", false,false));
                    }
                    // sesion.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(userSession.getDepId()));
                     response.sendRedirect("views/Dgp/Proceso_Dgp.html");
                }
                if (opc.equals("eliminarDGP")) {
                    String iddgp = request.getParameter("iddgp");

                    dgp.eliminarDGP(iddgp);
                    if (permission.isDepartFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(userSession.getDepId(), "", "", false,false));
                    }
                    if (permission.isDireccionFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", userSession.getDirId(), "", false,false));
                    }
                    if (permission.isPuestoFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", userSession.getPuestoId(), false,false));
                    } else {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(userSession.getDepId(), "", "", false,false));
                    }
                    // sesion.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(userSession.getDepId()));
                    response.sendRedirect("views/Dgp/Proceso_Dgp.html");

                }
                if (opc.equals("Rechazar")) {
                    String iddgp = request.getParameter("IDDETALLE_DGP");
                    String comentario = request.getParameter("comentario");
                    String estado = "2";
                    String nropaso = request.getParameter("NROPASO");
                    //String usuario_ip = "";
                    String cod = request.getParameter("COD");
                    String iddrp = request.getParameter("IDDETALLE_REQ_PROCESO");
                    String idpasos = request.getParameter("IDPASOS");
                    String nombres = request.getParameter("NOMBRES");
                    String idtrab = request.getParameter("IDTRAB");
                    /*Cambiar con un trigger al momento de insertar*/
                    dgp.VAL_DGP_PASOS();
                    dgp.RECHAZAR_DGP(iddgp);
                    String id_autorizacion = autorizacionDAO.insertDev("", iddgp, estado, nropaso, "", userSession.getId(), "", "", cod.trim(), userSession.getPuestoId(), iddrp, idpasos);
                    autorizacionDAO.insert("", id_autorizacion, iddgp, userSession.getId(), "1", id_autorizacion, comentario);
                    INotificationDAO notificationDAO = new NotificationDAO();
                    Notification notification = new Notification();
                    IUsuarioDAO udao = new UsuarioDAO();
                    String username = udao.List_ID_User(userSession.getId()).get(0).getNo_usuario();
                    notification.setId_rol(userSession.getRoId());
                    notification.setEs_visualizado("0");
                    notification.setEs_leido("0");
                    notification.setTipo_notification("0");
                    notification.setDe_notification("Empleado rechazado por " + username);
                    notification.setDi_notification("trabajador?idtr=" + idtrab + "&opc=list");
                    notification.setTitulo(nombres);
                    List<String> ids = notificationDAO.PrevSteps(iddgp);
                    for (String id : ids) {
                        notification.setId_usuario(id);
                        notificationDAO.Registrar(notification);
                    }
                    session.setAttribute("List_id_Autorizacion", autorizacionDAO.List_id_Autorizacion(userSession.getPuestoId(), userSession.getId(), ""));
                    session.setAttribute("List_id_Autorizados", autorizacionDAO.List_Autorizados(userSession.getPuestoId()));
                    response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html?r=ok");
                    session.setAttribute("List_id_Autorizacion", autorizacionDAO.List_id_Autorizacion(userSession.getPuestoId(), userSession.getId(), ""));
                    session.setAttribute("List_id_Autorizados", autorizacionDAO.List_Autorizados(userSession.getPuestoId()));
                    //out.print(id_autorizacion);
                    response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html?r=ok");
                   
                }
            }
        }
        return new ResponseEntity<>(rpta ,HttpStatus.OK);

    }
}