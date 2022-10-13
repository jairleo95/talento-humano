/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.user;

import com.app.persistence.dao_imp.InterfaceDetalle_PrivilegioDAO;
import com.app.persistence.dao_imp.InterfacePrivilegioDAO;
import com.app.persistence.dao_imp.InterfaceRolDAO;

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
import com.app.persistence.dao.Detalle_PrivilegioDAO;
import com.app.persistence.dao.PrivilegioDAO;
import com.app.persistence.dao.RolDAO;

/**
 *
 * @author joserodrigo
 */

@RestController
@RequestMapping("Privilegio")

public class PrivilegeController {

    InterfaceRolDAO rol = new RolDAO();
    InterfacePrivilegioDAO priv = new PrivilegioDAO();
    InterfaceDetalle_PrivilegioDAO detp = new Detalle_PrivilegioDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String iduser = (String) sesion.getAttribute("IDUSER");
        Map<String, Object> rpta = new HashMap<String, Object>();

        try {

            if (opc.equals("ListPrivilegio")) {
                List<Map<String, ?>> list = priv.List_Priv_Mod();
                rpta.put("rpta", "1");
                rpta.put("Lista", list);
            }
            if (opc.equals("ListModulo")) {
                List<Map<String, ?>> list = priv.List_Modulo();
                rpta.put("rpta", "1");
                rpta.put("Lista", list);
            }
            if (opc.equals("MenuOpciones")) {
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/TEMPORAL.jsp");
            }

            if (opc.equals("Listar_Rol")) {
                sesion.setAttribute("List_Rol", rol.List_Rol());
                //response.sendRedirect("views/Usuario/Rol_Privilegio/List_Roles.jsp");

            }
            if (opc.equals("Modificar_Rol")) {
                String idrol = request.getParameter("idrol");
                sesion.setAttribute("Listar_Rol_id", rol.Listar_Rol_id(idrol));
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Mod_Rol.jsp");
            }

            /*if(opc.equals("Listar_Privilegio")){
             sesion.setAttribute("List_Privilegio",priv.List_Privilegio());
             response.sendRedirect("views/Usuario/Rol_Privilegio/List_Privilegios.jsp");
             }*/
            if (opc.equals("Listar_Privilegio")) {
                
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegio.jsp");
            }
            if (opc.equals("Desactivar_Priv")) {
                String idrol = request.getParameter("id_priv");
                priv.Desactivar_Privilegio(idrol);
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegio.jsp");

            }
            if (opc.equals("Eliminar_Priv")) {
                String idrol = request.getParameter("id_priv");
                priv.Eliminar_Privilegio(idrol);
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegio.jsp");

            }
            if (opc.equals("Activar_Priv")) {
                String idrol = request.getParameter("id_priv");
                priv.Activar_Privilegio(idrol);
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegio.jsp");

            }
            if (opc.equals("modificar_Priv1")) {
                String idpriv = request.getParameter("id_priv");
                sesion.setAttribute("List_Pri_Id", priv.List_Pri_Id(idpriv));
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Mod_Privilegio.jsp");
            }
            if (opc.equals("modificar_Priv2")) {
                String id_priv = request.getParameter("Id_priv");
                String No_Link = request.getParameter("No_Link");
                String Es_priv = request.getParameter("Es_Privilegio");
                String Di_url = request.getParameter("Di_url");
                String Ic_link = request.getParameter("Ic_link");
                priv.Mod_Priv(id_priv, No_Link, Es_priv, Di_url, Ic_link);
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegio.jsp");
            }
            if (opc.equals("Registrar")) {
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegios.jsp");
            }
            if (opc.equals("REGISTRAR PRIVILEGIO")) {
                String no_link = request.getParameter("No_Link");
                String id_modulo = request.getParameter("modulo");
                String di_url = request.getParameter("Di_url");
                String es_privilegio = request.getParameter("Es_privilegio");
                String Ic_Link = request.getParameter("Ic_Link");
                priv.Insert_Privilegio(no_link, di_url, es_privilegio, Ic_Link, id_modulo);
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Privilegios.jsp");
            }
            if (opc.equals("REGISTRAR_PRIVILEGIO")) {
                String no_link = request.getParameter("No_Link");
                String id_modulo = request.getParameter("modulo");
                String di_url = request.getParameter("Di_url");
                String es_privilegio = request.getParameter("Es_privilegio");
                String Ic_Link = request.getParameter("Ic_Link");
                System.out.println(no_link);
                System.out.println(id_modulo);
                System.out.println(di_url);
                System.out.println(es_privilegio);
                System.out.println(Ic_Link);
                priv.Insert_Privilegio(no_link, di_url, es_privilegio, Ic_Link, id_modulo);
            }
            if (opc.equals("Otorgar")) {
                sesion.setAttribute("List_Rol", rol.List_Rol());
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Otorgar_Privilegio.jsp");
            }
            if (opc.equals("Listar_PR_ROL")) {
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/List_Pri_Roles.jsp");
            }
            if (opc.equals("REGISTRAR PRIVILEGIO DADO")) {
                String Id_rol = request.getParameter("id_rol");
                String id_Priv = request.getParameter("id_privilegio");
                String Nu_Orden = request.getParameter("NRO_ORDEN");
                String Es_detalle_privilegio = request.getParameter("ESTADO");
                ///out.print(Id_rol + " " + id_Priv + " " + Nu_Orden + "" + Es_detalle_privilegio);
                detp.Registrar_Detalle_Priv(Id_rol, Nu_Orden, id_Priv, Es_detalle_privilegio);
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Otorgar_Privilegio.jsp");
            }
            if (opc.equals("Mod_det_pr")) {
                String id_det_pr = request.getParameter("id_det_pr");
                sesion.setAttribute("List_det_pri_id", detp.List_det_pri_id(id_det_pr));
                sesion.setAttribute("List_Privilegio", priv.List_Privilegio());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Mod_Detalle_privilegio.jsp");
            }
            if (opc.equals("Mod_det_pr2")) {
                String id_det_pr = request.getParameter("id_det_pr");
                String id_Priv = request.getParameter("id_Priv");
                int Nu_Orden = Integer.parseInt(request.getParameter("Nu_Orden"));
                String Es_detalle_privilegio = request.getParameter("Es_detalle_privilegio");
                //out.print(id_det_pr + id_Priv + Nu_Orden + Es_detalle_privilegio);
                detp.Mod_Detalle_Priv(id_det_pr, Nu_Orden, id_Priv, Es_detalle_privilegio);
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Otorgar_Privilegio.jsp");
            }
            if (opc.equals("Desactivar_det_pr")) {
                String id_det_pr = request.getParameter("id_det_pr");
                //out.print(id_det_pr);
                detp.Desc_r_pr(id_det_pr);
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Otorgar_Privilegio.jsp");
            }
            if (opc.equals("Activar_det_pr")) {
                String id_det_pr = request.getParameter("id_det_pr");
                //out.print(id_det_pr);
                detp.Act_r_pr(id_det_pr);
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Otorgar_Privilegio.jsp");
            }
            if (opc.equals("Elim_det_pr")) {
                String id_det_pr = request.getParameter("id_det_pr");
                //out.print(id_det_pr);
                detp.Elim_Detalle_Priv(id_det_pr);
                sesion.setAttribute("List_Pr_Rol", priv.List_Pr_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Otorgar_Privilegio.jsp");
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

}
