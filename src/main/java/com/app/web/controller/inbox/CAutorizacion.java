/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.inbox;

import com.app.dao.NotificationDAO;
import com.app.model.Notification;
import com.app.model.V_Autorizar_Dgp;
import com.app.util.CCriptografiar;
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
import com.app.dao.AutorizacionDAO;
import com.app.dao.CorreoDAO;
import com.app.dao.DgpDAO;
import com.app.dao.EmpleadoDAO;
import com.app.dao.UsuarioDAO;
import com.app.dao_imp.InterfaceAutorizacionDAO;
import com.app.dao_imp.InterfaceCorreoDAO;
import com.app.dao_imp.InterfaceDgpDAO;
import com.app.dao_imp.InterfaceEmpleadoDAO;
import com.app.dao_imp.InterfaceNotificationDAO;
import com.app.dao_imp.InterfaceUsuarioDAO;

/**
 *
 * @author Alfa.sistemas
 */

@RestController
@RequestMapping("imbox")
public class CAutorizacion {

    InterfaceEmpleadoDAO e = new EmpleadoDAO();
    InterfaceAutorizacionDAO a = new AutorizacionDAO();
    InterfaceDgpDAO dgp = new DgpDAO();
    InterfaceCorreoDAO correo = new CorreoDAO();

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
                        System.out.println("1 :" + vAut.getNu_pasos());
                        System.out.println("2 :" + vAut.getId_pasos());
                        System.out.println("3 :" + vAut.getCo_pasos());
                        System.out.println("4 :" + vAut.getId_detalle_req_proceso());
                        System.out.println("5 :" + idp);
                        /*Cambiar con un trigger al momento de insertar (esta generando mucho retrazo)*/
                        //  dgp.VAL_DGP_PASOS();
                        /*Autorizaci??n*/
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
                            System.out.println("Academico");
                            System.out.println("1 :" + vAutAcademico.getNu_pasos());
                            System.out.println("2 :" + vAutAcademico.getId_pasos());
                            System.out.println("3 :" + vAutAcademico.getCo_pasos());
                            System.out.println("4 :" + vAutAcademico.getId_detalle_req_proceso());
                            System.out.println("5 :" + idp);

