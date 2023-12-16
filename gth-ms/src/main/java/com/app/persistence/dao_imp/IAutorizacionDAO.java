/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.Autorizacion;
import com.app.domain.model.V_Autorizar_Dgp;
import com.app.domain.model.X_List_De_Autorizacion;

/**
 *
 * @author Admin
 */
public interface IAutorizacionDAO {

    void insert(String ID_AUTORIZACION, String ID_DGP, String ES_AUTORIZACION, String NU_PASOS, String IP_USUARIO, String US_CREACION, String US_MODIF, String FE_MODIF, String CO_PUESTO, String ID_PUESTO, String ID_DETALLE_REQ_PROCESO, String ID_PASOS);

    String insertDev(String ID_AUTORIZACION, String ID_DGP, String ES_AUTORIZACION, String NU_PASOS, String IP_USUARIO, String US_CREACION, String US_MODIF, String FE_MODIF, String CO_PUESTO, String ID_PUESTO, String ID_DETALLE_REQ_PROCESO, String ID_PASOS);

    List<String> getDetail(String id_rpp);

    List<X_List_De_Autorizacion> List_Detalle_Autorizacion(String iddgp, String idrp);

    List<V_Autorizar_Dgp> List_id_Autorizacion(String id_aurotizacion, String id_user, String iddgp);

    List<V_Autorizar_Dgp> List_Autorizacion_Academico(String id_aurotizacion, String id_user, String iddgp);

    List<Autorizacion> NO_List_DGP();

    void Elim_Aut(String id_Autorizacion);

    void insert(String ID_COMENTARIO_DGP_SP, String id_autorizacion, String id_dgp, String us_creacion, String es_comentario, String fe_creacion, String comentario);

    int Val_Aut_DGP_M(String id_dgp);

    List<V_Autorizar_Dgp> List_Autorizados(String id_puesto);

    String Mes_plazo(String id_dgp);

    List<Map<String, ?>> List_Dgp_Autorizados(String id_usuario, int pageNumber, int pageSize, int mes, String año);

    Integer getListAuthorizeRequirementsSize(String id_usuario, int mes, String año);

    List<Map<String, ?>> List_procesar_req(boolean tipo_list, boolean permisoAsigFam, boolean permisoEsSistema);

    boolean UpdateDgp_EstadoProcesar(String[] iddgp, int tipo, boolean estado);

    List<Map<String, ?>> ShowCkbEstado_procesarIndiviual(String iddgp);
}
