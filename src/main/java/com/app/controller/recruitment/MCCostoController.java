/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment;

import com.app.persistence.dao.CentroCostoDAO;
import com.app.persistence.dao_imp.InterfaceCentroCosto;
import com.app.controller.util.StringTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Andres
 */

@RestController
@RequestMapping("Costo")
public class MCCostoController {

    @PostMapping
    public ResponseEntity<?> processRequest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        // String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
        InterfaceCentroCosto cc = new CentroCostoDAO();
        try {
            if (opc.equals("menu")) {
                response.sendRedirect("views/CCosto/MantCCosto.html");
            }
            if (opc.equals("list_ccosto")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista;
                if (id != null) {
                    lista = cc.listarCcosto(id);
                } else {
                    lista = cc.listarCcosto();
                }
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("list_dir")) {
                List<Map<String, ?>> lista = cc.List_Direccion();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Asignar_cc")) {
                String id_centro_costo = request.getParameter("id_cc");
                String id_departamento = request.getParameter("dep");
                String id_area = request.getParameter("area");
                String id_seccion = request.getParameter("seccion");
                cc.AsignarCentroCosto(id_centro_costo, id_departamento, id_area, id_seccion);
                rpta.put("rpta", "1");
            }
            if (opc.equals("list_dep")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista = cc.List_Depxdir(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("list_ar")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista = cc.List_Arxdep(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("list_se")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista = cc.List_SecxArea(id);
                rpta.put("rpta", lista.size());
                rpta.put("lista", lista);
            }
            if (opc.equals("edit_cc")) {
                String ID_CENTRO_COSTO, CO_CENTRO_COSTO, DE_CENTRO_COSTO, ID_DEPARTAMENTO, ID_AREA, ID_SECCION;
                ID_CENTRO_COSTO = StringTools.getString(request.getParameter("ID_CENTRO_COSTO"));
                CO_CENTRO_COSTO = StringTools.getString(request.getParameter("CO_CENTRO_COSTO"));
                DE_CENTRO_COSTO = StringTools.getString(request.getParameter("DE_CENTRO_COSTO"));
                ID_DEPARTAMENTO = StringTools.getString(request.getParameter("ID_DEPARTAMENTO"));
                ID_AREA = StringTools.getString(request.getParameter("ID_AREA"));
                ID_SECCION = StringTools.getString(request.getParameter("ID_SECCION"));
                String ID_DET_CC = StringTools.getString(request.getParameter("id_det_cc"));

                cc.editarCCosto(ID_CENTRO_COSTO, CO_CENTRO_COSTO, DE_CENTRO_COSTO, ID_DEPARTAMENTO, ID_AREA, ID_SECCION, ID_DET_CC);
                rpta.put("rpta", "1");
            }
            if (opc.equals("add_cc")) {
                String CO_CENTRO_COSTO, DE_CENTRO_COSTO, ID_DEPARTAMENTO, ID_AREA, ID_SECCION;
                CO_CENTRO_COSTO = request.getParameter("CO_CENTRO_COSTO");
                DE_CENTRO_COSTO = request.getParameter("DE_CENTRO_COSTO");
                ID_DEPARTAMENTO = request.getParameter("ID_DEPARTAMENTO");
                ID_AREA = request.getParameter("ID_AREA");
                ID_SECCION = request.getParameter("ID_SECCION");
                cc.crearCCosto(CO_CENTRO_COSTO, DE_CENTRO_COSTO, ID_DEPARTAMENTO, ID_AREA, ID_SECCION);
            }
            if (opc.equals("del_cc")) {
                String ID_CENTRO_COSTO;
                ID_CENTRO_COSTO = request.getParameter("id_det_cc");

                rpta.put("isDeleted", cc.eliminarCCosto(ID_CENTRO_COSTO));
               rpta.put("status", true);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("status", false);
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


}
