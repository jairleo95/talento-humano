
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
