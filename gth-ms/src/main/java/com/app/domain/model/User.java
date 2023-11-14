/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Alfa.sistemas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id_usuario;
    @JsonProperty("username")
    private String no_usuario;
    @JsonProperty("userPassword")
    private String pw_usuario;
    private String id_empleado;
    private String id_rol;

}
