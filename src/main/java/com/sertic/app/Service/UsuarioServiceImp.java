package com.sertic.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertic.app.Model.Usuario;
import com.sertic.app.Repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario iniciarSesion(String correo, String contrasena) {
        try {
            Usuario usuario = usuarioRepository.findByCorreo(correo); 
            if (usuario != null && usuario.getContrasena().equals(contrasena)) { 
                return usuario; } return null; 
        } catch (Exception e) {
            throw new RuntimeException("Error al iniciar sesión", e);
        }
        }
    
    
}
