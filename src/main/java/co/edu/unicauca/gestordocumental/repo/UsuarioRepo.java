package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends CrudRepository<Usuario, Integer>
{
    Usuario findByUsuario(String usuario);

    @Query("SELECT u FROM Usuario u where u.persona.id_persona = ?1")
    Usuario findByIdPersona(Integer idPersona);

    @Query("SELECT u.usuario FROM Usuario u, Estudiante e where u.persona.id_persona = e.persona.id_persona and e.id = ?1")
    String usuarioPorIdEst(Integer idEstu);
}