package com.sertic.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sertic.app.DTO.LoginRequest;
import com.sertic.app.DTO.LoginResponse;
import com.sertic.app.Model.Usuario;
import com.sertic.app.Service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/publico")
public class PublicoController {

    private final UsuarioService usuarioService;

    @Autowired
    public PublicoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.iniciarSesion(loginRequest.getCorreo(), loginRequest.getContrasena());
        if (usuario != null) {
            // Crear una respuesta que incluya el rol del usuario
            LoginResponse loginResponse = new LoginResponse(usuario.getCorreo(), usuario.getRol());
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}


