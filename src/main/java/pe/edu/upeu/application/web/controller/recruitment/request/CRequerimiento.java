/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.web.controller.recruitment.request;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pe.edu.upeu.application.dao.DgpDAO;
import pe.edu.upeu.application.dao.RequerimientoDAO;
import pe.edu.upeu.application.dao_imp.InterfaceDgpDAO;
import pe.edu.upeu.application.dao_imp.InterfaceRequerimientoDAO;

/**
 *
 * @author Admin
 */
@RestController
public class CRequerimiento {


    InterfaceRequerimientoDAO req = new RequerimientoDAO();
    InterfaceDgpDAO Idgp = new DgpDAO();

    @GetMapping("requerimiento")
    public ResponseEntity<?> processRequest(HttpServletRequest request) {

         Map<String, Object> rpta = new HashMap<String, Object>();

        HttpSession sesion = request.getSession(true);
        String opc = request.getParameter("opc");
        try {
            if (opc.equals("Listar")) {
                String iddep = (String) sesion.getAttribute("IDDEPARTAMENTO");

                sesion.setAttribute("List_Det_Dgp", Idgp.LIST_DET_DGP(iddep));
                // out.print(Idgp.LIST_DET_DGP(iddep).size());
                //return new ModelAndView("/views/Dgp/List_Dgp.html?iddep");
            }

            if (opc.equals("Listar_tp")) {
                List<Map<String, ?>> lista = req.List_planilla();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Listar_req_id")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista = req.List_Req_tipo_planilla(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Listar_id_req")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista = req.List_requerimiento(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Listar_Tipo_Planilla")) {
                //String id = request.getParameter("id");
                List<Map<String, ?>> lista = req.List_planilla();
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
