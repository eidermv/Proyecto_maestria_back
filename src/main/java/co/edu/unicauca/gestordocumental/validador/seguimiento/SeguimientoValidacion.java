package co.edu.unicauca.gestordocumental.validador.seguimiento;

import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.SeguimientoRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeguimientoValidacion {

    @Autowired
    private TutorRepo tutorRepo;

    @Autowired
    private EstudianteRepo estudianteRepo;

    @Autowired
    private SeguimientoRepo seguimientoRepo;

        /*
    id_seguimiento
nombre (obligatorio)
id_tutor
codirector - string ()
id_estudiante
cohorte
objetivos (obligatorio)
objetivos_especificos
id_estado_proyecto
id_tipo_seguimiento
id_estado_seguimiento

     */

    public String SeguimientoValidacionCrear(Map<String, String> body) {
        String nombre = body.get("nombre").toString();
        String id_tutor = body.get("id_tutor").toString();
        String id_estudiante = body.get("id_estudiante").toString();
        String objetivos = body.get("objetivoGeneral").toString();

        if (objetivos == null) {
            return "Objetivo es obligatorio";
        }

        if (nombre == null){
            return "Nombre es obligatorio";
        }

        if (id_tutor == null){
            return "Tutor es obligatorio";
        }
        if (tutorRepo.findById(Integer.parseInt(id_tutor)).isEmpty()) {
            return "Tutor no existe";
        }

        if (id_estudiante == null){
            return "Estudiante es obligatorio";
        }
        if (estudianteRepo.findById(Integer.parseInt(id_estudiante))==null) {
            return "Estudiante no existe";
        }

        return "";
    }

    public String SeguimientoValidacionEditar(Map<String, Object> body) {
        String id_seguimiento = body.get("idSeguimiento").toString();
        String nombre = body.get("nombre").toString();
        String id_tutor = body.get("id_tutor").toString();
        String id_estudiante = body.get("id_estudiante").toString();
        String objetivos = body.get("objetivoGeneral").toString();

        if (seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).isEmpty()) {
            return "Seguimiento no existe";
        }

        if (objetivos == null) {
            return "Objetivo es obligatorio";
        }

        if (nombre == null){
            return "Nombre es obligatorio";
        }

        if (id_tutor == null){
            return "Tutor es obligatorio";
        }
        if (tutorRepo.findById(Integer.parseInt(id_tutor)).isEmpty()) {
            return "Tutor no existe";
        }

        if (id_estudiante == null){
            return "Estudiante es obligatorio";
        }
        if (estudianteRepo.findById(Integer.parseInt(id_estudiante))==null) {
            return "Estudiante no existe";
        }

        return "";
    }
}
