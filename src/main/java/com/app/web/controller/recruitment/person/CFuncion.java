/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.person;

import com.app.dao.DireccionDAO;
import com.app.dao.FuncionDAO;
import com.app.dao.PuestoDAO;
import com.app.dao_imp.InterfaceDireccionDAO;
import com.app.dao_imp.InterfaceFuncionDAO;
import com.app.dao_imp.InterfacePuestoDAO;

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

/**
 *
 * @author joserodrigo
 */

@RestController
@RequestMapping("funcion")
public class CFuncion {

    InterfaceFuncionDAO f = new FuncionDAO();
    InterfacePuestoDAO p = new PuestoDAO();
    InterfaceDireccionDAO di = new DireccionDAO();

    @PostMapping
    protected ResponseEntity<?> process(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String id_user = (String) sesion.getAttribute("IDUSER");

        /* TODO output your page here. You may use following sample code. */
        try {
            if (opc.equals("princpal_funcion")) {
                sesion.setAttribute("Listar_Direccion", di.Listar_Direccion());
                ///response.sendRedirect("views/Funciones/Otorgar_funciones.html");
            }
            
            if (opc.equals("listar_x_puesto")) {
                String id_pu = request.getParameter("id_puesto");
                List<Map<String, ?>> list = f.Listar_fun_x_pu(id_pu);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("listarF")) {
                List<Map<String, ?>> list = f.Listar_Funciones();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("listar_Direccion")) {
                List<Map<String, ?>> list = di.List_Direccion();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("otorgar_funciones")) {
                sesion.setAttribute("Listar_Direccion", di.Listar_Direccion());
                ///response.sendRedirect("views/Funciones/Otorgar_funciones.html");
            }
            if (opc.equals("otorgar")) {
                String id_puesto=request.getParameter("id_puesto");
                String de_funcion=request.getParameter("de_funcion");
                String ti_funcion=request.getParameter("ti_funcion");
                f.Insertar_funcion(id_puesto, de_funcion, id_user, ti_funcion);
            }
            if(opc.equals("list_pu")){
                List<Map<String, ?>> list = p.List_puesto();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("del_fun")) {
                String id_funcion=request.getParameter("id_fun");
                f.Eliminar_funcion(id_funcion);
            }
            if (opc.equals("edit_function")) {
                String id_funcion=request.getParameter("id_fun");
                String de_funcion=request.getParameter("de_fun");
                String es_funcion=request.getParameter("es_fun");
                String id_puesto=request.getParameter("id_pu");
                String ti_funcion=request.getParameter("ti_funcion");
                f.Modificar_funcion(id_funcion, es_funcion, de_funcion, id_puesto, id_user, ti_funcion);
            }
            if (opc.equals("direccion")){
                String id_de=request.getParameter("idDireccion");
                List<Map<String, ?>> list = f.Listar_dep_x_dir(id_de);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("departamento")){
                String id_de=request.getParameter("idDepartamento");
                List<Map<String, ?>> list = f.Listar_ar_x_dep(id_de);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("area")){
                String id_de=request.getParameter("idArea");
                List<Map<String, ?>> list = f.Listar_sec_x_area(id_de);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("seccion")){
                String id_se=request.getParameter("idSeccion");
                List<Map<String, ?>> list = f.Listar_pu_x_sec(id_se);
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
