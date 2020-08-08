package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.OpenKM;
import co.edu.unicauca.gestordocumental.model.Publicacion;
import co.edu.unicauca.gestordocumental.model.PublicacionRevista;
import co.edu.unicauca.gestordocumental.repo.EstudianteRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionRepo;
import co.edu.unicauca.gestordocumental.repo.PublicacionRevistaRepo;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PublicacionRevistaValidador;
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
@RequestMapping(path="/publicacion/revista")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PublicacionRevistaController {
        
    /**
     * Valida los datos de la publicación
     */
    private final PublicacionValidador publicacionValidador;
    
    /**
     * Valida los datos de la publicacion en la revista
     */
    private final PublicacionRevistaValidador publicacionRevistaValidador;
    
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
    private PublicacionRevistaRepo publicacionRevistaRepo;
    
    public PublicacionRevistaController() {
        res = new Respuesta();
        publicacionValidador = new PublicacionValidador(res);
        publicacionRevistaValidador = new PublicacionRevistaValidador(res);
        openKM = OpenKM.getInstance();
    }
    
    /**
     * Registra una publicación en+ revista a un estudiante
     * @param datos el cuerpo de la solicitud con todos los datos de la nueva
     * publicación en una revista, en el cuerpo de la solicitud también se debe
     * proveer el código del estudiante quien está registrando la publicación
     * @param indice archivo con el indice de la revista
     * @param articulo archivo con el artículo de la revista
     * @param correoAceptacion pantallazo del correo de aceptación del artículo
     * @param clasificacionRevista pantallazo con la clasificación de la revista
     * clasificación de la revista
     */
    @Transactional
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/registrar")
    public @ResponseBody void crearPublicacionRevista(
            @RequestParam("datos") String datos,
            @RequestParam("indice") MultipartFile indice,
            @RequestParam("articulo") MultipartFile articulo,
            @RequestParam("correoAceptacion") MultipartFile correoAceptacion,
            @RequestParam("clasificacionRevista") MultipartFile clasificacionRevista) throws Exception {        
        
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
        String extensionArticulo = body.get("extensionArticulo");
        String extensionCorreoAceptacion = body.get("extensionCorreoAceptacion");
        String extensionClasificacionRevista = body.get("extensionClasificacionRevista");
        if ((extensionIndice == null || extensionIndice.length() == 0) 
            || (extensionArticulo == null || extensionArticulo.length() == 0) 
            || (extensionCorreoAceptacion == null || extensionCorreoAceptacion.length() == 0) 
            || (extensionClasificacionRevista == null || extensionClasificacionRevista.length() == 0)) {
            res.badRequest(1, 1);
        }
       
        String autor = publicacionValidador.validarAutor(body.get("autor"), true);  
        String autoresSecundarios = publicacionValidador.validarAutoresSecundarios(body.get("autoresSecundarios"), false);
        Date fechaAceptacion = publicacionValidador.validarFechaAceptacion(body.get("fechaAceptacion"), true);
        Date fechaPublicacion = publicacionValidador.validarFechaPublicacion(body.get("fechaPublicacion"), false);  
        publicacionValidador.validarFechaAceptacionFechaPublicacion(fechaAceptacion, fechaPublicacion);
        String doi = publicacionRevistaValidador.validarDoi(body.get("doi"), true);
        String tituloArticulo = publicacionRevistaValidador.validarTituloArticulo(body.get("tituloArticulo"), true);
        String nombreRevista = publicacionRevistaValidador.validarNombreRevista(body.get("nombreRevista"), true);
        String categoria = publicacionRevistaValidador.validarCategoria(body.get("categoria"), true);        
        
        /**
         * Se verifica que no exista el folder de la publicación y se crea
         */
        String rutaFolder = OpenKM.RUTA_BASE + estudiante.getUsuario().getUsuario() + "/Revista/" + tituloArticulo;
        if (openKM.verificarExistenciaRuta(rutaFolder)) {
           res.badRequest(500, 102);
        }
        
        openKM.crearFolder(rutaFolder);        
        rutaFolder += "/";
        openKM.crearDocumento(indice.getBytes(), rutaFolder, "Indice", extensionIndice);
        openKM.crearDocumento(articulo.getBytes(), rutaFolder, "Articulo", extensionArticulo);
        openKM.crearDocumento(correoAceptacion.getBytes(), rutaFolder, "CorreoAceptacion", extensionCorreoAceptacion);
        openKM.crearDocumento(clasificacionRevista.getBytes(), rutaFolder, "ClasificacionRevista", extensionClasificacionRevista);
         
        List<FormElement> metadatos = openKM.obtenerMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:revista");
        for (FormElement metadato : metadatos) {
            switch(metadato.getName()) {
                case "okp:revista.autor":
                    Input inAutor = (Input) metadato;
                    inAutor.setValue(autor);
                    break;
                case "okp:revista.autoresSecundarios":
                    Input inAutoresSecundarios = (Input) metadato;
                    inAutoresSecundarios.setValue(autoresSecundarios);
                    break;
                case "okp:revista.fechaAceptacion":
                    Input inFechaAceptacion = (Input) metadato;
                    inFechaAceptacion.setValue(fechaAceptacion.toString());
                    break;
                case "okp:revista.fechaPublicacion":
                    if (fechaPublicacion != null) {
                        Input inFechaPublicacion = (Input) metadato;
                        inFechaPublicacion.setValue(fechaPublicacion.toString());
                    }
                    break;
                case "okp:revista.doi":
                    Input inDoi = (Input) metadato;
                    inDoi.setValue(doi);
                    break;
                case "okp:revista.tituloArticulo":
                    Input inTituloArticulo = (Input) metadato;
                    inTituloArticulo.setValue(tituloArticulo);
                    break;
                case "okp:revista.nombreRevista":
                    Input inNombreRevista = (Input) metadato;
                    inNombreRevista.setValue(nombreRevista);
                    break;
                case "okp:revista.categoria":
                    Input inCategoria = (Input) metadato;
                    inCategoria.setValue(categoria);
                    break;
            }
        }
        
        openKM.asignarMetadatos(rutaFolder + "Indice." + extensionIndice, "okg:revista", metadatos);
        openKM.asignarMetadatos(rutaFolder + "Articulo." + extensionArticulo, "okg:revista", metadatos);
        openKM.asignarMetadatos(rutaFolder + "CorreoAceptacion." + extensionCorreoAceptacion, "okg:revista", metadatos);
        openKM.asignarMetadatos(rutaFolder + "ClasificacionRevista." + extensionClasificacionRevista, "okg:revista", metadatos);
        
        /**
         * Se asigna el estudiante a la publicación y se registra
         */
        Publicacion publicacion = new Publicacion();
        publicacion.setAutor(autor);
        publicacion.setAutoresSecundarios(autoresSecundarios);
        publicacion.setFechaAceptacion(fechaAceptacion);
        publicacion.setFechaPublicacion(fechaPublicacion);
        publicacion.setEstudiante(estudiante);
        publicacion.setTipoDocumento(Publicacion.REVISTA);
        publicacion.setEstado(Publicacion.ESTADO_POR_VERIFICAR);
        publicacion.setCreditos(0);
        publicacion.setFechaRegistro(new Date());
        publicacionRepo.save(publicacion);
        
        /**
         * Se asocia la publicación a la publicación de revista y se registra
         */
        PublicacionRevista publicacionRevista = new PublicacionRevista();
        publicacionRevista.setDoi(doi);
        publicacionRevista.setTituloArticulo(tituloArticulo);
        publicacionRevista.setCategoria(categoria);
        publicacionRevista.setNombreRevista(nombreRevista);
        publicacionRevista.setPublicacion(publicacion);
        publicacionRevistaRepo.save(publicacionRevista);        
    }
    
    /**
     * Consulta una publicación de revista por id de la publicacion
     * @param idPublicacion el id de la publicación que se quiere consultar
     * @return los datos de la revista que tienen esa publicación asociada
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/idPublicacion/{idPublicacion}")
    public @ResponseBody PublicacionRevista getPublicacionRevistaIdPublicacion(
            @PathVariable int idPublicacion) {            
        
        return publicacionRevistaRepo.findByIdPublicacion(idPublicacion);
    }
    
    /**
     * Descarga un archivo de la publicación con el id de publicación dado
     * @param idPublicacion el id de la publicación que se quiere descargar
     * @param archivo el nombre del archivo que se quiere descargar de la publicación
     * @return el archivo buscado de la publicación
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/descargar/{idPublicacion}/{archivo}")
    public ResponseEntity<InputStreamResource> getArchivoRevista(
            @PathVariable int idPublicacion,
            @PathVariable String archivo) throws Exception {            
        
        PublicacionRevista publicacionRevista = publicacionRevistaRepo.findByIdPublicacion(idPublicacion);
        if (publicacionRevista == null) {
            res.badRequest(405, 103);
        }
        String rutaFolder = OpenKM.RUTA_BASE + publicacionRevista.getPublicacion().getEstudiante().getUsuario().getUsuario()
                + "/Revista/" + publicacionRevista.getTituloArticulo();
        
        String nombreReal = openKM.getNombreRealArchivo(rutaFolder, archivo);
        if (nombreReal == null) {
            res.badRequest(507, 103);
        }
        MediaType mediaType = MediaType.parseMediaType(openKM.getMimeTypeArchivo(rutaFolder, archivo));        
        InputStreamResource isr = new InputStreamResource(openKM.getArchivo(rutaFolder, archivo));
                
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + nombreReal + "\"")
                .contentType(mediaType)
                .body(isr);
    }
}
