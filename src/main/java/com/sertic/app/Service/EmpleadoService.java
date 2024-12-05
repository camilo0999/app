package com.sertic.app.Service;


import java.util.List;

import com.sertic.app.Model.Empleado;
import com.sertic.app.Model.Tareas;

public interface EmpleadoService {


    public Empleado registrarEmpleado(Empleado empleado);
    
    public void resolvertarea(Long idEmpleado, Long idTarea);

    public List<Tareas> listarTareas(Long idEmpleado);

    public List<Empleado> listarEmpleados();

   


}
