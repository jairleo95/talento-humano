SELECT id_contrato ,
  ID_DETALLE_CENTRO_COSTO
FROM rhtm_contrato c ,
  (SELECT id_dgp ,
    ID_DETALLE_CENTRO_COSTO
  FROM RHTD_DETALLE_CENTRO_COSTO
  WHERE id_dgp    IS NOT NULL
  AND id_contrato IS NULL
  ) d
WHERE c.ID_DGP=d.ID_DGP;
ALTER TABLE RHTM_CONTRATO
DROP CONSTRAINT CFK_ID_ANNO_01;
ALTER TABLE RHTM_CONTRATO
DROP COLUMN ID_ANNO;
DROP TABLE rhtr_anno;
COMMIT;
CREATE OR REPLACE PROCEDURE "RHSP_INSERT_CONTRATO"(
    ID_CONTRATO_SP                 CHAR,
    ID_DGP_SP                      CHAR,
    FE_DESDE_SP                    DATE,
    FE_HASTA_SP                    DATE,
    FE_CESE_SP                     DATE,
    ID_FUNC_SP                     CHAR,
    LI_CONDICION_SP                CHAR,
    CA_SUELDO_SP                   NUMBER,
    CA_REINTEGRO_SP                NUMBER,
    CA_ASIG_FAMILIAR_SP            NUMBER,
    HO_SEMANA_SP                   NUMBER,
    NU_HORAS_LAB_SP                NUMBER,
    DIA_CONTRATO_SP                NUMBER,
    TI_TRABAJADOR_SP               CHAR,
    LI_REGIMEN_LABORAL_SP          CHAR,
    ES_DISCAPACIDAD_SP             CHAR,
    TI_CONTRATO_SP                 CHAR,
    LI_REGIMEN_PENSIONARIO_SP      CHAR,
    ES_CONTRATO_TRABAJADOR_SP      CHAR,
    US_CREACION_SP                 CHAR,
    FE_CREACION_SP                 DATE,
    US_MODIF_SP                    CHAR,
    FE_MODIF_SP                    DATE,
    US_IP_SP                       VARCHAR2,
    FE_VACACIO_INI_SP              DATE,
    FE_VACACIO_FIN_SP              DATE,
    ES_CONTRATO_SP                 CHAR,
    ID_FILIAL_SP                   CHAR,
    ID_PUESTO_SP                   CHAR,
    CA_BONO_ALIMENTO_SP            NUMBER,
    LI_TIPO_CONVENIO_SP            CHAR,
    ES_FIRMO_CONTRATO_SP           CHAR,
    NU_CONTRATO_SP                 NUMBER,
    DE_OBSERVACION_SP              VARCHAR2,
    ES_APOYO_SP                    CHAR,
    TI_HORA_PAGO_SP                VARCHAR2,
    NU_DOCUMENTO_SP                CHAR,
    ES_ENTREGAR_DOC_REGLAMENTOS_SP CHAR,
    ES_REGISTRO_HUELLA_SP          CHAR,
    DE_REGISTRO_SISTEM_REMU_SP     CHAR,
    ID_TRABAJADOR_SP               CHAR,
    CA_SUELDO_TOTAL_SP             NUMBER,
    ID_REGIMEN_LABORAL_SP          CHAR,
    ID_MODALIDAD_SP                CHAR,
    ID_SUB_MODALIDAD_SP            CHAR,
    CO_GR_OCUPACION_SP             CHAR,
    FE_SUSCRIPCION_SP              DATE,
    CO_TI_MONEDA_SP                CHAR,
    CO_TI_REM_VARIAB_SP            CHAR,
    DE_REMU_ESPECIE_SP             CHAR,
    DE_RUC_EMP_TRAB_SP             CHAR,
    CO_SUCURSAL_SP                 CHAR,
    DE_MYPE_SP                     CHAR,
    ES_TI_CONTRATACION_SP          CHAR,
    CA_BEV_SP                      NUMBER,
    ID_TIPO_PLANILLA_SP            CHAR,
    ES_REMUNERACION_PROCESADO_SP   VARCHAR2,
    ID_DETALLE_HORARIO_SP          CHAR,
    ID_PLANTILLA_CONTRACTUAL_SP    CHAR,
    CA_BONIFICACION_P_SP           NUMBER,
    ES_MFL_SP                      CHAR)
