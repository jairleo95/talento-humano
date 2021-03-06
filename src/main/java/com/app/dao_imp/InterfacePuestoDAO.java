/*
 +

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.model.Puesto;
import com.app.model.V_Puesto_Direccion;

/**
 *
 * @author Admin
 */
public interface InterfacePuestoDAO {

    public boolean Guardar_Puesto();

    public void Registrar_Puesto_Paso(String ID_DETALLE_PASOS, String ID_PASOS, String ID_PUESTO, String ES_DETALLE_PASOS, String CO_PUESTO);

    public boolean Eliminar_Puesto();

    public List<Puesto> List_Puesto();

    public List<Puesto> List_Puesto_lima();

    public String List_Puesto_x_iddgp(String id_dgp);

    public String List_Puesto_x_id_con(String id_contrato);

    public List<V_Puesto_Direccion> List_Puesto_Dep(String id_departamento);

    public List<V_Puesto_Direccion> List_Det_Puesto();

    public List<Puesto> List_Id_Puesto(String id_puesto);

    public boolean Modif_Puesto(String id_puesto, String no_puesto, String no_corto_pu, String es_puesto, String id_seccion, String co_grupo);

    public List<Map<String, ?>> Listar_Puesto_id(String id);
    public List<Map<String, ?>> Listar_Puesto_id_es(String id);

    public List<Map<String, ?>> List_puesto();

    public String puesto(String id_cto);
    
    //MANTENIMIENTO
    
    public boolean crear_puesto(String nombre, String ncorto, String estado, String cgrupo, String idSec);
    public boolean editar_puesto(String id, String nombre, String ncorto, String estado, String cgrupo, String idSec);
    public boolean activar_puesto(String id);
    public boolean desactivar_puesto(String id);
    public boolean eliminar_puesto(String id);
}
