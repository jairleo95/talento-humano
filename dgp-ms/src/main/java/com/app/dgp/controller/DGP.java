package com.app.dgp.controller;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RHTM_DGP")
@ToString
public class DGP {

    @Id
    private String id_dgp;
    private String fe_desde;
    private String fe_hasta;
    private Double ca_sueldo;
    private String de_dias_trabajo;
//    private String id_puesto;
//    private String id_requerimiento;
//    private String id_trabajador;
//    private String co_ruc;
//    private String de_lugar_servicio;
//    private String de_servicio;
//    private String de_periodo_pago;
//    private String de_domicilio_fiscal;
//    private String de_subvencion;
//    private String de_horario_capacitacion;
//    private String de_horario_refrigerio;
//    private String de_dias_capacitacion;
//    private String es_dgp;
//    private String us_creacion;
//    private String fe_creacion;
//    private String us_modif;
//    private String fe_modif;
//    private String ip_usuario;
//    private Double ca_bono_alimentario;
//    private Double de_bev;
//    private Double ca_centro_costos;
//    private String de_antecedentes_policiales;
//    private String de_certificado_salud;
//    private String de_monto_honorario;
//    private String no_banco;
//    private String nu_cuenta;
//    private String nu_cuenta_banc;
//    private String es_gen_nu_cuenta;
//    private String no_banco_otros;
    
}
