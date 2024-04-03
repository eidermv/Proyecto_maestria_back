package co.edu.unicauca.gestordocumental.repo;
import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface TipoUsuarioRepo extends CrudRepository<TipoUsuario, Long>{
    TipoUsuario findByNombre(@Param("nombre") String nombre);

    @Query("SELECT tu FROM TipoUsuario tu where tu.id = ?1")
    TipoUsuario buscarPorId(Long id);

}
