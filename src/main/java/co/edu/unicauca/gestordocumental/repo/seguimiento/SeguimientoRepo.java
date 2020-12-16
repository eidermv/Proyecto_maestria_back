package co.edu.unicauca.gestordocumental.repo.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.Seguimiento;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SeguimientoRepo extends JpaRepository<Seguimiento, Integer> {

    Void save(Optional<Seguimiento> seguimiento);

    @Query("SELECT s FROM Seguimiento s")
    List<Seguimiento> seguimientos();

    @Query("SELECT s FROM Seguimiento s where s.tutor.id_tutor = ?1")
    // List<Object[]> seguimientosPorTutos(Integer id_tutor);
    List<Seguimiento> seguimientosPorTutor(Integer id_tutor);

    @Query("SELECT s FROM Seguimiento s where s.estudiante.id = ?1")
    List<Seguimiento> seguimientosPorEstudiante(Integer id_estudiante);

    @Modifying
    @Query(value = "DELETE FROM Seguimiento s WHERE s.id_seguimiento = ?1")
    int eliminarPorId(Integer id_seguimiento);

    @Modifying
    @Query(value = "DELETE FROM Actividad a WHERE a.seguimiento.id_seguimiento = ?1")
    int eliminarActividadPorSeguimiento(Integer id_seguimiento);
}
