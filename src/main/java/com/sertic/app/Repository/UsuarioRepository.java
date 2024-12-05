package com.sertic.app.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sertic.app.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    public Usuario findByCorreo(String correo);
    
}
