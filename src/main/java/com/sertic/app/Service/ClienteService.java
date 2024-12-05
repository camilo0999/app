package com.sertic.app.Service;


import java.util.List;

import com.sertic.app.Model.Cliente;
import com.sertic.app.Model.Tareas;

public interface ClienteService {

    public void registrarCliente(Cliente cliente);

    public List<Cliente> listarClientes();

    public Cliente buscarCliente(Long idCliente);

    public void registrarTarea(Long idCliente, Tareas tareas);

    public void editarCliente(Cliente clienteC, Long id);
    
}
