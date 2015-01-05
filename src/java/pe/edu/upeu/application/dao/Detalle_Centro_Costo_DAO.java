/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import pe.edu.upeu.application.dao_imp.InterfaceDetalle_Centro_Costo;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.web.controller.CConversion;

/**
 *
 * @author JAIR
 */
public class Detalle_Centro_Costo_DAO implements InterfaceDetalle_Centro_Costo {

    ConexionBD conn;
    CConversion c = new CConversion();

    @Override
    public void INSERT_DETALLE_CENTRO_COSTO(String ID_DETALLE_CENTRO_COSTO, String ID_CENTRO_COSTO, String ID_DGP, Double CA_PORCENTAJE, String ES_DETALLE_CC, String US_CREACION, String FE_CREACION, String US_MODIF, String FE_MODIF, String IP_USUARIO) {
        try {
            this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            CallableStatement cst = this.conn.conex.prepareCall("{CALL RHSP_INSERT_DETALLE_CC( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )} ");
            cst.setString(1, null);
            cst.setString(2, ID_CENTRO_COSTO);
            cst.setString(3, ID_DGP);
            cst.setDouble(4, CA_PORCENTAJE);
            cst.setString(5, ES_DETALLE_CC);
            cst.setString(6, US_CREACION);
            cst.setString(7, FE_CREACION);
            cst.setString(8, US_MODIF);
            cst.setString(9, FE_MODIF);
            cst.setString(10, IP_USUARIO);
            cst.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            this.conn.close();
        }
    }

}