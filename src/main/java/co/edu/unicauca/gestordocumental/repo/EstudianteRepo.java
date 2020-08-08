package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EstudianteRepo extends CrudRepository<Estudiante, Integer>{
    Estudiante findByCodigo(@Param("codigo") String codigo);
    List<Estudiante> findByNombre(@Param("nombre") String nombre);
    Estudiante findByCorreo(@Param("correo") String correo);
    List<Estudiante> findByMatch(@Param("match") String match);
    Estudiante findById(@Param("id") int id);
    Estudiante findByUsuario(@Param("usuario") Usuario usuario);
}