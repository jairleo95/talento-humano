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
    CA_BONIFICACION_P_SP           NUMBER,
    PRACTICANTE_SP                 CHAR)
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
, PRACTICANTE                 =PRACTICANTE_SP 
WHERE ID_CONTRATO               = ID_CONTRATO_SP;
commit;
 RHSP_VAL_ESTADO_CONTRATO;
 commit;
END ;




create or replace PROCEDURE              "RHSP_INSERT_CONTRATO" (
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
    ES_MFL_SP                      CHAR,
    PRACTICANTE_SP                 CHAR)
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
      ES_MFL,
      PRACTICANTE
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
      ES_MFL_SP,
      PRACTICANTE_SP
    );
END ;

















