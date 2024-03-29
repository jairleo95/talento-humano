/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.domain.model.Privilegio;
import com.app.domain.model.V_Privilegio_Rol;

/**
 *
 * @author Admin
 */
public interface InterfacePrivilegioDAO {
    public void Insert_Privilegio(String No_Link, String Di_url, String Es_privilegio,String Ic_Link,String Modulo);
    public boolean Insert_Proceso();
    public boolean Insert_Detalle_Privilegio();
    public List<Privilegio> List_Privilegio();
    public void Desactivar_Privilegio(String id_Priv);
    public void Eliminar_Privilegio(String id_Priv);
    public void Activar_Privilegio(String id_Priv);
    public void Mod_Priv(String id_Priv,String No_link,String Es_priv,String di_url,String ic_link);
    public List<Privilegio>List_Pri_Id(String id_Priv);
    public List<V_Privilegio_Rol>List_Pr_Rol();
    public List<Map<String,?>> List_Priv();
    public List<Map<String,?>> List_Modulo();
    public List<Map<String,?>> List_Priv_Mod();
    public List<Map<String,?>> List_Priv_Mod_x_id(String id_mod);
    
}
