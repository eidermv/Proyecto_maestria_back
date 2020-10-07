package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface TutorRepo extends CrudRepository<Tutor, Integer>{
    Tutor findByNombre(@Param("nombre") String nombre);
    List<Tutor> findAllByNombre(@Param("nombre") String nombre);

    @Query("SELECT t FROM Tutor t where t.persona = ?1")
    Tutor buscarPorPersona(Persona persona);

    @Query("SELECT concat(p.nombres, ' ', p.apellidos) as nombre FROM Tutor t, Persona p where t.persona.id_persona = p.id_persona")
    List<String> listarTodos();
}
