/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.user;

import com.app.dao_imp.InterfaceModuloDAO;
import com.app.dao_imp.InterfacePrivilegioDAO;
import com.app.dao_imp.InterfaceRolDAO;
import com.app.model.Privilegio;
import com.app.model.V_Privilegio;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.app.dao.ModuloDAO;
import com.app.dao.PrivilegioDAO;
import com.app.dao.RolDAO;

/**
 *
 * @author joserodrigo
 */
@RestController
public class ModuloController {

    @RequestMapping(value = "/modules", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<V_Privilegio> modules(HttpServletRequest request) {

        HttpSession sesion = request.getSession(true);
        String idrol = (String) sesion.getAttribute("IDROL");
        InterfaceRolDAO Irol = new RolDAO();

        return Irol.LISTAR_MODULOS(idrol);
    }

    @RequestMapping(value = "/modulo", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String module(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();
        InterfaceModuloDAO modu = new ModuloDAO();
        InterfacePrivilegioDAO prv = new PrivilegioDAO();
        try {
            String opc = request.getParameter("opc");
            if (opc.equals("lis_mod")) {
                List<Map<String, ?>> list = modu.List_Modulo();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("lis_req")) {
                List<Map<String, ?>> list = prv.List_Priv();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("lis_pr_mod")) {
                List<Map<String, ?>> list = prv.List_Priv_Mod();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("lis_pr_mod_x_id")) {
                String id_mod = request.getParameter("id_modulo");
                List<Map<String, ?>> list = prv.List_Priv_Mod_x_id(id_mod);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("activar_pri_mod")) {
                String id_priv = request.getParameter("id_pr");
                prv.Activar_Privilegio(id_priv);
                //response.sendRedirect("");

            }
            if (opc.equals("desactivar_pri_mod")) {
                String id_priv = request.getParameter("id_privilegio");
                prv.Desactivar_Privilegio(id_priv);
            }
            if (opc.equals("Registrar")) {
                String id_priv = request.getParameter("id_privi");
                String id_mod = request.getParameter("id_modulo");
               // prv.List_Pri_Id(id_priv);
                String no_link="";
                for (int i = 0; i < prv.List_Pri_Id(id_priv).size(); i++) {
                    Privilegio x = new Privilegio();
                    x = (Privilegio) prv.List_Pri_Id(id_priv).get(i);
                    no_link = x.getNo_link();
                    String di_url = x.getDi_url();
                    String ic_link = x.getIc_link();
                    String es_priv = x.getEs_privilegio();
                    prv.Insert_Privilegio(no_link, di_url, es_priv, ic_link,id_mod);
                }
                 //rpta.put("rpta",no_link);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        Gson gson = new Gson();
        return gson.toJson(rpta);

    }

}
