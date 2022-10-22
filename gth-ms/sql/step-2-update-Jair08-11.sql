
/*INICIO DE CAMBIOS EN LA BD*/

delete from RHTD_DETALLE_CENTRO_COSTO where ID_DGP is null and ID_CONTRATO is null;

create or replace TRIGGER "PROCESOSRH"."RHGT_INSERT_ID_DETALLE_CC" 
BEFORE INSERT ON RHTD_DETALLE_CENTRO_COSTO
FOR EACH ROW
 DECLARE
 id_new rhtd_detalle_centro_costo.id_detalle_centro_costo %type;
 count_row number;
BEGIN
SELECT COUNT(id_detalle_centro_costo) INTO count_row  FROM RHTD_DETALLE_CENTRO_COSTO;
IF count_row >0 and count_row<999999 THEN
         SELECT 'DTC-'||lpad(to_char(MAX(TO_NUMBER(SUBSTR(id_detalle_centro_costo,5,10))+1)),6,'0') into :new.id_detalle_centro_costo FROM RHTD_DETALLE_CENTRO_COSTO ;
ELSIF count_row=0 THEN
    id_new:='DTC-000001';
    SELECT id_new INTO :new.id_detalle_centro_costo FROM DUAL;
END IF;
END;
/


UPDATE RHTD_DETALLE_CENTRO_COSTO  SET  ID_DETALLE_CENTRO_COSTO='DTC-'||SUBSTR(ID_DETALLE_CENTRO_COSTO,5,10);


declare 
cursor t is 
select  e.S,e.ID_DETALLE_CENTRO_COSTO,e.ID_DEPART_CENTRO_COSTO  from (
select dep.ID_DEPART_CENTRO_COSTO as s, dcc.ID_DETALLE_CENTRO_COSTO,dcc.ID_DEPART_CENTRO_COSTO, dcc.ID_DGP ,dcc.ID_CENTRO_COSTO ,dep.ID_DEPARTAMENTO,dep.ID_AREA ,dep.ID_SECCION ,dep.NO_AREA,dep.NO_SECCION from RHTD_DETALLE_CENTRO_COSTO dcc , RHVD_CENTRO_COSTO dep
where dep.ID_CENTRO_COSTO = dcc.ID_CENTRO_COSTO and dcc.ID_DGP is not null  and dcc.ID_DEPART_CENTRO_COSTO is null) e,  rhvd_det_dgp  d where e.ID_DGP=d.ID_DGP and  e.ID_SECCION = d.ID_SECCION;
begin 
for f in t loop
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO =f.s  where ID_DETALLE_CENTRO_COSTO=f.ID_DETALLE_CENTRO_COSTO;
commit;
end loop;
end;
/



update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000465';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000652';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000607';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000602';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000585';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000580';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000572';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000572';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000371' where ID_DETALLE_CENTRO_COSTO='DTC-000571';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000567';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000566';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000371' where ID_DETALLE_CENTRO_COSTO='DTC-000565';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000371' where ID_DETALLE_CENTRO_COSTO='DTC-000563';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000562';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000561';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000560';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000558';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000371' where ID_DETALLE_CENTRO_COSTO='DTC-000553';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000549';



update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000371' where ID_DETALLE_CENTRO_COSTO='DTC-000548';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000547';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000545';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000544';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000543';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000540';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000530';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000528';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000527';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000526';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000525';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000524';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000521';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000520';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000515';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000512';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000510';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000509';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000505';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000501';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000500';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000491';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000489';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000488';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000486';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000481';

update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000480';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000479';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000478';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000373' where ID_DETALLE_CENTRO_COSTO='DTC-000474';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000472';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000471';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000470';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000469';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000467';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000462';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000461';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000460';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000459';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000458';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000457';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000455';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000453';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000452';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000451';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000450';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000449';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000447';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000445';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000444';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000440';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000439';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000438';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000435';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000433';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000426';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000424';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000282' where ID_DETALLE_CENTRO_COSTO='DTC-000423';
update RHTD_DETALLE_CENTRO_COSTO set ID_DEPART_CENTRO_COSTO='DCC-000263' where ID_DETALLE_CENTRO_COSTO='DTC-000321';



DELETE RHTV_AUTORIZACION WHERE ID_DGP='DGP-000235';
DELETE RHTR_CUMPLIMIENTO_PLAZO WHERE ID_DGP='DGP-000235';
DELETE RHTD_DETALLE_HORARIO WHERE ID_DGP='DGP-000235';
DELETE RHTV_DGP_DOC_ADJ WHERE ID_DGP='DGP-000235';
DELETE RHTD_DETALLE_CENTRO_COSTO WHERE ID_DGP='DGP-000235';
DELETE RHTD_SOLICITUD_DGP WHERE ID_DGP='DGP-000235';
DELETE rhtm_dgp WHERE ID_DGP='DGP-000235';


ALTER TABLE PROCESOSRH.RHTM_DGP ADD CONSTRAINT RHTM_DGP_RHTM_TRABAJADOR_FK
FOREIGN KEY ( ID_TRABAJADOR ) REFERENCES PROCESOSRH.RHTM_TRABAJADOR (
ID_TRABAJADOR ) NOT DEFERRABLE ;

ALTER TABLE PROCESOSRH.RHTM_TRABAJADOR ADD ( CA_TIEMPO_EXPERIENCIA NUMBER (4,2)
DEFAULT 0 ) ;


CREATE
  TABLE PROCESOSRH.RHTD_TI_HORA_PAGO
  (
    ID_TI_HORA_PAGO CHAR (12 BYTE) NOT NULL ,
    ID_TRABAJADOR   CHAR (10 BYTE) NOT NULL ,
    ID_CURSO        CHAR (8 BYTE) ,
    CA_TI_HORA_PAGO NUMBER (4,2) DEFAULT 0 NOT NULL
    , 
  "US_CREACION" CHAR(10 BYTE) NOT NULL ENABLE, 
  "FE_CREACION" DATE NOT NULL ENABLE, 
  "US_MODIF" CHAR(10 BYTE), 
  "FE_MODIF" DATE, 
  "IP_USUARIO" VARCHAR2(500 BYTE)
  )
  LOGGING ;
ALTER TABLE PROCESOSRH.RHTD_TI_HORA_PAGO ADD CONSTRAINT RHTD_TI_HORA_PAGO_PK
PRIMARY KEY ( ID_TI_HORA_PAGO ) ;

ALTER TABLE PROCESOSRH.RHTD_TI_HORA_PAGO ADD CONSTRAINT
RHTD_TI_HORA_PAGO_FK FOREIGN KEY ( ID_TRABAJADOR ) REFERENCES
PROCESOSRH.RHTM_TRABAJADOR ( ID_TRABAJADOR ) NOT DEFERRABLE ;

CREATE
  TABLE RHTX_CURSO
  (
    ID_CURSO   CHAR (8 BYTE) NOT NULL ,
    CO_CURSO   VARCHAR2 (10 BYTE) NOT NULL ,
    NO_CURSO   VARCHAR2 (200 BYTE) NOT NULL ,
    ID_SECCION CHAR (8 BYTE) NOT NULL ,
    ES_CURSO   CHAR (1) NOT NULL
  )
  LOGGING ;
ALTER TABLE RHTX_CURSO ADD CONSTRAINT RHTX_CURSO_PK PRIMARY KEY ( ID_CURSO ) ;

ALTER TABLE RHTX_CURSO ADD CONSTRAINT RHTX_CURSO_RHTR_SECCION_FK FOREIGN KEY (
ID_SECCION ) REFERENCES PROCESOSRH.RHTR_SECCION ( ID_SECCION ) NOT DEFERRABLE ;

ALTER TABLE PROCESOSRH.RHTD_TI_HORA_PAGO ADD CONSTRAINT
RHTD_TI_HORA_PAGO_RHTX_CURSO_FK FOREIGN KEY ( ID_CURSO ) REFERENCES RHTX_CURSO
( ID_CURSO ) NOT DEFERRABLE ;


CREATE
  TABLE rhtr_centro_costo_curso
  (
    id_centro_costo_curso CHAR (8 BYTE) ,
    CO_CENTRO_COSTO       CHAR (8 BYTE) ,
    DE_CENTRO_COSTO       VARCHAR2 (200 BYTE) ,
    ES_CENTRO_COSTO       CHAR (1 BYTE) ,
    ID_CURSO              CHAR (8 BYTE) NOT NULL
  )
  LOGGING ;

