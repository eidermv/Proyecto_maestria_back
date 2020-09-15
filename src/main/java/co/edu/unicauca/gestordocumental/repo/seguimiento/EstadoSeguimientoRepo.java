package co.edu.unicauca.gestordocumental.repo.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.EstadoSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoSeguimientoRepo extends JpaRepository<EstadoSeguimiento, Integer> {
}
