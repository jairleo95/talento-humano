/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.dao_imp;

import java.util.List;
import com.app.model.Fotos_Trabajador;

/**
 *
 * @author Admin
 */
public interface InterfaceImagenDAO {
    public List<Fotos_Trabajador> List_Foto_Trab(String id_trab);
}
