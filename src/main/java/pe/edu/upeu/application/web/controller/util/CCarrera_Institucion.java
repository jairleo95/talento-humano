/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.web.controller.util;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
import pe.edu.upeu.application.dao.Carrera_UniversidadDAO;
import pe.edu.upeu.application.dao_imp.InterfaceCarrera_UniversidadDAO;

/**
 *
 * @author Alfa.sistemas
 */
@RestController
@RequestMapping("detalle_carrera")
public class CCarrera_Institucion {

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        InterfaceCarrera_UniversidadDAO model = new Carrera_UniversidadDAO();
        String opc = request.getParameter("opc");
        try {
            if (opc.equals("institucion")) {
                String id = request.getParameter("ti");
                List<Map<String, ?>> lista = model.Istitucion(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("ti_inst")) {
                String reg = request.getParameter("regimen");

                List<Map<String, ?>> lista = model.Tipo_Institucion(reg);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("carrera")) {
                String reg = request.getParameter("inst");

                List<Map<String, ?>> lista = model.Carrera_Id_universidad(reg);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


}
