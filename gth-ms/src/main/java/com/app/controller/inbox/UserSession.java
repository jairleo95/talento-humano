package com.app.controller.inbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSession {
    private String id;
    private String puestoId;
    private String roId;
    private String depId;
    private String dirId;

}
