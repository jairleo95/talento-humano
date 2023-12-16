package com.app.controller.inbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ProcessStatusRequest {
    @JsonProperty("dgps")
    private List<DGPDetails> dgps;

    @Getter
    @Setter
    @ToString
    public static class DGPDetails {
        @JsonProperty("iddgp")
        private String iddgp;
        @JsonProperty("iddrp")
        private String iddrp;
        @JsonProperty("iddep")
        private String iddep;
//        @JsonProperty("html")
//        private String html;
    }
}
