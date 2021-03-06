/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.dao_imp;

import java.util.List;
import com.app.model.Detalle_Privilegio;
import com.app.model.X_Detalle_Privilegio;

/**
 *
 * @author joserodrigo
 */
public interface InterfaceDetalle_PrivilegioDAO {
    public void Registrar_Detalle_Priv (String id_rol,String nu_orden, String id_priv, String es_detalle_priv);
    public void Mod_Detalle_Priv (String id_det_pri,int nu_orden, String id_priv, String es_detalle_priv);
    public void Elim_Detalle_Priv (String id_pri_rol);
    public void Desc_r_pr (String id_pr_r);
    public void Act_r_pr (String id_pr_r);
    public List<Detalle_Privilegio> List_det_pr_id(String id_pr);
    public List<X_Detalle_Privilegio> List_det_pri_id(String id_pr);
}
