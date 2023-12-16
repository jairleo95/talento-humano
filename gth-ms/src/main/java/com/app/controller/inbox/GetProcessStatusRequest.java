package com.app.controller.inbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;


@Data
//@buil
public class GetProcessStatusRequest {
    @JsonProperty("iddgp")
    private String iddgp;
    @JsonProperty("iddrp")
    private String iddrp;
    @JsonProperty("iddep")
    private String iddep;

}
