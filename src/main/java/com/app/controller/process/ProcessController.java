/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.process;

import com.app.persistence.dao_imp.InterfaceDetalleReqProcesoDAO;
import com.app.persistence.dao_imp.InterfaceProcesoDAO;
import com.app.domain.model.DetalleReqProceso;
import com.app.controller.util.CCriptografiar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.persistence.dao.DetalleReqProcesoDAO;
import com.app.persistence.dao.ProcesoDAO;

/**
 *
 * @author ALFA 3
 */
@RestController
@RequestMapping("Proceso")
public class ProcessController {

    InterfaceProcesoDAO p = new ProcesoDAO();
    InterfaceDetalleReqProcesoDAO iddrp = new DetalleReqProcesoDAO();

    @GetMapping
    public ResponseEntity<?> listarT() {
        //InterfaceProcesoDAO p = new ProcesoDAO();
            Map<String, Object> rpta = new HashMap<String, Object>();
            List<Map<String, ?>> lista = p.List_AllProceso();
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping("createee")
    public ResponseEntity<?> create(HttpServletRequest request) {
        //InterfaceProcesoDAO p = new ProcesoDAO();
        Map<String, Object> rpta = new HashMap<String, Object>();

        String nom = request.getParameter("nom");
        String desc = request.getParameter("desc");
        p.create(nom, desc);
        rpta.put("rpta", "1");

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request) {
        //InterfaceProcesoDAO p = new ProcesoDAO();
        Map<String, Object> rpta = new HashMap<String, Object>();

        String idpro = request.getParameter("id");
        String nom = request.getParameter("nom");
        String desc = request.getParameter("desc");
        p.editprocess(idpro, nom, desc);

        rpta.put("rpta", "1");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("details")
    public ResponseEntity<?> details(HttpServletRequest request) {
        //InterfaceProcesoDAO p = new ProcesoDAO();
        Map<String, Object> rpta = new HashMap<String, Object>();

        String id_req = request.getParameter("id_req");
        String id_pro = request.getParameter("id_pro");
        String id_dir = request.getParameter("id_dir");
        String id_dep = request.getParameter("id_dep");
        String id_area = request.getParameter("id_area");
        String id_ti_planilla = request.getParameter("id_ti_planilla");
        List<Map<String, ?>> lista = p.List_Pro_Paso_Id(id_req, id_pro, id_dir, id_dep, id_area, id_ti_planilla);
        rpta.put("rpta", "1");
        rpta.put("lista", lista);

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }
    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        InterfaceProcesoDAO p = new ProcesoDAO();
        Map<String, Object> rpta = new HashMap<String, Object>();
        List<Map<String, ?>> lista = p.List_Proceso();
        rpta.put("rpta", "1");
        rpta.put("lista", lista);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    //todo: refactor this
    @PostMapping("register")
     public ResponseEntity<?> process(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();

        try {
            String opc = request.getParameter("opc");

            if (opc.equals("Mantenimiento")) {
                ////response.sendRedirect("views/Dgp/Plazo/Reg_Plazo_Dgp.html");

            }
            if (opc.equals("Eliminar")) {

            }
            if (opc.equals("statupdate")) {
                String id = request.getParameter("id");
                String es = request.getParameter("es");
                p.statupdate(id, es);
                System.out.println("All is right in controller " + es + " " + id);
            }
            if (opc.equals("insertDetalleReqProceso")) {
                DetalleReqProceso drp = new DetalleReqProceso();
                drp.setEsReqProceso("1");

                String area = "0";
                String departamento = "0";
                
                if (request.getParameter("area") .equals("")) {
                    area = "0";
                } else {
                    area = request.getParameter("area");
                }
                if (request.getParameter("departamento") .equals("")) {
                    departamento = "0";
                } else {
                    departamento = request.getParameter("departamento");
                }
                
                drp.setIdArea(area);
                drp.setIdDepartamento(departamento);
                drp.setIdDireccion(request.getParameter("direccion"));

                drp.setIdProceso(request.getParameter("proceso"));
                drp.setIdRequerimiento(request.getParameter("requerimiento"));
                  rpta.put("serialize", drp);
                String id = iddrp.insertDetalleReqProceso(drp);
                rpta.put("id", CCriptografiar.Encriptar(id));
                rpta.put("status", true);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


}
