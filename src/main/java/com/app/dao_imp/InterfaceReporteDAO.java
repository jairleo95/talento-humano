/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao_imp;

import java.util.List;
import java.util.Map;
import com.app.model.Datos_Generales;

/**
 *
 * @author ALFA 3
 */
public interface InterfaceReporteDAO {

    public List<Map<String, ?>> Contrato_Mes();

    public List<Datos_Generales> Reporte_Datos_Generales();

    public List<Map<String, ?>> listar_padre_hi(String desde, String hasta, String edad,String aps,String dep,String are,String sec,String pue,String nom,String pat,String mat,String num,String tip);

    public List<Map<String, ?>> lirtar_trabajor_Navidad(String mes);

    public List<Map<String, ?>> Reporte_Datos_Gen(String aps, String dep, String are, String sec, String puesto, String edad, String ape, String mat, String nom, String num);
    
    public List<Map<String, ?>> reporte_hijos(String sql);
    
    public List<Map<String, ?>> datosTrabajador(String direccion, String dep, String area, String sec, String puesto);

}
