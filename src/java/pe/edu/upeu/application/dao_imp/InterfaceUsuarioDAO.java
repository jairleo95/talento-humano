/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.edu.upeu.application.dao_imp;

import pe.edu.upeu.application.model.Usuario;
import java.util.List;
import pe.edu.upeu.application.model.Trabajador;
import pe.edu.upeu.application.model.V_Usuario;

/**
 *
 * @author Alfa.sistemas
 */
public interface InterfaceUsuarioDAO {
    public List<Usuario> List_Usuario();
    public List<V_Usuario> Val_Usuario(String Usuario, String PWD);
    public int  Val_Cuenta(int Usuario);
    public boolean Mod_perfil(Usuario proveedor,Trabajador trabajador);
    public List<V_Usuario> Val_Usuario(String id_usuario);
    public boolean Mod_Pw(String nombre ,String usuario, String clave);
    public Usuario Edit_Usuario( String ID);
    public boolean Edit_Usuario(Usuario usuario);
    public boolean Save_Usuario(Usuario usuario);
    public boolean Delete_Usuario(String ID);
}