ALTER TABLE rhtr_centro_costo_curso ADD CONSTRAINT
rhtr_centro_costo_curso_RHTX_CURSO_FK FOREIGN KEY ( ID_CURSO ) REFERENCES
RHTX_CURSO ( ID_CURSO ) NOT DEFERRABLE ;

ALTER TABLE RHTV_DGP_DOC_ADJ 
ADD (ES_DOC_VERIFICADO CHAR(1) DEFAULT 1 NOT NULL);


ALTER TABLE PROCESOSRH.RHTH_MODIF_TRABAJADOR ADD ( CA_TIEMPO_EXPERIENCIA NUMBER (4,2)
DEFAULT 0 ) ;


ALTER TABLE RHTM_TRABAJADOR 
ADD (ID_FACULTAD_ADSCRITA CHAR(8) );

ALTER TABLE RHTM_TRABAJADOR  
MODIFY (CA_TIEMPO_EXPERIENCIA NOT NULL);


ALTER TABLE RHTH_MODIF_TRABAJADOR 
ADD (ID_FACULTAD_ADSCRITA CHAR(8) );

ALTER TABLE RHTH_MODIF_TRABAJADOR  
MODIFY (CA_TIEMPO_EXPERIENCIA NOT NULL);

ALTER TABLE RHTM_CARGA_ACADEMICA 
ADD (ID_FACULTAD_ADSCRITA CHAR(8) );

ALTER TABLE RHTM_TRABAJADOR
ADD CONSTRAINT RHTM_TRABAJADOR_FK3 FOREIGN KEY
(
  ID_FACULTAD_ADSCRITA 
)
REFERENCES RHTX_DEPARTAMENTO
(
  ID_DEPARTAMENTO 
)
ENABLE;




/*Regimen laboral Mintra--
Modalidad--
Submodalidad--
Tipo de Contratacion--
Cod. Grupo de Ocupaciones--
Fecha de Suscripcion--
Tip de Moneda--
Tipo Rem. Variable
Remuneración en especie
Condicion Laboral--*/

ALTER TABLE RHTM_CONTRATO  
MODIFY (ID_SUB_MODALIDAD NULL);
CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_CONTRATOS_HISTORIAL" ("ID_CONTRATO", "ID_DGP", "FE_DESDE", "FE_HASTA", "FE_CESE", "ID_FUNC", "LI_CONDICION", "CA_SUELDO", "CA_REINTEGRO", "CA_ASIG_FAMILIAR", "HO_SEMANA", "NU_HORAS_LAB", "DIA_CONTRATO", "TI_TRABAJADOR", "LI_REGIMEN_LABORAL", "ES_DISCAPACIDAD", "TI_CONTRATO", "LI_REGIMEN_PENSIONARIO", "ES_CONTRATO_TRABAJADOR", "US_CREACION", "FE_CREACION", "US_MODIF", "FE_MODIF", "US_IP", "FE_VACACIO_INI", "FE_VACACIO_FIN", "ES_CONTRATO", "ID_FILIAL", "ID_PUESTO", "CA_BONO_ALIMENTO", "LI_TIPO_CONVENIO", "ES_FIRMO_CONTRATO", "NU_CONTRATO", "DE_OBSERVACION", "ES_APOYO", "TI_HORA_PAGO", "NU_DOCUMENTO", "ES_ENTREGAR_DOC_REGLAMENTOS", "ES_REGISTRO_HUELLA", "DE_REGISTRO_SISTEM_REMU", "ID_TRABAJADOR", "CA_SUELDO_TOTAL", "ID_REGIMEN_LABORAL", "ID_SUB_MODALIDAD", "ID_GRUPO_OCUPACION", "FE_SUSCRIPCION", "CO_TI_MONEDA", "CO_TI_REM_VARIAB", "DE_REMU_ESPECIE", "DE_RUC_EMP_TRAB", "CO_SUCURSAL", "DE_MYPE", "ES_TI_CONTRATACION", "CA_BEV", "NO_PUESTO",
  "NO_SECCION", "NO_AREA", "NO_DEP", "NO_DIRECCION", "DE_GRUPO_OCUPACION", "DE_SUB_MODALIDAD", "DE_MODALIDAD", "NO_TRABAJADOR", "AP_PATERNO", "AP_MATERNO", "LI_DI_DOM_A_D1", "DI_DOM_A_D2", "LI_DI_DOM_A_D3", "DI_DOM_A_D4", "LI_DI_DOM_A_D5", "DI_DOM_A_D6", "NO_PROVINCIA", "NO_DISTRITO", "FE_SUS", "FE_DES", "FE_HAS", "ID_PLANTILLA_CONTRACTUAL", "CA_BONIFICACION_P", "ES_SECRE_IS", "LI_RELIGION", "PRACTICANTE")
AS

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
    a.LI_RELIGION,
    a.PRACTICANTE
    FROM
    (select  cc.*,ss.DE_MODALIDAD,   ss.DE_SUB_MODALIDAD  from 
(SELECT c."ID_CONTRATO",
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
      c.CA_BONIFICACION_P,
      c.ES_SECRE_IS,
      c.PRACTICANTE
      
     -- , m.DE_MODALIDAD,   s.DE_SUB_MODALIDAD
    FROM rhtm_contrato c,
      RHVD_puesto_direccion pd,
     -- RHTX_MODALIDAD m ,
    --  RHTX_SUB_MODALIDAD s,
      RHTM_TRABAJADOR tr,
      RHTX_UB_PROVINCIA pr,
      RHTX_UB_DISTRITO ds
    WHERE c.id_puesto           = pd.id_puesto
    AND c.ID_TRABAJADOR         =tr.ID_TRABAJADOR
    AND tr.ID_DISTRITO          =ds.ID_DISTRITO
    AND ds.ID_PROVINCIA         =pr.ID_PROVINCIA
  --  AND s.ID_SUB_MODALIDAD      = c.ID_SUB_MODALIDAD
  --  AND s.ID_MODALIDAD          = m.ID_MODALIDAD
    AND c.ES_CONTRATO_TRABAJADOR='1') cc left outer join  (select mo.DE_MODALIDAD,sm.DE_SUB_MODALIDAD,sm.ID_SUB_MODALIDAD from RHTX_MODALIDAD mo ,RHTX_SUB_MODALIDAD sm where sm.ID_MODALIDAD = mo.ID_MODALIDAD) ss
    on  ( ss.ID_SUB_MODALIDAD      = cc.ID_SUB_MODALIDAD)
    ) a
  LEFT OUTER JOIN RHTX_GRUPO_OCUPACION g
  ON(g.ID_GRUPO_OCUPACION = a.ID_GRUPO_OCUPACION);

ALTER TABLE RHTV_AUTORIZACION  
MODIFY (IP_USUARIO VARCHAR2(200 BYTE) );

ALTER TABLE RHTR_FOTOS_TRABAJADOR  
MODIFY (EFOTO DEFAULT 0 );
ALTER TABLE RHTR_FOTOS_TRABAJADOR  
MODIFY (EFOTO NOT NULL);



  CREATE OR REPLACE TYPE "PROCESOSRH"."ARRAY_ID_DGP" AS TABLE OF char(10 byte);

/


--------------------------------------------------------
--  DDL for Type TABLA_PAGO_DOCENTE
--------------------------------------------------------

  CREATE OR REPLACE TYPE "PROCESOSRH"."TABLA_PAGO_DOCENTE" as object
    (
   html VARCHAR2(8000)  
   );
  

/
--------------------------------------------------------
--  DDL for Type TABLA_PD
--------------------------------------------------------

  CREATE OR REPLACE TYPE "PROCESOSRH"."TABLA_PD" IS TABLE OF tabla_pago_docente;
    

/
--------------------------------------------------------
--  DDL for Table RHTC_HORARIO
--------------------------------------------------------
--  DDL for Type TABLA_PD

  CREATE OR REPLACE FUNCTION "PROCESOSRH"."RHFU_CUOTAS_DOCENTE" (fe_desde date, fe_hasta date, ca_pago_mensual number)
RETURN tabla_pd
IS
  tabla_retorno tabla_pd;
  indice number;
  
  i number;
j int;
num_mes int;
anno char(4);
mes char(2);

  
  
BEGIN
  tabla_retorno := tabla_pd();
  
  
