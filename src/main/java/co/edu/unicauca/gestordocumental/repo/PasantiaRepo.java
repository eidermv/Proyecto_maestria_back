package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Pasantia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface PasantiaRepo extends CrudRepository<Pasantia, Integer>{
    List<Pasantia> findByIdEstudiante(@Param("idEstudiante") Integer idEstudiante);
    Pasantia findByIdPasantia(@Param("idPasantia") Integer idPasantia);
}
