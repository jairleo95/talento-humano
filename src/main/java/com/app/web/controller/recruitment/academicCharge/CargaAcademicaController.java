/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.academicCharge;

import com.app.model.DGP;
import com.app.model.V_Detalle_Carga_Academica;
import com.app.properties.Strings;
import com.app.properties.UserMachineProperties;
import com.app.properties.globalProperties;
import com.app.util.CCriptografiar;
import com.app.util.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.app.dao.AutorizacionDAO;
import com.app.dao.Carga_AcademicaDAO;
import com.app.dao.DgpDAO;
import com.app.dao.DireccionDAO;
import com.app.dao.ListaDAO;
import com.app.dao.RequerimientoDAO;
import com.app.dao.ScheduledTest;
import com.app.dao.Tipo_DocumentoDAO;
import com.app.dao.TrabajadorDAO;
import com.app.dao.UbigeoDAO;
import com.app.dao_imp.InterfaceAutorizacionDAO;
import com.app.dao_imp.InterfaceCarga_AcademicaDAO;
import com.app.dao_imp.InterfaceDgpDAO;
import com.app.dao_imp.InterfaceDireccionDAO;
import com.app.dao_imp.InterfaceListaDAO;
import com.app.dao_imp.InterfaceRequerimientoDAO;
import com.app.dao_imp.InterfaceTipo_DocumentoDAO;
import com.app.dao_imp.InterfaceTrabajadorDAO;
import com.app.dao_imp.InterfaceUbigeoDAO;

/**
 *
 * @author ALFA 3
 */
@RestController
@RequestMapping("carga_academica/procesess")
public class CargaAcademicaController {

        InterfaceCarga_AcademicaDAO carga = new Carga_AcademicaDAO();
        InterfaceTrabajadorDAO tr = new TrabajadorDAO();
        InterfaceListaDAO li = new ListaDAO();
        InterfaceUbigeoDAO ub = new UbigeoDAO();
        InterfaceDireccionDAO dir = new DireccionDAO();
        InterfaceTipo_DocumentoDAO tdoc = new Tipo_DocumentoDAO();

        InterfaceRequerimientoDAO IReq = new RequerimientoDAO();
        InterfaceAutorizacionDAO a = new AutorizacionDAO();
        InterfaceDgpDAO dgp = new DgpDAO();

        @Autowired
        ServletContext context;

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String opc = request.getParameter("opc");
        HttpSession session = request.getSession(true);

        String iddep = (String) session.getAttribute("DEPARTAMENTO_ID");
        String semestre = request.getParameter("semestre");

        HttpSession sesion = request.getSession(true);

