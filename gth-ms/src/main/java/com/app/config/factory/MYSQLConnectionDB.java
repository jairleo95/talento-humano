/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.config.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Docente
 */
public final class MYSQLConnectionDB extends DBConnection {

    public MYSQLConnectionDB(String[] parametro) {
        this.parameter = parametro;
        this.open();
    }
   
    @Override
    public Connection open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/"+this.parameter[0],this.parameter[1],this.parameter[2]);
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }
        return this.connection;
    }
    
}
