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
 * @author joserodrigo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostCenter {
    private String id_centro_costo;
    private String co_centro_costo;
    private String de_centro_costo;
    private String id_departamento;
    private String us_creacion;
    private String fe_creacion;
    private String us_modif;
    private String fe_modif;
    private String ca_porcentaje;
    
}
