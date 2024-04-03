package co.edu.unicauca.gestordocumental.validador.seguimiento;

import co.edu.unicauca.gestordocumental.repo.seguimiento.ActividadRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.SeguimientoRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
@Service
public class ActividadValidacion {

    @Autowired
    private SeguimientoRepo seguimientoRepo;

    @Autowired
    private ActividadRepo actividadRepo;

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
    public String actividadValidacionCrear(Map<String, String> body) {
        String fecha_inicio = body.get("fecha_inicio");
        String compromisos = body.get("compromisos");
        String id_seguimiento = body.get("id_seguimiento");
        String objetivos = body.get("objetivos");
        String fecha_entrega = body.get("fecha_entrega");

        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(fecha_inicio);
            new SimpleDateFormat("dd/MM/yyyy").parse(fecha_entrega);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Fechas invalidas";
        }

        if (fecha_inicio == null) {
            return "Fecha inicio es obligatorio";
        }

        if (compromisos == null){
            return "Compromisos es obligatorio";
        }

        if (id_seguimiento == null){
            return "Id seguimiento es obligatorio";
        }
        if (seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).isEmpty()) {
            return "Seguimiento no existe";
        }

        return "";
    }

    public String actividadValidacionEditar(Map<String, String> body) {
        String id_actividad = body.get("id_actividad");
        String fecha_inicio = body.get("fecha_inicio");
        String compromisos = body.get("compromisos");
        String id_seguimiento = body.get("id_seguimiento");

        if (actividadRepo.findById(Integer.parseInt(id_actividad)).isEmpty()){
            return "Actividad no existe";
        }

        if (fecha_inicio == null) {
            return "Fecha inicio es obligatorio";
        }

        if (compromisos == null){
            return "Compromisos es obligatorio";
        }

        if (id_seguimiento == null){
            return "Id seguimiento es obligatorio";
        }
        if (seguimientoRepo.findById(Integer.parseInt(id_seguimiento)).isEmpty()) {
            return "Seguimiento no existe";
        }

        return "";
    }
}
