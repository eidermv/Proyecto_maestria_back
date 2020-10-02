package co.edu.unicauca.gestordocumental.controller.seguimiento;

import co.edu.unicauca.gestordocumental.model.seguimiento.TipoSeguimiento;
import co.edu.unicauca.gestordocumental.repo.seguimiento.TipoSeguimientoRepo;
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
@RequestMapping(path="/tipo_seguimiento")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TipoSeguimientoController {

    @Autowired
    private TipoSeguimientoRepo tipoSeguimientoRepo;

    JSONObject rta = new JSONObject();

    @PreAuthorize("hasAnyAuthority('Cordinador', 'Tutor')")
    @GetMapping(path="/tipos", produces = "application/json")
    public String listar() {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        List<TipoSeguimiento> estados = this.tipoSeguimientoRepo.findAll();
        if (estados.size() > 0) {

            rta.put("estado", "exito");
            rta.put("data", estados);
            rta.put("mensaje", "Lista de tipos de seguimiento");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen tipos de seguimientos");
        }

        return rta.toString();
    }
}
