/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.person.job;

import com.app.persistence.dao.AreaDAO;
import com.app.persistence.dao.DireccionDAO;
import com.app.persistence.dao.PuestoDAO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao.DepartamentoDao;
import com.app.persistence.dao.ListaDAO;
import com.app.persistence.dao.SeccionDAO;
import com.app.persistence.dao.Sub_ModalidadDAO;
import com.app.persistence.dao_imp.IAreaDAO;
import com.app.persistence.dao_imp.IDepartamentoDAO;
import com.app.persistence.dao_imp.IDireccionDAO;
import com.app.persistence.dao_imp.IListaDAO;
import com.app.persistence.dao_imp.IPuestoDAO;
import com.app.persistence.dao_imp.InterfaceSeccionDAO;
import com.app.persistence.dao_imp.ISub_ModalidadDAO;

/**
 *
 * @author joserodrigo
 */
@RestController
@RequestMapping("Direccion_Puesto")
public class CDir_Puesto {


    IDepartamentoDAO dep = new DepartamentoDao();
    IAreaDAO are = new AreaDAO();
    InterfaceSeccionDAO sec = new SeccionDAO();
    IPuestoDAO p = new PuestoDAO();
    ISub_ModalidadDAO sub = new Sub_ModalidadDAO();
    IDireccionDAO dir = new DireccionDAO();
    IListaDAO lD=new ListaDAO();

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");

        /*LOS QUE SIRVEN*/
        if (opc.equals("getDirections")) {//Listar_direccion
            List<Map<String, ?>> lista = dir.List_Direccion();
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_direccion_filial")) {
            String id_fil = request.getParameter("id_fil");
            List<Map<String, ?>> lista = dir.List_Direccion_filial(id_fil);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_dir_dep")) {
            String id = request.getParameter("id");
            List<Map<String, ?>> lista = dep.Listar_dep_id(id);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_area2")) {
            String id_dep = request.getParameter("id");
            List<Map<String, ?>> lista = are.List_area_id_d(id_dep);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_sec2")) {
            String id_are = request.getParameter("id");
            List<Map<String, ?>> lista = sec.List_sec_ida_Es(id_are);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_pu_id")) {
            String id = request.getParameter("id");
            String es_l = request.getParameter("esL");
            List<Map<String, ?>> lista = p.Listar_Puesto_id_es(id);
            if (es_l != null) {

                List<Map<String, ?>> lista1 = new ArrayList<>();
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).get("estado").equals("1")) {
                        lista1.add(lista.get(i));
                    }
                }
                rpta.put("rpta", "1");
                rpta.put("lista", lista1);
            } else {
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

        }
        if (opc.equals("Listar_area")) {
            String id_dep = request.getParameter("id_dep");
            List<Map<String, ?>> lista = are.List_area_id_json(id_dep);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_sec")) {
            String id_are = request.getParameter("id_are");
            List<Map<String, ?>> lista = sec.List_sec_id(id_are);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


    @PostMapping
    public void processRequest(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");

        HttpSession sesion = request.getSession(true);
        String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
        try {


            /*CIERRE*/
            if (opc.equals("Listar")) {
                String id_dir = request.getParameter("id_dir");
                List<Map<String, ?>> lista = dep.Listar_dep_id(id_dir);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);

            }
            if (opc.equals("Listar_Dir")) {
                List<Map<String, ?>> lista = dir.List_Direccion();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

            if (opc.equals("Listar_dir")) {
                String id_dep = request.getParameter("id_dep");
                List<Map<String, ?>> lista = are.List_area_id_json(id_dep);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            /* seleccion de id por puestos para reg_contratos*/
            if (opc.equals("Reg_puesto_paso")) {
                String idpu = request.getParameter("id_pu");
                String idpasos = request.getParameter("idpasos");
                String co_pasos = request.getParameter("co_pasos");
                p.Registrar_Puesto_Paso(null, idpasos, idpu, null, co_pasos);
                rpta.put("rpta", "1");
            }
            /*SUBMODALIDAD*/
            if (opc.equals("Listar_SUB_MO")) {
                String id_mod = request.getParameter("id");
                List<Map<String, ?>> lista = sub.Listar_Sub_mo_x_mod(id_mod);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);

            }
            /*Listas para Registro DGP*/
            if (opc.equals("List_Area_RDGP")) {
                List<Map<String, ?>> lista = are.List_area_es(iddep);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);

            }
            if (opc.equals("list_req")) {
                List<Map<String, String>> lista = lD.listCondicionContratoJson();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        Gson gson = new Gson();
//        out.print(gson.toJson(rpta));
//        out.flush();
//        out.close();
    }


}
