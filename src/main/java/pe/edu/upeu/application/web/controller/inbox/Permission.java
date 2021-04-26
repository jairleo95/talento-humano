package pe.edu.upeu.application.web.controller.inbox;

public class Permission {

    /*permisos*/
    private boolean asigFam = false;
    private boolean esSistema = false;
    private boolean direccionFilter = false;
    private boolean departFilter = false;
    private boolean puestoFilter = false;

    private boolean admin = false;
    private boolean regDGPAddiotionals = false;

    public boolean isAsigFam() {
        return asigFam;
    }

    public boolean isEsSistema() {
        return esSistema;
    }

    public boolean isDireccionFilter() {
        return direccionFilter;
    }

    public boolean isDepartFilter() {
        return departFilter;
    }

    public boolean isPuestoFilter() {
        return puestoFilter;
    }

    public void setAsigFam(boolean asigFam) {
        this.asigFam = asigFam;
    }

    public void setEsSistema(boolean esSistema) {
        this.esSistema = esSistema;
    }

    public void setDireccionFilter(boolean direccionFilter) {
        this.direccionFilter = direccionFilter;
    }

    public void setDepartFilter(boolean departFilter) {
        this.departFilter = departFilter;
    }

    public void setPuestoFilter(boolean puestoFilter) {
        this.puestoFilter = puestoFilter;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setRegDGPAddiotionals(boolean regDGPAddiotionals) {
        this.regDGPAddiotionals = regDGPAddiotionals;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isRegDGPAddiotionals() {
        return regDGPAddiotionals;
    }

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
