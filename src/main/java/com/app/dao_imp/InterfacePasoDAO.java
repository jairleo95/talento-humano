/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao_imp;

import java.util.List;
import java.util.Map;

/**
 *
 * @author JAIR
 */
public interface InterfacePasoDAO {

    public List<Map<String, ?>> List_Paso_Habilitado(String proceso);

    public void INSERT_PASOS(String ID_PASOS, String ID_PROCESO, String DE_PASOS, String NU_PASOS, String CO_PASOS);

    public void DELETE_PASOS(String ID_PASOS);

    public void UPDATE_NU_PASO(String ID_PASO, String NU_PASO);

    public List<Map<String, ?>> List_Paso_x_Puesto(String id);

    public void DELETE_PUESTO_PASO(String IDDP);

    public void ESTADO_DETALLE_PUESTO(String ID, String ESTADO);

    public void UPDATE_PASOS(String ID_PASOS, String ID_PROCESO, String DE_PASOS, String NU_PASOS, String CO_PASOS, String ES_PASO);

}
