/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.process.deliveryTerm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.persistence.dao.Plazo_DgpDAO;
import com.app.persistence.dao_imp.InterfacePlazo_DgpDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JAIR
 */
@RestController
@RequestMapping("plazo_dgp")
public class TimeLimitController {

    InterfacePlazo_DgpDAO pl = new Plazo_DgpDAO();

    @GetMapping
    public ResponseEntity<?> read(HttpServletRequest request){
        Map<String, Object> rpta = new HashMap<String, Object>();
        HttpSession sesion = request.getSession(true);

        String opc = request.getParameter("opc");
        if (opc.equals("Listar")) {
            String tipo = request.getParameter("tipo");
            List<Map<String, ?>> lista = pl.List_Plazo(tipo);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_Plazo")) {
            String id_dep = request.getParameter("departamento");
            String id_area = request.getParameter("area");
            String tipo = request.getParameter("tipo");
            String req = request.getParameter("id_req");
            int dias = Integer.parseInt(request.getParameter("tolerancia"));
            String dep = request.getParameter("dep_tolerancia");
            List<Map<String, ?>> lista = pl.Listar_Plazo(tipo, req, dias, dep, id_dep, id_area);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Ver_detalle_plazo")) {
            String iddgp = request.getParameter("iddgp");
            sesion.setAttribute("Lista_detalle_plazo", pl.Lista_detalle_plazo(iddgp));
            //response.sendRedirect("views/Dgp/Plazo/Detalle_Plazo.html");
        }
        if (opc.equals("List_id_plazo")) {
            String id = request.getParameter("tipo");
            String iddgp = request.getParameter("iddgp");
            List<Map<String, ?>> lista = pl.Listar_Plazo_id(id, iddgp);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("fecha_habilitada")) {
            String tipo = request.getParameter("tipo");
            String req = request.getParameter("req");
            String dias = request.getParameter("dias");
            String dep = request.getParameter("dep");
            String fecha = pl.HABILITAR_FECHA(tipo, req, dias, dep);
            rpta.put("fecha", fecha);
            rpta.put("rpta", "1");
        }
        return new ResponseEntity<>(rpta,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> rpta = new HashMap<String, Object>();
        HttpSession session = request.getSession(true);
        try {
            String opc = request.getParameter("opc");
            if (opc.equals("Mantenimiento")) {
                response.sendRedirect("views/Dgp/Plazo/Reg_Plazo_Dgp.html");
            }
            if (opc.equals("Registrar")) {
                String NO_PLAZO = request.getParameter("nombre_plazo");
                String DET_ALERTA = request.getParameter("descripcion");
                String FE_DESDE = request.getParameter("desde");
                String FE_HASTA = request.getParameter("hasta");
                String ID_REQUERIMIENTO = request.getParameter("id_req");
                String TI_PLAZO = request.getParameter("tipo");
                int CA_DIAS_TOLERANCIA = Integer.parseInt(request.getParameter("tolerancia"));
                String ID_DEPARTAMENTO_TOLERANCIA = request.getParameter("dep_tolerancia");
                String DEP = "0";
                String AREA = "0";
                if (request.getParameter("departamento") != null) {
                    if (!request.getParameter("departamento").equals("")) {
                        DEP = request.getParameter("departamento");
                    } else {
                        DEP = "0";
                    }
                }

                if (request.getParameter("area") != null) {
                    if (!request.getParameter("area").equals("")) {
                        AREA = request.getParameter("area");
                    } else {
                        AREA = "0";
                    }
                }
                pl.INSERT_PLAZO(null, NO_PLAZO, DET_ALERTA, FE_DESDE, FE_HASTA, "1", ID_REQUERIMIENTO.trim(), TI_PLAZO, CA_DIAS_TOLERANCIA, ID_DEPARTAMENTO_TOLERANCIA, DEP, AREA);
            }
            if (opc.equals("Modificar")) {
                String ID_PLAZO = request.getParameter("ID");
                String NO_PLAZO = request.getParameter("nombre_plazo");
                String DET_ALERTA = request.getParameter("descripcion");
                String FE_DESDE = request.getParameter("desde");
                String FE_HASTA = request.getParameter("hasta");
                String ES_PLAZO = "1";
                String ID_REQUERIMIENTO = request.getParameter("id_req");
                String TI_PLAZO = request.getParameter("tipo");
                int CA_DIAS_TOLERANCIA = Integer.parseInt(request.getParameter("tolerancia"));
                String ID_DEPARTAMENTO_TOLERANCIA = request.getParameter("dep_tolerancia");
                String DEP = "0";
                String AREA = "0";
                pl.UPDATE_PLAZO(ID_PLAZO, NO_PLAZO, DET_ALERTA, FE_DESDE, FE_HASTA, ES_PLAZO, ID_REQUERIMIENTO, TI_PLAZO, CA_DIAS_TOLERANCIA, ID_DEPARTAMENTO_TOLERANCIA, DEP, AREA);
            }

            if (opc.equals("Eliminar")) {
                String id = request.getParameter("plz");
                pl.REMOVE_PLAZO(id);
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta,HttpStatus.OK);
    }

}
