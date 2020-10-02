package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.config.ConstantesSeguridad;
import co.edu.unicauca.gestordocumental.model.*;
import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.TipoUsuarioRepo;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.EstudianteValidador;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path="/estudiante")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EstudianteController {
    
    private final static String ESTUDIANTE_STRING = "Estudiante";
    
    /**
     * Valida los datos del estudiante
     */
    private final EstudianteValidador estudianteValidador;
    
    /**
     * Envía la respuesta al cliente
     */
    private final Respuesta res;
    
    /**
     * Crea la carpeta asociada al estudiante
     */
    private final OpenKM openKM;
    
    @Autowired
    private EstudianteRepo estudianteRepo;
    
    @Autowired
    private TutorRepo tutorRepo;
    
    @Autowired
    private UsuarioRepo usuarioRepo;
    
    @Autowired
    private TipoUsuarioRepo tipoUsuarioRepo;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private TipoUsuario tipoUsuario;
    
    public EstudianteController () {
        res = new Respuesta();
        estudianteValidador = new EstudianteValidador(res);
        openKM = OpenKM.getInstance();
    }

    /**
     * Crea un nuevo estudiante
     * @param body el cuerpo de la solicitud
     * @throws Exception si hay errores en el formato o existencia en
     * los atributos del estudiante
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/crear")
    public @ResponseBody void crearNuevoEstudiante (
            @RequestBody Map<String, String> body) throws Exception {

        String codigo = estudianteValidador.validarCodigo(body.get("codigo"), true);
        String nombres = estudianteValidador.validarNombres(body.get("nombres"), true);
        String apellidos = estudianteValidador.validarApellidos(body.get("apellidos"), true);
        String correo = estudianteValidador.validarCorreo(body.get("correo"), true);
        Integer cohorte = estudianteValidador.validarCohorte(body.get("cohorte"), true);
        Integer semestre = estudianteValidador.validarSemestre(body.get("semestre"), true);
        String pertenece = estudianteValidador.validarPertenece(body.get("pertenece"), true);
        String estado = estudianteValidador.validarEstado(body.get("estado"), true);
        String tutor = body.get("tutor");
        String usuario = correo.split("@")[0].toLowerCase();
        String contrasena = bCryptPasswordEncoder.encode(codigo);
        tipoUsuario = tipoUsuarioRepo.findByNombre(ESTUDIANTE_STRING);

        /*Se verifica que el código no esté registrado*/
        Estudiante esEstudianteRegistrado = estudianteRepo.findByCodigo(codigo);
        if (esEstudianteRegistrado != null) {
            res.badRequest(100, 102);
        }

        /*Se verifica que el correo no esté registrado*/
        esEstudianteRegistrado = estudianteRepo.findByCorreo(correo);
        if (esEstudianteRegistrado != null) {
            res.badRequest(103, 102);
        }

        if(tutor == null) {
            res.badRequest(1, 1);
        }

        /*Se verifica que el tutor esté registrado*/
        Tutor tutorAsignado = tutorRepo.findByNombre(tutor);
        if (tutorAsignado == null) {
            res.badRequest(200, 103);
        }

        /*Se registran las carpetas de ese usuario en OpenKM*/
        String rutaPrincipal = OpenKM.RUTA_BASE + usuario;
        String rutaRevista = rutaPrincipal + "/Revista";
        String rutaEvento = rutaPrincipal + "/Evento";
        String rutaLibro = rutaPrincipal + "/Libro";
        String rutaCapituloLibro = rutaPrincipal + "/CapituloLibro";
        String rutaPracticaDocente = rutaPrincipal + "/PracticaDocente";
        String rutaPasantia = rutaPrincipal + "/Pasantia";
        openKM.crearFolder(rutaPrincipal);
        openKM.crearFolder(rutaRevista);
        openKM.crearFolder(rutaEvento);
        openKM.crearFolder(rutaLibro);
        openKM.crearFolder(rutaCapituloLibro);
        openKM.crearFolder(rutaPracticaDocente);
        openKM.crearFolder(rutaPasantia);

        /*Se registra un nuevo usuario para el estudiante*/
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setEstado(Boolean.TRUE);
        // nuevoUsuario.addTipoUsuario(tipoUsuario);
        usuarioRepo.save(nuevoUsuario);

        Persona persona = new Persona();
        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setCorreo(correo);
        persona.addTipoUsuario(tipoUsuario);

        /*Se registra el estudiante*/
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setCodigo(codigo);
        // nuevoEstudiante.setNombres(nombres);
        // nuevoEstudiante.setApellidos(apellidos);
        // nuevoEstudiante.setCorreo(correo);
        nuevoEstudiante.setCohorte(cohorte);
        nuevoEstudiante.setSemestre(semestre);
        nuevoEstudiante.setEstado(estado);
        nuevoEstudiante.setCreditos(0);
        nuevoEstudiante.setPertenece(pertenece);
        nuevoEstudiante.setTutor(tutorAsignado);
        nuevoEstudiante.setPersona(persona);
        // nuevoEstudiante.setUsuario(nuevoUsuario);
        estudianteRepo.save(nuevoEstudiante);
    }

    /**
     * Consulta todos los estudiantes registrados en el sistema de gestión
     * @return todos los estudiantes registrados
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/todo")
    public @ResponseBody Iterable<Estudiante> getTodosEstudiantes() {
        return estudianteRepo.findAll();
    }

    /**
     * Consulta varios estudiantes por match con el código, los nombres y apellidos
     * @param match la cadena de caracteres por la que se quiere consultar
     * @return todos los datos de los estudiantes que hacen match con la cadena de
     * caracteres
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/match/{match}")
    public @ResponseBody Iterable<Estudiante> getEstudianteByMatch(
            @PathVariable String match) {
        return estudianteRepo.findByMatch(match);
    }

    /**
     * Consulta un estudiante en especifico
     * @param codigo el código del estudiante que se quiere consultar
     * @return todos los datos del estudiante, incluyendo su usuario y contraseña
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/codigo/{codigo}")
    public @ResponseBody Estudiante getEstudianteByCodigo(
            @PathVariable String codigo) {
        return estudianteRepo.findByCodigo(codigo);
    }

    /**
     * Consulta varios estudiantes que tengan un nombre dado
     * @param nombre el nombre que quiere consultar
     * @return todos los datos de los estudiantes que hagan match con el nombre
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/nombre/{nombre}")
    public @ResponseBody Iterable<Estudiante> getEstudianteByNombre(
            @PathVariable String nombre) {
        return estudianteRepo.findByNombre(nombre);
    }

     /**
     * Crea un nuevo estudiante
     * @param body el cuerpo de la solicitud
     * @throws Exception si hay errores en el formato o existencia en
     * los atributos del estudiante
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/actualizar")
    public @ResponseBody void actualizarEstudiante (
            @RequestBody Map<String, String> body) throws Exception {

        String idCadena = body.get("id");
        if (idCadena == null) {
            res.badRequest(1, 1);
        }

        int id = estudianteValidador.validarId(idCadena, true);

        /*Se verifica que ese estudiante esté registrado*/
        Estudiante estudianteRegistrado = estudianteRepo.findById(id);
        if (estudianteRegistrado == null) {
            res.badRequest(100, 103);
        }

        String codigo = estudianteValidador.validarCodigo(body.getOrDefault("codigo", estudianteRegistrado.getCodigo()), false);
        String nombres = estudianteValidador.validarNombres(body.getOrDefault("nombres", estudianteRegistrado.getPersona().getNombres()), false);
        String apellidos = estudianteValidador.validarApellidos(body.getOrDefault("apellidos", estudianteRegistrado.getPersona().getApellidos()), false);
        String correo = estudianteValidador.validarCorreo(body.getOrDefault("correo", estudianteRegistrado.getPersona().getCorreo()), false);
        Integer cohorte = estudianteValidador.validarCohorte(body.getOrDefault("cohorte", "" + estudianteRegistrado.getCohorte()), false);
        Integer semestre = estudianteValidador.validarSemestre(body.getOrDefault("semestre", "" + estudianteRegistrado.getSemestre()), false);
        String estado = estudianteValidador.validarEstado(body.getOrDefault("estado", estudianteRegistrado.getEstado()), false);
        String pertenece = estudianteValidador.validarPertenece(body.getOrDefault("pertenece", estudianteRegistrado.getEstado()), false);
        String tutor = body.getOrDefault("tutor", estudianteRegistrado.getTutor().getNombre());

        /*Se verifica que el nuevo código no exista*/
        Estudiante esEstudianteRegistrado = estudianteRepo.findByCodigo(codigo);
        if ((esEstudianteRegistrado != null) && (esEstudianteRegistrado.getId() != id)) {
            res.badRequest(100, 102);
        }

        /*Se verifica que el nuevo correo no exista*/
        esEstudianteRegistrado = estudianteRepo.findByCorreo(correo);
        if ((esEstudianteRegistrado != null) && (esEstudianteRegistrado.getId() != id)) {
            res.badRequest(103, 102);
        }

        /*Se verifica que el tutor esté registrado*/
        Tutor tutorAsignado = tutorRepo.findByNombre(tutor);
        if (tutorAsignado == null) {
            res.badRequest(200, 103);
        }

        /*Se actualiza el estudiante*/
        estudianteRegistrado.setCodigo(codigo);
        estudianteRegistrado.getPersona().setNombres(nombres);
        estudianteRegistrado.getPersona().setApellidos(apellidos);
        estudianteRegistrado.getPersona().setCorreo(correo);
        estudianteRegistrado.setCohorte(cohorte);
        estudianteRegistrado.setSemestre(semestre);
        estudianteRegistrado.setEstado(estado);
        estudianteRegistrado.setPertenece(pertenece);
        estudianteRegistrado.setTutor(tutorAsignado);
        estudianteRepo.save(estudianteRegistrado);
    }

    /**
     * Crea un nuevo estudiante
     * @param body el cuerpo de la solicitud
     * @throws Exception si hay errores en el formato o existencia en
     * los atributos del estudiante
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/actualizar/contrasena")
    public @ResponseBody void actualizarContrasena (
            @RequestBody Map<String, String> body) throws Exception {        
        
        String idCadena = body.get("id");
        if (idCadena == null) {
            res.badRequest(1, 1);
        }
        
        int id = estudianteValidador.validarId(idCadena, true);
        
        String contrasena = estudianteValidador.validarContrasena(body.get("contrasena"));
        contrasena = bCryptPasswordEncoder.encode(contrasena);
        
        /*Se verifica que ese estudiante esté registrado*/
        Estudiante estudianteRegistrado = estudianteRepo.findById(id);
        if (estudianteRegistrado == null) {
            res.badRequest(100, 103);
        }
        
        Usuario usuario = usuarioRepo.findByIdPersona(estudianteRegistrado.getPersona().getIdPersona());
        usuario.setContrasena(contrasena);
        
        usuarioRepo.save(usuario);
    }
    
}