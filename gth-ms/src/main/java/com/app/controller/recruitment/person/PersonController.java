/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller.recruitment.person;

import com.app.persistence.dao.Carrera_UniversidadDAO;
import com.app.persistence.dao.DireccionDAO;
import com.app.persistence.dao.Hist_Estado_CivilDAO;
import com.app.persistence.dao.ListaDAO;
import com.app.domain.model.SalaryAccount;
import com.app.domain.model.Employee;
import com.app.domain.model.TipoHoraPago;
import com.app.controller.util.CCriptografiar;
import com.app.controller.util.DateFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.persistence.dao.Datos_Hijo_TrabajadorDAO;
import com.app.persistence.dao.DgpDAO;
import com.app.persistence.dao.DocumentoDAO;
import com.app.persistence.dao.EmpleadoDAO;
import com.app.persistence.dao.TipoHoraPagoDAO;
import com.app.persistence.dao.Tipo_DocumentoDAO;
import com.app.persistence.dao.TrabajadorDAO;
import com.app.persistence.dao.UbigeoDAO;
import com.app.persistence.dao_imp.InterfaceCarrera_UniversidadDAO;
import com.app.persistence.dao_imp.IDatosHijoTrabajador;
import com.app.persistence.dao_imp.IDgpDAO;
import com.app.persistence.dao_imp.IDireccionDAO;
import com.app.persistence.dao_imp.IDocumentoDAO;
import com.app.persistence.dao_imp.IEmpleadoDAO;
import com.app.persistence.dao_imp.InterfaceHist_Estado_CivilDAO;
import com.app.persistence.dao_imp.IListaDAO;
import com.app.persistence.dao_imp.InterfaceTipoHoraPagoDAO;
import com.app.persistence.dao_imp.ITipo_DocumentoDAO;
import com.app.persistence.dao_imp.ITrabajadorDAO;
import com.app.persistence.dao_imp.IUbigeoDAO;

/**
 *
 * @author Alfa.sistemas
 */
@RestController
@RequestMapping("trabajador")
public class PersonController {

    ITrabajadorDAO tr = new TrabajadorDAO();
    InterfaceTipoHoraPagoDAO thp = new TipoHoraPagoDAO();
    IListaDAO li = new ListaDAO();
    IUbigeoDAO ub = new UbigeoDAO();
    IDocumentoDAO d = new DocumentoDAO();
    IEmpleadoDAO em = new EmpleadoDAO();
    IDgpDAO dgp = new DgpDAO();
    IDireccionDAO dir = new DireccionDAO();
    ITipo_DocumentoDAO tdoc = new Tipo_DocumentoDAO();
    InterfaceCarrera_UniversidadDAO cu = new Carrera_UniversidadDAO();
    InterfaceHist_Estado_CivilDAO ec = new Hist_Estado_CivilDAO();
    IDatosHijoTrabajador h = new Datos_Hijo_TrabajadorDAO();

