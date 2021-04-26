/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.web.controller.user;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.application.dao.ValidarFotoDAO;
import pe.edu.upeu.application.dao_imp.InterfaceValidarFoto;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("validar_foto")
public class CValidar_Foto {

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {


        String opc = request.getParameter("opc");
        String idtr = request.getParameter("idtr");
        String estado = request.getParameter("estado");
        String bus = request.getParameter("bus");
        String btipo = request.getParameter("btipo");
        Map<String, Object> rpta = new HashMap<String, Object>();
        InterfaceValidarFoto x = new ValidarFotoDAO();
        
        if(opc.equals("Index")){
            ///response.sendRedirect("views/Reportes/Validar_Foto/Index.html");
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
