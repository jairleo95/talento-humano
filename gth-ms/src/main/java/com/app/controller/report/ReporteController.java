/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.report;

import com.app.persistence.dao.AreaDAO;
import com.app.persistence.dao.Carrera_UniversidadDAO;
import com.app.persistence.dao.DireccionDAO;
import com.app.persistence.dao.PuestoDAO;

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
import com.app.persistence.dao.Datos_Hijo_TrabajadorDAO;
import com.app.persistence.dao.DepartamentoDao;
import com.app.persistence.dao.NacionalidadDAO;
import com.app.persistence.dao.ReporteDAO;
import com.app.persistence.dao.SeccionDAO;
import com.app.persistence.dao.SituacionEducativaDAO;
import com.app.persistence.dao_imp.IAreaDAO;
import com.app.persistence.dao_imp.InterfaceCarrera_UniversidadDAO;
import com.app.persistence.dao_imp.IDatosHijoTrabajador;
import com.app.persistence.dao_imp.IDepartamentoDAO;
import com.app.persistence.dao_imp.IDireccionDAO;
import com.app.persistence.dao_imp.InterfaceNacionalidadDAO;
import com.app.persistence.dao_imp.IPuestoDAO;
import com.app.persistence.dao_imp.InterfaceReporteDAO;
import com.app.persistence.dao_imp.InterfaceSeccionDAO;
import com.app.persistence.dao_imp.InterfaceSituacionEducativaDAO;

/**
 *
 * @author joserodrigo
 */

@RestController
@RequestMapping("reporte")
public class ReporteController {


    InterfaceReporteDAO r = new ReporteDAO();
    IDepartamentoDAO d = new DepartamentoDao();
    IAreaDAO a = new AreaDAO();
    InterfaceSeccionDAO s = new SeccionDAO();
    IPuestoDAO p = new PuestoDAO();
    InterfaceReporteDAO RP = new ReporteDAO();
    InterfaceNacionalidadDAO n = new NacionalidadDAO();
    InterfaceSituacionEducativaDAO se = new SituacionEducativaDAO();
    InterfaceCarrera_UniversidadDAO ca = new Carrera_UniversidadDAO();
    IDatosHijoTrabajador dah = new Datos_Hijo_TrabajadorDAO();
    IDireccionDAO dir = new DireccionDAO();
    IAreaDAO area = new AreaDAO();
    IDepartamentoDAO dep = new DepartamentoDao();
    InterfaceSeccionDAO sec = new SeccionDAO();


