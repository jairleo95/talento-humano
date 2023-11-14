/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.AcademicCharge;
import com.app.domain.model.DGP;
import com.app.domain.model.ProcesoCargaAcademica;
import com.app.domain.model.V_Detalle_Carga_Academica;

/**
 *
 * @author ALFA 3
 */
public interface ICargaAcademicaDAO {

    String DNI_ID_TRABAJADOR(String dni);

    void INSERT_CARGA_ACADEMICA(String ID_CARGA_ACADEMICA, String ES_CARGA_ACADEMICA, String CAMPUS, String ES_TIPO_DOC, String NU_DOC, String AP_PATERNO, String AP_MATERNO, String NO_TRABAJADOR, String NO_FACULTAD, String NO_EAP, String DE_CARGA, String NO_CURSO, String NU_GRUPO, String DE_HORARIO, int CA_HLAB, String DE_CONDICION, String DE_TIPO_CURSO, String ES_PROCESADO, String FE_CREACION);

    String INSERT_PROCESO_CARGA_ACADEMICA(String ID_PROCESO_CARGA_AC, String ES_PROCESO_CARGA_AC, String CA_TIPO_HORA_PAGO, double CA_TOTAL_HL, String FE_DESDE, String FE_HASTA, String ES_PROCESADO, String US_CREACION, String FE_CREACION, String US_MODIF, String FE_MODIF, String IP_USUARIO, String NO_USUARIO, String ID_DGP);

    List<AcademicCharge> ListCarAca();

    List<V_Detalle_Carga_Academica> Lista_detalle_academico(String idtr, String facultad, String eap, String ciclo, String dni);

    String INSERT_DETALLE_CARGA_ACADEMICA(String ID_DETALLE_CARGA_ACADEMICA, String ID_PROCESO_CARGA_AC, String ID_CARGA_ACADEMICA, String ES_DETALLE_CARGA);

    String INSERT_CARGA_ACADEMICA(String ID_CARGA_ACADEMICA, String ES_CARGA_ACADEMICA, String CAMPUS, String ES_TIPO_DOC, String NU_DOC, String AP_PATERNO, String AP_MATERNO, String NO_TRABAJADOR, String NO_FACULTAD, String NO_EAP, String DE_CARGA, String NO_CURSO, String NU_GRUPO, String DE_HORARIO, double CA_HLAB, String DE_CONDICION, String DE_TIPO_CURSO, String ES_PROCESADO, String FE_CREACION, String ID_PROCESO_CARGA_AC);

    String INSERT_PAGO_DOCENTE(String ID_PAGO_DOCENTE, String NU_CUOTA, double CA_CUOTA, String FE_PAGO, String ES_PAGO_DOCENTE, String ID_PROCESO_CARGA_AC, String FE_CREACION, String US_MODIF, String FE_MODIF, String IP_USUARIO, String NO_USUARIO);

    String insertDGP(DGP dgp);

    void PROCESAR_CARGA_ACADEMICA(String id_proceso, String iddgp);

    List<Map<String, ?>> Cuotas_Pago_Docente(String fe_desde, String fe_hasta, double pago_semanal);

    List<Map<String, ?>> List_Carga_Academica_WS(String semestre);

    ProcesoCargaAcademica getProcesoCargaAcademciaById(String id);

    String syncupCargaAcademica(String semestre,String docentesXCurso[]);

}