IS
BEGIN
  INSERT
  INTO RHTM_CONTRATO
    (
      ID_CONTRATO,
      ID_DGP,
      FE_DESDE,
      FE_HASTA,
      FE_CESE,
      ID_FUNC,
      LI_CONDICION,
      CA_SUELDO,
      CA_REINTEGRO,
      CA_ASIG_FAMILIAR,
      HO_SEMANA,
      NU_HORAS_LAB,
      DIA_CONTRATO,
      TI_TRABAJADOR,
      LI_REGIMEN_LABORAL,
      ES_DISCAPACIDAD,
      TI_CONTRATO,
      LI_REGIMEN_PENSIONARIO,
      ES_CONTRATO_TRABAJADOR,
      US_CREACION,
      FE_CREACION,
      US_MODIF,
      FE_MODIF,
      US_IP,
      FE_VACACIO_INI,
      FE_VACACIO_FIN,
      ES_CONTRATO,
      ID_FILIAL,
      ID_PUESTO,
      CA_BONO_ALIMENTO,
      LI_TIPO_CONVENIO,
      ES_FIRMO_CONTRATO,
      NU_CONTRATO,
      DE_OBSERVACION,
      ES_APOYO,
      TI_HORA_PAGO,
      NU_DOCUMENTO,
      ES_ENTREGAR_DOC_REGLAMENTOS,
      ES_REGISTRO_HUELLA,
      DE_REGISTRO_SISTEM_REMU,
      ID_TRABAJADOR,
      CA_SUELDO_TOTAL,
      ID_REGIMEN_LABORAL,
      ID_SUB_MODALIDAD,
      ID_GRUPO_OCUPACION,
      FE_SUSCRIPCION,
      CO_TI_MONEDA,
      CO_TI_REM_VARIAB,
      DE_REMU_ESPECIE,
      DE_RUC_EMP_TRAB,
      CO_SUCURSAL,
      DE_MYPE,
      ES_TI_CONTRATACION,
      CA_BEV,
      ID_TIPO_PLANILLA,
      ES_REMUNERACION_PROCESADO,
      ID_DETALLE_HORARIO,
      ID_PLANTILLA_CONTRACTUAL,
      CA_BONIFICACION_P,
      ES_MFL
    )
    VALUES
    (
      NULL ,
      ID_DGP_SP ,
      FE_DESDE_SP ,
      FE_HASTA_SP ,
      FE_CESE_SP ,
      ID_FUNC_SP ,
      LI_CONDICION_SP ,
      CA_SUELDO_SP ,
      CA_REINTEGRO_SP ,
      CA_ASIG_FAMILIAR_SP ,
      HO_SEMANA_SP ,
      NU_HORAS_LAB_SP ,
      DIA_CONTRATO_SP ,
      TI_TRABAJADOR_SP ,
      LI_REGIMEN_LABORAL_SP ,
      ES_DISCAPACIDAD_SP ,
      TI_CONTRATO_SP ,
      LI_REGIMEN_PENSIONARIO_SP ,
      ES_CONTRATO_TRABAJADOR_SP ,
      US_CREACION_SP ,
      SYSDATE ,
      US_MODIF_SP ,
      FE_MODIF_SP ,
      US_IP_SP ,
      FE_VACACIO_INI_SP ,
      FE_VACACIO_FIN_SP ,
      ES_CONTRATO_SP ,
      ID_FILIAL_SP ,
      ID_PUESTO_SP ,
      CA_BONO_ALIMENTO_SP ,
      LI_TIPO_CONVENIO_SP ,
      ES_FIRMO_CONTRATO_SP ,
      NU_CONTRATO_SP ,
      DE_OBSERVACION_SP ,
      ES_APOYO_SP ,
      TI_HORA_PAGO_SP ,
      NU_DOCUMENTO_SP ,
      ES_ENTREGAR_DOC_REGLAMENTOS_SP ,
      ES_REGISTRO_HUELLA_SP ,
      DE_REGISTRO_SISTEM_REMU_SP ,
      ID_TRABAJADOR_SP ,
      (CA_SUELDO_SP+CA_BONO_ALIMENTO_SP+CA_BEV_SP+CA_BONIFICACION_P_SP+CA_ASIG_FAMILIAR_SP) ,
      ID_REGIMEN_LABORAL_SP ,
      ID_SUB_MODALIDAD_SP ,
      CO_GR_OCUPACION_SP ,
      FE_SUSCRIPCION_SP ,
      CO_TI_MONEDA_SP ,
      CO_TI_REM_VARIAB_SP ,
      DE_REMU_ESPECIE_SP ,
      DE_RUC_EMP_TRAB_SP ,
      CO_SUCURSAL_SP ,
      DE_MYPE_SP ,
      ES_TI_CONTRATACION_SP ,
      CA_BEV_SP ,
      ID_TIPO_PLANILLA_SP ,
      ES_REMUNERACION_PROCESADO_SP ,
      ID_DETALLE_HORARIO_SP,
      ID_PLANTILLA_CONTRACTUAL_SP,
      CA_BONIFICACION_P_SP,
      ES_MFL_SP
    );
