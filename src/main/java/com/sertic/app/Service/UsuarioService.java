package com.sertic.app.Service;

import com.sertic.app.Model.Usuario;

public interface UsuarioService {

    public Usuario iniciarSesion(String correo, String contrasena);
    
}
