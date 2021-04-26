/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.dgp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.app.properties.UserMachineProperties;
import com.app.util.DateFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.AreaDAO;
import com.app.dao.AutorizacionDAO;
import com.app.dao.Centro_CostoDAO;
import com.app.dao.ContratoDAO;
import com.app.dao.Datos_Hijo_TrabajadorDAO;
import com.app.dao.Detalle_Centro_Costo_DAO;
import com.app.dao.DgpDAO;
import com.app.dao.DireccionDAO;
import com.app.dao.DocumentoDAO;
import com.app.dao.FuncionDAO;
import com.app.dao.GrupoOcupacionesDAO;
import com.app.dao.HorarioDAO;
import com.app.dao.ListaDAO;
import com.app.dao.Periodo_PagoDAO;
import com.app.dao.Plazo_DgpDAO;
import com.app.dao.PuestoDAO;
import com.app.dao.RequerimientoDAO;
import com.app.dao.TrabajadorDAO;
import com.app.dao.UsuarioDAO;
import com.app.dao_imp.InterfaceAreaDAO;
import com.app.dao_imp.InterfaceAutorizacionDAO;
import com.app.dao_imp.InterfaceCentro_CostosDAO;
import com.app.dao_imp.InterfaceContratoDAO;
import com.app.dao_imp.InterfaceDatos_Hijo_Trabajador;
import com.app.dao_imp.InterfaceDetalle_Centro_Costo;
import com.app.dao_imp.InterfaceDgpDAO;
import com.app.dao_imp.InterfaceDireccionDAO;
import com.app.dao_imp.InterfaceDocumentoDAO;
import com.app.dao_imp.InterfaceFuncionDAO;
import com.app.dao_imp.InterfaceGrupo_ocupacionesDAO;
import com.app.dao_imp.InterfaceHorarioDAO;
import com.app.dao_imp.InterfaceListaDAO;
import com.app.dao_imp.InterfacePeriodo_PagoDAO;
import com.app.dao_imp.InterfacePlazo_DgpDAO;
import com.app.dao_imp.InterfacePuestoDAO;
import com.app.dao_imp.InterfaceRequerimientoDAO;
import com.app.dao_imp.InterfaceTrabajadorDAO;
import com.app.dao_imp.InterfaceUsuarioDAO;
import com.app.web.controller.inbox.Permission;

/**
 *
 * @author Admin
 */

@RestController
@RequestMapping("processes/dgp")
public class CDgp {

    InterfaceRequerimientoDAO requerimientoDAO = new RequerimientoDAO();
    InterfaceTrabajadorDAO tr = new TrabajadorDAO();
    InterfaceDgpDAO dgp = new DgpDAO();
    InterfaceAutorizacionDAO autorizacionDAO = new AutorizacionDAO();
    InterfaceAreaDAO areaDAO = new AreaDAO();
    InterfaceHorarioDAO IHor = new HorarioDAO();
    InterfaceDocumentoDAO doc = new DocumentoDAO();
    InterfaceDetalle_Centro_Costo dcc = new Detalle_Centro_Costo_DAO();
    InterfacePuestoDAO puesto = new PuestoDAO();
    InterfaceContratoDAO contrato = new ContratoDAO();
    InterfaceCentro_CostosDAO cc = new Centro_CostoDAO();
    InterfaceDatos_Hijo_Trabajador dht = new Datos_Hijo_TrabajadorDAO();
    InterfaceGrupo_ocupacionesDAO gr = new GrupoOcupacionesDAO();
    InterfacePlazo_DgpDAO plazo = new Plazo_DgpDAO();
    InterfaceFuncionDAO funcion = new FuncionDAO();
    InterfaceListaDAO lista = new ListaDAO();
    InterfaceUsuarioDAO usuario = new UsuarioDAO();
    InterfaceDireccionDAO dir = new DireccionDAO();
    InterfacePeriodo_PagoDAO periodoPago = new Periodo_PagoDAO();