END ;
/


  CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_USUARIO" as
 SELECT
   t."ID_TRABAJADOR",
    t."ID_USUARIO",
    t."ID_ROL",
    t."ID_EMPLEADO",
    t."NO_USUARIO",
    t."PW_USUARIO",
    uc."NO_PUESTO",
    uc."ID_PUESTO",
    uc."NO_AREA",
    uc."ID_AREA",
    uc."NO_DEP",
    uc."ID_DEPARTAMENTO",
    uc."NO_DIRECCION",
    uc."ID_DIRECCION",
    uc."ID_SECCION",
    uc."NO_SECCION",
    uc."NO_TRABAJADOR",
    uc."AP_PATERNO",
    uc."AP_MATERNO",
    uc."FE_CREACION",
    uc."CL_TRA",
    uc."TE_TRABAJADOR",
    uc."LI_DI_DOM_A_D1",
    uc."DI_DOM_A_D2",
    uc."LI_DI_DOM_A_D3",
    uc."DI_DOM_A_D4",
    uc."LI_DI_DOM_A_D5",
    uc."DI_DOM_A_D6",
    uc."DI_DOM_A_REF",
    uc."DI_CORREO_PERSONAL",
    uc."DI_CORREO_INST",
    uc."LI_RELIGION",
    uc."NO_IGLESIA",
    uc."DE_CARGO",
    uc."LI_AUTORIDAD",
    uc."NO_AP_AUTORIDAD",
    uc."CL_AUTORIDAD",
    uc."ID_DISTRITO_UB",
    uc."ID_PROVINCIA_UB",
    uc."ID_DEPARTAMENTO_UB",
    uc."NO_DEPARTAMENTO_UB",
    uc."NO_PROVINCIA_UB",
    uc."NO_DISTRITO_UB",
    t."AR_FOTO",--<--
    t.NO_AR_FOTO --<--
  FROM
    (SELECT usu.*,
      emp.id_trabajador,
      ftr.AR_FOTO,    --<--
      ftr.NO_AR_FOTO  --<--
    FROM RHTC_USUARIO usu ,
         RHTD_EMPLEADO emp,
         ----------------------------------
         (SELECT empl.ID_TRABAJADOR, ftra.AR_FOTO, ftra.NO_AR_FOTO
          FROM RHTD_EMPLEADO empl
          LEFT OUTER JOIN RHTR_FOTOS_TRABAJADOR ftra 
          ON empl.id_trabajador = ftra.id_trabajador) ftr
          ----------------------------------
    WHERE usu.ID_EMPLEADO = emp.ID_EMPLEADO
      AND usu.es_usuario    ='1'
      AND ftr.ID_TRABAJADOR= emp.ID_TRABAJADOR
    ) t
  , ( SELECT utr.ID_TRABAJADOR,
  ID_USUARIO,
  ID_ROL,
  ID_EMPLEADO,
  NO_USUARIO,
  PW_USUARIO,
  NO_TRABAJADOR,
  AP_PATERNO,
  AP_MATERNO,
  CL_TRA,
  TE_TRABAJADOR,
  LI_DI_DOM_A_D1,
  DI_DOM_A_D2,
  LI_DI_DOM_A_D3,
  DI_DOM_A_D4,
  LI_DI_DOM_A_D5,
  DI_DOM_A_D6,
  DI_DOM_A_REF,
  DI_CORREO_PERSONAL,
  DI_CORREO_INST,
  LI_RELIGION,
  NO_IGLESIA,
  DE_CARGO,
  LI_AUTORIDAD,
  NO_AP_AUTORIDAD,
  CL_AUTORIDAD,
  ID_DISTRITO_UB,
  ID_PROVINCIA_UB,
  ID_DEPARTAMENTO_UB,
  NO_DEPARTAMENTO_UB,
  NO_PROVINCIA_UB,
  NO_DISTRITO_UB,
  ID_CONTRATO,
  ID_DGP,
  FE_DESDE,
  FE_HASTA,
  FE_CESE,
  ID_FUNC,
  LI_CONDICION,
  CA_SUELDO,
  CA_REINTEGRO,
  CA_ASIG_FAMILIAR,
  HO_SEMANA,
  NU_HORAS_LAB,
  DIA_CONTRATO,
  TI_TRABAJADOR,
  LI_REGIMEN_LABORAL,
  ES_DISCAPACIDAD,
  TI_CONTRATO,
  LI_REGIMEN_PENSIONARIO,
  ES_CONTRATO_TRABAJADOR,
  US_CREACION,
  FE_CREACION,
  US_MODIF,
  FE_MODIF,
  US_IP,
  FE_VACACIO_INI,
  FE_VACACIO_FIN,
  ES_CONTRATO,
  ID_FILIAL,
  ID_PUESTO,
  CA_BONO_ALIMENTO,
  LI_TIPO_CONVENIO,
  ES_FIRMO_CONTRATO,
  NU_CONTRATO,
  DE_OBSERVACION,
  ES_APOYO,
  TI_HORA_PAGO,
  NU_DOCUMENTO,

  ES_ENTREGAR_DOC_REGLAMENTOS,
  ES_REGISTRO_HUELLA,
  DE_REGISTRO_SISTEM_REMU,
  CA_SUELDO_TOTAL,
  ID_REGIMEN_LABORAL,
  ID_SUB_MODALIDAD,
  ID_GRUPO_OCUPACION,
  FE_SUSCRIPCION,
  CO_TI_MONEDA,
  CO_TI_REM_VARIAB,
  DE_REMU_ESPECIE,
  DE_RUC_EMP_TRAB,
  CO_SUCURSAL,
  DE_MYPE,
  ES_TI_CONTRATACION,
  CA_BEV,
  ID_TIPO_PLANILLA,
  ES_REMUNERACION_PROCESADO,
  ID_DETALLE_HORARIO,
  ID_PLANTILLA_CONTRACTUAL,
  CA_BONIFICACION_P,
  ES_MFL,
  ES_SECRE_IS,
  NO_PUESTO,
  NO_AREA,
  ID_AREA,
  NO_DEP,
  ID_DEPARTAMENTO,
  NO_DIRECCION,
  ID_DIRECCION,
  ID_SECCION,
  NO_SECCION
FROM
  (SELECT e.id_trabajador,
    u.id_usuario,
    u.id_rol ,
    e.id_empleado,
    u.no_usuario,
    u.pw_usuario,
    dt.no_trabajador,
    dt.ap_paterno,
    dt.ap_materno ,
    --c.fe_creacion,
    dt.CL_TRA,
    dt.TE_TRABAJADOR,
    dt."LI_DI_DOM_A_D1",
    dt."DI_DOM_A_D2",
    dt."LI_DI_DOM_A_D3",
    dt."DI_DOM_A_D4",
    dt."LI_DI_DOM_A_D5",
    dt."DI_DOM_A_D6",
    dt."DI_DOM_A_REF",
    dt."DI_CORREO_PERSONAL",
    dt."DI_CORREO_INST",
    dt."LI_RELIGION",
    dt."NO_IGLESIA",
    dt."DE_CARGO",
    dt."LI_AUTORIDAD",
    dt."NO_AP_AUTORIDAD",
    dt."CL_AUTORIDAD",
    ub.ID_DISTRITO     AS ID_DISTRITO_UB,
    ub.ID_PROVINCIA    AS ID_PROVINCIA_UB,
    ub.ID_DEPARTAMENTO AS ID_DEPARTAMENTO_UB,
    ub.NO_DEPARTAMENTO AS NO_DEPARTAMENTO_UB,
    ub.NO_PROVINCIA    AS NO_PROVINCIA_UB,
    ub.NO_DISTRITO     AS NO_DISTRITO_UB
  FROM rhtd_empleado e,
    rhtc_usuario u ,
    --rhvd_puesto_direccion dd ,
    rhtm_trabajador dt,
    RHTR_ROL r,
    RHVD_UBIGEO ub
    --, rhtm_contrato c
  WHERE u.ES_USUARIO ='1'
  AND u.id_empleado  = e.id_empleado
    --AND dd.id_puesto       =c.id_puesto
  AND r.id_rol        =u.id_rol
  AND r.ES_ROL        ='1'
  AND ub.ID_DISTRITO  =dt.ID_DI_DOM_A_DISTRITO
  AND e.id_trabajador = dt.id_trabajador
    -- AND  c.es_contrato      =1
    --AND dt.id_trabajador = c.id_trabajador
  ) utr 
LEFT OUTER JOIN
  (SELECT ct.*,
    dd.no_puesto,
    -- dd.id_puesto,
    dd.no_area,
    dd.id_area,
    dd.no_dep ,
    dd.id_departamento,
    dd.no_direccion,
    dd.id_direccion,
    dd.id_seccion,
    dd.no_seccion
  FROM rhtm_contrato ct ,
    rhvd_puesto_direccion dd
  WHERE dd.id_puesto =ct.id_puesto
  AND ct.es_contrato =1
  ) c
ON(utr.id_trabajador = c.id_trabajador)) uc  
where (uc.id_usuario    = t.id_usuario);
 



  CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_DET_DGP" as
  SELECT dg."ID_DGP",
    dg."FE_DESDE",
    dg."FE_HASTA",
    dg."CA_SUELDO",
    dg."DE_DIAS_TRABAJO",
    dg."NO_PUESTO",
    dg."ID_PUESTO",
    dg."ID_REQUERIMIENTO",
    dg."ID_TRABAJADOR",
    dg."CO_RUC",
    dg."DE_LUGAR_SERVICIO",
    dg."DE_SERVICIO",
    dg."DE_PERIODO_PAGO",
    dg."DE_DOMICILIO_FISCAL",
    dg."DE_SUBVENCION",
    dg."DE_HORARIO_CAPACITACION",
    dg."DE_HORARIO_REFRIGERIO",
    dg."DE_DIAS_CAPACITACION",
    dg."ES_DGP",
    dg."US_CREACION",
    dg."FE_CREACION",
    dg."US_MODIF",
    dg."FE_MODIF",
    dg."IP_USUARIO",
    dg."NO_REQ",
    dg."CA_BONO_ALIMENTARIO",
    dg."DE_BEV",
    dg."DE_ANTECEDENTES_POLICIALES",
    dg."ES_CERTIFICADO_SALUD",
    dg."ID_DEPARTAMENTO",
    dg."ID_DIRECCION",
    dg."ID_SECCION",
    dg."ID_AREA",
    dg."ID_TIPO_PLANILLA",
    dg."TI_PLANILLA",
    dg.ID_DETALLE_HORARIO,
    dg.no_dep,
    dg.no_usuario_crea,
    (usu.NO_USUARIO)AS no_usuario_mod,
    usu.no_trabajador
    ||' '
    ||usu.ap_paterno AS no_trab_us_mod,
    dg."LI_MOTIVO",
    dg."ES_MFL",
    dg.ca_bonificacion_p,
    dg.no_area,
    dg.no_trab_us_cr,
    dg.no_seccion,
    dg.CA_ASIG_FAMILIAR,
    dg.nombre_trabajador, RHFU_CA_HORAS_HORARIO_DGP(dg.id_dgp) as ca_horas_horario
  FROM
    (SELECT a."ID_DGP",
      a."FE_DESDE",
      a."FE_HASTA",
      a."CA_SUELDO",
      a."DE_DIAS_TRABAJO",
      a."NO_PUESTO",
      a."ID_PUESTO",
      a."ID_REQUERIMIENTO",
      a."ID_TRABAJADOR",
      a."CO_RUC",
      a."DE_LUGAR_SERVICIO",
      a."DE_SERVICIO",
      a."DE_PERIODO_PAGO",
      a."DE_DOMICILIO_FISCAL",
      a."DE_SUBVENCION",
      a."DE_HORARIO_CAPACITACION",
      a."DE_HORARIO_REFRIGERIO",
      a."DE_DIAS_CAPACITACION",
      a."ES_DGP",
      a."US_CREACION",
      a."FE_CREACION",
      a."US_MODIF",
      a."FE_MODIF",
      a."IP_USUARIO",
      a."NO_REQ",
      a."CA_BONO_ALIMENTARIO",
      a."DE_BEV",
      a."DE_ANTECEDENTES_POLICIALES",
      a."ES_CERTIFICADO_SALUD",
      a."ID_DEPARTAMENTO",
      a."ID_DIRECCION",
      a."ID_SECCION",
      a."ID_AREA",
      a."ID_TIPO_PLANILLA",
      a."TI_PLANILLA",
      (a.NO_USUARIO)AS no_usuario_crea,
      a."LI_MOTIVO",
      a."ES_MFL",
      CASE
        WHEN dh.id_detalle_horario IS NULL
        THEN '0'
        ELSE dh.id_detalle_horario
      END AS ID_DETALLE_HORARIO,
      a.no_dep,
      a.ca_bonificacion_p,
      a.no_area,
      a.no_trab_us_cr,
      a.no_seccion,
      a.CA_ASIG_FAMILIAR,
      a.nombre_trabajador
    FROM
      (SELECT d.ID_DGP,
        TO_CHAR(d.fe_desde,'dd/mm/yyyy') AS fe_desde,
        TO_CHAR(d.fe_hasta,'dd/mm/yyyy') AS fe_hasta ,
        d.CA_sueldo,
        d.DE_DIAS_TRABAJO,
        pu.NO_PUESTO,
        d.ID_PUESTO,
        d.ID_REQUERIMIENTO,
        d.ID_TRABAJADOR,
        d.CO_RUC,
        d.DE_LUGAR_SERVICIO,
        d.DE_SERVICIO,
        d.DE_PERIODO_PAGO,
        d.DE_DOMICILIO_FISCAL,
        d.DE_SUBVENCION,
        d.DE_HORARIO_CAPACITACION,
        d.DE_HORARIO_REFRIGERIO,
        d.DE_DIAS_CAPACITACION,
        d.ES_DGP,
        d.US_CREACION,
        TO_CHAR(d.FE_CREACION,'dd/mm/yyyy HH:MI:SS') AS FE_CREACION,
        d.US_MODIF,
        d.FE_MODIF,
        d.IP_USUARIO,
        r.NO_REQ,
        d.CA_BONO_ALIMENTARIO,
        d.DE_BEV,
        d.DE_ANTECEDENTES_POLICIALES,
        d.ES_CERTIFICADO_SALUD,
        pu.ID_DEPARTAMENTO,
        pu.ID_DIRECCION,
        pu.ID_SECCION,
        pu.ID_AREA,
        r.ID_TIPO_PLANILLA,
        t.TI_PLANILLA,
        pu.no_dep,
        u.NO_USUARIO,
        u.no_trabajador
        ||' '
        ||u.ap_paterno AS no_trab_us_cr,
        d.LI_MOTIVO,
        d.ES_MFL,
        d.ca_bonificacion_p,
        pu.no_area,
        pu.no_seccion,
        d.CA_ASIG_FAMILIAR,
        tr.ap_paterno
        ||' '
        || tr.ap_materno
        ||' '
        ||tr.no_trabajador AS nombre_trabajador
      FROM RHTM_DGP d ,
        RHTR_REQUERIMIENTO r ,
        RHVD_PUESTO_DIRECCION pu,
        RHTR_TIPO_PLANILLA t,
        RHVD_USUARIO u,
        RHTM_TRABAJADOR tr
      WHERE r.ID_REQUERIMIENTO = d.ID_REQUERIMIENTO
      AND pu.id_puesto         = d.id_puesto
      AND t.ID_TIPO_PLANILLA   = r.ID_TIPO_PLANILLA
      AND u.ID_USUARIO         = d.US_CREACION
      AND tr.id_trabajador     =d.id_trabajador
      )a
    LEFT OUTER JOIN RHTD_DETALLE_HORARIO dh
    ON ( dh.ID_DGP = a.ID_DGP)
    )dg
  LEFT OUTER JOIN RHVD_USUARIO usu
  ON(usu.ID_USUARIO=dg."US_MODIF");
 


  CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_CONTRATOS_HISTORIAL" as
 
 SELECT a."ID_CONTRATO",
    a."ID_DGP",
    a.fe_desde,
    a.fe_hasta,
    a.fe_cese,
    a."ID_FUNC",
    a."LI_CONDICION",
    a."CA_SUELDO",
    a."CA_REINTEGRO",
    a."CA_ASIG_FAMILIAR",
    a."HO_SEMANA",
    a."NU_HORAS_LAB",
    a."DIA_CONTRATO",
    a."TI_TRABAJADOR",
    a."LI_REGIMEN_LABORAL",
    a."ES_DISCAPACIDAD",
    a."TI_CONTRATO",
    a."LI_REGIMEN_PENSIONARIO",
    a."ES_CONTRATO_TRABAJADOR",
    a."US_CREACION",
    a.fe_creacion ,
    a."US_MODIF",
    a.fe_modif,
    a."US_IP",
    a.fe_vacacio_ini,
    a.fe_vacacio_fin,
    a."ES_CONTRATO",
    a."ID_FILIAL",
    a."ID_PUESTO",
    a."CA_BONO_ALIMENTO",
    a."LI_TIPO_CONVENIO",
    a."ES_FIRMO_CONTRATO",
    a."NU_CONTRATO",
    a."DE_OBSERVACION",
    a."ES_APOYO",
    a."TI_HORA_PAGO",
    a."NU_DOCUMENTO",
    
    a."ES_ENTREGAR_DOC_REGLAMENTOS",
    a."ES_REGISTRO_HUELLA",
    a."DE_REGISTRO_SISTEM_REMU",
    a."ID_TRABAJADOR",
    a."CA_SUELDO_TOTAL",
    a."ID_REGIMEN_LABORAL",
    a."ID_SUB_MODALIDAD",
    a."ID_GRUPO_OCUPACION",
    a.fe_suscripcion,
    a."CO_TI_MONEDA",
    a."CO_TI_REM_VARIAB",
    a."DE_REMU_ESPECIE",
    a."DE_RUC_EMP_TRAB",
    a."CO_SUCURSAL",
    a."DE_MYPE",
    a."ES_TI_CONTRATACION",
    a."CA_BEV",
    a.no_puesto,
    a.no_seccion,
    a.no_area,
    a.no_dep,
    a.no_direccion,
    g.DE_GRUPO_OCUPACION,
    a.DE_SUB_MODALIDAD,
    a.DE_MODALIDAD,
    a.NO_TRABAJADOR,
    a.AP_PATERNO,
    a.AP_MATERNO,
    a.LI_DI_DOM_A_D1,
    a.DI_DOM_A_D2 ,
    a.LI_DI_DOM_A_D3,
    a.DI_DOM_A_D4,
    a.LI_DI_DOM_A_D5,
    a.DI_DOM_A_D6,
    a.NO_PROVINCIA,
    a.NO_DISTRITO,
    a.fe_sus,
    a.fe_des,
    a.fe_has,
    a.ID_PLANTILLA_CONTRACTUAL,
    a.CA_BONIFICACION_P,
    a.ES_SECRE_IS,
    a.LI_RELIGION
