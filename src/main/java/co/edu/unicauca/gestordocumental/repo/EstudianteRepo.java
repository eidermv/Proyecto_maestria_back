package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import co.edu.unicauca.gestordocumental.model.seguimiento.Seguimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface EstudianteRepo extends CrudRepository<Estudiante, Integer>{
    Estudiante findByCodigo(@Param("codigo") String codigo);
    List<Estudiante> findByNombre(@Param("nombre") String nombre);
    Estudiante findByCorreo(@Param("correo") String correo);
    List<Estudiante> findByMatch(@Param("match") String match);
    Estudiante findById(@Param("id") int id);
    Estudiante findByUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT e FROM Estudiante e where e.persona = ?1")
    Estudiante buscarPorPersona(Persona persona);
}