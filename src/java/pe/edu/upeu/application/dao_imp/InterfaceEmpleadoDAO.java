/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.edu.upeu.application.dao_imp;

import java.util.List;
import pe.edu.upeu.application.model.V_List_Empleado;

/**
 *
 * @author Alfa.sistemas
 */
public interface InterfaceEmpleadoDAO {
    public String Id_Puesto_Personal(String ide);
    public void VALIDAR_EMPLEADO(String id_tra);
    public List<V_List_Empleado> Listar_Empleado(String Departamento);
    public List<V_List_Empleado> Listar_Empleado();
    public List<V_List_Empleado>Listar_Emp();
}
