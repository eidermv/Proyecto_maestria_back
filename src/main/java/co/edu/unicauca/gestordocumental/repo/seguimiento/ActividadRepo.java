package co.edu.unicauca.gestordocumental.repo.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.Actividad;
import co.edu.unicauca.gestordocumental.model.seguimiento.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadRepo extends JpaRepository<Actividad, Integer> {

    Void save(Optional<Actividad> actividad);

    @Query("SELECT a FROM Actividad a where a.seguimiento.id_seguimiento = ?1")
    List<Actividad> listarActividadPorSeguimiento(Integer id_seguimiento);

    @Query("SELECT a FROM Actividad a where a.seguimiento.id_seguimiento = ?1 and a.visible = 1")
    List<Actividad> listarActividadPorSeguimientoVisible(Integer id_seguimiento);

    @Modifying
    @Query(value = "DELETE FROM Actividad a WHERE a.id_actividad = ?1")
    int eliminarPorId(Integer id_actividad);
}
