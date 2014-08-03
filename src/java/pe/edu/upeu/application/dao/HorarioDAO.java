/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.edu.upeu.application.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.upeu.application.dao_imp.InterfaceHorarioDAO;
import pe.edu.upeu.application.factory.Conexion;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.model.Horario;

/**
 *
 * @author Admin
 */
public class HorarioDAO implements InterfaceHorarioDAO{
    ConexionBD conn;




    @Override
    public String Max_id_Detalle_Horario() {
         this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql="SELECT 'DHO-' ||MAX (SUBSTR(ID_DETALLE_HORARIO,5,10)) FROM RHTD_DETALLE_HORARIO";
        String Max = "" ;
        try {
             ResultSet rs = this.conn.query(sql);
             while (rs.next()) {
                Max=rs.getString(1);
             }
        } catch (SQLException e) {
        }finally{
         this.conn.close();
         }
        return Max;
    }

    @Override
    public List<Horario> List_Horario() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql="select max(id_detalle_horario) from rhtd_detalle_horario";
        List<Horario> list= new ArrayList<Horario>();
        try {
             ResultSet rs = this.conn.query(sql);
             Horario h = new Horario();
             while (rs.next()) {
                h.setDia_horario(rs.getString("dia_horario"));
                h.setEs_horario(rs.getString("es_horario"));
                h.setHo_desde(rs.getString("ho_desde"));
                h.setHo_hasta(rs.getString("ho_hasta"));
                h.setId_detalle_horario(rs.getString("id_detalle_horario"));
                h.setId_horario(rs.getString("id_horario"));
             }
        } catch (SQLException e) {
        }finally{
         this.conn.close();
         }
        return list;
    }

    @Override
    public void Insert_Detalle_Horario(String ID_DET_HOR, String ID_DGP, String ES_DE_HOR, String US_CRE, String US_MODIF,String FE_CRE, String FE_MODIF) {
         CallableStatement cst;
         try {
            Connection cx = Conexion.getConex();
            this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            cst = cx.prepareCall("{CALL RHSP_INSERT_DETALLE_HORARIO( ?, ?, ?, ?,?, ?, ?)}");
            cst.setString(1, null);
            cst.setString(2, ID_DGP);
            cst.setString(3, ES_DE_HOR);
            cst.setString(4, US_CRE);
            cst.setString(5, US_MODIF);
            cst.setString(6, FE_CRE);
            cst.setString(7, FE_MODIF);
            cst.execute();
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }catch (Exception ex){
            Logger.getLogger(AutorizacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
         this.conn.close();
         }
    }

    @Override
    public void Insert_Horario(String ID_HOR, String HO_DESDE, String HO_HASTA, String DIA_HOR, String ES_HOR, String ID_DET_HOR) {
        CallableStatement cst;
        try {
            Connection cx = Conexion.getConex();
            this.conn= FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            cst = cx.prepareCall("{CALL RHSP_INSERT_HORARIO( ?, ?, ?, ?, ?, ?)}");
            cst.setString(1,null);
            cst.setString(2,HO_DESDE);
            cst.setString(3,HO_HASTA);
            cst.setString(4,DIA_HOR);
            cst.setString(5,ES_HOR);
            cst.setString(6,ID_DET_HOR);
            cst.execute();
       } catch (SQLException e) {
            System.out.println(e.getMessage());
        }catch (Exception ex){
            Logger.getLogger(AutorizacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
         this.conn.close();
        }
    }
    
}
