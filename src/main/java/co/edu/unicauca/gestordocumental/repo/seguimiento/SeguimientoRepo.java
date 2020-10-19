package co.edu.unicauca.gestordocumental.repo.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.Seguimiento;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeguimientoRepo extends JpaRepository<Seguimiento, Integer> {

    Void save(Optional<Seguimiento> seguimiento);

    List<Object[]> seguimientos();

    // @Query("SELECT s FROM Seguimiento s where s.tutor.id_tutor = ?1")
    List<Object[]> seguimientosPorTutos(Integer id_tutor);

    List<Object[]> seguimientosPorEstudiante(Integer id_estudiante);
}
