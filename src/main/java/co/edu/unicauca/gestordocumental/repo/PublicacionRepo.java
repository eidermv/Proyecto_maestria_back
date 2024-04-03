package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Publicacion;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface PublicacionRepo extends CrudRepository<Publicacion, Integer>{
    List<Publicacion> findByIdEstudiante(@Param("idEstudiante") Integer idEstudiante);
}