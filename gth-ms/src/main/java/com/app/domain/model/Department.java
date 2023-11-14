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
public class Department {

    private String id_departamento;
    private String no_dep;
    private String no_corto_dep;
    private String es_departamento;
    private String id_direccion;

}
