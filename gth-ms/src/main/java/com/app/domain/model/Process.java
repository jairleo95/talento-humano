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
public class Process {

    private String id_proceso;
    private String no_proceso;
    private String de_proceso;
}
