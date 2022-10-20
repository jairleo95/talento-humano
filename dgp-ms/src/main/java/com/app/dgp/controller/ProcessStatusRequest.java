package com.app.dgp.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class ProcessStatusRequest {
    @JsonProperty("dgps")
    private List<DGPDetails> dgps;

    @Getter
    @Setter
    @ToString
    public static class DGPDetails{
        @JsonProperty("iddgp")
        private String iddgp;
        @JsonProperty("iddrp")
        private String iddrp;
        @JsonProperty("iddep")
        private String iddep;
        @JsonProperty("html")
        private String html;
    }
}
