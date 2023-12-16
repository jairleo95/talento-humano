package com.app.controller.inbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {

    private boolean asigFam;
    private boolean esSistema ;
    private boolean direccionFilter;
    private boolean departFilter;
    private boolean puestoFilter;

    private boolean admin;
    private boolean regDGPAddiotionals;

    public Permission getPermissions(String idRol){
        switch (idRol) {

            case "ROL-0001":
                this.esSistema = true;
                this.asigFam = true;
                this.departFilter = true;

                admin = true;
                regDGPAddiotionals = true;
                break;
            case "ROL-0003":
                regDGPAddiotionals = true;
                break;
            case "ROL-0008":
                this.direccionFilter = true;

                admin = false;
                break;
            case "ROL-0009":
                this.asigFam = true;
                this.esSistema = true;
                break;
            case "ROL-0010":
                this.puestoFilter = true;
                break;
            case "ROL-0017":
                this.asigFam = true;
                break;
            case "ROL-0018":
                this.esSistema = true;
                break;
            case "ROL-0019":
                direccionFilter = true;
            default:
                //permissionDepartFilter = true;
                admin = false;
                departFilter = true;
        }
        return this;
    }
}
