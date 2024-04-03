package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.PracticaDocente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface PracticaDocenteRepo extends CrudRepository<PracticaDocente, Integer>{
    List<PracticaDocente> findByIdEstudiante(@Param("idEstudiante") Integer idEstudiante);
    PracticaDocente findByIdPracticaDocente(@Param("idPracticaDocente") Integer idPracticaDocente);
}
