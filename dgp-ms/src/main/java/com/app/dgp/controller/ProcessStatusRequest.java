package com.app.dgp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
//@buil
public class ProcessStatusRequest {
    @JsonProperty("iddgp")
    private String iddgp;
    @JsonProperty("iddrp")
    private String iddrp;
    @JsonProperty("iddep")
    private String iddep;

}
