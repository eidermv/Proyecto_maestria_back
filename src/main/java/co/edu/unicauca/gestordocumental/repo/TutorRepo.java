package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Tutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TutorRepo extends CrudRepository<Tutor, Integer>{
    Tutor findByNombre(@Param("nombre") String nombre);
    List<Tutor> findAllByNombre(@Param("nombre") String nombre);
}