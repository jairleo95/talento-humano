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
 * @author Alex
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicCharge {

    private String nu_doc;
    private String es_tipo_doc;
    private String no_trabajador;
    private String ap_paterno;
    private String ap_materno;
    private String no_eap;
    private String no_facultad;
    private String de_condicion;
    private String de_carga;
    private String id_proceso_carga_ac;
    private String id_trabajador;
    private String id_situacion_educativa;
    private String no_s_educativa;
    private String profesion_docente;
    private String fe_desde;
    private String fe_hasta;
    private String fe_creacion;
    private Integer countCursos;

    private String validateExistTrabajador;

}