select  to_number(to_char(fe_hasta,'mm'))-  to_number(to_char(fe_desde,'mm'))+1  into num_mes from dual;
anno:=to_char(fe_desde,'yyyy');
mes:=to_char(fe_desde,'mm');
j:=1;
while j<=num_mes loop
i:=0.0;
if j=1 then 
i:=( LAST_DAY(fe_desde)-fe_desde+1)/7;
elsif j=num_mes then
i:=( (fe_hasta-add_months(to_date('01/'||mes||'/'||anno,'dd/mm/yyyy'),(j-1)))+1)/7;
else 
i:=( LAST_DAY(add_months(to_date('01/'||mes||'/'||anno,'dd/mm/yyyy'),j))-add_months(to_date('01/'||mes||'/'||anno,'dd/mm/yyyy'),j)+1)/7;
end if;


if i >=3.14 then 
if i<=4 or  i< 5then
     tabla_retorno.extend;
      indice:= tabla_retorno.count;  
        tabla_retorno(indice):= tabla_pago_docente('<label for="category"> MES '||j||' - 4 Semanas </label>'||'<input type="text" class="form-control" name="MES'||j||'" readonly="" value="'||(4*ca_pago_mensual)||'"  required />');

end if;
elsif i<3.14 then
if i>1 then 
 tabla_retorno.extend;
      indice:= tabla_retorno.count;  
      tabla_retorno(indice):= tabla_pago_docente('<label for="category"> MES '||j||' - '||TO_CHAR(round(i,2),'99999999D99','NLS_NUMERIC_CHARACTERS = ''.,''') ||' Semanas </label>'||'<input type="text" class="form-control" name="MES'||j||'" readonly="" value="'||TO_CHAR(round(i,2)*ca_pago_mensual,'99999999D99','NLS_NUMERIC_CHARACTERS = ''.,''')||'"  required />');

elsif i>0.14 then
 tabla_retorno.extend;
      indice:= tabla_retorno.count;  
      tabla_retorno(indice):= tabla_pago_docente('<label for="category"> MES '||j||' - 1 Semana </label>'||'<input type="text" class="form-control" name="MES'||j||'" readonly="" value="'||(1*ca_pago_mensual)||'"  required />');
end if;
end if;
j:=j+1;
end loop;
  return tabla_retorno;
END;

/
--------------------------------------------------------

ALTER TABLE RHTM_PROCESO_CARGA_ACADEMICA  
MODIFY (IP_USUARIO VARCHAR2(200 BYTE) );

create or replace function rhfu_conutProcCAcademica (dgp char) return integer
as
i int;
begin
select count(*)  into i from RHTM_PROCESO_CARGA_ACADEMICA where ID_DGP=dgp;
return i;
end;
/

CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_ES_REQUERIMIENTO" as
  SELECT f."ID_TRABAJADOR",
    f."AP_PATERNO",
    f."AP_MATERNO",
    f."NO_TRABAJADOR",
    f.ti_doc,
    f.nu_doc,
    f."ID_DGP",
    f."ID_DEPARTAMENTO",
    f."ES_DGP",
    f."AR_FOTO",
    f."DE_FOTO",
    f."ID_FOTO",
    f."NO_AR_FOTO",
    f."TA_AR_FOTO",
    k.ES_PORCENT ,
    NULL                          AS es_proceso_aut,
    RHFU_REQ_PRO_ID_DGP(f.id_dgp) AS id_detalle_req_proceso,
    RHFU_AUT_ACTUAL( f.id_dgp)    AS aut_actual,
    TO_CHAR( f.fe_creacion,'MONTH','nls_date_language=spanish') mes_creacion,
    f.fe_creacion,
    f.LI_MOTIVO,
    f.NO_DEP,
    trim(rhfu_anno_procesamiento_dgp(f.ID_DGP)) AS anno_procesamiento,
    trim(rhfu_mes_procesamiento_dgp(f.ID_DGP))  AS mes_procesamiento,
    f.ID_DIRECCION,
    f.ID_AREA,
    f.NO_AREA,
    f.NO_DIRECCION,rhfu_conutProcCAcademica( f."ID_DGP") as es_proc_acad
  FROM
    (SELECT e.*,
      f.AR_FOTO,
      f.DE_FOTO,
      f.ID_FOTO,
      f.NO_AR_FOTO,
      f.TA_AR_FOTO
    FROM
      (SELECT dgp.id_trabajador,
        dt.ap_paterno,
        dt.ap_materno,
        dt.no_trabajador,
        dt.ti_doc,
        dt.nu_doc,
        dgp.id_dgp ,
        dgp.FE_CREACION,
        pd.id_departamento,
        pd.NO_DEP,
        dgp.ES_DGP,
        dgp.LI_MOTIVO,
        pd.ID_DIRECCION,
        pd.ID_AREA,
        pd.NO_AREA,
        pd.NO_DIRECCION
      FROM rhtm_dgp dgp,
        rhtm_trabajador dt ,
        rhvd_puesto_direccion pd
      WHERE dt.id_trabajador = dgp.id_trabajador
      AND dgp.es_dgp        IN('0','2')
      AND dgp.id_dgp        IN
        (SELECT id_dgp FROM rhtv_autorizacion GROUP BY id_dgp)
      AND pd.id_puesto= dgp.id_puesto
      )e
    LEFT OUTER JOIN RHTR_FOTOS_TRABAJADOR f
    ON (f.ID_TRABAJADOR = e.ID_TRABAJADOR)
    ) f
  LEFT OUTER JOIN
    (SELECT DISTINCT( ID_DGP)                                                                                                            AS id_dgp,
      ROUND((to_number(SUBSTR(nu_pasos_actual,2,LENGTH(nu_pasos_actual)))/to_number(SUBSTR(NU_PASOS_ULTI,2,LENGTH(NU_PASOS_ULTI))))*100) AS ES_PORCENT
    FROM
      (SELECT m.NU_PASOS AS nu_pasos_actual ,
        z.nu_pasos       AS NU_PASOS_ULTI,
        M.ID_DGP
      FROM
        (SELECT a.nu_pasos,
          a.id_detalle_req_proceso,
          a.id_dgp,
          a.id_pasos
        FROM rhtv_autorizacion a,
          (SELECT 'P'
            ||TO_CHAR(MAX(to_number(SUBSTR(a.nu_pasos,2,LENGTH(a.nu_pasos))))) AS NU_PASOs ,
            a.id_detalle_req_proceso,
            a.id_dgp
          FROM rhvd_req_paso_pu p ,
            rhtv_autorizacion a
          WHERE p.id_detalle_req_proceso=a.id_detalle_req_proceso
          GROUP BY a.id_detalle_req_proceso,
            a.id_dgp
          ) d
        WHERE trim(d.nu_pasos)       = trim(a.nu_pasos)
        AND d.id_detalle_req_proceso = a.id_detalle_req_proceso
        AND a.id_dgp                 =d.id_dgp
        ) m,
        (SELECT a.nu_pasos,
          a.id_detalle_req_proceso,
          a.id_dgp ,
          po.id_pasos
        FROM RHVD_REQ_PASO_PU po,
          (SELECT 'P'
            ||TO_CHAR(MAX(to_number(SUBSTR(p.nu_pasos,2,LENGTH(p.nu_pasos))))) AS NU_PASOS ,
            a.id_detalle_req_proceso,
            a.id_dgp
          FROM rhvd_req_paso_pu p ,
            rhtv_autorizacion a
          WHERE p.id_detalle_req_proceso=a.id_detalle_req_proceso
          GROUP BY a.id_detalle_req_proceso,
            a.id_dgp
          )a
        WHERE po.id_detalle_req_proceso = a.id_detalle_req_proceso
        AND trim(po.nu_pasos)           = trim(a.nu_pasos)
        ) z
      WHERE m.id_dgp=z.id_dgp
      )
    ) k
  ON (f.id_dgp                     =k.ID_DGP)
  /*WHERE VAL_DGP_PROCESO(f.id_dgp )=0;*/



create or replace function  rhfu_countCursosEAPCiclo (eap varchar, dni varchar, ciclo varchar ) return int
as 
i int;
begin
select count(*) into i from RHTM_CARGA_ACADEMICA where trim(NU_DOC)=trim(dni) and trim(NO_EAP)=trim(eap) and trim(DE_CARGA)=trim(ciclo);
return i;
end;
/



CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_CARGA_ACADEMICA" as

  SELECT ca."NU_DOC",
    ca."ES_TIPO_DOC",
    ca."NO_TRABAJADOR",
    ca."AP_PATERNO",
    ca."AP_MATERNO",
    ca."NO_EAP",
    ca."NO_FACULTAD",
    ca."DE_CONDICION",
    ca."DE_CARGA",
    ca."FE_DESDE",
    ca."FE_HASTA",
    ca."ID_PROCESO_CARGA_AC",
    ca."ES_PROCESADO",
    ca."FE_CREACION",
    t.ID_TRABAJADOR ,
    t.ID_SITUACION_EDUCATIVA,
    t.NO_S_EDUCATIVA ,
    t.profesion_docente,rhfu_countCursosEAPCiclo(ca.NO_EAP,t.NU_DOC,ca.DE_CARGA) as countCursos
  FROM
    (SELECT c.NU_DOC,
      c.ES_TIPO_DOC,
      c.NO_TRABAJADOR,
      c.AP_PATERNO,
      c.AP_MATERNO,
      c.NO_EAP,
      c.NO_FACULTAD ,
      c.DE_CONDICION,
      c. DE_CARGA,
      d.fe_desde,
      d.fe_hasta,
      d.ID_PROCESO_CARGA_AC,
      d.es_procesado,
      d.fe_creacion
    FROM RHTM_CARGA_ACADEMICA c
    LEFT OUTER JOIN
      (SELECT ca.*,
        dc.ID_DETALLE_CARGA_ACADEMICA,
        dc.ID_CARGA_ACADEMICA
      FROM RHTM_PROCESO_CARGA_ACADEMICA ca,
        RHTD_DETALLE_CARGA_ACADEMICA dc
      WHERE dc.ID_PROCESO_CARGA_AC = ca.ID_PROCESO_CARGA_AC
      AND dc.ES_DETALLE_CARGA      ='1'
      AND dc.ES_DETALLE_CARGA      ='1'
      ) d
    ON (c.id_carga_academica=d.id_carga_academica)
    GROUP BY nu_doc,
      c.ES_TIPO_DOC,
      c.NO_TRABAJADOR ,
      c.AP_PATERNO,
      c.AP_MATERNO,
      c.NO_EAP,
      c.NO_FACULTAD,
      c.DE_CONDICION,
      c.DE_CARGA,
      d.fe_desde,
      d.fe_hasta,
      d.ID_PROCESO_CARGA_AC,
      d.es_procesado,
      d.fe_creacion
    )ca
  LEFT OUTER JOIN
    (SELECT tr.ID_TRABAJADOR,
      tr.NU_DOC,
      s.ID_SITUACION_EDUCATIVA,
      s.NO_S_EDUCATIVA ,
      tr.NO_CARRERA AS profesion_docente
    FROM rhvd_trabajador tr
    LEFT OUTER JOIN RHTX_SITUACION_EDUCATIVA s
    ON (tr.ID_SITUACION_EDUCATIVA = s.ID_SITUACION_EDUCATIVA)
    ) t ON(trim( t.NU_DOC)        = trim(ca.NU_DOC));

create or replace function rhfu_validateExistTrabajador(dni char) return char
as
idtr char(10);
begin
select id_trabajador into idtr  from rhtm_trabajador  where  trim(NU_DOC)=trim(dni);
return idtr;
end;
/

 CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_CARGA_ACADEMICA" AS  
  SELECT ca."NU_DOC",
    ca."ES_TIPO_DOC",
    ca."NO_TRABAJADOR",
    ca."AP_PATERNO",
    ca."AP_MATERNO",
    ca."NO_EAP",
    ca."NO_FACULTAD",
    ca."DE_CONDICION",
    ca."DE_CARGA",
    ca."FE_DESDE",
    ca."FE_HASTA",
    ca."ID_PROCESO_CARGA_AC",
    ca."ES_PROCESADO",
    ca."FE_CREACION",
    t.ID_TRABAJADOR ,
    t.ID_SITUACION_EDUCATIVA,
    t.NO_S_EDUCATIVA ,
    t.profesion_docente,rhfu_countCursosEAPCiclo(ca.NO_EAP,t.NU_DOC,ca.DE_CARGA) as countCursos, rhfu_validateExistTrabajador(ca.NU_DOC) as  validateExistTrabajador
  FROM
    (SELECT c.NU_DOC,
      c.ES_TIPO_DOC,
      c.NO_TRABAJADOR,
      c.AP_PATERNO,
      c.AP_MATERNO,
      c.NO_EAP,
      c.NO_FACULTAD ,
      c.DE_CONDICION,
      c. DE_CARGA,
      d.fe_desde,
      d.fe_hasta,
      d.ID_PROCESO_CARGA_AC,
      d.es_procesado,
      d.fe_creacion
    FROM RHTM_CARGA_ACADEMICA c
    LEFT OUTER JOIN
      (SELECT ca.*,
        dc.ID_DETALLE_CARGA_ACADEMICA,
        dc.ID_CARGA_ACADEMICA
      FROM RHTM_PROCESO_CARGA_ACADEMICA ca,
        RHTD_DETALLE_CARGA_ACADEMICA dc
      WHERE dc.ID_PROCESO_CARGA_AC = ca.ID_PROCESO_CARGA_AC
      AND dc.ES_DETALLE_CARGA      ='1'
      AND dc.ES_DETALLE_CARGA      ='1'
      ) d
    ON (c.id_carga_academica=d.id_carga_academica)
    GROUP BY nu_doc,
      c.ES_TIPO_DOC,
      c.NO_TRABAJADOR ,
      c.AP_PATERNO,
      c.AP_MATERNO,
      c.NO_EAP,
      c.NO_FACULTAD,
      c.DE_CONDICION,
      c.DE_CARGA,
      d.fe_desde,
      d.fe_hasta,
      d.ID_PROCESO_CARGA_AC,
      d.es_procesado,
      d.fe_creacion
    )ca
  LEFT OUTER JOIN
    (SELECT tr.ID_TRABAJADOR,
      tr.NU_DOC,
      s.ID_SITUACION_EDUCATIVA,
      s.NO_S_EDUCATIVA ,
      tr.NO_CARRERA AS profesion_docente
    FROM rhvd_trabajador tr
    LEFT OUTER JOIN RHTX_SITUACION_EDUCATIVA s
    ON (tr.ID_SITUACION_EDUCATIVA = s.ID_SITUACION_EDUCATIVA)
    ) t ON(trim( t.NU_DOC)        = trim(ca.NU_DOC));


ALTER TABLE RHTD_PAGO_DOCENTE  
MODIFY (IP_USUARIO CHAR(200 BYTE) );


select count(*) from rhtm_contrato where ES_CONTRATO='1' and id_trabajador='TRB-003059';
create or replace PROCEDURE  "RHSP_VAL_EMP" ( idtr CHAR) as
i int;
f int; 
dni RHTM_TRABAJADOR.nu_doc%type;
id_emp rhtd_empleado.id_empleado%type ;
begin 
select count(*) into i from rhtm_contrato c, rhtd_empleado e  where e.id_trabajador = c.id_trabajador and 
c.ES_CONTRATO='1' and c.id_trabajador=idtr;
--Validar si el trabajador tiene contrato , para usuarios que no tienen contrato
select count(*) into f from rhtm_contrato where ES_CONTRATO='1' and id_trabajador=idtr;
select nu_doc into dni from RHTM_TRABAJADOR where id_trabajador = idtr;

DBMS_OUTPUT.PUT_LINE('i:'||i);
DBMS_OUTPUT.PUT_LINE('f:'||f);
DBMS_OUTPUT.PUT_LINE('dni:'||dni);
if f >0 then
--desabilitamos empleados 
UPDATE RHTD_EMPLEADO set ES_EMPLEADO='0' where id_trabajador=idtr; 
else 
if i =0 then
DBMS_OUTPUT.PUT_LINE('idtr:'||idtr);
insert into rhtd_empleado (ES_EMPLEADO,id_trabajador) values ('1',idtr) RETURNING ID_EMPLEADO INTO id_emp ;
COMMIT;

insert into RHTC_USUARIO (
NO_USUARIO,
PW_USUARIO,
ID_EMPLEADO,
ID_ROL,
ES_USUARIO) values (dni,dni,id_emp,'ROL-0013','1');
COMMIT;
else 
UPDATE RHTD_EMPLEADO set ES_EMPLEADO='1' where id_trabajador=idtr; 
COMMIT;
end if;
COMMIT;
end if;
end;
/

