package com.sertic.app.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sertic.app.Model.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

   
}