    @PostMapping
    public ResponseEntity<?> processRequest(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        try {
            //Reportes
            if (opc.equals("reporte1")) {
                sesion.setAttribute("Reporte_Datos_Generales", r.Reporte_Datos_Generales());
                sesion.setAttribute("List_Departamento_Lima", d.List_Departamento_Lima());
                sesion.setAttribute("List_Area_Lima", a.List_Area_Lima());
                sesion.setAttribute("LISTA_RH_SECCION_LIMA", s.LISTA_RH_SECCION_LIMA());
                sesion.setAttribute("List_Puesto_lima", p.List_Puesto_lima());
                sesion.setAttribute("List_Nacionalidad", n.List_Nacionalidad());
                sesion.setAttribute("List_SituacionEducativa", se.List_SituacionEducativa());
                sesion.setAttribute("List_Carrera", ca.List_Carrera());
                response.sendRedirect("views/Reportes/newjsp.jsp");
            }
            if (opc.equals("reporte_hijo")) {
                String desde = request.getParameter("desde");
                String hasta = request.getParameter("hasta");
                String edad = request.getParameter("edad");
                String nom = request.getParameter("nom");
                String pat = request.getParameter("paterno");
                String mat = request.getParameter("materno");
                String dn = request.getParameter("dni");
                String gen = request.getParameter("genero");
                List<Map<String, ?>> lista = dah.Listar_hijo_filtro(desde, hasta, edad, nom, pat, mat, dn, gen);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("reporte_padre_hijo")) {
                //RANGO DE EDADES
                String desde = request.getParameter("desde");
                String hasta = request.getParameter("hasta");
                String edad = request.getParameter("edad");
                //FILTROS
                String aps = request.getParameter("aps");
                String dep = request.getParameter("departamento");
                String are = request.getParameter("area");
                String sec = request.getParameter("seccion");
                String pue = request.getParameter("puesto");
                String fec = request.getParameter("fecha");
                String nom = request.getParameter("nombre");
                String pat = request.getParameter("paterno");
                String mat = request.getParameter("materno");
                String tip = request.getParameter("tipo");
                String nun = request.getParameter("numero");
                List<Map<String, ?>> lista = RP.listar_padre_hi(desde, hasta, edad, aps, dep, are, sec, pue, nom, pat, mat, nun, tip);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

            if (opc.equals("reporte_cumpleaños")) {
                String mes = request.getParameter("mes");
                String dia = request.getParameter("dia");
                String aps = request.getParameter("aps");
                String dep = request.getParameter("departamento");
                String are = request.getParameter("area");
                String sec = request.getParameter("seccion");
                String pue = request.getParameter("puesto");
                String fec = request.getParameter("fecha");
                String edad = request.getParameter("edad");
                String ape = request.getParameter("paterno");
                String mat = request.getParameter("materno");
                String nom = request.getParameter("nombre");
                String tip = request.getParameter("tipo");
                String num = request.getParameter("num");
                List<Map<String, ?>> lista = dah.Listar_Cumpleaños(mes, dia, aps, dep, are, sec, pue, fec, edad, ape, mat, nom, tip, num);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("reporte_t_navidad")) {
                String mes = request.getParameter("mes");
                List<Map<String, ?>> lista = RP.lirtar_trabajor_Navidad(mes);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("reporte_datos_genereales")) {
                String aps = request.getParameter("aps");
                String dep = request.getParameter("departamento");
                String are = request.getParameter("seccion");
                String sec = request.getParameter("area");
                String puesto = request.getParameter("puesto");
                //String fec = request.getParameter("fecha");
                String edad = request.getParameter("edad");
                String ape = request.getParameter("paterno");
                String mat = request.getParameter("materno");
                String nom = request.getParameter("nombre");
                String num = request.getParameter("num");
                List<Map<String, ?>> lista = r.Reporte_Datos_Gen(aps, dep, are, sec, puesto, edad, ape, mat, nom, num);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

            //viewss
            if (opc.equals("Reporte_padres_madres")) {
                response.sendRedirect("views/Reportes/Reporte_Padres_Madres.jsp");
            }
            if (opc.equals("Reporte_datos")) {
                //String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
                /*sesion.setAttribute("Listar_Direccion", dir.Listar_Direccion());
                 sesion.setAttribute("List_Area_ID", area.List_Area_ID(iddep));*/
                response.sendRedirect("views/Reportes/Reporte_Datos_Generales.jsp");
            }
            if (opc.equals("list_da")) {
                String ps = request.getParameter("ps");
                switch (ps) {
                    case "dir":
                        rpta.put("dir", dir.List_Direccion());
                        break;
                    case "dep":
                        String idd = request.getParameter("iddir");
                        rpta.put("dep", dep.Listar_dep_id(idd));
                        break;
                    case "area":
                        String idde = request.getParameter("iddep");
                        rpta.put("area", area.List_area_id_json(idde));
                        break;
                    case "seccion":
                        String idarea = request.getParameter("idarea");
                        rpta.put("seccion", sec.List_sec_ida(idarea));
                        break;
                    case "puesto":
                        String idseccion = request.getParameter("idseccion");
                        rpta.put("puesto", p.Listar_Puesto_id_es(idseccion));
                        break;
                    case "seducativa":
                        rpta.put("seducativa", se.List_SituacionEducativaM());
                        break;
                }
            }
            if (opc.equals("searchtb")) {
                String direccion = request.getParameter("direccion");
                String departamento = request.getParameter("departamento");
                String area = request.getParameter("area");
                String seccion = request.getParameter("seccion");
                String puesto = request.getParameter("puesto");
                if (direccion.equals("null")) {
                    direccion = "";
                }
                if (departamento.equals("null")) {
                    departamento = "";
                }
                if (area.equals("null")) {
                    area = "";
                }
                if (seccion.equals("null")) {
                    seccion = "";
                }
                if (puesto.equals("null")) {
                    puesto = "";
                }
                rpta.put("respuesta", r.datosTrabajador(direccion, departamento, area, seccion, puesto));
            }
            if (opc.equals("Reporte_Datos_Hijos")) {
                response.sendRedirect("views/Reportes/RTHijo.jsp");
            }

            if (opc.equals("Reporte_Datos_cumpl")) {
                response.sendRedirect("views/Reportes/Reporte_Cumpleanos.jsp");
            }
            if (opc.equals("Reporte_Navidad")) {
                response.sendRedirect("views/Reportes/Reporte_para_navidad.jsp");
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
