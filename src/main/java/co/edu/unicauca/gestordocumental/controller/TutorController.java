package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.TipoUsuario;
import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.model.Usuario;
import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import co.edu.unicauca.gestordocumental.repo.TipoUsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.PersonaRepo;
import co.edu.unicauca.gestordocumental.repo.seguimiento.TipoTutorRepo;
import co.edu.unicauca.gestordocumental.validador.seguimiento.PersonaValidacion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path="/tutor")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TutorController {
        
    @Autowired
    private TutorRepo tutorRepo;

    @Autowired
    private PersonaRepo personaRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private TipoUsuarioRepo tipoUsuarioRepo;

    @Autowired
    private TipoTutorRepo tipoTutorRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    JSONObject rta = new JSONObject();

    
    /**
     * Consulta todos los tutor registrados en el sistema de gestión
     * @return todos los tutores registrados
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/todo")
    public @ResponseBody List<String> getTodosTutores() {
        System.out.println("entra aqui para cargar tutores --- ");
        List<String> iterable = tutorRepo.listarTodos();
        System.out.println(" aqui ---- " + iterable.get(0));
        return iterable;
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

    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/crear", produces = "application/json")
    public @ResponseBody String crearNuevoTutor(
            @RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        PersonaValidacion tutorV = new PersonaValidacion();
        String validacion = tutorV.TutorValidacionCrear(body);
        if (!validacion.equals("")) {
            String identificacion = body.get("identificacion");
            String nombres = body.get("nombres");
            String apellidos = body.get("apellidos");
            String correo = body.get("correo");
            String telefono = body.get("telefono");
            String departamento = body.get("departamento");
            String grupo_investigacion = body.get("grupo_investigacion");
            String id_tipo_tutor = body.get("id_tipo_tutor");
            String universidad = body.get("universidad");

            this.rta = new JSONObject();
            Tutor tutorNuevo = new Tutor();
            Persona personaNueva = new Persona();
            Usuario usuarioNuevo = new Usuario();

            personaNueva.setNombres(nombres);
            personaNueva.setApellidos(apellidos);
            personaNueva.setCorreo(correo);
            // 3 - equivale a tutor
            Set<TipoUsuario> tipo = new HashSet<>();
            TipoUsuario tut = tipoUsuarioRepo.findById(3).get();
            tipo.add(tut);
            // personaNueva.setTiposUsuario(tipo);

            Persona guardada = this.personaRepo.save(personaNueva);

            if (guardada != null){
                usuarioNuevo.setPersona(guardada);
                usuarioNuevo.setEstado(true);
                usuarioNuevo.setUsuario(correo);
                usuarioNuevo.setContrasena(bCryptPasswordEncoder.encode(identificacion));
                usuarioNuevo.setTiposUsuario(tipo);

                Usuario us = usuarioRepo.save(usuarioNuevo);

                if (us != null) {
                    tutorNuevo.setTipoTutor(tipoTutorRepo.findById(Integer.parseInt(id_tipo_tutor)).get());
                    tutorNuevo.setPersona(guardada);
                    tutorNuevo.setGrupoInvestigacion(grupo_investigacion);
                    tutorNuevo.setApellido(apellidos);
                    tutorNuevo.setCorreo(correo);
                    tutorNuevo.setNombre(nombres);
                    tutorNuevo.setUniversidad(universidad);
                    tutorNuevo.setTelefono((telefono==null)?0:Integer.parseInt(telefono));
                    tutorNuevo.setDepartamento(departamento);
                    if (tutorRepo.save(tutorNuevo) != null){
                        rta.put("estado", "exito");
                        rta.put("data", "");
                        rta.put("mensaje", "Tutor se creo correctamente");
                    }else {
                        rta.put("estado", "fallo");
                        rta.put("data", "");
                        rta.put("mensaje", "Fallo creando tutor");
                    }
                } else {
                    rta.put("estado", "fallo");
                    rta.put("data", "");
                    rta.put("mensaje", "Fallo creando usuario");
                }
            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo creando persona");
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
    public @ResponseBody String editarTutor(
            @RequestBody Map<String, String> body) {
        // return tutorRepo.findAllByNombre(nombre);
        PersonaValidacion tutorV = new PersonaValidacion();
        String validacion = tutorV.TutorValidacionEditar(body);
        if (!validacion.equals("")) {
            String id = body.get("id_tutor");
            String identificacion = body.get("identificacion");
            String nombres = body.get("nombres");
            String apellidos = body.get("apellidos");
            String correo = body.get("correo");
            String telefono = body.get("telefono");
            String departamento = body.get("departamento");
            String grupo_investigacion = body.get("grupo_investigacion");
            String id_tipo_tutor = body.get("id_tipo_tutor");
            String universidad = body.get("universidad");

            this.rta = new JSONObject();
            Tutor tutorNuevo = this.tutorRepo.findById(Integer.parseInt(id)).get();
            Persona personaNueva = tutorNuevo.getPersona();
            Usuario usuarioNuevo = this.usuarioRepo.findByUsuario(personaNueva.getCorreo());

            personaNueva.setNombres(nombres);
            personaNueva.setApellidos(apellidos);
            personaNueva.setCorreo(correo);
            // 3 - equivale a tutor
            Set<TipoUsuario> tipo = new HashSet<>();
            TipoUsuario tut = tipoUsuarioRepo.findById(3).get();
            tipo.add(tut);
            // personaNueva.setTiposUsuario(tipo);

            Persona guardada = this.personaRepo.save(personaNueva);

            if (guardada != null){
                usuarioNuevo.setPersona(guardada);
                usuarioNuevo.setEstado(true);
                usuarioNuevo.setUsuario(correo);
                usuarioNuevo.setContrasena(bCryptPasswordEncoder.encode(identificacion));
                usuarioNuevo.setTiposUsuario(tipo);

                Usuario us = usuarioRepo.save(usuarioNuevo);

                if (us != null) {
                    tutorNuevo.setTipoTutor(tipoTutorRepo.findById(Integer.parseInt(id_tipo_tutor)).get());
                    tutorNuevo.setPersona(guardada);
                    tutorNuevo.setGrupoInvestigacion(grupo_investigacion);
                    tutorNuevo.setApellido(apellidos);
                    tutorNuevo.setCorreo(correo);
                    tutorNuevo.setNombre(nombres);
                    tutorNuevo.setUniversidad(universidad);
                    tutorNuevo.setTelefono((telefono==null)?0:Integer.parseInt(telefono));
                    tutorNuevo.setDepartamento(departamento);
                    if (tutorRepo.save(tutorNuevo) != null){
                        rta.put("estado", "exito");
                        rta.put("data", "");
                        rta.put("mensaje", "Tutor se actualizo correctamente");
                    }else {
                        rta.put("estado", "fallo");
                        rta.put("data", "");
                        rta.put("mensaje", "Fallo actualizando tutor");
                    }
                } else {
                    rta.put("estado", "fallo");
                    rta.put("data", "");
                    rta.put("mensaje", "Fallo actualizando usuario");
                }
            } else {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo actualizando persona");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", validacion);
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Coordinador')")
    @DeleteMapping(path="/eliminar/{id_tutor}", produces = "application/json")
    public @ResponseBody String eliminarTutor(
            @PathVariable String id_tutor) {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        if (this.tutorRepo.findById(Integer.parseInt(id_tutor)).isPresent()) {


            Tutor tutorNuevo = this.tutorRepo.findById(Integer.parseInt(id_tutor)).get();
            Persona personaNueva = tutorNuevo.getPersona();
            Usuario usuarioNuevo = this.usuarioRepo.findByUsuario(personaNueva.getCorreo());

            try {
                this.usuarioRepo.deleteById(usuarioNuevo.getId());
                this.tutorRepo.deleteById(tutorNuevo.getId_tutor());
                this.personaRepo.deleteById(personaNueva.getIdPersona());
                rta.put("estado", "exito");
                rta.put("data", "");
                rta.put("mensaje", "Tutor se elimino correctamente");
            } catch (Exception e) {
                rta.put("estado", "fallo");
                rta.put("data", "");
                rta.put("mensaje", "Fallo eliminando tutor");
            }

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "Tutor no se puede eliminar poque no existe");
        }

        return rta.toString();
    }

    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/listar", produces = "application/json")
    public @ResponseBody String listarTutor() {
        // return tutorRepo.findAllByNombre(nombre);
        this.rta = new JSONObject();
        List<Tutor> tutores = (List<Tutor>) this.tutorRepo.findAll();
        if (tutores.size() > 0) {

                rta.put("estado", "exito");
                rta.put("data", tutores);
                rta.put("mensaje", "Lista de tutores");

        } else {
            rta.put("estado", "fallo");
            rta.put("data", "");
            rta.put("mensaje", "No existen tutores");
        }

        return rta.toString();
    }
}
