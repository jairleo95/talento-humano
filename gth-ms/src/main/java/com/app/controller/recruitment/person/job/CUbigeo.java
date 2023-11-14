/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.person.job;

import com.app.persistence.dao.Carrera_UniversidadDAO;
import com.app.persistence.dao_imp.InterfaceCarrera_UniversidadDAO;
import com.app.persistence.dao_imp.IUbigeoDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao.UbigeoDAO;

/**
 *
 * @author Alfa.sistemas
 */
@RestController
@RequestMapping("ubigeo")
public class CUbigeo{

    IUbigeoDAO ub = new UbigeoDAO();
    InterfaceCarrera_UniversidadDAO c = new Carrera_UniversidadDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();

        String opc = request.getParameter("opc");

        try {
            if (opc.equals("dep_nac")) {
                String id_dep = request.getParameter("id_dep");
                List<Map<String, ?>> lista = ub.Provincia(id_dep);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);

            }
            if (opc.equals("pro_nac")) {
                String id = request.getParameter("id_dist");
                List<Map<String, ?>> lista = ub.Distrito(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);

            }
            if (opc.equals("Listar_D")) {
                List<Map<String, ?>> lista = ub.Departamento();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Listar_P")) {
                String id = request.getParameter("id_dep");
                List<Map<String, ?>> lista = ub.Provincia(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Listar_Di")) {
                String id = request.getParameter("id_pro");
                List<Map<String, ?>> lista = ub.Distrito(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta , HttpStatus.OK);

    }

}
