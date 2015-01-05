/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.edu.upeu.application.dao_imp.InterfaceCentro_CostosDAO;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.model.Centro_Costos;

/**
 *
 * @author JAIR
 */
public class Centro_CostoDAO implements InterfaceCentro_CostosDAO {

    ConexionBD cnn;

    @Override
    public List<Map<String, ?>> List_centro_costo(String iddep) {

        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "select * from rhtr_centro_costo where  id_departamento='" + iddep.trim() + "' ";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("id_centro_costo"));
                rec.put("nombre", rs.getString("de_centro_costo"));
                Lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error!");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return Lista;
    }

    @Override
    public List<Centro_Costos> List_centro_costo() {
        this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from RHTR_CENTRO_COSTO order by DE_CENTRO_COSTO";
        List<Centro_Costos> list = new ArrayList<Centro_Costos>();
        try {
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Centro_Costos d = new Centro_Costos();
                d.setId_centro_costo(rs.getString("ID_CENTRO_COSTO"));
                d.setDe_centro_costo(rs.getString("DE_CENTRO_COSTO"));
                d.setCo_centro_costo(rs.getString("CO_CENTRO_COSTO"));
                d.setId_departamento(rs.getString("ID_DEPARTAMENTO"));
                list.add(d);
            }
        } catch (SQLException e) {
        } finally {
            this.cnn.close();
        }

        return list;
    }

    @Override
    public List<Map<String, ?>> Direccion_CC() {

        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "select distinct(d.id_direccion) as id_direccion, d.no_direccion    from rhtr_centro_costo cc, rhtx_departamento dp , rhtx_direccion d where dp.id_departamento = cc.id_departamento and  dp.id_direccion = d.id_direccion";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("id_direccion"));
                rec.put("nombre", rs.getString("no_direccion"));
                Lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error!");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return Lista;
    }

    @Override
    public List<Map<String, ?>> Departamento_CC(String iddir) {

        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "select  distinct (cc.id_departamento) as id_departamento,dp.no_dep   from rhtr_centro_costo cc, rhtx_departamento dp , rhtx_direccion d where dp.id_departamento = cc.id_departamento and  dp.id_direccion = d.id_direccion and d.id_direccion='" + iddir + "'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("id_departamento"));
                rec.put("nombre", rs.getString("no_dep"));
                Lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error!");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return Lista;
    }

    @Override
    public List<Map<String, ?>> Centro_Costo_Dep(String iddep) {

        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "select  cc.id_centro_costo,cc.de_centro_costo  from rhtr_centro_costo cc, rhtx_departamento dp , rhtx_direccion d where dp.id_departamento = cc.id_departamento and  dp.id_direccion = d.id_direccion and cc.id_departamento='" + iddep + "'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("id_centro_costo"));
                rec.put("nombre", rs.getString("de_centro_costo"));
                Lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error!");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return Lista;
    }

}