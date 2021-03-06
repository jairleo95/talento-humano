/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import com.app.dao_imp.InterfaceFuncionDAO;
import com.app.factory.ConexionBD;
import com.app.factory.FactoryConnectionDB;
import com.app.model.Funciones;

/**
 *
 * @author joserodrigo
 */
public class FuncionDAO implements InterfaceFuncionDAO {

    ConexionBD cnn;

    @Override
    public List<Funciones> Listar_funciones() {
        this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "SELECT f.ID_FUNCION,f.DE_FUNCION,f.ES_FUNCION,f.US_CREACION,f.FE_CREACION,f.US_MODIF,f.FE_MODIF,f.ID_PUESTO, f.TI_FUNCION,p.NO_PUESTO FROM RHTD_FUNCION f LEFT OUTER JOIN RHTR_PUESTO p ON( p.ID_PUESTO = f.ID_PUESTO)";
        List<Funciones> Lista = new ArrayList<Funciones>();
        try {
            ResultSet rs = this.cnn.query(sql);

            while (rs.next()) {
                Funciones x = new Funciones();
                x.setDe_funcion(rs.getString("DE_FUNCION"));
                x.setEs_funcion(rs.getString("ES_FUNCION"));
                x.setUs_creacion(rs.getString("US_CREACION"));
                x.setFe_creacion(rs.getString("FE_CREACION"));
                x.setUs_modif(rs.getString("US_MODIF"));
                x.setFe_modif(rs.getString("FE_MODIF"));
                x.setId_puesto(rs.getString("ID_PUESTO"));
                x.setNo_puesto(rs.getString("NO_PUESTO"));
                x.setTi_funcion(rs.getString("TI_FUNCION"));
                Lista.add(x);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.cnn.close();
        }
        return Lista;
    }

    @Override
    public List<Map<String, ?>> Listar_fun_x_pu(String id_pu) {
        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT f.ID_FUNCION,f.DE_FUNCION,f.ES_FUNCION, f.TI_FUNCION,p.ID_PUESTO,p.NO_PUESTO FROM RHTD_FUNCION f,RHTR_PUESTO p where p.ID_PUESTO = f.ID_PUESTO and f.ID_PUESTO='" + id_pu.trim() + "'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id_fu", rs.getString("ID_FUNCION"));
                rec.put("nom_fu", rs.getString("DE_FUNCION"));
                rec.put("es_fu", rs.getString("ES_FUNCION"));
                rec.put("id_pu", rs.getString("ID_PUESTO"));
                rec.put("no_pu", rs.getString("NO_PUESTO"));
                rec.put("ti_fu", rs.getString("TI_FUNCION"));
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
    public void Insertar_funcion(String id_pu, String de_fu, String user_crea, String tipo_funcion) {
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            CallableStatement cst = this.cnn.conex.prepareCall("{CALL RHSP_INSERT_FUNCION( ?, ?, ?, ?)}");
            cst.setString(1, id_pu.trim());
            cst.setString(2, de_fu.trim());
            cst.setString(3, user_crea.trim());
            cst.setString(4, tipo_funcion.trim());
            cst.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.cnn.close();
        }
    }

    @Override
    public boolean Modificar_funcion(String id_fun, String es_fun, String de_fun, String id_pu, String us_mod, String tipo_funcion) {
        boolean ok = false;
        this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        try {
            CallableStatement cst = this.cnn.conex.prepareCall("{CALL RHSP_MOD_FUNCION( ?, ?, ?, ?, ?, ?)}");
            cst.setString(1, id_fun.trim());
            cst.setString(2, de_fun.trim());
            cst.setString(3, es_fun.trim());
            cst.setString(4, us_mod.trim());
            cst.setString(5, id_pu.trim());
            cst.setString(6, tipo_funcion.trim());
            ok = cst.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            this.cnn.close();
        }
        return ok;
    }

    @Override
    public void Eliminar_funcion(String id_fun) {
        CallableStatement cst;
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            cst = cnn.conex.prepareCall("{CALL RHSP_ELIMINAR_FUNCION(?)}");
            cst.setString(1, id_fun);
            cst.execute();
        } catch (SQLException ex) {
        } finally {
            this.cnn.close();
        }
    }

