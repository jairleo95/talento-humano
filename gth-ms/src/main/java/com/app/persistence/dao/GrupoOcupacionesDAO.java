/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.persistence.dao_imp.IGrupo_ocupacionesDAO;
import com.app.config.factory.DBConnection;
import com.app.config.factory.FactoryConnectionDB;
import com.app.domain.model.Grupo_Ocupaciones;

/**
 *
 * @author joserodrigo
 */
public class GrupoOcupacionesDAO implements IGrupo_ocupacionesDAO {
    DBConnection conn;
    @Override
    public List<Grupo_Ocupaciones> List_grup_ocu() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = " select * from RHTX_GRUPO_OCUPACION order by de_grupo_ocupacion ";
        List<Grupo_Ocupaciones> Lista = new ArrayList<Grupo_Ocupaciones>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Grupo_Ocupaciones x = new Grupo_Ocupaciones();
                x.setId_grupo_ocupacion(rs.getString("id_grupo_ocupacion"));
                x.setCo_grupo_ocupacion(rs.getString("co_grupo_ocupacion"));
                x.setDe_grupo_ocupacion(rs.getString("de_grupo_ocupacion"));
                Lista.add(x);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            this.conn.close();
        }
                return Lista;
    }
    
}
