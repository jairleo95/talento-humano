/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.dgp;

import com.app.dao.Centro_CostoDAO;
import com.app.dao_imp.InterfaceCentro_CostosDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JAIR
 */

@RestController
@RequestMapping("centro_costo")
public class CCentro_Costo {

 public ResponseEntity<?> process(HttpServletRequest request){
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
        InterfaceCentro_CostosDAO cc = new Centro_CostoDAO();
        try {
            if (opc.equals("Listar_cc")) {
                List<Map<String, ?>> list = cc.List_centro_costo(iddep);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listar_dir")) {
                List<Map<String, ?>> list = cc.Direccion_CC();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listar_dep")) {
                String id = request.getParameter("id_dir");
                List<Map<String, ?>> list = cc.Departamento_CC(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listar_CC")) {
                String id = request.getParameter("id_dep");
                List<Map<String, ?>> list = cc.Centro_Costo_Dep(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listar_centro_id")) {
                String id_dgp = request.getParameter("id_dgp");
                List<Map<String, ?>> list = cc.List_centr_id(id_dgp);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listar_centro_id_dgp")) {
                String id_con = request.getParameter("id_dgp");
                List<Map<String, ?>> list = cc.List_centr_iddgp(id_con);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("listCentroCostoByIdContrato")) {
                String id_con = request.getParameter("idContrato");
                List<Map<String, ?>> list = cc.listCentroCostoByIdContrato(id_con);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Cargar_cc_DGP")) {
                String id = request.getParameter("id_c");
                List<Map<String, ?>> list = cc.Cargar_cc_dgp(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Lista_cc_area")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = cc.listar_cc_area(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Lista_cc_seccion")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = cc.listar_cc_seccion(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Eliminar_det_cc")) {
                String id_dcc = request.getParameter("id_dcc");
                cc.Eliminar_dcc(id_dcc);
                rpta.put("rpta", "1");
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta , HttpStatus.OK);
    }

}
