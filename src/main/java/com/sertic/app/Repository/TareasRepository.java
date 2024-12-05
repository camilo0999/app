package com.sertic.app.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sertic.app.Model.Tareas;

@Repository
public interface TareasRepository extends JpaRepository <Tareas, Long> {
    
}
