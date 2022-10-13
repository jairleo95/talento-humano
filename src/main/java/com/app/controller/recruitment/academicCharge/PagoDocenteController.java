/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.academicCharge;

import com.app.persistence.dao.Carga_AcademicaDAO;
import com.app.persistence.dao.PagoDocenteDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao_imp.InterfaceCarga_AcademicaDAO;
import com.app.persistence.dao_imp.InterfacePagoDocenteDAO;
import com.app.domain.model.PagoDocente;
import com.app.controller.util.CCriptografiar;
import com.app.controller.util.DateFormat;

/**
 *
 * @author ALFA 3
 */

@RestController
@RequestMapping("pago_docente")
public class PagoDocenteController {

    InterfaceCarga_AcademicaDAO c = new Carga_AcademicaDAO();
    InterfacePagoDocenteDAO pd = new PagoDocenteDAO();

    @PostMapping
    public ResponseEntity<?> processRequest(HttpServletRequest request) {

        String opc = request.getParameter("opc");
        Map<String, Object> rpta = new HashMap<String, Object>();
        try {
            if (opc.equals("Listar_Cuotas")) {
                String feDesde = request.getParameter("fe_desde");
                String feHasta = request.getParameter("fe_hasta");
                String pagoSemanal = request.getParameter("pago_semanal");

                List<Map<String, ?>> lista = null;
                if (feDesde != null & feHasta != null & !feDesde.equals("") & !feHasta.equals("") & pagoSemanal != null & !pagoSemanal.equals("") & !pagoSemanal.equals("0")) {
                    Double ca_pago_semanal = Double.parseDouble(pagoSemanal);
                    feDesde = DateFormat.toFormat3(feDesde);
                    feHasta = DateFormat.toFormat3(feHasta);
                    lista = c.Cuotas_Pago_Docente(DateFormat.toFormat1(feDesde), DateFormat.toFormat1(feHasta), ca_pago_semanal);
                } else {
                    rpta.put("statusListCuotas", "No se recibieron los parametros correctamente");
                }
                rpta.put("lista", lista);
            }
            if (opc.equals("getPagoDocenteHtml")) {
                String idpca = CCriptografiar.Desencriptar(request.getParameter("id"));
                List<PagoDocente> x = pd.getPagoDocenteByIdProcCA(idpca);
                String html = "";
                html += "<div class='row text-center'><div class='col-md-2'>Mes</div><div class='col-md-5'>Fecha Pago Aprox.</div><div class='col-md-5'>Monto</div></div>";

                for (int i = 0; i < x.size(); i++) {
                    html += "<div class='row text-center'>";
                    html += "<div class='form-group'><label class='col-md-2 control-label'>" + (i + 1) + "</label>";

                    html += "<div class='col-md-5'>";
                    html += "<div class='input-group'>";
                    html += "<span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
                    html += "<input type='text' class='form-control' disabled='disabled' value='" + x.get(i).getFePago() + "' />";
                    html += "</div>";
                    html += "</div>";
                    html += "<div class='col-md-5'>";
                    html += "<div class='input-group'>";
                    html += "<span class='input-group-addon'>S/.</span>";
                    html += "<input type='text' class='form-control'  disabled='disabled' value='" + x.get(i).getCaCuota() + "' />";
                    html += "</div>";
                    html += "</div>";
                    html += "</div>";
                    html += "</div>";

                    //   html += "<div class='row'><div class='col-md-2 text-center' ><label class=''>"
                    //      + (i + 1) + "</label></div><div class='col-md-10'><input type='text' class='form-control' readonly='' value='" + x.get(i).getCaCuota() + "' /></div></div>";
                }
                rpta.put("html", html);
                rpta.put("status", true);

            }
            rpta.put("status", true);
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("status", false);
            rpta.put("message", e.getMessage());
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }



}