    @GetMapping
    public ResponseEntity<?> read(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String opc = request.getParameter("opc");

        Map<String, Object> rpta = new HashMap<String, Object>();

        //session parameters
        String iddep = (String) session.getAttribute("DEPARTAMENTO_ID");
        String iduser = (String) session.getAttribute("IDUSER");
        String idrol = (String) session.getAttribute("IDROL");
        String idtr = request.getParameter("idtr");

        if (opc.equals("Listar_Req")) {
            String id_tr = request.getParameter("idtr");
            List<Map<String, ?>> lista = dgp.list_Req(id_tr);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }
        if (opc.equals("Listar_Datos")) {
            String id_c = request.getParameter("idc");
            List<Map<String, ?>> lista = dgp.Cargar_Datos_Dgp(id_c);
            rpta.put("rpta", "1");
            rpta.put("lista", lista);
        }

        if (opc.equals("Detalle")) {

            String autorizar = request.getParameter("contrato");
            if (autorizar == null) {
                autorizar = "";
            }
            String ID_DGP = request.getParameter("iddgp");
            String ID_TRABAJADOR = request.getParameter("idtr");
            session.setAttribute("LIST_ID_DGP", dgp.LIST_ID_DGP(ID_DGP));
            session.setAttribute("Cargar_dcc_dgp", cc.Cargar_dcc_dgp(ID_DGP));
            int num = dgp.VALIDAR_DGP_CONTR(ID_DGP, ID_TRABAJADOR);
            session.setAttribute("LIST_ID_USER", usuario.List_ID_User(iduser));

            if (idrol.trim().equals("ROL-0006") & autorizar.equals("true")) {
                if (num == 0 & idrol.trim().equals("ROL-0006") & dgp.LIST_ID_DGP(ID_DGP).get(0).getEs_dgp().equals("0")) {
                    String iddgp = request.getParameter("iddgp");
                    String id_dir = puesto.List_Puesto_x_iddgp(iddgp);
                    session.setAttribute("LIST_ID_DGP", dgp.LIST_ID_DGP(iddgp));
                    session.setAttribute("List_Puesto", puesto.List_Puesto());
                    session.setAttribute("Listar_Direccion", dir.Listar_Direccion());
                    session.setAttribute("List_modalidad", contrato.List_modalidad());
                    session.setAttribute("list_reg_labo", contrato.list_reg_labo());
                    session.setAttribute("List_grup_ocu", gr.List_grup_ocu());
                    session.setAttribute("Listar_Requerimiento", requerimientoDAO.Listar_Requerimiento());

                    int asig = dht.ASIGNACION_F(ID_TRABAJADOR);
                    Calendar fecha = new GregorianCalendar();
                    int año = fecha.get(Calendar.YEAR);
                    int mes = fecha.get(Calendar.MONTH);
                    int dia = fecha.get(Calendar.DAY_OF_MONTH);
                    String fe_subs = "";
                    if (mes < 9 && dia < 9) {
                        fe_subs = año + "-" + "0" + (mes + 1) + "-" + "0" + dia;
                    }

                    if (mes < 9 && dia > 9) {
                        fe_subs = año + "-" + "0" + (mes + 1) + "-" + dia;
                    }

                    if (mes >= 9 && dia < 9) {
                        fe_subs = año + "-" + (mes + 1) + "-" + "0" + dia;
                    }
                    if (mes >= 9 && dia > 9) {
                        fe_subs = año + "-" + (mes + 1) + "-" + dia;
                    }
                    ////response.sendRedirect("views/Contrato/Reg_Contrato.html?num=" + asig + "&id_direc=" + id_dir + "&fe_subs=" + fe_subs);

                } else if (num == 0 & idrol.trim().equals("ROL-0006") & dgp.LIST_ID_DGP(ID_DGP).get(0).getEs_dgp().equals("1")) {

                    String id_cto = contrato.Contrato_max(idtr);
                    String id_pu = puesto.puesto(id_cto);
                    session.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(idtr));
                    session.setAttribute("List_contra_x_idcto", contrato.List_contra_x_idcto(id_cto));
                    session.setAttribute("List_Situacion_Actual", lista.List_Situacion_Actual());
                    session.setAttribute("List_Usuario", usuario.List_Usuario());
                    session.setAttribute("list_Condicion_contrato", lista.list_Condicion_contrato());
                    session.setAttribute("List_tipo_contrato", lista.List_tipo_contrato());
                    session.setAttribute("List_x_fun_x_idpu", funcion.List_x_fun_x_idpu(id_pu));
                    session.setAttribute("list_reg_labo", contrato.list_reg_labo());
                    //// response.sendRedirect("views/Contrato/Detalle_Info_Contractualq.html?idtr=" + idtr + "&id_cto=" + id_cto);
                } else {
                    String id_cto = contrato.Contrato_max(idtr);
                    String id_pu = puesto.puesto(id_cto);
                    session.setAttribute("Lis_c_c_id_contr", cc.Lis_c_c_id_contr(idtr));
                    session.setAttribute("List_contra_x_idcto", contrato.List_contra_x_idcto(id_cto));
                    session.setAttribute("List_Situacion_Actual", lista.List_Situacion_Actual());
                    session.setAttribute("List_Usuario", usuario.List_Usuario());
                    session.setAttribute("list_Condicion_contrato", lista.list_Condicion_contrato());
                    session.setAttribute("List_x_fun_x_idpu", funcion.List_x_fun_x_idpu(id_pu));
                    session.setAttribute("List_tipo_contrato", lista.List_tipo_contrato());
                    session.setAttribute("list_reg_labo", contrato.list_reg_labo());
                    ///response.sendRedirect("views/Contrato/Detalle_Info_Contractualq.html?idtr=" + idtr + "&id_cto=" + id_cto);
                }
            } else {

                ///response.sendRedirect("views/Dgp/Detalle_Dgp.html?idtr=" + ID_TRABAJADOR + "&num=" + num + "&idgp=" + ID_DGP);

            }
        }

        if (opc.equals("Listar")) {
            session.setAttribute("List_Det_Dgp", dgp.LIST_DET_DGP(iddep));
            session.setAttribute("List_Trb_Mod_Rel", tr.LIST_TRABAJADOR_MOD_REL());
            //// response.sendRedirect("views/Dgp/List_Dgp.html?iddep");

        }
        if (opc.equals("Imprimir_det_proceso")) {
            String idrp = request.getParameter("idrp");
            String iddgp = request.getParameter("dgp");
            String dep = request.getParameter("iddep");
            rpta.put("html", dgp.Imprimir_det_proceso(iddgp, idrp, dep));
            rpta.put("rpta", "1");
        }
        if (opc.equals("List_Dgp_Tr")) {

            session.setAttribute("LIST_ID_TRAB_DGP", dgp.LIST_ID_TRAB_DGP(idtr));
            ////response.sendRedirect("views/Trabajador/List_Dgp_Trabajador.html");
        }

