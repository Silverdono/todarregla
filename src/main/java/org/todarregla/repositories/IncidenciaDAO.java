package org.todarregla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.todarregla.model.Empleado;
import org.todarregla.model.Incidencia;
import org.todarregla.model.Sector;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IncidenciaDAO extends JpaRepository<Incidencia, Long> {

    @Query(value = "SELECT incidencia FROM Incidencia incidencia where incidencia.idIncidencia = ?1")
    Incidencia getIncidenciaById(Long idIncidencia);

    List<Incidencia> getIncidenciasByCompletada(Boolean completada);

    @Query(value = "SELECT incidencia from Incidencia incidencia" +
            " where incidencia.empleado in ?1 and incidencia.completada = ?2 and incidencia.confirmada = ?3 " +
            "order by incidencia.empleado.idEmpleado asc")
    public List<Incidencia> getIncidenciasByEmpleadoInAndCompletadaAndConfirmada(Collection<Empleado> empleados, Boolean completada, Boolean confirmada);

    @Query(value = "SELECT incidencia.empleado.idEmpleado, incidencia from Incidencia incidencia" +
            " where incidencia.empleado in ?1 and incidencia.completada = ?2 and incidencia.confirmada = ?3 " +
            "order by incidencia.empleado.idEmpleado asc")
    Map<Long, Incidencia> getIncidenciasAndEmpleadoIdByEmpleadosAndCompletadaAndConfirmada(List<Empleado> empleados, Boolean completada, Boolean confirmada);


    @Query(value = "SELECT incidencia from Incidencia incidencia " +
            "where incidencia.sector = ?1 and incidencia.completada = ?2 and incidencia.confirmada = ?3")
    List<Incidencia> getIncidenciasDatesBySectorAndConfirmadaAndCompletada(Sector sector, Boolean completada, Boolean confirmada);

    @Query(value = "UPDATE Incidencia incidencia set incidencia.completada = true where incidencia.idIncidencia in ?1")
    @Transactional
    @Modifying
    void bulkUpdateCompletadaField(List<Long> ids);

    @Query(value = "UPDATE Incidencia incidencia set incidencia.completada = true where incidencia.idIncidencia = ?1")
    @Transactional
    @Modifying
    void updateCompletadaField(Long id);
}
