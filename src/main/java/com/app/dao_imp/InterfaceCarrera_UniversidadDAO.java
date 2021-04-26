/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.model.Carrera;
import com.app.model.Tipo_Institucion;

/**
 *
 * @author JAIR
 */
public interface InterfaceCarrera_UniversidadDAO {

    public List<Map<String, ?>> Tipo_Institucion(String id);

    public List<Tipo_Institucion> List_Tipo_Ins();
    public List<Map<String, ?>> Istitucion(String id);

    public List<Map<String, ?>> Carrera_Id_universidad(String id);
    public List<Carrera> List_Carrera();
 }