    @PostMapping("diezmo")
    public ResponseEntity<?> diezmo(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");

        HttpSession sesion = request.getSession(true);
        String user = (String) sesion.getAttribute("IDUSER");

        if (user != null) {
            if (opc.equals("ModDiezmoDetalleTrabajador")) {
                String idtr = request.getParameter("id");
                int i = tr.ShowEsDiezmoTrabajador(idtr);
                String html = "";
                html += "   <div class='input-group'> "
                        + "          <span class='form-control' style='padding: 5px;padding-left: 10px'> ¿Autorizar descuento?</span>     "
                        + "          <span class='input-group-addon'>";
                html += "                              <span class='onoffswitch'>";
                if (i == 1) {
                    html += "                                <input type='checkbox' name='diezmo' value='1' checked=''   class='onoffswitch-checkbox cbkDiezmo' id='st3'>";
                } else if (i == 0) {
                    html += "                                <input type='checkbox' name='diezmo' value='1'    class='onoffswitch-checkbox cbkDiezmo' id='st3'>";
                }
                html += "                                   <label class='onoffswitch-label' for='st3'> ";

                html += "                                   <span class='onoffswitch-inner' data-swchon-text='SI' data-swchoff-text='NO'></span> ";
                html += "                                     <span class='onoffswitch-switch'></span> ";
                html += "                                      </label> ";
                html += "                              </span>";
                html += "                      </span>"
                        + "</div>"
                        + "</div>";

                rpta.put("html", html);
                rpta.put("rpta", "1");
            }
            if (opc.equals("UpdateEsDiezmo")) {
                String idtr = request.getParameter("id");
                int estado = Integer.parseInt(request.getParameter("estado"));
                rpta.put("status", tr.UpdateEsDiezmo(idtr, estado));
                rpta.put("rpta", "1");
            }
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping("afp")
    public ResponseEntity<?> afp(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");

        HttpSession sesion = request.getSession(true);
        String user = (String) sesion.getAttribute("IDUSER");
        String idrol = (String) sesion.getAttribute("IDROL");

        if (user != null) {
            if (opc.equals("ShowAFP_SP")) {
                String idtr = request.getParameter("id");
                String data[] = tr.ShowAFP_ONP(idtr);
                String html = "";

                boolean permisoShowAFP_SP = false;
                switch (idrol) {
                    case "ROL-0006":
                        permisoShowAFP_SP = true;
                        break;
                }
                if (permisoShowAFP_SP) {

                    html += " <div class='row'>";
                    html += " <div class='col-md-8'><strong>Nombre AFP:</strong>";
                    html += " </div>";
                    html += " <div class='col-md-4'>";
                    for (int w = 0; w < li.List_Nom_AFP().size(); w++) {
                        if (data[0].trim().equals(w + 1 + "")) {
                            html += li.List_Nom_AFP().get(w);
                        }
                    }
                    html += " </div>";
                    html += " </div>";
                    html += " <div class='row'>";
                    html += " <div class='col-md-8'><strong>Sistema Pensionario:</strong>";
                    html += " </div>";
                    html += " <div class='col-md-4'>";
                    for (int dd = 0; dd < li.List_Sp().size(); dd++) {
                        if (data[1].trim().equals(dd + 1 + "")) {
                            html += (li.List_Sp().get(dd));
                        }
                    }
                    html += " </div>";
                    html += " </div>";
                }
                rpta.put("html", html);
                rpta.put("rpta", "1");
            }
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping("tiHoraPago")
    public ResponseEntity<?> tiHoraPago(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("IDUSER");

        if (user != null) {
         if (opc.equals("getTiHoraPago")) {
            System.out.println("enter to getTiHoraPago");
            String idtr = null;
            try {
                idtr = CCriptografiar.Desencriptar(String.valueOf(request.getParameter("idtr")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // System.out.println((request.getParameter("idtr")));
            //  System.out.println("id:"+CCriptografiar.Encriptar("TRB-002172"));
            String html = "";
            List<TipoHoraPago> t = thp.listTiHoraPagoByIdTrabajador(idtr);
            html += "<select  name='TiHoraPago' class='form-control input-sm TiHoraPago bounceIn animated' required>";
            if (t.isEmpty()) {
                html += "<option>[Sin datos]</option>";
            } else {
                html += "<option >[Seleccione]</option>";
                for (int i = 0; i < t.size(); i++) {
                    html += "<option value='" + CCriptografiar.Encriptar(t.get(i).getIdTiHoraPago()) + "' data-valor='" + t.get(i).getCaTiHoraPago() + "'>S/." + t.get(i).getCaTiHoraPago()
                            + " ( " + t.get(i).getFeCreacion() + ") </option>";
                }
            }
            html += "</select>";
            rpta.put("html", html);
            rpta.put("status", true);

            }
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @RequestMapping
    public ResponseEntity<?> read(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");

        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("IDUSER");
        String iddep = (String) session.getAttribute("DEPARTAMENTO_ID");
        String Text = (String) session.getAttribute("text");

        if (user != null) {

            if (opc.equals("Buscar")) {
                String Buscar = request.getParameter("busqueda");
                String dni = request.getParameter("dni");
                String nom = request.getParameter("nom");
                String ape_mat = request.getParameter("ape_mat");
                String ape_pat = request.getParameter("ape_pat");
                String id_req = request.getParameter("id_req");
                if (("Buscar".equals(Buscar) & (!"".equals(dni) | !"".equals(nom) | !"".equals(ape_mat) | !"".equals(ape_pat)))) {
                    String busc = (String) request.getParameter("busc");
                    if (busc != null) {
                        session.setAttribute("ListarTrabajador2", tr.Buscar_Trabajador_Requerimiento(iddep, dni, nom, ape_pat, ape_mat, id_req));
                        //no va getServletContext().setAttribute(nom, dgp.VAL_OPC_DGP(dni));

                        response.sendRedirect("views/Dgp/Generar_Dgp.html?text=" + Text);
                    }
                } else {
                     response.sendRedirect("views/Dgp/Generar_Dgp.html?text=" + Text);

                }
            }
            if (opc.equals("Buscar_Trabajador")) {
                String Buscar = request.getParameter("busqueda");
                String dni = request.getParameter("dni");
                String nom = request.getParameter("nom");
                String ape_mat = request.getParameter("ape_mat");
                String ape_pat = request.getParameter("ape_pat");

                System.out.println(ape_pat);
                System.out.println(ape_mat);
                System.out.println(nom);
                if (("Buscar".equals(Buscar) & (!"".equals(dni) | !"".equals(nom) | !"".equals(ape_mat) | !"".equals(ape_pat)))) {
                    session.setAttribute("ListarTrabajador", tr.Buscar_Ficha_Trabajador(iddep, dni, nom, ape_pat, ape_mat));
                    //getServletContext().setAttribute(nom, dgp.VAL_OPC_DGP(dni));
                    response.sendRedirect("Ficha_Trabajador");
                } else {
                    response.sendRedirect("Ficha_Trabajador");
                }
            }
            if ("list".equals(opc)) {
                String idtr = request.getParameter("idtr");
                session.setAttribute("List_Cuenta_Sueldo", tr.List_Cuenta_Sueldo(idtr));
                session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                session.setAttribute("List_Universidad", li.List_Universidad());
                session.setAttribute("List_tipo_institucion", cu.List_Tipo_Ins());
                session.setAttribute("List_Ubigeo", ub.List_Distrito());
                session.setAttribute("Listar_tipo_doc", tdoc.Listar_tipo_doc());
                session.setAttribute("id_empleadox_ide", em.id_empleadox_ide(idtr));
                if (request.getParameter("dgp") != null) {
                    response.sendRedirect("Detalle_Trabajador?idtr=" + idtr + "&dgp=" + request.getParameter("dgp"));
                } else {
                    response.sendRedirect("Detalle_Trabajador?idtr=" + idtr);
                }
            }
            if ("list_reg_tra".equals(opc)) {
                String idtr = request.getParameter("idtr");
                String me = request.getParameter("aa");
                String op = request.getParameter("a");
                if (op != null) {
                    me = "t";
                }
                session.setAttribute("List_Cuenta_Sueldo", tr.List_Cuenta_Sueldo(idtr));
                session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                session.setAttribute("List_Universidad", li.List_Universidad());
                session.setAttribute("List_tipo_institucion", cu.List_Tipo_Ins());
                session.setAttribute("List_Ubigeo", ub.List_Distrito());
                session.setAttribute("Listar_tipo_doc", tdoc.Listar_tipo_doc());
                    response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?idtr=" + idtr.trim() + "&ms=" + me);
            }
            if (opc.equals("Mostrar_Cod_APS")) {
                String idtr = request.getParameter("tr");
                List<Employee> e = em.id_empleadox_ide(idtr);
                if (e.size() == 0) {
                    rpta.put("msg", "0");
                } else {
                    for (int i = 0; i < e.size(); i++) {

                    }
                    rpta.put("msg", "1");
                }

            }
            if (opc.equals("ShowEsDiezmoTrabajador")) {
                String idtr = request.getParameter("id");
                int i = tr.ShowEsDiezmoTrabajador(idtr);
                String html = "";
                html += "             <label  id='titu'>    Descuento de Diezmo: </label>    ";
                html += "   <div class='input-group'> "
                        + "          <span class='form-control' style='padding: 5px;padding-left: 10px'> ¿Autorizar descuento?</span>     "
                        + "          <span class='input-group-addon'>";
                html += "                              <span class='onoffswitch'>";
                if (i == 1) {
                    html += "                                <input type='checkbox' name='diezmo' value='1' checked=''   class='onoffswitch-checkbox cbkDiezmo' id='st3'>";
                } else if (i == 0) {
                    html += "                                <input type='checkbox' name='diezmo' value='1'    class='onoffswitch-checkbox cbkDiezmo' id='st3'>";
                }
                html += "                                   <label class='onoffswitch-label' for='st3'> ";

                html += "                                   <span class='onoffswitch-inner' data-swchon-text='SI' data-swchoff-text='NO'></span> ";
                html += "                                     <span class='onoffswitch-switch'></span> ";
                html += "                                      </label> ";
                html += "                              </span>";
                html += "                      </span>"
                        + "</div>"
                        + "</div>";

                rpta.put("html", html);
                rpta.put("rpta", "1");
            }


            if (opc.equals("ShowDialogFotoTrabajador")) {
                String idtr = request.getParameter("id");
                String html = "<div class='dialog-message' title='Dialog Simple Title'>"
                        + "            <p>La imagen todavia no esta <b>Confirmada</b> aun confirme para poder guardar la imagen</p>"
                        + "            <div class='hr hr-12 hr-double'></div>"
                        + "            <div class='pre-avatar col-md-offset-1 center-block'>"
                        + "                <img class='pre_foto thumbnail center-block' style='max-height: 300px; max-width: 500px; min-height:200px; min-width:400px;' />"
                        + "            </div>"
                        + "            <p>Si la imagen está <b>borrosa</b> es porque el tamaño es muy pequeño, no se preocupe, se verá mejor en el menú</p>"
                        + "            <div class='hr hr-12 hr-double'></div>"
                        + "                 <div class='avatar-user center-block'>"
                        + "                     <a href='javascript:void(0)' id='show-shortcut' >"
                        + "                         <img class='pre_foto bounceIn animated center-block' style='height:100px;width:100px;'/>"
                        + "                     </a>  "
                        + "                     <a href='javascript:void(0)' id='show-shortcut' >"
                        + "                         <img class='pre_foto bounceIn animated center-block' style='border-radius:50%;height:70px;width:70px;'/>"
                        + "                     </a>  "
                        + "                 </div>"
                        + "            <div style='display:none' class='progressbar'>"
                        + "                <div class='progress-label progress-bar progress-primary'>Loading...</div> "
                        + "            </div>"
                        + "        </div>";
                rpta.put("html", html);
                rpta.put("rpta", "1");
            }
            if (opc.equals("ShowPorcentageTrabajador")) {
                String id = request.getParameter("id");
                rpta.put("porcentaje", tr.ShowPorcentageTrabajador(id));
            }
            if (opc.equals("Listar_Asp_Social")) {
                String idtr = request.getParameter("idtr");
                session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                session.setAttribute("Listar_via", dir.Listar_via());
                session.setAttribute("Listar_zona", dir.Listar_zona());
                session.setAttribute("ListarDir_Dom", li.List_Dom_D3_Id());
                session.setAttribute("List_Provincia", ub.List_Provincia());
                session.setAttribute("List_Distrito", ub.List_DistritoTra());
                session.setAttribute("List_Ubigeo", ub.List_Distrito());
                session.setAttribute("List_Departamento", ub.List_Departamento());
                session.setAttribute("List_Dom_D1_Id", li.List_Dom_D1_Id());
                session.setAttribute("List_Dom_D5_Id", li.List_Dom_D5_Id());
                session.setAttribute("List_Dom_D3_Id", li.List_Dom_D3_Id());
                response.sendRedirect("Trabajador/Aspecto_Social.html?idtr=" + idtr);
            }
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rpta = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession session = request.getSession(true);
        String iduser = (String) session.getAttribute("IDUSER");

        if ("actualizar".equals(opc)) {
            String idtr = request.getParameter("idtr");
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?idtr=" + idtr.trim());
        }
        if ("edit_perfil".equals(opc)) {
            String idtr = tr.ID_TRB(iduser);
            session.setAttribute("List_Cuenta_Sueldo", tr.List_Cuenta_Sueldo(idtr));
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            session.setAttribute("List_Universidad", li.List_Universidad());
            session.setAttribute("List_tipo_institucion", cu.List_Tipo_Ins());
            session.setAttribute("List_Ubigeo", ub.List_Distrito());
            session.setAttribute("Listar_tipo_doc", tdoc.Listar_tipo_doc());
            session.setAttribute("id_empleadox_ide", em.id_empleadox_ide(idtr));
            response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?edit=ok&idtr=" + idtr);
        }
        if (opc.equals("Modificar_Dat_Gen")) {
            String edit = request.getParameter("editar");
            String ID_TRABAJADOR = request.getParameter("IDTR");
            String AP_PATERNO = request.getParameter("APELLIDO_P");
            String AP_MATERNO = request.getParameter("APELLIDO_M");
            String NO_TRABAJADOR = request.getParameter("NOMBRES");
            String ES_SEXO = request.getParameter("SEXO");
            String FE_NAC = request.getParameter("FECHA_NAC");
            String ID_NACIONALIDAD = request.getParameter("NACIONALIDAD");
            String ID_DEPARTAMENTO = null;
            String ID_PROVINCIA = null;
            String ID_DISTRITO = request.getParameter("DISTRITO");
            String TI_DOC = "";
            String NU_DOC = "";
            if (edit.equals("ok")) {
                TI_DOC = request.getParameter("TI_DOC");
                NU_DOC = request.getParameter("NU_DOC");
            } else {
                TI_DOC = request.getParameter("TIPO_DOC");
                NU_DOC = request.getParameter("NRO_DOC");
            }
            String ES_CIVIL = request.getParameter("ESTADO_CIVIL");
            String LI_GRUPO_SANGUINEO = request.getParameter("GRUPO_SANGUINEO");
            String ES_FACTOR_RH = request.getParameter("FACTOR_RH_ID");
            String TE_TRABAJADOR = request.getParameter("TELEFONO");
            String CL_TRA = request.getParameter("CELULAR");
            String DI_CORREO_PERSONAL = request.getParameter("CORREO_PERSONAL");
            String DI_CORREO_INST = request.getParameter("CORREO_INST");
            String CO_SISTEMA_PENSIONARIO = request.getParameter("SISTEMA_PENSIONARIO");
            String ID_NO_AFP = request.getParameter("NOMBRE_AFP_ID");
            String ES_AFILIADO_ESSALUD = request.getParameter("AFILIADO_ESSALUD_ID");
            String LI_TIPO_TRABAJADOR = request.getParameter("TIPO_TRABAJADOR_ID");
            String idtr = request.getParameter("idtr");
            String US_MODIF = iduser;
            String IP_USUARIO = tr.ip();
            String FE_MODIFICACION = "";
            String ES_CIVIL_A = request.getParameter("ES_CIVIL_A");
            String ES_REGISTRO = request.getParameter("ES_REGISTRO");
            if (!ES_CIVIL.equals(ES_CIVIL_A)) {
                ec.INSERT_HIST_ESTADO_CIVIL(null, ES_CIVIL_A, FE_MODIFICACION, US_MODIF, ID_TRABAJADOR, ES_REGISTRO);
            }
            FE_NAC = DateFormat.toFormat3(FE_NAC);
            tr.MOD_DAT_GEN(AP_PATERNO, AP_MATERNO, NO_TRABAJADOR, TI_DOC, NU_DOC, ES_CIVIL, FE_NAC, ID_NACIONALIDAD, ID_DEPARTAMENTO, ID_PROVINCIA, ID_DISTRITO, TE_TRABAJADOR, CL_TRA, DI_CORREO_PERSONAL, DI_CORREO_INST, CO_SISTEMA_PENSIONARIO, ES_SEXO, LI_GRUPO_SANGUINEO, ID_NO_AFP, ES_AFILIADO_ESSALUD, LI_TIPO_TRABAJADOR, ES_FACTOR_RH, idtr, US_MODIF, IP_USUARIO);
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            if (edit.equals("ok")) {
                response.sendRedirect("views/Trabajador/Datos_Generales.html?idtr=" + idtr + "&edit=" + edit);
            } else {
                response.sendRedirect("views/Trabajador/Datos_Generales.html?idtr=" + idtr);
            }
        }
        if (opc.equals("Editar_Dat_Gen")) {
            String idtr = request.getParameter("idtr");
            String edit = request.getParameter("edit");
            session.setAttribute("3e", tr.ListaridTrabajador(idtr));
            session.setAttribute("List_Nacionalidad", li.List_Nacionalidad());
            session.setAttribute("List_Departamento", ub.List_Departamento());
            session.setAttribute("List_Provincia", ub.List_Provincia());
            session.setAttribute("List_Distrito", ub.List_DistritoTra());
            session.setAttribute("Listar_tipo_doc", tdoc.Listar_tipo_doc());
            response.sendRedirect("views/Trabajador/Mod_Datos_Generales.html?idtr=" + idtr + "&edit=" + edit);
        }

        if (opc.equals("Editar_Asp_Acad")) {
            String idtr = request.getParameter("idtr");
            String edit = request.getParameter("edit");
            List<SalaryAccount> li1 = tr.List_Cuenta_Sueldo(idtr);
            if (!li1.isEmpty()) {
                session.setAttribute("List_Cuenta_Sueldo", li1);
            } else {
                tr.INSERT_CUENTA_SUELDO(null, null, null, null, "0", null, idtr, "0");
            }
            if (li1.get(0).getEs_cuenta_sueldo().trim().equals("1")) {
                edit = "ok";
            }
            session.setAttribute("List_tipo_institucion", cu.List_Tipo_Ins());
            session.setAttribute("list_año", li.lista_años());
            session.setAttribute("List_Universidad", li.List_Universidad());
            session.setAttribute("List_Carrera", li.List_Carrera());
            session.setAttribute("List_Situacion_Educativa", li.List_Situacion_Educativa());
            response.sendRedirect("views/Trabajador/Mod_Aspecto_Academico.html?idtr=" + idtr + "&edit=" + edit);

        }
        if (opc.equals("Modificar_Asp_Acad")) {
            String idtr = request.getParameter("idtr");
            //ASPECTO ACADEMICO
            String LI_NIVEL_EDUCATIVO = request.getParameter("NIVEL_EDUCATIVO");
            String CARRERA = request.getParameter("CARRERA");
            String REGIMEN = request.getParameter("REGIMEN");
            String ES_INST_PERU = request.getParameter("ES_INST_PERU");
            String DE_ANNO_EGRESO = request.getParameter("A_EGRESO");
            String CM_OTROS_ESTUDIOS = request.getParameter("OTROS_ESTUDIOS");
            String CA_TIPO_HORA_PAGO_REFEERENCIAL = request.getParameter("TIPO_HORA_PAGO_REFEERENCIAL");
            String CO_UNIVERSITARIO = request.getParameter("CO_UNIVERSITARIO");
            String US_MODIF = iduser;
            String IP_USUARIO = tr.ip();
            tr.MOD_ASPEC_ACADEM(LI_NIVEL_EDUCATIVO, REGIMEN, ES_INST_PERU, CARRERA, DE_ANNO_EGRESO, CM_OTROS_ESTUDIOS, CA_TIPO_HORA_PAGO_REFEERENCIAL, idtr, CO_UNIVERSITARIO, US_MODIF, IP_USUARIO);
            //MODIFICAR CUENTA SUELDO
            String NO_BANCO = "";
            String NU_CUENTA = "";
            String NU_CUENTA_BANC = "";
            String ES_GEM_NU_CUENTA = "";
            String NO_BANCO_OTROS = "";

            if (request.getParameter("BANCO") != null) {
                NO_BANCO = (request.getParameter("BANCO") == null) ? "" : request.getParameter("BANCO");
                NU_CUENTA = (request.getParameter("CUENTA") == null) ? "" : request.getParameter("CUENTA");
                NU_CUENTA_BANC = (request.getParameter("CUENTA_BANC") == null) ? "" : request.getParameter("CUENTA_BANC");
                ES_GEM_NU_CUENTA = (request.getParameter("GEN_NU_CUEN") == null) ? "0" : request.getParameter("GEN_NU_CUEN");
                NO_BANCO_OTROS = (request.getParameter("BANCO_OTROS") == null) ? "" : request.getParameter("BANCO_OTROS");
                String ES_CUENTA_SUELDO = request.getParameter("ES_CUENTA_SUELDO");
                if (NO_BANCO.equals("")) {
                    ES_CUENTA_SUELDO = "0";
                } else {
                    ES_CUENTA_SUELDO = "1";
                }
                tr.MOD_CUENTA_SUELDO(NO_BANCO, NU_CUENTA, NU_CUENTA_BANC, ES_GEM_NU_CUENTA, NO_BANCO_OTROS, idtr, ES_CUENTA_SUELDO);
            }
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            session.setAttribute("List_Cuenta_Sueldo", tr.List_Cuenta_Sueldo(idtr));
            response.sendRedirect("views/Trabajador/Aspecto_Academico.html?idtr=" + idtr);

        }
        if (opc.equals("Editar_Asp_Soc")) {
            String idtr = request.getParameter("idtr");
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            session.setAttribute("Listar_via", dir.Listar_via());
            session.setAttribute("Listar_zona", dir.Listar_zona());
            session.setAttribute("ListarDir_Dom", li.List_Dom_D3_Id());
            session.setAttribute("List_Provincia", ub.List_Provincia());
            session.setAttribute("List_Distrito", ub.List_DistritoTra());
            session.setAttribute("List_Ubigeo", ub.List_Distrito());
            session.setAttribute("List_Departamento", ub.List_Departamento());
            session.setAttribute("List_Dom_D1_Id", li.List_Dom_D1_Id());
            session.setAttribute("List_Dom_D5_Id", li.List_Dom_D5_Id());
            session.setAttribute("List_Dom_D3_Id", li.List_Dom_D3_Id());

            response.sendRedirect("views/Trabajador/Mod_Aspecto_Social.html?idtr=" + idtr);
        }
        if (opc.equals("Modificar_Asp_Soc")) {
            String idtr = request.getParameter("idtr");
            String LI_DI_DOM_A_D1 = request.getParameter("DIR_DOM_A_D1_ID");
            String DI_DOM_A_D2 = request.getParameter("DIR_DOM_A_D2");
            String LI_DI_DOM_A_D3 = request.getParameter("DIR_DOM_A_D3_ID");
            String DI_DOM_A_D4 = request.getParameter("DIR_DOM_A_D4");
            String LI_DI_DOM_A_D5 = request.getParameter("DIR_DOM_A_D5_ID");
            String DI_DOM_A_D6 = request.getParameter("DIR_DOM_A_D6");
            String DI_DOM_A_REF = request.getParameter("DIR_DOM_A_REF");
            String ID_DI_DOM_A_DISTRITO = request.getParameter("DIR_DOM_A_DISTRITO_ID");
            String LI_DI_DOM_LEG_D1 = request.getParameter("DIR_DOM_LEG_D1_ID");
            String DI_DOM_LEG_D2 = request.getParameter("DIR_DOM_LEG_D2");
            String LI_DI_DOM_LEG_D3 = request.getParameter("DIR_DOM_LEG_D3_ID");
            String DI_DOM_LEG_D4 = request.getParameter("DIR_DOM_LEG_D4");
            String LI_DI_DOM_LEG_D5 = request.getParameter("DIR_DOM_LEG_D5_ID");
            String DI_DOM_LEG_D6 = request.getParameter("DIR_DOM_LEG_D6");
            String ID_DI_DOM_LEG_DISTRITO = request.getParameter("DIR_DOM_LEG_DISTRITO_ID");
            String CA_ING_QTA_CAT_EMPRESA = request.getParameter("ING_QTA_CAT_EMPRESA");
            String CA_ING_QTA_CAT_RUC = request.getParameter("ING_QTA_CAT_RUC");
            String CA_ING_QTA_CAT_OTRAS_EMPRESAS = request.getParameter("ING_QTA_CAT_OTRAS_EMPRESAS");
            String US_MODIF = iduser;
            String IP_USUARIO = tr.ip();
            tr.MOD_ASPEC_SOCIAL(LI_DI_DOM_A_D1, DI_DOM_A_D2, LI_DI_DOM_A_D3, DI_DOM_A_D4, LI_DI_DOM_A_D5, DI_DOM_A_D6, DI_DOM_A_REF, ID_DI_DOM_A_DISTRITO, LI_DI_DOM_LEG_D1, DI_DOM_LEG_D2, LI_DI_DOM_LEG_D3, DI_DOM_LEG_D4, LI_DI_DOM_LEG_D5, DI_DOM_LEG_D6, ID_DI_DOM_LEG_DISTRITO, CA_ING_QTA_CAT_EMPRESA, CA_ING_QTA_CAT_RUC, CA_ING_QTA_CAT_OTRAS_EMPRESAS, idtr, US_MODIF, IP_USUARIO);
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            response.sendRedirect("views/Trabajador/Aspecto_Social.html?idtr=" + idtr);
        }
        if ("Editar_Asp_Rel".equals(opc)) {
            String idtr = request.getParameter("idtr");
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            response.sendRedirect("views/Trabajador/Historial_Religion/Mod_Asp_Religioso.jsp?idtr=" + idtr + "&iduser=" + iduser);
        }
        if ("Modificar_Asp_Rel".equals(opc)) {
            String idtr = request.getParameter("idtr");
            String LI_RELIGION = request.getParameter("RELIGION");
            String NO_IGLESIA = request.getParameter("IGLESIA");
            String DE_CARGO = request.getParameter("CARGO");
            String LI_AUTORIDAD = request.getParameter("AUTORIDAD");
            String NO_AP_AUTORIDAD = request.getParameter("AUT_APELLIDOSNOMBRES");
            String CL_AUTORIDAD = request.getParameter("AUT_CELULAR");
            String FE_MODIF = "";
            tr.INSERT_HIST_RELIGION(null, LI_RELIGION, NO_IGLESIA, DE_CARGO, LI_AUTORIDAD, NO_AP_AUTORIDAD, CL_AUTORIDAD, "1", idtr, iduser, FE_MODIF);
            tr.MOD_ASP_REL(LI_RELIGION, NO_IGLESIA, DE_CARGO, LI_AUTORIDAD, NO_AP_AUTORIDAD, CL_AUTORIDAD, idtr, iduser, tr.ip());
            session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            response.sendRedirect("views/Trabajador/Aspecto_Social.html");
        }


        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rpta = new HashMap<String, Object>();
        HttpSession session = request.getSession(true);
        String idrol = (String) session.getAttribute("IDROL");
        String iduser = (String) session.getAttribute("IDUSER");
        String user = (String) session.getAttribute("IDUSER");

        if (user != null) {
            try {

                String opc = "";
                String Text = "";
                opc = (String) request.getParameter("opc");
                Text = (String) request.getParameter("text");

                if (opc.equals("Form_Reg")) {
                    session.setAttribute("List_Carrera", li.List_Carrera());
                    session.setAttribute("List_Nacionalidad", li.List_Nacionalidad());
                    session.setAttribute("List_Universidad", li.List_Universidad());
                    session.setAttribute("List_Departamento", ub.List_Departamento());
                    session.setAttribute("List_Situacion_Educativa", li.List_Situacion_Educativa());
                    session.setAttribute("Listar_via", dir.Listar_via());
                    session.setAttribute("Listar_zona", dir.Listar_zona());
                    session.setAttribute("Listar_tipo_doc", tdoc.Listar_tipo_doc());
                    session.setAttribute("list_año", li.lista_años());
                    response.sendRedirect("views/Trabajador/Reg_Trabajador.html");
                }
                if (opc.equals("Registrar")) {
                    String AP_PATERNO = request.getParameter("APELLIDO_P");
                    String AP_MATERNO = request.getParameter("APELLIDO_M");
                    String NO_TRABAJADOR = request.getParameter("NOMBRES");
                    String TI_DOC = request.getParameter("TIPO_DOC");
                    String NU_DOC = request.getParameter("NRO_DOC");
                    String ES_CIVIL = request.getParameter("ESTADO_CIVIL");
                    String FE_NAC = request.getParameter("FECHA_NAC");
                    String ID_NACIONALIDAD = request.getParameter("NACIONALIDAD");
                    String ID_DEPARTAMENTO = null;
                    String ID_PROVINCIA = null;
                    String ID_DISTRITO = request.getParameter("DISTRITO");
                    String TE_TRABAJADOR = request.getParameter("TELEFONO");
                    String CL_TRA = request.getParameter("CELULAR");
                    String DI_CORREO_PERSONAL = request.getParameter("CORREO_PERSONAL");
                    String DI_CORREO_INST = request.getParameter("CORREO_INST");
                    String CO_SISTEMA_PENSIONARIO = request.getParameter("SISTEMA_PENSIONARIO");
                    String LI_NIVEL_EDUCATIVO = request.getParameter("NIVEL_EDUCATIVO");
                    String CARRERA = request.getParameter("CARRERA");
                    String REGIMEN = request.getParameter("REGIMEN");
                    String ES_INST_PERU = request.getParameter("ES_INST_PERU");
                    String DE_ANNO_EGRESO = request.getParameter("A_EGRESO");
                    String CM_OTROS_ESTUDIOS = request.getParameter("OTROS_ESTUDIOS");
                    String ES_SEXO = request.getParameter("SEXO");
                    String LI_GRUPO_SANGUINEO = request.getParameter("GRUPO_SANGUINEO");
                    String DE_REFERENCIA = request.getParameter("DE_REFERENCIA");
                    String LI_RELIGION = request.getParameter("RELIGION");
                    String NO_IGLESIA = request.getParameter("IGLESIA");
                    String DE_CARGO = request.getParameter("CARGO");
                    String LI_AUTORIDAD = request.getParameter("AUTORIDAD");
                    String NO_AP_AUTORIDAD = request.getParameter("AUT_APELLIDOSNOMBRES");
                    String CL_AUTORIDAD = request.getParameter("AUT_CELULAR");
                    String ID_NO_AFP = request.getParameter("NOMBRE_AFP_ID");
                    String ES_AFILIADO_ESSALUD = request.getParameter("AFILIADO_ESSALUD_ID");
                    String LI_TIPO_TRABAJADOR = request.getParameter("TIPO_TRABAJADOR_ID");
                    String CA_TIPO_HORA_PAGO_REFEERENCIAL = request.getParameter("TIPO_HORA_PAGO_REFEERENCIAL");
                    String ES_FACTOR_RH = request.getParameter("FACTOR_RH_ID");
                    String LI_DI_DOM_A_D1 = request.getParameter("DIR_DOM_A_D1_ID");
                    String DI_DOM_A_D2 = request.getParameter("DIR_DOM_A_D2");
                    String LI_DI_DOM_A_D3 = request.getParameter("DIR_DOM_A_D3_ID");
                    String DI_DOM_A_D4 = request.getParameter("DIR_DOM_A_D4");
                    String LI_DI_DOM_A_D5 = request.getParameter("DIR_DOM_A_D5_ID");
                    String DI_DOM_A_D6 = request.getParameter("DIR_DOM_A_D6");
                    String DI_DOM_A_REF = request.getParameter("DIR_DOM_A_REF");
                    String ID_DI_DOM_A_DISTRITO = request.getParameter("DIR_DOM_A_DISTRITO_ID");
                    String LI_DI_DOM_LEG_D1 = request.getParameter("DIR_DOM_LEG_D1_ID");
                    String DI_DOM_LEG_D2 = request.getParameter("DIR_DOM_LEG_D2");
                    String LI_DI_DOM_LEG_D3 = request.getParameter("DIR_DOM_LEG_D3_ID");
                    String DI_DOM_LEG_D4 = request.getParameter("DIR_DOM_LEG_D4");
                    String LI_DI_DOM_LEG_D5 = request.getParameter("DIR_DOM_LEG_D5_ID");
                    String DI_DOM_LEG_D6 = request.getParameter("DIR_DOM_LEG_D6");
                    String ID_DI_DOM_LEG_DISTRITO = request.getParameter("DIR_DOM_LEG_DISTRITO_ID");
                    String CA_ING_QTA_CAT_EMPRESA = request.getParameter("ING_QTA_CAT_EMPRESA");
                    String CA_ING_QTA_CAT_RUC = request.getParameter("ING_QTA_CAT_RUC");
                    String CA_ING_QTA_CAT_OTRAS_EMPRESAS = request.getParameter("ING_QTA_CAT_OTRAS_EMPRESAS");
                    String CM_OBSERVACIONES = request.getParameter("OBSERVACIONES");
                    String US_CREACION = iduser;
                    String FE_CREACION = request.getParameter("FECHA_CREACION");
                    String US_MODIF = request.getParameter("US_MODIF");
                    String FE_MODIF = request.getParameter("FE_MODIF");
                    String IP_USUARIO = request.getParameter("USUARIO_IP");

                    String AP_NOMBRES_PADRE = request.getParameter("APELLIDOS_NOMBRES_PADRE");
                    String AP_NOMBRES_MADRE = request.getParameter("APELLIDOS_NOMBRES_MADRE");
                    String ES_TRABAJA_UPEU_C = request.getParameter("TRABAJA_UPEU_CONYUGUE");
                    String AP_NOMBRES_C = request.getParameter("APELLIDO_NOMBRES_CONYUGUE");
                    String FE_NAC_C = request.getParameter("FECHA_NAC_CONYUGUE");
                    String ID_TIPO_DOC_C = request.getParameter("TIPO_DOC_ID");
                    String NU_DOC_C = request.getParameter("NRO_DOC_C");
                    String LI_INSCRIPCION_VIG_ESSALUD_C = request.getParameter("INSCRIPCION_VIG_ESSALUD");
                    String ID_CONYUGUE = request.getParameter("CONYUGUE");
                    int num_hijo = Integer.parseInt(request.getParameter("num_hijo"));
                    //REGISTRAR EN TABLA CUENTA SUELDO
                    String NO_BANCO = request.getParameter("BANCO");
                    String NU_CUENTA = (request.getParameter("CUENTA") == null) ? "no tiene" : request.getParameter("CUENTA");
                    String NU_CUENTA_BANC = request.getParameter("CUENTA_BANC");
                    String ES_GEM_NU_CUENTA = (request.getParameter("GEN_NU_CUEN") == null) ? "0" : "1";
                    String NO_BANCO_OTROS = request.getParameter("BANCO_OTROS");
                    String ES_CUENTA_SUELDO = request.getParameter("ES_CUENTA_SUELDO");
                    String CO_UNIVERSITARIO = request.getParameter("COD_UNI");

                    String ES_DIEZMO = "0";
                    if (request.getParameter("diezmo") != null) {
                        ES_DIEZMO = "1";
                    } else {
                        ES_DIEZMO = "0";
                    }

                    //VALIDACIONES CUENTA SUELDO
                    if (NO_BANCO.equals("") && ES_GEM_NU_CUENTA.equals("0")) {
                        ES_CUENTA_SUELDO = "0";
                    } else {
                        ES_CUENTA_SUELDO = "1";
                    }

                    if (tr.val_nu_doc(NU_DOC)) {
//                        out.print("Trabajador ya existe!");
                    } else {

                        FE_NAC = DateFormat.toFormat3(FE_NAC);
                        FE_NAC_C = DateFormat.toFormat3(FE_NAC_C);

//                        out.println(" fecha nac :" + DateFormat.toFormat1(FE_NAC));
//                        out.println(" fecha nac C :" + DateFormat.toFormat1(FE_NAC_C));

                        tr.INSERT_TRABAJADOR(null, AP_PATERNO, AP_MATERNO, NO_TRABAJADOR, TI_DOC, NU_DOC, ES_CIVIL, FE_NAC, ID_NACIONALIDAD, ID_DEPARTAMENTO, ID_PROVINCIA, ID_DISTRITO, TE_TRABAJADOR, CL_TRA, DI_CORREO_PERSONAL, DI_CORREO_INST, CO_SISTEMA_PENSIONARIO, LI_NIVEL_EDUCATIVO, REGIMEN, ES_INST_PERU, CARRERA, DE_ANNO_EGRESO, CM_OTROS_ESTUDIOS, ES_SEXO, LI_GRUPO_SANGUINEO, DE_REFERENCIA, LI_RELIGION, NO_IGLESIA, DE_CARGO, LI_AUTORIDAD, NO_AP_AUTORIDAD, CL_AUTORIDAD, ID_NO_AFP, ES_AFILIADO_ESSALUD, LI_TIPO_TRABAJADOR, CA_TIPO_HORA_PAGO_REFEERENCIAL, ES_FACTOR_RH, LI_DI_DOM_A_D1, DI_DOM_A_D2, LI_DI_DOM_A_D3, DI_DOM_A_D4, LI_DI_DOM_A_D5, DI_DOM_A_D6, DI_DOM_A_REF, ID_DI_DOM_A_DISTRITO, LI_DI_DOM_LEG_D1, DI_DOM_LEG_D2, LI_DI_DOM_LEG_D3, DI_DOM_LEG_D4, LI_DI_DOM_LEG_D5, DI_DOM_LEG_D6, ID_DI_DOM_LEG_DISTRITO, CA_ING_QTA_CAT_EMPRESA, CA_ING_QTA_CAT_RUC, CA_ING_QTA_CAT_OTRAS_EMPRESAS, CM_OBSERVACIONES, US_CREACION, FE_CREACION, US_MODIF, FE_MODIF, IP_USUARIO, AP_NOMBRES_PADRE, AP_NOMBRES_MADRE,
                                ((ES_TRABAJA_UPEU_C!=null)? ES_TRABAJA_UPEU_C : "0"), AP_NOMBRES_C, FE_NAC_C, ID_TIPO_DOC_C, NU_DOC_C, LI_INSCRIPCION_VIG_ESSALUD_C,
                                ID_CONYUGUE, CO_UNIVERSITARIO, ES_DIEZMO);
                        String idtr = tr.MAX_ID_DATOS_TRABAJADOR();
                        tr.INSERT_CUENTA_SUELDO(null, NO_BANCO, NU_CUENTA, NU_CUENTA_BANC, ES_GEM_NU_CUENTA, NO_BANCO_OTROS, idtr, ES_CUENTA_SUELDO);
                        tr.INSERT_HIST_RELIGION(null, LI_RELIGION, NO_IGLESIA, DE_CARGO, LI_AUTORIDAD, NO_AP_AUTORIDAD, CL_AUTORIDAD, "1", idtr, iduser, FE_MODIF);
                        US_MODIF = iduser;
                        IP_USUARIO = tr.ip();
                        tr.UPDATE_ID_CONYUGUE(idtr, ID_CONYUGUE, US_MODIF, IP_USUARIO);
                        for (int i = 1; i <= num_hijo; i++) {
                            String AP_PATERNO_H = request.getParameter("APELLIDO_P_H" + i);
                            String AP_MATERNO_H = request.getParameter("APELLIDO_M_H" + i);
                            String NO_HIJO_TRABAJADOR = request.getParameter("NOMBRE_H" + i);
                            String FE_NACIMIENTO = request.getParameter("FECHA_NAC_H" + i);
                            String ES_SEXO_H = request.getParameter("SEXO_H" + i);
                            String ES_TIPO_DOC = request.getParameter("TIPO_DOC_ID_H" + i);
                            String NU_DOC_H = request.getParameter("NRO_DOC_H" + i);
                            String ES_PRESENTA_DOCUMENTO = null;
                            String ES_INSCRIPCION_VIG_ESSALUD = request.getParameter("ESSALUD_H" + i);
                            String ES_ESTUDIO_NIV_SUPERIOR = request.getParameter("EST_SUP_H" + i);
                            String ES_DATOS_HIJO_TRABAJADOR = "1";
                            if (NU_DOC_H != null) {
                                if (!NU_DOC_H.equals("")) {
                                    FE_NACIMIENTO = DateFormat.toFormat3(FE_NACIMIENTO);
                                    h.INSERT_DATOS_HIJO_TRABAJADOR(null, idtr, AP_PATERNO_H, AP_MATERNO_H, NO_HIJO_TRABAJADOR, FE_NACIMIENTO, ES_SEXO_H, ES_TIPO_DOC, NU_DOC_H, ES_PRESENTA_DOCUMENTO, ES_INSCRIPCION_VIG_ESSALUD, ES_ESTUDIO_NIV_SUPERIOR, US_CREACION, FE_CREACION, US_MODIF, FE_MODIF, IP_USUARIO, ES_DATOS_HIJO_TRABAJADOR);
                                }
                            }
                        }
                        session.setAttribute("List_Hijos", d.List_Hijos(idtr));
                        session.setAttribute("Documentos", d.Documentos());
                        session.setAttribute("Lis_doc_trabajador", d.Lis_doc_trabajador(idtr));
                        session.setAttribute("List_Conyugue", d.List_Conyugue(idtr));
                        int s = d.List_Req_nacionalidad(idtr);
                        int num_ad = d.List_Adventista(idtr);
                        int count = d.count_documentos_x_tra(idtr);
                        if (count > 0) {
                           response.sendRedirect("Reg_Documento?pro=regTR&idtr=" + idtr);
                        } else {
                            response.sendRedirect("views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr + "&pro=pr_dgp&P2=TRUE&dt=ok");
                        }
                    }
                }

                if ("Documento_Trabajador".equals(opc)) {
                    String idtr = request.getParameter("idtr");
                    session.setAttribute("Lis_doc_trabajador_hab", d.Lis_doc_trabajador_hab(idtr));
                    response.sendRedirect("views/Trabajador/List_Doc_Trabajador.html?idtr=" + idtr);
                }
                if ("aut".equals(opc)) {
                    String idtr = request.getParameter("idtr");
                    int val_aps = 0;
                    int val_huella = 0;
                    String iddgp = request.getParameter("iddetalle_dgp");
                    String puesto_id = request.getParameter("puesto_id");
                    String cod = request.getParameter("cod");
                    String idpasos = request.getParameter("idpasos");
                    String drp = request.getParameter("IDDETALLE_REQ_PROCESO");
                    String np = request.getParameter("nup");
                    int num_c_dgp = dgp.VALIDAR_DGP_CONTRATO(iddgp);
                    val_aps = em.val_cod_aps_empleado(idtr);
                    val_huella = em.val_cod_huella(idtr);
                    session.setAttribute("id_empleadox_ide", em.id_empleadox_ide(idtr));
                    session.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                    session.setAttribute("List_Auto_mostrar", li.List_Auto_mostrar(idrol));
                    session.setAttribute("List_", li.List_Auto_mostrar(idrol));

                    response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?idtr=" + idtr.trim() + "&aut=1&dgp=" + iddgp + "&p=" + puesto_id + "&c=" + cod + "&pas=" + idpasos + "&drp=" + drp + "&np=" + np + "&vnc=" + num_c_dgp + "&val_aps=" + val_aps + "&val_huella=" + val_huella);
                }

                if ("reg_aps_masivo".equals(opc)) {
                    String idtr = request.getParameter("idtr");
                    int co_aps = Integer.parseInt(request.getParameter("cod"));
                    em.Reg_aps(idtr, co_aps);
                    session.setAttribute("id_empleadox_ide", em.id_empleadox_ide(idtr));
                    rpta.put("rpta", true);
                    // response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?idtr=" + idtr + "");
                }
                if ("registrar_huella".equals(opc)) {
                    String idtr = request.getParameter("idtr");
                    int co_huella = Integer.parseInt(request.getParameter("cod"));
                    em.Reg_cod_huella(idtr, co_huella);
                    rpta.put("rpta", true);
                }

                if (opc.equals("Form_Cambiar_Clave")) {
                    response.sendRedirect("Cambiar_Pwd");
                }
                if (opc.equals("Val_num_Doc")) {
                    String nu_doc = request.getParameter("doc");
                    rpta.put("rpta", "1");
                    rpta.put("nu_doc", tr.val_nu_doc(nu_doc));
                }

                if (opc.equals("reg_trb")) {
                    response.sendRedirect("Ficha_Trabajador.jsp");
                }
                if (opc.equals("validar_cod_uni")) {
                    String cod_uni = request.getParameter("cod_uni");
                    int n = tr.cod_uni_unico(cod_uni);
                    rpta.put("rpta", "1");
                    rpta.put("cod", n);
                }

            } catch (Exception e) {
                rpta.put("rpta", "-1");
                rpta.put("mensaje", e.getMessage());
            } finally {
                Gson gson = new Gson();
//                out.print(gson.toJson(rpta));
//                out.flush();
//                out.close();
            }
        } else {
             response.sendRedirect("/TALENTO_HUMANO/");
        }
    }
}
