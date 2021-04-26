/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.request;

import com.app.dao_imp.InterfaceSolicitud_RequerimientoDAO;
import com.app.util.DateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.Solicitud_RequerimientoDAO;

/**
 *
 * @author ALFA 3
 */

@RestController
@RequestMapping("solicitud_requerimiento")
public class CSolicitud_Requerimiento {

    InterfaceSolicitud_RequerimientoDAO s = new Solicitud_RequerimientoDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request){

        String opc = request.getParameter("opc");

        HttpSession sesion = request.getSession(true);
        String iduser = (String) sesion.getAttribute("IDUSER");
        String rol = (String) sesion.getAttribute("IDROL");
        String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
        Map<String, Object> rpta = new HashMap<String, Object>();

        if (opc.equals("Listar_Solicitud")) {
            sesion.setAttribute("Listar_solicitud", s.Listar_solicitud());
            ///response.sendRedirect("views/Solicitud/Reporte_Solicitud.html");
        }
        if (opc.equals("Listar_Sol_Pendientes")) {
            List<Map<String, ?>> list;
            if (rol.equals("ROL-0001")) {
                list = s.Listar_solicitud("0", null);
            } else {
                list = s.Listar_solicitud("0", iddep);
            }

            rpta.put("rpta", "1");
            rpta.put("lista", list);
        }
        if (opc.equals("Listar_Sol_Aut")) {
            List<Map<String, ?>> list;
            if (rol.equals("ROL-0001")) {
                list = s.Listar_solicitud("1", null);
            } else {
                list = s.Listar_solicitud("1", iddep);
            }
            rpta.put("rpta", "1");
            rpta.put("lista", list);
        }
        if (opc.equals("Registrar_solicitud")) {
            String FE_DESDE = request.getParameter("desde");
            String ID_DGP = request.getParameter("iddgp");
            String ID_PLAZO = request.getParameter("plazo");
            String DE_SOLICITUD = request.getParameter("descripcion");
            String ES_AUTORIZAR = request.getParameter("ES_AUTORIZAR");
            String ES_SOLICITUD_DGP = request.getParameter("ES_SOLICITUD_DGP");
            String IP_USUARIO = request.getParameter("IP_USUARIO");
            String FE_CREACION = request.getParameter("FE_CREACION");
            String US_MODIF = request.getParameter("US_MODIF");
            String FE_MODIF = request.getParameter("FE_MODIF");
            String NO_USUARIO = request.getParameter("NO_USUARIO");
            String tipo = request.getParameter("tipo_fecha");
            if (tipo.equals("month")) {
                FE_DESDE = FE_DESDE + "-01";
            }
            s.INSERT_SOLICITUD_DGP(null, FE_DESDE, ID_DGP, ID_PLAZO, DE_SOLICITUD, ES_AUTORIZAR, ES_SOLICITUD_DGP, IP_USUARIO, iduser, FE_CREACION, US_MODIF, FE_MODIF, NO_USUARIO);

            sesion.setAttribute("List_Solicitud_User", s.Listar_solicitud_id_us(iduser, ID_DGP));
        }
        if (opc.equals("Reg_List_Solicitud")) {
            String iddgp = request.getParameter("iddgp");
            /**
             * String tipo = request.getParameter("tipo"); String idplazo =
             * request.getParameter("plazo"); String desde =
             * request.getParameter("desde"); String desc =
             * request.getParameter("descripcion");
             */
            sesion.setAttribute("List_Solicitud_User", s.Listar_solicitud_id_us(iduser, iddgp));
            //response.sendRedirect("views/Solicitud/");
            // rpta.put("rpta", "1");
        }
        if (opc.equals("Ver_Detalle_Solicitud")) {
            String id = request.getParameter("id");
            sesion.setAttribute("Detalle_Solicitud", s.Listar_solicitud_id(id));
            ///response.sendRedirect("views/Solicitud/Detalle_Solicitud.html");
        }
        if (opc.equals("Ver_Solicitud")) {
            String id = request.getParameter("id");
            List<Map<String, ?>> list = s.List_solicitud_id(id);
            rpta.put("rpta", "1");
            rpta.put("lista", list);
            if (rol.equals("ROL-0001") | rol.equals("ROL-0007")) {
                rpta.put("permisos", "<button type=\"button\" class=\"btn btn-default btn-labeled\" data-dismiss=\"modal\"><span class=\"btn-label btn_cancel_form\"><i class=\"fa fa-times\"></i></span>Cancel</button><button class=\"btn btn-primary btn-labeled btn_procesar_sol\"  type=\"button\" ><span class=\"btn-label\"><i class=\"fa fa-check\"></i></span>Procesar</button>");
            }
        }
        if (opc.equals("Procesar_Solicitud")) {
            String id = request.getParameter("id");
            String tipo = request.getParameter("tipo");
            String fecha = request.getParameter("fecha");
            String comentario = request.getParameter("comentario");
            s.procesar_solicitud(tipo, id, DateFormat.toFormat3(fecha), iduser, comentario);
            rpta.put("rpta", "1");
        }
        if (opc.equals("Val_Envio_Solicitud")) {
            String h = "";
            String id = request.getParameter("iddgp");
            boolean estado = s.Validar_Envio_Solicitud(id);
            if (estado == true) {
                h = "<form id=\"checkout-form\" class=\"smart-form solicitud_plazo\">\n"
                        + "                                            <div class=\"row\">\n"
                        + "\n"
                        + "                                                <section class=\"col col-4\">\n"
                        + "                                                    <label class=\"label\">Tipo de Plazo :</label>\n"
                        + "                                                    <label class=\"select\"> \n"
                        + "                                                        <select name=\"tipo\" class=\"tipo\" required=\"\">\n"
                        + "                                                            <option value=''>[SELECCIONE]</option>\n"
                        + "                                                            <option value='2'>Ingreso a planilla</option>\n"
                        + "                                                            <option value='1' selected >Inicio de Contrato</option>\n"
                        + "\n"
                        + "                                                        </select>          \n"
                        + "                                                    </label>\n"
                        + "                                                </section>\n"
                        + "                                                <section class=\"col col-4\">\n"
                        + "                                                    <label class=\"label\">Plazo :</label>\n"
                        + "                                                    <label class=\"select\"> \n"
                        + "                                                        <select name=\"plazo\" class=\"plazo\" required=\"\">\n"
                        + "                                                            <option value='' selected >[SELECCIONE]</option>\n"
                        + "                                                        </select>          \n"
                        + "                                                    </label>\n"
                        + "                                                </section>\n"
                        + "                                                <section class=\"col col-4\">\n"
                        + "                                                    <label class=\"label lb_fecha_solicitud\">Fecha de Inicio :</label>\n"
                        + "                                                    <label class=\"input\"> \n"
                        + "\n"
                        + "                                                        <input type=\"date\" name=\"desde\"  class=\"fe_inicio\" required=\"\">\n"
                        + "                                                        <input type=\"hidden\" name=\"tipo_fecha\"  class=\"tipo_fecha\" value=\"date\" required=\"\">\n"
                        + "                                                    </label>\n"
                        + "                                                </section>\n"
                        + "                                            </div>\n"
                        + "                                            <section >\n"
                        + "                                                <label class=\"label\">Motivo :</label>\n"
                        + "                                                <label class=\"textarea\"> 										\n"
                        + "                                                    <textarea rows=\"3\" class=\"comentario\" name=\"descripcion\" placeholder=\"\" required=\"\"></textarea> \n"
                        + "                                                </label>\n"
                        + "                                            </section>\n"
                        + "                                            <footer>\n"
                        + "                                                <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">\n"
                        + "                                                    Cancelar\n"
                        + "                                                </button>\n"
                        + "                                                <button type=\"button\" class=\"btn btn-primary sbm_solicitud\" data-dismiss=\"modal\" >\n"
                        + "                                                    Enviar\n"
                        + "                                                </button>\n"
                        + "                                            </footer>\n"
                        + "\n"
                        + "                                        </form>  ";
            } else {
                h = "Ya tiene una solicitud en proceso";
            }
            rpta.put("rpta", "1");
            rpta.put("estado", estado);
            rpta.put("html", h);
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

}
