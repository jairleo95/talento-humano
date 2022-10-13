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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.app.persistence.dao.AutorizacionDAO;
import com.app.persistence.dao.DgpDAO;
import com.app.persistence.dao.UsuarioDAO;
import com.app.persistence.dao_imp.InterfaceAutorizacionDAO;
import com.app.persistence.dao_imp.InterfaceDgpDAO;
import com.app.persistence.dao_imp.InterfaceNotificationDAO;
import com.app.persistence.dao_imp.InterfaceUsuarioDAO;

/**
 *
 * @author Alfa.sistemas
 */

@RestController
@RequestMapping("imbox")
public class AuthorizationController {

    InterfaceAutorizacionDAO a = new AutorizacionDAO();
    InterfaceDgpDAO dgp = new DgpDAO();

    @PostMapping("core")
    public ResponseEntity<?> coreProcess(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String iduser = (String) session.getAttribute("IDUSER");
        String idp = (String) session.getAttribute("PUESTO_ID");
        String idrol = (String) session.getAttribute("IDROL");
        String iddep = (String) session.getAttribute("DEPARTAMENTO_ID");
        String iddir = (String) session.getAttribute("IDDIR");

        String opc = request.getParameter("opc");
        Map<String, Object> rpta = new HashMap<String, Object>();

        if (iduser != null) {
            if (opc != null) {
                //check Permissions
                Permission permission = new Permission().getPermissions(idrol);

                if (opc.equals("Aceptar")) {
                    String iddgp = request.getParameter("IDDETALLE_DGP");
                    String estado = "1";

                    /*Cambiar con un trigger al momento de insertar*/
                    System.out.println("Call List_id_Autorizacion");
                    List<V_Autorizar_Dgp> autDGP = a.List_id_Autorizacion(idp, iduser, iddgp);
                    if (autDGP.size() == 1) {

                        System.out.println("Enter to Autorizacion DGP");

                        V_Autorizar_Dgp vAut = autDGP.get(0);

                        /*Cambiar con un trigger al momento de insertar (esta generando mucho retrazo)*/
                        //  dgp.VAL_DGP_PASOS();
                        /*Autorización*/
                        a.Insert_Autorizacion("", iddgp, estado, vAut.getNu_pasos(), "", iduser, "", "", vAut.getCo_pasos(), idp, vAut.getId_detalle_req_proceso(), vAut.getId_pasos());

                        session.setAttribute("List_id_Autorizacion", a.List_id_Autorizacion(idp, iduser, ""));
                        rpta.put("rpta", true);
                        /*Notificaciones*/
                        InterfaceNotificationDAO notdao = new NotificationDAO();
                        Notification not = new Notification();
                        InterfaceUsuarioDAO udao = new UsuarioDAO();
                        String username = udao.List_ID_User(iduser).get(0).getNo_usuario();
                        not.setId_rol(idrol);
                        not.setEs_visualizado("0");
                        not.setEs_leido("0");
                        not.setTipo_notification("1");
                        not.setDe_notification("Empleado autorizado por " + username);

                        not.setDi_notification("trabajador?idtr=" + vAut.getId_trabajador() + "&opc=list");
                        not.setTitulo(vAut.getNo_trabajador() + " " + vAut.getAp_paterno() + " " + vAut.getAp_materno());

                        List<String> ids = notdao.PrevSteps(iddgp);
                        for(int i = 0; i < ids.size(); i++) {
                            not.setId_usuario(ids.get(i));
                            notdao.Registrar(not);
                        }
                        /*End Notify*/
                        // sesion.setAttribute("List_id_Autorizados", a.List_Autorizados(idp));
                    } else {
                        System.out.println("Enter to Autorizacion academico");
                        List<V_Autorizar_Dgp> autAcademico = a.List_Autorizacion_Academico(idp, iduser, iddgp);
                        if (autAcademico.size() == 1) {

                            V_Autorizar_Dgp vAutAcademico = autAcademico.get(0);

                            /*Autorización*/
                            a.Insert_Autorizacion("", iddgp, estado, vAutAcademico.getNu_pasos(), "", iduser, "", "", vAutAcademico.getCo_pasos(), idp, vAutAcademico.getId_detalle_req_proceso(), vAutAcademico.getId_pasos());
                            session.setAttribute("List_Autorizacion_Academico", a.List_Autorizacion_Academico(idp, iduser, ""));
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
                    dgp.HABILITAR_DGP(iddgp);
                    if (permission.isDepartFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false,false));
                    }
                    if (permission.isDireccionFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", iddir, "", false,false));
                    }
                    if (permission.isPuestoFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", idp, false,false));
                    } else {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false,false));
                    }
                    // sesion.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep));
                    //// response.sendRedirect("views/Dgp/Proceso_Dgp.html");
                }
                if (opc.equals("eliminarDGP")) {
                    String iddgp = request.getParameter("iddgp");

                    dgp.eliminarDGP(iddgp);
                    if (permission.isDepartFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false,false));
                    }
                    if (permission.isDireccionFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", iddir, "", false,false));
                    }
                    if (permission.isPuestoFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", idp, false,false));
                    } else {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false,false));
                    }
                    // sesion.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep));
                    /////response.sendRedirect("views/Dgp/Proceso_Dgp.html");

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
                    String id_autorizacion = a.Insert_Autorizacion_dev("", iddgp, estado, nropaso, "", iduser, "", "", cod.trim(), idp, iddrp, idpasos);
                    a.Insert_comentario_Aut("", id_autorizacion, iddgp, iduser, "1", id_autorizacion, comentario);
                    InterfaceNotificationDAO notdao = new NotificationDAO();
                    Notification not = new Notification();
                    InterfaceUsuarioDAO udao = new UsuarioDAO();
                    String username = udao.List_ID_User(iduser).get(0).getNo_usuario();
                    not.setId_rol(idrol);
                    not.setEs_visualizado("0");
                    not.setEs_leido("0");
                    not.setTipo_notification("0");
                    not.setDe_notification("Empleado rechazado por " + username);
                    not.setDi_notification("trabajador?idtr=" + idtrab + "&opc=list");
                    not.setTitulo(nombres);
                    List<String> ids = notdao.PrevSteps(iddgp);
                    for (int i = 0; i < ids.size(); i++) {
                        not.setId_usuario(ids.get(i));
                        notdao.Registrar(not);
                    }
                    session.setAttribute("List_id_Autorizacion", a.List_id_Autorizacion(idp, iduser, ""));
                    session.setAttribute("List_id_Autorizados", a.List_Autorizados(idp));
                    ///response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html?r=ok");
                    session.setAttribute("List_id_Autorizacion", a.List_id_Autorizacion(idp, iduser, ""));
                    session.setAttribute("List_id_Autorizados", a.List_Autorizados(idp));
                    //out.print(id_autorizacion);
                    ///response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html?r=ok");
                    /////out.print("correcto ");
                }
            }
        }
        return new ResponseEntity<>(rpta ,HttpStatus.OK);

    }


}