package co.edu.unicauca.gestordocumental.validador.seguimiento;

import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PersonaValidacion {

    @Autowired
    private TutorRepo tutorRepo;
    /*
            id_tutor
    identificacion (obligatorio)
    nombres (obligatorio)
    apellidos (obligatorio)
    correo (obligatorio)
    telefono
    departamento
    grupo_investigacion
    id_tipo_tutor
    universidad (SI es externo) - si es interno, sería unicauca
             */
    // si es '', esta bien, si es diferente de '', hay error
    public String TutorValidacionCrear(Map<String, String> body) {
        String identificacion = body.get("identificacion");
        String nombres = body.get("nombres");
        String apellidos = body.get("apellidos");
        String correo = body.get("correo");

        if (identificacion == null){
            return "Identificacion es obligatoria";
        }
        try {
            Integer.parseInt(identificacion);
        } catch (NumberFormatException nfe){
            return "Identificacion debe ser numerica";
        }

        if (nombres == null){
            return "Nombres son obligatorios";
        }

        if (apellidos == null){
            return "Apellidos son obligatorios";
        }

        if (correo == null){
            return "Corre es obligatoria";
        }

        return "";
    }

    // si es '', esta bien, si es diferente de '', hay error
    public String TutorValidacionEditar(Map<String, String> body) {
        String id = body.get("id_tutor");
        String identificacion = body.get("identificacion");
        String nombres = body.get("nombres");
        String apellidos = body.get("apellidos");
        String correo = body.get("correo");

        if (this.tutorRepo.findById(Integer.parseInt(id)).isEmpty()) {
            return "Tutor no se puede editar, no exite";
        }

        if (identificacion == null){
            return "Identificacion es obligatoria";
        }
        try {
            Integer.parseInt(identificacion);
        } catch (NumberFormatException nfe){
            return "Identificacion debe ser numerica";
        }

        if (nombres == null){
            return "Nombres son obligatorios";
        }

        if (apellidos == null){
            return "Apellidos son obligatorios";
        }

        if (correo == null){
            return "Corre es obligatoria";
        }

        return "";
    }
}