create or replace PROCEDURE              "RHSP_INSERT_TRABAJADOR" (
    ID_TRABAJADOR_SP                  CHAR,
    AP_PATERNO_SP                     VARCHAR2,
    AP_MATERNO_SP                     VARCHAR2,
    NO_TRABAJADOR_SP                  VARCHAR2,
    TI_DOC_SP                         VARCHAR2,
    NU_DOC_SP                         CHAR,
    ES_CIVIL_SP                       CHAR,
    FE_NAC_SP                         DATE,
    ID_NACIONALIDAD_SP                CHAR,
    ID_DEPARTAMENTO_SP                VARCHAR2,
    ID_PROVINCIA_SP                   CHAR,
    ID_DISTRITO_SP                    CHAR,
    TE_TRABAJADOR_SP                  VARCHAR2,
    CL_TRA_SP                         CHAR,
    DI_CORREO_PERSONAL_SP             VARCHAR2,
    DI_CORREO_INST_SP                 VARCHAR2,
    CO_SISTEMA_PENSIONARIO_SP         CHAR,
    ID_SITUACION_EDUCATIVA_SP         CHAR,
    LI_REG_INST_EDUCATIVA_SP          CHAR,
    ES_INST_EDUC_PERU_SP              CHAR,
    ID_UNIVERSIDAD_CARRERA_SP         CHAR,
    DE_ANNO_EGRESO_SP                 CHAR,
    CM_OTROS_ESTUDIOS_SP              VARCHAR2,
    ES_SEXO_SP                        CHAR,
    LI_GRUPO_SANGUINEO_SP             CHAR,
    DE_REFERENCIA_SP                  VARCHAR2,
    LI_RELIGION_SP                    VARCHAR2,
    NO_IGLESIA_SP                     VARCHAR2,
    DE_CARGO_SP                       VARCHAR2,
    LI_AUTORIDAD_SP                   CHAR,
    NO_AP_AUTORIDAD_SP                VARCHAR2,
    CL_AUTORIDAD_SP                   VARCHAR2,
    ID_NO_AFP_SP                      CHAR,
    ES_AFILIADO_ESSALUD_SP            CHAR,
    LI_TIPO_TRABAJADOR_SP             CHAR,
    CA_TIPO_HPR_SP CHAR,
    ES_FACTOR_RH_SP                   CHAR,
    LI_DI_DOM_A_D1_SP                 CHAR,
    DI_DOM_A_D2_SP                    CHAR,
    LI_DI_DOM_A_D3_SP                 CHAR,
    DI_DOM_A_D4_SP                    CHAR,
    LI_DI_DOM_A_D5_SP                 CHAR,
    DI_DOM_A_D6_SP                    CHAR,
    DI_DOM_A_REF_SP                   CHAR,
    ID_DI_DOM_A_DISTRITO_SP           CHAR,
    LI_DI_DOM_LEG_D1_SP               CHAR,
    DI_DOM_LEG_D2_SP                  CHAR,
    LI_DI_DOM_LEG_D3_SP               CHAR,
    DI_DOM_LEG_D4_SP                  CHAR,
    LI_DI_DOM_LEG_D5_SP               CHAR,
    DI_DOM_LEG_D6_SP                  CHAR,
    ID_DI_DOM_LEG_DISTRITO_SP         CHAR,
    CA_ING_QTA_CAT_EMPRESA_SP         VARCHAR2,
    CA_ING_QTA_CAT_RUC_SP             VARCHAR2,
    CA_ING_QTA_OE_SP  CHAR,
    CM_OBSERVACIONES_SP               CHAR,
    US_CREACION_SP                    CHAR,
    FE_CREACION_SP                    DATE,
    US_MODIF_SP                       CHAR,
    FE_MODIF_SP                       DATE,
    IP_USUARIO_SP                     VARCHAR2,
    AP_NOMBRES_PADRE_SP               VARCHAR2,
    AP_NOMBRES_MADRE_SP               VARCHAR2,
    ES_TRABAJA_UPEU_C_SP              CHAR,
    AP_NOMBRES_C_SP                   VARCHAR2,
    FE_NAC_C_SP                       DATE,
    ID_TIPO_DOC_C_SP                  CHAR,
    NU_DOC_C_SP                       CHAR,
    LI_INSC_VIG_ESSALUD_C_SP   CHAR,
    ID_CONYUGUE_SP                    CHAR,
    CO_UNIVERSITARIO_SP              CHAR,ES_DIEZMO_SP CHAR)
IS
IDTR RHTM_TRABAJADOR.ID_TRABAJADOR%TYPE ;
BEGIN
  INSERT
  INTO RHTM_TRABAJADOR
    (
      ID_TRABAJADOR,
      AP_PATERNO,
      AP_MATERNO,
      NO_TRABAJADOR,
      TI_DOC,
      NU_DOC,
      ES_CIVIL,
      FE_NAC,
      ID_NACIONALIDAD,
      ID_DEPARTAMENTO,
      ID_PROVINCIA,
      ID_DISTRITO,
      TE_TRABAJADOR,
      CL_TRA,
      DI_CORREO_PERSONAL,
      DI_CORREO_INST,
      CO_SISTEMA_PENSIONARIO,
      ID_SITUACION_EDUCATIVA,
      LI_REG_INST_EDUCATIVA,
      ES_INST_EDUC_PERU,
      ID_UNIVERSIDAD_CARRERA,
      DE_ANNO_EGRESO,
      CM_OTROS_ESTUDIOS,
      ES_SEXO,
      LI_GRUPO_SANGUINEO,
      DE_REFERENCIA,
      LI_RELIGION,
      NO_IGLESIA,
      DE_CARGO,
      LI_AUTORIDAD,
      NO_AP_AUTORIDAD,
      CL_AUTORIDAD,
      ID_NO_AFP,
      ES_AFILIADO_ESSALUD,
      LI_TIPO_TRABAJADOR,
      CA_TIPO_HORA_PAGO_REFEERENCIAL,
      ES_FACTOR_RH,
      LI_DI_DOM_A_D1,
      DI_DOM_A_D2,
      LI_DI_DOM_A_D3,
      DI_DOM_A_D4,
      LI_DI_DOM_A_D5,
      DI_DOM_A_D6,
      DI_DOM_A_REF,
      ID_DI_DOM_A_DISTRITO,
      LI_DI_DOM_LEG_D1,
      DI_DOM_LEG_D2,
      LI_DI_DOM_LEG_D3,
      DI_DOM_LEG_D4,
      LI_DI_DOM_LEG_D5,
      DI_DOM_LEG_D6,
      ID_DI_DOM_LEG_DISTRITO,
      CA_ING_QTA_CAT_EMPRESA,
      CA_ING_QTA_CAT_RUC,
      CA_ING_QTA_CAT_OTRAS_EMPRESAS,
      CM_OBSERVACIONES,
      US_CREACION,
      FE_CREACION,
      US_MODIF,
      FE_MODIF,
      IP_USUARIO,
      AP_NOMBRES_PADRE,
      AP_NOMBRES_MADRE,
      ES_TRABAJA_UPEU_C,
      AP_NOMBRES_C,
      FE_NAC_C,
      ID_TIPO_DOC_C,
      NU_DOC_C,
      LI_INSCRIPCION_VIG_ESSALUD_C,
      ID_CONYUGUE,
      CO_UNIVERSITARIO,ES_DIEZMO
    )
    VALUES
    (
      null ,
      AP_PATERNO_SP ,
      AP_MATERNO_SP ,
      NO_TRABAJADOR_SP ,
      TI_DOC_SP ,
      NU_DOC_SP ,
      ES_CIVIL_SP ,
      FE_NAC_SP ,
      ID_NACIONALIDAD_SP ,
      ID_DEPARTAMENTO_SP ,
      ID_PROVINCIA_SP ,
      ID_DISTRITO_SP ,
      TE_TRABAJADOR_SP ,
      CL_TRA_SP ,
      DI_CORREO_PERSONAL_SP ,
      DI_CORREO_INST_SP ,
      CO_SISTEMA_PENSIONARIO_SP ,
      ID_SITUACION_EDUCATIVA_SP ,
      LI_REG_INST_EDUCATIVA_SP ,
      ES_INST_EDUC_PERU_SP ,
      ID_UNIVERSIDAD_CARRERA_SP ,
      DE_ANNO_EGRESO_SP ,
      CM_OTROS_ESTUDIOS_SP ,
      ES_SEXO_SP ,
      LI_GRUPO_SANGUINEO_SP ,
      DE_REFERENCIA_SP ,
      LI_RELIGION_SP ,
      NO_IGLESIA_SP ,
      DE_CARGO_SP ,
      LI_AUTORIDAD_SP ,
      NO_AP_AUTORIDAD_SP ,
      CL_AUTORIDAD_SP ,
      ID_NO_AFP_SP ,
      ES_AFILIADO_ESSALUD_SP ,
      LI_TIPO_TRABAJADOR_SP ,
      CA_TIPO_HPR_SP ,
      ES_FACTOR_RH_SP ,
      LI_DI_DOM_A_D1_SP ,
      DI_DOM_A_D2_SP ,
      LI_DI_DOM_A_D3_SP ,
      DI_DOM_A_D4_SP ,
      LI_DI_DOM_A_D5_SP ,
      DI_DOM_A_D6_SP ,
      DI_DOM_A_REF_SP ,
      ID_DI_DOM_A_DISTRITO_SP ,
      LI_DI_DOM_LEG_D1_SP ,
      DI_DOM_LEG_D2_SP ,
      LI_DI_DOM_LEG_D3_SP ,
      DI_DOM_LEG_D4_SP ,
      LI_DI_DOM_LEG_D5_SP ,
      DI_DOM_LEG_D6_SP ,
      ID_DI_DOM_LEG_DISTRITO_SP ,
      CA_ING_QTA_CAT_EMPRESA_SP ,
      CA_ING_QTA_CAT_RUC_SP ,
      CA_ING_QTA_OE_SP ,
      CM_OBSERVACIONES_SP ,
      US_CREACION_SP ,
      SYSDATE ,
      US_MODIF_SP ,
      FE_MODIF_SP ,
      IP_USUARIO_SP ,
      AP_NOMBRES_PADRE_SP ,
      AP_NOMBRES_MADRE_SP ,
      ES_TRABAJA_UPEU_C_SP ,
      AP_NOMBRES_C_SP ,
      FE_NAC_C_SP ,
      ID_TIPO_DOC_C_SP ,
      NU_DOC_C_SP ,
      LI_INSC_VIG_ESSALUD_C_SP ,
      ID_CONYUGUE_SP,
      CO_UNIVERSITARIO_SP,ES_DIEZMO_SP
    ) RETURNING ID_TRABAJADOR INTO IDTR;
    /*Validaciones*/
      RHSP_VAL_EMP(IDTR);
