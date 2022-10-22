/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.user;

import com.app.persistence.dao_imp.InterfaceValidarFoto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao.ValidarFotoDAO;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("validar_foto")
public class PhotoValidationController {

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String opc = request.getParameter("opc");
        String idtr = request.getParameter("idtr");
        String estado = request.getParameter("estado");
        String bus = request.getParameter("bus");
        String btipo = request.getParameter("btipo");
        Map<String, Object> rpta = new HashMap<String, Object>();
        InterfaceValidarFoto x = new ValidarFotoDAO();
        
        if(opc.equals("Index")){
            response.sendRedirect("views/Reportes/Validar_Foto/Index.html");
        }
        if(opc.equals("getFotos")){
            rpta.put("Fotos_NoVal",x.ListFotos(estado));
        }
        if(opc.equals("validar")){
          x.validar(idtr.trim(),estado.trim());
           rpta.put("val","1");
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
