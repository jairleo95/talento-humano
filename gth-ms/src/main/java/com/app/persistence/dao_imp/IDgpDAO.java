/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.SalaryAccount;
import com.app.domain.model.V_Det_DGP;
import com.app.domain.model.V_Es_Req_Incompleto;
import com.app.domain.model.V_Es_Requerimiento;
import com.app.domain.model.X_List_det_dgp;
import com.app.domain.model.X_List_dgp_by;
import com.app.domain.model.X_User_dgp;
import com.app.domain.model.X_val_tra_dgp;
import com.app.domain.model.x_List_Id_Trab_Dgp;

/**
 *
 * @author Jose
 */
public interface IDgpDAO {

    List<X_User_dgp> USER_DGP(String id_dgp);

    void insert(String ID_DGP, String FE_DESDE, String FE_HASTA, double CA_SUELDO, String DE_DIAS_TRABAJO, String ID_PUESTO, String ID_REQUERIMIENTO, String ID_TRABAJADOR, String CO_RUC, String DE_LUGAR_SERVICIO, String DE_SERVICIO, String DE_PERIODO_PAGO, String DE_DOMICILIO_FISCAL, String DE_SUBVENCION, String DE_HORARIO_CAPACITACION, String DE_HORARIO_REFRIGERIO, String DE_DIAS_CAPACITACION, String ES_DGP, String US_CREACION, String FE_CREACION, String US_MODIF, String FE_MODIF, String IP_USUARIO, double CA_BONO_ALIMENTARIO, double DE_BEV, String DE_ANTECEDENTES_POLICIALES, String ES_CERTIFICADO_SALUD, String DE_MONTO_HONORARIO, String LI_MOTIVO, String ES_MFL, double BONO_PUESTO, double ASIGNACION_FAMILIAR, String ES_PRESUPUESTADO);

    List<x_List_Id_Trab_Dgp> LIST_ID_TRAB_DGP(String id);

    List<X_List_det_dgp> LIST_DET_DGP(String id_dep);

    List<X_List_dgp_by> LIST_DGP_BY(String id_user);

    void VAL_DGP_PASOS();

    void eliminarDGP(String IDDGP);

    void enableDGP(String IDDGP);

    List<V_Es_Requerimiento> LIST_DGP_PROCESO(String id_dep, String id_dir,String idPuesto, Boolean procAcad, boolean admin);

    List<X_val_tra_dgp> VAL_TRA_DGP(String id_tr);

    int VAL_OPC_DGP(String idtr);

    List<V_Det_DGP> LIST_ID_DGP(String id);

    String MAX_ID_DGP();

    int VALIDAR_DGP_CONTR(String id_dgp, String id_tr);

    void REG_DGP_FINAL(String IDDGP);

    void RECHAZAR_DGP(String IDDGP);

    void MOD_REQUE(String ID_DGP, String FE_DESDE, String FE_HASTA, double CA_SUELDO, String ID_PUESTO, String ID_REQUERIMIENTO, double CA_BONO_ALIMENTARIO, double DE_BEV, double CA_CENTRO_COSTOS, String DE_ANTECEDENTES_POLICIALES, String DE_CERTIFICADO_SALUD);

    List<V_Es_Req_Incompleto> List_Incomplet(String iddep, boolean permisoAdmin);

    int VALIDAR_DGP_CONTRATO(String id);

    int Can_cc_iddgp(String id_dgp);

    List<Map<String, ?>> list_Req(String id);

    List<Map<String, ?>> Cargar_Datos_Dgp(String id);

    String getProcessDetail(String iddgp, String idrp, String iddep);

    List<SalaryAccount> LIST_CUEN_SUEL(String id_trabajador);

    String obt_idtr_x_dgp(String id_dgp);

    void update(String ID_DGP, String FE_DESDE, String FE_HASTA, double CA_SUELDO, String DE_DIAS_TRABAJO, String ID_PUESTO, String ID_REQUERIMIENTO, String ID_TRABAJADOR, String CO_RUC, String DE_LUGAR_SERVICIO, String DE_SERVICIO, String DE_PERIODO_PAGO, String DE_DOMICILIO_FISCAL, String DE_SUBVENCION, String DE_HORARIO_CAPACITACION, String DE_HORARIO_REFRIGERIO, String DE_DIAS_CAPACITACION, String ES_DGP, String US_CREACION, String FE_CREACION, String US_MODIF, String FE_MODIF, String IP_USUARIO, double CA_BONO_ALIMENTARIO, double DE_BEV, String DE_ANTECEDENTES_POLICIALES, String ES_CERTIFICADO_SALUD, String DE_MONTO_HONORARIO, String LI_MOTIVO, String ES_MFL, double BONO_PUESTO, String ES_PRESUPUESTADO);

    boolean val_fe_inicio_dgp(String fecha);
}
