/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.app.model;

/**
 *
 * @author Alfa.sistemas
 */
public class V_Trabajador {
    private String id_trabajador;
private String ap_paterno;
private String ap_materno;
private String no_trabajador;
private String ti_doc;
private String nu_doc;
private String es_civil;
private String fe_nac;
private String no_nacionalidad;
private String no_departamento;
private String no_provincia;
private String no_distrito;
private String te_trabajador;
private String cl_tra;
private String di_correo_personal;
private String di_correo_inst;
private String co_sistema_pensionario;
private String li_nivel_educativo;
private String li_grado_academico;
private String li_titulo_profesional;
private String no_carrera;
private String no_universidad;
private String cm_otros_estudios;
private String es_sexo;
private String li_grupo_sanguineo;
private String de_referencia;
private String li_religion;
private String no_iglesia;
private String de_cargo;
private String li_autoridad;
private String no_ap_autoridad;
private String cl_autoridad;
private String id_no_afp;
private String es_afiliado_essalud;
private String li_tipo_trabajador;
private String ca_tipo_hora_pago_refeerencial;
private String es_factor_rh;
private String li_di_dom_a_d1;
private String di_dom_a_d2;
private String li_di_dom_a_d3;
private String di_dom_a_d4;
private String li_di_dom_a_d5;

