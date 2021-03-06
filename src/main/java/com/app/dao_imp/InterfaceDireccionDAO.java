/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.model.Direccion;
import com.app.model.Via;
import com.app.model.Zona;

/**
 *
 * @author joserodrigo
 */
public interface InterfaceDireccionDAO {

    /**
     * direccion del organigrama de la universidad
     *
     * @return
     */
    public List<Direccion> Listar_Direccion();
    public List<Map<String, ?>> List_Direccion();
    public List<Map<String, ?>> List_Direccion_filial(String fil);
    /*direccion de ubicacion geografica*/
    public List<Via> Listar_via();
    public List<Zona> Listar_zona();
    public List<Map<String, ?>> List_Direccion_estado();
    //Mantenimiento
    public boolean Editar_Direccion(String id, String nombre, String ncorto, String estado, String filial);
    public boolean Crear_Direccion(String nombre, String ncorto, String estado, String filial);
    public boolean Eliminar_Direccion(String id);
    public boolean Activar_Direccion(String id);
    public boolean Desactivar_Direccion(String id);
    
}
