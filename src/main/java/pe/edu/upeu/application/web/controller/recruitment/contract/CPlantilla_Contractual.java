/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.web.controller.recruitment.contract;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.application.dao.ContratoDAO;
import pe.edu.upeu.application.dao.FuncionDAO;
import pe.edu.upeu.application.dao.ListaDAO;
import pe.edu.upeu.application.dao.PlantillaContractualDAO;
import pe.edu.upeu.application.dao.PuestoDAO;
import pe.edu.upeu.application.dao.TrabajadorDAO;
import pe.edu.upeu.application.dao_imp.InterfaceContratoDAO;
import pe.edu.upeu.application.dao_imp.InterfaceFuncionDAO;
import pe.edu.upeu.application.dao_imp.InterfaceListaDAO;
import pe.edu.upeu.application.dao_imp.InterfacePlantillaContractualDAO;
import pe.edu.upeu.application.dao_imp.InterfacePuestoDAO;
import pe.edu.upeu.application.dao_imp.InterfaceTrabajadorDAO;

/**
 *
 * @author joserodrigo
 */
@RestController
@RequestMapping("plantilla_contractual")
public class CPlantilla_Contractual {


    InterfacePuestoDAO pu = new PuestoDAO();
    InterfaceTrabajadorDAO tr = new TrabajadorDAO();
    InterfaceListaDAO l = new ListaDAO();
    InterfacePlantillaContractualDAO pl = new PlantillaContractualDAO();
    InterfaceContratoDAO con = new ContratoDAO();
    InterfaceFuncionDAO fu = new FuncionDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        String opc = request.getParameter("opc");

        Map<String, Object> rpta = new HashMap<String, Object>();
        HttpSession session = request.getSession(true);
        try {
            if (opc.equals("List_planti")) {
                String idpu = request.getParameter("id_pu");
                List<Map<String, ?>> list = pl.List_PLantillas(idpu);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listpuesto")) {
                List<Map<String, ?>> list = pu.List_puesto();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("cargar")) {
                String no_arch = request.getParameter("Imprimir");
                String id_con = request.getParameter("id_con");
                session.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(id_con));
                ///response.sendRedirect("views/Contrato/Formato_Plantilla/reg_formato.html?no_arc=" + no_arch);
            }
            if (opc.equals("Imprimir")) {
                String id_planti_con = request.getParameter("id_plan_contr");
                String no_arch = pl.List_pl_con_x_id(id_planti_con);
                String id_con = request.getParameter("id_con");
                String id_puesto = pu.puesto(id_con);
                //out.print(no_arch);
                session.setAttribute("List_x_fun_x_idpu", fu.List_x_fun_x_idpu(id_puesto));
                session.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(id_con));
                session.setAttribute("List_Dom_D1_Id", l.List_Dom_D1_Id());
                session.setAttribute("List_Dom_D5_Id", l.List_Dom_D5_Id());
                ///response.sendRedirect("views/Contrato/Formato_Plantilla/reg_formato.html?no_arc=" + no_arch);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
//        Gson gson = new Gson();
//        out.println(gson.toJson(rpta));
//        out.flush();
//        out.close();
    }

}
