package co.edu.unicauca.gestordocumental.repo.seguimiento;

import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import co.edu.unicauca.gestordocumental.model.seguimiento.TipoTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTutorRepo extends JpaRepository<TipoTutor, Integer> {

    @Query("SELECT tu FROM TipoTutor tu where tu.id_tipo_tutor = ?1")
    TipoTutor buscarPorId(Integer id);
}
