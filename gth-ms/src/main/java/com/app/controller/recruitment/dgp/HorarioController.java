/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.dgp;

import com.app.persistence.dao.HorarioDAO;
import com.app.persistence.dao.ListaDAO;
import com.app.persistence.dao_imp.IHorarioDAO;
import com.app.persistence.dao_imp.IListaDAO;
import com.app.domain.model.V_Horario;

import java.io.IOException;
import java.util.ArrayList;
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

/**
 *
 * @author Admin
 */

@RestController
@RequestMapping("horario")
public class HorarioController {


    IHorarioDAO horarioDAO = new HorarioDAO();
    IListaDAO listaDAO = new ListaDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> rpta = new HashMap<String, Object>();

        HttpSession session = request.getSession();

        String iduser = (String) session.getAttribute("IDUSER");

        List<String> dia = new ArrayList<String>();
        dia.add("lun");
        dia.add("mar");
        dia.add("mie");
        dia.add("jue");
        dia.add("vie");
        dia.add("dom");

        String opc = request.getParameter("opc");

        try {
            if (opc.equals("REGISTRAR HORARIO")) {
                String ID_DETALLE_HORARIO = request.getParameter("ID_DETALLE_HORARIO");
                String ID_DGP = request.getParameter("IDDETALLE_DGP");
                String ES_DETALLE_HORARIO = "1";
                String ES_HORARIO = "1";
                String ID_TRABAJJADOR = request.getParameter("idtr");
                String ID_TIPO_HORARIO = request.getParameter("HORARIO");
                String ES_MOD_FORMATO = "1";
                Double Ca_ho_total = Double.parseDouble(request.getParameter("h_total"));
                ID_DETALLE_HORARIO = horarioDAO.Insert_Detalle_Horario(ID_DETALLE_HORARIO, ID_DGP, ES_DETALLE_HORARIO, iduser, null, null, null, ID_TIPO_HORARIO, ES_MOD_FORMATO, Ca_ho_total);
                for (String s : dia) {
                    for (int j = 0; j < 10; j++) {
                        String hora_desde = request.getParameter("HORA_DESDE_" + s + j);
                        String hora_hasta = request.getParameter("HORA_HASTA_" + s + j);
                        String d = request.getParameter("DIA_" + s + j);
                        if (hora_desde != null & d != null & hora_hasta != null) {
                            if (!hora_hasta.equals("") & !hora_desde.equals("") & !d.equals("")) {
                                horarioDAO.Insert_Horario(null, hora_desde, hora_hasta, d, ES_HORARIO, ID_DETALLE_HORARIO);
                            }
                        }
                    }

                }

                session.setAttribute("List_V_Horario", horarioDAO.List_V_Horario(ID_DGP));
                session.setAttribute("List_H", listaDAO.List_H());
//out.print(ID_DGP);
                response.sendRedirect("views/Dgp/Horario/Detalle_Horario.html?iddgp=" + ID_DGP + "&idtr=" + ID_TRABAJJADOR + "&P2=1");
            }

            if (opc.equals("Listar")) {
                String ID_DGP = request.getParameter("iddgp");
                session.setAttribute("List_V_Horario", horarioDAO.List_V_Horario(ID_DGP));
                session.setAttribute("List_H", listaDAO.List_H());

                response.sendRedirect("views/Dgp/Horario/Detalle_Horario.html");

            }
            if (opc.equals("Listar2")) {
                ArrayList<Map<String, ?>> lista = new ArrayList<>();                
                String ID_DGP = request.getParameter("iddgp");
                List<V_Horario> lv = horarioDAO.List_V_Horario(ID_DGP);
                for (V_Horario vHorario : lv) {
                    Map<String, Object> a = new HashMap<>();
                    a.put("dia_horario", vHorario.getDia_horario());
                    a.put("ho_desde", vHorario.getHo_desde());
                    a.put("ho_hasta", vHorario.getHo_hasta());
                    a.put("id_detalle_horario", vHorario.getId_detalle_horario());
                    a.put("id_dgp", vHorario.getId_dgp());
                    a.put("id_horario", vHorario.getId_horario());
                    a.put("no_horario", vHorario.getNo_ti_horario());
                    lista.add(a);
                }                
                rpta.put("listar", lista);
            }
            if (opc.equals("listaHorario")) {

            }
        } catch (NumberFormatException e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

}