    @Override
    public List<Map<String, ?>> Listar_Funciones() {
        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT f.ID_FUNCION,f.DE_FUNCION,f.ES_FUNCION,f.US_CREACION,f.FE_CREACION,f.US_MODIF,f.FE_MODIF,f.ID_PUESTO,p.NO_PUESTO, f.TI_FUNCION FROM RHTD_FUNCION f LEFT OUTER JOIN RHTR_PUESTO p ON( p.ID_PUESTO = f.ID_PUESTO) ORDER BY f.DE_FUNCION ASC";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id_fu", rs.getString("ID_FUNCION"));
                rec.put("de_fu", rs.getString("DE_FUNCION"));
                rec.put("es_fu", rs.getString("ES_FUNCION"));
                rec.put("us_cr", rs.getString("US_CREACION"));
                rec.put("fe_cr", rs.getString("FE_CREACION"));
                rec.put("us_mo", rs.getString("US_MODIF"));
                rec.put("fe_mo", rs.getString("FE_MODIF"));
                rec.put("id_pu", rs.getString("ID_PUESTO"));
                rec.put("no_pu", rs.getString("NO_PUESTO"));
                rec.put("ti_fu", rs.getString("TI_FUNCION"));
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
    public List<Map<String, ?>> Listar_dep_x_dir(String id_de) {
        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "select d.ID_DEPARTAMENTO, d.NO_DEP\n"
                    + "           from rhtx_departamento d, rhtx_direccion r\n"
                    + "             where d.ID_DIRECCION=r.ID_DIRECCION and d.ID_DIRECCION='" + id_de.trim() + "'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id_de", rs.getString("ID_DEPARTAMENTO"));
                rec.put("nom_de", rs.getString("NO_DEP"));
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
    public List<Map<String, ?>> Listar_ar_x_dep(String id_ar) {
        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql ="SELECT A.ID_AREA, A.NO_AREA\n" +
            "FROM RHTD_AREA A, RHTX_DEPARTAMENTO D\n" +
            "WHERE A.ID_DEPARTAMENTO= D.ID_DEPARTAMENTO\n" +
            "AND D.ID_DEPARTAMENTO='"+id_ar+"'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id_ar", rs.getString("ID_AREA"));
                rec.put("nom_ar", rs.getString("NO_AREA"));
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
    public List<Map<String, ?>> Listar_sec_x_area(String id_se) {
        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql ="SELECT S.ID_SECCION, S.NO_SECCION\n" +
                "FROM RHTD_AREA A, RHTR_SECCION S\n" +
                "WHERE S.ID_AREA= A.ID_AREA\n" +
                "AND A.ID_AREA='"+id_se+"'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id_se", rs.getString("ID_SECCION"));
                rec.put("nom_se", rs.getString("NO_SECCION"));
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
    public List<Map<String, ?>> Listar_pu_x_sec(String id_pu) {
        List<Map<String, ?>> Lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql ="SELECT P.ID_PUESTO, P.NO_PUESTO\n" +
                "FROM RHTR_PUESTO P, RHTR_SECCION S\n" +
                "WHERE S.ID_SECCION= P.ID_SECCION\n" +
                "AND P.ID_SECCION='"+id_pu+"'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id_pu", rs.getString("ID_PUESTO"));
                rec.put("nom_pu", rs.getString("NO_PUESTO"));
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
    public List<Funciones> List_x_fun_x_idpu(String id_pu) {
        this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "SELECT * FROM RHTD_FUNCION where ID_PUESTO='"+ id_pu.trim() + "' AND ES_FUNCION='1'";
        List<Funciones> Lista = new ArrayList<Funciones>();
        try {
            ResultSet rs = this.cnn.query(sql);

            while (rs.next()) {
                Funciones x = new Funciones();
                x.setId_fucion(rs.getString("ID_FUNCION"));
                x.setDe_funcion(rs.getString("DE_FUNCION"));
                x.setTi_funcion(rs.getString("TI_FUNCION"));
                Lista.add(x);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            this.cnn.close();
        }
        return Lista;
    }

}
