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
 * @author ALPHA
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDetail {

    private String idDetalleReqProceso;
    private String idProceso;
    private String esReqProceso;
    private String idDireccion;
    private String idDepartamento;
    private String idArea;
    private String idRequerimiento;

}
