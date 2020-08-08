package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/tutor")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TutorController {
        
    @Autowired
    private TutorRepo tutorRepo;
    
    /**
     * Consulta todos los tutor registrados en el sistema de gestión
     * @return todos los tutores registrados
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/todo")
    public @ResponseBody Iterable<Tutor> getTodosTutores() {        
        return tutorRepo.findAll();
    }
    
     /**
     * Consulta varios tutores que tengan un nombre dado
     * @param nombre el nombre que quiere consultar
     * @return los nombres de los tutores que hagan match con el nombre dado
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/nombre/{nombre}")
    public @ResponseBody Iterable<Tutor> getTutorByNombre(
            @PathVariable String nombre) {
        return tutorRepo.findAllByNombre(nombre);
    }
}
