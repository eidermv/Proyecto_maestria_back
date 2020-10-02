package co.edu.unicauca.gestordocumental.controller.seguimiento;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.model.seguimiento.EstadoProyecto;
import co.edu.unicauca.gestordocumental.model.seguimiento.EstadoSeguimiento;
import co.edu.unicauca.gestordocumental.model.seguimiento.TipoSeguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import co.edu.unicauca.gestordocumental.model.seguimiento.Seguimiento;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.TipoUsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.*;
import co.edu.unicauca.gestordocumental.validador.seguimiento.PersonaValidacion;
import co.edu.unicauca.gestordocumental.validador.seguimiento.SeguimientoValidacion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(path="/seguimiento")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguimientoController {

    @Autowired
    private SeguimientoRepo seguimientoRepo;

    @Autowired
    private TutorRepo tutorRepo;

    @Autowired
    private TipoSeguimientoRepo tipoSeguimientoRepo;

    @Autowired
    private EstadoProyectoRepo estadoProyectoRepo;

    @Autowired
    private EstadoSeguimientoRepo estadoSeguimientoRepo;

    @Autowired
    private EstudianteRepo estudianteRepo;

    JSONObject rta = new JSONObject();

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

    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/crear", produces = "application/json")
    public String crearNuevoSeguimiento(
            @RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        SeguimientoValidacion seguimientoValidacion = new SeguimientoValidacion();
        String validacion = seguimientoValidacion.SeguimientoValidacionCrear(body);
        if (!validacion.equals("")) {

            String nombre = body.get("nombre");
            String id_tutor = body.get("id_tutor");
            String codirector = body.get("codirector");
            String id_estudiante = body.get("id_estudiante");
            String objetivos = body.get("objetivos");

            String objetivos_especificos = body.get("objetivos_especificos");
            String id_estado_proyecto = body.get("id_estado_proyecto");
            String id_tipo_seguimiento = body.get("id_tipo_seguimiento");
            String id_estado_seguimiento = body.get("id_estado_seguimiento");


            this.rta = new JSONObject();
            Seguimiento seguimientoNuevo = new Seguimiento();
            EstadoSeguimiento estadoSeguimiento = estadoSeguimientoRepo.findById(Integer.parseInt(id_estado_seguimiento)).get();
            TipoSeguimiento tipoSeguimiento = tipoSeguimientoRepo.findById(Integer.parseInt(id_tipo_seguimiento)).get();
            EstadoProyecto estadoProyecto = estadoProyectoRepo.findById(Integer.parseInt(id_estado_proyecto)).get();
            Estudiante estudiante = estudianteRepo.findById(Integer.parseInt(id_estudiante));
            Tutor tutor = tutorRepo.findById(Integer.parseInt(id_tutor)).get();

            seguimientoNuevo.setTutor(tutor);
            seguimientoNuevo.setEstudiante(estudiante);
            seguimientoNuevo.setEstadoProyecto(estadoProyecto);
            seguimientoNuevo.setTipoSeguimiento(tipoSeguimiento);
            seguimientoNuevo.setEstadoSeguimiento(estadoSeguimiento);
            seguimientoNuevo.setNombre(nombre);
            seguimientoNuevo.setCodirector(codirector);
            seguimientoNuevo.setObjetivoGeneral(objetivos);
            seguimientoNuevo.setObjetivosEspecificos(objetivos_especificos);




            Seguimiento guardada = this.seguimientoRepo.save(seguimientoNuevo);

            if (guardada != null){

                        rta.put("estado", "exito");
                        rta.put("data", "");
                        rta.put("mensaje", "Seguimiento se creo correctamente");

            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo creando seguimiento");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", validacion);
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Coordinador')")
    @PutMapping(path="/editar", produces = "application/json")
    public String editarSeguimiento(
            @RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        SeguimientoValidacion seguimientoValidacion = new SeguimientoValidacion();
        String validacion = seguimientoValidacion.SeguimientoValidacionEditar(body);
        if (!validacion.equals("")) {

            String id_seguimiento = body.get("id_seguimiento");
            String nombre = body.get("nombre");
            String id_tutor = body.get("id_tutor");
            String codirector = body.get("codirector");
            String id_estudiante = body.get("id_estudiante");
            String objetivos = body.get("objetivos");

            String objetivos_especificos = body.get("objetivos_especificos");
            String id_estado_proyecto = body.get("id_estado_proyecto");
            String id_tipo_seguimiento = body.get("id_tipo_seguimiento");
            String id_estado_seguimiento = body.get("id_estado_seguimiento");


            this.rta = new JSONObject();
            Seguimiento seguimientoNuevo = seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).get();
            EstadoSeguimiento estadoSeguimiento = estadoSeguimientoRepo.findById(Integer.parseInt(id_estado_seguimiento)).get();
            TipoSeguimiento tipoSeguimiento = tipoSeguimientoRepo.findById(Integer.parseInt(id_tipo_seguimiento)).get();
            EstadoProyecto estadoProyecto = estadoProyectoRepo.findById(Integer.parseInt(id_estado_proyecto)).get();
            Estudiante estudiante = estudianteRepo.findById(Integer.parseInt(id_estudiante));
            Tutor tutor = tutorRepo.findById(Integer.parseInt(id_tutor)).get();

            seguimientoNuevo.setTutor(tutor);
            seguimientoNuevo.setEstudiante(estudiante);
            seguimientoNuevo.setEstadoProyecto(estadoProyecto);
            seguimientoNuevo.setTipoSeguimiento(tipoSeguimiento);
            seguimientoNuevo.setEstadoSeguimiento(estadoSeguimiento);
            seguimientoNuevo.setNombre(nombre);
            seguimientoNuevo.setCodirector(codirector);
            seguimientoNuevo.setObjetivoGeneral(objetivos);
            seguimientoNuevo.setObjetivosEspecificos(objetivos_especificos);




            Seguimiento guardada = this.seguimientoRepo.save(seguimientoNuevo);

            if (guardada != null){

                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Seguimiento se edito correctamente");

            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo editando seguimiento");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", validacion);
        }

        return rta.toString();
    }

    @DeleteMapping(path="/eliminar/{id_seguimiento}", produces = "application/json")
    public String editarTutor(
            @PathVariable String id_seguimiento) {
        this.rta = new JSONObject();
        if (this.tutorRepo.findById(Integer.parseInt(id_seguimiento)).isPresent()) {

            Seguimiento seguimiento = this.seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).get();

            try {
                this.seguimientoRepo.deleteById(seguimiento.getIdSeguimiento());
                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Seguimiento se elimino correctamente");
            } catch (Exception e) {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo eliminando seguimiento");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "Seguimiento no se puede eliminar porque no existe");
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/listar", produces = "application/json")
    public String listarSeguimientos() {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        List<Seguimiento> seguimientos = (List<Seguimiento>) this.seguimientoRepo.findAll();
        if (seguimientos.size() > 0) {

            rta.put("estado", "exito");
            rta.put("data", seguimientos);
            rta.put("mensaje", "Lista de seguimientos");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen seguimientos");
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Tutor')")
    @GetMapping(path="/listarPorTutor/{id_tutor}", produces = "application/json")
    public String listarSeguimientoPorTutor(@PathVariable String id_tutor) {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        List<Seguimiento> seguimientos = this.seguimientoRepo.seguimientosPorTutos(Integer.parseInt(id_tutor));
        if (seguimientos.size() > 0) {

            rta.put("estado", "exito");
            rta.put("data", seguimientos);
            rta.put("mensaje", "Lista de seguimientos por tutor");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen seguimientos por tutor");
        }

        return rta.toString();
    }
}