FROM(
SELECT c."ID_CONTRATO",
    c."ID_DGP",
    TO_CHAR(c."FE_DESDE",'dd/mm/yyyy') AS fe_desde,
    TO_CHAR(c."FE_HASTA",'dd/mm/yyyy') AS fe_hasta,
    TO_CHAR(c."FE_CESE",'dd/mm/yyyy')  AS fe_cese,
    c."ID_FUNC",
    c."LI_CONDICION",
    c."CA_SUELDO",
    c."CA_REINTEGRO",
    c."CA_ASIG_FAMILIAR",
    c."HO_SEMANA",
    c."NU_HORAS_LAB",
    c."DIA_CONTRATO",
    c."TI_TRABAJADOR",
    c."LI_REGIMEN_LABORAL",
    c."ES_DISCAPACIDAD",
    c."TI_CONTRATO",
    c."LI_REGIMEN_PENSIONARIO",
    c."ES_CONTRATO_TRABAJADOR",
    c."US_CREACION",
    TO_CHAR(c."FE_CREACION",'dd/mm/yyyy') AS fe_creacion,
    c."US_MODIF",
    TO_CHAR(c."FE_MODIF",'dd/mm/yyyy') AS fe_modif,
    c."US_IP",
    TO_CHAR(c."FE_VACACIO_INI",'dd/mm/yyyy') AS fe_vacacio_ini,
    TO_CHAR(c."FE_VACACIO_FIN",'dd/mm/yyyy') AS fe_vacacio_fin,
    c."ES_CONTRATO",
    c."ID_FILIAL",
    c."ID_PUESTO",
    c."CA_BONO_ALIMENTO",
    c."LI_TIPO_CONVENIO",
    c."ES_FIRMO_CONTRATO",
    c."NU_CONTRATO",
    c."DE_OBSERVACION",
    c."ES_APOYO",
    c."TI_HORA_PAGO",
    (tr."NU_DOC") AS "NU_DOCUMENTO",
 
    c."ES_ENTREGAR_DOC_REGLAMENTOS",
    c."ES_REGISTRO_HUELLA",
    c."DE_REGISTRO_SISTEM_REMU",
    c."ID_TRABAJADOR",
    c."CA_SUELDO_TOTAL",
    c."ID_REGIMEN_LABORAL",
    c."ID_SUB_MODALIDAD",
    c."ID_GRUPO_OCUPACION",
    TO_CHAR(c."FE_SUSCRIPCION",'dd/mm/yyyy') AS fe_suscripcion,
    c."CO_TI_MONEDA",
    c."CO_TI_REM_VARIAB",
    c."DE_REMU_ESPECIE",
    c."DE_RUC_EMP_TRAB",
    c."CO_SUCURSAL",
    c."DE_MYPE",
    c."ES_TI_CONTRATACION",
    c."CA_BEV",
    pd.no_puesto,
    pd.no_seccion,
    pd.no_area,
    pd.no_dep,
    pd.no_direccion,
    s.DE_SUB_MODALIDAD,
    m.DE_MODALIDAD,
    tr.NO_TRABAJADOR,
    tr.AP_PATERNO,
    tr.AP_MATERNO,
    tr.LI_DI_DOM_A_D1,
    tr.DI_DOM_A_D2 ,
    tr.LI_DI_DOM_A_D3,
    tr.DI_DOM_A_D4,
    tr.LI_DI_DOM_A_D5,
    tr.DI_DOM_A_D6,
    tr.LI_RELIGION,
    pr.NO_PROVINCIA,
    ds.NO_DISTRITO,
    TO_CHAR(c."FE_SUSCRIPCION",'dd/month/yyyy','nls_date_language=spanish') AS fe_sus,
    TO_CHAR(c."FE_DESDE",'dd/month/yyyy','nls_date_language=spanish')       AS fe_des,
    TO_CHAR(c."FE_HASTA",'dd/month/yyyy','nls_date_language=spanish')       AS fe_has,
    c.ID_PLANTILLA_CONTRACTUAL,
    c.CA_BONIFICACION_P,c.ES_SECRE_IS
  FROM rhtm_contrato c,
    RHVD_puesto_direccion pd,
    RHTX_MODALIDAD m ,
    RHTX_SUB_MODALIDAD s,
    RHTM_TRABAJADOR tr,
    RHTX_UB_PROVINCIA pr,
    RHTX_UB_DISTRITO ds
  WHERE c.id_puesto           = pd.id_puesto
  AND c.ID_TRABAJADOR         =tr.ID_TRABAJADOR
  AND tr.ID_DISTRITO          =ds.ID_DISTRITO
  AND ds.ID_PROVINCIA         =pr.ID_PROVINCIA
  AND s.ID_SUB_MODALIDAD      = c.ID_SUB_MODALIDAD
  AND s.ID_MODALIDAD          = m.ID_MODALIDAD
  AND c.ES_CONTRATO_TRABAJADOR='1') a LEFT OUTER JOIN RHTX_GRUPO_OCUPACION g ON(g.ID_GRUPO_OCUPACION  = a.ID_GRUPO_OCUPACION);
 


CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_CONTRATO_DGP"

AS
  SELECT c."ID_CONTRATO",
    c."ID_DGP",
    c."FE_DESDE",
    c."FE_HASTA",
    c."FE_CESE",
    c."ID_FUNC",
    c."LI_CONDICION",
    c."CA_SUELDO",
    c."CA_REINTEGRO",
    c."CA_ASIG_FAMILIAR",
    c."HO_SEMANA",
    c."NU_HORAS_LAB",
    c."DIA_CONTRATO",
    c."TI_TRABAJADOR",
    c."LI_REGIMEN_LABORAL",
    c."ES_DISCAPACIDAD",
    c."TI_CONTRATO",
    c."LI_REGIMEN_PENSIONARIO",
    c."ES_CONTRATO_TRABAJADOR",
    c."US_CREACION",
    c."FE_CREACION",
    c."US_MODIF",
    c."FE_MODIF",
    c."US_IP",
    c."FE_VACACIO_INI",
    c."FE_VACACIO_FIN",
    c."ES_CONTRATO",
    c."ID_FILIAL",
    c."ID_PUESTO",
    c."CA_BONO_ALIMENTO",
    c."LI_TIPO_CONVENIO",
    c."ES_FIRMO_CONTRATO",
    c."NU_CONTRATO",
    c."DE_OBSERVACION",
    c."ES_APOYO",
    c."TI_HORA_PAGO",
    c."NU_DOCUMENTO",

    c."ES_ENTREGAR_DOC_REGLAMENTOS",
    c."ES_REGISTRO_HUELLA",
    c."DE_REGISTRO_SISTEM_REMU",
    c."ID_TRABAJADOR",
    c."CA_SUELDO_TOTAL",
    c."ID_REGIMEN_LABORAL",
    c."ID_SUB_MODALIDAD",
    c."ID_GRUPO_OCUPACION",
    c."FE_SUSCRIPCION",
    c."CO_TI_MONEDA",
    c."CO_TI_REM_VARIAB",
    c."DE_REMU_ESPECIE",
    c."DE_RUC_EMP_TRAB",
    c."CO_SUCURSAL",
    c."DE_MYPE",
    c."ES_TI_CONTRATACION",
    c."CA_BEV",
    c."NO_PUESTO",
    c."NO_SECCION",
    c."NO_AREA",
    c."NO_DEP",
    c."NO_DIRECCION",
    c."DE_GRUPO_OCUPACION",
    c."DE_SUB_MODALIDAD",
    c."DE_MODALIDAD",
    c."NO_TRABAJADOR",
    c."AP_PATERNO",
    c."AP_MATERNO",
    c."LI_DI_DOM_A_D1",
    c."DI_DOM_A_D2",
    c."LI_DI_DOM_A_D3",
    c."DI_DOM_A_D4",
    c."LI_DI_DOM_A_D5",
    c."DI_DOM_A_D6",
    c."NO_PROVINCIA",
    c."NO_DISTRITO",
    c."FE_SUS",
    c."FE_DES",
    c."FE_HAS",
    c."ID_PLANTILLA_CONTRACTUAL",
    c."CA_BONIFICACION_P",
    G.LI_MOTIVO,
    (SELECT dd.id_direccion
    FROM rhvd_puesto_direccion dd
    WHERE dd.id_puesto=c.id_puesto
    GROUP BY(dd.id_direccion)
    ) AS id_direccion ,
    (SELECT dd.ID_DEPARTAMENTO
    FROM rhvd_puesto_direccion dd
    WHERE dd.id_puesto=c.id_puesto
    GROUP BY(dd.ID_DEPARTAMENTO)
    )AS id_departamento,
    (SELECT dd.ID_AREA
    FROM rhvd_puesto_direccion dd
    WHERE dd.id_puesto=c.id_puesto
    GROUP BY(dd.ID_AREA)
    )AS id_area,
    (SELECT dd.ID_SECCION
    FROM rhvd_puesto_direccion dd
    WHERE dd.id_puesto=c.id_puesto
    GROUP BY(dd.ID_SECCION)
    )AS id_seccion,
    G.ES_MFL
  FROM RHVD_CONTRATOS_HISTORIAL c
  LEFT OUTER JOIN RHVD_DET_DGP G
  ON(c.ID_DGP=G.ID_DGP);


