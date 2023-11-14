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
public class Job {

    private String id_puesto;
    private String no_puesto;
    private String no_corto_pu;
    private String es_puesto;
    private String id_seccion;
    private String co_grupo;
    
}
