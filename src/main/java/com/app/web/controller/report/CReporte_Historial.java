/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.report;

import com.app.dao.ListaDAO;
import com.app.dao_imp.InterfaceListaDAO;
import com.app.dao_imp.InterfaceReporte_HistorialDAO;

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
import com.app.dao.Reporte_HistorialDAO;

/**
 *
 * @author Andres
 */

@RestController
@RequestMapping("RHistorial")
public class CReporte_Historial {

    InterfaceReporte_HistorialDAO re = new Reporte_HistorialDAO();
    InterfaceListaDAO li = new ListaDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String id_user = (String) sesion.getAttribute("IDUSER");

        try {
            if (opc.equals("hist_tra")) {
                String pagina = "views/Reportes/Trabajador/Historial.html";
                ///response.sendRedirect(pagina);
            }
            if (opc.equals("list_mod_fecha")) {
                String FE_INICIO = request.getParameter("fe_inicio");
                String FE_FIN = request.getParameter("fe_fin");
                List<Map<String, ?>> list = re.Listar_Tra_Fecha(FE_INICIO, FE_FIN);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("mod_tra")) {
                String ID_TRABAJADOR = request.getParameter("idtr");
                ///response.sendRedirect("views/Reportes/Trabajador/detalleHistorial.html?idtr=" + ID_TRABAJADOR);
            }
            if (opc.equals("list_mod_tra")) {
                String ID_TRABAJADOR = request.getParameter("idtr");
                List<Map<String, ?>> list = re.Listar_Mod_Tra(ID_TRABAJADOR);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("hist_es_civil")) {
                sesion.setAttribute("List_Estado_Civil", li.List_Estado_Civil());
                ///response.sendRedirect("views/Reportes/Trabajador/Historial_Est_Civil.html");
            }
            if (opc.equals("list_hist_es_civil")) {
                String FE_INICIO = request.getParameter("fe_inicio");
                String FE_FIN = request.getParameter("fe_fin");
                List<Map<String, ?>> list = re.Listar_His_Estado_Civil(FE_INICIO, FE_FIN);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if(opc.equals("Procesar_reg_ec")){
                String id_ec = request.getParameter("idec");
                re.Procesar_Est_Civil(id_ec);
                rpta.put("rpta", "1");
            }
            if(opc.equals("Detalle_hist_ec")){
                String idtr = request.getParameter("idtr"); 
                String nombre = request.getParameter("name");
                ///response.sendRedirect("views/Reportes/Trabajador/Det_Historial_Est_Civil.html?idtr=" + idtr + "&name="+ nombre  );
            }
            if(opc.equals("list_detalle_ec")){
                String idtr = request.getParameter("idtr"); 
                List<Map<String, ?>> list = re.Listar_Det_EC(idtr);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            
            if (opc.equals("Filtro_hijo")) {
                String FE_INICIO = request.getParameter("fe_inicio");
                String FE_FIN = request.getParameter("fe_fin");
                String tipo = request.getParameter("tipo");
                List<Map<String, ?>> lista = re.List_historial_modf_hijo(FE_INICIO, FE_FIN, tipo);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Fe_Modif_Hijo")) {
                String id = request.getParameter("hijo");
                List<Map<String, ?>> lista = re.list_fecha_modif(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Fe_Modif_Hijo2")) {
                String id = request.getParameter("hijo");
                String fecha = request.getParameter("fecha");
                List<Map<String, ?>> lista = re.list_fecha_modif2(id, fecha);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Comparar_dato_Hijo")) {
                String id = request.getParameter("id");
                String fecha1 = request.getParameter("fecha1");
                String fecha2 = request.getParameter("fecha2");
                List<Map<String, ?>> lista = re.Lista_campos_modif(fecha1, fecha2, id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("list_hist_fecha")) {
                String fe_modif = request.getParameter("fe_modif");
                String idtra = request.getParameter("idtra");
                List<Map<String, ?>> lista = re.Listar_hist_fecha(fe_modif, idtra);
                String[]r =re.decode((String)lista.get(0).get("col62"));
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
                rpta.put("us_ip", r);
            }
            if (opc.equals("list_actual")) {
                String idtra = request.getParameter("idtra");
                List<Map<String, ?>> lista = re.Listar_dat_actual(idtra);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Comparar_hijo")) {
                String id_trabajador = request.getParameter("idtr");
                String id_hijo = request.getParameter("idh");
                String fecha_default = request.getParameter("fecha_default");
                ///response.sendRedirect("views/Reportes/Hijo/Detalle_Comparacion.html?idtr=" + id_trabajador + "&idh=" + id_hijo + "&fecha_default=" + fecha_default);
            }
            if (opc.equals("Listar_hijo_trabajador")) {
                String id_trabajador = request.getParameter("idtr");
                List<Map<String, ?>> lista = re.list_hijo_trabajdor(id_trabajador);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Procesar_datos_hijos")) {
                String es_fecha = request.getParameter("es_fecha");
                String fecha = request.getParameter("fecha");
                String idh = request.getParameter("idh");
                re.Procesar_historial_hijo(idh, es_fecha, fecha);
                rpta.put("rpta", "1");
            }
            if (opc.equals("Historial_Datos_Hijo")) {
               ///response.sendRedirect("views/Reportes/Hijo/Historial_Hijo.html");
            }
            if (opc.equals("proc_hist")) {
                String idtra= request.getParameter("idtra");
                String fe_modif= request.getParameter("fe_modif");
                re.procesar_h(idtra, fe_modif);
            }
            if (opc.equals("proc_act")) {
                String idtra= request.getParameter("idtra");
                re.procesar_a(idtra);
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
