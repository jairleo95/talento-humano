/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.dgp;

import com.app.dao.DireccionDAO;
import com.app.dao_imp.InterfaceDepartamentoDAO;
import com.app.dao_imp.InterfaceDireccionDAO;
import com.app.dao_imp.InterfaceFormato_HorarioDAO;

import java.util.HashMap;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.DepartamentoDao;
import com.app.dao.Formato_HorarioDAO;

/**
 *
 * @author Alex
 */

@RestController
@RequestMapping("schedule")//formato_horario
public class CFormato_Horario {

    InterfaceFormato_HorarioDAO Ifh = new Formato_HorarioDAO();
    InterfaceDepartamentoDAO dp = new DepartamentoDao();
    InterfaceDireccionDAO di = new DireccionDAO();
    ///InterfaceCarrera_UniversidadDAO model = new Carrera_UniversidadDAO();


    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        HttpSession sesion = request.getSession(true);

        String opc = request.getParameter("opc");
        Map<String, Object> rpta = new HashMap<String, Object>();

        List<String> dia = new ArrayList<String>();
        dia.add("lun");
        dia.add("mar");
        dia.add("mie");
        dia.add("jue");
        dia.add("vie");
        dia.add("sab");
        dia.add("dom");
        try {

            if (opc.equals("registrar")) {
                String ID_TIPO_HORARIO = null;
                String NO_HORARIO = request.getParameter("NO_HORARIO");
                String DE_HORARIO = request.getParameter("DE_HORARIO");
                String ES_HORARIO = request.getParameter("ES_HORARIO");
                Double CA_HORAS = Double.parseDouble(request.getParameter("CA_HORAS"));

                Ifh.Insert_Horario(ID_TIPO_HORARIO, NO_HORARIO, DE_HORARIO, ES_HORARIO, CA_HORAS, null, null, null);
                sesion.setAttribute("List_Tipo_Horario", Ifh.Listar_Tipo_Horario());
                //response.sendRedirect("views/Formato_Horario/Detalle_Formato_Horario.html");

            }
            if (opc.equals("GuardarFH")) {
                String NO_HORARIO = request.getParameter("NO_HORARIO");
                String DE_HORARIO = request.getParameter("DE_HORARIO");
                String ES_HORARIO = "1";
                String ID_DEP = sesion.getAttribute("DEPARTAMENTO_ID").toString();
                Double CA_HORAS = Double.parseDouble(request.getParameter("CA_HORAS"));
                String id_ar = request.getParameter("id_ar");
                String id_sec = request.getParameter("id_sec");

                Ifh.Insert_Horario(null, NO_HORARIO, DE_HORARIO, ES_HORARIO, CA_HORAS, ID_DEP, id_ar, id_sec);
            }
            if (opc.equals("GuardarFHAdmin")) {
                String NO_HORARIO = request.getParameter("NO_HORARIO");
                String DE_HORARIO = request.getParameter("DE_HORARIO");
                String ES_HORARIO = "1";
                String ID_DEP = request.getParameter("ID_DEPARTAMENTO");
                Double CA_HORAS = 0.0;
                try {
                    CA_HORAS = Double.parseDouble(request.getParameter("CA_HORAS"));
                } catch (Exception e) {
                    CA_HORAS = 0.0;
                }
                String id_ar = request.getParameter("id_ar");
                String id_sec = request.getParameter("id_sec");
                Ifh.Insert_Horario(null, NO_HORARIO, DE_HORARIO, ES_HORARIO, CA_HORAS, ID_DEP, id_ar, id_sec);
            }

            if (opc.equals("Listar_Formato")) {
                sesion.setAttribute("List_Tipo_Horario", Ifh.Listar_Tipo_Horario());
                sesion.setAttribute("Listar_Direccion", di.Listar_Direccion());
                ///response.sendRedirect("views/Formato_Horario/Detalle_Formato_Horario.html");
            }
            if (opc.equals("LFH")) {
                List<Map<String, ?>> lista = Ifh.List_Tipo_Horarios();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("ultimo_fh")) {
                String id = Ifh.ultimo_Tipo_Horario();
                rpta.put("rpta", "1");
                rpta.put("lista", id);
            }
            if (opc.equals("editar_fh")) {
                String ID_HORARIO = request.getParameter("ID_HORARIO");
                String NO_HORARIO = request.getParameter("NO_HORARIO");
                String DE_HORARIO = request.getParameter("DE_HORARIO");
                String ES_HORARIO = "1";
                String ID_DEP = request.getParameter("ID_DEPARTAMENTO");
                Double CA_HORAS = 0.0;
                try {
                    CA_HORAS = Double.parseDouble(request.getParameter("CA_HORAS"));
                } catch (Exception e) {
                    CA_HORAS = 0.0;
                }
                String id_ar = request.getParameter("id_ar");
                String id_sec = request.getParameter("id_sec");
                Ifh.Editar_FH(ID_HORARIO, NO_HORARIO, DE_HORARIO, ES_HORARIO, CA_HORAS, ID_DEP, id_ar, id_sec);
                Ifh.Eliminar_formato_horario(ID_HORARIO);
                String ID_FORMATO_HORARIO = null;
                String ES_F_HORARIO = "1";

                for (int i = 0; i < dia.size(); i++) {
                    for (int j = 0; j < 10; j++) {
                        String HO_DESDE = request.getParameter("HORA_DESDE_" + dia.get(i) + j);
                        HO_DESDE = parser24(HO_DESDE);
                        String HO_HASTA = request.getParameter("HORA_HASTA_" + dia.get(i) + j);
                        HO_HASTA = parser24(HO_HASTA);
                        String NO_DIA = request.getParameter("DIA_" + dia.get(i) + j);
                        if (HO_DESDE != null & NO_DIA != null & HO_HASTA != null) {
                            if (!HO_HASTA.equals("") & !HO_DESDE.equals("") & !NO_DIA.equals("")) {
                                Ifh.Insert_Formato_Horario(ID_FORMATO_HORARIO, "T" + j, NO_DIA, HO_DESDE, HO_HASTA, ES_F_HORARIO, ID_HORARIO);
                            }
                        }
                    }
                }
                
            }
            if (opc.equals("eliminar_fh")) {
                String ID_HORARIO = request.getParameter("ID_HORARIO");
                System.out.println(ID_HORARIO);
                Ifh.Eliminar_formato_horario(ID_HORARIO);
                Ifh.Eliminar_horario(ID_HORARIO);
            }
            if (opc.equals("REGISTRAR_FORMATOS")) {
                String ID_FORMATO_HORARIO = null;
                String ID_TIPO_HORARIO = Ifh.ultimo_Tipo_Horario();
                String ES_F_HORARIO = "1";

                for (int i = 0; i < dia.size(); i++) {
                    for (int j = 0; j < 10; j++) {
                        String HO_DESDE = request.getParameter("HORA_DESDE_" + dia.get(i) + j);
                        HO_DESDE = parser24(HO_DESDE);
                        String HO_HASTA = request.getParameter("HORA_HASTA_" + dia.get(i) + j);
                        HO_HASTA = parser24(HO_HASTA);
                        String NO_DIA = request.getParameter("DIA_" + dia.get(i) + j);
                        if (HO_DESDE != null & NO_DIA != null & HO_HASTA != null) {
                            if (!HO_HASTA.equals("") & !HO_DESDE.equals("") & !NO_DIA.equals("")) {
                                Ifh.Insert_Formato_Horario(ID_FORMATO_HORARIO, "T" + j, NO_DIA, HO_DESDE, HO_HASTA, ES_F_HORARIO, ID_TIPO_HORARIO);
                            }
                        }
                    }
                }
            }

            if (opc.equals("REGISTRAR_FOR_HORARIO")) {
                String ID_FORMATO_HORARIO = request.getParameter(null);
                String ID_TIPO_HORARIO = request.getParameter("IDTIPOHORARIO");
                String ES_F_HORARIO = request.getParameter("ESTADO");

                for (int i = 0; i < dia.size(); i++) {
                    for (int j = 0; j < 10; j++) {
                        String HO_DESDE = request.getParameter("HORA_DESDE_" + dia.get(i) + j);
                        String HO_HASTA = request.getParameter("HORA_HASTA_" + dia.get(i) + j);
                        String NO_TURNO = request.getParameter("TURNO_" + dia.get(i) + j);
                        String NO_DIA = request.getParameter("DIA_" + dia.get(i) + j);

                        if (HO_DESDE != null & NO_DIA != null & HO_HASTA != null) {
                            if (!HO_HASTA.equals("") & !HO_DESDE.equals("") & !NO_DIA.equals("")) {
                                Ifh.Insert_Formato_Horario(ID_FORMATO_HORARIO, NO_TURNO, NO_DIA, HO_DESDE, HO_HASTA, ES_F_HORARIO, ID_TIPO_HORARIO);
                            }
                        }
                    }

                }
                sesion.setAttribute("List_Tipo_Horario", Ifh.Listar_Tipo_Horario());
                ///response.sendRedirect("views/Formato_Horario/Detalle_Formato_Horario.html");

            }
            if (opc.equals("LISTAR_FORMATO_HORARIO")) {
                String ID_TIPO_HORARIO = request.getParameter("idth");
                String nofor = request.getParameter("nofor");
                sesion.setAttribute("LISTAR_FORMATO_HORARIO", Ifh.Listar_Formato_Horario(ID_TIPO_HORARIO));
                sesion.setAttribute("List_D", Ifh.List_D());
                ///response.sendRedirect("views/Formato_Horario/List_Formato_Horario.html?nofor=" + nofor + "");
            }
            if (opc.equals("Listar_Tip_Horario")) {
                String id = request.getParameter("sec");
                List<Map<String, ?>> lista = Ifh.List_Tipo_HorarioSec(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("statupdate")) {
                String id = request.getParameter("id");
                String es = request.getParameter("es");
                Ifh.StatUpdate(id, es);
            }
            if (opc.equals("Eliminar_turno")) {
                String id_horario = request.getParameter("id_horario");
                Ifh.Eliminar_turno(id_horario);
            }
            if (opc.equals("Listar_Horas_horario")) {
                String id_dgp = request.getParameter("iddgp");
                List<Map<String, ?>> lista = Ifh.Listar_Horario_dgp(id_dgp);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Listar_Horario")) {
                String id = request.getParameter("id");
                List<Map<String, ?>> lista = Ifh.List_Formato_h(id);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("cargar_dep")) {
                List<Map<String, ?>> lista = dp.List_departamento_2();
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta , HttpStatus.OK);

//        Gson gson = new Gson();
//        out.print(gson.toJson(rpta));
//        out.flush();
//        out.close();
    }

    public static String parser24(String fecha) {
        if (fecha != null) {
            String ret = "";
            String[] buffer = fecha.split(":");
            int x = Integer.parseInt(buffer[0]);
            String[] buffer2 = buffer[1].split(" ");
            int y = Integer.parseInt(buffer2[0]);
            String h = buffer2[1];
            if (h.toUpperCase().equals("AM")) {
                if (x < 12) {
                    ret = x + ":" + y;
                } else {
                    if (x == 12) {
                        ret = 0 + ":" + y;
                    }
                }
            }
            if (h.toUpperCase().equals("PM")) {
                if (x < 12) {
                    ret = (x + 12) + ":" + y;
                } else {
                    if (x == 12) {
                        ret = x + ":" + y;
                    }
                }

            }
            return ret;
        } else {
            return null;
        }

    }
}
