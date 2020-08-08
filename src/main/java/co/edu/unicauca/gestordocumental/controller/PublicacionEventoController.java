package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.OpenKM;
import co.edu.unicauca.gestordocumental.model.Publicacion;
import co.edu.unicauca.gestordocumental.model.PublicacionEvento;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionEventoRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PublicacionEventoValidador;
import co.edu.unicauca.gestordocumental.validador.PublicacionValidador;
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

@Controller
@RequestMapping(path="/publicacion/evento")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PublicacionEventoController {
    
    /**
     * Valida los datos de la publicación
     */
    private final PublicacionValidador publicacionValidador;
    
    /**
     * Valida los datos de la publicacion en el evento
     */
    private final PublicacionEventoValidador publicacionEventoValidador;
    
    /**
     * Envía respuesta al cliente
     */
    private final Respuesta res;
    
    /**
     * Instancia para conectarse con OpenKM
     */
    private final OpenKM openKM;
    
    @Autowired
    private PublicacionRepo publicacionRepo;
    
    @Autowired
    private EstudianteRepo estudianteRepo;
    
    @Autowired
    private PublicacionEventoRepo publicacionEventoRepo;    
    
    public PublicacionEventoController() {
        res = new Respuesta();
        publicacionValidador = new PublicacionValidador(res);
        publicacionEventoValidador = new PublicacionEventoValidador(res);
        openKM = OpenKM.getInstance();       
    }
    
    @Transactional
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/registrar")
    public @ResponseBody void crearPublicacionRevista(
            @RequestParam("datos") String datos,
            @RequestParam("indice") MultipartFile indice,
            @RequestParam("ponencia") MultipartFile ponencia,
            @RequestParam("certificadoEvento") MultipartFile certificadoEvento) throws Exception { 
        
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
        
        String extensionIndice = body.get("extensionIndice");
        String extensionPonencia = body.get("extensionPonencia");
        String extensionCertificadoEvento = body.get("extensionCertificadoEvento");
        if ((extensionIndice == null || extensionIndice.length() == 0) 
            || (extensionPonencia == null || extensionPonencia.length() == 0) 
            || (extensionCertificadoEvento == null || extensionCertificadoEvento.length() == 0)) {
            res.badRequest(1, 1);
        }
        
        String autor = publicacionValidador.validarAutor(body.get("autor"), true);  
        String autoresSecundarios = publicacionValidador.validarAutoresSecundarios(body.get("autoresSecundarios"), false);
        Date fechaAceptacion = publicacionValidador.validarFechaAceptacion(body.get("fechaAceptacion"), true);
        Date fechaPublicacion = publicacionValidador.validarFechaPublicacion(body.get("fechaPublicacion"), false);
        publicacionValidador.validarFechaAceptacionFechaPublicacion(fechaAceptacion, fechaPublicacion);
        String doi = publicacionEventoValidador.validarDoi(body.get("doi"), true);
        Date fechaInicio = publicacionEventoValidador.validarFechaInicio(body.get("fechaInicio"), true);
        Date fechaFin = publicacionEventoValidador.validarFechaFin(body.get("fechaFin"), true);
        publicacionEventoValidador.validarFechaInicioFechaFin(fechaInicio, fechaFin);
        String issn = publicacionEventoValidador.validarIssn(body.get("issn"), true);
        String tituloPonencia = publicacionEventoValidador.validarTituloPonencia(body.get("tituloPonencia"), true);
        String nombreEvento = publicacionEventoValidador.validarNombreEvento(body.get("nombreEvento"), true);
        String tipoEvento = publicacionEventoValidador.validarTipoEvento(body.get("tipoEvento"), true);
        String pais = publicacionEventoValidador.validarPais(body.get("pais"), true);
        String ciudad = publicacionEventoValidador.validarCiudad(body.get("ciudad"), true);
                
        /**
         * Se verifica que no exista el folder de la publicación y se crea
         */
        String rutaFolder = OpenKM.RUTA_BASE + estudiante.getUsuario().getUsuario() + "/Evento/" + tituloPonencia;
        if (openKM.verificarExistenciaRuta(rutaFolder)) {
           res.badRequest(604, 102);
        }
        
        openKM.crearFolder(rutaFolder);        
        rutaFolder += "/";
        openKM.crearDocumento(indice.getBytes(), rutaFolder, "Indice", extensionIndice);
        openKM.crearDocumento(ponencia.getBytes(), rutaFolder, "Ponencia", extensionPonencia);
        openKM.crearDocumento(certificadoEvento.getBytes(), rutaFolder, "CertificadoEvento", extensionCertificadoEvento);
        
        List<FormElement> metadatos = openKM.obtenerMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:evento");
        for (FormElement metadato : metadatos) {
            switch(metadato.getName()) {
                case "okp:evento.autor":
                    Input inAutor = (Input) metadato;
                    inAutor.setValue(autor);
                    break;
                case "okp:evento.autoresSecundarios":
                    Input inAutoresSecundarios = (Input) metadato;
                    inAutoresSecundarios.setValue(autoresSecundarios);
                    break;
                case "okp:evento.fechaAceptacion":
                    Input inFechaAceptacion = (Input) metadato;
                    inFechaAceptacion.setValue(fechaAceptacion.toString());
                    break;
                case "okp:evento.fechaPublicacion":
                    if (fechaPublicacion != null) {
                        Input inFechaPublicacion = (Input) metadato;
                        inFechaPublicacion.setValue(fechaPublicacion.toString());
                    }
                    break;
                case "okp:evento.doi":
                    Input inDoi = (Input) metadato;
                    inDoi.setValue(doi);
                    break;
                case "okp:evento.fechaInicio":
                    Input inFechaInicio = (Input) metadato;
                    inFechaInicio.setValue(fechaInicio.toString());
                    break;
                case "okp:evento.fechaFin":
                    Input inFechaFin = (Input) metadato;
                    inFechaFin.setValue(fechaFin.toString());
                    break;
                case "okp:evento.issn":
                    Input inIssn = (Input) metadato;
                    inIssn.setValue(issn);
                    break;
                case "okp:evento.tituloPonencia":
                    Input inTituloPonencia = (Input) metadato;
                    inTituloPonencia.setValue(tituloPonencia);
                    break;
                case "okp:evento.nombreEvento":
                    Input inNombreEvento = (Input) metadato;
                    inNombreEvento.setValue(nombreEvento);
                    break;
                case "okp:evento.tipoEvento":
                    Input inTipoEvento = (Input) metadato;
                    inTipoEvento.setValue(tipoEvento);
                    break;
                case "okp:evento.pais":
                    Input inPais = (Input) metadato;
                    inPais.setValue(pais);
                    break;
                case "okp:evento.ciudad":
                    Input inCiudad = (Input) metadato;
                    inCiudad.setValue(ciudad);
                    break;
            }
        }
        
        openKM.asignarMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:evento", metadatos);
        openKM.asignarMetadatos(rutaFolder + "Ponencia." + extensionPonencia, "okg:evento", metadatos);
        openKM.asignarMetadatos(rutaFolder + "CertificadoEvento." + extensionCertificadoEvento, "okg:evento", metadatos);
        
        /**
         * Se asigna el estudiante a la publicación y se registra
         */
        Publicacion publicacion = new Publicacion();
        publicacion.setAutor(autor);
        publicacion.setAutoresSecundarios(autoresSecundarios);
        publicacion.setFechaAceptacion(fechaAceptacion);
        publicacion.setFechaPublicacion(fechaPublicacion);
        publicacion.setEstudiante(estudiante);
        publicacion.setTipoDocumento(Publicacion.EVENTO);
        publicacion.setEstado(Publicacion.ESTADO_POR_VERIFICAR);
        publicacion.setCreditos(0);
        publicacion.setFechaRegistro(new Date());
        publicacionRepo.save(publicacion);
        
        /**
         * Se asocia la publicación a la publicación de evento y se registra
         */
        PublicacionEvento publicacionEvento = new PublicacionEvento();
        publicacionEvento.setDoi(doi);
        publicacionEvento.setFechaInicio(fechaInicio);
        publicacionEvento.setFechaFin(fechaFin);
        publicacionEvento.setIssn(issn);
        publicacionEvento.setTituloPonencia(tituloPonencia);
        publicacionEvento.setNombreEvento(nombreEvento);
        publicacionEvento.setTipoEvento(tipoEvento);
        publicacionEvento.setPais(pais);
        publicacionEvento.setCiudad(ciudad);
        publicacionEvento.setPublicacion(publicacion);
        publicacionEventoRepo.save(publicacionEvento);        
    }
    
    /**
     * Consulta una publicación de evento por id de la publicacion
     * @param idPublicacion el id de la publicación que se quiere consultar
     * @return los datos de la publicacion del evento que tienen esa
     * publicación asociada
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/idPublicacion/{idPublicacion}")
    public @ResponseBody PublicacionEvento getPublicacionEventoIdPublicacion(
            @PathVariable int idPublicacion) {            
        
        return publicacionEventoRepo.findByIdPublicacion(idPublicacion);
    }
    
    /**
     * Descarga un archivo de la publicación con el id de publicación dado
     * @param idPublicacion el id de la publicación que se quiere descargar
     * @param archivo el nombre del archivo que se quiere descargar de la publicación
     * @return el archivo buscado de la publicación
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/descargar/{idPublicacion}/{archivo}")
    public ResponseEntity<InputStreamResource> getArchivoEvento(
            @PathVariable int idPublicacion,
            @PathVariable String archivo) throws Exception { 
                
        PublicacionEvento publicacionEvento = publicacionEventoRepo.findByIdPublicacion(idPublicacion);
        if (publicacionEvento == null) {
            res.badRequest(405, 103);
        }
        String rutaFolder = OpenKM.RUTA_BASE + publicacionEvento.getPublicacion().getEstudiante().getUsuario().getUsuario()
                + "/Evento/" + publicacionEvento.getTituloPonencia();
        
        String nombreReal = openKM.getNombreRealArchivo(rutaFolder, archivo);
        if (nombreReal == null) {
            res.badRequest(603, 103);
        }
        MediaType mediaType = MediaType.parseMediaType(openKM.getMimeTypeArchivo(rutaFolder, archivo));        
        InputStreamResource isr = new InputStreamResource(openKM.getArchivo(rutaFolder, archivo));
                
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreReal + "\"")
                .contentType(mediaType)
                .body(isr);
    }
}
