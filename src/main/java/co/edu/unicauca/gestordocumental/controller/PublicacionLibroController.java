package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.OpenKM;
import co.edu.unicauca.gestordocumental.model.Publicacion;
import co.edu.unicauca.gestordocumental.model.PublicacionLibro;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionLibroRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PublicacionLibroValidador;
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
@RequestMapping(path="/publicacion/libro")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PublicacionLibroController {
    
    /**
     * Valida los datos de la publicación
     */
    private final PublicacionValidador publicacionValidador;
    
    /**
     * Valida los datos de la publicación en el libro
     */
    private final PublicacionLibroValidador publicacionLibroValidador;
    
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
    private PublicacionLibroRepo publicacionLibroRepo;
    
    public PublicacionLibroController() {
        res = new Respuesta();
        publicacionValidador = new PublicacionValidador(res);
        publicacionLibroValidador = new PublicacionLibroValidador(res);
        openKM = OpenKM.getInstance();
    }
    
    @Transactional
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/registrar")
    public @ResponseBody void crearPublicacionRevista(
            @RequestParam("datos") String datos,
            @RequestParam("indice") MultipartFile indice,
            @RequestParam("libro") MultipartFile libro,
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
        String extensionLibro = body.get("extensionLibro");
        String extensionCertificadoEditorial = body.get("extensionCertificadoEditorial");
        if ((extensionIndice == null || extensionIndice.length() == 0) 
            || (extensionLibro == null || extensionLibro.length() == 0) 
            || (extensionCertificadoEditorial == null || extensionCertificadoEditorial.length() == 0)) {
            res.badRequest(1, 1);
        }
        
        String autor = publicacionValidador.validarAutor(body.get("autor"), true);  
        String autoresSecundarios = publicacionValidador.validarAutoresSecundarios(body.get("autoresSecundarios"), false);
        Date fechaAceptacion = publicacionValidador.validarFechaAceptacion(body.get("fechaAceptacion"), true);
        Date fechaPublicacion = publicacionValidador.validarFechaPublicacion(body.get("fechaPublicacion"), false);
        publicacionValidador.validarFechaAceptacionFechaPublicacion(fechaAceptacion, fechaPublicacion);
        String isbn = publicacionLibroValidador.validarIsbn(body.get("isbn"), true);
        String tituloLibro = publicacionLibroValidador.validarTituloLibro(body.get("tituloLibro"), true);
        String editorial = publicacionLibroValidador.validarEditorial(body.get("editorial"), true);
        String pais = publicacionLibroValidador.validarPais(body.get("pais"), true);
        String ciudad = publicacionLibroValidador.validarCiudad(body.get("ciudad"), true);
        
        /**
         * Se verifica que no exista el folder de la publicación y se crea
         */
        String rutaFolder = OpenKM.RUTA_BASE + estudiante.getUsuario().getUsuario() + "/Libro/" + tituloLibro;
        if (openKM.verificarExistenciaRuta(rutaFolder)) {
           res.badRequest(701, 102);
        }
        
        openKM.crearFolder(rutaFolder);        
        rutaFolder += "/";
        openKM.crearDocumento(indice.getBytes(), rutaFolder, "Indice", extensionIndice);
        openKM.crearDocumento(libro.getBytes(), rutaFolder, "Libro", extensionLibro);
        openKM.crearDocumento(certificadoEditorial.getBytes(), rutaFolder, "CertificadoEditorial", extensionCertificadoEditorial);
        
        List<FormElement> metadatos = openKM.obtenerMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:libro");
        for (FormElement metadato : metadatos) {
            switch(metadato.getName()) {
                case "okp:libro.autor":
                    Input inAutor = (Input) metadato;
                    inAutor.setValue(autor);
                    break;
                case "okp:libro.autoresSecundarios":
                    Input inAutoresSecundarios = (Input) metadato;
                    inAutoresSecundarios.setValue(autoresSecundarios);
                    break;
                case "okp:libro.fechaAceptacion":
                    Input inFechaAceptacion = (Input) metadato;
                    inFechaAceptacion.setValue(fechaAceptacion.toString());
                    break;
                case "okp:libro.fechaPublicacion":
                    if (fechaPublicacion != null) {
                        Input inFechaPublicacion = (Input) metadato;
                        inFechaPublicacion.setValue(fechaPublicacion.toString());
                    }
                    break;
                case "okp:libro.isbn":
                    Input inIsbn = (Input) metadato;
                    inIsbn.setValue(isbn);
                    break;
                case "okp:libro.tituloLibro":
                    Input inTituloLibro = (Input) metadato;
                    inTituloLibro.setValue(tituloLibro);
                    break;
                case "okp:libro.editorial":
                    Input inEditorial = (Input) metadato;
                    inEditorial.setValue(editorial);
                    break;                
                case "okp:libro.pais":
                    Input inPais = (Input) metadato;
                    inPais.setValue(pais);
                    break;
                case "okp:libro.ciudad":
                    Input inCiudad = (Input) metadato;
                    inCiudad.setValue(ciudad);
                    break;
            }
        }
        
        openKM.asignarMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:libro", metadatos);
        openKM.asignarMetadatos(rutaFolder + "Libro." + extensionLibro, "okg:libro", metadatos);
        openKM.asignarMetadatos(rutaFolder + "CertificadoEditorial." + extensionCertificadoEditorial, "okg:libro", metadatos);
        
        /**
         * Se asigna el estudiante a la publicación y se registra
         */
        Publicacion publicacion = new Publicacion();
        publicacion.setAutor(autor);
        publicacion.setAutoresSecundarios(autoresSecundarios);
        publicacion.setFechaAceptacion(fechaAceptacion);
        publicacion.setFechaPublicacion(fechaPublicacion);
        publicacion.setEstudiante(estudiante);
        publicacion.setTipoDocumento(Publicacion.LIBRO);
        publicacion.setEstado(Publicacion.ESTADO_POR_VERIFICAR);
        publicacion.setCreditos(0);
        publicacion.setFechaRegistro(new Date());
        publicacionRepo.save(publicacion);
        
        /**
         * Se asocia la publicación a la publicación de libro y se registra
         */
        PublicacionLibro publicacionLibro = new PublicacionLibro();
        publicacionLibro.setIsbn(isbn);
        publicacionLibro.setTituloLibro(tituloLibro);
        publicacionLibro.setEditorial(editorial);
        publicacionLibro.setPais(pais);
        publicacionLibro.setCiudad(ciudad);
        publicacionLibro.setPublicacion(publicacion);
        publicacionLibroRepo.save(publicacionLibro);
    }
    
    /**
     * Consulta una publicación de libro por id de la publicacion
     * @param idPublicacion el id de la publicación que se quiere consultar
     * @return los datos de la publicacion del libro que tienen esa
     * publicación asociada
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/idPublicacion/{idPublicacion}")
    public @ResponseBody PublicacionLibro getPublicacionLibroIdPublicacion(
            @PathVariable int idPublicacion) {            
        
        return publicacionLibroRepo.findByIdPublicacion(idPublicacion);
    }
    
    /**
     * Descarga un archivo de la publicación con el id de publicación dado
     * @param idPublicacion el id de la publicación que se quiere descargar
     * @param archivo el nombre del archivo que se quiere descargar de la publicación
     * @return el archivo buscado de la publicación
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/descargar/{idPublicacion}/{archivo}")
    public ResponseEntity<InputStreamResource> getArchivoLibro(
            @PathVariable int idPublicacion,
            @PathVariable String archivo) throws Exception {            
        
        PublicacionLibro publicacionLibro = publicacionLibroRepo.findByIdPublicacion(idPublicacion);
        if (publicacionLibro == null) {
            res.badRequest(405, 103);
        }
        String rutaFolder = OpenKM.RUTA_BASE + publicacionLibro.getPublicacion().getEstudiante().getUsuario().getUsuario()
                + "/Libro/" + publicacionLibro.getTituloLibro();
        
        String nombreReal = openKM.getNombreRealArchivo(rutaFolder, archivo);
        if (nombreReal == null) {
            res.badRequest(706, 103);
        }
        MediaType mediaType = MediaType.parseMediaType(openKM.getMimeTypeArchivo(rutaFolder, archivo));        
        InputStreamResource isr = new InputStreamResource(openKM.getArchivo(rutaFolder, archivo));
                        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreReal + "\"")
                .contentType(mediaType)
                .body(isr);
    }
}
