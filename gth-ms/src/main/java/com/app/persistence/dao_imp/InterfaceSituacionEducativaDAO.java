/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.Situacion_Educativa;

/**
 *
 * @author joserodrigo
 */
public interface InterfaceSituacionEducativaDAO {
    public List<Situacion_Educativa> List_SituacionEducativa();
    
    public List<Map<String, ?>> List_SituacionEducativaM();
}
