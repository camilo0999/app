package com.sertic.app.Service;




import java.util.List;

import org.checkerframework.checker.units.qual.t;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.sertic.app.Model.Cliente;
import com.sertic.app.Model.Tareas;
import com.sertic.app.Repository.ClienteRepository;
import com.sertic.app.Repository.TareasRepository;
import com.sertic.app.Repository.UsuarioRepository;

@Service
public class ClienteServiceImp implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImp.class);
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TareasRepository tareasRepository;

    

    @Transactional
    @Override
    public void registrarCliente(Cliente cliente) {
        if (cliente == null || cliente.getUsuario() == null) {
            throw new IllegalArgumentException("Cliente and Usuario must not be null");
        }
        try {
            cliente.getUsuario().setRol("Cliente");
            usuarioRepository.save(cliente.getUsuario());
            clienteRepository.save(cliente);
            logger.info("Cliente registrado");
        } catch (Exception e) {
            logger.error("Error registrando el cliente: ", e);
            throw e;  // Re-throw the exception to ensure the transaction is rolled back
        }
    }

    @Override
    public Cliente buscarCliente(Long idCliente) {

        try {
            return clienteRepository.findById(idCliente).orElse(null);
        } catch (Exception e) {
            logger.error("Error buscando el cliente: " ,e);
            return null;
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void registrarTarea(Long idCliente, Tareas tarea) {
        try {
            // Verifica si el cliente existe
            Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
            if (cliente == null) {
                throw new RuntimeException("Cliente no encontrado");
            }

            // Verifica si la tarea es válida
            if (tarea == null) {
                throw new IllegalArgumentException("Tarea no puede ser nula");
            }

            tarea.setEstado("Pendiente");

            tarea.setCliente(cliente);

            tareasRepository.save(tarea);

            // Asocia la tarea al cliente
            cliente.getTareasRegistradas().add(tarea);
            
            // Guarda el cliente con la nueva tarea
            clienteRepository.save(cliente);
            
            logger.info("Tarea registrada exitosamente para el cliente ID: " + idCliente);

        } catch (RuntimeException e) {
            logger.error("Error al registrar la tarea: Cliente no encontrado", e);
            throw new RuntimeException("Error al registrar la tarea: Cliente no encontrado");
        } catch (Exception e) {
            logger.error("Error inesperado al registrar la tarea", e);
            throw new RuntimeException("Error inesperado al registrar la tarea");
        }
    }

    @Override
    public void editarCliente(Cliente clienteC, Long id) {
        try {
            
            Cliente cliente = buscarCliente(id);
            if (cliente == null) {
                throw new RuntimeException("Cliente no encontrado");
            }
            cliente.setDireccion(clienteC.getDireccion());
            cliente.setTelefono(clienteC.getTelefono());
            cliente.setFechaDeNacimiento(clienteC.getFechaDeNacimiento());
            cliente.setGenero(clienteC.getGenero());

            clienteRepository.save(cliente);
            logger.info("Cliente editado exitosamente");
        } catch (Exception e) {
            logger.error("Error editando el cliente: " ,e);
        }
    }

}