    public V_Trabajador(String id_trabajador, String ap_paterno, String ap_materno, String no_trabajador, String ti_doc, String nu_doc, String es_civil, String fe_nac, String no_nacionalidad, String no_departamento, String no_provincia, String no_distrito, String te_trabajador, String cl_tra, String di_correo_personal, String di_correo_inst, String co_sistema_pensionario, String li_nivel_educativo, String li_grado_academico, String li_titulo_profesional, String no_carrera, String no_universidad, String cm_otros_estudios, String es_sexo, String li_grupo_sanguineo, String de_referencia, String li_religion, String no_iglesia, String de_cargo, String li_autoridad, String no_ap_autoridad, String cl_autoridad, String id_no_afp, String es_afiliado_essalud, String li_tipo_trabajador, String ca_tipo_hora_pago_refeerencial, String es_factor_rh, String li_di_dom_a_d1, String di_dom_a_d2, String li_di_dom_a_d3, String di_dom_a_d4, String li_di_dom_a_d5, String di_dom_a_d6, String di_dom_a_ref, String di_dom_a_distrito, String li_di_dom_leg_d1, String di_dom_leg_d2, String li_di_dom_leg_d3, String di_dom_leg_d4, String li_di_dom_leg_d5, String di_dom_leg_d6, String di_dom_leg_distrito, String ca_ing_qta_cat_empresa, String ca_ing_qta_cat_ruc, String ca_ing_qta_cat_otras_empresas, String cm_observaciones, String us_creacion, String fe_creacion, String us_modif, String fe_modif, String ip_usuario) {
        this.id_trabajador = id_trabajador;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.no_trabajador = no_trabajador;
        this.ti_doc = ti_doc;
        this.nu_doc = nu_doc;
        this.es_civil = es_civil;
        this.fe_nac = fe_nac;
        this.no_nacionalidad = no_nacionalidad;
        this.no_departamento = no_departamento;
        this.no_provincia = no_provincia;
        this.no_distrito = no_distrito;
        this.te_trabajador = te_trabajador;
        this.cl_tra = cl_tra;
        this.di_correo_personal = di_correo_personal;
        this.di_correo_inst = di_correo_inst;
        this.co_sistema_pensionario = co_sistema_pensionario;
        this.li_nivel_educativo = li_nivel_educativo;
        this.li_grado_academico = li_grado_academico;
        this.li_titulo_profesional = li_titulo_profesional;
        this.no_carrera = no_carrera;
        this.no_universidad = no_universidad;
        this.cm_otros_estudios = cm_otros_estudios;
        this.es_sexo = es_sexo;
        this.li_grupo_sanguineo = li_grupo_sanguineo;
        this.de_referencia = de_referencia;
        this.li_religion = li_religion;
        this.no_iglesia = no_iglesia;
        this.de_cargo = de_cargo;
        this.li_autoridad = li_autoridad;
        this.no_ap_autoridad = no_ap_autoridad;
        this.cl_autoridad = cl_autoridad;
        this.id_no_afp = id_no_afp;
        this.es_afiliado_essalud = es_afiliado_essalud;
        this.li_tipo_trabajador = li_tipo_trabajador;
        this.ca_tipo_hora_pago_refeerencial = ca_tipo_hora_pago_refeerencial;
        this.es_factor_rh = es_factor_rh;
        this.li_di_dom_a_d1 = li_di_dom_a_d1;
        this.di_dom_a_d2 = di_dom_a_d2;
        this.li_di_dom_a_d3 = li_di_dom_a_d3;
        this.di_dom_a_d4 = di_dom_a_d4;
        this.li_di_dom_a_d5 = li_di_dom_a_d5;
        this.di_dom_a_d6 = di_dom_a_d6;
        this.di_dom_a_ref = di_dom_a_ref;
        this.di_dom_a_distrito = di_dom_a_distrito;
        this.li_di_dom_leg_d1 = li_di_dom_leg_d1;
        this.di_dom_leg_d2 = di_dom_leg_d2;
        this.li_di_dom_leg_d3 = li_di_dom_leg_d3;
        this.di_dom_leg_d4 = di_dom_leg_d4;
        this.li_di_dom_leg_d5 = li_di_dom_leg_d5;
        this.di_dom_leg_d6 = di_dom_leg_d6;
        this.di_dom_leg_distrito = di_dom_leg_distrito;
        this.ca_ing_qta_cat_empresa = ca_ing_qta_cat_empresa;
        this.ca_ing_qta_cat_ruc = ca_ing_qta_cat_ruc;
        this.ca_ing_qta_cat_otras_empresas = ca_ing_qta_cat_otras_empresas;
        this.cm_observaciones = cm_observaciones;
        this.us_creacion = us_creacion;
        this.fe_creacion = fe_creacion;
        this.us_modif = us_modif;
        this.fe_modif = fe_modif;
        this.ip_usuario = ip_usuario;
    }
    public V_Trabajador() {
        this.id_trabajador = "";
        this.ap_paterno = "";
        this.ap_materno = "";
        this.no_trabajador = "";
        this.ti_doc = "";
        this.nu_doc = "";
        this.es_civil = "";
        this.fe_nac = "";
        this.no_nacionalidad = "";
        this.no_departamento = "";
        this.no_provincia = "";
        this.no_distrito = "";
        this.te_trabajador = "";
        this.cl_tra = "";
        this.di_correo_personal = "";
        this.di_correo_inst = "";
        this.co_sistema_pensionario = "";
        this.li_nivel_educativo = "";
        this.li_grado_academico = "";
        this.li_titulo_profesional = "";
        this.no_carrera = "";
        this.no_universidad = "";
        this.cm_otros_estudios = "";
        this.es_sexo = "";
        this.li_grupo_sanguineo = "";
        this.de_referencia = "";
        this.li_religion = "";
        this.no_iglesia = "";
        this.de_cargo = "";
        this.li_autoridad = "";
        this.no_ap_autoridad ="";
        this.cl_autoridad = "";
        this.id_no_afp = "";
        this.es_afiliado_essalud = "";
        this.li_tipo_trabajador = "";
        this.ca_tipo_hora_pago_refeerencial = "";
        this.es_factor_rh = "";
        this.li_di_dom_a_d1 = "";
        this.di_dom_a_d2 = "";
        this.li_di_dom_a_d3 = "";
        this.di_dom_a_d4 = "";
        this.li_di_dom_a_d5 = "";
        this.di_dom_a_d6 = "";
        this.di_dom_a_ref = "";
        this.di_dom_a_distrito = "";
        this.li_di_dom_leg_d1 = "";
        this.di_dom_leg_d2 = "";
        this.li_di_dom_leg_d3 = "";
        this.di_dom_leg_d4 = "";
        this.li_di_dom_leg_d5 = "";
        this.di_dom_leg_d6 = "";
        this.di_dom_leg_distrito = "";
        this.ca_ing_qta_cat_empresa = "";
        this.ca_ing_qta_cat_ruc = "";
        this.ca_ing_qta_cat_otras_empresas = "";
        this.cm_observaciones = "";
        this.us_creacion = "";
        this.fe_creacion = "";
        this.us_modif = "";
        this.fe_modif = "";
        this.ip_usuario = "";
    }
private String di_dom_a_d6;
private String di_dom_a_ref;
private String di_dom_a_distrito;
private String li_di_dom_leg_d1;
private String di_dom_leg_d2;
private String li_di_dom_leg_d3;
private String di_dom_leg_d4;
private String li_di_dom_leg_d5;
private String di_dom_leg_d6;
private String di_dom_leg_distrito;
private String ca_ing_qta_cat_empresa;
private String ca_ing_qta_cat_ruc;
private String ca_ing_qta_cat_otras_empresas;
private String cm_observaciones;
private String us_creacion;
private String fe_creacion;
private String us_modif;
private String fe_modif;
private String ip_usuario;

