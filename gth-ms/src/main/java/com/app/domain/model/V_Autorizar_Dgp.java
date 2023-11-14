/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Alfa.sistemas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class V_Autorizar_Dgp {

    private String id_trabajador;
    private String no_trabajador;
    private String ap_paterno;
    private String ap_materno;
    private String no_puesto;
    private String nu_pasos;
    private String id_dgp;
    private String co_pasos;
    private String id_detalle_req_proceso;
    private String de_pasos;
    private String id_departamento;
    private String id_puesto;
    private String id_requerimiento;
    private String id_tipo_planilla;
    private String no_req;
    private String id_pasos;
    private String no_usuario;
    private String id_usuario;
    private String no_seccion;
    private String no_area;
    private String ar_foto;
    private String de_foto;
    private String id_foto;
    private String no_ar_foto;
    private String ta_ar_foto;
    private String ti_ar_foto;
    private String fe_creacion;
    private String fe_autorizacion;
    private String ver_list_plazo;
    private String elab_contrato;
    private String val_firm_contrato;
    private String no_dep;
    private String mes_creacion;
    private int val_cod_aps_empleado;
    private int val_cod_huella;
    private int co_aps;
    private int co_huella_digital;
    private String li_motivo;
    private String es_mfl;
    private String di_correo_personal;
    private String di_correo_inst;
    private int val_contrato_adjunto;
    private int val_dgp_cotrato;
    private String mes_plazo;

}
