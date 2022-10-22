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
import com.app.persistence.dao_imp.InterfacePagoDocenteDAO;
import com.app.config.factory.ConexionBD;
import com.app.config.factory.FactoryConnectionDB;
import com.app.domain.model.PagoDocente;
import com.app.controller.util.CCriptografiar;

/**
 *
 * @author JAIR
 */
public class PagoDocenteDAO implements InterfacePagoDocenteDAO {

    ConexionBD conn;

    @Override
    public List<PagoDocente> getPagoDocenteByIdProcCA(String idpca) {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select ID_PAGO_DOCENTE, NU_CUOTA,CA_CUOTA,ES_PAGO_DOCENTE,ID_PROCESO_CARGA_AC ,to_char(fe_pago,'yyyy/mm/dd') as fe_pago from RHTD_PAGO_DOCENTE where ID_PROCESO_CARGA_AC='" + idpca + "'";
        List<PagoDocente> l = new ArrayList<PagoDocente>();
        try {
            ResultSet rs = this.conn.query(sql);
            while (rs.next()) {
                PagoDocente p = new PagoDocente();
                p.setIdPagoDocente(CCriptografiar.Encriptar(rs.getString("id_pago_docente")));
                p.setNuCuota(rs.getInt("nu_cuota"));
                p.setCaCuota(rs.getDouble("ca_cuota"));
                p.setEsPagoDocente(rs.getString("ES_PAGO_DOCENTE"));
                p.setFePago(rs.getString("fe_pago"));
                p.setIdProcesoCargaAc(CCriptografiar.Encriptar(rs.getString("ID_PROCESO_CARGA_AC")));
                l.add(p);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR : " + e.getMessage());
        } finally {
            try {
                this.conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return l;
    }

}
