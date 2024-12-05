package com.sertic.app.Service;

import java.util.List;

import com.sertic.app.Model.Tareas;

public interface TraeasService {

    public List<Tareas> listarTareas();

    public List<Tareas> listarTareasPorCliente(Long idCliente);

    public Tareas buscarTarea(Long idTarea);

    public void eliminarTarea(Long idTarea);

    public void editarTarea(Tareas tareasC, Long id);

    public void estadoRechazado(Long idTarea);

    public void estadoResuelto(Long idTarea);

    
}