DROP VIEW RHVD_USUARIO_TEMP;
COMMIT;


create or replace PROCEDURE              "RHSP_MODIF_CONTRATO" (
    ID_CONTRATO_SP                 CHAR,
    ID_DGP_SP                      CHAR,
    FE_DESDE_SP                    DATE,
    FE_HASTA_SP                    DATE,
    FE_CESE_SP                     DATE,
    ID_FUNC_SP                     CHAR,
    LI_CONDICION_SP                CHAR,
    CA_SUELDO_SP                   NUMBER,
    CA_REINTEGRO_SP                NUMBER,
    CA_ASIG_FAMILIAR_SP            NUMBER,
    HO_SEMANA_SP                   NUMBER,
    NU_HORAS_LAB_SP                NUMBER,
    DIA_CONTRATO_SP                NUMBER,
    TI_TRABAJADOR_SP               CHAR,
    LI_REGIMEN_LABORAL_SP          CHAR,
    ES_DISCAPACIDAD_SP             CHAR,
    TI_CONTRATO_SP                 CHAR,
    LI_REGIMEN_PENSIONARIO_SP      CHAR,
    ES_CONTRATO_TRABAJADOR_SP      CHAR,
    US_CREACION_SP                 CHAR,
    FE_CREACION_SP                 DATE,
    US_MODIF_SP                    CHAR,
    FE_MODIF_SP                    DATE,
    US_IP_SP                       VARCHAR2,
    FE_VACACIO_INI_SP              DATE,
    FE_VACACIO_FIN_SP              DATE,
    ES_CONTRATO_SP                 CHAR,
    ID_FILIAL_SP                   CHAR,
    ID_PUESTO_SP                   CHAR,
    CA_BONO_ALIMENTO_SP            NUMBER,
    LI_TIPO_CONVENIO_SP            CHAR,
    ES_FIRMO_CONTRATO_SP           CHAR,
    NU_CONTRATO_SP                 NUMBER,
    DE_OBSERVACION_SP              VARCHAR2,
    ES_APOYO_SP                    CHAR,
    TI_HORA_PAGO_SP                VARCHAR2,
    NU_DOCUMENTO_SP                CHAR,

    ES_ENTREGAR_DOC_REGLAMENTOS_SP CHAR,
    ES_REGISTRO_HUELLA_SP          CHAR,
    DE_REGISTRO_SISTEM_REMU_SP     CHAR,
    ID_TRABAJADOR_SP               CHAR,
    CA_SUELDO_TOTAL_SP             NUMBER,
    ID_REGIMEN_LABORAL_SP          CHAR,
    ID_MODALIDAD_SP                CHAR,
    ID_SUB_MODALIDAD_SP            CHAR,
    CO_GR_OCUPACION_SP             CHAR,
    FE_SUSCRIPCION_SP              DATE,
    CO_TI_MONEDA_SP                CHAR,
    CO_TI_REM_VARIAB_SP            CHAR,
    DE_REMU_ESPECIE_SP             CHAR,
    DE_RUC_EMP_TRAB_SP             CHAR,
    CO_SUCURSAL_SP                 CHAR,
    DE_MYPE_SP                     CHAR,
    ES_TI_CONTRATACION_SP          CHAR,
    CA_BEV_SP                      NUMBER,
    ID_TIPO_PLANILLA_SP            CHAR,
    ES_REMUNERACION_PROCESADO_SP   VARCHAR2,
    ID_DETALLE_HORARIO_SP          CHAR,
    ID_PLANTILLA_CONTRACTUAL_SP    CHAR,
    CA_BONIFICACION_P_SP              NUMBER)
