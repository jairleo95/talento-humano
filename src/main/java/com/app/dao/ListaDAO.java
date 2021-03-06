/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app.dao_imp.InterfaceListaDAO;
import com.app.factory.ConexionBD;
import com.app.factory.FactoryConnectionDB;
import com.app.model.Auto_Mostrar;
import com.app.model.Carrera;
import com.app.model.Nacionalidad;
import com.app.model.Proceso;
import com.app.model.Situacion_Educativa;
import com.app.model.Tipo_Contrato;
import com.app.model.Universidad;
import com.app.model.Via;
import com.app.model.Zona;

/**
 *
 * @author Admin
 */
public class ListaDAO implements InterfaceListaDAO {

    ConexionBD conn;

    @Override
    public List<Nacionalidad> List_Nacionalidad() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_nacionalidad order by no_nacionalidad ";
        List<Nacionalidad> list = new ArrayList<Nacionalidad>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Nacionalidad n = new Nacionalidad();
                n.setId_nacionalidad(rs.getString("id_nacionalidad"));
                n.setNo_nacionalidad(rs.getString("no_nacionalidad"));
                list.add(n);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }

    @Override
    public List<Proceso> List_Proceso() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtv_proceso order by no_proceso";
        List<Proceso> list = new ArrayList<Proceso>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Proceso p = new Proceso();
                p.setDe_proceso(rs.getString("de_proceso"));
                p.setId_proceso(rs.getString("id_proceso"));
                p.setNo_proceso(rs.getString("no_proceso"));
                list.add(p);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }

    @Override
    public List<Carrera> List_Carrera() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_carrera";
        List<Carrera> list = new ArrayList<Carrera>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Carrera c = new Carrera();
                c.setId_carrera(rs.getString("id_carrera"));
                c.setNo_carrera(rs.getString("no_carrera"));
                list.add(c);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }

    @Override
    public List<Universidad> List_Universidad() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_universidad order by no_universidad";
        List<Universidad> list = new ArrayList<Universidad>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Universidad u = new Universidad();
                u.setId_universidad(rs.getString("id_universidad"));
                u.setNo_universidad(rs.getString("no_universidad"));
                u.setCo_universidad(rs.getString("co_universidad"));
                u.setId_tipo_institucion(rs.getString("id_tipo_institucion"));
                list.add(u);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }

    @Override
    public List<Auto_Mostrar> List_Auto_mostrar(String id_rol) {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select di_url from RHTX_AUTO_MOSTRAR where ID_ROL='" + id_rol + "'";
        List<Auto_Mostrar> list = new ArrayList<Auto_Mostrar>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Auto_Mostrar am = new Auto_Mostrar();
                am.setDi_url(rs.getString("di_url"));
                // am.setId_auto_mostrar(rs.getString("id_auto_mostrar"));
                //    am.setId_rol(rs.getString("id_rol"));
                list.add(am);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;
    }

    @Override
    public List<String> List_Estado_Civil() {
        List<String> list = new ArrayList<String>();
        list.add("Soltero(a)");
        list.add("Casado(a)");
        list.add("Divorcio(a)");
        list.add("Viudo(a)");
        list.add("Separado(a)");
        list.add("Conviviente(a)");
        return list;
    }

    @Override
    public List<String> List_Doc() {
        List<String> list = new ArrayList<String>();
        list.add("DNI");
        list.add("Carne de Extrangeria");
        list.add("Pasaporte");
        list.add("Partida de  Nacimiento");
        list.add("Libreta Electoral");
        list.add("Libreta Militar");
        list.add("Boleta Inscripci??n Militar");
        list.add("Permiso para Menores");
        return list;
    }

    @Override
    public List<String> List_Gs() {
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("AB");
        list.add("O");
        return list;
    }

    @Override
    public List<String> List_Sp() {
        List<String> list = new ArrayList<String>();
        list.add("AFP");
        list.add("ONP");
        list.add("Sin R??gimen Privisional(juvilado,cesante).");
        list.add("Fuera de Planilla");
        return list;
    }

    @Override
    public List<String> List_Nom_AFP() {
        List<String> list = new ArrayList<String>();
        list.add("Integra");
        list.add("Prima");
        list.add("Profuturo");  
        list.add("Horizonte");
        list.add("Habitat");
        list.add("Ninguno");
        return list;
    }

    @Override
    public List<String> List_Nivel_Educativo() {
        List<String> list = new ArrayList<String>();
        list.add("Ninguno");
        list.add("Primaria Incompleta");
        list.add("Primaria Completa");
        list.add("Superior No Universitario Incompleto");
        list.add("Superior No Universitario Completo");
        list.add("Superior Universitario Incompleto");
        list.add("Superior Universitario Completo");
        list.add("Superior Post Grado Incompleto");
        list.add("Superior Post Grado Completo");
        return list;
    }

    @Override
    public List<String> List_Grado_Academico() {
        List<String> list = new ArrayList<String>();
        list.add("Ninguno");
        list.add("Bachiller");
        list.add("Magister");
        list.add("Doctor");
        return list;
    }

    @Override
    public List<Via> List_Dom_D1_Id() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_via order by co_via";
        List<Via> list = new ArrayList<Via>();
        try {
            ResultSet rs = this.conn.query(sql);
            while (rs.next()) {
                Via am = new Via();
                am.setCo_via(rs.getString("co_via"));
                am.setDe_via(rs.getString("de_via"));
                am.setId_via(rs.getString("id_via"));
                list.add(am);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;

    }

    @Override
    public List<String> List_Dom_D3_Id() {
        List<String> list = new ArrayList<String>();
        list.add("Numero");
        list.add("Lote");
        list.add("S/N");
        list.add("Km");
        list.add("Block");
        list.add("Etapa");
        list.add("Departamento");
        list.add("Interior");
        return list;
    }

    @Override
    public List<Zona> List_Dom_D5_Id() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_zona";
        List<Zona> list = new ArrayList<Zona>();
        try {
            ResultSet rs = this.conn.query(sql);
            while (rs.next()) {
                Zona am = new Zona();
                am.setId_zona(rs.getString("id_zona"));
                am.setDe_zona(rs.getString("de_zona"));
                am.setCo_zona(rs.getString("co_zona"));
                list.add(am);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;

    }

    @Override
    public List<String> List_Jefe() {
        List<String> list = new ArrayList<String>();
        list.add("No es Jefe");
        list.add("Jefe de Secci??n");
        list.add("Jefe de Area");
        list.add("Jefe de Departamento");
        list.add("Jefe de Direcci??n");
        return list;
    }

    @Override
    public Map<String,String> list_Condicion_contrato() {
        Map<String,String> list = new HashMap();
        list.put("REQ-0001","Contrato Personal - Tiempo Completo");
        list.put("REQ-0002","Contrato Personal - Medio Tiempo (24 Horas Semanal)");
        list.put("REQ-0003","Contrato Personal - Tiempo Parcial");
        list.put("REQ-0005","Contrato Personal - Extranjero");
        list.put("REQ-0007","Practicas Preprofesionales (Tope 6hrs diarias / 30 hrs  semanales.)");
        list.put("REQ-0008","Practicas Profesionales");
        list.put("REQ-0009","Convenio Laboral Juvenil (Hasta 22 a??os, no matriculados)");
        list.put("REQ-0010","Locacion de Servicios");
        list.put("REQ-0011","No domiciliado (Expositores Extranjeros)");
        list.put("REQ-0018","Contrato Personal - Tiempo Parcial (Trabajador Docente)");
        list.put("REQ-0019","Contratacion Casos Especiales ");
        list.put("REQ-0020","Contrato Personal > MFL - Medio Tiempo");
        list.put("REQ-0021","Contrato Personal > MFL - Tiempo Completo");
        list.put("REQ-0022","Empleado");
        list.put("REQ-0023","Misionero");
        return list;
    }
    
    @Override
    public List<Map<String,String>> listCondicionContratoJson(){
        List<Map<String,String>> list=new ArrayList();
        Map<String,String> mp = new HashMap();
        mp.put("nombre","Contrato Personal - Tiempo Completo");
        mp.put("id","REQ-0001");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contrato Personal - Medio Tiempo (24 Horas Semanal)");
        mp.put("id","REQ-0002");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contrato Personal - Tiempo Parcial");
        mp.put("id","REQ-0003");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contrato Personal - Extranjero");
        mp.put("id","REQ-0005");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Practicas Preprofesionales (Tope 6hrs diarias / 30 hrs  semanales.)");
        mp.put("id","REQ-0007");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Practicas Profesionales");
        mp.put("id","REQ-0008");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Convenio Laboral Juvenil (Hasta 22 a??os, no matriculados)");
        mp.put("id","REQ-0009");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Locacion de Servicios");
        mp.put("id","REQ-0010");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","No domiciliado (Expositores Extranjeros)");
        mp.put("id","REQ-0011");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contrato Personal - Tiempo Parcial (Trabajador Docente)");
        mp.put("id","REQ-0018");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contratacion Casos Especiales");
        mp.put("id","REQ-0019");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contrato Personal > MFL - Medio Tiempo");
        mp.put("id","REQ-0020");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Contrato Personal > MFL - Tiempo Completo");
        mp.put("id","REQ-0021");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Empleado");
        mp.put("id","REQ-0022");
        list.add(mp);
        mp = new HashMap();
        mp.put("nombre","Misionero");
        mp.put("id","REQ-0023");
        list.add(mp);
        
        return list;
    }

    @Override
    public List<Tipo_Contrato> List_tipo_contrato() {
        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_tipo_contrato";
        List<Tipo_Contrato> list = new ArrayList<Tipo_Contrato>();
        try {
            ResultSet rs = this.conn.query(sql);
            while (rs.next()) {
                Tipo_Contrato tc = new Tipo_Contrato();
                tc.setId_tipo_contrato(rs.getString("id_tipo_contrato"));
                tc.setCo_tipo_contrato(rs.getString("co_tipo_contrato"));
                tc.setDe_ti_contrato(rs.getString("de_ti_contrato"));
                tc.setDe_abrev(rs.getString("de_abrev"));
                list.add(tc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error :" + e.getMessage());
        } finally {
            try {
                this.conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }

    @Override
    public List<String> List_Situacion_Actual() {
        List<String> list = new ArrayList<String>();
        list.add("Activo");
        list.add("Termin?? Contrato");
        list.add("Renuncia Voluntaria");
        list.add("Traslado a otra Filial/Instituci??n");
        list.add("No Inicio Relacion Laboral");
        list.add("Cambio de Modalidad Contractual");
        list.add("Abandono de Trabajo");
        return list;
    }

    public String[][] List_H() {
        String[][] l = new String[7][2];
        l[0][0] = "lun";
        l[0][1] = "Lunes";

        l[1][0] = "mar";
        l[1][1] = "Martes";

        l[2][0] = "mie";
        l[2][1] = "Miercoles";

        l[3][0] = "jue";
        l[3][1] = "Jueves";

        l[4][0] = "vie";
        l[4][1] = "Viernes";

        l[5][0] = "sab";
        l[5][1] = "Sabado";

        l[6][0] = "dom";
        l[6][1] = "Domingo";

        return l;
    }

    @Override
    public List<Situacion_Educativa> List_Situacion_Educativa() {

        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
        String sql = "select * from rhtx_situacion_educativa ";
        List<Situacion_Educativa> list = new ArrayList<Situacion_Educativa>();
        try {
            ResultSet rs = this.conn.query(sql);

            while (rs.next()) {
                Situacion_Educativa n = new Situacion_Educativa();
                n.setId_situacion_educativa(rs.getString("id_situacion_educativa"));
                n.setNo_s_educativa(rs.getString("no_s_educativa"));
                n.setEs_s_educativa(rs.getString("es_s_educativa"));
                list.add(n);
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;

    }

    @Override
    public List<String> lista_a??os() {

        this.conn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);

        List<String> list = new ArrayList<String>();
        try {

            for (int i = 0; i < 50; i++) {
                String sql = "select  to_number(to_char(sysdate,'yyyy')) - " + i + " from dual";
                ResultSet rs = this.conn.query(sql);
                rs.next();
                list.add(rs.getString(1).trim());
            }
        } catch (SQLException e) {
        } finally {
            this.conn.close();
        }
        return list;

    }

}
