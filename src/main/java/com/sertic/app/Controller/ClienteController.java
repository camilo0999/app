package com.sertic.app.Controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sertic.app.Model.Cliente;
import com.sertic.app.Model.Tareas;
import com.sertic.app.Service.ClienteService;
import com.sertic.app.Service.EmpleadoService;
import com.sertic.app.Service.TraeasService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final TraeasService tareasService;

    @Autowired
    public ClienteController(ClienteService clienteService, EmpleadoService empleadoService, TraeasService tareasService) {
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.tareasService = tareasService;
    }

    @PostMapping(value = "/registrar", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody Cliente cliente) {
        try {
            System.out.println("Cliente: " + cliente);
            if (cliente.getUsuario() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario es requerido");
            }
            clienteService.registrarCliente(cliente);
            return ResponseEntity.ok("Cliente registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error registrando el cliente: " + e.getMessage());
        }
    }
    




    @PostMapping(value = "/registrar-tarea", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registrarTarea(@RequestBody Map<String, Object> request) {
        try {
            Long idCliente = Long.valueOf(request.get("idCliente").toString());
            if (idCliente == null) {
                return ResponseEntity.badRequest().body("idCliente es requerido");
            }

            Map<String, Object> tareasMap = (Map<String, Object>) request.get("tareas");
            if (tareasMap == null) {
                return ResponseEntity.badRequest().body("Tareas son requeridas");
            }

            String dispositivo = (String) tareasMap.get("dispositivo");
            if (dispositivo == null || dispositivo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Dispositivo es requerido");
            }

            String fechaInicioStr = (String) tareasMap.get("fechaInicio");
            String fechaFinStr = (String) tareasMap.get("fechaFin");
            if (fechaInicioStr == null || fechaFinStr == null) {
                return ResponseEntity.badRequest().body("Las fechas de inicio y fin son requeridas");
            }

            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(fechaInicioStr);
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(fechaFinStr);

            Tareas tarea = new Tareas();
            tarea.setDispositivo(dispositivo);
            tarea.setFechaInicio(fechaInicio);
            tarea.setFechaFin(fechaFin);
            tarea.setEstado((String) tareasMap.get("estado"));
            tarea.setDetalle((String) tareasMap.get("detalle"));
            tarea.setImagen((String) tareasMap.get("imagen"));
            tarea.setValor(new BigDecimal(tareasMap.get("valor").toString()));

            clienteService.registrarTarea(idCliente, tarea);
            return ResponseEntity.ok("Tarea registrada exitosamente");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al parsear las fechas: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la tarea: " + e.getMessage());
        }
    }

    @GetMapping(value = "/buscar-cliente", produces = "application/json")
    public ResponseEntity<?> buscarCliente(@RequestParam Long idCliente) {
        try {
            Cliente cliente = clienteService.buscarCliente(idCliente);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID de cliente inválido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el cliente");
        }
    }

    @GetMapping(value = "/listar-por-cliente", produces = "application/json")
    public ResponseEntity<?> listarClientes(@RequestParam Long idCliente) {
        try {
            List<Tareas> clientes = tareasService.listarTareasPorCliente(idCliente);
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar los clientes: " + e.getMessage());
        }
    }

    @PutMapping(value = "/actualizar", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> actualizarCliente(@RequestParam Long idCliente, @RequestBody Cliente cliente) {
        try {
            clienteService.editarCliente(cliente, idCliente);
            return ResponseEntity.ok("Cliente actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el cliente: " + e.getMessage());
        }
    }
}
