package com.app.config.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Docente
 */
public final class ORACLEConnectionDB extends DBConnection {

    ORACLEConnectionDB(String[] parametro) {
        this.parameter = parametro;
        this.open();
    }

    @Override
    Connection open() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:" + this.parameter[0] + "/" + this.parameter[1] + "@" + this.parameter[2] + ":" + this.parameter[3] + ":" + this.parameter[4];
        try {
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            System.out.println("Error al conectar : "+e);
            e.printStackTrace();
        }
        return this.connection;
    }
}
