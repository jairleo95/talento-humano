/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface InterfaceComentario_DGPDAO {

    public void INSERT_COMENTARIO_DGP(String ID_COMENTARIO_DGP, String ID_DGP, String ID_AUTORIZACION, String CM_COMENTARIO, String US_CREACION, String FE_CREACION, String US_MODIFICACION, String FE_MODIFICACION, String ES_COMENTARIO_DGP);

    public List<Map<String, ?>> List_Comentario_DGP(String id_dgp);
    
    public String Comentario_dgp_aut(String iddgp, String id_aut);
}
