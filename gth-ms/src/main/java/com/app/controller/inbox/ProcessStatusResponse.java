package com.app.controller.inbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
public class ProcessStatusResponse {
    @JsonProperty("iddgp")
    private String iddgp;
    @JsonProperty("html")
    private String html;

}
