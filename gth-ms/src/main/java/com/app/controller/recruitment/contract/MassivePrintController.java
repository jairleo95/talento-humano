/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.contract;

import com.app.persistence.dao.ContratoDAO;
import com.app.persistence.dao_imp.IContratoDAO;
import com.app.controller.util.DateFormat;
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

/**
 *
 * @author joserodrigo
 */

@RestController
@RequestMapping("impresion_masiva")
public class MassivePrintController {


    DateFormat co = new DateFormat();

    @PostMapping
    public ResponseEntity<?> processRequest(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);

        String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
        DateFormat co = new DateFormat();
        IContratoDAO c = new ContratoDAO();
        try {
            if (opc.equals("filtrar")) {
                String del = request.getParameter("desde").trim();

                String al = request.getParameter("al").trim();
                String nom_ape = request.getParameter("nom_ape");
                String direccion = request.getParameter("direccion");
                String departamento = request.getParameter("departamento");
                String area = request.getParameter("area");
                String seccion = request.getParameter("seccion");
                String puesto = request.getParameter("puesto");
                String fec_i = request.getParameter("fec_i");
                String fec_f = request.getParameter("fec_f");
                String sueldo = request.getParameter("sueldo");
                String fe_sus = request.getParameter("fe_sus");
                if (direccion.equals("null")) {
                    direccion = null;
                }
                List<Map<String, ?>> list = c.Listar_Contratos(del, al, direccion, departamento, area, seccion, puesto, sueldo, nom_ape, fec_i, fec_f, fe_sus, iddep);
                String List = del + "," + al + "," + direccion + "," + nom_ape + "," + departamento + "," + area + "," + seccion + "," + puesto + "," + fec_i + "," + fec_f + "," + sueldo;
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