                            /*Autorizaci??n*/
                            a.Insert_Autorizacion("", iddgp, estado, vAutAcademico.getNu_pasos(), "", iduser, "", "", vAutAcademico.getCo_pasos(), idp, vAutAcademico.getId_detalle_req_proceso(), vAutAcademico.getId_pasos());
                            session.setAttribute("List_Autorizacion_Academico", a.List_Autorizacion_Academico(idp, iduser, ""));
                            rpta.put("rpta", true);
                        } else {
                            rpta.put("rpta", false);
                            rpta.put("message", "No se obtuvo valores de Autorizaci??n");
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

    @PostMapping("academic")
    public ResponseEntity<?> academicProcess(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        CCriptografiar cr = new CCriptografiar();
        Map<String, Object> rpta = new HashMap<String, Object>();

        String iduser = (String) session.getAttribute("IDUSER");


        if (iduser != null) {
            String ide = (String) session.getAttribute("IDPER");
            String idp = (String) session.getAttribute("PUESTO_ID");
            String iddep = (String) session.getAttribute("DEPARTAMENTO_ID");
            //String iddir = (String) session.getAttribute("IDDIR");
            String idrol = (String) session.getAttribute("IDROL");

            String opc = request.getParameter("opc");

            try {
                if (opc != null) {

                    //check Permissions
                    Permission permission = new Permission().getPermissions(idrol);

                    //AUTORIZACION CARGA ACADEMICA POR DOCENTE
                    if (opc.equals("Autorizacion_CD")) {
                        String idpu = e.Id_Puesto_Personal(ide);
                        session.setAttribute("List_Autorizacion_Academico", a.List_Autorizacion_Academico(idpu, iduser, ""));
                        ///response.sendRedirect("views/Academico/Autorizar_Carga_Academica.html");
                    }
                    if (opc.equals("headerTableAutorizacionCA")) {
                        String htmlHeader = "";
                        if (idrol.trim().equals("ROL-0007") | idrol.trim().equals("ROL-0001")) {

                            htmlHeader += "  <tr>";
                            htmlHeader += " <th class='hasinput' colspan='14' style='width:95%' ></th>";
                            htmlHeader += "<th class='hasinput'>";
                            htmlHeader += " <button   rel='tooltip' data-placement='left' data-original-title='Autorizar y Procesar codigo de huella digital'  class='btn btn-warning btn-circle btn-lg  btn_cod_huella'>";
                            htmlHeader += "  <i class='glyphicon glyphicon-ok'></i></button>";
                            htmlHeader += " </th>";
                            htmlHeader += "   </tr>";
                        }
                        if (idrol.trim().equals("ROL-0009")) {
                            htmlHeader += "     <tr>";
                            htmlHeader += "   <th class='hasinput' colspan='14' style='width:95%' ></th>";
                            htmlHeader += "   <th class='hasinput'  style='' >";
                            htmlHeader += "      <button   rel='tooltip' data-placement='left' data-original-title='Autorizar y Procesar c??digo aps'";
                            htmlHeader += "            class='btn bg-color-magenta txt-color-white btn-circle btn-lg  btn_cod_aps'>";
                            htmlHeader += "        <i class='glyphicon glyphicon-ok'></i></button>";
                            htmlHeader += "    </th>";
                            htmlHeader += "  </tr>";
                        }
                        if (idrol.trim().equals("ROL-0006")) {
                            htmlHeader += "   <tr>";
                            htmlHeader += "  <th class='hasinput' colspan='15' style='width:95%' ></th>";
                            htmlHeader += "  <th class='hasinput'>";
                            htmlHeader += "     <button   disabled rel='tooltip' data-placement='top' data-original-title='Procesar Firmas'  class='btn btn-primary btn-circle btn-sm btn_pro_firma'>";
                            htmlHeader += "       <i class='glyphicon glyphicon-ok'></i></button>";
                            htmlHeader += " </th>";
                            htmlHeader += "  <th class='hasinput' >";
                            htmlHeader += "   <button  disabled rel='tooltip' data-placement='top' data-original-title='Procesar a remuneraciones'  class='btn btn-default btn-circle btn-sm btn_pro_remuneracion'>";
                            htmlHeader += "     <i class='glyphicon glyphicon-ok'></i></button>";
                            htmlHeader += "   </th>";
                            htmlHeader += "   <th class='hasinput'  style='' >";
                            htmlHeader += " <button  disabled  rel='tooltip' data-placement='top' data-original-title='Procesar a Firmas y Envio a Remuneraciones'  class='btn btn-warning btn-circle btn-sm btnProcesarFirmaAndRem'>";
                            htmlHeader += "    <i class='glyphicon glyphicon-ok'></i></button>";
                            htmlHeader += "   </th>";
                            htmlHeader += "   </tr>";
                        }
                        htmlHeader += "  <tr data-hide='phone,tablet'>";
                        htmlHeader += "   <th><strong>Nro</strong></th>";
                        htmlHeader += "    <th data-hide='phone,tablet'><strong>Acci??n</strong></th>";
                        htmlHeader += "  <th>Mes</th>";
                        htmlHeader += "   <th data-hide='phone,tablet'><strong>Foto</strong> </th>";
                        htmlHeader += "   <th data-class='expand' ><strong>Apellidos Y Nombres</strong></th>";
                        htmlHeader += "    <th data-hide='phone,tablet'><strong>Puesto</strong></th>";
                        htmlHeader += "    <th data-hide='phone,tablet'><strong>Area</strong></th>";
                        htmlHeader += "    <th data-hide='phone,tablet'><strong>Departamento</strong></th>";
                        htmlHeader += "   <th data-hide='phone,tablet'><strong>Requerimiento</strong></th>";
                        htmlHeader += "  <th data-hide='phone,tablet'><strong>Descripci??n</strong></th>";
                        htmlHeader += "   <th  data-hide='phone,tablet'>Fecha de Creaci??n</th>  ";
                        htmlHeader += "    <th  data-hide='phone,tablet'>Motivo</th>  ";
                        htmlHeader += "   <th  data-hide='phone,tablet'>MFL</th>  ";
                        if (iddep.equals("DPT-0019")) {
                            htmlHeader += "  <th><strong>??Cumplio Plazos?</strong></th>";
                            if (idrol.trim().equals("ROL-0006")) {
                                htmlHeader += "   <th><strong>??Contrato Elaborado?</strong></th>";
                                htmlHeader += "    <th><strong>??Firmo Contrato?</strong></th>";
                                htmlHeader += " <th><strong>Enviar a Rem.</strong></th>";
                                htmlHeader += "  <th><strong>??Contrato Subido?</strong></th>";
                            }
                        }
                        if (idrol.trim().equals("ROL-0009")) {
                            htmlHeader += "  <th><strong>C??digo APS</strong></th>";
                        }
                        if (idrol.trim().equals("ROL-0007") | idrol.trim().equals("ROL-0001")) {
                            htmlHeader += " <th><strong>C??digo Huella</strong></th>";
                        }
                        htmlHeader += "  </tr>";
                        rpta.put("htmlHeader", htmlHeader);
                        String htmlBody = "";
                        int num_cod_aps = 0;
                        int num_cod_huella = 0;

                        List<V_Autorizar_Dgp> listaAutCA = a.List_Autorizacion_Academico(idp, iduser, "");

                        for (int f = 0; f < listaAutCA.size(); f++) {
                            V_Autorizar_Dgp autCA = new V_Autorizar_Dgp();
                            autCA = (V_Autorizar_Dgp) listaAutCA.get(f);

                            htmlBody += "<tr>";
                            htmlBody += "<td>" + (f + 1) + "</td>";
                            htmlBody += "   <td>";
                            htmlBody += " <div class='btn-group'>";
                            htmlBody += "<button class='btn btn-primary dropdown-toggle' data-toggle='dropdown'>";
                            htmlBody += "<i class='fa fa-gear fa-lg'></i>";
                            htmlBody += "  <i class='fa fa-caret-down'></i>";
                            htmlBody += "  </button>";
                            htmlBody += " <ul class='dropdown-menu'>";
                            htmlBody += "  <li><a href='../../dgp?iddgp=" + autCA.getId_dgp().trim() + "&opc=Seguimiento'>Ver Proceso</a></li>";
                            htmlBody += "  <li><a href='../../documento?iddgp=" + autCA.getId_dgp().trim() + "&idtr=" + autCA.getId_trabajador().trim() + "&opc=Ver_Documento'>Ver Documentos</a></li>";
                            htmlBody += "   <li><a  data-valor='" + autCA.getId_dgp().trim() + ";" + autCA.getId_trabajador().trim() + ";" + autCA.getAp_paterno() + " " + autCA.getAp_materno() + " "
                                    + autCA.getNo_trabajador() + "' class='click' data-toggle='modal' data-target='#myModal' data-backdrop='static' data-keyboard='false' onclick='sendAjax('')' >Comentario</a></li>";
                            if (Integer.parseInt(autCA.getElab_contrato()) > 0) {
                                htmlBody += "<li>";
                                htmlBody += "  <a href='../../contrato?idtr=" + autCA.getId_trabajador().trim() + "&opc=Detalle_Contractual'>Ver Contrato</a></li>";
                            }
                            htmlBody += "   <li class='divider'></li>";
                            htmlBody += " <li>";
                            htmlBody += " <li>";

                            int num = autCA.getVal_dgp_cotrato();

                            htmlBody += "   <a href='../../trabajador?idtr=" + autCA.getId_trabajador() + "&IDDETALLE_REQ_PROCESO=" + autCA.getId_detalle_req_proceso()
                                    + "&iddetalle_dgp=" + autCA.getId_dgp() + "&p=" + autCA.getId_puesto() + "&cod=" + autCA.getCo_pasos() + "&idpasos=" + autCA.getId_pasos() + "&autorizacion=1&opc=aut&nup=" + autCA.getNu_pasos() + "'>";

                            if (idrol != null) {
                                if (idrol.trim().equals("ROL-0006")) {
                                    if (num >= 1) {
                                        htmlBody += "Registrar Firma";
                                    }
                                    if (num == 0) {
                                        htmlBody += "Hacer Contrato";
                                    } else {
                                        htmlBody += "Autorizar";
                                    }
                                } else {
                                    htmlBody += "Autorizar";
                                }
                            }
                            htmlBody += " </a>";
                            htmlBody += " </li>";
                            htmlBody += "  </ul>";
                            htmlBody += " </div>";
                            htmlBody += " </td>";
                            htmlBody += " <td >";
                            htmlBody += a.Mes_plazo(autCA.getId_dgp());
                            htmlBody += "  </td>   ";
                            if (autCA.getAr_foto() == null) {
                                htmlBody += " <td>";
                                htmlBody += " <img class='user_avatar_" + autCA.getId_trabajador() + "' src='../../img/avatar_default.jpg'  width='30'  height='30'>";
                                htmlBody += "</td>";
                            } else {
                                htmlBody += " <td>";
                                htmlBody += "   <img class='user_avatar_" + autCA.getId_trabajador() + "' src='../../Archivo/Fotos/" + autCA.getAr_foto() + "'  width='30'  height='30'>";
                                htmlBody += " </td>";
                            }
                            htmlBody += "  <td >" + autCA.getAp_paterno() + " " + autCA.getAp_materno() + " " + autCA.getNo_trabajador() + "</td>";
                            htmlBody += " <td >" + autCA.getNo_puesto() + "</td>";
                            htmlBody += "  <td >" + autCA.getNo_area() + "</td>";
                            htmlBody += "<td >" + autCA.getNo_dep() + "</td>";
                            htmlBody += " <td >" + autCA.getNo_req() + "</td>";
                            htmlBody += " <input type='hidden' class='val_aut" + (f + 1) + " valAut' "
                                    + " value='&IDDETALLE_REQ_PROCESO=" + autCA.getId_detalle_req_proceso() + "&IDDETALLE_DGP=" + autCA.getId_dgp() + "&p==a.getId_puesto()+"
                                    + "&COD=" + autCA.getCo_pasos() + "&IDPASOS=" + autCA.getId_pasos() + "&NROPASO=" + autCA.getNu_pasos() + "&IDTR=" + autCA.getId_trabajador() + "'/>";
                            htmlBody += "    <input type='hidden' class='val_firm" + (f + 1) + "' value='&IDDETALLE_DGP=" + autCA.getId_dgp() + "&IDTR=" + autCA.getId_trabajador() + "'/>";
                            htmlBody += "   <input type='hidden' class='correos_" + (f + 1) + " correoTrabajador' value='&IDTR=" + autCA.getId_trabajador() + "&co_inst=" + autCA.getDi_correo_inst()
                                    + "&co_pers=" + autCA.getDi_correo_personal() + "'/>";
                            htmlBody += " <td class='text-info'>";
                            htmlBody += "    <a href='../../trabajador?idtr=" + autCA.getId_trabajador() + "&IDDETALLE_REQ_PROCESO=" + autCA.getId_detalle_req_proceso() + "&iddetalle_dgp=" + autCA.getId_dgp() + "&p="
                                    + autCA.getId_puesto() + "&cod=" + autCA.getCo_pasos() + "&idpasos=" + autCA.getId_pasos() + "+&autorizacion=1&opc=aut&nup=" + autCA.getNu_pasos() + "'>";
                            htmlBody += "       <strong>" + autCA.getDe_pasos() + "</strong>";
                            htmlBody += "   </a>";
                            htmlBody += "  </td>";
                            htmlBody += "   <td >" + autCA.getFe_creacion() + "</td>";
                            htmlBody += " <td>";
                            if (autCA.getLi_motivo() != null) {

                                if (autCA.getLi_motivo().trim().equals("1")) {
                                    htmlBody += " Trabajdor Nuevo";
                                }
                                if (autCA.getLi_motivo().trim().equals("2")) {
                                    htmlBody += "Renovaci??n";
                                }
                            } else {
                                htmlBody += "No registrado";
                            }
                            htmlBody += " </td> ";
                            htmlBody += "  <td>";
                            if (autCA.getEs_mfl() != null) {
                                if (autCA.getEs_mfl().trim().equals("0")) {
                                    htmlBody += "No";
                                }
                                if (autCA.getEs_mfl().trim().equals("1")) {
                                    htmlBody += "Si";
                                }
                            } else {
                                htmlBody += "No registrado";
                            }
                            htmlBody += " </td> ";
                            if (iddep.equals("DPT-0019")) {
                                htmlBody += "<td>";
                                if (autCA.getVal_plazo() > 0) {
                                    htmlBody += " <a href='../../plazo_dgp?opc=Ver_detalle_plazo&iddgp=" + autCA.getId_dgp() + "' class='label label-danger popoverPlazo'  data-toggle='popover' data-trigger='hover' data-placement='top' title='Record de plazos cumplidos' data-content=\"" + autCA.getVer_list_plazo() + "\" data-html='true'> <strong>No cumplio plazos</strong></a>";
                                    htmlBody += " </td>";
                                } else if (autCA.getVal_plazo() == 0) {
                                    htmlBody += "   <a href='../../plazo_dgp?opc=Ver_detalle_plazo&iddgp=" + autCA.getId_dgp() + "' class='label label-primary popoverPlazo' data-toggle='popover' data-trigger='hover' data-placement='top' title='Record de plazos cumplidos' data-content=\"" + autCA.getVer_list_plazo() + "\" data-html='true'> <strong>Cumplio plazos</strong></a></td>";
                                }
                                if (idrol.trim().equals("ROL-0006")) {

                                    htmlBody += "<td >";
                                    if (Integer.parseInt(autCA.getElab_contrato()) == 0) {
                                        htmlBody += "No";
                                    } else {
                                        htmlBody += "Si";
                                    }
                                    htmlBody += "  </td> ";
                                    htmlBody += " <td>";
                                    if (Integer.parseInt(autCA.getVal_firm_contrato()) == 0) {
                                        if (Integer.parseInt(autCA.getElab_contrato()) == 0) {
                                            htmlBody += "  !Falta procesar??";
                                        } else {
                                            htmlBody += "    <div class='smart-form'>";
                                            htmlBody += "    <label class='toggle'><input type='checkbox' value='" + (f + 1) + "'  class='firm_contr'  name='estado' name='checkbox-toggle' ><i data-swchon-text='SI' data-swchoff-text='NO'></i></label>";
                                            htmlBody += "   </div>";
                                        }
                                    } else {
                                        htmlBody += " Si";
                                    }
                                    htmlBody += "    </td>";
                                    htmlBody += "    <td>";
                                    if (Integer.parseInt(autCA.getVal_firm_contrato()) != 0 & Integer.parseInt(autCA.getElab_contrato()) != 0) {

                                        htmlBody += "    <div class='smart-form'>";
                                        htmlBody += "       <label class='toggle'><input type='checkbox' value='" + (f + 1) + "'  class='env_rem" + (f + 1) + "envioRem'  name='estado' name='checkbox-toggle' ><i data-swchon-text='SI' data-swchoff-text='NO'></i></label>";
                                        htmlBody += " </div>";

                                    } else {
                                        htmlBody += "??Falta Procesar!";
                                    }

                                    htmlBody += " </td>";
                                    htmlBody += " <td>";

                                    if (autCA.getVal_contrato_adjunto() == 0) {
                                        htmlBody += " No";
                                    } else {
                                        htmlBody += "  Si";
                                    }

                                    htmlBody += "  </td>";
                                }
                                if (idrol.trim().equals("ROL-0009")) {
                                    if (autCA.getVal_cod_aps_empleado() == 0) {
                                        num_cod_aps++;
                                        htmlBody += " <td>";
                                        htmlBody += "  <input type='text' name='cod_aps' maxlength='6' class='cod_aps" + (f + 1) + " inp_cod_aps' style='width:50px'/>";
                                        htmlBody += "</td>";
                                        htmlBody += " <input type='hidden' name='idtr'  class='idtr" + (f + 1) + " idTrabajador' value='" + autCA.getId_trabajador() + "' />";
                                    } else {
                                        htmlBody += "  <td>";
                                        htmlBody += "  <strong>" + autCA.getCo_aps() + "</strong>";
                                        htmlBody += "  </td>";
                                    }
                                }
                                if (idrol.trim().equals("ROL-0007") | idrol.trim().equals("ROL-0001")) {
                                    if (autCA.getVal_cod_huella() == 0) {
                                        num_cod_huella++;

                                        htmlBody += "  <td>";
                                        htmlBody += "   <input type='text' name='cod_huella' maxlength='6' class='form-control cod_huella" + (f + 1) + " inp_cod_huella' style='width:70px'/>";
                                        htmlBody += "   </td>";
                                        htmlBody += " <input type='hidden' name='idtr'  class='idtr=" + (f + 1) + " value='" + autCA.getId_trabajador() + "' />";
                                    } else {
                                        htmlBody += "  <td class='' >";
                                        htmlBody += "  <div class='input-group' >";

                                        htmlBody += "   <input class='form-control' placeholder=''  style='width: 70px;' type='text' value='" + autCA.getCo_huella_digital() + "'>";
                                        htmlBody += "     <span class='input-group-addon'  >";
                                        htmlBody += "     <span class='checkbox'>";
                                        htmlBody += " <label >";
                                        htmlBody += "   <input type='checkbox' class='checkbox style-0 cbHuellaItem' >";
                                        htmlBody += "       <span></span>";
                                        htmlBody += "    </label>";
                                        htmlBody += "  </span>";
                                        htmlBody += "  </span>";

                                        htmlBody += "  </div>";
                                        htmlBody += "  </td>";
                                    }
                                }
                                htmlBody += " </tr>";
                            }
                        }
                        rpta.put("htmlBody", htmlBody);
                        rpta.put("access", true);
                    }
                    if (opc.equals("mens_cod_aps")) {
                        String idpu = e.Id_Puesto_Personal(ide);
                        session.setAttribute("List_id_Autorizacion", a.List_id_Autorizacion(idpu, iduser, ""));
                        session.setAttribute("List_id_Autorizados", a.List_Autorizados(idpu));
                       /// response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html?m=si");
                    }
                    if (opc.equals("mens_cod_huella")) {
                        String idpu = e.Id_Puesto_Personal(ide);
                        session.setAttribute("List_id_Autorizacion", a.List_id_Autorizacion(idpu, iduser, ""));
                        session.setAttribute("List_id_Autorizados", a.List_Autorizados(idpu));
                        ///response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html?h=si");
                    }
                    if (opc.equals("Enviar_Correo")) {
                        String emailSubject=request.getParameter("from");
                        String pwdSubject =request.getParameter("pwdSubject");
                        String to = request.getParameter("to");
                        String from = request.getParameter("from");
                        String asunto = request.getParameter("asunto");
                        String cuerpo = request.getParameter("cuerpo");
                        String[] emails = to.split(",");
                        for (String email : emails) {
                            System.out.println("email:" + email);
                            correo.Enviar(emailSubject,pwdSubject,to, from, asunto, cuerpo);
                        }
                        System.out.print("Ejecutando envio de correos");
                        /*correo.Enviar("jairleo95@gmail.com", "jairleo95@gmail.com", "CARPETA LABORAL - UPEU", "Estimado(a) Colaborador(a),\n"
                                + "Compartimos la siguiente informaci??n\n \n"
                                + "- Bienestar para el trabajador\n"
                                + "- Reglamento de Control de Asistencia\n"
                                + "- Reglamento de trabajo\n"
                                + "- Bolet??n Informativo - sistema pensionario\n \n"
                                + "Saludos Cordiales");*/
                        rpta.put("sendto", emails);
                        rpta.put("status", true);
                    }
                    if (opc.equals("List_Dgp_Aut")) {
                        String draw = request.getParameter("draw");
                        int start = Integer.parseInt(request.getParameter("start"));
                        int length = Integer.parseInt(request.getParameter("length"));
                        int pageNumber = (start / length) + 1;

                        String anno = request.getParameter("anno");
                        int mes = 0;
                        if (request.getParameter("mes") != null) {
                            if (!request.getParameter("mes").equals("")) {
                                mes = Integer.parseInt(request.getParameter("mes"));
                            }
                        }
                        List<Map<String, ?>> lista = a.List_Dgp_Autorizados(iduser, pageNumber, length, mes, anno);
                        int size = a.getListAuthorizeRequirementsSize(iduser, mes, anno);
                        rpta.put("rpta", "1");
                        rpta.put("draw", draw);
                        rpta.put("recordsTotal", size);
                        rpta.put("recordsFiltered", size);
                        rpta.put("data", lista);
                    }
                    if (opc.equals("ValBtnAutorizacion")) {
                        String html = "";
                        String idtr = request.getParameter("trabajador");
                        if (idrol.trim().equals("ROL-0009")) {
                            int val_aps = val_aps = e.val_cod_aps_empleado(idtr);
                            if (val_aps > 0) {
                                html = "<button class='btn btn-labeled btn-success btn-autor' type='button'>"
                                        + "                            <span class='btn-label'><i class='glyphicon glyphicon-ok'></i></span>PROCESAR REQUERIMIENTO "
                                        + "                        </button>";
                            } else {
                                html = "<div class='alert alert-warning fade in'><i class='fa-fw fa fa-warning'></i><strong>Atenci??n!</strong> Usted no puede <strong>AUTORIZAR</strong> el requerimiento, debe primero registrar el <strong>C??digo APS</strong>.</div>";
                            }
                        } else if (idrol.trim().equals("ROL-0007") | idrol.trim().equals("ROL-0001")) {
                            int val_huella = e.val_cod_huella(idtr);
                            if (val_huella > 0) {
                                html = "<button class='btn btn-labeled btn-success btn-autor' type='button'>"
                                        + "                            <span class='btn-label'><i class='glyphicon glyphicon-ok'></i></span>AUTORIZAR REQUERIMIENTO "
                                        + "                        </button>";

                            } else {
                                html = "<div class='alert alert-warning fade in'><i class='fa-fw fa fa-warning'></i><strong>Atenci??n!</strong> Usted no puede <strong>AUTORIZAR</strong> el requerimiento, debe primero registrar el <strong>C??digo de Huella Digital</strong>.</div>";
                            }
                        } else {
                            html = "<button class='btn btn-labeled btn-success btn-autor' type='button'>"
                                    + "                            <span class='btn-label'><i class='glyphicon glyphicon-ok'></i></span>AUTORIZAR REQUERIMIENTO "
                                    + "                        </button>";
                        }
                        rpta.put("rpta", "1");
                        rpta.put("data", html);
                    }
                    if (opc.equals("ShowListProcesarReq")) {
                        boolean tipo_lista = Boolean.parseBoolean((request.getParameter("tipo_lista")));
                        String html_table = "";
                        if (tipo_lista) {
                            html_table += "<table id='table_procesar' class='table table-striped table-bordered table-hover' width='100%'>";
                            html_table += "<thead><tr>";
                            html_table += (permission.isAsigFam() & permission.isEsSistema()) ? " <th class='hasinput' colspan='7' style='width:95%' ></th>" : "<th class='hasinput' colspan='7' style='width:95%' ></th>";
                            html_table += (permission.isAsigFam()) ? "<th class='hasinput'  ><center><button  class='btn btn-primary btn-circle btn-lg btnAsigFam'><i class='glyphicon glyphicon-ok'></i></button></center></th>" : "";
                            html_table += (permission.isEsSistema()) ? " <th class='hasinput' ><center><button  class='btn bg-color-blueDark txt-color-white  btn-circle btn-lg btnActSisEs'><i class='glyphicon glyphicon-ok'></i></button></center></th>" : "";
                            html_table += "</tr>"
                                    + "  <tr data-hide='phone,tablet'> <th><strong>Nro</strong></th>"
                                    + "  <th><strong>Mes - A??o </strong></th>"
                                    + " <th data-class='expand' ><strong>Apellidos Y Nombres</strong></th>"
                                    + "  <th data-hide='phone,tablet'><strong>Puesto</strong></th>"
                                    + " <th data-hide='phone,tablet'><strong>Area</strong></th>"
                                    + "  <th data-hide='phone,tablet'><strong>Departamento</strong></th>"
                                    + "  <th data-hide='phone,tablet'><strong>Requerimiento</strong></th>";
                            html_table += (permission.isAsigFam()) ? " <th  data-hide='phone,tablet'>Asig. Fam.</th> " : "";
                            html_table += (permission.isEsSistema()) ? "<th  data-hide='phone,tablet'>T-REGISTRO</th>" : "";
                            html_table += "</tr></thead>";
                        } else {

                            html_table += "<table id='table_autorizados' class='table table-striped table-bordered table-hover' width='100%'>";
                            html_table += "<thead>"
                                    + "  <tr data-hide='phone,tablet'> <th><strong>Nro</strong></th>"
                                    + "   <th><strong>Mes - A??o </strong></th>"
                                    + " <th data-class='expand' ><strong>Apellidos Y Nombres</strong></th>"
                                    + "  <th data-hide='phone,tablet'><strong>Puesto</strong></th>"
                                    + " <th data-hide='phone,tablet'><strong>Area</strong></th>"
                                    + "  <th data-hide='phone,tablet'><strong>Departamento</strong></th>"
                                    + "  <th data-hide='phone,tablet'><strong>Requerimiento</strong></th>";
                            html_table += (permission.isAsigFam()) ? " <th  data-hide='phone,tablet'>Asig. Fam.</th> " : "";
                            html_table += (permission.isEsSistema()) ? "<th  data-hide='phone,tablet'>T-REGISTRO</th>" : "";
                            html_table += "</tr></thead>";
                        }

                        if (tipo_lista) {
                            html_table += "<tbody class='tbody_procesar_req'> </tbody> ";
                        } else {
                            html_table += "<tbody class='tbody_procesar_req_aut'> </tbody> ";
                        }
                        html_table += "</table>";
                        List<Map<String, ?>> lista = a.List_procesar_req(tipo_lista, permission.isAsigFam(), permission.isEsSistema());
                        String text_html = "";
                        for (int i = 0; i < lista.size(); i++) {
                            Map<String, ?> x = lista.get(i);
                            //x.get("idtr")  String idtr = cr.Encriptar(x.get("idtr") + "");
                            text_html += "<tr>";
                            text_html += "<td>" + (i + 1) + "</td>";
                            text_html += "<td>" + x.get("mes") + " - " + x.get("anno") + "</td>";
                            text_html += "<td><a href='../../trabajador?idtr=" + x.get("idtr") + "&opc=list&dgp=" + x.get("iddgp") + "'>" + x.get("ap_p") + " " + x.get("ap_m") + " " + x.get("nombre") + "</a></td>";
                            text_html += "<td>" + x.get("puesto") + "</td>";
                            text_html += "<td>" + x.get("area") + "</td>";
                            text_html += "<td>" + x.get("dep") + "</td>";
                            text_html += "<td>" + x.get("req") + "</td>";
                            if (x.get("es_asignacion_f").equals("0")) {
                                text_html += (permission.isAsigFam()) ? "<td class='smart-form'><center><label class='toggle'><input type='checkbox' name='checkbox-toggle' class='chkAsigFam" + (i) + "' value='" + x.get("iddgp") + "'><i data-swchon-text='SI' data-swchoff-text='NO'></i></label></center></td>" : "";
                            } else {
                                text_html += (permission.isAsigFam()) ? "<td>Si</td>" : "";
                            }
                            if (x.get("es_activ_sis").equals("0")) {
                                text_html += (permission.isEsSistema()) ? "<td class='smart-form' ><center><label class='toggle'><input type='checkbox' name='checkbox-toggle' class='chkActSistEs" + (i) + "' value='" + x.get("iddgp") + "' ><i data-swchon-text='SI' data-swchoff-text='NO'></i></label></center></td>" : "";
                            } else {
                                text_html += (permission.isEsSistema()) ? "<td>Si</td>" : "";
                            }
                            text_html += "</tr>";
                        }
                        rpta.put("rpta", "1");
                        rpta.put("lista", lista.size());
                        rpta.put("text_html", text_html);
                        rpta.put("html_table", html_table);
                    }
                    if (opc.equals("ListProcesarReq")) {
                        ///response.sendRedirect("views/Dgp/Procesar_Req.html");
                    }
                    if (opc.equals("UpdateStatusDgp_Procesar")) {
                        boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
                        int tipo = Integer.parseInt(request.getParameter("tipo"));
                        String[] array = request.getParameterValues("json[]");
                        a.UpdateDgp_EstadoProcesar(array, tipo, estado);
                        rpta.put("rpta", "1");
                        rpta.put("aaas", array);
                    }
                    if (opc.equals("ShowCkbEstado_procesarIndiviual")) {
                        String iddgp = request.getParameter("iddgp");

                        List<Map<String, ?>> lista = a.ShowCkbEstado_procesarIndiviual(iddgp);
                        if (lista.size() > 0) {

                            Map<String, ?> x = lista.get(0);
                            int es_sis = Integer.parseInt(x.get("es_sis_estado") + "");
                            int es_asiFam = Integer.parseInt(x.get("es_asig_fam") + "");
                            String html_ckbAsigFam = "";
                            String html_ckbEs_Sis = "";
                            if (es_asiFam == 0) {
                                html_ckbAsigFam += (permission.isAsigFam()) ? "  <div class='col-md-8'><strong>??Asignaci??n Familiar?</strong></div><div class='col-md-4'><label class='toggle'>"
                                        + "<input type='checkbox' name='checkbox-toggle' class='ckbAsigFam' value='" + iddgp + "'><i data-swchon-text='SI' data-swchoff-text='NO'></i></label>"
                                        + "</div>"
                                        : "<div class='col-md-8'><strong>??Asignaci??n Familiar?</strong></div><div class='col-md-4'><label class='toggle'>No</div>";
                            } else if (es_asiFam == 1) {
                                html_ckbAsigFam += (permission.isAsigFam()) ? "<div class='col-md-8'><strong>??Asignaci??n Familiar?</strong></div><div class='col-md-4'>"
                                        + "<label class='toggle'><input type='checkbox' checked='' name='checkbox-toggle' class='ckbAsigFam' value='" + iddgp + "'><i data-swchon-text='SI' data-swchoff-text='NO'></i></label></div>"
                                        : "<div class='col-md-8'><strong>??Asignaci??n Familiar?</strong></div><div class='col-md-4'><label class='toggle'>Si</div>";
                            }
                            if (es_sis == 0) {
                                html_ckbEs_Sis += (permission.isEsSistema()) ? " <div class='col-md-8'><strong>??T-REGISTRO?</strong></div><div class='col-md-4'><label class='toggle'>"
                                        + "<input type='checkbox'  name='checkbox-toggle' class='ckbEstSistema' value='" + iddgp + "'><i data-swchon-text='SI' data-swchoff-text='NO'></i></label></div>"
                                        : "<div class='col-md-8'><strong>??T-REGISTRO?</strong></div><div class='col-md-4'><label class='toggle'>No</div>";
                            } else if (es_sis == 1) {
                                html_ckbEs_Sis += (permission.isEsSistema()) ? " <div class='col-md-8'><strong>??T-REGISTRO?</strong></div><div class='col-md-4'><label class='toggle'>"
                                        + "<input type='checkbox' checked='' name='checkbox-toggle' class='ckbEstSistema' value='" + iddgp + "'><i data-swchon-text='SI' data-swchoff-text='NO'></i></label></div>"
                                        : "<div class='col-md-8'><strong>??T-REGISTRO?</strong></div><div class='col-md-4'><label class='toggle'>Si</div>";
                            }
                            rpta.put("ckbAsigFam", html_ckbAsigFam);
                            rpta.put("ckbEs_Sis", html_ckbEs_Sis);
                            rpta.put("rpta", "1");
                        }
                    }

                } else {
                    // Logger.getLogger(getClass().getName()).log(Level.INFO, ide);
                    //  String idpu = e.Id_Puesto_Personal(ide);
                    session.setAttribute("List_id_Autorizacion", a.List_id_Autorizacion(idp, iduser, ""));

                    ////response.sendRedirect("views/Dgp/Autorizar_Requerimiento.html");
                }
            } catch (Exception ex) {
                rpta.put("rpta", "-1");
                rpta.put("status", false);
                rpta.put("error", ex.getMessage());
                rpta.put("mensaje", ex.getMessage());
            }

        } else {
            ////response.sendRedirect("/");
        }
        return new ResponseEntity<>(rpta ,HttpStatus.OK);
    }

}