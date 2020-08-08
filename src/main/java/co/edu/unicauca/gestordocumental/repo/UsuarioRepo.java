package co.edu.unicauca.gestordocumental.repo;

import co.edu.unicauca.gestordocumental.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepo extends CrudRepository<Usuario, Integer>
{
    Usuario findByUsuario(String usuario);
}