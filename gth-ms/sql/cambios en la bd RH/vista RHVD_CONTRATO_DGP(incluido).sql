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
    G.ES_MFL,
    c.practicante 
  FROM RHVD_CONTRATOS_HISTORIAL c
  LEFT OUTER JOIN RHVD_DET_DGP G
  ON(c.ID_DGP=G.ID_DGP)