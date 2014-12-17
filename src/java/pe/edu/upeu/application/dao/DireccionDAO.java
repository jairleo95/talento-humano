/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.upeu.application.dao_imp.InterfaceDireccionDAO;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.model.Direccion;
import pe.edu.upeu.application.model.Via;
import pe.edu.upeu.application.model.Zona;

/**
 *
 * @author joserodrigo
 */
public class DireccionDAO implements InterfaceDireccionDAO {

    ConexionBD conn;

    @Override
    public List<Direccion> Listar_Direccion() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = " select * from RHTX_DIRECCION";
        List<Direccion> Lista = new ArrayList<Direccion>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Direccion x = new Direccion();
                x.setId_direccion(rs.getString("id_direccion"));
                x.setNo_direccion(rs.getString("no_direccion"));
                x.setNo_corto_dir(rs.getString("no_corto_dir"));
                x.setId_filial(rs.getString("id_filial"));
                x.setEs_direccion(rs.getString("es_direccion"));
                Lista.add(x);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.conn.close();
        }
        return Lista;
    }

    @Override
    public List<Via> Listar_via() {
         this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = " select * from RHTX_VIA";
        List<Via> Lista = new ArrayList<Via>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Via x = new Via();
                x.setId_via(rs.getString("id_via"));
                x.setDe_via(rs.getString("de_via"));
                x.setCo_via(rs.getString("co_via"));
                Lista.add(x);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.conn.close();
        }
        return Lista;
    }

    @Override
    public List<Zona> Listar_zona() {
         this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = " select * from RHTX_ZONA";
        List<Zona> Lista = new ArrayList<Zona>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Zona x = new Zona();
                x.setId_zona(rs.getString("id_zona"));
                x.setDe_zona(rs.getString("de_zona"));
                x.setCo_zona(rs.getString("co_zona"));
                Lista.add(x);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.conn.close();
        }
        return Lista;
    }

}