IS
BEGIN
 
    UPDATE RHTM_CONTRATO
SET  FE_DESDE                    = FE_DESDE_SP
, FE_HASTA                    = FE_HASTA_SP
, FE_CESE                     = FE_CESE_SP
, ID_FUNC                     = ID_FUNC_SP
, LI_CONDICION                = LI_CONDICION_SP
, CA_SUELDO                   = CA_SUELDO_SP
, CA_REINTEGRO                = CA_REINTEGRO_SP
, CA_ASIG_FAMILIAR            = CA_ASIG_FAMILIAR_SP
, HO_SEMANA                   =  HO_SEMANA_SP
, NU_HORAS_LAB                = NU_HORAS_LAB_SP
, DIA_CONTRATO                = DIA_CONTRATO_SP
, TI_TRABAJADOR               = TI_TRABAJADOR_SP
, LI_REGIMEN_LABORAL          = LI_REGIMEN_LABORAL_SP
, ES_DISCAPACIDAD             = ES_DISCAPACIDAD_SP
, TI_CONTRATO                 = TI_CONTRATO_SP
, LI_REGIMEN_PENSIONARIO      = LI_REGIMEN_PENSIONARIO_SP
, US_MODIF                    = US_MODIF_SP
, FE_MODIF                    = SYSDATE
, US_IP                       = US_IP_SP
, FE_VACACIO_INI              = FE_VACACIO_INI_SP
, FE_VACACIO_FIN              = FE_VACACIO_FIN_SP
, ID_FILIAL                   = ID_FILIAL_SP
, ID_PUESTO                   = ID_PUESTO_SP
, CA_BONO_ALIMENTO            = CA_BONO_ALIMENTO_SP
, LI_TIPO_CONVENIO            = LI_TIPO_CONVENIO_SP
, ES_FIRMO_CONTRATO           = ES_FIRMO_CONTRATO_SP
, NU_CONTRATO                 = NU_CONTRATO_SP
, DE_OBSERVACION              = DE_OBSERVACION_SP
, ES_APOYO                    = ES_APOYO_SP
, TI_HORA_PAGO                = TI_HORA_PAGO_SP
, NU_DOCUMENTO                = NU_DOCUMENTO_SP

