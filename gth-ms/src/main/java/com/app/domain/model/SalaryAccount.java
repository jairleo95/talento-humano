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
public class SalaryAccount {
    private String id_cuenta_sueldo;
    private String no_banco;
    private String nu_cuenta;
    private String nu_cuenta_banc;
    private String es_gem_nu_cuenta;
    private String no_banco_otros;
    private String id_trabajador;
    private String es_cuenta_sueldo;

}
