package com.app.web.controller.recruitment.contract;

import com.app.model.Contrato;
import com.app.properties.UserMachineProperties;
import com.app.util.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.AreaDAO;
import com.app.dao.Centro_CostoDAO;
import com.app.dao.ContratoDAO;
import com.app.dao.Datos_Hijo_TrabajadorDAO;
import com.app.dao.Detalle_Centro_Costo_DAO;
import com.app.dao.DgpDAO;
import com.app.dao.DireccionDAO;
import com.app.dao.DocumentoDAO;
import com.app.dao.EmpleadoDAO;
import com.app.dao.FuncionDAO;
import com.app.dao.GrupoOcupacionesDAO;
import com.app.dao.HorarioDAO;
import com.app.dao.ListaDAO;
import com.app.dao.PlantillaDAO;
import com.app.dao.PuestoDAO;
import com.app.dao.Sub_ModalidadDAO;
import com.app.dao.TrabajadorDAO;
import com.app.dao.UsuarioDAO;
import com.app.dao_imp.InterfaceAreaDAO;
import com.app.dao_imp.InterfaceCentro_CostosDAO;
import com.app.dao_imp.InterfaceContratoDAO;
import com.app.dao_imp.InterfaceDatos_Hijo_Trabajador;
import com.app.dao_imp.InterfaceDetalle_Centro_Costo;
import com.app.dao_imp.InterfaceDgpDAO;
import com.app.dao_imp.InterfaceDireccionDAO;
import com.app.dao_imp.InterfaceDocumentoDAO;
import com.app.dao_imp.InterfaceEmpleadoDAO;
import com.app.dao_imp.InterfaceFuncionDAO;
import com.app.dao_imp.InterfaceGrupo_ocupacionesDAO;
import com.app.dao_imp.InterfaceHorarioDAO;
import com.app.dao_imp.InterfaceListaDAO;
import com.app.dao_imp.InterfacePlantillaDAO;
import com.app.dao_imp.InterfacePuestoDAO;
import com.app.dao_imp.InterfaceSub_ModalidadDAO;
import com.app.dao_imp.InterfaceTrabajadorDAO;
import com.app.dao_imp.InterfaceUsuarioDAO;

/**
 *
 * @author Jair
 */
@RestController
@RequestMapping("contrato")
public class CContrato {

    InterfaceDgpDAO dgp = new DgpDAO();
    InterfacePuestoDAO puesto = new PuestoDAO();
    InterfaceHorarioDAO horarioDAO = new HorarioDAO();
    InterfaceDatos_Hijo_Trabajador dht = new Datos_Hijo_TrabajadorDAO();
    InterfaceContratoDAO con = new ContratoDAO();
    InterfaceListaDAO l = new ListaDAO();
    InterfaceDetalle_Centro_Costo dcc = new Detalle_Centro_Costo_DAO();
    InterfaceAreaDAO area = new AreaDAO();
    InterfaceEmpleadoDAO emp = new EmpleadoDAO();
    InterfacePlantillaDAO pl = new PlantillaDAO();
    InterfaceUsuarioDAO usu = new UsuarioDAO();
    InterfaceTrabajadorDAO tr = new TrabajadorDAO();
    InterfaceCentro_CostosDAO cc = new Centro_CostoDAO();
    InterfaceDireccionDAO dir = new DireccionDAO();
    InterfaceGrupo_ocupacionesDAO gr = new GrupoOcupacionesDAO();
    InterfaceSub_ModalidadDAO sub = new Sub_ModalidadDAO();
    InterfaceDocumentoDAO doc = new DocumentoDAO();
    InterfaceFuncionDAO fu = new FuncionDAO();


