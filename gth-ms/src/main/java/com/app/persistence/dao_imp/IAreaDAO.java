/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.Area;

/**
 *
 * @author Admin
 */
public interface IAreaDAO {

    public Area getAreaById(String idArea);

    public List<Area> List_Area();

    public List<Area> List_Area_Lima();

    public List<Area> List_Area_ID(String id_departamento);

    public List<Map<String, ?>> List_area_id_json(String id_dep);

    public List<Map<String, ?>> List_area_id_d(String id_dep);

    public List<Map<String, ?>> selec_area(String id_pu);

    //CRUD
    public List<Map<String, ?>> List_area_es(String idDep);

    public boolean crear_area(String nombre, String ncorto, String estado, String idDep);

    public boolean editar_area(String idArea, String nombre, String ncorto, String estado, String idDep);

    public boolean activar_area(String idArea);

    public boolean desactivar_area(String idArea);

    public boolean eliminar_area(String idArea);

}
