package com.sertic.app.DTO;

public class LoginResponse {
    private String correo;
    private String rol;
    private String mensaje;

    public LoginResponse(String correo, String rol) {
        this.correo = correo;
        this.rol = rol;
        this.mensaje = "Inicio de sesión exitoso";
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
