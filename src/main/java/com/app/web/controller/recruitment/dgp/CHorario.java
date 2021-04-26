/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.dgp;

import com.app.dao.HorarioDAO;
import com.app.dao.ListaDAO;
import com.app.dao_imp.InterfaceHorarioDAO;
import com.app.dao_imp.InterfaceListaDAO;
import com.app.model.V_Horario;

import java.util.ArrayList;
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

/**
 *
 * @author Admin
 */

@RestController
@RequestMapping("horario")
public class CHorario {


    InterfaceHorarioDAO IHor = new HorarioDAO();
    InterfaceListaDAO Ilis = new ListaDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();

        HttpSession sesion = request.getSession();

        String iduser = (String) sesion.getAttribute("IDUSER");

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
                ID_DETALLE_HORARIO = IHor.Insert_Detalle_Horario(ID_DETALLE_HORARIO, ID_DGP, ES_DETALLE_HORARIO, iduser, null, null, null, ID_TIPO_HORARIO, ES_MOD_FORMATO, Ca_ho_total);
                for (int i = 0; i < dia.size(); i++) {
                    for (int j = 0; j < 10; j++) {
                        String hora_desde = request.getParameter("HORA_DESDE_" + dia.get(i) + j);
                        String hora_hasta = request.getParameter("HORA_HASTA_" + dia.get(i) + j);
                        String d = request.getParameter("DIA_" + dia.get(i) + j);
                        if (hora_desde != null & d != null & hora_hasta != null) {
                            if (!hora_hasta.equals("") & !hora_desde.equals("") & !d.equals("")) {
                                IHor.Insert_Horario(null, hora_desde, hora_hasta, d, ES_HORARIO, ID_DETALLE_HORARIO);
                            }
                        }
                    }

                }

                sesion.setAttribute("List_V_Horario", IHor.List_V_Horario(ID_DGP));
                sesion.setAttribute("List_H", Ilis.List_H());
//out.print(ID_DGP);
                ///response.sendRedirect("views/Dgp/Horario/Detalle_Horario.html?iddgp=" + ID_DGP + "&idtr=" + ID_TRABAJJADOR + "&P2=1");
            }

            if (opc.equals("Listar")) {
                String ID_DGP = request.getParameter("iddgp");
                sesion.setAttribute("List_V_Horario", IHor.List_V_Horario(ID_DGP));
                sesion.setAttribute("List_H", Ilis.List_H());

                ///response.sendRedirect("views/Dgp/Horario/Detalle_Horario.html");

            }
            if (opc.equals("Listar2")) {
                ArrayList<Map<String, ?>> lista = new ArrayList<>();                
                String ID_DGP = request.getParameter("iddgp");
                List<V_Horario> lv = IHor.List_V_Horario(ID_DGP);
                for (int i = 0; i < lv.size(); i++) {
                    Map<String, Object> a = new HashMap<>();
                    a.put("dia_horario", lv.get(i).getDia_horario());
                    a.put("ho_desde", lv.get(i).getHo_desde());
                    a.put("ho_hasta", lv.get(i).getHo_hasta());
                    a.put("id_detalle_horario", lv.get(i).getId_detalle_horario());
                    a.put("id_dgp", lv.get(i).getId_dgp());
                    a.put("id_horario", lv.get(i).getId_horario());
                    a.put("no_horario", lv.get(i).getNo_ti_horario());
                    lista.add(a);
                }                
                rpta.put("listar", lista);
            }
            if (opc.equals("listaHorario")) {

            }
        } catch (NumberFormatException e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

}
