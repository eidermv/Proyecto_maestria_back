package co.edu.unicauca.gestordocumental.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "publicacion")
@NamedQueries({
    @NamedQuery(name = "Publicacion.findByIdEstudiante", query = "SELECT p FROM Publicacion p WHERE p.estudiante.id =:idEstudiante")
})
public class Publicacion implements Serializable {
    
    /**
     * Tipos de publicacion
     */
    public static final String REVISTA = "Revista";
    public static final String LIBRO = "Libro";
    public static final String EVENTO = "Evento";
    public static final String CAPITULO_LIBRO = "Capítulo de libro";
    
    /**
     * Estados válidos de una publicación
     */
    public static final String ESTADO_POR_VERIFICAR = "Por verificar";    
    public static final String ESTADO_APROBADO = "Aprobado";    
    public static final String ESTADO_RECHAZADO = "Rechazado";
    
    /**
     * Identificador de la publicación
     */
    @Id
    @Column(name = "pub_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Nombre del autor principal de la publicación
     */
    @Size(max = 50)
    @Column(name = "pub_autor")
    @NotNull
    private String autor;
    
    /**
     * Todos los autores secundarios de la publicación separados por coma
     */
    @Size(max = 250)
    @Column(name = "pub_autores_secundarios")
    private String autoresSecundarios;
    
    /**
     * Fecha de aceptación de la publicación
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    @Column(name = "pub_fecha_aceptacion")
    @NotNull
    private Date fechaAceptacion;
    
    /**
     * Fecha de publicación de la publicación
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    @Column(name = "pub_fecha_publicacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPublicacion;
    
    /**
     * Tipo de documento de la publicación
     */
    @Size(max = 20)
    @Column(name = "pub_tipo_documento")
    @NotNull
    private String tipoDocumento;
    
    /**
     * Los creditos asignados a la publicación
     */
    @Column(name = "pub_creditos")
    @NotNull
    private Integer creditos;
    
    /**
     * El estado de la publicación en el sistema
     */
    @Size(max = 30)
    @Column(name = "pub_estado")
    @NotNull
    private String estado;
    
    /**
     * El comentario de aceptación o rechazo de una publicación
     */
    @Size(max = 300)
    @Column(name = "pub_comentario", columnDefinition = "text")
    private String comentario;
    
    /**
     * La fecha y hora en que la publicación fue subida al sistema
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Bogota")
    @Column(name = "pub_fecha_registro")
    @NotNull
    private Date fechaRegistro;
    
    @OneToOne
    private Estudiante estudiante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutoresSecundarios() {
        return autoresSecundarios;
    }

    public void setAutoresSecundarios(String autoresSecundarios) {
        this.autoresSecundarios = autoresSecundarios;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
