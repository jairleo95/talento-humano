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
import pe.edu.upeu.application.dao_imp.InterfaceAnnoDAO;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.model.Anno;
import pe.edu.upeu.application.model.Contrato;

/**
 *
 * @author Admin
 */
public class AnnoDAO implements InterfaceAnnoDAO {

    ConexionBD conn;

    @Override
    public List<Anno> List_Anno() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtr_anno order by id_anno desc";
        List<Anno> list = new ArrayList<Anno>();
        try {
            ResultSet rs = this.conn.query(sql);
            
            while (rs.next()) {
                Anno a = new Anno();
                a.setId_anno(rs.getString("id_anno"));
                a.setNo_anno(rs.getString("no_anno"));
                a.setDe_anno(rs.getString("de_anno"));
                a.setFe_desde(rs.getString("fe_desde"));
                a.setFe_hasta(rs.getString("fe_hasta"));
                a.setDe_observacion(rs.getString("de_observacion"));
                list.add(a);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }

    @Override
    public String List_Anno_Max_Cont(String id_Trabajador) {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "SELECT ID_ANNO FROM RHTR_ANNO WHERE ID_ANNO=(SELECT 'ANN-'||lpad(TO_CHAR(MAX(TO_NUMBER(SUBSTR(ID_ANNO,5,8)))),6,'0')FROM(SELECT f.id_anno,f.no_anno,f.id_trabajador FROM (SELECT a.id_anno, a.no_anno , r.id_dgp , r.id_trabajador FROM RHTR_ANNO a ,RHTM_CONTRATO r WHERE a.id_anno=r.id_anno AND r.ES_CONTRATO_TRABAJADOR=1)f WHERE f.id_trabajador='"+id_Trabajador.trim()+"' ORDER BY f.no_anno DESC))";
        ResultSet rs = this.conn.query(sql);
        String id = null;

        try {
            rs.next();
            id = rs.getString(1);
        } catch (SQLException ex) {
            //Logger.getLogger(AnnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.conn.close();
        }
        return id;
    }

    @Override
    public List<Contrato> List_A_Cont(String id_dgp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Anno> List_anno_max() {
        this.conn=FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql="Select * from RHTR_ANNO WHERE ID_ANNO=(SELECT 'ANN-'||lpad(to_char(MAX(TO_NUMBER(SUBSTR(ID_ANNO,5,8)))),6,'0') FROM RHTR_ANNO)";
        List<Anno> a= new ArrayList<Anno>();
        try {
            ResultSet rs=this.conn.query(sql);
            while(rs.next()){
                Anno b=new Anno();
                b.setId_anno(rs.getString("id_anno"));
                b.setNo_anno(rs.getString("no_anno"));
                b.setDe_anno(rs.getString("de_anno"));
                b.setFe_desde(rs.getString("fe_desde"));
                b.setFe_hasta(rs.getString("fe_hasta"));
                b.setDe_observacion(rs.getString("de_observacion"));
                a.add(b);
            }
        } catch (SQLException e) {
        }finally{
            this.conn.close();
        }
        return a;
    }
    @Override
    public List<Anno> List_anno_ma() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Anno> List_Anno_trabajador(String idtr) {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = " SELECT f.id_anno,f.no_anno,f.id_trabajador,f.ID_CONTRATO FROM(SELECT a.id_anno, a.no_anno ,r.id_dgp ,r.id_trabajador,r.ID_CONTRATO FROM RHTR_ANNO a ,RHTM_CONTRATO r WHERE a.id_anno  =r.id_anno AND r.ES_CONTRATO_TRABAJADOR=1)f WHERE f.id_trabajador='"+idtr.trim()+"' ORDER BY f.no_anno DESC";
        List<Anno> list = new ArrayList<Anno>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Anno a = new Anno();
                a.setId_anno(rs.getString("id_anno"));
                a.setNo_anno(rs.getString("no_anno"));
                a.setId_contrato(rs.getString("id_contrato"));
                list.add(a);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }
}
