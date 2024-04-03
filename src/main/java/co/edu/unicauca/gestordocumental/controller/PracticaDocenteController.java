package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.OpenKM;
import co.edu.unicauca.gestordocumental.model.PracticaDocente;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.PracticaDocenteRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PracticaDocenteValidador;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.bean.form.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping(path="/practicaDocente")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PracticaDocenteController {
    
    /**
     * Valida los datos de la práctica docente
     */
    private final PracticaDocenteValidador practicaDocenteValidador;
    
    /**
     * Envía respuesta al cliente
     */
    private final Respuesta res;
    
    /**
     * Instancia para conectarse con OpenKM
     */
    private final OpenKM openKM;
    
    @Autowired
    private PracticaDocenteRepo practicaDocenteRepo;
    
    @Autowired
    private EstudianteRepo estudianteRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;
    
    public PracticaDocenteController() {
        res = new Respuesta();
        practicaDocenteValidador = new PracticaDocenteValidador(res);
        openKM = OpenKM.getInstance();
    }
    
    /**
     * Registra una nueva práctica docente a un estudiante
     * @param datos los datos de la práctica docente
     * @param certificado el archivo con el certificado firmado por el docente
     */
    @Transactional
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/registrar")
    public @ResponseBody void registrarPracticaDocente(
            @RequestParam("datos") String datos,
            @RequestParam("certificado") MultipartFile certificado) throws Exception {
        
        Map<String, String> body = new ObjectMapper().readValue(datos, Map.class);
        
        /**
         * Se verifica que el estudiante exista
         */
        String codigoEstudiante = body.get("codigoEstudiante");
        if (codigoEstudiante == null) {
            res.badRequest(1, 1);
        }        
        Estudiante estudiante = estudianteRepo.findByCodigo(codigoEstudiante);
        if (estudiante == null) {
            res.badRequest(100, 103);
        }
        
        String extensionCertificado = body.get("extensionCertificado");
        if (extensionCertificado == null || extensionCertificado.length() == 0) {
            res.badRequest(1, 1);
        }
        
        String tipoPracticaDocente = practicaDocenteValidador.validarTipoPracticaDocente(body.get("tipoPracticaDocente"), true);
        Date fechaInicio = practicaDocenteValidador.validarFechaInicio(body.get("fechaInicio"), false);
        Date fechaFin = practicaDocenteValidador.validarFechaFin(body.get("fechaFin"), false);
        int horas = 0;
        if ((fechaInicio != null) && (fechaFin != null)) {
            practicaDocenteValidador.validarFechaInicioFechaFin(fechaInicio, fechaFin);
        }
                
        /**
         * Crear práctica docente y registrarla en BD
         */
        PracticaDocente practicaDocente = new PracticaDocente();
        practicaDocente.setEstudiante(estudiante);
        practicaDocente.setFechaFin(fechaFin);
        practicaDocente.setFechaInicio(fechaInicio);
        practicaDocente.setFechaRegistro(new Date());
        practicaDocente.setEstado(PracticaDocente.ESTADO_POR_VERIFICAR);        
        practicaDocente.setHoras(horas);
        practicaDocente.setCreditos(0);
        practicaDocente.setTipoPracticaDocente(tipoPracticaDocente);
        practicaDocente = practicaDocenteRepo.save(practicaDocente);
        
        String rutaFolder = OpenKM.RUTA_BASE + usuarioRepo.usuarioPorIdEst(estudiante.getId()) + "/PracticaDocente/" + practicaDocente.getId();
        openKM.crearFolder(rutaFolder);        
        rutaFolder += "/";
        openKM.crearDocumento(certificado.getBytes(), rutaFolder, "Certificado", extensionCertificado);
        
        List<FormElement> metadatos = openKM.obtenerMetadatos(rutaFolder + "Certificado." + extensionCertificado, "okg:practicaDocente");
        for (FormElement metadato : metadatos) {
            switch(metadato.getName()) {
                case "okp:practicaDocente.autor":
                    Input inAutor = (Input) metadato;
                    inAutor.setValue(estudiante.getNombres() + " " + estudiante.getApellidos());
                    break;
                case "okp:practicaDocente.tipoPracticaDocente":
                    Input inTipoPracticaDocente = (Input) metadato;
                    inTipoPracticaDocente.setValue(tipoPracticaDocente);
                    break;
                case "okp:practicaDocente.fechaInicio":
                    if (fechaInicio != null) {
                        Input inFechaInicio = (Input) metadato;
                        inFechaInicio.setValue(fechaInicio.toString());
                    }
                    break;
                case "okp:practicaDocente.fechaFin":
                    if (fechaFin != null) {
                        Input inFechaFin = (Input) metadato;
                        inFechaFin.setValue(fechaFin.toString());
                    }
                    break;
            }
        }
        
        openKM.asignarMetadatos(rutaFolder + "Certificado." + extensionCertificado, "okg:practicaDocente", metadatos);
    }
        
    /**
     * Consulta los créditos y las horas de un estudiante en su práctica docente
     * @param codigoEstudiante código del estudiante
     * @return horas y créditos del estudiante en todas sus prácticas docentes
     */
    @PreAuthorize("hasAuthority('Coordinador') or hasAuthority('Estudiante')")
    @GetMapping(path="/consultar/creditosyhoras/codigoEstudiante/{codigoEstudiante}")
    public ResponseEntity<HashMap> getCreditosYHorasPracticaDocente (
            @PathVariable String codigoEstudiante) throws Exception {
        
        HashMap<String, Float> creditosYHoras = new HashMap<>();
        ArrayList<PracticaDocente> practicaDocenteEstudiante 
                = (ArrayList<PracticaDocente>) getPracticaDocenteByCodigoEstudiante(codigoEstudiante);
        int horas = 0;
        
        for (PracticaDocente pc : practicaDocenteEstudiante) {
            horas += pc.getHoras();
        }
        
        float creditos = horas / 48f;
        String creditosFormateados = String.format("%.2f", creditos).replace(",", ".");
        creditosYHoras.put("horas", (float)horas);
        creditosYHoras.put("creditos", Float.parseFloat(creditosFormateados));
        return new ResponseEntity<>(creditosYHoras, HttpStatus.OK);
    }
    
    /**
     * Obtiene los registros de práctica docente asociados a un estudiante
     * @param codigoEstudiante estudiante que se quiere consultar
     * @return todos los registros de práctica docente asociados al estudiante
     */
    @PreAuthorize("hasAuthority('Estudiante')")
    @GetMapping(path="/buscar/codigoEstudiante/{codigoEstudiante}")
    public @ResponseBody Iterable<PracticaDocente> getPracticaDocenteByCodigoEstudiante(
            @PathVariable String codigoEstudiante) throws Exception {
               
        Estudiante estudiante = estudianteRepo.findByCodigo(codigoEstudiante);
        if (estudiante == null) {
            res.badRequest(100, 103);
        }
        return practicaDocenteRepo.findByIdEstudiante((Integer) estudiante.getId());
    }
    
    /**
     * Busca todos los registros de práctica docente
     * @return los registros de práctica docente
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/todo")
    public @ResponseBody Iterable<PracticaDocente> getPracticaDocente () {
        
        return practicaDocenteRepo.findAll();
    }
    
    /**
     * Descarga un archivo de la práctica docente con el id de práctica docente dado
     * @param idPracticaDocente el id de la práctica docente que se quiere descargar
     * @param archivo el nombre del archivo que se quiere descargar de la práctica docente
     * @return el archivo buscado de la pasantía
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/descargar/{idPracticaDocente}/{archivo}")
    public ResponseEntity<InputStreamResource> getArchivoEvento(
            @PathVariable int idPracticaDocente,
            @PathVariable String archivo) throws Exception { 
                
        PracticaDocente practicaDocente = practicaDocenteRepo.findByIdPracticaDocente(idPracticaDocente);
        if (practicaDocente == null) {
            res.badRequest(903, 103);
        }
        String rutaFolder = OpenKM.RUTA_BASE + usuarioRepo.usuarioPorIdEst(practicaDocente.getEstudiante().getId())
                + "/PracticaDocente/" + practicaDocente.getId();
        
        String nombreReal = openKM.getNombreRealArchivo(rutaFolder, archivo);
        if (nombreReal == null) {
            res.badRequest(904, 103);
        }
        MediaType mediaType = MediaType.parseMediaType(openKM.getMimeTypeArchivo(rutaFolder, archivo));        
        InputStreamResource isr = new InputStreamResource(openKM.getArchivo(rutaFolder, archivo));
                
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreReal + "\"")
                .contentType(mediaType)
                .body(isr);
    }
    
    /**
     * Actualiza el estado de una práctica docente
     * @param body los datos de la práctica docente que se quiere actualizar     
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/actualizar/estado")
    public @ResponseBody void actualizarEstadoPracticaDocente (
            @RequestBody Map<String, String> body) throws Exception {
        
        int idPracticaDocente = practicaDocenteValidador.validarIdPracticaDocente(body.get("idPracticaDocente"), true);
        int horas = practicaDocenteValidador.validarHoras(body.get("horas"), true);
        String estado = practicaDocenteValidador.validarEstado(body.get("estado"), true);
        String observacion = practicaDocenteValidador.validarObservacion(body.get("observacion"), false);
        
        Optional<PracticaDocente> opPracticaDocente = practicaDocenteRepo.findById(idPracticaDocente);
        if(!opPracticaDocente.isPresent()) {
            res.badRequest(905, 103);
        }
        PracticaDocente practicaDocente = opPracticaDocente.get();
        if(!estado.equals(PracticaDocente.ESTADO_APROBADO)) {
            horas = 0;
        } else {
            if (horas == 0) {
                res.badRequest(906, 104);                
            }
        }
        
        practicaDocente.setEstado(estado);
        practicaDocente.setHoras(horas);
        practicaDocente.setObservacion(observacion);
        practicaDocenteRepo.save(practicaDocente);    
    }
    
    /**
     * Elimina una practica docente
     * @param idPracticaDocente la práctica docente que se quiere eliminar     
     */
    @PreAuthorize("hasAuthority('Estudiante')")
    @DeleteMapping(path="/eliminar/idPracticaDocente/{idPracticaDocente}")
    public @ResponseBody void eliminarPasantia (
            @PathVariable int idPracticaDocente) throws Exception {
                
        PracticaDocente practicaDocente = practicaDocenteRepo.findById(idPracticaDocente).get();
        if (practicaDocente == null) {
            res.badRequest(905, 103);
        }
        
        String estado = practicaDocente.getEstado();
        if (!estado.equals(PracticaDocente.ESTADO_POR_VERIFICAR)) {
            res.badRequest(907, 104);
        }
        
        String carpetaOpenKMEliminar = OpenKM.RUTA_BASE + usuarioRepo.usuarioPorIdEst(practicaDocente.getEstudiante().getId()) +
                "/PracticaDocente/" + practicaDocente.getId();
        
        practicaDocenteRepo.delete(practicaDocente);        
        openKM.eliminarFolder(carpetaOpenKMEliminar);
    }
}
