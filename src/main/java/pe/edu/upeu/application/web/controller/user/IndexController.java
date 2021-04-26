/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.web.controller.user;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.application.dao.RolDAO;
import pe.edu.upeu.application.dao.UsuarioDAO;
import pe.edu.upeu.application.dao_imp.InterfaceRolDAO;
import pe.edu.upeu.application.dao_imp.InterfaceUsuarioDAO;
import pe.edu.upeu.application.model.Usuario;
import pe.edu.upeu.application.model.V_Usuario;

@RestController
public class IndexController {

    InterfaceUsuarioDAO us = new UsuarioDAO();
    //V_Usuario user = new V_Usuario();

    @RequestMapping(value = "valida", method = {RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String authAccess(HttpServletRequest request,@RequestBody Usuario x) {

        List<V_Usuario> u = us.Val_Usuario(x.getNo_usuario(),x.getPw_usuario());
        Map<String, Object> rpta = new HashMap<String, Object>();
        try {
            InterfaceRolDAO Irol = new RolDAO();
            if (x.getNo_usuario().equals("") && x.getPw_usuario().equals("")) {
                rpta.put("rpta", false);
            } else if (u.size() == 1) {

                V_Usuario user = new V_Usuario();
                user = (V_Usuario) u.get(0);
                HttpSession sesion = request.getSession(true);

                sesion.setAttribute("IDUSER", user.getId_usuario());
                sesion.setAttribute("IDTR", user.getId_trabajador());
                sesion.setAttribute("FECHA_MINIMA", us.fecha_actual());
                sesion.setAttribute("USER", user.getNo_usuario());
                sesion.setAttribute("IDPER", user.getId_empleado());
                sesion.setAttribute("IDROL", user.getId_rol().trim());
                sesion.setAttribute("CL", user.getPw_usuario());
                sesion.setAttribute("PUESTO_ID", user.getId_puesto());
                sesion.setAttribute("AREA_ID", user.getId_area());
                sesion.setAttribute("AREA", user.getNo_area());
                sesion.setAttribute("DEPARTAMENTO", user.getNo_dep());
                sesion.setAttribute("SECCION", user.getNo_seccion());
                sesion.setAttribute("DEPARTAMENTO_ID", user.getId_departamento());
                sesion.setAttribute("PUESTO", user.getNo_puesto());
                sesion.setAttribute("AR_FOTO", user.getAr_foto());
                sesion.setAttribute("IDDIR", user.getId_direccion());

                if (user.getAp_paterno() == null) {
                    sesion.setAttribute("NOMBRE_AP", "");
                } else {
                    sesion.setAttribute("NOMBRE_AP", user.getAp_paterno() + " " + user.getAp_materno() + " " + user.getNo_trabajador());
                }
                sesion.setAttribute("LIST_MODULO", Irol.LISTAR_MODULOS(user.getId_rol()));


                /*FALTA VALIDAR ESTOS DOS PROCEDIMIENTOS*/
                //   pl.Validar_Cumplimiento_plazo_t2();
                // pl.Validar_Cumplimiento_plazo_t1();
                //esta funcion valida los procesos de dgp se terminen cuando esten en el ultimo paso(SE PUSO EN UN JOB de cada 60 minutos)
                //  dgp.VAL_DGP_PASOS();
                // cto.VALIDAR_FE_CESE_CON();
                //validacion es lo mismo que Val_Es_Plazo();
                //pl.validar_Vig_plazos();
                //it's ok
                /*este procedimiento se puso en un job cada un dia de intervalo*/
                //pl.Val_Es_Plazo();

                /*este procedmiento se puso en un job de cada un dia de intervalo*/
                //  cto.VALIDAR_FE_HASTA_CON();
                //response.sendRedirect("menu");
                System.out.println(user.toString());
                rpta.put("rpta", true);

            } else {
                rpta.put("rpta", false);
                rpta.put("message", "Usuario o clave incorrecta.");
            }
            rpta.put("status", true);
        } catch (Exception ex) {
            rpta.put("status", false);
            rpta.put("message", ex.getMessage());
        } finally {
            Gson gson = new Gson();
            return gson.toJson(rpta);

        }

    }


}