END ;
/

create or replace TRIGGER "PROCESOSRH"."RHGT_INSERT_ID_CURSO" 
BEFORE INSERT ON RHTX_CURSO 
FOR EACH ROW
 DECLARE
 id_max number;
 id_new RHTX_CURSO.ID_CURSO%type;
 count_row number;
BEGIN
SELECT COUNT(ID_CURSO) INTO count_row  FROM RHTX_CURSO;
IF count_row >0 and count_row<9999 THEN
         SELECT 'CUR-'||lpad(to_char(MAX(TO_NUMBER(SUBSTR(ID_CURSO,5,8))+1)),4,'0') into :new.ID_CURSO FROM RHTX_CURSO ;
ELSIF count_row=0 THEN
    id_new:='CUR-0001';
    SELECT id_new INTO :new.ID_CURSO FROM DUAL;
END IF;
END;
/

create or replace TRIGGER "PROCESOSRH"."RHGT_INSERT_ID_TIHORAPAGO" 
BEFORE INSERT ON RHTD_TI_HORA_PAGO 
FOR EACH ROW
 DECLARE
 id_new RHTD_TI_HORA_PAGO.ID_TI_HORA_PAGO %type;
 count_row number;
BEGIN
SELECT COUNT(ID_TI_HORA_PAGO) INTO count_row  FROM RHTD_TI_HORA_PAGO;
IF count_row >0 and count_row<999999 THEN
         SELECT 'THP-'||lpad(to_char(MAX(TO_NUMBER(SUBSTR(ID_TI_HORA_PAGO,5,12))+1)),8,'0') into :new.ID_TI_HORA_PAGO FROM RHTD_TI_HORA_PAGO ;
ELSIF count_row=0 THEN
    id_new:='THP-00000001';
    SELECT id_new INTO :new.ID_TI_HORA_PAGO FROM DUAL;
END IF;
END;
/

create or replace TRIGGER "PROCESOSRH"."RHGT_INSERT_ID_CC_CURSO" 
BEFORE INSERT ON RHTR_CENTRO_COSTO_CURSO 
FOR EACH ROW
 DECLARE
 id_max number;
 id_new RHTR_CENTRO_COSTO_CURSO.ID_CENTRO_COSTO_CURSO%type;
 count_row number;
BEGIN
SELECT COUNT(ID_CENTRO_COSTO_CURSO) INTO count_row  FROM RHTR_CENTRO_COSTO_CURSO;
IF count_row >0 and count_row<9999 THEN
         SELECT 'CCC-'||lpad(to_char(MAX(TO_NUMBER(SUBSTR(ID_CENTRO_COSTO_CURSO,5,8))+1)),4,'0') into :new.ID_CENTRO_COSTO_CURSO FROM RHTR_CENTRO_COSTO_CURSO ;
ELSIF count_row=0 THEN
    id_new:='CCC-0001';
    SELECT id_new INTO :new.ID_CENTRO_COSTO_CURSO FROM DUAL;
END IF;
END;
/

ALTER TABLE RHTD_TI_HORA_PAGO 
ADD (CA_HL VARCHAR2(20) DEFAULT 0 NOT NULL);

COMMENT ON COLUMN RHTD_TI_HORA_PAGO.CA_TI_HORA_PAGO IS 'Tipo de hora Pago';

COMMENT ON COLUMN RHTD_TI_HORA_PAGO.CA_HL IS 'Horas Laborales';

ALTER TABLE RHTD_TI_HORA_PAGO 
DROP COLUMN ID_CURSO;
ALTER TABLE RHTD_TI_HORA_PAGO 
DROP COLUMN CA_HL;



/*cambios de local alpha
*/

  CREATE TABLE "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" 
   (  "ID_SITUACION_ESPECIAL" CHAR(6 BYTE) NOT NULL ENABLE, 
  "DE_SITUACION_ESPECIAL" VARCHAR2(100 BYTE) NOT NULL ENABLE
   );
   ALTER TABLE RHTR_SITUACION_ESPECIAL
ADD CONSTRAINT RHTR_SITUACION_ESPECIAL_PK PRIMARY KEY 
(
  ID_SITUACION_ESPECIAL 
)
ENABLE;
ALTER TABLE RHTM_CONTRATO 
ADD (ID_SITUACION_ESPECIAL CHAR(6) );

ALTER TABLE RHTM_CONTRATO
ADD CONSTRAINT RHTM_CONTRATO_FK1 FOREIGN KEY
(
  ID_SITUACION_ESPECIAL 
)
REFERENCES RHTR_SITUACION_ESPECIAL
(
  ID_SITUACION_ESPECIAL 
)
ENABLE;
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-01', 'Ninguna');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-02', 'Trabajador de dirección - Presencial');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-03', 'Trabajador de confianza - Presencial');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-04', 'Trabajador de dirección - Teletrabajo Mixto');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-05', 'Trabajador de confianza - Teletrabajo Presencial');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-06', 'Trabajador de dirección - Teletrabajo Completo');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-07', 'Trabajador de confianza - Teletrabajo Completo');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-08', 'Teletrabajo Mixto');
INSERT INTO "PROCESOSRH"."RHTR_SITUACION_ESPECIAL" (ID_SITUACION_ESPECIAL, DE_SITUACION_ESPECIAL) VALUES ('SE-09', 'Teletrabajo Completo');
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
    PRACTICANTE_SP                 CHAR,
    situacionEspecialSP CHAR)
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
      PRACTICANTE,
      ID_SITUACION_ESPECIAL
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
      '0',situacionEspecialSP
    );
