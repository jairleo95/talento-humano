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
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.application.dao.RolDAO;
import pe.edu.upeu.application.dao_imp.InterfaceRolDAO;

/**
 *
 * @author joserodrigo
 */
@RestController
@RequestMapping("Roles")
public class CRoles {

    InterfaceRolDAO rol = new RolDAO();


    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request){
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String iduser = (String) sesion.getAttribute("IDUSER");

        try {
            if (opc.equals("mat_rol")) {
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Roles.jsp");
            }
            if (opc.equals("Listar_Rol")) {
                List<Map<String, ?>> list = rol.List_rol();
                rpta.put("rpta", "1");
                rpta.put("data", list);
            }
            if (opc.equals("Modificar_Rol")) {
                String idrol = request.getParameter("idrol");
                sesion.setAttribute("Listar_Rol_id", rol.Listar_Rol_id(idrol));
                sesion.setAttribute("List_Rol", rol.List_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Mod_Rol.jsp");
            }
            if (opc.equals("Modificar")) {
                String idrol = request.getParameter("id_rol");
                String no_rol = request.getParameter("Nombre_Rol");
                String Es_rol = request.getParameter("Es_rol");
                rol.Mod_Rol(idrol, no_rol, Es_rol);
                sesion.setAttribute("List_Rol", rol.List_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Roles.jsp");
            }
            if (opc.equals("Activar_Rol")) {
                String idrol = request.getParameter("idrol");
                rol.Activar_Roles(idrol);
                sesion.setAttribute("List_Rol", rol.List_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Roles.jsp");
            }
            if (opc.equals("Desactivar_Rol")) {
                String idrol = request.getParameter("idrol");
                rol.Desactivar_Roles(idrol);
                sesion.setAttribute("List_Rol", rol.List_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Roles.jsp");
            }
            if (opc.equals("Eliminar_Rol")) {
                String idrol = request.getParameter("idrol");
                rol.Desactivar_Roles(idrol);
                sesion.setAttribute("List_Rol", rol.List_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/List_Roles.jsp");
            }
            if (opc.equals("REGISTRAR")) {
                String no_rol = request.getParameter("NOMBRE");
                String ESTADO = request.getParameter("ESTADO");
                if (ESTADO == null) {
                    ESTADO = "0";
                    rol.INSERT_ROLES(no_rol, ESTADO);
                } else {
                    rol.INSERT_ROLES(no_rol, ESTADO);
                }
                ///out.print(request.getParameter("ESTADO"));
                sesion.setAttribute("List_Rol", rol.List_Rol());
                ///response.sendRedirect("views/Usuario/Rol_Privilegio/Reg_Roles.jsp");
            }
            rpta.put("status", true);
        } catch (Exception e) {

            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
            rpta.put("status", false);
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
