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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.edu.upeu.application.dao_imp.InterfaceReporte_HistorialDAO;
import pe.edu.upeu.application.factory.ConexionBD;
import pe.edu.upeu.application.factory.FactoryConnectionDB;

/**
 *
 * @author Andres
 */
public class Reporte_HistorialDAO implements InterfaceReporte_HistorialDAO {

    ConexionBD cnn;

    @Override
    public List<Map<String, ?>> Listar_Tra_Fecha(String FE_INICIO, String FE_FIN) {
        String adday = "+0";
        List<Map<String, ?>> Lista = new ArrayList<>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            if (FE_INICIO.equals(FE_FIN)) {
                adday = "+1";
            }
            String sql = "SELECT ID_TRABAJADOR, NO_TRABAJADOR, AP_PATERNO, AP_MATERNO, COUNT(*) CANT_M\n"
                    + "FROM RHTH_MODIF_TRABAJADOR\n"
                    + "WHERE FE_MODIF >= TO_DATE('" + FE_INICIO + "') AND FE_MODIF <= TO_DATE('" + FE_FIN + "') " + adday + " \n"
                    + "GROUP BY ID_TRABAJADOR,NO_TRABAJADOR, AP_PATERNO, AP_MATERNO";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<>();
                rec.put("id_tra", rs.getString("ID_TRABAJADOR"));
                rec.put("no_tra", rs.getString("NO_TRABAJADOR"));
                rec.put("ap_pat", rs.getString("AP_PATERNO"));
                rec.put("ap_mat", rs.getString("AP_MATERNO"));
                rec.put("cant_mod", rs.getString("CANT_M"));
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
    public List<Map<String, ?>> Listar_His_Estado_Civil(String FE_INICIO, String FE_FIN) {
        String adday = "+0";
        List<Map<String, ?>> Lista = new ArrayList<>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            if (FE_INICIO.equals(FE_FIN)) {
                adday = "+1";
            }

            String sql = " select * from RHVD_HISTORIAL_ES_CIVIL  h1 where h1.FE_MODIFICACION = (select  max(h2.FE_MODIFICACION) from RHVD_HISTORIAL_ES_CIVIL h2 where h1.ID_TRABAJADOR = h2.ID_TRABAJADOR )  and h1.FE_MODIFICACION >= TO_CHAR('"+FE_INICIO+"') AND h1.FE_MODIFICACION <= TO_CHAR('"+FE_FIN+"')  ";

            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<>();
                rec.put("id_tra", rs.getString("ID_TRABAJADOR"));
                rec.put("no_tra", rs.getString("NO_TRABAJADOR"));
                rec.put("ap_pat", rs.getString("AP_PATERNO"));
                rec.put("ap_mat", rs.getString("AP_MATERNO"));
                rec.put("es_civil_a", rs.getString("ES_CIVIL"));
                rec.put("es_civil_p", rs.getString("LI_ESTADO_CIVIL"));
                rec.put("no_usuario", rs.getString("NO_USUARIO"));
                rec.put("fe_modi", rs.getString("FE_MODIFICACION"));
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
    public List<Map<String, ?>> Listar_Mod_Tra(String ID_TRABAJADOR) {
        List<Map<String, ?>> Lista = new ArrayList<>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT ID_TRABAJADOR,TO_CHAR(FE_MODIF,'DD/MM/YYYY HH:MI:SS')FE_MODIFI, US_MODIF, IP_USUARIO\n"
                    + "FROM RHTH_MODIF_TRABAJADOR\n"
                    + "WHERE ID_TRABAJADOR='" + ID_TRABAJADOR + "' AND FE_MODIF IS NOT NULL\n"
                    + "ORDER BY FE_MODIFi desc";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<>();
                rec.put("id_tra", rs.getString(1));
                rec.put("fe_mod", rs.getString(2));
                rec.put("us_mod", rs.getString(3));

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
    public List<Map<String, ?>> List_historial_modf_hijo(String FE_INICIO, String FE_FIN, String tipo) {
        List<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT AP_MATERNO_T, AP_PATERNO_T, NO_TRABAJADOR_T, ID_DATOS_HIJOS_TRABAJADOR, ID_TRABAJADOR, AP_PATERNO, AP_MATERNO, NO_HIJO_TRABAJADOR, FE_NACIMIENTO, NO_ES_SEXO, ES_SEXO, ES_TIPO_DOC, NU_DOC, ES_PRESENTA_DOCUMENTO, ES_INSCRIPCION_VIG_ESSALUD, NO_ESSALUD, ES_ESTUDIO_NIV_SUPERIOR, NO_ESTUDIO_SUPERIOR, US_CREACION, FE_CREACION, US_MODIF, FE_MODIF, IP_USUARIO, ES_DATOS_HIJO_TRABAJADOR, SEMESTRE, ESTADO_REGISTRO, to_char(FE_FILTRO_TODO,'dd/mm/yyyy hh:mi:ss') as FE_FILTRO_TODO, NO_USUARIO_CREACION, NO_USUARIO_MODIF, DE_TIP_DOC,ES_PROCESADO FROM RHVD_HISTORIAL_MOD_HIJO WHERE  us_creacion is not null ";
            if (tipo.equals("2")) {
                sql += " and  ESTADO_REGISTRO ='0' and fe_creacion BETWEEN TO_DATE('" + FE_INICIO.trim() + "') AND TO_DATE('" + FE_FIN.trim() + "')  ";
            } else if (tipo.equals("3")) {
                sql += " and  ESTADO_REGISTRO ='1'  and fe_modif BETWEEN TO_DATE('" + FE_INICIO.trim() + "') AND TO_DATE('" + FE_FIN.trim() + "') ";
            } else if (tipo.equals("1")) {
                sql += " and FE_FILTRO_TODO BETWEEN TO_DATE('" + FE_INICIO.trim() + "') AND TO_DATE('" + FE_FIN.trim() + "') ";
            }
            sql += " ORDER BY FE_FILTRO_TODO";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("id_datos_hijos_trabajador"));
                rec.put("idtr", rs.getString("id_trabajador"));
                rec.put("no_tra", rs.getString("NO_TRABAJADOR_T"));
                rec.put("ap_pat_t", rs.getString("AP_PATERNO_T"));
                rec.put("ap_mat_t", rs.getString("AP_MATERNO_T"));
                rec.put("no_hijo", rs.getString("NO_HIJO_TRABAJADOR"));
                rec.put("ap_pat_h", rs.getString("AP_PATERNO"));
                rec.put("ap_mat_h", rs.getString("AP_MATERNO"));
                rec.put("estado_filtro", rs.getString("ESTADO_REGISTRO"));
                rec.put("fecha", rs.getString("FE_FILTRO_TODO"));
                rec.put("procesado", rs.getString("ES_PROCESADO"));
                lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return lista;

    }

    @Override
    public List<Map<String, ?>> list_fecha_modif(String Hijo) {
        List<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "select  to_char(FE_FILTRO_TODO,'dd/mm/yyyy hh:mi:ss') as FE_FILTRO_TODO  from RHVD_HISTORIAL_MOD_HIJO where ID_DATOS_HIJOS_TRABAJADOR='" + Hijo + "'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("FE_FILTRO_TODO"));
                rec.put("fecha", rs.getString("FE_FILTRO_TODO"));
                rec.put("nombre", rs.getString("FE_FILTRO_TODO"));
                lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    @Override
    public List<Map<String, ?>> list_hijo_trabajdor(String id_tr) {
        List<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "  SELECT * FROM RHTD_DATOS_HIJO_TRABAJADOR WHERE ID_TRABAJADOR='" + id_tr.trim() + "'";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("id", rs.getString("id_datos_hijos_trabajador"));
                rec.put("nombre", rs.getString("ap_paterno") + " " + rs.getString("ap_materno") + " " + rs.getString("no_hijo_trabajador"));
                lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    @Override
    public List<Map<String, ?>> Lista_campos_modif(String fecha1, String fecha2, String id) {

        List<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT * FROM RHVD_HISTORIAL_MOD_HIJO where ID_DATOS_HIJOS_TRABAJADOR='" + id + "' and to_char(FE_FILTRO_TODO,'dd/mm/yyyy hh:mi:ss') ='" + fecha1.trim() + "' or to_char(FE_FILTRO_TODO,'dd/mm/yyyy hh:mi:ss') ='" + fecha2.trim() + "' ";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("ap_p", rs.getString("ap_paterno"));
                rec.put("ap_m", rs.getString("ap_materno"));
                rec.put("no_hijo", rs.getString("no_hijo_trabajador"));
                rec.put("fe_nac", rs.getString("fe_nacimiento"));
                rec.put("sexo", rs.getString("ES_SEXO"));
                rec.put("ti_doc", rs.getString("ES_TIPO_DOC"));
                rec.put("nu_doc", rs.getString("NU_DOC"));
                rec.put("essalud", rs.getString("ES_INSCRIPCION_VIG_ESSALUD"));

                rec.put("no_essalud", rs.getString("NO_ESSALUD"));
                rec.put("no_niv_sup", rs.getString("NO_ESTUDIO_SUPERIOR"));
                rec.put("no_sexo", rs.getString("NO_ES_SEXO"));
                rec.put("de_tip_doc", rs.getString("DE_TIP_DOC"));

                rec.put("estudios", rs.getString("ES_ESTUDIO_NIV_SUPERIOR"));
                rec.put("us_creacion", rs.getString("NO_USUARIO_CREACION"));
                rec.put("creacion", rs.getString("FE_CREACION"));
                rec.put("us_modif", rs.getString("no_usuario_modif"));
                rec.put("modif", rs.getString("FE_MODIF"));
                rec.put("ip_usuario", rs.getString("IP_USUARIO"));
                lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    @Override
    public List<Map<String, ?>> Listar_hist_fecha(String FE_MODIF, String idtra) {
        List<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT *\n"
                    + "FROM (\n"
                    + "      SELECT ROWNUM R,\n"
                    + "        T.ID_TRABAJADOR, T.AP_PATERNO, T.AP_MATERNO, T.NO_TRABAJADOR, T.TI_DOC, T.NU_DOC, T.\n"
                    + "        ES_CIVIL, TO_CHAR(T.FE_NAC,'DD/MM/YYYY HH:MI:SS')AS FE_NAC , T.ID_NACIONALIDAD,\n"
                    + "        T.ID_DEPARTAMENTO, T.ID_PROVINCIA, T.ID_DISTRITO, T.TE_TRABAJADOR,\n"
                    + "        T.CL_TRA, T.DI_CORREO_PERSONAL, T.DI_CORREO_INST, T.CO_SISTEMA_PENSIONARIO,\n"
                    + "        T.ID_SITUACION_EDUCATIVA, T.LI_REG_INST_EDUCATIVA, T.ES_INST_EDUC_PERU,\n"
                    + "        T.ID_UNIVERSIDAD_CARRERA, T.DE_ANNO_EGRESO, T.CM_OTROS_ESTUDIOS,\n"
                    + "        T.ES_SEXO, T.LI_GRUPO_SANGUINEO, T.DE_REFERENCIA, T.LI_RELIGION, T.NO_IGLESIA, \n"
                    + "        T.DE_CARGO, T.LI_AUTORIDAD, T.NO_AP_AUTORIDAD, T.CL_AUTORIDAD, T.ID_NO_AFP, \n"
                    + "        T.ES_AFILIADO_ESSALUD, T.LI_TIPO_TRABAJADOR, T.CA_TIPO_HORA_PAGO_REFEERENCIAL, \n"
                    + "        T.ES_FACTOR_RH, T.LI_DI_DOM_A_D1, T.DI_DOM_A_D2, T.LI_DI_DOM_A_D3, T.DI_DOM_A_D4, \n"
                    + "        T.LI_DI_DOM_A_D5, T.DI_DOM_A_D6, T.DI_DOM_A_REF, T.ID_DI_DOM_A_DISTRITO,\n"
                    + "        T.LI_DI_DOM_LEG_D1, T.DI_DOM_LEG_D2, T.LI_DI_DOM_LEG_D3, T.DI_DOM_LEG_D4, \n"
                    + "        T.LI_DI_DOM_LEG_D5, T.DI_DOM_LEG_D6, T.ID_DI_DOM_LEG_DISTRITO, T.CA_ING_QTA_CAT_EMPRESA, \n"
                    + "        T.CA_ING_QTA_CAT_RUC, T.CA_ING_QTA_CAT_OTRAS_EMPRESAS, T.CM_OBSERVACIONES,\n"
                    + "        T.US_CREACION, TO_CHAR(T.FE_CREACION,'DD/MM/YYYY HH:MI:SS')AS FE_CREACION,\n"
                    + "        T.US_MODIF, TO_CHAR(T.FE_MODIF,'DD/MM/YYYY HH:MI:SS')AS FE_MODIF, T.IP_USUARIO, T.AP_NOMBRES_PADRE, \n"
                    + "        T.AP_NOMBRES_MADRE, T.ES_TRABAJA_UPEU_C, T.AP_NOMBRES_C, TO_CHAR(T.FE_NAC_C,'DD/MM/YYYY HH:MI:SS')AS FE_NAC_C,\n"
                    + "        T.ID_TIPO_DOC_C, T.NU_DOC_C, T.LI_INSCRIPCION_VIG_ESSALUD_C, T.ID_CONYUGUE, T.CO_UNIVERSITARIO, T.SEMESTRE\n"
                    + "      FROM RHTH_MODIF_TRABAJADOR T\n"
                    + "      WHERE T.ID_TRABAJADOR='" + idtra + "'\n"
                    + "      AND (TO_CHAR(FE_MODIF,'DD/MM/YYYY HH:MI:SS')\n"
                    + "      =TO_CHAR(TO_DATE('" + FE_MODIF + "','DD/MM/YYYY HH:MI:SS'),'DD/MM/YYYY HH:MI:SS') )\n"
                    + "      )\n"
                    + "UNPIVOT INCLUDE NULLS(\n"
                    + "        DETALLE FOR COLUMNA IN\n"
                    + "        (ID_TRABAJADOR,AP_PATERNO,AP_MATERNO,NO_TRABAJADOR,TI_DOC,NU_DOC,ES_CIVIL,FE_NAC,ID_NACIONALIDAD,\n"
                    + "        ID_DEPARTAMENTO,ID_PROVINCIA,ID_DISTRITO,TE_TRABAJADOR,CL_TRA,DI_CORREO_PERSONAL,DI_CORREO_INST,\n"
                    + "        CO_SISTEMA_PENSIONARIO,ID_SITUACION_EDUCATIVA,LI_REG_INST_EDUCATIVA,ES_INST_EDUC_PERU,\n"
                    + "        ID_UNIVERSIDAD_CARRERA,DE_ANNO_EGRESO,CM_OTROS_ESTUDIOS,ES_SEXO,LI_GRUPO_SANGUINEO,DE_REFERENCIA,\n"
                    + "        lI_RELIGION,NO_IGLESIA,DE_CARGO,LI_AUTORIDAD,NO_AP_AUTORIDAD,CL_AUTORIDAD,ID_NO_AFP,ES_AFILIADO_ESSALUD,\n"
                    + "        LI_TIPO_TRABAJADOR,CA_TIPO_HORA_PAGO_REFEERENCIAL,ES_FACTOR_RH,LI_DI_DOM_A_D1,DI_DOM_A_D2,\n"
                    + "        LI_DI_DOM_A_D3,DI_DOM_A_D4,LI_DI_DOM_A_D5,DI_DOM_A_D6,DI_DOM_A_REF,ID_DI_DOM_A_DISTRITO,\n"
                    + "        LI_DI_DOM_LEG_D1,DI_DOM_LEG_D2,LI_DI_DOM_LEG_D3,DI_DOM_LEG_D4,LI_DI_DOM_LEG_D5,DI_DOM_LEG_D6,\n"
                    + "        ID_DI_DOM_LEG_DISTRITO,CA_ING_QTA_CAT_EMPRESA,CA_ING_QTA_CAT_RUC,CA_ING_QTA_CAT_OTRAS_EMPRESAS,\n"
                    + "        CM_OBSERVACIONES,US_CREACION,FE_CREACION,US_MODIF,FE_MODIF,IP_USUARIO,AP_NOMBRES_PADRE,\n"
                    + "        AP_NOMBRES_MADRE,ES_TRABAJA_UPEU_C,AP_NOMBRES_C,FE_NAC_C,ID_TIPO_DOC_C,NU_DOC_C,\n"
                    + "        LI_INSCRIPCION_VIG_ESSALUD_C,ID_CONYUGUE,CO_UNIVERSITARIO,SEMESTRE)\n"
                    + "        )";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("col", rs.getString("columna"));
                rec.put("det", rs.getString("detalle"));
                lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    @Override
    public List<Map<String, ?>> Listar_dat_actual(String ID_TRABAJADOR) {
        List<Map<String, ?>> lista = new ArrayList<Map<String, ?>>();
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            String sql = "SELECT *\n"
                    + "FROM (\n"
                    + "      SELECT ROWNUM R,\n"
                    + "        T.ID_TRABAJADOR, T.AP_PATERNO, T.AP_MATERNO, T.NO_TRABAJADOR, T.TI_DOC, T.NU_DOC, T.\n"
                    + "        ES_CIVIL, TO_CHAR(T.FE_NAC,'DD/MM/YYYY HH:MI:SS')AS FE_NAC , T.ID_NACIONALIDAD,\n"
                    + "        T.ID_DEPARTAMENTO, T.ID_PROVINCIA, T.ID_DISTRITO, T.TE_TRABAJADOR,\n"
                    + "        T.CL_TRA, T.DI_CORREO_PERSONAL, T.DI_CORREO_INST, T.CO_SISTEMA_PENSIONARIO,\n"
                    + "        T.ID_SITUACION_EDUCATIVA, T.LI_REG_INST_EDUCATIVA, T.ES_INST_EDUC_PERU,\n"
                    + "        T.ID_UNIVERSIDAD_CARRERA, T.DE_ANNO_EGRESO, T.CM_OTROS_ESTUDIOS,\n"
                    + "        T.ES_SEXO, T.LI_GRUPO_SANGUINEO, T.DE_REFERENCIA, T.LI_RELIGION, T.NO_IGLESIA, \n"
                    + "        T.DE_CARGO, T.LI_AUTORIDAD, T.NO_AP_AUTORIDAD, T.CL_AUTORIDAD, T.ID_NO_AFP, \n"
                    + "        T.ES_AFILIADO_ESSALUD, T.LI_TIPO_TRABAJADOR, T.CA_TIPO_HORA_PAGO_REFEERENCIAL, \n"
                    + "        T.ES_FACTOR_RH, T.LI_DI_DOM_A_D1, T.DI_DOM_A_D2, T.LI_DI_DOM_A_D3, T.DI_DOM_A_D4, \n"
                    + "        T.LI_DI_DOM_A_D5, T.DI_DOM_A_D6, T.DI_DOM_A_REF, T.ID_DI_DOM_A_DISTRITO,\n"
                    + "        T.LI_DI_DOM_LEG_D1, T.DI_DOM_LEG_D2, T.LI_DI_DOM_LEG_D3, T.DI_DOM_LEG_D4, \n"
                    + "        T.LI_DI_DOM_LEG_D5, T.DI_DOM_LEG_D6, T.ID_DI_DOM_LEG_DISTRITO, T.CA_ING_QTA_CAT_EMPRESA, \n"
                    + "        T.CA_ING_QTA_CAT_RUC, T.CA_ING_QTA_CAT_OTRAS_EMPRESAS, T.CM_OBSERVACIONES,\n"
                    + "        T.US_CREACION, TO_CHAR(T.FE_CREACION,'DD/MM/YYYY HH:MI:SS')AS FE_CREACION,\n"
                    + "        T.US_MODIF, TO_CHAR(T.FE_MODIF,'DD/MM/YYYY HH:MI:SS')AS FE_MODIF, T.IP_USUARIO, T.AP_NOMBRES_PADRE, \n"
                    + "        T.AP_NOMBRES_MADRE, T.ES_TRABAJA_UPEU_C, T.AP_NOMBRES_C, TO_CHAR(T.FE_NAC_C,'DD/MM/YYYY HH:MI:SS')AS FE_NAC_C,\n"
                    + "        T.ID_TIPO_DOC_C, T.NU_DOC_C, T.LI_INSCRIPCION_VIG_ESSALUD_C, T.ID_CONYUGUE, T.CO_UNIVERSITARIO, T.SEMESTRE\n"
                    + "      FROM RHTM_TRABAJADOR T\n"
                    + "      WHERE T.ID_TRABAJADOR='" + ID_TRABAJADOR + "'\n"
                    + "      )\n"
                    + "UNPIVOT INCLUDE NULLS(\n"
                    + "        DETALLE FOR COLUMNA IN\n"
                    + "        (ID_TRABAJADOR,AP_PATERNO,AP_MATERNO,NO_TRABAJADOR,TI_DOC,NU_DOC,ES_CIVIL,FE_NAC,ID_NACIONALIDAD,\n"
                    + "        ID_DEPARTAMENTO,ID_PROVINCIA,ID_DISTRITO,TE_TRABAJADOR,CL_TRA,DI_CORREO_PERSONAL,DI_CORREO_INST,\n"
                    + "        CO_SISTEMA_PENSIONARIO,ID_SITUACION_EDUCATIVA,LI_REG_INST_EDUCATIVA,ES_INST_EDUC_PERU,\n"
                    + "        ID_UNIVERSIDAD_CARRERA,DE_ANNO_EGRESO,CM_OTROS_ESTUDIOS,ES_SEXO,LI_GRUPO_SANGUINEO,DE_REFERENCIA,\n"
                    + "        lI_RELIGION,NO_IGLESIA,DE_CARGO,LI_AUTORIDAD,NO_AP_AUTORIDAD,CL_AUTORIDAD,ID_NO_AFP,ES_AFILIADO_ESSALUD,\n"
                    + "        LI_TIPO_TRABAJADOR,CA_TIPO_HORA_PAGO_REFEERENCIAL,ES_FACTOR_RH,LI_DI_DOM_A_D1,DI_DOM_A_D2,\n"
                    + "        LI_DI_DOM_A_D3,DI_DOM_A_D4,LI_DI_DOM_A_D5,DI_DOM_A_D6,DI_DOM_A_REF,ID_DI_DOM_A_DISTRITO,\n"
                    + "        LI_DI_DOM_LEG_D1,DI_DOM_LEG_D2,LI_DI_DOM_LEG_D3,DI_DOM_LEG_D4,LI_DI_DOM_LEG_D5,DI_DOM_LEG_D6,\n"
                    + "        ID_DI_DOM_LEG_DISTRITO,CA_ING_QTA_CAT_EMPRESA,CA_ING_QTA_CAT_RUC,CA_ING_QTA_CAT_OTRAS_EMPRESAS,\n"
                    + "        CM_OBSERVACIONES,US_CREACION,FE_CREACION,US_MODIF,FE_MODIF,IP_USUARIO,AP_NOMBRES_PADRE,\n"
                    + "        AP_NOMBRES_MADRE,ES_TRABAJA_UPEU_C,AP_NOMBRES_C,FE_NAC_C,ID_TIPO_DOC_C,NU_DOC_C,\n"
                    + "        LI_INSCRIPCION_VIG_ESSALUD_C,ID_CONYUGUE,CO_UNIVERSITARIO,SEMESTRE)\n"
                    + "        )";
            ResultSet rs = this.cnn.query(sql);
            while (rs.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                rec.put("col", rs.getString("columna"));
                rec.put("det", rs.getString("detalle"));
                lista.add(rec);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
        return lista;
    }

    @Override
    public String[] decode(String t) {
        String x[] = new String[3];
        int cont = 0;
        String b[] = t.split("\\*");
        for (String a : b) {
            if (!a.equals("")) {
                x[cont] = a;
                cont++;
            }
        }
        return x;
    }

    @Override
    public void Procesar_historial_hijo(String id_hijo, String es_fecha, String fecha) {
        try {
            this.cnn = FactoryConnectionDB.open(FactoryConnectionDB.ORACLE);
            CallableStatement cst = this.cnn.conex.prepareCall("{CALL rhsp_procesar_historial_hijo(?,?,?)}");
            cst.setString(1, id_hijo);
            cst.setString(2, es_fecha);
            cst.setString(3, fecha);
            cst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        } finally {
            try {
                this.cnn.close();
            } catch (Exception e) {
            }
        }
    }

}