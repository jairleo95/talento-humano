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
public class Autorizacion {

    private String id_autorizacion;
    private String id_dgp;
    private String es_autorizacion;
    private String nu_pasos;
    private String ip_usuario;
    private String us_creacion;
    private String fe_creacion;
    private String us_modif;
    private String fe_modif;
    private String co_puesto;
    private String id_puesto;
    private String id_detalle_req_proceso;
    private String id_pasos;

}
