package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.OpenKM;
import co.edu.unicauca.gestordocumental.model.Publicacion;
import co.edu.unicauca.gestordocumental.model.PublicacionCapituloLibro;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionCapituloLibroRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionRepo;
import co.edu.unicauca.gestordocumental.repo.UsuarioRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PublicacionCapituloLibroValidador;
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
@RequestMapping(path="/publicacion/capituloLibro")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PublicacionCapituloLibroController {
    
    /**
     * Valida los datos de la publicación
     */
    private final PublicacionValidador publicacionValidador;
    
    /**
     * Valida los datos de la publicación del capítulo del libro
     */
    private final PublicacionCapituloLibroValidador publicacionCapituloLibroValidador;
    
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
    private UsuarioRepo usuarioRepo;
    
    @Autowired
    private PublicacionCapituloLibroRepo publicacionCapituloLibroRepo;
        
    public PublicacionCapituloLibroController() {
        res = new Respuesta();
        publicacionCapituloLibroValidador = new PublicacionCapituloLibroValidador(res); 
        publicacionValidador = new PublicacionValidador(res);
        openKM = OpenKM.getInstance();
    }
    
    @Transactional
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/registrar")
    public @ResponseBody void crearPublicacionRevista(
            @RequestParam("datos") String datos,
            @RequestParam("indice") MultipartFile indice,
            @RequestParam("capituloLibro") MultipartFile capituloLibro,
            @RequestParam("certificadoEditorial") MultipartFile certificadoEditorial) throws Exception { 
        
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
        String extensionCapituloLibro = body.get("extensionCapituloLibro");
        String extensionCertificadoEditorial = body.get("extensionCertificadoEditorial");
        if ((extensionIndice == null || extensionIndice.length() == 0) 
            || (extensionCapituloLibro == null || extensionCapituloLibro.length() == 0) 
            || (extensionCertificadoEditorial == null || extensionCertificadoEditorial.length() == 0)) {
            res.badRequest(1, 1);
        }
        
        String autor = publicacionValidador.validarAutor(body.get("autor"), true);  
        String autoresSecundarios = publicacionValidador.validarAutoresSecundarios(body.get("autoresSecundarios"), false);
        Date fechaAceptacion = publicacionValidador.validarFechaAceptacion(body.get("fechaAceptacion"), true);
        Date fechaPublicacion = publicacionValidador.validarFechaPublicacion(body.get("fechaPublicacion"), false); 
        publicacionValidador.validarFechaAceptacionFechaPublicacion(fechaAceptacion, fechaPublicacion);
        String isbn = publicacionCapituloLibroValidador.validarIsbn(body.get("isbn"), false);
        String tituloCapituloLibro = publicacionCapituloLibroValidador.validarTituloCapituloLibro(body.get("tituloCapituloLibro"), true);
        String tituloLibro = publicacionCapituloLibroValidador.validarTituloLibro(body.get("tituloLibro"), true);
        String editorial = publicacionCapituloLibroValidador.validarEditorial(body.get("editorial"), true);
        
        /**
         * Se verifica que no exista el folder de la publicación y se crea
         */
        String rutaFolder = OpenKM.RUTA_BASE + usuarioRepo.usuarioPorIdEst(estudiante.getId()) + "/CapituloLibro/" + tituloCapituloLibro;
        if (openKM.verificarExistenciaRuta(rutaFolder)) {
           res.badRequest(801, 102);
        }
        
        openKM.crearFolder(rutaFolder);
        rutaFolder += "/";
        openKM.crearDocumento(indice.getBytes(), rutaFolder, "Indice", extensionIndice);
        openKM.crearDocumento(capituloLibro.getBytes(), rutaFolder, "CapituloLibro", extensionCapituloLibro);
        openKM.crearDocumento(certificadoEditorial.getBytes(), rutaFolder, "CertificadoEditorial", extensionCertificadoEditorial);
        
        List<FormElement> metadatos = openKM.obtenerMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:capituloLibro");
        for (FormElement metadato : metadatos) {
            switch(metadato.getName()) {
                case "okp:capituloLibro.autor":
                    Input inAutor = (Input) metadato;
                    inAutor.setValue(autor);
                    break;
                case "okp:capituloLibro.autoresSecundarios":
                    Input inAutoresSecundarios = (Input) metadato;
                    inAutoresSecundarios.setValue(autoresSecundarios);
                    break;
                case "okp:capituloLibro.fechaAceptacion":
                    Input inFechaAceptacion = (Input) metadato;
                    inFechaAceptacion.setValue(fechaAceptacion.toString());
                    break;
                case "okp:capituloLibro.fechaPublicacion":
                    if (fechaPublicacion != null) {
                        Input inFechaPublicacion = (Input) metadato;
                        inFechaPublicacion.setValue(fechaPublicacion.toString());
                    }
                    break;
                case "okp:capituloLibro.isbn":
                    Input inIsbn = (Input) metadato;
                    inIsbn.setValue(isbn);
                    break;
                case "okp:capituloLibro.tituloCapituloLibro":
                    Input inTituloCapituloLibro = (Input) metadato;
                    inTituloCapituloLibro.setValue(tituloCapituloLibro);
                    break;
                case "okp:capituloLibro.tituloLibro":
                    Input inTituloLibro = (Input) metadato;
                    inTituloLibro.setValue(tituloLibro);
                    break;
                case "okp:capituloLibro.editorial":
                    Input inEditorial = (Input) metadato;
                    inEditorial.setValue(editorial);
                    break;
            }
        }
        
        openKM.asignarMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:capituloLibro", metadatos);
        openKM.asignarMetadatos(rutaFolder + "CapituloLibro." + extensionCapituloLibro, "okg:capituloLibro", metadatos);
        openKM.asignarMetadatos(rutaFolder + "CertificadoEditorial." + extensionCertificadoEditorial, "okg:capituloLibro", metadatos);
        
        /**
         * Se asigna el estudiante a la publicación y se registra
         */
        Publicacion publicacion = new Publicacion();
        publicacion.setAutor(autor);
        publicacion.setAutoresSecundarios(autoresSecundarios);
        publicacion.setFechaAceptacion(fechaAceptacion);
        publicacion.setFechaPublicacion(fechaPublicacion);
        publicacion.setEstudiante(estudiante);
        publicacion.setTipoDocumento(Publicacion.CAPITULO_LIBRO);
        publicacion.setEstado(Publicacion.ESTADO_POR_VERIFICAR);
        publicacion.setCreditos(0);
        publicacion.setFechaRegistro(new Date());
        publicacionRepo.save(publicacion);
        
        /**
         * Se asocia la publicación a la publicación del capítulo del libro y se registra
         */
        PublicacionCapituloLibro publicacionCapituloLibro = new PublicacionCapituloLibro();
        publicacionCapituloLibro.setIsbn(isbn);
        publicacionCapituloLibro.setTituloCapituloLibro(tituloCapituloLibro);
        publicacionCapituloLibro.setTituloLibro(tituloLibro);
        publicacionCapituloLibro.setEditorial(editorial);
        publicacionCapituloLibro.setPublicacion(publicacion);
        publicacionCapituloLibroRepo.save(publicacionCapituloLibro);
    }
    
    /**
     * Consulta una publicación de capitulo de libro por id de la publicacion
     * @param idPublicacion el id de la publicación que se quiere consultar
     * @return los datos de la publicacion del capitulo del libro que tienen esa
     * publicación asociada
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/idPublicacion/{idPublicacion}")
    public @ResponseBody PublicacionCapituloLibro getPublicacionCapituloLibroIdPublicacion(
            @PathVariable int idPublicacion) {            
        
        return publicacionCapituloLibroRepo.findByIdPublicacion(idPublicacion);
    }
    
    /**
     * Descarga un archivo de la publicación con el id de publicación dado
     * @param idPublicacion el id de la publicación que se quiere descargar
     * @param archivo el nombre del archivo que se quiere descargar de la publicación
     * @return el archivo buscado de la publicación
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/descargar/{idPublicacion}/{archivo}")
    public ResponseEntity<InputStreamResource> getArchivoCapituloLibro(
            @PathVariable int idPublicacion,
            @PathVariable String archivo)throws Exception {            
        
        PublicacionCapituloLibro publicacionCapituloLibro = publicacionCapituloLibroRepo.findByIdPublicacion(idPublicacion);
        if (publicacionCapituloLibro == null) {
            res.badRequest(405, 103);
        }
        String rutaFolder = OpenKM.RUTA_BASE + usuarioRepo.usuarioPorIdEst(publicacionCapituloLibro.getPublicacion().getEstudiante().getId())
                + "/CapituloLibro/" + publicacionCapituloLibro.getTituloCapituloLibro();
        
        String nombreReal = openKM.getNombreRealArchivo(rutaFolder, archivo);
        if (nombreReal == null) {
            res.badRequest(806, 103);
        }
        MediaType mediaType = MediaType.parseMediaType(openKM.getMimeTypeArchivo(rutaFolder, archivo));        
        InputStreamResource isr = new InputStreamResource(openKM.getArchivo(rutaFolder, archivo));
                
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreReal + "\"")
                .contentType(mediaType)
                .body(isr);
    }
}
