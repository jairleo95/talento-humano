/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

/**
 *
 * @author ALFA 3
 */
public interface InterfaceCorreoDAO {

    public void Enviar(String emailSubject,String pwdSubject,String to, String from, String Asunto, String Cuerpo);

}
