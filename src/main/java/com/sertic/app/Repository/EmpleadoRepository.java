package com.sertic.app.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sertic.app.Model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository <Empleado, Long>{
    
}