END ;
/


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
    a.LI_RELIGION,
    a.PRACTICANTE,a.id_situacion_especial,se.DE_SITUACION_ESPECIAL
    FROM
    (select  cc.*,ss.DE_MODALIDAD,   ss.DE_SUB_MODALIDAD  from 
(SELECT c."ID_CONTRATO",
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
      c.CA_BONIFICACION_P,
      c.ES_SECRE_IS,
      c.PRACTICANTE
      ,c.id_situacion_especial
     -- , m.DE_MODALIDAD,   s.DE_SUB_MODALIDAD
    FROM rhtm_contrato c,
      RHVD_puesto_direccion pd,
     -- RHTX_MODALIDAD m ,
    --  RHTX_SUB_MODALIDAD s,
      RHTM_TRABAJADOR tr,
      RHTX_UB_PROVINCIA pr,
      RHTX_UB_DISTRITO ds
    WHERE c.id_puesto           = pd.id_puesto
    AND c.ID_TRABAJADOR         =tr.ID_TRABAJADOR
    AND tr.ID_DISTRITO          =ds.ID_DISTRITO
    AND ds.ID_PROVINCIA         =pr.ID_PROVINCIA
  --  AND s.ID_SUB_MODALIDAD      = c.ID_SUB_MODALIDAD
  --  AND s.ID_MODALIDAD          = m.ID_MODALIDAD
    AND c.ES_CONTRATO_TRABAJADOR='1') cc left outer join  (select mo.DE_MODALIDAD,sm.DE_SUB_MODALIDAD,sm.ID_SUB_MODALIDAD from RHTX_MODALIDAD mo ,RHTX_SUB_MODALIDAD sm where sm.ID_MODALIDAD = mo.ID_MODALIDAD) ss
    on  ( ss.ID_SUB_MODALIDAD      = cc.ID_SUB_MODALIDAD)
    ) a
  LEFT OUTER JOIN RHTX_GRUPO_OCUPACION g
  ON(g.ID_GRUPO_OCUPACION = a.ID_GRUPO_OCUPACION)
  left outer join rhtr_situacion_especial se
  on (se.id_situacion_especial=a.id_situacion_especial);

CREATE OR REPLACE FORCE VIEW "PROCESOSRH"."RHVD_CONTRATO_DGP" as

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
    c.practicante,c.ID_SITUACION_ESPECIAL,c.DE_SITUACION_ESPECIAL
  FROM RHVD_CONTRATOS_HISTORIAL c
  LEFT OUTER JOIN RHVD_DET_DGP G
  ON(c.ID_DGP=G.ID_DGP);

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
    PRACTICANTE_SP                 CHAR,idSituacionEspecialSP char)
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
, PRACTICANTE                 =PRACTICANTE_SP ,id_situacion_especial=idSituacionEspecialSP
WHERE ID_CONTRATO               = ID_CONTRATO_SP;
commit;
 RHSP_VAL_ESTADO_CONTRATO;
 commit;
END ;
/

CREATE TABLE "PROCESOSRH"."RHTV_NOTIFICATION" 
   (  "ID_NOTIFICATION" CHAR(12 BYTE), 
  "ID_ROL" CHAR(8 BYTE), 
  "ES_VISUALIZADO" CHAR(1 BYTE) DEFAULT 0, 
  "ES_LEIDO" CHAR(1 BYTE) DEFAULT 0, 
  "DE_NOTIFICATION" VARCHAR2(200 BYTE), 
  "DI_NOTIFICATION" VARCHAR2(300 BYTE), 
  "TITULO" VARCHAR2(40 BYTE), 
  "FECHA_REG" DATE, 
   PRIMARY KEY ("ID_NOTIFICATION")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE, 
   CONSTRAINT "FK_ID_ROL" FOREIGN KEY ("ID_ROL")
    REFERENCES "PROCESOSRH"."RHTR_ROL" ("ID_ROL") ENABLE
   ) ;


------------------------------------------------------------------------------------------------------------------------


  CREATE OR REPLACE TRIGGER "PROCESOSRH"."RHGT_INSERT_ID_NOTIFICATION" 
BEFORE INSERT ON RHTV_NOTIFICATION 
FOR EACH ROW
 DECLARE
 id_max number;
 id_new RHTV_NOTIFICATION.ID_NOTIFICATION %type;
 count_row number;
BEGIN
SELECT COUNT(ID_NOTIFICATION) INTO count_row  FROM RHTV_NOTIFICATION;
IF count_row >0 and count_row<999999  THEN
         SELECT 'NOT-'||lpad(to_char(MAX(TO_NUMBER(SUBSTR(ID_NOTIFICATION,5,8))+1)),6,'0') into :new.ID_NOTIFICATION FROM RHTV_NOTIFICATION ;
ELSIF count_row=0 THEN
    id_new:='NOT-000001';
    SELECT id_new INTO :new.ID_NOTIFICATION FROM DUAL;
END IF;
END;
/
ALTER TRIGGER "PROCESOSRH"."RHGT_INSERT_ID_NOTIFICATION" ENABLE;


------------------------------------------------------------------------------------------------------------------------


create or replace PROCEDURE RHSP_UPDATE_VIS_NOTIFICATION (ID_NOT_SP CHAR) 
IS BEGIN 
UPDATE RHTV_NOTIFICATION SET ES_VISUALIZADO=1
WHERE ID_NOTIFICATION=ID_NOT_SP; 
END ;
/

------------------------------------------------------------------------------------------------------------------------


create or replace PROCEDURE RHSP_UPDATE_REA_NOTIFICATION (ID_NOT_SP CHAR) 
IS BEGIN 
UPDATE RHTV_NOTIFICATION SET ES_LEIDO=1
WHERE ID_NOTIFICATION=ID_NOT_SP; 
END ;
/
alter table RHTV_NOTIFICATION add TIPO_NOTIFICATION CHAR (1 byte);

create or replace PROCEDURE RHSP_INSERT_NOTIFICATION (ID_ROL_NOT_SP CHAR,ES_VIS_SP CHAR, ES_LEIDO_SP CHAR, DE_NOT_SP VARCHAR2, DI_NOT_SP VARCHAR2, TITLE VARCHAR2, TI_NOT_SP CHAR) 
IS BEGIN 
INSERT INTO RHTV_NOTIFICATION ( ID_ROL, ES_VISUALIZADO, ES_LEIDO, DE_NOTIFICATION,DI_NOTIFICATION,TITULO,FECHA_REG,TIPO_NOTIFICATION) 
VALUES ( ID_ROL_NOT_SP, ES_VIS_SP, ES_LEIDO_SP, DE_NOT_SP, DI_NOT_SP,TITLE,SYSDATE(),TI_NOT_SP); 
END ;
/

create or replace PROCEDURE              "RHSP_INSERT_DGP" (
    ID_DGP_SP                     CHAR,
    FE_DESDE_SP                   DATE,
    FE_HASTA_SP                   DATE,
    CA_SUELDO_SP                  NUMBER,
    DE_DIAS_TRABAJO_SP            VARCHAR2,
    ID_PUESTO_SP                  CHAR,
    ID_REQUERIMIENTO_SP           CHAR,
    ID_TRABAJADOR_SP              CHAR,
    CO_RUC_SP                     VARCHAR2,
    DE_LUGAR_SERVICIO_SP          VARCHAR2,
    DE_SERVICIO_SP                VARCHAR2,
    DE_PERIODO_PAGO_SP            VARCHAR2,
    DE_DOMICILIO_FISCAL_SP        VARCHAR2,
    DE_SUBVENCION_SP              VARCHAR2,
    DE_HORARIO_CAPACITACION_SP    VARCHAR2,
    DE_HORARIO_REFRIGERIO_SP      VARCHAR2,
    DE_DIAS_CAPACITACION_SP       VARCHAR2,
    ES_DGP_SP                     CHAR,
    US_CREACION_SP                CHAR,
    FE_CREACION_SP                DATE,
    US_MODIF_SP                   CHAR,
    FE_MODIF_SP                   DATE,
    IP_USUARIO_SP                 VARCHAR2,
    CA_BONO_ALIMENTARIO_SP        NUMBER,
    DE_BEV_SP                     NUMBER,
    DE_ANTECEDENTES_POLICIALES_SP VARCHAR2,
    DE_CERTIFICADO_SALUD_SP       VARCHAR2,
    DE_MONTO_HONORARIO_SP         VARCHAR2,
    LI_MOTIVO_SP                  CHAR,
    ES_MFL_SP                     CHAR,
    BONO_PUESTO                   NUMBER,
    ASIGNACION_FAMILIAR_SP        NUMBER,
    -- nuevo, ES_PRESUPUESTADO
    ES_PRESUPUESTADO              CHAR)
IS

iddgp RHTM_DGP.ID_DGP%TYPE ;

