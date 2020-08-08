package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.PublicacionCapituloLibro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PublicacionCapituloLibroRepo extends CrudRepository<PublicacionCapituloLibro, Integer>{
    PublicacionCapituloLibro findByIdPublicacion(@Param("idPublicacion") int idPublicacion);
}