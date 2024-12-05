package com.sertic.app.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sertic.app.Model.Empleado;
import com.sertic.app.Model.Tareas;
import com.sertic.app.Model.Usuario;
import com.sertic.app.Repository.EmpleadoRepository;
import com.sertic.app.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmpleadoServiceImp implements EmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImp.class);

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TraeasService traeasService;

    @Override
    public Empleado registrarEmpleado(Empleado empleado) {
        try {
            empleado.getUsuario().setRol("Empleado");
            empleado.setSalario(BigDecimal.ZERO);
            usuarioRepository.save(empleado.getUsuario());
            empleadoRepository.save(empleado);
            return empleado;
        } catch (Exception e) {
            logger.error("Error registrando el empleado: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void resolvertarea(Long idEmpleado, Long idTarea) {
        try {
           Tareas tareas = traeasService.buscarTarea(idTarea);
           Empleado empleado = empleadoRepository.findById(idEmpleado).orElse(null); 
        


           if (tareas != null && empleado != null) {
                empleado.getTareasRealizada().add(tareas);
                empleado.setSalario(empleado.getSalario().add(tareas.getValor()));                
                tareas.setEmpleado(empleado);
                empleadoRepository.save(empleado);
                tareas.setEmpleado(empleado);
                traeasService.estadoResuelto(idTarea);
           }

        } catch (Exception e) {
            logger.error("Error resolviendo la tarea: " + e.getMessage());
        }    
    }

    @Override
    public List<Tareas> listarTareas(Long idEmpleado) {
        try {
            Empleado empleado = empleadoRepository.findById(idEmpleado).orElse(null);
            return empleado.getTareasRealizada();
        } catch (Exception e) {
            logger.error("Error listando las tareas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Empleado> listarEmpleados() {
       try {
        return empleadoRepository.findAll();

       } catch (Exception e) {
        logger.error("Error listando los empleados: " + e.getMessage());
        return null;
       }
    }


    


}
