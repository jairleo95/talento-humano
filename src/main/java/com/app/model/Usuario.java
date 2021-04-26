/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Alfa.sistemas
 */
public class Usuario {

    private String id_usuario;
    @JsonProperty("username")
    private String no_usuario;
    @JsonProperty("userPassword")
    private String pw_usuario;
    private String id_empleado;
    private String id_rol;

   public Usuario() {
        this.id_usuario = "";
        this.no_usuario = "";
        this.pw_usuario = "";
        this.id_empleado = "";
        this.id_rol = "";
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNo_usuario() {
        return no_usuario;
    }

    public void setNo_usuario(String no_usuario) {
        this.no_usuario = no_usuario;
    }

    public String getPw_usuario() {
        return pw_usuario;
    }

    public void setPw_usuario(String pw_usuario) {
        this.pw_usuario = pw_usuario;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getId_rol() {
        return id_rol;
    }

    public void setId_rol(String id_rol) {
        this.id_rol = id_rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario='" + id_usuario + '\'' +
                ", no_usuario='" + no_usuario + '\'' +
                ", pw_usuario='" + pw_usuario + '\'' +
                ", id_empleado='" + id_empleado + '\'' +
                ", id_rol='" + id_rol + '\'' +
                '}';
    }
}
