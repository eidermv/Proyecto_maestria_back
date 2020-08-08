package co.edu.unicauca.gestordocumental.controller;

import co.edu.unicauca.gestordocumental.model.*;
import co.edu.unicauca.gestordocumental.repo.*;
import co.edu.unicauca.gestordocumental.respuesta.Respuesta;
import co.edu.unicauca.gestordocumental.validador.PublicacionValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path="/publicacion")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PublicacionController {
    
    /**
     * Envía respuesta al cliente
     */
    private final Respuesta res;
    /**
     * Valida los datos de la publicación
     */
    private final PublicacionValidador publicacionValidador;
    
    /**
     * Se conecta con el gestor OpenKM
     */
    private final OpenKM openKM;
    
    @Autowired
    private PublicacionRepo publicacionRepo;
    
    @Autowired
    private PublicacionEventoRepo publicacionEventoRepo;
    
    @Autowired
    private PublicacionCapituloLibroRepo publicacionCapituloLibroRepo;
    
    @Autowired
    private PublicacionLibroRepo publicacionLibroRepo;
    
    @Autowired
    private PublicacionRevistaRepo publicacionRevistaRepo;
    
    @Autowired
    private EstudianteRepo estudianteRepo;
    
    public PublicacionController() {
        res = new Respuesta();
        publicacionValidador = new PublicacionValidador(res);
        openKM = OpenKM.getInstance();
    }
    
    /**
     * Busca todas las publicaciones de un estudiante
     * @param codigoEstudiante el estudiante
     * @return todas las publicaciones de ese estudiante con ese código
     */
    @PreAuthorize("hasAuthority('Estudiante') or hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/codigoEstudiante/{codigoEstudiante}")
    public @ResponseBody Iterable<Publicacion> getPublicacionByCodigoEstudiante (
            @PathVariable String codigoEstudiante) throws Exception {
                
        Estudiante estudiante = estudianteRepo.findByCodigo(codigoEstudiante);
        if (estudiante == null) {
            res.badRequest(100, 103);
        }
        return publicacionRepo.findByIdEstudiante((Integer) estudiante.getId());
    }
    
    /**
     * Retorna todas las publicaciones registradas en la plataforma
     * @return las publicaciones registradas
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @GetMapping(path="/buscar/todo")
    public @ResponseBody Iterable<Publicacion> getPublicacion() {
        
        return publicacionRepo.findAll();
    }
    
    /**
     * Actualiza el estado de una publicación
     * @param body los datos de la publicación que se quiere actualizar     
     */
    @PreAuthorize("hasAuthority('Coordinador')")
    @PostMapping(path="/actualizar/estado")
    public @ResponseBody void actualizarEstadoPublicacion (
            @RequestBody Map<String, String> body) throws Exception {
        
        int idPublicacion = publicacionValidador.validarIdPublicacion(body.get("idPublicacion"), true);
        int creditos = publicacionValidador.validarCreditos(body.get("creditos"), true);
        String estado = publicacionValidador.validarEstado(body.get("estado"), true);
        String comentario = publicacionValidador.validarComentario(body.get("comentario"), false);
        
        Optional<Publicacion> oPPublicacion = publicacionRepo.findById(idPublicacion);
        if(!oPPublicacion.isPresent())
            res.badRequest(405, 103);
        Publicacion publicacion = oPPublicacion.get();
        if(!estado.equals(Publicacion.ESTADO_APROBADO))
            creditos = 0;
        else
        {
            if(creditos == 0)
                res.badRequest(406, 104);
        }
        
      
        
        publicacion.setEstado(estado);
        publicacion.setCreditos(creditos);
        publicacion.setComentario(comentario);
        publicacionRepo.save(publicacion);
        
    }
    
    /**
     * Elimina una publicación
     * @param body los datos de la publicación que se quiere eliminar     
     */
    @PreAuthorize("hasAuthority('Estudiante')")
    @PostMapping(path="/eliminar")
    public @ResponseBody void eliminarPublicacion (
            @RequestBody Map<String, String> body) throws Exception {
        
        int idPublicacion = publicacionValidador.validarIdPublicacion(body.get("idPublicacion"), true);
        
        Publicacion publicacion = publicacionRepo.findById(idPublicacion).get();
        if(publicacion == null)
            res.badRequest(405, 103);
        
        String tipoPublicacion = publicacion.getTipoDocumento();
        String carpetaOpenKMEliminar = OpenKM.RUTA_BASE + publicacion.getEstudiante().getUsuario().getUsuario() + "/";
        
        switch(tipoPublicacion)
        {
            case Publicacion.CAPITULO_LIBRO:
                PublicacionCapituloLibro capLibro = publicacionCapituloLibroRepo.findByIdPublicacion(idPublicacion);
                publicacionCapituloLibroRepo.delete(capLibro);
                carpetaOpenKMEliminar += "CapituloLibro/" + capLibro.getTituloCapituloLibro();
                break;
            case Publicacion.EVENTO:
                PublicacionEvento evento = publicacionEventoRepo.findByIdPublicacion(idPublicacion);
                publicacionEventoRepo.delete(evento);
                carpetaOpenKMEliminar += "Evento/" + evento.getTituloPonencia();
                break;
            case Publicacion.LIBRO:
                PublicacionLibro libro = publicacionLibroRepo.findByIdPublicacion(idPublicacion);
                publicacionLibroRepo.delete(libro);
                carpetaOpenKMEliminar += "Libro/" + libro.getTituloLibro();
                break;
            case Publicacion.REVISTA:
                PublicacionRevista revista = publicacionRevistaRepo.findByIdPublicacion(idPublicacion);
                publicacionRevistaRepo.delete(revista);
                carpetaOpenKMEliminar += "Revista/" + revista.getTituloArticulo();
                break;
        }
        
        openKM.eliminarFolder(carpetaOpenKMEliminar);
        publicacionRepo.delete(publicacion);        
    }
}
