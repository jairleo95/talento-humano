/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.report;

import com.app.dao_imp.InterfaceReporteDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.ReporteDAO;

/**
 *
 * @author Alexander
 */

@RestController
@RequestMapping("CReporte_Hijo")
public class ReporteHijoController {

    InterfaceReporteDAO rep= new ReporteDAO();

    @PostMapping
    public ResponseEntity<?> processRequest(HttpServletRequest request) {

        Map<String, Object> rpta= new HashMap<>();
        String opc =request.getParameter("opc");
        List<Map<String,?>> lista;
        try {
            if (opc.equals("reporte_hijos")) {
                String sql="SELECT * FROM RHVD_REPORTE_HIJOS";
                String nombre=request.getParameter("nombre");
                String paterno=request.getParameter("paterno");
                String materno=request.getParameter("materno");
                String genero=request.getParameter("genero");
                String edad=request.getParameter("edad");
                String desde=request.getParameter("desde");
                String hasta=request.getParameter("hasta");
                String dni=request.getParameter("dni");
                nombre=nombre+"";
                paterno= paterno+"";
                materno=materno+"";
                genero=genero+"";
                edad=edad+"";
                desde=desde+"";
                hasta=hasta+"";
                dni=dni+"";
                int cont=0;
                if (!nombre.equals("")) cont++;
                if (!paterno.equals("")) cont++;
                if (!materno.equals("")) cont++;
                if (!edad.equals("")) cont++;
                if (!genero.equals("")) cont++;
                if (!desde.equals("")&&!hasta.equals("")) cont++;
                if (!dni.equals("")) cont++;
                if(cont>0)sql+=" WHERE";
                if (!nombre.equals("")) {
                    sql+=" UPPER(NO_HIJO_TRABAJADOR) LIKE UPPER('%"+nombre+"%')";
                }
                if (!paterno.equals("")) {
                    if((sql.length()-sql.indexOf("WHERE"))>5)sql+=" AND";
                    sql+=" UPPER(AP_PATERNO_HIJO) LIKE UPPER('%"+paterno+"%')";
                }
                if (!materno.equals("")) {
                    if((sql.length()-sql.indexOf("WHERE"))>5)sql+=" AND";
                    sql+=" UPPER(AP_MATERNO_HIJO) LIKE UPPER('%"+materno+"%')";
                }
                if (!genero.equals("")) {
                    if((sql.length()-sql.indexOf("WHERE"))>5)sql+=" AND";
                    sql+=" UPPER(ES_SEXO) LIKE UPPER('%"+genero+"%')";
                }
                if (!dni.equals("")) {
                    if((sql.length()-sql.indexOf("WHERE"))>5)sql+=" AND";
                    sql+=" NU_DOC_HIJO ='"+dni+"'";
                }
                if (!edad.equals("")) {
                    if((sql.length()-sql.indexOf("WHERE"))>5)sql+=" AND";
                    sql+=" EDAD_HIJO="+edad;
                }
                if (!desde.equals("") && !hasta.equals("")) {
                    if((sql.length()-sql.indexOf("WHERE"))>5)sql+=" AND";
                    sql+=" (FE_NACIMIENTO_HIJO>=TO_DATE('"+desde+"','yyyy-mm-dd') AND FE_NACIMIENTO_HIJO<=TO_DATE('"+hasta+"','yyyy-mm-dd'))";
                }
                sql+=" ORDER BY AP_PATERNO";
                System.out.println(sql);
                lista= rep.reporte_hijos(sql);
                rpta.put("lista", lista);
            }
            
        } catch (Exception e) {
            System.out.println(e);
            rpta.put("err", "Error al Procesar los Datos, intentelo nuevamente"+ e);
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
        
    }

}
