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
 * @author Admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

    private String id_contrato;
    private String id_dgp;
    private String fe_desde;
    private String fe_hasta;
    private String fe_cese;
    private String id_func;
    private String li_condicion;
    private Double ca_sueldo;
    private Double ca_reintegro;
    private Double ca_asig_familiar;
    private Double ho_semana;
    private Double nu_horas_lab;
    private Double dia_contrato;
    private String ti_trabajador;
    private String li_regimen_laboral;
    private String es_discapacidad;
    private String ti_contrato;
    private String li_regimen_pensionario;
    private String es_contrato_trabajador;
    private String us_creacion;
    private String fe_creacion;
    private String us_modif;
    private String fe_modif;
    private String us_ip;
    private String fe_vacacio_ini;
    private String fe_vacacio_fin;
    private String es_contrato;
    private String id_filial;
    private String id_direccion;
    private String id_departamento;
    private String id_area;
    private String id_seccion;
    private String id_puesto;
    private Double ca_bono_alimento;
    private String es_jefe;
    private String li_tipo_convenio;
    private String es_firmo_contrato;
    private Double nu_contrato;
    private String de_observacion;
    private String es_apoyo;
    private String ti_hora_pago;
    private String nu_documento;
    private String es_entregar_doc_reglamentos;
    private String es_registro_huella;
    private String de_registro_sistem_remu;
    private String id_trabajador;
    
}
