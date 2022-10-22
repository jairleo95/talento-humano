/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.Carrera;
import com.app.domain.model.Tipo_Institucion;

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
