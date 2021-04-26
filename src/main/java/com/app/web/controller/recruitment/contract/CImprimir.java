/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.contract;

import com.app.dao.ContratoDAO;
import com.app.dao.PlantillaContractualDAO;
import com.app.dao_imp.InterfaceContratoDAO;
import com.app.dao_imp.InterfacePlantillaContractualDAO;
import com.app.factory.FactoryConnectionDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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
@RequestMapping("imprimir")
public class CImprimir {

    InterfaceContratoDAO con = new ContratoDAO();
    InterfacePlantillaContractualDAO pl = new PlantillaContractualDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request){

        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);

        try {
            /* TODO output your page here. You may use following sample code. */
            if (opc.equals("Imprimir")) {
                String[] id_con = request.getParameterValues("Imprimir");
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < id_con.length; i++) {
                    list.add(id_con[i]);
                }
                sesion.setAttribute("lista", list);
                ////response.sendRedirect("views/Contrato/Formato_Plantilla/Impresion_Masiva.html");
                // out.print(list.get(0).toString());
            }
            if (opc.equals("Imprimir1")) {
                String[] id_con = request.getParameterValues("Imprimir");

                List<String> list = new ArrayList<String>();
                List<String> texto = new ArrayList<String>();
                for (int i = 0; i < id_con.length; i++) {
                    String[] alma;
                    String textor="";
                    String imprimir="";
                    list.add(id_con[i]);
                    alma = id_con[i].split("/");
                   // String ubicacion = "/var/lib/tomcat7/webapps/TALENTO_HUMANO/views/Contrato/Formato_Plantilla/Formato/";
                    //String ubicacion = getServletContext().getRealPath(".").substring(0, getServletContext().getRealPath(".").length() - 11)+"web\\views\\Contrato\\Formato_Plantilla\\Formato\\";
                    //String ubicacion=getServletConfig().getServletContext().getRealPath("/")+"views/Contrato/Formato_Plantilla/Formato/";
                    String ubicacion= FactoryConnectionDB.url+"Formato/";
                  
                    
                    String no_arhivo_or = pl.List_pl_con_x_id(alma[1]);
                    FileReader lector = new FileReader(ubicacion + no_arhivo_or.trim());
                    BufferedReader contenido = new BufferedReader(lector);
                    while ((textor = contenido.readLine()) != null) {
                        imprimir =imprimir+ textor;
                    }
                    texto.add(imprimir);
                }
                sesion.setAttribute("lista", list);
                sesion.setAttribute("texto", texto);
                ///response.sendRedirect("views/Contrato/Formato_Plantilla/Impresion_Masiva2.html");
                // out.print(list.get(0).toString());
            }
            if (opc.equals("Listar_contrato")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> list = con.List_contra_x_idcto_json(id);
                // out.print(list.get(0).toString());
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
