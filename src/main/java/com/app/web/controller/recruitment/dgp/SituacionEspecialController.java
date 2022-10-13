/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.dgp;

import com.app.dao_imp.InterfaceSituacionEspecialDAO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.SituacionEspecialDAO;

/**
 *
 * @author JAIR
 */

@RestController
@RequestMapping("SituacionEspecial")
public class SituacionEspecialController {

    InterfaceSituacionEspecialDAO s = new SituacionEspecialDAO();

    @PostMapping
    protected ResponseEntity<?> processRequest(HttpServletRequest request) {

            Map<String, Object> rpta = new HashMap<>();
            HttpSession sesion = request.getSession(true);
            String user = (String) sesion.getAttribute("IDUSER");
            String opc = request.getParameter("opc");

            if (user != null) {
                try {
                    if (opc.equals("list")) {
                        rpta.put("lista", s.list());
                        rpta.put("status", true);
                    }

                } catch (Exception e) {
                    rpta.put("rpta", false);
                    System.out.println("1ER - ERROR " + Arrays.toString(e.getStackTrace()));
                    System.out.println("2DO - ERROR " + e.getMessage());
                    rpta.put("mensaje", e.getMessage());
                }

            }
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

}