    public String getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(String id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getNo_trabajador() {
        return no_trabajador;
    }

    public void setNo_trabajador(String no_trabajador) {
        this.no_trabajador = no_trabajador;
    }

    public String getTi_doc() {
        return ti_doc;
    }

    public void setTi_doc(String ti_doc) {
        this.ti_doc = ti_doc;
    }

    public String getNu_doc() {
        return nu_doc;
    }

    public void setNu_doc(String nu_doc) {
        this.nu_doc = nu_doc;
    }

    public String getEs_civil() {
        return es_civil;
    }

    public void setEs_civil(String es_civil) {
        this.es_civil = es_civil;
    }

    public String getFe_nac() {
        return fe_nac;
    }

    public void setFe_nac(String fe_nac) {
        this.fe_nac = fe_nac;
    }

    public String getNo_nacionalidad() {
        return no_nacionalidad;
    }

    public void setNo_nacionalidad(String no_nacionalidad) {
        this.no_nacionalidad = no_nacionalidad;
    }

    public String getNo_departamento() {
        return no_departamento;
    }

    public void setNo_departamento(String no_departamento) {
        this.no_departamento = no_departamento;
    }

    public String getNo_provincia() {
        return no_provincia;
    }

    public void setNo_provincia(String no_provincia) {
        this.no_provincia = no_provincia;
    }

    public String getNo_distrito() {
        return no_distrito;
    }

    public void setNo_distrito(String no_distrito) {
        this.no_distrito = no_distrito;
    }

    public String getTe_trabajador() {
        return te_trabajador;
    }

    public void setTe_trabajador(String te_trabajador) {
        this.te_trabajador = te_trabajador;
    }

    public String getCl_tra() {
        return cl_tra;
    }

    public void setCl_tra(String cl_tra) {
        this.cl_tra = cl_tra;
    }

    public String getDi_correo_personal() {
        return di_correo_personal;
    }

    public void setDi_correo_personal(String di_correo_personal) {
        this.di_correo_personal = di_correo_personal;
    }

    public String getDi_correo_inst() {
        return di_correo_inst;
    }

    public void setDi_correo_inst(String di_correo_inst) {
        this.di_correo_inst = di_correo_inst;
    }

    public String getCo_sistema_pensionario() {
        return co_sistema_pensionario;
    }

    public void setCo_sistema_pensionario(String co_sistema_pensionario) {
        this.co_sistema_pensionario = co_sistema_pensionario;
    }

    public String getLi_nivel_educativo() {
        return li_nivel_educativo;
    }

    public void setLi_nivel_educativo(String li_nivel_educativo) {
        this.li_nivel_educativo = li_nivel_educativo;
    }

    public String getLi_grado_academico() {
        return li_grado_academico;
    }

    public void setLi_grado_academico(String li_grado_academico) {
        this.li_grado_academico = li_grado_academico;
    }

    public String getLi_titulo_profesional() {
        return li_titulo_profesional;
    }

    public void setLi_titulo_profesional(String li_titulo_profesional) {
        this.li_titulo_profesional = li_titulo_profesional;
    }

    public String getNo_carrera() {
        return no_carrera;
    }

    public void setNo_carrera(String no_carrera) {
        this.no_carrera = no_carrera;
    }

    public String getNo_universidad() {
        return no_universidad;
    }

    public void setNo_universidad(String no_universidad) {
        this.no_universidad = no_universidad;
    }

    public String getCm_otros_estudios() {
        return cm_otros_estudios;
    }

    public void setCm_otros_estudios(String cm_otros_estudios) {
        this.cm_otros_estudios = cm_otros_estudios;
    }

    public String getEs_sexo() {
        return es_sexo;
    }

    public void setEs_sexo(String es_sexo) {
        this.es_sexo = es_sexo;
    }

    public String getLi_grupo_sanguineo() {
        return li_grupo_sanguineo;
    }

    public void setLi_grupo_sanguineo(String li_grupo_sanguineo) {
        this.li_grupo_sanguineo = li_grupo_sanguineo;
    }

    public String getDe_referencia() {
        return de_referencia;
    }

    public void setDe_referencia(String de_referencia) {
        this.de_referencia = de_referencia;
    }

    public String getLi_religion() {
        return li_religion;
    }

    public void setLi_religion(String li_religion) {
        this.li_religion = li_religion;
    }

    public String getNo_iglesia() {
        return no_iglesia;
    }

    public void setNo_iglesia(String no_iglesia) {
        this.no_iglesia = no_iglesia;
    }

    public String getDe_cargo() {
        return de_cargo;
    }

    public void setDe_cargo(String de_cargo) {
        this.de_cargo = de_cargo;
    }

    public String getLi_autoridad() {
        return li_autoridad;
    }

    public void setLi_autoridad(String li_autoridad) {
        this.li_autoridad = li_autoridad;
    }

    public String getNo_ap_autoridad() {
        return no_ap_autoridad;
    }

    public void setNo_ap_autoridad(String no_ap_autoridad) {
        this.no_ap_autoridad = no_ap_autoridad;
    }

    public String getCl_autoridad() {
        return cl_autoridad;
    }

    public void setCl_autoridad(String cl_autoridad) {
        this.cl_autoridad = cl_autoridad;
    }

    public String getId_no_afp() {
        return id_no_afp;
    }

    public void setId_no_afp(String id_no_afp) {
        this.id_no_afp = id_no_afp;
    }

    public String getEs_afiliado_essalud() {
        return es_afiliado_essalud;
    }

    public void setEs_afiliado_essalud(String es_afiliado_essalud) {
        this.es_afiliado_essalud = es_afiliado_essalud;
    }

    public String getLi_tipo_trabajador() {
        return li_tipo_trabajador;
    }

    public void setLi_tipo_trabajador(String li_tipo_trabajador) {
        this.li_tipo_trabajador = li_tipo_trabajador;
    }

    public String getCa_tipo_hora_pago_refeerencial() {
        return ca_tipo_hora_pago_refeerencial;
    }

    public void setCa_tipo_hora_pago_refeerencial(String ca_tipo_hora_pago_refeerencial) {
        this.ca_tipo_hora_pago_refeerencial = ca_tipo_hora_pago_refeerencial;
    }

    public String getEs_factor_rh() {
        return es_factor_rh;
    }

    public void setEs_factor_rh(String es_factor_rh) {
        this.es_factor_rh = es_factor_rh;
    }

    public String getLi_di_dom_a_d1() {
        return li_di_dom_a_d1;
    }

    public void setLi_di_dom_a_d1(String li_di_dom_a_d1) {
        this.li_di_dom_a_d1 = li_di_dom_a_d1;
    }

    public String getDi_dom_a_d2() {
        return di_dom_a_d2;
    }

    public void setDi_dom_a_d2(String di_dom_a_d2) {
        this.di_dom_a_d2 = di_dom_a_d2;
    }

    public String getLi_di_dom_a_d3() {
        return li_di_dom_a_d3;
    }

    public void setLi_di_dom_a_d3(String li_di_dom_a_d3) {
        this.li_di_dom_a_d3 = li_di_dom_a_d3;
    }

    public String getDi_dom_a_d4() {
        return di_dom_a_d4;
    }

    public void setDi_dom_a_d4(String di_dom_a_d4) {
        this.di_dom_a_d4 = di_dom_a_d4;
    }

    public String getLi_di_dom_a_d5() {
        return li_di_dom_a_d5;
    }

    public void setLi_di_dom_a_d5(String li_di_dom_a_d5) {
        this.li_di_dom_a_d5 = li_di_dom_a_d5;
    }

    public String getDi_dom_a_d6() {
        return di_dom_a_d6;
    }

    public void setDi_dom_a_d6(String di_dom_a_d6) {
        this.di_dom_a_d6 = di_dom_a_d6;
    }

    public String getDi_dom_a_ref() {
        return di_dom_a_ref;
    }

    public void setDi_dom_a_ref(String di_dom_a_ref) {
        this.di_dom_a_ref = di_dom_a_ref;
    }

    public String getDi_dom_a_distrito() {
        return di_dom_a_distrito;
    }

    public void setDi_dom_a_distrito(String di_dom_a_distrito) {
        this.di_dom_a_distrito = di_dom_a_distrito;
    }

    public String getLi_di_dom_leg_d1() {
        return li_di_dom_leg_d1;
    }

    public void setLi_di_dom_leg_d1(String li_di_dom_leg_d1) {
        this.li_di_dom_leg_d1 = li_di_dom_leg_d1;
    }

    public String getDi_dom_leg_d2() {
        return di_dom_leg_d2;
    }

    public void setDi_dom_leg_d2(String di_dom_leg_d2) {
        this.di_dom_leg_d2 = di_dom_leg_d2;
    }

    public String getLi_di_dom_leg_d3() {
        return li_di_dom_leg_d3;
    }

    public void setLi_di_dom_leg_d3(String li_di_dom_leg_d3) {
        this.li_di_dom_leg_d3 = li_di_dom_leg_d3;
    }

    public String getDi_dom_leg_d4() {
        return di_dom_leg_d4;
    }

    public void setDi_dom_leg_d4(String di_dom_leg_d4) {
        this.di_dom_leg_d4 = di_dom_leg_d4;
    }

    public String getLi_di_dom_leg_d5() {
        return li_di_dom_leg_d5;
    }

    public void setLi_di_dom_leg_d5(String li_di_dom_leg_d5) {
        this.li_di_dom_leg_d5 = li_di_dom_leg_d5;
    }

    public String getDi_dom_leg_d6() {
        return di_dom_leg_d6;
    }

    public void setDi_dom_leg_d6(String di_dom_leg_d6) {
        this.di_dom_leg_d6 = di_dom_leg_d6;
    }

    public String getDi_dom_leg_distrito() {
        return di_dom_leg_distrito;
    }

    public void setDi_dom_leg_distrito(String di_dom_leg_distrito) {
        this.di_dom_leg_distrito = di_dom_leg_distrito;
    }

    public String getCa_ing_qta_cat_empresa() {
        return ca_ing_qta_cat_empresa;
    }

    public void setCa_ing_qta_cat_empresa(String ca_ing_qta_cat_empresa) {
        this.ca_ing_qta_cat_empresa = ca_ing_qta_cat_empresa;
    }

    public String getCa_ing_qta_cat_ruc() {
        return ca_ing_qta_cat_ruc;
    }

    public void setCa_ing_qta_cat_ruc(String ca_ing_qta_cat_ruc) {
        this.ca_ing_qta_cat_ruc = ca_ing_qta_cat_ruc;
    }

    public String getCa_ing_qta_cat_otras_empresas() {
        return ca_ing_qta_cat_otras_empresas;
    }

    public void setCa_ing_qta_cat_otras_empresas(String ca_ing_qta_cat_otras_empresas) {
        this.ca_ing_qta_cat_otras_empresas = ca_ing_qta_cat_otras_empresas;
    }

    public String getCm_observaciones() {
        return cm_observaciones;
    }

    public void setCm_observaciones(String cm_observaciones) {
        this.cm_observaciones = cm_observaciones;
    }

    public String getUs_creacion() {
        return us_creacion;
    }

    public void setUs_creacion(String us_creacion) {
        this.us_creacion = us_creacion;
    }

    public String getFe_creacion() {
        return fe_creacion;
    }

    public void setFe_creacion(String fe_creacion) {
        this.fe_creacion = fe_creacion;
    }

    public String getUs_modif() {
        return us_modif;
    }

    public void setUs_modif(String us_modif) {
        this.us_modif = us_modif;
    }

    public String getFe_modif() {
        return fe_modif;
    }

    public void setFe_modif(String fe_modif) {
        this.fe_modif = fe_modif;
    }

    public String getIp_usuario() {
        return ip_usuario;
    }

    public void setIp_usuario(String ip_usuario) {
        this.ip_usuario = ip_usuario;
    }
    
    
    
    
    
    
}