area char(8) ;
dep char(8);  
BEGIN

  INSERT
  INTO RHTM_DGP
    (
      ID_DGP,
      FE_DESDE,
      FE_HASTA,
      CA_SUELDO,
      DE_DIAS_TRABAJO,
      ID_PUESTO,
      ID_REQUERIMIENTO,
      ID_TRABAJADOR,
      CO_RUC,
      DE_LUGAR_SERVICIO,
      DE_SERVICIO,
      DE_PERIODO_PAGO,
      DE_DOMICILIO_FISCAL,
      DE_SUBVENCION,
      DE_HORARIO_CAPACITACION,
      DE_HORARIO_REFRIGERIO,
      DE_DIAS_CAPACITACION,
      ES_DGP,
      US_CREACION,
      FE_CREACION,
      US_MODIF,
      FE_MODIF,
      IP_USUARIO,
      CA_BONO_ALIMENTARIO,
      DE_BEV,
      DE_ANTECEDENTES_POLICIALES,
      ES_CERTIFICADO_SALUD,
      DE_MONTO_HONORARIO,
      LI_MOTIVO,
      ES_MFL,
      CA_BONIFICACION_P,CA_ASIG_FAMILIAR,
      ES_PRESUPUESTADO
    )
    VALUES
    (
      ID_DGP_SP ,
      FE_DESDE_SP ,
      FE_HASTA_SP ,
      CA_SUELDO_SP ,
      DE_DIAS_TRABAJO_SP ,
      ID_PUESTO_SP ,
      ID_REQUERIMIENTO_SP ,
      ID_TRABAJADOR_SP ,
      CO_RUC_SP ,
      DE_LUGAR_SERVICIO_SP ,
      DE_SERVICIO_SP ,
      DE_PERIODO_PAGO_SP ,
      DE_DOMICILIO_FISCAL_SP ,
      DE_SUBVENCION_SP ,
      DE_HORARIO_CAPACITACION_SP ,
      DE_HORARIO_REFRIGERIO_SP ,
      DE_DIAS_CAPACITACION_SP ,
      null ,
      US_CREACION_SP ,
      sysdate ,
      US_MODIF_SP ,
      FE_MODIF_SP ,
      IP_USUARIO_SP ,
      CA_BONO_ALIMENTARIO_SP ,
      DE_BEV_SP ,
      DE_ANTECEDENTES_POLICIALES_SP ,
      DE_CERTIFICADO_SALUD_SP ,
      DE_MONTO_HONORARIO_SP,
      LI_MOTIVO_SP,
      ES_MFL_SP,
      BONO_PUESTO,ASIGNACION_FAMILIAR_SP,
      ES_PRESUPUESTADO
    )returning id_dgp into iddgp;
    commit;
    select trim(id_departamento) ,trim(id_area) into dep,area from rhvd_puesto_direccion where trim(id_puesto) =trim(ID_PUESTO_SP);
    
  rhsp_reg_cumplimiento_plazo (iddgp  ,ID_REQUERIMIENTO_SP , FE_DESDE_SP ,dep,area );
  commit;
     
END;
/

create or replace PROCEDURE              "RHSP_MODIFICAR_DGP" (
    ID_DGP_SP                     CHAR,
    FE_DESDE_SP                   DATE,
    FE_HASTA_SP                   DATE,
    CA_SUELDO_SP                  NUMBER,
    DE_DIAS_TRABAJO_SP            VARCHAR2,
    ID_PUESTO_SP                  CHAR,
    ID_REQUERIMIENTO_SP           CHAR,
    ID_TRABAJADOR_SP              CHAR,
    CO_RUC_SP                     VARCHAR2,
    DE_LUGAR_SERVICIO_SP          VARCHAR2,
    DE_SERVICIO_SP                VARCHAR2,
    DE_PERIODO_PAGO_SP            VARCHAR2,
    DE_DOMICILIO_FISCAL_SP        VARCHAR2,
    DE_SUBVENCION_SP              VARCHAR2,
    DE_HORARIO_CAPACITACION_SP    VARCHAR2,
    DE_HORARIO_REFRIGERIO_SP      VARCHAR2,
    DE_DIAS_CAPACITACION_SP       VARCHAR2,
    ES_DGP_SP                     CHAR,
    US_CREACION_SP                CHAR,
    FE_CREACION_SP                DATE,
    US_MODIF_SP                   CHAR,
    FE_MODIF_SP                   DATE,
    IP_USUARIO_SP                 VARCHAR2,
    CA_BONO_ALIMENTARIO_SP        NUMBER,
    DE_BEV_SP                     NUMBER,
    DE_ANTECEDENTES_POLICIALES_SP VARCHAR2,
    ES_CERTIFICADO_SALUD_SP       VARCHAR2,
    DE_MONTO_HONORARIO_SP         VARCHAR2,
    LI_MOTIVO_SP                  CHAR,
    ES_MFL_SP                     CHAR,
    BONO_PUESTO                   NUMBER,
    ES_PRESUPUESTADO_SP           CHAR)
IS
BEGIN
UPDATE RHTM_DGP SET FE_DESDE=FE_DESDE_SP,
      FE_HASTA=FE_HASTA_SP,
      CA_SUELDO=CA_SUELDO_SP,
      DE_DIAS_TRABAJO=DE_DIAS_TRABAJO_SP,
      ID_PUESTO=ID_PUESTO_SP,
      ID_REQUERIMIENTO=ID_REQUERIMIENTO_SP,
      ID_TRABAJADOR=ID_TRABAJADOR_SP,
      CO_RUC=CO_RUC_SP,
      DE_LUGAR_SERVICIO=DE_LUGAR_SERVICIO_SP,
      DE_SERVICIO=DE_SERVICIO_SP,
      DE_PERIODO_PAGO=DE_PERIODO_PAGO_SP,
      DE_DOMICILIO_FISCAL=DE_DOMICILIO_FISCAL_SP,
      DE_SUBVENCION=DE_SUBVENCION_SP,
      DE_HORARIO_CAPACITACION=DE_HORARIO_CAPACITACION_SP,
      DE_HORARIO_REFRIGERIO=DE_HORARIO_REFRIGERIO_SP,
      DE_DIAS_CAPACITACION=DE_DIAS_CAPACITACION_SP,
      --US_CREACION=US_CREACION_SP,
      US_MODIF=US_MODIF_SP,
      FE_MODIF=sysdate,
      IP_USUARIO=IP_USUARIO_SP,
      CA_BONO_ALIMENTARIO=CA_BONO_ALIMENTARIO_SP,
      DE_BEV=DE_BEV_SP,
      DE_ANTECEDENTES_POLICIALES=DE_ANTECEDENTES_POLICIALES_SP,
      ES_CERTIFICADO_SALUD=ES_CERTIFICADO_SALUD_SP,
      DE_MONTO_HONORARIO=DE_MONTO_HONORARIO_SP,
      LI_MOTIVO=LI_MOTIVO_SP,
      ES_MFL=ES_MFL_SP,
      CA_BONIFICACION_P=BONO_PUESTO,
      ES_PRESUPUESTADO=ES_PRESUPUESTADO_SP  
      WHERE ID_DGP=ID_DGP_SP;
      COMMIT;
END;
/
create or replace PROCEDURE RHSP_UPDATE_TIPO_HORARIO (ID_TIPO_HOR_SP CHAR,ES_HOR_SP CHAR) 
IS BEGIN 
UPDATE RHTR_TIPO_HORARIO SET ES_HORARIO= ES_HOR_SP WHERE ID_TIPO_HORARIO=ID_TIPO_HOR_SP; 
END ;
/
CREATE OR REPLACE PROCEDURE RHSP_UPDATE_ES_PROCESO (ID_PRO_SP CHAR,ES_PRO_SP CHAR) 
IS BEGIN 
UPDATE RHTV_PROCESO SET ES_PROCESO= ES_PRO_SP WHERE ID_PROCESO=ID_PRO_SP; 
END ;
/

alter table RHTV_NOTIFICATION add (
"ID_USUARIO" CHAR (10 byte),
CONSTRAINT FK_ID_USUARIO FOREIGN KEY ("ID_USUARIO") REFERENCES "PROCESOSRH"."RHTC_USUARIO"("ID_USUARIO") ENABLE
);

create or replace PROCEDURE RHSP_INSERT_NOTIFICATION (ID_ROL_NOT_SP CHAR,ES_VIS_SP CHAR, ES_LEIDO_SP CHAR, DE_NOT_SP VARCHAR2, DI_NOT_SP VARCHAR2, TITLE VARCHAR2, TI_NOT_SP CHAR, US_NOT_SP CHAR) 
IS BEGIN 
INSERT INTO RHTV_NOTIFICATION ( ID_ROL, ES_VISUALIZADO, ES_LEIDO, DE_NOTIFICATION,DI_NOTIFICATION,TITULO,FECHA_REG,TIPO_NOTIFICATION, ID_USUARIO) 
VALUES ( ID_ROL_NOT_SP, ES_VIS_SP, ES_LEIDO_SP, DE_NOT_SP, DI_NOT_SP,TITLE,SYSDATE(),TI_NOT_SP,US_NOT_SP); 
END ;
/