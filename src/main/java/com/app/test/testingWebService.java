/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.app.persistence.dao.Carga_AcademicaDAO;
import com.app.persistence.dao_imp.InterfaceCarga_AcademicaDAO;
import com.app.config.globalProperties;

/**
 *
 * @author JAIR
 */
public class testingWebService {

    public static void main(String[] args) {
        
        System.out.println(":::::test");
        InterfaceCarga_AcademicaDAO a = new Carga_AcademicaDAO();
        try {
            a.syncupCargaAcademica("2017-1", globalProperties.DOCENTESXCURSO_METHOD);
        } catch (Exception ex) {
            Logger.getLogger(testingWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
