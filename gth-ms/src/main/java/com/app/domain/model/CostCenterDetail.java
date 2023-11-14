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
public class CostCenterDetail {
    
    private String id_dgp;
    private String id_centro_costo;
    private String id_direccion;
    private String id_departamento;
    private String id_area;
    private Double ca_porcentaje;
    private String de_centro_costo;
    private String co_centro_costo;

}
