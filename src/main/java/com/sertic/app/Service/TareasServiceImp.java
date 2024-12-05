package com.sertic.app.Service;

import org.springframework.stereotype.Service;

import com.sertic.app.Model.Cliente;
import com.sertic.app.Model.Tareas;
import com.sertic.app.Repository.TareasRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TareasServiceImp implements TraeasService {

    private static final Logger logger = LoggerFactory.getLogger(TareasServiceImp.class);

    private final TareasRepository tareasRepository;

    private final ClienteService clienteService;

    @Autowired
    public TareasServiceImp(TareasRepository tareasRepository, ClienteService clienteService) {
        this.tareasRepository = tareasRepository;
        this.clienteService = clienteService;
    }

    @Override
    public List<Tareas> listarTareas() {
        try {
            return tareasRepository.findAll();
        } catch (Exception e) {
            logger.error("Error listando las tareas: " ,e);
            return null;
        }
    }

    @Override
    public List<Tareas> listarTareasPorCliente(Long idCliente) {
        try {
            Cliente cliente = clienteService.buscarCliente(idCliente);
            return cliente.getTareasRegistradas();
        } catch (Exception e) {
            logger.error("Error listando las tareas por cliente: " ,e);
            return null;
        }
    }

    @Override
    public Tareas buscarTarea(Long idTarea) {
        try {
            return tareasRepository.findById(idTarea).orElse(null);
        } catch (Exception e) {
            logger.error("Error buscando la tarea: " ,e);
            return null;
        }
    }

    @Override
    public void eliminarTarea(Long idTarea) {
       try {
        tareasRepository.deleteById(idTarea);
       } catch (Exception e) {
        logger.error("Error eliminando la tarea: " ,e);
       }
    }

    @Override
    public void editarTarea(Tareas tareasC, Long id) {
        try {
            Tareas tarea = tareasRepository.findById(id).orElse(null);
            if (tarea == null) {
                throw new RuntimeException("Tarea no encontrada");
            }
            tarea.setDispositivo(tareasC.getDispositivo());
            tarea.setFechaInicio(tareasC.getFechaInicio());
            tarea.setFechaFin(tareasC.getFechaFin());
            tarea.setEstado(tareasC.getEstado());
            tarea.setDetalle(tareasC.getDetalle());
            tarea.setImagen(tareasC.getImagen());
            tarea.setValor(tareasC.getValor());
            tareasRepository.save(tarea);
        } catch (Exception e) {
            logger.error("Error editando la tarea: " ,e);
        }
    }

    @Override
    public void estadoRechazado(Long idTarea) {
        try {
            Tareas tarea = tareasRepository.findById(idTarea).orElse(null);
            if (tarea == null) {
                throw new RuntimeException("Tarea no encontrada");
            }
            tarea.setEstado("Rechazado");
            tareasRepository.save(tarea);
        } catch (Exception e) {
            logger.error("Error cambiando el estado de la tarea: " ,e);
        }
    }

    @Override
    public void estadoResuelto(Long idTarea) {
        try {
            Tareas tarea = tareasRepository.findById(idTarea).orElse(null);
            if (tarea == null) {
                throw new RuntimeException("Tarea no encontrada");
            }
            tarea.setEstado("Resuelto");
            tareasRepository.save(tarea);
        } catch (Exception e) {
            logger.error("Error cambiando el estado de la tarea: " ,e);
        }
    }
    
}
