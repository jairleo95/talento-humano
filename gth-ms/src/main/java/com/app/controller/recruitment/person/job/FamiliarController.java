/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.person.job;

import com.app.persistence.dao_imp.IDatosHijoTrabajador;
import com.app.persistence.dao_imp.InterfacePadre_Madre_ConyugueDAO;
import com.app.persistence.dao_imp.ITipo_DocumentoDAO;
import com.app.persistence.dao_imp.ITrabajadorDAO;
import com.app.controller.util.DateFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.app.persistence.dao.Datos_Hijo_TrabajadorDAO;
import com.app.persistence.dao.Padre_Madre_ConyugueDAO;
import com.app.persistence.dao.Tipo_DocumentoDAO;
import com.app.persistence.dao.TrabajadorDAO;

/**
 *
 * @author Karencita
 */
@RestController
@RequestMapping("familiar")
public class FamiliarController {

    IDatosHijoTrabajador h = new Datos_Hijo_TrabajadorDAO();
    InterfacePadre_Madre_ConyugueDAO pmc = new Padre_Madre_ConyugueDAO();
    ITrabajadorDAO tr = new TrabajadorDAO();
    ITipo_DocumentoDAO td = new Tipo_DocumentoDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sesion = request.getSession(true);

        String user = (String) sesion.getAttribute("IDUSER");

        String opc = request.getParameter("opc");
        DateFormat c = new DateFormat();