, ES_ENTREGAR_DOC_REGLAMENTOS = ES_ENTREGAR_DOC_REGLAMENTOS_SP
, ES_REGISTRO_HUELLA          = ES_REGISTRO_HUELLA_SP
, DE_REGISTRO_SISTEM_REMU     = DE_REGISTRO_SISTEM_REMU_SP
, ID_TRABAJADOR               = ID_TRABAJADOR_SP
, CA_SUELDO_TOTAL             = CA_SUELDO_TOTAL_SP
, ID_REGIMEN_LABORAL          = ID_REGIMEN_LABORAL_SP
, ID_SUB_MODALIDAD            = ID_SUB_MODALIDAD_SP
, ID_GRUPO_OCUPACION          = CO_GR_OCUPACION_SP
, FE_SUSCRIPCION              = FE_SUSCRIPCION_SP
, CO_TI_MONEDA                = CO_TI_MONEDA_SP
, CO_TI_REM_VARIAB            = CO_TI_REM_VARIAB_SP
, DE_REMU_ESPECIE             = DE_REMU_ESPECIE_SP
, DE_RUC_EMP_TRAB             = DE_RUC_EMP_TRAB_SP
, CO_SUCURSAL                 = CO_SUCURSAL_SP
, DE_MYPE                     = DE_MYPE_SP
, ES_TI_CONTRATACION          = ES_TI_CONTRATACION_SP
, CA_BEV                      = CA_BEV_SP
, ID_TIPO_PLANILLA            = ID_TIPO_PLANILLA_SP
, ES_REMUNERACION_PROCESADO   = ES_REMUNERACION_PROCESADO_SP
, ID_DETALLE_HORARIO          = ID_DETALLE_HORARIO_SP
, ID_PLANTILLA_CONTRACTUAL    = ID_PLANTILLA_CONTRACTUAL_SP
, CA_BONIFICACION_P           = CA_BONIFICACION_P_SP
WHERE ID_CONTRATO               = ID_CONTRATO_SP;
END ;
/