    @GetMapping
    public ResponseEntity<?> read(HttpServletRequest request){

        Map<String, Object> rpta = new HashMap<String, Object>();

        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String iduser = (String) sesion.getAttribute("IDUSER");
        String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");

        if (iduser != null) {
            if (opc != null) {

                if (opc.equals("Detalle_Contractual")) {
                    String idtr = request.getParameter("idtr");
                    String id_cto = con.Contrato_max(idtr);
                    if (id_cto != null) {
                        String id_pu = puesto.puesto(id_cto);
                        sesion.setAttribute("List_x_fun_x_idpu", fu.List_x_fun_x_idpu(id_pu));
                        sesion.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(id_cto));
                        sesion.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(id_cto));
                        sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                        sesion.setAttribute("List_Usuario", usu.List_Usuario());
                        sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                        sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                    } else {
                        sesion.removeAttribute("List_x_fun_x_idpu");
                        sesion.removeAttribute("Lis_c_c_id_contr");
                        sesion.removeAttribute("List_contra_x_idcto");
                        sesion.removeAttribute("List_Situacion_Actual");
                        sesion.removeAttribute("List_Usuario");
                        sesion.removeAttribute("list_Condicion_contrato");
                        sesion.removeAttribute("List_tipo_contrato");
                        sesion.removeAttribute("list_reg_labo");
                    }
                    ///response.sendRedirect("views/Contrato/Detalle_Info_Contractualq.html?idtr=" + idtr + "&id_cto=" + id_cto);
                }
                if (opc.equals("SelectorListaContrato")) {
                    String id_Trabajador = request.getParameter("idtr");
                    String idc = request.getParameter("idc");
                    String html = "";
                    List<Contrato> lista = con.ListaSelectorContrato(id_Trabajador);
                    html += "<select name='ida' class='select anno form-control'>";
                    for (Contrato lista1 : lista) {
                        lista1.getCa_asig_familiar();
                        if (idc.equals(lista1.getId_contrato())) {
                            html += "<option selected='' value='" + lista1.getId_contrato() + "'>De " + lista1.getFe_desde() + " Hasta " + ((lista1.getFe_hasta() != null) ? lista1.getFe_hasta() : " Indefinido") + "</option>";
                        } else {
                            html += "<option  value='" + lista1.getId_contrato() + "'>De " + lista1.getFe_desde() + " Hasta " + ((lista1.getFe_hasta() != null) ? lista1.getFe_hasta() : " Indefinido") + "</option>";
                        }
                    }
                    html += "</select>";
                    rpta.put("html", html);
                    rpta.put("rpta", true);
                }

                if (opc.equals("List_ti_contrato")) {
                    rpta.put("lista", l.List_tipo_contrato());
                    rpta.put("rpta", 1);
                }

                if (opc.equals("LIST_FORMULARIO")) {

                    String US_CREACION = iduser;
                    String idtr = request.getParameter("idtr");
                    String nom = request.getParameter("nom");
                    int cant_hijos = dht.ASIGNACION_F(idtr);
                    String co_aps = "";
                    String co_hu = "";
                    if (idtr != null) {
                        co_aps = tr.Cod_aps_x_idt(idtr);
                        co_hu = tr.Cod_huella_x_idt(idtr);
                        if (co_aps != null) {
                        } else {
                            co_aps = "--";
                        }
                        if (co_hu != null) {
                        } else {
                            co_hu = "--";
                        }
                    }
                    Calendar fecha = new GregorianCalendar();
                    int a??o = fecha.get(Calendar.YEAR);
                    int mes = fecha.get(Calendar.MONTH);
                    int dia = fecha.get(Calendar.DAY_OF_MONTH);
                    String fe_subs = "";
                    if (mes < 9 && dia < 9) {
                        fe_subs = a??o + "-" + "0" + (mes + 1) + "-" + "0" + dia;
                    }

                    if (mes < 9 && dia > 9) {
                        fe_subs = a??o + "-" + "0" + (mes + 1) + "-" + dia;
                    }

                    if (mes >= 9 && dia < 9) {
                        fe_subs = a??o + "-" + (mes + 1) + "-" + "0" + dia;
                    }
                    if (mes >= 9 && dia > 9) {
                        fe_subs = a??o + "-" + (mes + 1) + "-" + dia;
                    }
                    sesion.setAttribute("Listar_Direccion", dir.Listar_Direccion());
                    sesion.setAttribute("List_Jefe", l.List_Jefe());
                    sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                    sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                    sesion.setAttribute("List_modalidad", con.List_modalidad());
                    sesion.setAttribute("List_Puesto", puesto.List_Puesto());
                    sesion.setAttribute("List_ID_User", usu.List_ID_User(US_CREACION));
                    sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                    sesion.setAttribute("List_grup_ocu", gr.List_grup_ocu());
                    sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                    ///response.sendRedirect("views/Contrato/Reg_Casos_Especiales.html?idtr=" + idtr + "&nom=" + nom + "&cant_hijos=" + cant_hijos + "&fe_subs=" + fe_subs + "&co_ap=" + co_aps + "&co_hu=" + co_hu);
                }

                if (opc.equals("Ver_Plantilla")) {
                    String idc = request.getParameter("idc");
                    sesion.setAttribute("LIST_DAT_TR_PLANTILLA", tr.LIST_DAT_TR_PLANTILLA(idc));
                    ///response.sendRedirect("views/Contrato/Plantilla/Editor_Plantilla.html");
                }

                if (opc.equals("Ver Plantilla")) {
                    String idc = request.getParameter("idc");
                    sesion.setAttribute("LIST_DAT_TR_PLANTILLA", tr.LIST_DAT_TR_PLANTILLA(idc));
                    ///response.sendRedirect("views/Contrato/Plantilla/Editor_Plantilla.html");
                }

                if (opc.equals("REG_CASOS_ESP")) {

                    String ID_CONTRATO = "";
                    String ID_DGP = "";
                    String ID_PUESTO = request.getParameter("PUESTO_ID");
                    String ID_FUNC = "";
                    String ID_TRABAJADOR = request.getParameter("IDDATOS_TRABAJADOR");
                    String ID_TIPO_PLANILLA = request.getParameter("TIPO_PLANILLA");
                    String ID_REGIMEN_LABORAL = request.getParameter("REG_LAB_MINTRA");
                    String ID_MODALIDAD = request.getParameter("MODALIDAD");
                    if (!ID_MODALIDAD.equals("")) {
                        ID_MODALIDAD = request.getParameter("MODALIDAD");
                    } else {
                        ID_MODALIDAD = "MOD-0004";
                    }
                    String ID_SUB_MODALIDAD = request.getParameter("SUB_MODALIDAD").trim();
                    if (!ID_SUB_MODALIDAD.equals("")) {
                        ID_SUB_MODALIDAD = request.getParameter("SUB_MODALIDAD").trim();
                    } else {
                        ID_SUB_MODALIDAD = "SMD-0011";
                    }
                    String FE_DESDE = request.getParameter("FEC_DESDE");
                    String FE_HASTA = request.getParameter("FEC_HASTA");
                    String LI_CONDICION = request.getParameter("CONDICION");
                    Double CA_SUELDO = Double.parseDouble(request.getParameter("SUELDO"));
                    Double CA_REINTEGRO = Double.parseDouble(request.getParameter("REINTEGRO"));
                    Double CA_BONO_ALIMENTO = Double.parseDouble(request.getParameter("BONO_ALIMENTO"));
                    Double CA_BEV = Double.parseDouble(request.getParameter("BEV"));
                    Double CA_SUELDO_TOTAL = Double.parseDouble(request.getParameter("TOTAL_SUELDO"));
                    String TI_HORA_PAGO = request.getParameter("TIPO_HORA_PAGO");
                    Double CA_ASIG_FAMILIAR = Double.parseDouble(request.getParameter("ASIG_FAMILIAR"));
                    String ES_TI_CONTRATACION = request.getParameter("TI_CONTRATACION");
                    String CO_GR_OCUPACION = request.getParameter("CO_GRUPO_OCU");
                    String FE_SUSCRIPCION = request.getParameter("FECHA_SUSCRIPCION");
                    String CO_TI_MONEDA = request.getParameter("TIPO_MONEDA");
                    String CO_TI_REM_VARIAB = request.getParameter("REM_VARIABLE");
                    String DE_REMU_ESPECIE = request.getParameter("REM_ESPECIE");
                    Double HO_SEMANA = Double.parseDouble(request.getParameter("HORAS_SEMANA"));
                    Double NU_HORAS_LAB = Double.parseDouble(request.getParameter("NRO_HORAS_LAB"));
                    Double DIA_CONTRATO = Double.parseDouble(request.getParameter("DIAS"));
                    String TI_TRABAJADOR = request.getParameter("TIPO_TRABAJADOR");
                    String LI_REGIMEN_LABORAL = request.getParameter("REGIMEN_LABORAL");
                    String ES_DISCAPACIDAD = request.getParameter("DISCAPACIDAD");
                    String LI_REGIMEN_PENSIONARIO = request.getParameter("REGIMEN_PENSIONARIO");
                    String TI_CONTRATO = request.getParameter("TIPO_CONTRATO");
                    String LI_TIPO_CONVENIO = request.getParameter("TIPO_CONVENIO");
                    String DE_OBSERVACION = request.getParameter("OBSERVACION");
                    String ID_FILIAL = request.getParameter("FILIAL");
                    String DE_RUC_EMP_TRAB = request.getParameter("EMP_RUC");
                    String CO_SUCURSAL = request.getParameter("SUCURSAL");
                    String DE_MYPE = request.getParameter("MYPE");
                    String ES_ENTREGAR_DOC_REGLAMENTOS = request.getParameter("ENTREGAR_DOC_REGLAMENTOS");
                    String ES_REGISTRO_HUELLA = request.getParameter("REGISTRO_HUELLA");
                    String DE_REGISTRO_SISTEM_REMU = request.getParameter("REGISTRO_SISTEM_REMU");
                    String ID_PLANTILLA_CONTRACTUAL = request.getParameter("id_plantilla_contractual");

                    String FE_CESE = null;
                    String ES_CONTRATO_TRABAJADOR = null;
                    String US_CREACION = iduser;
                    String FE_CREACION = request.getParameter("FECHA_CREACION");
                    String US_MODIF = request.getParameter("USER_MODIF");
                    String FE_MODIF = request.getParameter("FECHA_MODIF");
                    String US_IP = request.getParameter("USUARIO_IP");
                    String FE_VACACIO_INI = "";
                    String FE_VACACIO_FIN = "";
                    String ES_CONTRATO = null;
                    String ES_FIRMO_CONTRATO = "1";
                    Double NU_CONTRATO = 0.0;

                    String ES_APOYO = "";

                    String NU_DOCUMENTO = "";

                    String ES_REMUNERACION_PROCESADO = null;
                    Double ca_bonificacion_p = Double.parseDouble(request.getParameter("ca_bono_puesto"));
                    String ES_MFL = request.getParameter("MFL");
                    List<String> dia = new ArrayList<String>();
                    dia.add("lun");
                    dia.add("mar");
                    dia.add("mie");
                    dia.add("jue");
                    dia.add("vie");
                    dia.add("sab");
                    dia.add("dom");

                    String ID_DETALLE_HORARIO = null;
                    String ES_DETALLE_HORARIO = "1";
                    String ES_HORARIO = "1";
                    String ID_TIPO_HORARIO = request.getParameter("ID_TIPO_HORARIO");
                    String ES_MOD_FARMATO = "1";
                    Double ca_ho_total = Double.parseDouble(request.getParameter("horas_totales"));
                    String PRACTICANTE = request.getParameter("PRACTICANTE");
                    if (PRACTICANTE == null) {
                        PRACTICANTE = "0";
                    }

                    ID_DETALLE_HORARIO = horarioDAO.Insert_Det_Hor_Casos_Esp(null, ID_DGP, ES_DETALLE_HORARIO, iduser, FE_CREACION, US_MODIF, FE_MODIF, ID_TIPO_HORARIO, ES_MOD_FARMATO, ca_ho_total);

                    for (int i = 0; i < dia.size(); i++) {
                        for (int j = 0; j < 10; j++) {
                            String hora_desde = request.getParameter("HORA_DESDE_" + dia.get(i) + j);
                            String hora_hasta = request.getParameter("HORA_HASTA_" + dia.get(i) + j);
                            String d = request.getParameter("DIA_" + dia.get(i) + j);

                            if (hora_desde != null & d != null & hora_hasta != null) {
                                if (!hora_hasta.equals("") & !hora_desde.equals("") & !d.equals("")) {
                                    horarioDAO.Insert_Horario(null, hora_desde, hora_hasta, d, ES_HORARIO, ID_DETALLE_HORARIO);
                                }
                            }
                        }
                    }
                    //System.out.println("FE_DESDE: " + FE_DESDE);
                    String situacionEspecial = request.getParameter("situacionEspecial");
                    FE_DESDE = DateFormat.toFormat3(FE_DESDE);
                    FE_HASTA = DateFormat.toFormat3(FE_HASTA);
                    System.out.println("FE_HASTA: " + FE_HASTA);
                    //    out.println("Nueva fecha :" + DateFormat.toFormat1(FE_HASTA));
                    con.INSERT_CONTRATO(ID_CONTRATO, ID_DGP, FE_DESDE, FE_HASTA, FE_CESE, ID_FUNC, LI_CONDICION, CA_SUELDO, CA_REINTEGRO, CA_ASIG_FAMILIAR, HO_SEMANA,
                            NU_HORAS_LAB, DIA_CONTRATO, TI_TRABAJADOR, LI_REGIMEN_LABORAL, ES_DISCAPACIDAD, TI_CONTRATO, LI_REGIMEN_PENSIONARIO, ES_CONTRATO_TRABAJADOR, US_CREACION,
                            FE_CREACION, US_MODIF, FE_MODIF, US_IP, FE_VACACIO_INI, FE_VACACIO_FIN, ES_CONTRATO, ID_FILIAL, ID_PUESTO, CA_BONO_ALIMENTO, LI_TIPO_CONVENIO, ES_FIRMO_CONTRATO, NU_CONTRATO,
                            DE_OBSERVACION, ES_APOYO, TI_HORA_PAGO, NU_DOCUMENTO, ES_ENTREGAR_DOC_REGLAMENTOS, ES_REGISTRO_HUELLA, DE_REGISTRO_SISTEM_REMU, ID_TRABAJADOR, CA_SUELDO_TOTAL, ID_REGIMEN_LABORAL,
                            ID_MODALIDAD, ID_SUB_MODALIDAD, CO_GR_OCUPACION, FE_SUSCRIPCION, CO_TI_MONEDA, CO_TI_REM_VARIAB, DE_REMU_ESPECIE, DE_RUC_EMP_TRAB, CO_SUCURSAL, DE_MYPE, ES_TI_CONTRATACION, CA_BEV,
                            ID_TIPO_PLANILLA, ES_REMUNERACION_PROCESADO, ID_DETALLE_HORARIO, ID_PLANTILLA_CONTRACTUAL, ca_bonificacion_p, ES_MFL, PRACTICANTE, situacionEspecial);
                    emp.VALIDAR_EMPLEADO(ID_TRABAJADOR);
                    //--------- COD HUELLA y APS ------------
                    String aps = request.getParameter("cod_aps");
                    if (!aps.equals("")) {
                        int cod_aps = Integer.parseInt(aps);
                        emp.Reg_aps(ID_TRABAJADOR, cod_aps);
                    }
                    String huella = request.getParameter("cod_hue");
                    if (!huella.equals("")) {
                        int cod_hue = Integer.parseInt(huella);
                        emp.Reg_cod_huella(ID_TRABAJADOR, cod_hue);
                    }
                    String idcto = con.MAX_ID_CONTRATO();
                    //--------- CENTRO COSTOS --------------
                    /*Actualizando centro de costo*/
                    System.out.println("::Agregando  Centro de costos....");
                    //--------- CENTRO COSTOS --------------
                    //  String IP_USUARIO = request.getParameter("USUARIO_IP");
                    int cant_cc = Integer.parseInt(request.getParameter("CANT"));
                    for (int g = 1; g <= cant_cc; g++) {
                        String ID_CENTRO_COSTO = request.getParameter("CENTRO_COSTOS_" + g);
                        double porcentaje = Double.parseDouble(request.getParameter("PORCENTAJE_" + g));
                        if (ID_CENTRO_COSTO != null && porcentaje != 0.0) {
                            dcc.INSERT_DETALLE_CENTRO_COSTO(null, null, porcentaje, "1", iduser, null, null, null, UserMachineProperties.getAll(), idcto, ID_CENTRO_COSTO);
                        }
                    }

                    //------------- HORARIO ------------
                    sesion.setAttribute("List_Jefe", l.List_Jefe());
                    sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                    sesion.setAttribute("List_ID_User", usu.List_ID_User(US_CREACION));
                    sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                    sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                    //   sesion.setAttribute("List_tipo_contrato", doc.List_Adventista(idcto));
                    sesion.setAttribute("Lis_doc_trabajador", doc.Lis_doc_trabajador(ID_TRABAJADOR));
                    // int i = doc.List_Req_nacionalidad(ID_TRABAJADOR);
                    //  int num_ad = doc.List_Adventista(ID_TRABAJADOR);
                    sesion.setAttribute("List_Hijos", doc.List_Hijos(ID_TRABAJADOR));
                    sesion.setAttribute("List_Conyugue", doc.List_Conyugue(ID_TRABAJADOR));
                    ///response.sendRedirect("views/Dgp/Documento/Reg_Documento.html?pro=casosEspeciales&idtr=" + ID_TRABAJADOR + "&P2=TRUE&ms=ok");
                }

                if (opc.equals("Reporte_CE")) {
                    sesion.setAttribute("List_Casos_Esp", con.LIST_CASOS_ESPECIALES());
                    ///response.sendRedirect("views/Contrato/Filtro_Contrato_CE.html");
                }

                if (opc.equals("Buscar")) {
                    String Buscar = request.getParameter("busqueda");
                    String dni = request.getParameter("dni");
                    String nom = request.getParameter("nom");
                    String ape_mat = request.getParameter("ape_mat");
                    String ape_pat = request.getParameter("ape_pat");
                    if (("Buscar".equals(Buscar) & (!"".equals(dni) | !"".equals(nom) | !"".equals(ape_mat) | !"".equals(ape_pat)))) {
                        String busc = (String) request.getParameter("busc");
                        if (busc != null) {
                            sesion.setAttribute("ListarTrabajador2", tr.Buscar_Ficha_Trabajador(iddep, dni, nom, ape_pat, ape_mat));
                            ///getServletContext().setAttribute(nom, dgp.VAL_OPC_DGP(dni));
                            ///response.sendRedirect("views/Contrato/Gen_Contrato_CE.html");
                        }
                    } else {
                        ///response.sendRedirect("views/Contrato/Gen_Contrato_CE.html");

                    }
                }

                if (opc.equals("Buscar")) {
                    sesion.setAttribute("List_Area", area.List_Area());
                }

                if (opc.equals("filtrar")) {
                    sesion.setAttribute("Listar_Direccion", dir.Listar_Direccion());
                    sesion.setAttribute("List_Area_ID", area.List_Area_ID(iddep));
                    ///response.sendRedirect("views/Contrato/Busc_Contrato.html");
                }

            }
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request){

        Map<String, Object> rpta = new HashMap<String, Object>();

        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String iduser = (String) sesion.getAttribute("IDUSER");
        //String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");

        try {
            if (iduser != null) {
                if (opc != null) {

                    if (opc.equals("casos_especiales")) {
                        ///response.sendRedirect("views/Contrato/Gen_Contrato_CE.html");
                    }
                    if (opc.equals("enviar")) {
                        String iddgp = request.getParameter("iddgp");
                        String idtr = request.getParameter("idtr");
                        String id_dir = puesto.List_Puesto_x_iddgp(iddgp);
                        sesion.setAttribute("LIST_ID_DGP", dgp.LIST_ID_DGP(iddgp));
                        sesion.setAttribute("List_Puesto", puesto.List_Puesto());
                        sesion.setAttribute("Listar_Direccion", dir.Listar_Direccion());
                        sesion.setAttribute("List_modalidad", con.List_modalidad());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                        sesion.setAttribute("List_centro_costo", cc.List_centro_costo());
                        sesion.setAttribute("List_grup_ocu", gr.List_grup_ocu());
                        int num = dht.ASIGNACION_F(idtr);
                        Calendar fecha = new GregorianCalendar();
                        int a??o = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH);
                        int dia = fecha.get(Calendar.DAY_OF_MONTH);
                        String fe_subs = "";
                        if (mes < 9 && dia < 9) {
                            fe_subs = a??o + "-" + "0" + (mes + 1) + "-" + "0" + dia;
                        }

                        if (mes < 9 && dia > 9) {
                            fe_subs = a??o + "-" + "0" + (mes + 1) + "-" + dia;
                        }

                        if (mes >= 9 && dia < 9) {
                            fe_subs = a??o + "-" + (mes + 1) + "-" + "0" + dia;
                        }
                        if (mes >= 9 && dia > 9) {
                            fe_subs = a??o + "-" + (mes + 1) + "-" + dia;
                        }
                        ///response.sendRedirect("views/Contrato/Reg_Contrato.html?num=" + num + "&id_direc=" + id_dir + "&fe_subs=" + fe_subs);
                    }
                    if (opc.equals("Editar")) {
                        String idcon = request.getParameter("idc");
                        String idtr = request.getParameter("idtr");
                        sesion.setAttribute("List_contrato", con.List_contrato(idcon));
                        sesion.setAttribute("Listar_Direccion", dir.Listar_Direccion());
                        sesion.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(idcon));
                        sesion.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(idcon));
                        sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                        sesion.setAttribute("List_Usuario", usu.List_Usuario());
                        sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                        sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                        sesion.setAttribute("List_modalidad", con.List_modalidad());
                        sesion.setAttribute("List_grup_ocu", gr.List_grup_ocu());
                        String id_dg = request.getParameter("id_dg");
                        String id_dir = puesto.List_Puesto_x_id_con(idcon);
                        String id_modalidad = sub.id_mod_x_id_con(idcon);
                        int num_cc = cc.count_cc_x_id_cont(idcon);
                        int num = dht.ASIGNACION_F(idtr);

                        if (id_dg != null) {
                            sesion.setAttribute("LIST_ID_DGP", dgp.LIST_ID_DGP(id_dg));
                        }
                        Calendar fecha = new GregorianCalendar();
                        int a??o = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH);
                        int dia = fecha.get(Calendar.DAY_OF_MONTH);
                        String fe_subs = "";
                        if (mes < 9 && dia < 9) {
                            fe_subs = a??o + "-" + "0" + (mes + 1) + "-" + "0" + dia;
                        }

                        if (mes < 9 && dia > 9) {
                            fe_subs = a??o + "-" + "0" + (mes + 1) + "-" + dia;
                        }

                        if (mes >= 9 && dia < 9) {
                            fe_subs = a??o + "-" + (mes + 1) + "-" + "0" + dia;
                        }
                        if (mes >= 9 && dia > 9) {
                            fe_subs = a??o + "-" + (mes + 1) + "-" + dia;

                        }

                        /// response.sendRedirect("views/Contrato/Editar_Contrato.html?num=" + num + "&id_direc=" + id_dir + "&fe_subs=" + fe_subs + "&id_mod=" + id_modalidad + "&num_cc=" + num_cc);
                    }
                    if (opc.equals("MODIFICAR CONTRATO")) {
                        String ID_CONTRATO = request.getParameter("id_contrato");
                        String ID_DGP = request.getParameter("IDDETALLE_DGP");
                        String FE_DESDE = request.getParameter("FEC_DESDE");
                        String FE_HASTA = request.getParameter("FEC_HASTA");
                        String FE_CESE = null;
                        String ID_FUNC = "";
                        String LI_CONDICION = request.getParameter("CONDICION");
                        Double CA_SUELDO;
                        if (request.getParameter("SUELDO") != null) {
                            CA_SUELDO = Double.parseDouble(request.getParameter("SUELDO"));
                        } else {
                            CA_SUELDO = 0.0;
                        }
                        Double CA_REINTEGRO = Double.parseDouble(request.getParameter("REINTEGRO"));
                        Double CA_ASIG_FAMILIAR = Double.parseDouble(request.getParameter("ASIG_FAMILIAR"));
                        Double HO_SEMANA = Double.parseDouble(request.getParameter("HORAS_SEMANA"));
                        Double NU_HORAS_LAB = Double.parseDouble(request.getParameter("NRO_HORAS_LAB"));
                        Double DIA_CONTRATO = Double.parseDouble(request.getParameter("DIAS"));

                        String TI_TRABAJADOR = request.getParameter("TIPO_TRABAJADOR");
                        String LI_REGIMEN_LABORAL = request.getParameter("REGIMEN_LABORAL");
                        String ES_DISCAPACIDAD = request.getParameter("DISCAPACIDAD");
                        String TI_CONTRATO = request.getParameter("TIPO_CONTRATO");
                        String LI_REGIMEN_PENSIONARIO = request.getParameter("REGIMEN_PENSIONARIO");
                        String ES_CONTRATO_TRABAJADOR = null;
                        String US_CREACION = iduser;
                        String FE_CREACION = request.getParameter("FECHA_CREACION");
                        String US_MODIF = request.getParameter("USER_MODIF");
                        String FE_MODIF = request.getParameter("FECHA_MODIF");
                        String US_IP = request.getParameter("USUARIO_IP");
                        String FE_VACACIO_INI = "";
                        String FE_VACACIO_FIN = "";
                        String ES_CONTRATO = null;
                        String ID_FILIAL = request.getParameter("FILIAL");
                        String ID_PUESTO = request.getParameter("PUESTO_ID");
                        Double CA_BONO_ALIMENTO = 0.0;
                        String LI_TIPO_CONVENIO = request.getParameter("TIPO_CONVENIO");
                        String ES_FIRMO_CONTRATO = "0";
                        Double NU_CONTRATO = 0.0;

                        String DE_OBSERVACION = request.getParameter("OBSERVACION");
                        String ES_APOYO = "";

                        String TI_HORA_PAGO = request.getParameter("TIPO_HORA_PAGO");
                        String NU_DOCUMENTO = "";

                        String ES_ENTREGAR_DOC_REGLAMENTOS = request.getParameter("ENTREGAR_DOC_REGLAMENTOS");
                        String ES_REGISTRO_HUELLA = request.getParameter("REGISTRO_HUELLA");
                        String DE_REGISTRO_SISTEM_REMU = request.getParameter("REGISTRO_SISTEM_REMU");
                        String ID_TRABAJADOR = request.getParameter("IDDATOS_TRABAJADOR");
                        Double CA_SUELDO_TOTAL = 0.0;
                        String ID_REGIMEN_LABORAL = request.getParameter("REG_LAB_MINTRA");
                        String ID_MODALIDAD = request.getParameter("MODALIDAD");
                        String ID_SUB_MODALIDAD = request.getParameter("SUB_MODALIDAD").trim();
                        String CO_GR_OCUPACION = request.getParameter("CO_GRUPO_OCU");
                        String FE_SUSCRIPCION = request.getParameter("FECHA_SUSCRIPCION");
                        String CO_TI_MONEDA = request.getParameter("TIPO_MONEDA");
                        String CO_TI_REM_VARIAB = request.getParameter("REM_VARIABLE");
                        String DE_REMU_ESPECIE = request.getParameter("REM_ESPECIE");
                        String DE_RUC_EMP_TRAB = request.getParameter("EMP_RUC");
                        String CO_SUCURSAL = request.getParameter("SUCURSAL");
                        String DE_MYPE = request.getParameter("MYPE");
                        String ES_TI_CONTRATACION = request.getParameter("TI_CONTRATACION");
                        Double CA_BEV = 0.0;
                        String ID_TIPO_PLANILLA = request.getParameter("TIPO_PLANILLA");
                        String ES_REMUNERACION_PROCESADO = null;
                        String ID_HORARIO = request.getParameter("HORARIO");
                        String ID_PLANTILLA_CONTRACTUAL = request.getParameter("id_plantilla_contractual");
                        Double ca_bonificacion_p = 0.0;
                        String PRACTICANTE = request.getParameter("PRACTICANTE");
                        if (PRACTICANTE == null) {
                            PRACTICANTE = "0";
                        }
                        FE_DESDE = DateFormat.toFormat3(FE_DESDE);
                        FE_HASTA = DateFormat.toFormat3(FE_HASTA);
                        String situacionEspecial = request.getParameter("situacionEspecial");
                        con.MODIFICAR_CONTRATO(ID_CONTRATO, ID_DGP, FE_DESDE, FE_HASTA, FE_CESE, ID_FUNC, LI_CONDICION, CA_SUELDO, CA_REINTEGRO, CA_ASIG_FAMILIAR, HO_SEMANA, NU_HORAS_LAB, DIA_CONTRATO,
                                TI_TRABAJADOR, LI_REGIMEN_LABORAL, ES_DISCAPACIDAD, TI_CONTRATO, LI_REGIMEN_PENSIONARIO, ES_CONTRATO_TRABAJADOR, US_CREACION, FE_CREACION, US_MODIF, FE_MODIF,
                                US_IP, FE_VACACIO_INI, FE_VACACIO_FIN, ES_CONTRATO, ID_FILIAL, ID_PUESTO, CA_BONO_ALIMENTO, LI_TIPO_CONVENIO, ES_FIRMO_CONTRATO, NU_CONTRATO, DE_OBSERVACION,
                                ES_APOYO, TI_HORA_PAGO, NU_DOCUMENTO, ES_ENTREGAR_DOC_REGLAMENTOS, ES_REGISTRO_HUELLA, DE_REGISTRO_SISTEM_REMU, ID_TRABAJADOR, CA_SUELDO_TOTAL, ID_REGIMEN_LABORAL,
                                ID_MODALIDAD, ID_SUB_MODALIDAD, CO_GR_OCUPACION, FE_SUSCRIPCION, CO_TI_MONEDA, CO_TI_REM_VARIAB, DE_REMU_ESPECIE, DE_RUC_EMP_TRAB, CO_SUCURSAL, DE_MYPE,
                                ES_TI_CONTRATACION, CA_BEV, ID_TIPO_PLANILLA, ES_REMUNERACION_PROCESADO, ID_HORARIO, ID_PLANTILLA_CONTRACTUAL, ca_bonificacion_p, PRACTICANTE, situacionEspecial);

                        //out.println("::Contrato Modificado");
                        /*Actualizando centro de costo*/
                        System.out.println("::Modificando Centro de costos....");
                        int cant_inicial = Integer.parseInt(request.getParameter("cant_inicial"));

                        System.out.println("::Cantidad Inicial:" + cant_inicial);
                        if (cant_inicial != 0) {

                            for (int gg = 0; gg < cant_inicial; gg++) {
                                if (request.getParameter("id_dcc" + (gg + 1)) != null) {
                                    Double porcen = Double.parseDouble(request.getParameter("PORCENTAJE_" + (gg + 1)));
                                    String id_dt_cen_c = request.getParameter("id_dcc" + (gg + 1));

                                    System.out.println("Modificando centro de costo:" + id_dt_cen_c);

                                    dcc.Modificar_Centro_Costo_porc(id_dt_cen_c, porcen, iduser);
                                } else {
                                    System.out.println(":: No se encontraron los id");
                                }
                            }
                            /*Se adicionaron nuevos centros de costo*/
                            int cantNueva = Integer.parseInt(request.getParameter("cant_ingresada"));
                            System.out.println("::Items de centro de costos adicionados:" + cantNueva);
                            if (cantNueva > 0) {
                                for (int i = 0; i < cantNueva; i++) {
                                    double porc_nuevo = Double.parseDouble(request.getParameter("PORCENTAJE_" + (cant_inicial + i)));
                                    String centro_c_nuevo = request.getParameter("CENTRO_COSTOS_" + (cant_inicial + i));

                                    System.out.println("***Agregando centro de costo:" + centro_c_nuevo);
                                    dcc.INSERT_DETALLE_CENTRO_COSTO("", "", porc_nuevo, "1", iduser, "", "", "", UserMachineProperties.getAll(),
                                            ID_CONTRATO, centro_c_nuevo);
                                    System.out.println("***Centro de costo agregado**");

                                }
                            } else {
                                System.out.println("::No se adicionaron Centros de Costo");
                            }
                        }
                        //out.print("2");
                        //  if (ID_CONTRATO != null) {

                        sesion.setAttribute("List_x_fun_x_idpu", fu.List_x_fun_x_idpu(ID_PUESTO));
                        sesion.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(ID_CONTRATO));
                        sesion.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(ID_CONTRATO));
                        // }
                        sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                        sesion.setAttribute("List_Usuario", usu.List_Usuario());
                        sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                        sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                        // con.VALIDAR_FE_HASTA_CON();
                        ///response.sendRedirect("views/Contrato/Detalle_Info_Contractualq.html?idtr=" + ID_TRABAJADOR + "&id_cto=" + ID_CONTRATO);
                    }
                    if (opc.equals("SI_CONNTRATO")) {
                        String idtr = request.getParameter("idtr");
                        /*mostrar el ultimo contrato registrado*/
                        String id_cto = con.Contrato_max(idtr);
                        if (id_cto != null) {
                            String id_pu = puesto.puesto(id_cto);
                            sesion.setAttribute("List_x_fun_x_idpu", fu.List_x_fun_x_idpu(id_pu));
                            sesion.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(id_cto));
                            sesion.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(id_cto));
                        }
                        sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                        sesion.setAttribute("List_Usuario", usu.List_Usuario());
                        sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                        sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                        ///response.sendRedirect("views/Contrato/Imprimir_Subir_Contrato.html?idtr=" + idtr);
                    }
                    if (opc.equals("Subir_Contrato")) {

                        ///response.sendRedirect("views/Contrato/Subir_Contrato_Adjunto.html?idc=" + request.getParameter("idc"));
                    }

                    if (opc.equals("Subir_Contrato2")) {
                        int coun_doc = con.Count_doc_con(request.getParameter("idc"));
                        String id_con = request.getParameter("idc");
                        ///response.sendRedirect("views/Contrato/Formato_Plantilla/Subir_Contrato_Firmado.html?idc=" + id_con + "&coun_doc=" + coun_doc);
                    }

                    if (opc.equals("Actualizar_Firma")) {
                        String idtr = request.getParameter("IDTR");
                        String iddgp = request.getParameter("IDDETALLE_DGP");
                        con.UPDATE_FIRMA(idtr, iddgp);
                        rpta.put("rpta", true);
                    }

                    if (opc.equals("actualizar")) {
                        String idtr = request.getParameter("idtr");
                        String id_cto = request.getParameter("idc");
                        String id_pu = puesto.puesto(id_cto);
                        sesion.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(id_cto));
                        sesion.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(id_cto));
                        sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                        sesion.setAttribute("List_Usuario", usu.List_Usuario());
                        sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                        sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                        sesion.setAttribute("List_x_fun_x_idpu", fu.List_x_fun_x_idpu(id_pu));
                        ///response.sendRedirect("views/Contrato/Detalle_Info_Contractualq.html?idtr=" + idtr.trim() + "&id_cto=" + id_cto);
                    }

                    if (opc.equals("REGISTRAR CONTRATO")) {

                        String ID_CONTRATO = "";
                        String ID_DGP = request.getParameter("IDDETALLE_DGP");
                        String FE_DESDE = request.getParameter("FEC_DESDE");
                        String FE_HASTA = request.getParameter("FEC_HASTA");
                        String FE_CESE = null;
                        String ID_FUNC = "";
                        String LI_CONDICION = request.getParameter("CONDICION");
                        double CA_SUELDO = Double.parseDouble(request.getParameter("SUELDO"));
                        double CA_REINTEGRO = Double.parseDouble(request.getParameter("REINTEGRO"));
                        double CA_ASIG_FAMILIAR = Double.parseDouble(request.getParameter("ASIG_FAMILIAR"));
                        double HO_SEMANA = Double.parseDouble(request.getParameter("HORAS_SEMANA"));
                        double NU_HORAS_LAB = Double.parseDouble(request.getParameter("NRO_HORAS_LAB"));
                        double DIA_CONTRATO = Double.parseDouble(request.getParameter("DIAS"));
                        String TI_TRABAJADOR = request.getParameter("TIPO_TRABAJADOR");
                        String LI_REGIMEN_LABORAL = request.getParameter("REGIMEN_LABORAL");
                        String ES_DISCAPACIDAD = request.getParameter("DISCAPACIDAD");
                        String TI_CONTRATO = request.getParameter("TIPO_CONTRATO");
                        String LI_REGIMEN_PENSIONARIO = request.getParameter("REGIMEN_PENSIONARIO");
                        String ES_CONTRATO_TRABAJADOR = null;
                        String US_CREACION = iduser;
                        String FE_CREACION = request.getParameter("FECHA_CREACION");
                        String US_MODIF = request.getParameter("USER_MODIF");
                        String FE_MODIF = request.getParameter("FECHA_MODIF");
                        String US_IP = request.getParameter("USUARIO_IP");
                        String FE_VACACIO_INI = "";
                        String FE_VACACIO_FIN = "";
                        String ES_CONTRATO = null;
                        String ID_FILIAL = request.getParameter("FILIAL");
                        String ID_DIRECCION = "";
                        String ID_DEPARTAMENTO = "";
                        String ID_AREA = request.getParameter("AREA_ID");
                        String ID_PUESTO = request.getParameter("PUESTO_ID");
                        String ID_SEC = request.getParameter("SECCION_ID");
                        double CA_BONO_ALIMENTO = Double.parseDouble(request.getParameter("BONO_ALIMENTO"));
                        String LI_TIPO_CONVENIO = request.getParameter("TIPO_CONVENIO");
                        String ES_FIRMO_CONTRATO = "0";
                        double NU_CONTRATO = 0.0;

                        String DE_OBSERVACION = request.getParameter("OBSERVACION");
                        String ES_APOYO = "";

                        String TI_HORA_PAGO = request.getParameter("TIPO_HORA_PAGO");
                        String NU_DOCUMENTO = "";

                        String ES_ENTREGAR_DOC_REGLAMENTOS = request.getParameter("ENTREGAR_DOC_REGLAMENTOS");
                        String ES_REGISTRO_HUELLA = request.getParameter("REGISTRO_HUELLA");
                        String DE_REGISTRO_SISTEM_REMU = request.getParameter("REGISTRO_SISTEM_REMU");
                        String ID_TRABAJADOR = request.getParameter("IDDATOS_TRABAJADOR");
                        double CA_SUELDO_TOTAL = Double.parseDouble(request.getParameter("TOTAL_SUELDO"));
                        String ID_REGIMEN_LABORAL = request.getParameter("REG_LAB_MINTRA");
                        String ID_MODALIDAD = request.getParameter("MODALIDAD");
                        String ID_SUB_MODALIDAD = request.getParameter("SUB_MODALIDAD").trim();
                        String CO_GR_OCUPACION = request.getParameter("CO_GRUPO_OCU");
                        String FE_SUSCRIPCION = request.getParameter("FECHA_SUSCRIPCION");
                        String CO_TI_MONEDA = request.getParameter("TIPO_MONEDA");
                        String CO_TI_REM_VARIAB = request.getParameter("REM_VARIABLE");
                        String DE_REMU_ESPECIE = request.getParameter("REM_ESPECIE");
                        String DE_RUC_EMP_TRAB = request.getParameter("EMP_RUC");
                        String CO_SUCURSAL = request.getParameter("SUCURSAL");
                        String DE_MYPE = request.getParameter("MYPE");
                        String ES_TI_CONTRATACION = request.getParameter("TI_CONTRATACION");
                        double CA_BEV = Double.parseDouble(request.getParameter("BEV"));
                        String ID_TIPO_PLANILLA = request.getParameter("TIPO_PLANILLA");
                        String ES_REMUNERACION_PROCESADO = null;
                        String ID_HORARIO = request.getParameter("HORARIO");
                        String ID_PLANTILLA_CONTRACTUAL = request.getParameter("id_plantilla_contractual");
                        double ca_bonificacion_p = Double.parseDouble(request.getParameter("ca_bono_puesto"));
                        int cantidad_centro = Integer.parseInt(request.getParameter("can_centro_cos"));
                        String ES_MFL = request.getParameter("MFL");
                        if (ES_MFL == null || ES_MFL.equals("0")) {
                            ES_MFL = "0";
                        } else {
                            ES_MFL = "1";
                        }
                        String PRACTICANTE = request.getParameter("PRACTICANTE");
                        if (PRACTICANTE == null) {
                            PRACTICANTE = "0";
                        }
                        String situacionEspecial = request.getParameter("situacionEspecial");

                        con.INSERT_CONTRATO(ID_CONTRATO, ID_DGP, FE_DESDE, FE_HASTA, FE_CESE, ID_FUNC, LI_CONDICION, CA_SUELDO, CA_REINTEGRO, CA_ASIG_FAMILIAR, HO_SEMANA, NU_HORAS_LAB,
                                DIA_CONTRATO, TI_TRABAJADOR, LI_REGIMEN_LABORAL, ES_DISCAPACIDAD, TI_CONTRATO, LI_REGIMEN_PENSIONARIO, ES_CONTRATO_TRABAJADOR, US_CREACION, FE_CREACION,
                                US_MODIF, FE_MODIF, US_IP, FE_VACACIO_INI, FE_VACACIO_FIN, ES_CONTRATO, ID_FILIAL, ID_PUESTO, CA_BONO_ALIMENTO, LI_TIPO_CONVENIO, ES_FIRMO_CONTRATO,
                                NU_CONTRATO, DE_OBSERVACION, ES_APOYO, TI_HORA_PAGO, NU_DOCUMENTO, ES_ENTREGAR_DOC_REGLAMENTOS, ES_REGISTRO_HUELLA, DE_REGISTRO_SISTEM_REMU, ID_TRABAJADOR,
                                CA_SUELDO_TOTAL, ID_REGIMEN_LABORAL, ID_MODALIDAD, ID_SUB_MODALIDAD, CO_GR_OCUPACION, FE_SUSCRIPCION, CO_TI_MONEDA, CO_TI_REM_VARIAB, DE_REMU_ESPECIE, DE_RUC_EMP_TRAB,
                                CO_SUCURSAL, DE_MYPE, ES_TI_CONTRATACION, CA_BEV, ID_TIPO_PLANILLA, ES_REMUNERACION_PROCESADO, ID_HORARIO, ID_PLANTILLA_CONTRACTUAL, ca_bonificacion_p, ES_MFL, PRACTICANTE, situacionEspecial);
                        /*Disminucion de Presupuesto al contratar*/
                /*Map<String, Object> s = new HashMap<>();
                String idDestino = request.getParameter("idDes");
                s.put("idDes", idDestino);
                s.put("SB", CA_SUELDO);
                s.put("idUSER", sesion.getAttribute("IDUSER"));
                s.put("AF", CA_ASIG_FAMILIAR);
                s.put("BA", CA_BONO_ALIMENTO);
                s.put("BO", ca_bonificacion_p);
                s.put("idtr", ID_TRABAJADOR);
                pd.Reg_DetPresupuesto(s);*/


                        String idtr1 = ID_TRABAJADOR;
                        String id_cto = con.Contrato_max(idtr1);
                        /*Modificar Centro de Costo*/
                        if (cantidad_centro > 0) {
                            for (int c = 0; c < cantidad_centro; c++) {
                                String ID_DET_CEN_COS = request.getParameter("id_dcc" + (c + 1));
                                cc.Mod_det_centro(ID_DET_CEN_COS, id_cto);
                            }
                        }
                        sesion.setAttribute("List_contra_x_idcto", con.List_contra_x_idcto(id_cto));
                        sesion.setAttribute("List_Situacion_Actual", l.List_Situacion_Actual());
                        sesion.setAttribute("List_Usuario", usu.List_Usuario());
                        sesion.setAttribute("list_Condicion_contrato", l.list_Condicion_contrato());
                        sesion.setAttribute("List_tipo_contrato", l.List_tipo_contrato());
                        sesion.setAttribute("list_reg_labo", con.list_reg_labo());
                        sesion.setAttribute("List_Jefe", l.List_Jefe());
                        sesion.setAttribute("List_Planilla", pl.List_Planilla(ID_DIRECCION, ID_DEPARTAMENTO, ID_SEC, ID_PUESTO, ID_AREA));
                        sesion.setAttribute("List_ID_User", usu.List_ID_User(US_CREACION));
                        sesion.setAttribute("List_x_fun_x_idpu", fu.List_x_fun_x_idpu(ID_PUESTO));
                        ///response.sendRedirect("views/Contrato/Detalle_Info_Contractualq.html?idtr=" + idtr1.trim() + "&id_cto=" + id_cto);
                    }

                    if (opc.equals("Habilitar_is")) {
                        String id = request.getParameter("id");
                        String estado = request.getParameter("estado");
                        con.HABILITAR_SI(id, estado);
                    }
                    if (opc.equals("validar_contrato")) {
                        String id_cto = request.getParameter("id_cto");
                        con.validar_contrato(id_cto);
                    }
                    if (opc.equals("gen_cont")) {
                        ///response.sendRedirect("views/Contrato/Gen_Contrato_CE.html");
                    }

                }
            }


        } catch (Exception ex) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", ex.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
