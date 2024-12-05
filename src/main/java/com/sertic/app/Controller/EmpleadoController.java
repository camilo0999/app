package com.sertic.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sertic.app.DTO.ResolverTareaRequest;
import com.sertic.app.Model.Cliente;
import com.sertic.app.Model.Empleado;
import com.sertic.app.Model.Tareas;
import com.sertic.app.Service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping(value = "/registrar", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registraEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            System.out.println("Cliente: " + empleado);
            if (empleado.getUsuario() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empleado es requerido");
            }
            empleadoService.registrarEmpleado(empleado);
            return ResponseEntity.ok("Empleado registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error registrando el empleado: " + e.getMessage());
        }
    }

    @PostMapping(value = "/resolvertarea", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> resolvertarea(@RequestBody ResolverTareaRequest request) {
        try {
            empleadoService.resolvertarea(request.getIdEmpleado(), request.getIdTarea());
            return ResponseEntity.ok("Tarea resuelta exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error resolviendo la tarea: " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar-por-empleado", produces = "application/json")
    public ResponseEntity<?> listaTareasEmpleado(@RequestParam Long idEmpleado) {
        try {
            List<Tareas> tareas = empleadoService.listarTareas(idEmpleado);
            return ResponseEntity.ok(tareas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar los empleado: " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar-empleados", produces = "application/json")
    public ResponseEntity<?> listarEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.listarEmpleados();
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar los empleados: " + e.getMessage());
        }
    }

}
