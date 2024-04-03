package co.edu.unicauca.gestordocumental.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "publicacion_evento")
@NamedQueries({
    @NamedQuery(name = "PublicacionEvento.findByIdPublicacion", query = "SELECT pe FROM PublicacionEvento pe WHERE pe.publicacion.id =:idPublicacion")
})
public class PublicacionEvento implements Serializable {
    
    /**
     * Identificador de la publicacion en la tabla evento
     */
    @Id
    @Column(name = "eve_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * (Digital Object Identifier) de un artículo en la red
     */
    @Size(max = 50)
    @Column(name = "eve_doi")
    // @NotNull
    private String doi;
    
    /**
     * Fecha cuando se dió inicio al evento
     */
    @Column(name = "eve_fecha_inicio")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    private Date fechaInicio;
    
    /**
     * Fecha cuando se dió fin al evento
     */
    @Column(name = "eve_fecha_fin")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    private Date fechaFin;
    
    /**
     * ISSN de la publicación
     */
    @Size(max = 50)
    @Column(name = "eve_issn")
    // @NotNull
    private String issn;
    
    /**
     * Título de la ponencia en el evento
     */
    @Size(max = 250)
    @Column(name = "eve_titulo_ponencia")
    @NotNull
    private String tituloPonencia;
    
    /**
     * Nombre del evento donde se presenta la ponencia
     */
    @Size(max = 150)
    @Column(name = "eve_nombre_evento")
    @NotNull
    private String nombreEvento;
    
    /**
     * Si es un congreso u otro tipo de evento
     */
    @Size(max = 20)
    @Column(name = "eve_tipo_evento")
    @NotNull
    private String tipoEvento;
    
    /**
     * Pais donde se llevó a cabo el evento
     */
    @Size(max = 30)
    @Column(name = "eve_pais")
    @NotNull
    private String pais;
    
    /**
     * Ciudad del pais donde se llevó a cabo el evento
     */
    @Size(max = 30)
    @Column(name = "eve_ciudad")
    @NotNull
    private String ciudad;
    
    @OneToOne
    private Publicacion publicacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getTituloPonencia() {
        return tituloPonencia;
    }

    public void setTituloPonencia(String tituloPonencia) {
        this.tituloPonencia = tituloPonencia;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
