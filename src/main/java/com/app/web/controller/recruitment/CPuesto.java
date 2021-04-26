/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.app.dao.AreaDAO;
import com.app.dao.DireccionDAO;
import com.app.dao.PuestoDAO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.DepartamentoDao;
import com.app.dao.SeccionDAO;
import com.app.dao_imp.InterfaceAreaDAO;
import com.app.dao_imp.InterfaceDepartamentoDAO;
import com.app.dao_imp.InterfaceDireccionDAO;
import com.app.dao_imp.InterfacePuestoDAO;
import com.app.dao_imp.InterfaceSeccionDAO;

/**
 *
 * @author Andres
 */

@RestController
@RequestMapping("Puesto")
public class CPuesto {

    InterfaceDireccionDAO di = new DireccionDAO();
    InterfaceDepartamentoDAO de = new DepartamentoDao();
    InterfaceAreaDAO ar = new AreaDAO();
    InterfaceSeccionDAO se= new SeccionDAO();
    InterfacePuestoDAO pu= new PuestoDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();

        try {
            String opc = request.getParameter("opc");
            if (opc.equals("menu")) {
                ///response.sendRedirect("views/Puesto/Menu_puesto.html");
            }
            //----DIRECCION
            if (opc.equals("list_direccion")) {
                List<Map<String, ?>> list = di.List_Direccion();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("editar-Direccion")) {
                String id, nombre, ncorto, estado, filial;
                id = request.getParameter("id");
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                filial = request.getParameter("filial");
                di.Editar_Direccion(id, nombre, ncorto, estado, filial);
            }
            if (opc.equals("crear-Direccion")) {
                String nombre, ncorto, estado, filial;
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                filial = request.getParameter("filial");
                di.Crear_Direccion(nombre, ncorto, estado, filial);
            }
            if (opc.equals("activar-Direccion")) {
                String id;
                id = request.getParameter("id");
                di.Activar_Direccion(id);
            }
            if (opc.equals("desactivar-Direccion")) {
                String id;
                id = request.getParameter("id");
                di.Desactivar_Direccion(id);
            }
            if (opc.equals("eliminar-Direccion")) {
                String id;
                id = request.getParameter("id");
                di.Eliminar_Direccion(id);
            }
            //--DEPARTAMENTO
            if (opc.equals("listar_dir_es")) {
                List<Map<String, ?>> list = di.List_Direccion_estado();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("listar_dep_dir")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = de.Listar_dep_x_dir(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("editar-Dep")) {
                String id, nombre, ncorto, estado, idDir;
                id = request.getParameter("id");
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                idDir = request.getParameter("idDir");
                de.Editar_Dep(id.trim(), nombre.trim(), ncorto.trim(), estado.trim(), idDir.trim());
            }
            if (opc.equals("crear-Dep")) {
                String nombre, ncorto, estado, idDir;
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                idDir = request.getParameter("idDir");
                de.Crear_Dep(nombre.trim(), ncorto.trim(), estado.trim(), idDir.trim());
            }
            if (opc.equals("activar-Dep")) {
                String id;
                id = request.getParameter("id");
                de.Activar_Dep(id.trim());
            }
            if (opc.equals("desactivar-Dep")) {
                String id;
                id = request.getParameter("id");
                de.Desactivar_Dep(id.trim());
            }
            if (opc.equals("eliminar-Dep")) {
                String id;
                id = request.getParameter("id");
                boolean x = de.Eliminar_Dep(id.trim());
            }
            //AREA
            if (opc.equals("list_dep_es")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = de.Listar_dep_x_es(id.trim());
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("list_area_dep")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = ar.List_area_id_d(id.trim());
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("crear_area")) {
                String nombre, ncorto, estado, idDep;
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                idDep = request.getParameter("idDep");
                ar.crear_area(nombre.trim(), ncorto.trim(), estado.trim(), idDep.trim());
            }
            if (opc.equals("editar_area")) {
                String idArea, nombre, ncorto, estado, idDep;
                idArea = request.getParameter("id");
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                idDep = request.getParameter("idDep");
                ar.editar_area(idArea.trim(), nombre.trim(), ncorto.trim(), estado.trim(), idDep.trim());
            }
            if (opc.equals("activar_area")) {
                String id = request.getParameter("id");
                ar.activar_area(id.trim());
            }
            if (opc.equals("desactivar_area")) {
                String id = request.getParameter("id");
                ar.desactivar_area(id.trim());
            }
            if (opc.equals("eliminar_area")) {
                String id = request.getParameter("id");
                ar.eliminar_area(id);
            }
            //SECCION
            if (opc.equals("list_area_es")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = ar.List_area_es(id);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("list_sec_area")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = se.List_sec_ida(id.trim());
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("crear_seccion")) {
                String nombre, ncorto, estado, idArea;
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                idArea = request.getParameter("idArea");
                se.crear_seccion(nombre.trim(), ncorto.trim(), estado.trim(), idArea.trim());
            }
            if (opc.equals("editar_seccion")) {
                String id, nombre, ncorto, estado, idArea;
                id= request.getParameter("id");
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                idArea = request.getParameter("idArea");
                se.editar_seccion(id.trim(), nombre.trim(), ncorto.trim(), estado.trim(), idArea.trim());
            }
            if (opc.equals("activar_seccion")) {
                String id = request.getParameter("id");
                se.activar_seccion(id.trim());
            }
            if (opc.equals("desactivar_seccion")) {
                String id = request.getParameter("id");
                se.desactivar_seccion(id.trim());
            }
            if (opc.equals("eliminar_seccion")) {
                String id = request.getParameter("id");
                se.eliminar_seccion(id.trim());
            }
            //PUESTO
            if (opc.equals("list_sec_es")) {
                String id= request.getParameter("id");
                List<Map<String, ?>> list = se.List_sec_es(id.trim());
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("list_puesto_sec")) {
                String id= request.getParameter("id");
                List<Map<String, ?>> list = pu.Listar_Puesto_id(id.trim());
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("crear_puesto")) {
                String nombre, ncorto, estado, idSec, cGrupo;
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                cGrupo = request.getParameter("cGrupo");
                idSec = request.getParameter("idSec");
                pu.crear_puesto(nombre.trim(), ncorto.trim(), estado.trim(), cGrupo.trim(), idSec.trim());
            }
            if (opc.equals("editar_puesto")) {
                String id,nombre, ncorto, estado, idSec, cGrupo;
                id = request.getParameter("id");
                nombre = request.getParameter("nombre");
                ncorto = request.getParameter("ncorto");
                estado = request.getParameter("estado");
                cGrupo = request.getParameter("cGrupo");
                idSec = request.getParameter("idSec");
                pu.editar_puesto(id.trim(), nombre.trim(), ncorto.trim(), estado.trim(), cGrupo.trim(), idSec.trim());
            }
            if (opc.equals("activar_puesto")) {
                String id = request.getParameter("id");
                pu.activar_puesto(id.trim());
            }
            if (opc.equals("desactivar_puesto")) {
                String id = request.getParameter("id");
                pu.desactivar_puesto(id.trim());
            }
            if (opc.equals("eliminar_puesto")) {
                String id = request.getParameter("id");
                pu.eliminar_puesto(id.trim());
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