        if (opc.equals("Seguimiento")) {
            String iddgp = request.getParameter("iddgp");
            /*corregir*/
            String idrp = requerimientoDAO.id_det_req_proc(iddgp);
            session.setAttribute("Det_Autorizacion", autorizacionDAO.List_Detalle_Autorizacion(iddgp, idrp));
            ////response.sendRedirect("views/Dgp/Detalle_Seguimiento_Dgp.html");
        }
        if (opc.equals("SeguimientoH")) {
            String iddgp = request.getParameter("iddgp");
            String idrp = requerimientoDAO.id_det_req_proc(iddgp);
            rpta.put("listar", autorizacionDAO.List_Detalle_Autorizacion(iddgp, idrp));
        }
        if (opc.equals("Val_Fe_Inicio")) {
            String fecha = request.getParameter("fecha");
            String newFormat = DateFormat.toFormat3(fecha);
            System.out.println("new format :" + newFormat);
            boolean respuesta = dgp.val_fe_inicio_dgp(newFormat);
            rpta.put("rpta", "1");
            rpta.put("estado", respuesta);
        }
        if (opc.equals("filtrar")) {
            session.setAttribute("List_Area", areaDAO.List_Area_ID(iddep));
            session.setAttribute("Listar_Requerimiento", requerimientoDAO.Listar_Requerimiento());
            ////response.sendRedirect("views/Dgp/Busc_Req_Autorizado.html");
        }
        if (opc.equals("User_Aut")) {
            String iddgp = request.getParameter("iddgp");
            session.setAttribute("USER_DGP", dgp.USER_DGP(iddgp));

            ///response.sendRedirect("views/Dgp/User_Dgp.html");
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        String opc = request.getParameter("opc");
        Map<String, Object> rpta = new HashMap<String, Object>();

        //session parameters
        String iddir = (String) session.getAttribute("IDDIR");
        String iddep = (String) session.getAttribute("DEPARTAMENTO_ID");
        String idpuesto = (String) session.getAttribute("PUESTO_ID");
        String iduser = (String) session.getAttribute("IDUSER");
        String idrol = (String) session.getAttribute("IDROL");
        String idtr = request.getParameter("idtr");
        String idreq = request.getParameter("idreq");
        
        //check persmissions
        Permission permission = new Permission().getPermissions(idrol);
        
        //PARA QUE EL RECTOR PUEDA VISUALIZAR TODOS LOS ESTADOS DE REQUERIMIENTOS DESCOMENTAR LA LINEA QUE ESTÁ DENTRO DE LA CONDICIONAL IF, 
        //VOLVER A COMENTAR PARA QUE EL RECTOR TENGA SUS PRIVILEGIOS POR DEFECTO//
        if(idpuesto.equals("PUT-000649")/*||idpuesto.equals("PUT-000638")*/){
            permission.setAdmin(true);
        }
        try {

            if (opc.equals("RegDGPAditionalPermissions")) {

                //htmlFilterAnyJobs
                String html = "";

                if (permission.isRegDGPAddiotionals()) {
                    html += "<div class='row'>";
                    html += "<section class='col col-md-6' >";
                    html += "<label class='label' id='titu'> Dirección:</label>";
                    html += "<label class='select'>";
                    html += "<select  class='selectDireccion' required='' >";
                    html += "<option value=''>[Seleccione]</option>  ";
                    html += "   </select>";
                    html += "  </label>";
                    html += "   </section>";
                    html += "  <section class='col col-md-6' >";
                    html += "      <label class='label' id='titu'> Departamento:</label>";
                    html += "    <label class='select'>";
                    html += "  <select   class='selectDepartamento' required='' >";
                    html += "  <option value=''>[Seleccione]</option>  ";
                    html += "    </select>";
                    html += " </label>";
                    html += " </section>";
                    html += "</div>";
                }
                rpta.put("filterAnyJobsHTML", html);
                rpta.put("filterAnyJobs", permission.isRegDGPAddiotionals());

            }
            if (opc.equals("Registrar")) {
                String FE_DESDE = request.getParameter("FEC_DESDE");
                String FE_HASTA = request.getParameter("FEC_HASTA");
                double CA_SUELDO = Double.parseDouble(request.getParameter("SUELDO"));
                double BONO_PUESTO = 0.0;
                String DE_DIAS_TRABAJO = request.getParameter("DIAS_TRABAJO");
                String ID_PUESTO = request.getParameter("IDPUESTO");
                String ID_REQUERIMIENTO = request.getParameter("IDREQUERIMIENTO");
                String ID_TRABAJADOR = request.getParameter("IDDATOS_TRABAJADOR");
                String CO_RUC = request.getParameter("RUC");
                String DE_LUGAR_SERVICIO = request.getParameter("LUGAR_SERVICIO");
                String DE_SERVICIO = request.getParameter("DESCRIPCION_SERVICIO");
                String DE_PERIODO_PAGO = request.getParameter("PERIODO_PAGO");
                String DE_DOMICILIO_FISCAL = request.getParameter("DOMICILIO_FISCAL");
                String DE_SUBVENCION = request.getParameter("SUBVENCION");
                String DE_HORARIO_CAPACITACION = request.getParameter("HORARIO_CAPACITACION");
                String DE_HORARIO_REFRIGERIO = request.getParameter("HORARIO_REFRIGERIO");
                String DE_DIAS_CAPACITACION = request.getParameter("DIAS_CAPACITACION");
                String ES_DGP = "0";
                String FE_CREACION = request.getParameter("FECHA_CREACION");
                String US_MODIF = request.getParameter("USER_MODIF");
                String FE_MODIF = request.getParameter("FECHA_MODIF");
                String IP_USUARIO = request.getParameter("USUARIO_IP");
                double CA_BONO_ALIMENTARIO = 0.0;
                double DE_BEV = 0.0;
                double ASIGNACION_FAMILIAR = 0.0;
                //----CA_CENTRO_COSTOS NO EXISTE EN TABLA RHTM_DGP---
                /*Fuera de planilla*/
                if (ID_REQUERIMIENTO.equals("REQ-0007") || ID_REQUERIMIENTO.equals("REQ-0008") || ID_REQUERIMIENTO.equals("REQ-0009") || ID_REQUERIMIENTO.equals("REQ-0010") || ID_REQUERIMIENTO.equals("REQ-0011")) {
                    CA_BONO_ALIMENTARIO = 0.0;
                    DE_BEV = 0.0;
                    BONO_PUESTO = 0.0;
                    ASIGNACION_FAMILIAR = 0.0;
                } else {
                    /*dentro de planilla de planilla*/
                    if (request.getParameter("BONO_PUESTO") == null) {
                        BONO_PUESTO = 0.0;
                    } else {
                        BONO_PUESTO = Double.parseDouble(request.getParameter("BONO_PUESTO"));
                    }

                    if (request.getParameter("BONO_ALIMENTARIO") == null) {
                        CA_BONO_ALIMENTARIO = 0.0;
                    } else {
                        CA_BONO_ALIMENTARIO = Double.parseDouble(request.getParameter("BONO_ALIMENTARIO"));
                    }

                    if (request.getParameter("BEV") == null) {
                        DE_BEV = 0.0;
                    } else {
                        DE_BEV = Double.parseDouble(request.getParameter("BEV"));
                    }
                    if (request.getParameter("ASIGNACION_FAMILIAR") == null) {
                        ASIGNACION_FAMILIAR = 0.0;
                    } else {
                        ASIGNACION_FAMILIAR = Double.parseDouble(request.getParameter("ASIGNACION_FAMILIAR"));
                    }
                }
                String DE_ANTECEDENTES_POLICIALES = request.getParameter("ANTECEDENTES_POLICIALES");
                String ES_CERTIFICADO_SALUD = request.getParameter("CERTIFICADO_SALUD");
                String DE_MONTO_HONORARIO = request.getParameter("MONTO_HONORARIO");
                //--INSERT TABLA_CUENTA_SUELDO
                String NO_BANCO = request.getParameter("BANCO");
                String NU_CUENTA = (request.getParameter("CUENTA") == null) ? "no tiene" : request.getParameter("CUENTA");
                String NU_CUENTA_BANC = request.getParameter("CUENTA_BANC");
                String ES_GEM_NU_CUENTA = (request.getParameter("GEN_NU_CUEN") == null) ? "0" : "1";
                String NO_BANCO_OTROS = request.getParameter("BANCO_OTROS");
                String ES_CUENTA_SUELDO = request.getParameter("ES_CUENTA_SUELDO");
                //--
                int NUMERO = 0;
                int cantidad = 0;
                if (ID_REQUERIMIENTO.equals("REQ-0010") || ID_REQUERIMIENTO.equals("REQ-0011")) {
                    NUMERO = 0;
                    cantidad = Integer.parseInt(request.getParameter("CANT"));
                } else {
                    NUMERO = Integer.parseInt(request.getParameter("numero"));
                    cantidad = 0;
                }
                String LI_MOTIVO = request.getParameter("MOTIVO");
                String ES_MFL = request.getParameter("MFL");
                if (ES_MFL != null) {
                    ES_MFL = "1";
                } else {
                    ES_MFL = "0";
                }
                //campo nuevo - Presupuestado
                String ES_PRESUPUESTADO = request.getParameter("ES_PRESUPUESTADO");
                if (ES_PRESUPUESTADO != null) {
                    ES_PRESUPUESTADO = "1";
                } else {
                    ES_PRESUPUESTADO = "0";
                }
                FE_DESDE = DateFormat.toFormat3(FE_DESDE);
                FE_HASTA = DateFormat.toFormat3(FE_HASTA);
                //out.println("Nueva fecha :" + DateFormat.toFormat1(FE_HASTA));

                dgp.INSERT_DGP(null, FE_DESDE, FE_HASTA, CA_SUELDO, DE_DIAS_TRABAJO, ID_PUESTO, ID_REQUERIMIENTO, ID_TRABAJADOR, CO_RUC, DE_LUGAR_SERVICIO,
                        DE_SERVICIO, DE_PERIODO_PAGO, DE_DOMICILIO_FISCAL, DE_SUBVENCION, DE_HORARIO_CAPACITACION, DE_HORARIO_REFRIGERIO, DE_DIAS_CAPACITACION,
                        ES_DGP, iduser, FE_CREACION, US_MODIF, FE_MODIF, IP_USUARIO, CA_BONO_ALIMENTARIO, DE_BEV, DE_ANTECEDENTES_POLICIALES, ES_CERTIFICADO_SALUD,
                        DE_MONTO_HONORARIO, LI_MOTIVO, ES_MFL, BONO_PUESTO, ASIGNACION_FAMILIAR, ES_PRESUPUESTADO);
                String iddgp = dgp.MAX_ID_DGP();
                String ESTADO = request.getParameter("ESTADO");
                if (ESTADO != null) {
                    if (ESTADO.trim().equals("0")) {
                        tr.MOD_CUENTA_SUELDO(NO_BANCO, NU_CUENTA, NU_CUENTA_BANC, ES_GEM_NU_CUENTA, NO_BANCO_OTROS, ID_TRABAJADOR, ES_CUENTA_SUELDO);
                    }
                }
                String idrp = requerimientoDAO.id_det_req_proc(iddgp);
                for (int i = 1; i <= cantidad; i++) {
                    String ID_PERIODO_PAG0 = null;
                    double NU_CUOTA = Double.parseDouble(request.getParameter("CUOTA_" + i));
                    String FE_PAGAR = request.getParameter("FEC_PAGAR_" + i);
                    double CA_MONTO = Double.parseDouble(request.getParameter("MONTO_" + i));
                    String ID_DGP = iddgp;
                    String ES_PER_PAGO = request.getParameter("ES_PERIODO");
                    periodoPago.InsetarPeriodo_Pago(ID_PERIODO_PAG0, NU_CUOTA, FE_PAGAR, CA_MONTO, ID_DGP, ES_PER_PAGO);
                }
                System.out.println("Numero centro costo:" + NUMERO);
                for (int g = 1; g <= NUMERO; g++) {
                    String ID_CENTRO_COSTO = request.getParameter("CENTRO_COSTOS_" + g);
                    double porcentaje = Double.parseDouble(request.getParameter("PORCENTAJE_" + g));
                    if (ID_CENTRO_COSTO != null && porcentaje != 0.0) {
                        dcc.INSERT_DETALLE_CENTRO_COSTO(null, iddgp, porcentaje, "1", iduser, null, null, null, UserMachineProperties.getAll(), null, ID_CENTRO_COSTO);
                    }
                }
                List<String> list = autorizacionDAO.Det_Autorizacion(idrp);
                System.out.println("Insertando autorizacion...");
                autorizacionDAO.Insert_Autorizacion("", iddgp, "1", "P1", "12312", iduser, "", "", "", list.get(1), idrp, list.get(0));

                //HORARIO
                List<String> dia = new ArrayList<String>();
                dia.add("lun");
                dia.add("mar");
                dia.add("mie");
                dia.add("jue");
                dia.add("vie");
                dia.add("sab");
                dia.add("dom");
                String ID_DETALLE_HORARIO = request.getParameter("ID_DETALLE_HORARIO");
                String ES_DETALLE_HORARIO = "1";
                String ES_HORARIO = "1";
                String ID_TIPO_HORARIO = request.getParameter("HORARIO");
                String ES_MOD_FORMATO = "1";
                Double horas_totales = Double.parseDouble(request.getParameter("h_total"));
                String id_d_hor = "";
                System.out.println("Insertando Horario...");
                id_d_hor = IHor.Insert_Detalle_Horario(ID_DETALLE_HORARIO, iddgp, ES_DETALLE_HORARIO, iduser, null, null, null, ID_TIPO_HORARIO, ES_MOD_FORMATO, horas_totales);
                System.out.println("Insertando detalle horario...");
                System.out.println("dias totales:" + dia);
                for (int i = 0; i < dia.size(); i++) {
                    for (int j = 0; j < 10; j++) {

                        String hora_desde = request.getParameter("HORA_DESDE_" + dia.get(i) + j);

                        String hora_hasta = request.getParameter("HORA_HASTA_" + dia.get(i) + j);

                        String d = request.getParameter("DIA_" + dia.get(i) + j);

                        /*  System.out.println("dia:" + d);
                        System.out.println("desde:" + hora_desde);
                        System.out.println("hasta:" + hora_hasta);*/
                        if (hora_desde != null & d != null & hora_hasta != null) {
                            if (!hora_hasta.equals("") & !hora_desde.equals("") & !d.equals("")) {
                                hora_desde = parser24(hora_desde);
                                hora_hasta = parser24(hora_hasta);
                                IHor.Insert_Horario(null, hora_desde, hora_hasta, d, ES_HORARIO, id_d_hor);
                                //ifo.Insert_Formato_Horario(null, "T"+j, d, hora_desde, hora_hasta, "1", ID_TIPO_HORARIO);
                            }
                        }
                    }
                }
                ////response.sendRedirect("views/Dgp/Documento/Reg_Documento.html?pro=pr_dgp&idtr=" + ID_TRABAJADOR + "&iddgp=" + iddgp);
            }
            if (opc.equals("Reg_form")) {
                /* TEMPORAL*/
                if (idreq.equals("1")) {
                    idreq = "REQ-0001";
                }
                if (idreq.equals("2")) {
                    idreq = "REQ-0002";
                }
                if (idreq.equals("3")) {
                    idreq = "REQ-0003";
                }
                if (idreq.equals("7")) {
                    idreq = "REQ-0007";
                }
                if (idreq.equals("8")) {
                    idreq = "REQ-0008";
                }
                if (idreq.equals("9")) {
                    idreq = "REQ-0009";
                }
                if (idreq.equals("10")) {
                    idreq = "REQ-0010";
                }
                if (idreq.equals("11")) {
                    idreq = "REQ-0011";
                }

                String ES_CUENTA_SUELDO = tr.CuentaSueldoTra(idtr);

                while (ES_CUENTA_SUELDO == null) {

                    tr.INSERT_CUENTA_SUELDO(null, null, null, null, "0", null, idtr, "0");
                    ES_CUENTA_SUELDO = tr.CuentaSueldoTra(idtr);
                }

                session.setAttribute("Listar_Requerimiento", requerimientoDAO.Listar_Requerimiento());
                session.setAttribute("List_Puesto", puesto.List_Puesto_Dep(iddep));
                session.setAttribute("Listar_Trabajador_id", tr.ListaridTrabajador(idtr));
                session.setAttribute("list_Cuenta_Sueldo", dgp.LIST_CUEN_SUEL(idtr));
                session.setAttribute("fecha_maxima_plazo", plazo.fecha_maxima_plazo());

                ////response.sendRedirect("views/Dgp/Reg_Dgp.html?idreq=" + idreq + "&es_cs=" + ES_CUENTA_SUELDO + "&as_f=" + dht.ASIGNACION_F(idtr));
            }
            if (opc.equals("Reg_renuncia")) {
                //   String iddeph = request.getParameter("idep");
                /* TEMPORAL*/
                String Tipo_planilla = tr.tipo_planilla(idtr);
                if (Tipo_planilla.equals("TPL-0001")) {
                    idreq = "REQ-0015";
                }
                if (Tipo_planilla.equals("TPL-0002")) {
                    idreq = "REQ-0016";
                }
                if (Tipo_planilla.equals("TPL-0003")) {
                    idreq = "REQ-0017";
                }
            }
            if (opc.equals("rd")) {
                String ID_DGP = request.getParameter("iddgp");
                String ID_TRABAJADOR = request.getParameter("idtr");
                session.setAttribute("LIST_ID_DGP", dgp.LIST_ID_DGP(ID_DGP));
                session.setAttribute("VALIDAR_DGP_CONTR", dgp.VALIDAR_DGP_CONTR(ID_DGP, idtr));
                session.setAttribute("Cargar_dcc_dgp", cc.Cargar_dcc_dgp(ID_DGP));
                int num = dgp.VALIDAR_DGP_CONTR(ID_DGP, ID_TRABAJADOR);
                session.setAttribute("LIST_ID_USER", usuario.List_ID_User(iduser));
                ////response.sendRedirect("views/Dgp/Detalle_Dgp.html?idtr=" + ID_TRABAJADOR + "&num=" + num + "&iddgp=" + ID_DGP + "&opc=reg_doc");
            }
            if (opc.equals("Proceso")) {

                if (permission.isAdmin()) {
                    session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", "", false, true));
                } else {
                    if (idrol.equals("ROL-0019")||idrol.equals("ROL-0008")) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", iddir, "", false, false));
                    } else {
                        if (permission.isDepartFilter()) {
                            session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false, false));
                        }
                        if (permission.isDireccionFilter()) {
                            session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", iddir, "", false, false));
                        }
                        if (permission.isPuestoFilter()) {
                            session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", idpuesto, false, false));
                        } else {
                            session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false, false));
                        }
                    }
                }

                ////response.sendRedirect("views/Dgp/Proceso_Dgp.html");
            }
            if (opc.equals("Terminar")) {
                String iddgp = request.getParameter("iddgp");
               // out.print(iddgp);
                dgp.REG_DGP_FINAL(iddgp);
                if (permission.isAdmin()) {
                    session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", "", false, true));
                } else {
                    if (permission.isDepartFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false, false));
                    }
                    if (permission.isDireccionFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", iddir, "", false, false));
                    }
                    if (permission.isPuestoFilter()) {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO("", "", idpuesto, false, false));
                    } else {
                        session.setAttribute("LIST_DGP_PROCESO", dgp.LIST_DGP_PROCESO(iddep, "", "", false, false));
                    }
                }
                ///response.sendRedirect("views/Dgp/Proceso_Dgp.html?a=t");
            }
            if (opc.equals("MODIFICAR REQUERIMIENTO")) {
                String iddgp = request.getParameter("iddgp");
                int can_cc = dgp.Can_cc_iddgp(iddgp);
                String id_d_hor = IHor.id_det_horario(iddgp);
                idtr = dgp.obt_idtr_x_dgp(iddgp);
                String ES_CUENTA_SUELDO = tr.CuentaSueldoTra(idtr);
               // out.println(idtr + " " + iddgp + "" + idreq + "" + iddep + "" + idpuesto);
                session.setAttribute("LIST_ID_DGP", dgp.LIST_ID_DGP(iddgp));
                session.setAttribute("Listar_Trabajador_id", tr.ListaridTrabajador(idtr));
                session.setAttribute("List_Puesto", puesto.List_Puesto_Dep(iddep));
                session.setAttribute("list_Cuenta_Sueldo", dgp.LIST_CUEN_SUEL(idtr));
                session.setAttribute("Listar_Requerimiento", requerimientoDAO.Listar_Requerimiento());
                String redirect = request.getParameter("redirect");
                if (redirect != null) {
                    ///response.sendRedirect("views/Dgp/Editar_DGP.html?es_cs=" + ES_CUENTA_SUELDO + "&can_cc=" + can_cc + "&id_det_hor=" + id_d_hor.trim() + "&redirect=proceso_dgp");

                } else {
                    ///response.sendRedirect("views/Dgp/Editar_DGP.html?es_cs=" + ES_CUENTA_SUELDO + "&can_cc=" + can_cc + "&id_det_hor=" + id_d_hor.trim());
                }
            }
            if (opc.equals("Modificar")) {
                String FE_DESDE = request.getParameter("FEC_DESDE");
                String FE_HASTA = request.getParameter("FEC_HASTA");
                double CA_SUELDO = Double.parseDouble(request.getParameter("SUELDO"));
                double BONO_PUESTO = 0.0;

                String DE_DIAS_TRABAJO = request.getParameter("DIAS_TRABAJO");
                String ID_PUESTO = request.getParameter("IDPUESTO");
                String ID_REQUERIMIENTO = request.getParameter("IDREQUERIMIENTO");
                String ID_TRABAJADOR = request.getParameter("IDDATOS_TRABAJADOR");
                String CO_RUC = request.getParameter("RUC");
                String DE_LUGAR_SERVICIO = request.getParameter("LUGAR_SERVICIO");
                String DE_SERVICIO = request.getParameter("DESCRIPCION_SERVICIO");
                String DE_PERIODO_PAGO = request.getParameter("PERIODO_PAGO");
                String DE_DOMICILIO_FISCAL = request.getParameter("DOMICILIO_FISCAL");
                String DE_SUBVENCION = request.getParameter("SUBVENCION");
                String DE_HORARIO_CAPACITACION = request.getParameter("HORARIO_CAPACITACION");
                String DE_HORARIO_REFRIGERIO = request.getParameter("HORARIO_REFRIGERIO");
                String DE_DIAS_CAPACITACION = request.getParameter("DIAS_CAPACITACION");
                String ES_DGP = "";
                String FE_CREACION = request.getParameter("FECHA_CREACION");
                // String US_MODIF = request.getParameter("USER_MODIF");
                String FE_MODIF = request.getParameter("FECHA_MODIF");
                String IP_USUARIO = request.getParameter("USUARIO_IP");
                double CA_BONO_ALIMENTARIO = 0.0;
                double DE_BEV = 0.0;
                //----CA_CENTRO_COSTOS NO EXISTE EN TABLA RHTM_DGP---

                /*Fuera de planilla*/
                if (ID_REQUERIMIENTO.equals("REQ-0007") || ID_REQUERIMIENTO.equals("REQ-0008") || ID_REQUERIMIENTO.equals("REQ-0009") || ID_REQUERIMIENTO.equals("REQ-0010") || ID_REQUERIMIENTO.equals("REQ-0011")) {
                    CA_BONO_ALIMENTARIO = 0.0;
                    DE_BEV = 0.0;
                    BONO_PUESTO = 0.0;
                } else {
                    /*dentro de planilla de planilla*/
                    BONO_PUESTO = Double.parseDouble(request.getParameter("BONO_PUESTO"));
                    CA_BONO_ALIMENTARIO = Double.parseDouble(request.getParameter("BONO_ALIMENTARIO"));
                    DE_BEV = Double.parseDouble(request.getParameter("BEV"));
                }

                String DE_ANTECEDENTES_POLICIALES = request.getParameter("ANTECEDENTES_POLICIALES");
                String ES_CERTIFICADO_SALUD = request.getParameter("CERTIFICADO_SALUD");
                String DE_MONTO_HONORARIO = request.getParameter("MONTO_HONORARIO");
                //--INSERT TABLA_CUENTA_SUELDO
                String NO_BANCO = request.getParameter("BANCO");
                String NU_CUENTA = (request.getParameter("CUENTA") == null) ? "no tiene" : request.getParameter("CUENTA");
                // String NU_CUENTA = request.getParameter("CUENTA");
                //String NU_CUENTA_BANC = (request.getParameter("CUENTA_BANC") == null) ? "0" : "no tiene";
                String NU_CUENTA_BANC = request.getParameter("CUENTA_BANC");
                String ES_GEM_NU_CUENTA = (request.getParameter("GEN_NU_CUEN") == null) ? "0" : "1";
                String NO_BANCO_OTROS = request.getParameter("BANCO_OTROS");
                String ES_CUENTA_SUELDO = request.getParameter("ES_CUENTA_SUELDO");
                String ID_DGP = request.getParameter("ID_DGP");
                //--

                int NUMERO = 0;
                int cantidad = 0;
                if (ID_REQUERIMIENTO.equals("REQ-0010") || ID_REQUERIMIENTO.equals("REQ-0011")) {
                    NUMERO = 0;
                    cantidad = Integer.parseInt(request.getParameter("CANT"));
                } else {
                    NUMERO = Integer.parseInt(request.getParameter("numero"));
                    cantidad = 0;
                }

                String LI_MOTIVO = request.getParameter("MOTIVO");
                String ES_MFL = "0";

                if (request.getParameter("MFL") == null) {
                    ES_MFL = "0";
                } else {
                    ES_MFL = "1";
                }
                String ES_PRESUPUESTADO = request.getParameter("ES_PRESUPUESTADO");
                if (request.getParameter("ES_PRESUPUESTADO") == null) {
                    ES_PRESUPUESTADO = "0";
                } else {
                    ES_PRESUPUESTADO = "1";
                }
                FE_DESDE = DateFormat.toFormat3(FE_DESDE);
                FE_HASTA = DateFormat.toFormat3(FE_HASTA);
                dgp.MODIFICAR_DGP(ID_DGP, FE_DESDE, FE_HASTA, CA_SUELDO, DE_DIAS_TRABAJO, ID_PUESTO, ID_REQUERIMIENTO, ID_TRABAJADOR, CO_RUC, DE_LUGAR_SERVICIO,
                        DE_SERVICIO, DE_PERIODO_PAGO, DE_DOMICILIO_FISCAL, DE_SUBVENCION, DE_HORARIO_CAPACITACION, DE_HORARIO_REFRIGERIO, DE_DIAS_CAPACITACION,
                        ES_DGP, null, FE_CREACION, iduser, FE_MODIF, IP_USUARIO, CA_BONO_ALIMENTARIO, DE_BEV, DE_ANTECEDENTES_POLICIALES, ES_CERTIFICADO_SALUD,
                        DE_MONTO_HONORARIO, LI_MOTIVO, ES_MFL, BONO_PUESTO, ES_PRESUPUESTADO);
                String iddgp = dgp.MAX_ID_DGP();
                String ESTADO = request.getParameter("ESTADO");
                if (ESTADO != null) {
                    if (ESTADO.trim().equals("0")) {
                        tr.MOD_CUENTA_SUELDO(NO_BANCO, NU_CUENTA, NU_CUENTA_BANC, ES_GEM_NU_CUENTA, NO_BANCO_OTROS, ID_TRABAJADOR, ES_CUENTA_SUELDO);
                    }
                }
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
                            dcc.INSERT_DETALLE_CENTRO_COSTO("", ID_DGP, porc_nuevo, "1", iduser, "", "", "", UserMachineProperties.getAll(),
                                    "", centro_c_nuevo);
                            System.out.println("***Centro de costo agregado**");

                        }
                    } else {
                        System.out.println("::No se adicionaron Centros de Costo");
                    }
                }
                //  List<String> list = a.Det_Autorizacion(idrp);
                // a.Insert_Autorizacion("", iddgp, "1", "P1", "12312", iduser, "", "", "", list.get(1), idrp, list.get(0));
                String es_mod = request.getParameter("estado_de_horario");

                if (es_mod.equals("1")) {
                    String id_de_horario = IHor.id_detalle_horario(ID_DGP);
                    IHor.ELIMINAR_HORARIO(id_de_horario);
                    List<String> dia = new ArrayList<String>();
                    dia.add("lun");
                    dia.add("mar");
                    dia.add("mie");
                    dia.add("jue");
                    dia.add("vie");
                    dia.add("sab");
                    dia.add("dom");

                    String ID_DETALLE_HORARIO = request.getParameter("ID_DETALLE_HORARIO");

                    String ES_DETALLE_HORARIO = "1";
                    String ES_HORARIO = "1";
                    String ID_TIPO_HORARIO = request.getParameter("HORARIO");
                    String ES_MOD_FORMATO = "1";
                    Double horas_totales = Double.parseDouble(request.getParameter("h_total"));
                    String id_d_hor = "";
                    id_d_hor = IHor.Insert_Detalle_Horario(ID_DETALLE_HORARIO, ID_DGP, ES_DETALLE_HORARIO, iduser, null, null, null, ID_TIPO_HORARIO, ES_MOD_FORMATO, horas_totales);
                    for (int i = 0; i < dia.size(); i++) {
                        for (int j = 0; j < 10; j++) {
                            String hora_desde = request.getParameter("HORA_DESDE_" + dia.get(i) + j);
                            String hora_hasta = request.getParameter("HORA_HASTA_" + dia.get(i) + j);
                            String d = request.getParameter("DIA_" + dia.get(i) + j);
                            if (hora_desde != null & d != null & hora_hasta != null) {
                                if (!hora_hasta.equals("") & !hora_desde.equals("") & !d.equals("")) {
                                    IHor.Insert_Horario("", hora_desde, hora_hasta, d, ES_HORARIO, id_d_hor);
                                }
                            }
                        }
                    }
                } else {
                    List<String> dia = new ArrayList<String>();
                    dia.add("lun");
                    dia.add("mar");
                    dia.add("mie");
                    dia.add("jue");
                    dia.add("vie");
                    dia.add("sab");
                    dia.add("dom");
                    for (int y = 0; y < dia.size(); y++) {
                        for (int j = 0; j < 10; j++) {
                            String id_horario = request.getParameter("id_horario" + j + dia.get(y));
                            String ho_desde = request.getParameter("HORA_DESDE_" + dia.get(y) + j);
                            String ho_hasta = request.getParameter("HORA_HASTA_" + dia.get(y) + j);
                            if (id_horario != null) {
                                IHor.modificar_horario(ho_desde, ho_hasta, id_horario);
                            }
                        }
                    }
                }
                session.setAttribute("List_doc_req_pla", doc.List_doc_req_pla(iddgp, ID_TRABAJADOR));
                session.setAttribute("List_Hijos", doc.List_Hijos(ID_TRABAJADOR));
                session.setAttribute("List_Conyugue", doc.List_Conyugue(ID_TRABAJADOR));

                String redireccionar = request.getParameter("redirect");
                if (redireccionar != null) {
                    if (redireccionar.equals("proceso_dgp")) {
                        ///response.sendRedirect("dgp?iddgp=" + ID_DGP + "&idtr=" + ID_TRABAJADOR + "&opc=rd");
                    } else {
                        ///response.sendRedirect("dgp?iddgp=" + ID_DGP + "&idtr=" + ID_TRABAJADOR + "&opc=Detalle");
                    }
                } else {
                    ///response.sendRedirect("dgp?iddgp=" + ID_DGP + "&idtr=" + ID_TRABAJADOR + "&opc=Detalle");
                }
            }
            if (opc.equals("Incompleto")) {

                session.setAttribute("List_Incomplet", dgp.List_Incomplet(iddep, permission.isAdmin()));
                ///response.sendRedirect("views/Dgp/List_req_incompl.html");
            }

        } catch (Exception e) {
            rpta.put("rpta", "-1");
            rpta.put("mensaje", e.getMessage());
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
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
                } else if (x == 12) {
                    ret = 0 + ":" + y;
                }
            }
            if (h.toUpperCase().equals("PM")) {
                if (x < 12) {
                    ret = (x + 12) + ":" + y;
                } else if (x == 12) {
                    ret = x + ":" + y;
                }

            }
            return ret;
        } else {
            return null;
        }

    }


}
