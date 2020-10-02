package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.PublicacionRevista;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface PublicacionRevistaRepo extends CrudRepository<PublicacionRevista, Integer>{
    PublicacionRevista findByIdPublicacion(@Param("idPublicacion") int idPublicacion);
}