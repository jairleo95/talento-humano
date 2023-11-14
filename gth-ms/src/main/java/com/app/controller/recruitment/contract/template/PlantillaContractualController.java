/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.contract.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao.ContratoDAO;
import com.app.persistence.dao.FuncionDAO;
import com.app.persistence.dao.ListaDAO;
import com.app.persistence.dao.PlantillaContractualDAO;
import com.app.persistence.dao.PuestoDAO;
import com.app.persistence.dao_imp.IContratoDAO;
import com.app.persistence.dao_imp.IFuncionDAO;
import com.app.persistence.dao_imp.IListaDAO;
import com.app.persistence.dao_imp.IContractTemplateDAO;
import com.app.persistence.dao_imp.IPuestoDAO;

/**
 *
 * @author joserodrigo
 */
@RestController
@RequestMapping("plantilla_contractual")
public class PlantillaContractualController {


    IPuestoDAO puestoDAO = new PuestoDAO();
    IListaDAO listaDAO = new ListaDAO();
    IContractTemplateDAO plantillaContractualDAO = new PlantillaContractualDAO();
    IContratoDAO contratoDAO = new ContratoDAO();
    IFuncionDAO funcionDAO = new FuncionDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request, HttpServletResponse response) {

        String opc = request.getParameter("opc");

        Map<String, Object> rpta = new HashMap<String, Object>();
        HttpSession session = request.getSession(true);
        try {
            if (opc.equals("List_planti")) {
                String idpu = request.getParameter("id_pu");
                List<Map<String, ?>> list = plantillaContractualDAO.List_PLantillas(idpu);
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("Listpuesto")) {
                List<Map<String, ?>> list = puestoDAO.List_puesto();
                rpta.put("rpta", "1");
                rpta.put("lista", list);
            }
            if (opc.equals("cargar")) {
                String no_arch = request.getParameter("Imprimir");
                String id_con = request.getParameter("id_con");
                session.setAttribute("List_contra_x_idcto", contratoDAO.List_contra_x_idcto(id_con));
                response.sendRedirect("views/Contrato/Formato_Plantilla/reg_formato.html?no_arc=" + no_arch);
            }
            if (opc.equals("Imprimir")) {
                String id_planti_con = request.getParameter("id_plan_contr");
                String no_arch = plantillaContractualDAO.List_pl_con_x_id(id_planti_con);
                String id_con = request.getParameter("id_con");
                String id_puesto = puestoDAO.puesto(id_con);
                //out.print(no_arch);
                session.setAttribute("List_x_fun_x_idpu", funcionDAO.List_x_fun_x_idpu(id_puesto));
                session.setAttribute("List_contra_x_idcto", contratoDAO.List_contra_x_idcto(id_con));
                session.setAttribute("List_Dom_D1_Id", listaDAO.List_Dom_D1_Id());
                session.setAttribute("List_Dom_D5_Id", listaDAO.List_Dom_D5_Id());
                response.sendRedirect("views/Contrato/Formato_Plantilla/reg_formato.html?no_arc=" + no_arch);
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

}
