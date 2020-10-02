package co.edu.unicauca.gestordocumental.controller.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.Actividad;
import co.edu.unicauca.gestordocumental.model.seguimiento.EstadoProyecto;
import co.edu.unicauca.gestordocumental.repo.seguimiento.EstadoProyectoRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path="/estado_proyecto")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EstadoProyectoController {

    @Autowired
    private EstadoProyectoRepo estadoProyectoRepo;

    JSONObject rta = new JSONObject();

    @PreAuthorize("hasAnyAuthority('Cordinador', 'Tutor')")
    @GetMapping(path="/estados", produces = "application/json")
    public String listar() {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        List<EstadoProyecto> estados = this.estadoProyectoRepo.findAll();
        if (estados.size() > 0) {

            rta.put("estado", "exito");
            rta.put("data", estados);
            rta.put("mensaje", "Lista de estados proyecto");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen estados proyectos");
        }

        return rta.toString();
    }
}
