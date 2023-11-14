/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

/**
 *
 * @author JAIR
 */
public interface IDetalleCentroCosto {

   public String INSERT_DETALLE_CENTRO_COSTO(String ID_DETALLE_CENTRO_COSTO,
          //  String ID_CENTRO_COSTO, 
            String ID_DGP, double CA_PORCENTAJE, String ES_DETALLE_CC, String US_CREACION, String FE_CREACION, String US_MODIF, String FE_MODIF,
            String IP_USUARIO, String ID_CONTRATO, String ID_DEPART_CENTRO_COSTO);
    public void Modificar_Centro_Costo_porc(String id_dcc, Double porc, String Id_user);

}
