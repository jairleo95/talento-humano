/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.dao_imp;

import java.util.List;
import java.util.Map;
import pe.edu.upeu.application.model.Centro_Costos;

/**
 *
 * @author joserodrigo
 */
public interface InterfaceCentro_CostosDAO {

    public List<Map<String, ?>> List_centro_costo(String iddep);

    public List<Centro_Costos> List_centro_costo();
}