            if (opc.equals("listCargaAcademica")) {
                dataMap.put("data", carga.ListCarAca());
                dataMap.put("status", true);
            }
            if (opc.equals("validateTrabajador")) {
                String dni = request.getParameter("nro_doc");
                String idtr = carga.DNI_ID_TRABAJADOR(dni);
                dataMap.put("validateData", (!idtr.equals("")));
                dataMap.put("status", true);
            }
            if (opc.equals("getDetCargaAcademica")) {
                String eap = request.getParameter("eap");
                String facu = request.getParameter("facultad");
                String dni = request.getParameter("nro_doc");
                String ciclo = request.getParameter("ciclo");
                String idtr = null;
                try {
                    idtr = CCriptografiar.Desencriptar(request.getParameter("idtr"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dataMap.put("list", carga.Lista_detalle_academico(idtr, facu, eap, ciclo, dni));
            }
            if (opc.equals("List_ws")) {
                dataMap.put("List_ws", carga.List_Carga_Academica_WS(semestre));
            }
            if (opc.equals("listEsCargaAcademica")) {
                dataMap.put("list", dgp.LIST_DGP_PROCESO(iddep, "", "", true,false));
            }
            if (opc.equals("getProcesoCargaAcademicaById")) {
    //                String id = CCriptografiar.Desencriptar(request.getParameter("id"));
    //                dataMap.put("item", carga.getProcesoCargaAcademciaById(id));
    //                dataMap.put("status", true);
            }
            if (opc.equals("statusSyncUpCargaAcademica")) {
                System.out.println("::statusSyncUpCargaAcademica");
                Boolean x = false;
                if (context.getAttribute("runnableCA") != null) {
                    ScheduledFuture sc = (ScheduledFuture) context.getAttribute("runnableCA");
                    //System.out.println("schedule properties:" + sc.get().toString());
                    dataMap.put("scheduleProperties", null);
                    x = true;
                } else if (context.getAttribute("runnableCA") == null) {
                    x = false;
                }
                dataMap.put("statusSyncUp", x);
            }

        return new ResponseEntity<>(dataMap, HttpStatus.OK);
    }



    @PutMapping("persons")//Completar_Datos
    public ResponseEntity<?> completeData(HttpServletRequest request){
        HttpSession sesion = request.getSession(true);
        //if (opc.equals("Completar_Datos")) {
            String eap = request.getParameter("eap");
            String facu = request.getParameter("facultad");
            String dni = request.getParameter("nro_doc");
            String idtr = carga.DNI_ID_TRABAJADOR(dni);

            if (idtr.equals("")) {
                sesion.setAttribute("List_Carrera", li.List_Carrera());
                sesion.setAttribute("List_Nacionalidad", li.List_Nacionalidad());
                sesion.setAttribute("List_Universidad", li.List_Universidad());
                sesion.setAttribute("List_Departamento", ub.List_Departamento());
                sesion.setAttribute("List_Situacion_Educativa", li.List_Situacion_Educativa());
                sesion.setAttribute("Listar_via", dir.Listar_via());
                sesion.setAttribute("Listar_zona", dir.Listar_zona());
                sesion.setAttribute("Listar_tipo_doc", tdoc.Listar_tipo_doc());
                sesion.setAttribute("list_año", li.lista_años());

                String no_trabajador = request.getParameter("no_tr");
                String ap_p = request.getParameter("ap_p");
                String ap_m = request.getParameter("ap_m");
                String ti_doc = request.getParameter("ti_doc");
                //response.sendRedirect("views/Trabajador/Reg_Trabajador.html?nro_doc=" + dni + "&ap_p=" + ap_p + "&ap_m=" + ap_m + "&ti_doc=" + ti_doc + "&no_tr=" + no_trabajador);
            } else {
                String hl = request.getParameter("hl");
                sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
                sesion.setAttribute("Lista_detalle_academico", carga.Lista_detalle_academico(idtr, facu, eap, "", ""));

                //response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?" + "idtr=" + idtr + "&academico=true" + "&hl=" + hl + "&eap=" + eap + "&facultad=" + facu);
            }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        String opc = request.getParameter("opc");

        HttpSession sesion = request.getSession(true);
        String iduser = (String) sesion.getAttribute("IDUSER");
        String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
        String semestre = request.getParameter("semestre");
        try {


            if (opc.equals("Procesar")) {
                String iddgp = CCriptografiar.Desencriptar(request.getParameter("dgp"));
                String idpro = CCriptografiar.Desencriptar(request.getParameter("proceso"));
                carga.PROCESAR_CARGA_ACADEMICA(idpro, iddgp);
                dataMap.put("status", true);
            }


            if (opc.equals("horarioCursosAcademico")) {
                //response.sendRedirect("views/Academico/Carga_Academica/horarioCursosAcademico.html");
            }
            if (opc.equals("updateCAData")) {
                System.out.println("::Enter to update CA");
                dataMap.put("responseWSCA", carga.syncupCargaAcademica(semestre, globalProperties.DOCENTESXCURSO_METHOD));
                dataMap.put("status", true);
            }

            if (opc.equals("Registrar_CA")) {
                /*Registrar proceso de carga academica*/
                String CA_TIPO_HORA_PAGO = CCriptografiar.Desencriptar(request.getParameter("idTiHoraPago"));
                double CA_TOTAL_HL = Double.parseDouble(request.getParameter("HL"));
                String FE_DESDE = DateFormat.toFormat3(request.getParameter("DESDE"));
                String FE_HASTA = DateFormat.toFormat3(request.getParameter("HASTA"));
                int numero = Integer.parseInt(request.getParameter("num_itera"));
                String ID_TRABAJADOR = CCriptografiar.Desencriptar(request.getParameter("id"));

                String eap = request.getParameter("eap");
                String facultad = request.getParameter("facultad");
                String ciclo = request.getParameter("ciclo");
                /* REGISTRAR REQUERIMIENTO*/
                DGP d = new DGP();
                d.setFe_desde(FE_DESDE);
                d.setFe_hasta(FE_HASTA);
                d.setId_puesto("PUT-000482");
                d.setId_requerimiento("REQ-0018");
                d.setId_trabajador(ID_TRABAJADOR);
                /**/
                System.out.println("::Obteniendo Datos de IP...");
                String ipUser = UserMachineProperties.getAll();
                d.setIp_usuario(ipUser);
                System.out.println("::Datos de IP obtenidos");
                d.setUs_creacion(iduser);
                System.out.println("::Insertando DGP...");
                String iddgp = carga.insertDGP(d);
                System.out.println("::Dgp registrado");
                /*PROCESO CARGA ACADEMICA*/
                System.out.println("::Insertando proceso carga academica...");
                String ID_PROCESO_CARGA_AC = carga.INSERT_PROCESO_CARGA_ACADEMICA(null, null, CA_TIPO_HORA_PAGO,
                        CA_TOTAL_HL, FE_DESDE, FE_HASTA, "0", iduser, null, null, null,
                        ipUser, iduser, iddgp.trim());
                System.out.println("::Proceso Carga academica registrado.");
                System.out.println("::Iterando Cuotas...");

                /*CUOTAS PAGO DOCENTE*/
                for (int i = 1; i <= numero; i++) {
                    /*pago docente (iterar)*/
                    String NU_CUOTA = "" + i;
                    double CA_CUOTA = Double.parseDouble(request.getParameter("MES" + i));
                    /*CORREGIR FECHAS*/
                    String FE_PAGO = request.getParameter("fe_pago" + i);
                    String id = carga.INSERT_PAGO_DOCENTE(null, NU_CUOTA, CA_CUOTA, FE_PAGO, null,
                            ID_PROCESO_CARGA_AC.trim(), null, null, null, ipUser, iduser);
                    System.out.println("::Cuota " + i + " " + "registrada. ");
                }
                System.out.println("::Insertando detalle carga academica...");
                /*DETALLE CARGA ACADEMICA*/
                List<V_Detalle_Carga_Academica> lCargaAcad = carga.Lista_detalle_academico(ID_TRABAJADOR, facultad, eap, ciclo, "");
                for (int i = 0; i < lCargaAcad.size(); i++) {
                    carga.INSERT_DETALLE_CARGA_ACADEMICA(null, ID_PROCESO_CARGA_AC.trim(), CCriptografiar.Desencriptar(lCargaAcad.get(i).getId_carga_academica()), "1");
                }
                String idrp = IReq.id_det_req_proc(iddgp.trim());
                /* REGISTRAR PRIMERA AUTORIZACION*/
                List<String> list = a.Det_Autorizacion(idrp);
                a.Insert_Autorizacion("", iddgp.trim(), "1", "P1", "", iduser, "", "", "", list.get(1).trim(), idrp.trim(), list.get(0));

                dataMap.put("dgp", CCriptografiar.Encriptar(iddgp));
                dataMap.put("proceso", CCriptografiar.Encriptar(idrp));
                dataMap.put("rpta", true);
            }



            if (opc.equals("initUpdateCAData")) {
                Boolean x = false;
                System.out.println("::Enter to initUpdateCAData");
                if (context.getAttribute("runnableCA") == null) {
                    ScheduledTest s = new ScheduledTest();
                    ScheduledFuture sc = s.runForAnHour(context);
                    Object obj = sc;
                    System.out.println("ScheduleFuture in servletContext:" + context.getAttribute("runnableCA"));
                    context.setAttribute("runnableCA", obj);
                    System.out.println("ScheduledFuture:" + sc.toString());
                    x = true;
                } else {
                    dataMap.put("message", "La tarea ya esta activa.");
                }

                dataMap.put("runUpdateCAData", x);

            }
            if (opc.equals("stopSyncUpCargaAcademica")) {
                Boolean x = false;
                System.out.println("::Enter to stopSyncUpCargaAcademica");
                if (context.getAttribute("runnableCA") != null) {
                    System.out.println("ScheduleFuture__:" + context.getAttribute("runnableCA"));
                    System.out.println("::Stoping schedule...");
                    ScheduledFuture beeperHandle = (ScheduledFuture) context.getAttribute("runnableCA");
                    x = beeperHandle.cancel(true);
                    context.setAttribute("runnableCA", null);
                    System.out.println("----Update finished.");
                } else {
                    dataMap.put("message", "No se encontró la tarea o no hay ninguna activa");
                }
                dataMap.put("cancelProcess", x);
            }
            dataMap.put("status", true);
        } catch (Exception e) {
            dataMap.put("status", false);
            dataMap.put("rpta", false);
            dataMap.put("message", e.getMessage());
            dataMap.put("errorMessage", Strings.ERROR_MESSAGE);
        }
        return new ResponseEntity<>(dataMap, HttpStatus.OK);
    }



}
