package co.edu.unicauca.gestordocumental.repo.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepo extends JpaRepository<Persona, Integer> {

    Void save(Optional<Persona> persona);
}
