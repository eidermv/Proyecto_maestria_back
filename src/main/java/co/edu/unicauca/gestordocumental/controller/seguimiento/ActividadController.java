package co.edu.unicauca.gestordocumental.controller.seguimiento;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.model.seguimiento.*;
import co.edu.unicauca.gestordocumental.model.seguimiento.TipoSeguimiento;
import co.edu.unicauca.gestordocumental.repo.seguimiento.ActividadRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.SeguimientoRepo;
import co.edu.unicauca.gestordocumental.validador.seguimiento.ActividadValidacion;
import co.edu.unicauca.gestordocumental.validador.seguimiento.SeguimientoValidacion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/actividad")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ActividadController {
    @Autowired
    private ActividadRepo actividadRepo;

    @Autowired
    private SeguimientoRepo seguimientoRepo;

    JSONObject rta = new JSONObject();

        /*
    id_actividad
semana: string
fecha_inicio (obligatorio)
fecha_entrega
entregas:string
compromisos (obligatorio):string
cumplido (si - 1, no - 0), por defecto no
id_seguimiento
visibilidad (si - 1 o no - 0) - para hacer visible al coordinador, por defecto no

     */

    @PreAuthorize("hasAuthority('Tutor')")
    @PostMapping(path="/crear", produces = "application/json")
    public @ResponseBody String crearNuevaActividad(
            @RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        ActividadValidacion actividadValidacion = new ActividadValidacion();
        String validacion = "-";// actividadValidacion.actividadValidacionCrear(body);
        if (!validacion.equals("")) {

            String semana = body.get("semana");
            String fecha_inicio = body.get("fecha_inicio");
            String fecha_entrega = body.get("fecha_entrega");
            String entregas = body.get("entregas");
            String compromisos = body.get("compromisos");

            String cumplido = body.get("cumplido");
            String id_seguimiento = body.get("id_seguimiento");
            String visibilidad = body.get("visibilidad");


            this.rta = new JSONObject();
            Actividad actividadNuevo = new Actividad();
            Seguimiento seguimiento = seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).get();

            // 0 - no, 1 - si
            actividadNuevo.setCumplida(0);
            actividadNuevo.setVisible(0);
            actividadNuevo.setSemana(semana);
            try {
                actividadNuevo.setFechaInicio(new SimpleDateFormat("dd/MM/yyyy").parse(fecha_inicio));
                actividadNuevo.setFechaEntrega(new SimpleDateFormat("dd/MM/yyyy").parse(fecha_entrega));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            actividadNuevo.setCompromisos(compromisos);
            actividadNuevo.setEntregas(entregas);
            actividadNuevo.setSeguimiento(seguimiento);


            Actividad guardada = this.actividadRepo.save(actividadNuevo);

            if (guardada != null){

                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Actividad se creo correctamente");

            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo creando actividad");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", validacion);
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Tutor')")
    @PutMapping(path="/editar", produces = "application/json")
    public @ResponseBody String editarActividad(
            @RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        ActividadValidacion actividadValidacion = new ActividadValidacion();
        String validacion =  "-";//  actividadValidacion.actividadValidacionEditar(body);
        if (!validacion.equals("")) {

            String id_actividad = body.get("id_actividad");
            String semana = body.get("semana");
            String fecha_inicio = body.get("fecha_inicio");
            String fecha_entrega = body.get("fecha_entrega");
            String entregas = body.get("entregas");
            String compromisos = body.get("compromisos");

            String cumplido = body.get("cumplido");
            String id_seguimiento = body.get("id_seguimiento");
            String visibilidad = body.get("visibilidad");


            this.rta = new JSONObject();
            Actividad actividadNuevo = actividadRepo.findById(Integer.parseInt(id_actividad)).get();
            Seguimiento seguimiento = seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).get();

            // 0 - no, 1 - si
            actividadNuevo.setCumplida(0);
            actividadNuevo.setVisible(0);
            actividadNuevo.setSemana(semana);
            try {
                actividadNuevo.setFechaInicio(new SimpleDateFormat("dd/MM/yyyy").parse(fecha_inicio));
                actividadNuevo.setFechaEntrega(new SimpleDateFormat("dd/MM/yyyy").parse(fecha_entrega));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            actividadNuevo.setCompromisos(compromisos);
            actividadNuevo.setEntregas(entregas);
            actividadNuevo.setSeguimiento(seguimiento);

            Actividad guardada = this.actividadRepo.save(actividadNuevo);


            if (guardada != null){

                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Actividad se edito correctamente");

            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo editando actividad");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", validacion);
        }

        return rta.toString();
    }

    @DeleteMapping(path="/eliminar/{id_actividad}", produces = "application/json")
    public @ResponseBody String eliminarActividad(
            @PathVariable String id_actividad) {
        this.rta = new JSONObject();
        if (this.actividadRepo.findById(Integer.parseInt(id_actividad)).isPresent()) {

            // Actividad actividad = this.actividadRepo.findById(Integer.parseInt(id_actividad)).get();

            try {
                this.actividadRepo.deleteById(Integer.parseInt(id_actividad));
                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Actividad se elimino correctamente");
            } catch (Exception e) {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo eliminando actividad");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "Actividad no se puede eliminar porque no existe");
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Tutor')")
    @GetMapping(path="/listarPorSeguimiento/{id_seguimiento}", produces = "application/json")
    public @ResponseBody String listarPorSeguimiento(@PathVariable String id_seguimiento) {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        List<Actividad> actividades = this.actividadRepo.listarActividadPorSeguimiento(Integer.parseInt(id_seguimiento));
        if (actividades.size() > 0) {

            rta.put("estado", "exito");
            rta.put("data", actividades);
            rta.put("mensaje", "Lista de actividades por seguimiento");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen activades");
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/listarPorVisibilidad/{id_seguimiento}", produces = "application/json")
    public @ResponseBody String listarPorVisibilidad(@PathVariable String id_seguimiento) {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        System.out.println("entra a cargar por visibilidad");
        List<Actividad> actividades = this.actividadRepo.listarActividadPorSeguimientoVisible(Integer.parseInt(id_seguimiento));
        if (actividades.size() > 0) {

            rta.put("estado", "exito");
            rta.put("data", actividades);
            rta.put("mensaje", "Lista de actividades visibles");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen actividades visibles");
        }

        System.out.println("responde a coordinador");

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Tutor')")
    @PostMapping(path="/visibilidad", produces = "application/json")
    public @ResponseBody String cambiarVisibilidad(@RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();

        String id_actividad = body.get("id_actividad");
        String visibilidad = body.get("visibilidad");

        if (this.actividadRepo.findById(Integer.parseInt(id_actividad)).isPresent()) {

            Actividad actividad = this.actividadRepo.findById(Integer.parseInt(id_actividad)).get();
            actividad.setVisible(Integer.parseInt(visibilidad));

            if (actividadRepo.save(actividad)!=null){
                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Se cambio visibilidad de la actividad");
            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "No se cambio visibilidad de actividad");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen actividades visibles");
        }

        return rta.toString();
    }
}
