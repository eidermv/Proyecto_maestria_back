package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.OpenKM;
import co.edu.unicauca.gestordocumental.model.Pasantia;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.PasantiaRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PasantiaValidador;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.bean.form.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path="/pasantia")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PasantiaController {
    
    /**
     * Valida los datos de la pasantía
     */
    private final PasantiaValidador pasantiaValidador;
    
    /**
     * Envía respuesta al cliente
     */
    private final Respuesta res;
    
    /**
     * Instancia para conectarse con OpenKM
     */
    private final OpenKM openKM;
    
    @Autowired
    private PasantiaRepo pasantiaRepo;
    
    @Autowired
    private EstudianteRepo estudianteRepo;
    
    public PasantiaController() {
        res = new Respuesta();
        pasantiaValidador = new PasantiaValidador(res);
        openKM = OpenKM.getInstance();
    }
    
    /**
     * Registra una nueva pasantía a un estudiante
     * @param datos datos de la pasantía
     * @param informe el archivo con el informe dado por el estudiante
     * @param certificado el archivo con el certificado firmado por el docente
     */
    @Transactional
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/registrar")
    public @ResponseBody void registrarPasantia(
            @RequestParam("datos") String datos,
            @RequestParam("informe") MultipartFile informe,
            @RequestParam("certificado") MultipartFile certificado) throws Exception {
        
        Map<String, String> body = new ObjectMapper().readValue(datos, Map.class);
        
        /**
         * Se verifica que el estudiante exista
         */
        String codigoEstudiante = body.get("codigoEstudiante");
        if (codigoEstudiante == null || codigoEstudiante.length() == 0) {
            res.badRequest(1, 1);
        }        
        Estudiante estudiante = estudianteRepo.findByCodigo(codigoEstudiante);
        if (estudiante == null) {
            res.badRequest(100, 103);
        }
        
        String extensionInforme = body.get("extensionInforme");
        String extensionCertificado = body.get("extensionCertificado");        
        if ((extensionCertificado == null || extensionCertificado.length() == 0) 
            || (extensionInforme == null || extensionInforme.length() == 0)) {
            res.badRequest(1, 1);
        }
        
        Date fechaInicio = pasantiaValidador.validarFechaInicio(body.get("fechaInicio"), true);
        Date fechaFin = pasantiaValidador.validarFechaFin(body.get("fechaFin"), true);
        pasantiaValidador.validarFechaInicioFechaFin(fechaInicio, fechaFin);
        String tipoPasantia = pasantiaValidador.validarTipoPasantia(body.get("tipoPasantia"), true);
        String institucion = pasantiaValidador.validarInstitucion(body.get("institucion"), true);
        String dependencia = pasantiaValidador.validarDependencia(body.get("dependencia"), true);
        String nombreDependencia = pasantiaValidador.validarNombreDependencia(body.get("nombreDependencia"), true);
        String responsable = pasantiaValidador.validarResponsable(body.get("responsable"), true);       
                
        /**
         * Registra la pasantía
         */
        Pasantia pasantia = new Pasantia();
        pasantia.setFechaInicio(fechaInicio);
        pasantia.setFechaFin(fechaFin);
        pasantia.setDependencia(dependencia);
        pasantia.setEstudiante(estudiante);
        pasantia.setFechaRegistro(new Date());
        pasantia.setNombreDependencia(nombreDependencia);
        pasantia.setTipoPasantia(tipoPasantia);
        pasantia.setInstitucion(institucion);
        pasantia.setResponsable(responsable);
        pasantia.setCreditos(0);
        pasantia.setEstado(Pasantia.ESTADO_POR_VERIFICAR);
        pasantiaRepo.save(pasantia);
        
        String rutaFolder = OpenKM.RUTA_BASE + estudiante.getUsuario().getUsuario() + "/Pasantia/" + pasantia.getId();
        openKM.crearFolder(rutaFolder);        
        rutaFolder += "/";
        openKM.crearDocumento(certificado.getBytes(), rutaFolder, "Informe", extensionInforme);
        openKM.crearDocumento(certificado.getBytes(), rutaFolder, "Certificado", extensionCertificado);
        
        List<FormElement> metadatos = openKM.obtenerMetadatos(rutaFolder + "Informe." + extensionInforme, "okg:pasantia");
        for (FormElement metadato : metadatos) {
            switch(metadato.getName()) {
                case "okp:pasantia.autor":
                    Input inAutor = (Input) metadato;
                    inAutor.setValue(estudiante.getNombres() + " " + estudiante.getApellidos());
                    break;
                case "okp:pasantia.tipoPasantia":
                    Input inTipoPasantia = (Input) metadato;
                    inTipoPasantia.setValue(tipoPasantia);
                    break;
                case "okp:pasantia.fechaInicio":
                    Input inFechaInicio = (Input) metadato;
                    inFechaInicio.setValue(fechaInicio.toString());
                    break;
                case "okp:pasantia.fechaFin":
                    Input inFechaFin = (Input) metadato;
                    inFechaFin.setValue(fechaFin.toString());
                    break;
                case "okp:pasantia.institucion":
                    Input inInstitucion = (Input) metadato;
                    inInstitucion.setValue(institucion);
                    break;
                case "okp:pasantia.dependencia":
                    Input inDependencia = (Input) metadato;
                    inDependencia.setValue(dependencia);
                    break;
                case "okp:pasantia.nombreDependencia":
                    Input inNombreDependencia = (Input) metadato;
                    inNombreDependencia.setValue(nombreDependencia);
                    break;
                case "okp:pasantia.responsable":
                    Input inResponsable = (Input) metadato;
                    inResponsable.setValue(responsable);
                    break;
            }
        }
        
        openKM.asignarMetadatos(rutaFolder + "Certificado." + extensionCertificado, "okg:pasantia", metadatos);
        openKM.asignarMetadatos(rutaFolder + "Informe." + extensionCertificado, "okg:pasantia", metadatos);        
    }
    
    /**
     * Encuentra todas las pasantias asociadas a un estudiante
     * @param codigoEstudiante el código del estudiante que se quiere consultar
     * @return todas las pasantías de ese estudiante 
     */
    @PreAuthorize("hasAuthority('Estudiante')")
    @GetMapping(path="/buscar/codigoEstudiante/{codigoEstudiante}")
    public @ResponseBody Iterable<Pasantia> getPasantiaByCodigoEstudiante(
            @PathVariable String codigoEstudiante) throws Exception {
               
        Estudiante estudiante = estudianteRepo.findByCodigo(codigoEstudiante);
        if (estudiante == null) {
            res.badRequest(100, 103);
        }
        return pasantiaRepo.findByIdEstudiante((Integer) estudiante.getId());
    }
    
    /**
     * Busca todos los registros de pasantía
     * @return los registros de pasantía
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/todo")
    public @ResponseBody Iterable<Pasantia> getPasantia () {
        
        return pasantiaRepo.findAll();
    }
    
    /**
     * Descarga un archivo de la pasantía con el id de pasantía dado
     * @param idPasantia el id de la pasantía que se quiere descargar
     * @param archivo el nombre del archivo que se quiere descargar de la pasantía
     * @return el archivo buscado de la pasantía
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/descargar/{idPasantia}/{archivo}")
    public ResponseEntity<InputStreamResource> getArchivoEvento(
            @PathVariable int idPasantia,
            @PathVariable String archivo) throws Exception { 
                
        Pasantia pasantia = pasantiaRepo.findByIdPasantia(idPasantia);
        if (pasantia == null) {
            res.badRequest(1007, 103);
        }
        String rutaFolder = OpenKM.RUTA_BASE + pasantia.getEstudiante().getUsuario().getUsuario()
                + "/Pasantia/" + pasantia.getId();
        
        String nombreReal = openKM.getNombreRealArchivo(rutaFolder, archivo);
        if (nombreReal == null) {
            res.badRequest(1008, 103);
        }
        MediaType mediaType = MediaType.parseMediaType(openKM.getMimeTypeArchivo(rutaFolder, archivo));        
        InputStreamResource isr = new InputStreamResource(openKM.getArchivo(rutaFolder, archivo));
                
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreReal + "\"")
                .contentType(mediaType)
                .body(isr);
    }
    
    /**
     * Actualiza el estado de una pasantía
     * @param body los datos de la pasantía que se quiere actualizar     
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/actualizar/estado")
    public @ResponseBody void actualizarEstadoPasantia (
            @RequestBody Map<String, String> body) throws Exception {
        
        int idPasantia = pasantiaValidador.validarIdPasantia(body.get("idPasantia"), true);
        int creditos = pasantiaValidador.validarCreditos(body.get("creditos"), true);
        String estado = pasantiaValidador.validarEstado(body.get("estado"), true);
        String observacion = pasantiaValidador.validarObservacion(body.get("observacion"), false);
        
        Optional<Pasantia> opPasantia = pasantiaRepo.findById(idPasantia);
        if(!opPasantia.isPresent()) {
            res.badRequest(1007, 103);
        }
        Pasantia pasantia = opPasantia.get();
        if(!estado.equals(Pasantia.ESTADO_APROBADO)) {
            creditos = 0;
        } else {
            if (creditos == 0) {
                res.badRequest(1009, 104);                
            }
        }
        
        pasantia.setEstado(estado);
        pasantia.setCreditos(creditos);
        pasantia.setObservacion(observacion);
        pasantiaRepo.save(pasantia);        
    }
    
    /**
     * Elimina una pasantía
     * @param idPasantia la pasantía que se quiere eliminar     
     */
    @PreAuthorize("hasAuthority('Estudiante')")
    @DeleteMapping(path="/eliminar/idPasantia/{idPasantia}")
    public @ResponseBody void eliminarPasantia (
            @PathVariable int idPasantia) throws Exception {
                
        Pasantia pasantia = pasantiaRepo.findById(idPasantia).get();
        if (pasantia == null) {
            res.badRequest(1007, 103);
        }
        
        String estado = pasantia.getEstado();
        if (!estado.equals(Pasantia.ESTADO_POR_VERIFICAR)) {
            res.badRequest(1010, 104);
        }
        
        String carpetaOpenKMEliminar = OpenKM.RUTA_BASE + pasantia.getEstudiante().getUsuario().getUsuario() + 
                "/Pasantia/" + pasantia.getId();
        
        pasantiaRepo.delete(pasantia);        
        openKM.eliminarFolder(carpetaOpenKMEliminar);
    }
}
