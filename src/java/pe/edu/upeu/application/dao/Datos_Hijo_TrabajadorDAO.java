/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.upeu.application.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.upeu.application.dao_imp.InterfaceDatos_Hijo_Trabajador;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.model.Datos_Hijo_Trabajador;
import pe.edu.upeu.application.web.controller.CConversion;

/**
 *
 * @author Jose
 */
public class Datos_Hijo_TrabajadorDAO implements InterfaceDatos_Hijo_Trabajador {

    ConexionBD conn;
    CConversion c=new CConversion();
    
    @Override
    public void INSERT_DATOS_HIJO_TRABAJADOR(String ID_DATOS_HIJOS_TRABAJADOR, String ID_TRABAJADOR, String AP_PATERNO, String AP_MATERNO, String NO_HIJO_TRABAJADOR, String FE_NACIMIENTO, String ES_SEXO, String ES_TIPO_DOC, String NU_DOC, String ES_PRESENTA_DOCUMENTO, String ES_INSCRIPCION_VIG_ESSALUD, String ES_ESTUDIO_NIV_SUPERIOR, String US_CREACION, String FE_CREACION, String US_MODIF, String FE_MODIF, String IP_USUARIO, String ES_DATOS_HIJO_TRABAJADOR) {
        CallableStatement cst;
        try {
            this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            cst = conn.conex.prepareCall("{CALL RHSP_INSERT_DA_HI_TRA( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cst.setString(1, null);
            cst.setString(2, ID_TRABAJADOR);
            cst.setString(3, AP_PATERNO);
            cst.setString(4, AP_MATERNO);
            cst.setString(5, NO_HIJO_TRABAJADOR);
            cst.setString(6, c.convertFecha(FE_NACIMIENTO));
            cst.setString(7, ES_SEXO);
            cst.setString(8, ES_TIPO_DOC);
            cst.setString(9, NU_DOC);
            cst.setString(10, ES_PRESENTA_DOCUMENTO);
            cst.setString(11, ES_INSCRIPCION_VIG_ESSALUD);
            cst.setString(12, ES_ESTUDIO_NIV_SUPERIOR);
            cst.setString(13, US_CREACION);
            cst.setString(14, null);
            cst.setString(15, null);
            cst.setString(16, null);
            cst.setString(17, IP_USUARIO);
            cst.setString(18, "1");
            cst.execute();
        } catch (SQLException ex) {
        } finally {
            this.conn.close();
        }
    }

    @Override
    public List<Datos_Hijo_Trabajador> LISTA_HIJOS(String id) {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from RHTD_DATOS_HIJO_TRABAJADOR where ID_TRABAJADOR='" + id.trim() + "'";
        List<Datos_Hijo_Trabajador> Lista = new ArrayList<Datos_Hijo_Trabajador>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Datos_Hijo_Trabajador t = new Datos_Hijo_Trabajador();
                t.setId_datos_hijos_trabajador(rs.getString("id_datos_hijos_trabajador"));
                t.setId_trabajador(rs.getString("id_trabajador"));
                t.setAp_paterno(rs.getString("ap_paterno"));
                t.setAp_materno(rs.getString("Ap_materno"));
                t.setNo_hijo_trabajador(rs.getString("no_hijo_trabajador"));
                t.setFe_nacimiento(rs.getString("fe_nacimiento"));
                t.setEs_sexo(rs.getString("es_sexo"));
                t.setEs_tipo_doc(rs.getString("es_tipo_doc"));
                t.setNu_doc(rs.getString("nu_doc"));
                t.setEs_presenta_documento(rs.getString("es_presenta_documento"));
                t.setEs_inscripcion_vig_essalud(rs.getString("es_inscripcion_vig_essalud"));
                t.setEs_estudio_niv_superior(rs.getString("es_estudio_niv_superior"));
                t.setUs_creacion(rs.getString("us_creacion"));
                t.setFe_creacion(rs.getString("fe_creacion"));
                t.setUs_modif(rs.getString("us_modif"));
                t.setFe_modif(rs.getString("fe_modif"));
                t.setIp_usuario(rs.getString("ip_usuario"));
                t.setEs_datos_hijo_trabajador(rs.getString("es_datos_hijo_trabajador"));
                Lista.add(t);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return Lista;
    }

    @Override
    public int ASIGNACION_F(String idtr) {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select count(*) from RHTD_DATOS_HIJO_TRABAJADOR where to_number((sysdate - FE_NACIMIENTO)/365 ) >=18  and ID_TRABAJADOR='" + idtr + "'";
        int TOTAL = 0;
        try {
            ResultSet rs = this.conn.query(sql);
            while (rs.next()) {
                TOTAL = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return TOTAL;
    }

    @Override
    public void MOD_HIJOS_TRAB(String ID_DATOS_HIJOS_TRABAJADOR, String AP_PATERNO, String AP_MATERNO, String NO_HIJO_TRABAJADOR, String FE_NACIMIENTO, String ES_SEXO, String ES_TIPO_DOC, String NU_DOC,  String ES_INSCRIPCION_VIG_ESSALUD, String ES_ESTUDIO_NIV_SUPERIOR) {
        CallableStatement cst;
        try {
            this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            cst = conn.conex.prepareCall("UPDATE RHTD_DATOS_HIJO_TRABAJADOR SET ap_paterno=?, Ap_materno =? , no_hijo_trabajador =? , fe_nacimiento=? , es_sexo=? , es_tipo_doc=? , nu_doc=?, es_inscripcion_vig_essalud=? , es_estudio_niv_superior=? WHERE ID_DATOS_HIJOS_TRABAJADOR = '"+ID_DATOS_HIJOS_TRABAJADOR.trim()+"'");
            cst.setString(1, AP_PATERNO);
            cst.setString(2, AP_MATERNO);
            cst.setString(3, NO_HIJO_TRABAJADOR);
            cst.setString(4, c.convertFecha(FE_NACIMIENTO));
            cst.setString(5, ES_SEXO);
            cst.setString(6, ES_TIPO_DOC);
            cst.setString(7, NU_DOC);
            cst.setString(8, ES_INSCRIPCION_VIG_ESSALUD);
            cst.setString(9, ES_ESTUDIO_NIV_SUPERIOR);
            cst.execute();
        } catch (SQLException ex) {
        } finally {
            this.conn.close();
        }
    }

    @Override
    public void ELIMINAR_HIJO(String id_hijo, String id_id_trabajador) {
         this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
         String sql = "UPDATE RHTD_DATOS_HIJO_TRABAJADOR SET ES_DATOS_HIJO_TRABAJADOR = '0' WHERE ID_TRABAJADOR = '"+id_id_trabajador+"' and ID_DATOS_HIJOS_TRABAJADOR = '"+id_hijo+"'";
         ResultSet rs = this.conn.query(sql);
    }

    @Override
    public List<Datos_Hijo_Trabajador> LISTA_HIJO(String idHijo, String idtr) {
         this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from RHTD_DATOS_HIJO_TRABAJADOR where ID_TRABAJADOR='" +idtr.trim()+ "' and ID_DATOS_HIJOS_TRABAJADOR ='"+idHijo.trim()+"'";
        List<Datos_Hijo_Trabajador> Lista = new ArrayList<Datos_Hijo_Trabajador>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Datos_Hijo_Trabajador t = new Datos_Hijo_Trabajador();
                t.setId_datos_hijos_trabajador(rs.getString("id_datos_hijos_trabajador"));
                t.setId_trabajador(rs.getString("id_trabajador"));
                t.setAp_paterno(rs.getString("ap_paterno"));
                t.setAp_materno(rs.getString("Ap_materno"));
                t.setNo_hijo_trabajador(rs.getString("no_hijo_trabajador"));
                t.setFe_nacimiento(rs.getString("fe_nacimiento"));
                t.setEs_sexo(rs.getString("es_sexo"));
                t.setEs_tipo_doc(rs.getString("es_tipo_doc"));
                t.setNu_doc(rs.getString("nu_doc"));
                t.setEs_presenta_documento(rs.getString("es_presenta_documento"));
                t.setEs_inscripcion_vig_essalud(rs.getString("es_inscripcion_vig_essalud"));
                t.setEs_estudio_niv_superior(rs.getString("es_estudio_niv_superior"));
                t.setUs_creacion(rs.getString("us_creacion"));
                t.setFe_creacion(rs.getString("fe_creacion"));
                t.setUs_modif(rs.getString("us_modif"));
                t.setFe_modif(rs.getString("fe_modif"));
                t.setIp_usuario(rs.getString("ip_usuario"));
                t.setEs_datos_hijo_trabajador(rs.getString("es_datos_hijo_trabajador"));
                Lista.add(t);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return Lista;
    }

}
