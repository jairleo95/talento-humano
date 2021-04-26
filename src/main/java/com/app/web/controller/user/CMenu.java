/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.app.dao_imp.InterfaceRolDAO;
import com.app.model.V_Privilegio;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.app.dao.RolDAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ALFA 3
 */

@RestController
@RequestMapping("/menu")
public class CMenu {

    @GetMapping
    public ModelAndView menu(HttpServletRequest request) {
            InterfaceRolDAO Irol = new RolDAO();
            String opc = request.getParameter("opc");
            //System.out.println("opc:"+opc);

            HttpSession sesion = request.getSession(true);
            String IDUSER = (String) sesion.getAttribute("IDUSER");
            String user = (String) sesion.getAttribute("USER");
            String idrol = (String) sesion.getAttribute("IDROL");

            if (IDUSER != null) {

                if (opc == null && !idrol.trim().equals("ROL-0013")) {
                    //System.out.println("rol:"+idrol);
                    return new ModelAndView("/views/Modulos.html");

                } else if (opc != null & user != null) {
                    if (opc.equals("logout")) {
                        sesion.invalidate();
                        return new ModelAndView("/views/index.html");

                    } if (opc.equals("List_Privilegios")) {
                        System.out.println("Enter to List_Privilegios");
                        String module = request.getParameter("idmod");
                        sesion.setAttribute("moduleSelected", module);
                        //sesion.setAttribute("listarURL", Irol.listarURL(idrol, id_modulo));
                        return new ModelAndView("/views/Principal.html");

                    }

                } else if (idrol.trim().equals("ROL-0013")) {
                    String id_modulo = "MOD-0001";
                    sesion.setAttribute("listarURL", Irol.listarURL(idrol, id_modulo));
                    return new ModelAndView("/views/Principal.html");

                } else {
                    return new ModelAndView("/views/index.html");

                }
            } else {
                return new ModelAndView("/views/index.html");

            }
        return new ModelAndView("/views/index.html");
    }

    @RequestMapping(value = "/privileges", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Collection<V_Privilegio> privileges(HttpServletRequest request){
        HttpSession sesion = request.getSession(true);
        InterfaceRolDAO Irol = new RolDAO();

        String idrol = (String) sesion.getAttribute("IDROL");
        String module = (String) sesion.getAttribute("moduleSelected");
        System.out.println("module selected:"+module);

        return Irol.listarURL(idrol, module);
    }

    @RequestMapping(value = "/elements", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> elements(HttpServletRequest request){
        HttpSession sesion = request.getSession(true);
        Map<String, Object> data = new HashMap<>();
        String breadcrumb = "";
          if (sesion.getAttribute("DEPARTAMENTO") != null) {
                    breadcrumb +="<li>" + "Departamento de  " + sesion.getAttribute("DEPARTAMENTO") + " / Area de "+ sesion.getAttribute("AREA")+ " / Secci�n de "+  sesion.getAttribute("SECCION") + " / Puesto de " +  sesion.getAttribute("PUESTO") +"</li>";
                        }
        data.put("breadcrumb", breadcrumb);
        data.put("spanuser",  sesion.getAttribute("USER"));
        data.put("fullname",  sesion.getAttribute("NOMBRE_AP"));


        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