        Map<String, Object> rpta = new HashMap<String, Object>();
        try {
            if (opc.equals("Registrar Conyugue")) {
                String ES_TRABAJA_UPEU_CONYUGUE = request.getParameter("TRABAJA_UPEU_CONYUGUE");
                String AP_NOMBRES_CONYUGUE = request.getParameter("APELLIDO_NOMBRES_CONYUGUE");
                String FE_NAC_CONYUGUE = request.getParameter("FECHA_NAC_CONYUGUE");
                String TI_DOC_ID = request.getParameter("TIPO_DOC_ID");
                String NU_DOC = request.getParameter("NRO_DOC");
                String LI_INSCRIPCION_VIG_ESSALUD = request.getParameter("INSCRIPCION_VIG_ESSALUD");
                String ID_TRABAJADOR = request.getParameter("idtr");
                String ID_CONYUGUE = request.getParameter("CONYUGUE");

                pmc.INSERT_CONYUGUE(ES_TRABAJA_UPEU_CONYUGUE, AP_NOMBRES_CONYUGUE, FE_NAC_CONYUGUE, TI_DOC_ID, NU_DOC, LI_INSCRIPCION_VIG_ESSALUD, user, tr.ip(), ID_TRABAJADOR, ID_CONYUGUE);
                //   sesion.setAttribute("List_PMC", pmc.List_PMC(ID_TRABAJADOR));
                sesion.setAttribute("LISTA_HIJOS", h.LISTA_HIJOS(ID_TRABAJADOR));
                sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(ID_TRABAJADOR));
                sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(ID_TRABAJADOR));
                response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + ID_TRABAJADOR);
            }
            if (opc.equals("Registrar Padres")) {
                String AP_NOMBRES_PADRE = request.getParameter("APELLIDOS_NOMBRES_PADRE");
                String AP_NOMBRES_MADRE = request.getParameter("APELLIDOS_NOMBRES_MADRE");

                String ID_TRABAJADOR = request.getParameter("idtr");

                pmc.INSERT_PADRES(AP_NOMBRES_MADRE, AP_NOMBRES_PADRE, ID_TRABAJADOR, user, tr.ip());
                //sesion.setAttribute("List_PMC", pmc.List_PMC(ID_TRABAJADOR));
                sesion.setAttribute("LISTA_HIJOS", h.LISTA_HIJOS(ID_TRABAJADOR));
                sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(ID_TRABAJADOR));
                sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(ID_TRABAJADOR));
                response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + ID_TRABAJADOR);
            }
            if (opc.equals("Detalle_Familiar")) {
                String idtr = request.getParameter("idtr");
                sesion.setAttribute("LISTA_HIJOS", h.LISTA_HIJOS(idtr));
                sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(idtr));
                sesion.setAttribute("Lista_Tipo_Doc", td.Listar_tipo_doc());
                response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + idtr);
            }
            if (opc.equals("Listar_Hijo_id_tr")) {
                String idtr = request.getParameter("idtr");
                List<Map<String, ?>> lista = h.Lis_Hijos_id_tr(idtr);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("REGISTRAR HIJO")) {
                String idtr = request.getParameter("idtr");
                String ID_DATOS_HIJOS_TRABAJADOR = null;
                String ID_TRABAJADOR = request.getParameter("idtr");
                String AP_PATERNO = request.getParameter("APELLIDO_P");
                String AP_MATERNO = request.getParameter("APELLIDO_M");
                String NO_HIJO_TRABAJADOR = request.getParameter("NOMBRE");
                String FE_NACIMIENTO = request.getParameter("FECHA_NAC");
                String ES_SEXO = request.getParameter("SEXO");
                String ES_TIPO_DOC = request.getParameter(("TIPO_DOC_ID"));
                String NU_DOC = request.getParameter("NRO_DOC");
                String ES_PRESENTA_DOCUMENTO = null;
                String ES_INSCRIPCION_VIG_ESSALUD = request.getParameter("INSCRIPCION_VIG_ESSALUD");
                String ES_ESTUDIO_NIV_SUPERIOR = request.getParameter("ESTUD_NIV_SUPERIOR");
                String FE_CREACION = null;
                String US_MODIF = null;
                String FE_MODIF = null;
                String IP_USUARIO = null;
                String ES_DATOS_HIJO_TRABAJADOR = "1";
                FE_NACIMIENTO = DateFormat.toFormat3(FE_NACIMIENTO);
                h.INSERT_DATOS_HIJO_TRABAJADOR(ID_DATOS_HIJOS_TRABAJADOR, ID_TRABAJADOR, AP_PATERNO, AP_MATERNO, NO_HIJO_TRABAJADOR, FE_NACIMIENTO, ES_SEXO, ES_TIPO_DOC, NU_DOC,
                        ES_PRESENTA_DOCUMENTO, ES_INSCRIPCION_VIG_ESSALUD, ES_ESTUDIO_NIV_SUPERIOR, user, FE_CREACION, US_MODIF, FE_MODIF, IP_USUARIO, ES_DATOS_HIJO_TRABAJADOR);
                sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(ID_TRABAJADOR));
                response.sendRedirect("views/Trabajador/Familiar/Reg_Datos_Hijo.html?idtr=" + idtr);
            }
            if (opc.equals("eliminar")) {
                String id_hijo = request.getParameter("idhijo");
                String id_tr = request.getParameter("idtr");
                h.ELIMINAR_HIJO(id_hijo, id_tr);
                sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(id_tr));
                response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + id_tr);
            }
            if (opc.equals("modificar")) {
                String idhijo = request.getParameter("idhijo");
                String idtr = request.getParameter("idtr");
                sesion.setAttribute("Lista_hijo_individual", h.LISTA_HIJO(idhijo, idtr));
                sesion.setAttribute("Listar_tipo_doc", td.Listar_tipo_doc());
                response.sendRedirect("views/Trabajador/Familiar/Mod_Datos_Hijos.html?idtr=" + idtr);

            }
            if (opc.equals("MODIFICAR HIJO")) {
                String idtr = request.getParameter("idtr");
                String ID_DATOS_HIJOS_TRABAJADOR = request.getParameter("idhijo");
                String AP_PATERNO = request.getParameter("APELLIDO_P");
                String AP_MATERNO = request.getParameter("APELLIDO_M");
                String NO_HIJO_TRABAJADOR = request.getParameter("NOMBRE");
                String FE_NACIMIENTO = request.getParameter("FECHA_NAC");
                String ES_SEXO = request.getParameter("SEXO");
                String ES_TIPO_DOC = request.getParameter("TIPO_DOC_ID");
                String NU_DOC = request.getParameter("NRO_DOC");
                String ES_INSCRIPCION_VIG_ESSALUD = request.getParameter("INSCRIPCION_VIG_ESSALUD");
                String ES_ESTUDIO_NIV_SUPERIOR = request.getParameter("ESTUD_NIV_SUPERIOR");
                FE_NACIMIENTO = DateFormat.toFormat3(FE_NACIMIENTO);
                h.MOD_HIJOS_TRAB(ID_DATOS_HIJOS_TRABAJADOR, AP_PATERNO, AP_MATERNO, NO_HIJO_TRABAJADOR, FE_NACIMIENTO, ES_SEXO, ES_TIPO_DOC, NU_DOC, ES_INSCRIPCION_VIG_ESSALUD, ES_ESTUDIO_NIV_SUPERIOR, user);
                //sesion.setAttribute("List_PMC", pmc.List_PMC(idtr));
                sesion.setAttribute("LISTA_HIJOS", h.LISTA_HIJOS(idtr));
                sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(idtr));
                response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + idtr);
            }
            if (opc.equals("Editar_Familiar")) {
                String idtr = request.getParameter("idtra");
                //getServletContext().setAttribute("List_PMC", pmc.List_PMC(idtr));
                sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                sesion.setAttribute("Listar_tipo_doc", td.Listar_tipo_doc());
                // out.print(tr.ListaridTrabajador(idtr).size()+"   "+idtr);
                response.sendRedirect("views/Trabajador/Familiar/Mod_Familiar.html?idtr=" + idtr);
            }
            if (opc.equals("Modificar_Padre_madre")) {
                String idtr = request.getParameter("idtr");
                String padre = request.getParameter("APELLIDOS_NOMBRES_PADRE");
                String madre = request.getParameter("APELLIDOS_NOMBRES_MADRE");
                pmc.MOD_PADRES(padre, madre, idtr, user);
                //sesion.setAttribute("List_PMC", pmc.List_PMC(idtr));
                sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + idtr);
            }
            if (opc.equals("List_Padre")) {
                String idtr = request.getParameter("id");
                List<Map<String, ?>> lista = pmc.Listar_Padres(idtr);
                rpta.put("rpta", "1");
                rpta.put("lista", lista);
            }
            if (opc.equals("Modificar_Padres")) {
                String idtr = request.getParameter("idtr");
                response.sendRedirect("views/Trabajador/Familiar/Reg_Padres.html?opc=Mod&idtr=" + idtr);
            }
            if (opc.equals("MODIFICAR_PMC")) {
                try {
                    String AP_NOMBRES_PADRE = "";
                    String AP_NOMBRES_MADRE = "";
                    String ES_TRABAJA_UPEU_CONYUGUE = request.getParameter("TRABAJA_UPEU_CONYUGUE");
                    String AP_NOMBRES_CONYUGUE = request.getParameter("APELLIDO_NOMBRES_CONYUGUE");

                    String FE_NAC_CONYUGUE = DateFormat.toFormat1(request.getParameter("FECHA_NAC_CONYUGUE"));
                    String TI_DOC_ID = request.getParameter("TIPO_DOC_ID");
                    String NU_DOC = request.getParameter("NRO_DOC");
                    String LI_INSCRIPCION_VIG_ESSALUD = request.getParameter("INSCRIPCION_VIG_ESSALUD");
                    //String US_MODIF = "";
                    //String FE_MODIF = "";
                    String ID_TRABAJADOR = request.getParameter("idtr");
                    // out.print(opc+"   "+AP_NOMBRES_PADRE+AP_NOMBRES_MADRE+ES_TRABAJA_UPEU_CONYUGUE+AP_NOMBRES_CONYUGUE+FE_NAC_CONYUGUE+TI_DOC_ID+ NU_DOC + LI_INSCRIPCION_VIG_ESSALUD + US_MODIF+FE_MODIF + ID_TRABAJADOR+"");
                    pmc.MOD_PADRE_MADRE_CONYUGUE(AP_NOMBRES_PADRE, AP_NOMBRES_MADRE, ES_TRABAJA_UPEU_CONYUGUE, AP_NOMBRES_CONYUGUE, FE_NAC_CONYUGUE, TI_DOC_ID, NU_DOC, LI_INSCRIPCION_VIG_ESSALUD, ID_TRABAJADOR, user, tr.ip());

                    sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(ID_TRABAJADOR));
                    sesion.setAttribute("LISTA_HIJOS", h.LISTA_HIJOS(ID_TRABAJADOR));
                    sesion.setAttribute("LISTA_HIJO", h.LISTA_HIJOS(ID_TRABAJADOR));
                    response.sendRedirect("views/Trabajador/Familiar/Detalle_Familiar.html?idtr=" + ID_TRABAJADOR);
                } catch (ParseException ex) {
                    Logger.getLogger(FamiliarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


}
