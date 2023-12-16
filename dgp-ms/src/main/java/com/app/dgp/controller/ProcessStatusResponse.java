package com.app.dgp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProcessStatusResponse {
    @JsonProperty("iddgp")
    private String iddgp;
    @JsonProperty("html")
    private String html;

}
