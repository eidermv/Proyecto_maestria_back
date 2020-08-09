package co.edu.unicauca.gestordocumental.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "publicacion_revista")
@NamedQueries({
    @NamedQuery(name = "PublicacionRevista.findByIdPublicacion", query = "SELECT pr FROM PublicacionRevista pr WHERE pr.publicacion.id =:idPublicacion")
})
public class PublicacionRevista implements Serializable {
    
    /**
     * Identificador de la publicacion en la tabla revista
     */
    @Id
    @Column(name = "rev_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * (Digital Object Identifier) de un artículo en la red
     */
    @Size(max = 50)
    @Column(name = "rev_doi")
    // @NotNull
    private String doi;
    
    /**
     * Título del artículo en la revista
     */
    @Size(max = 250)
    @Column(name = "rev_titulo_articulo")
    @NotNull
    private String tituloArticulo;
    
    /**
     * Nombre de la revista
     */    
    @Size(max = 100)
    @Column(name = "rev_nombre_revista")
    @NotNull
    private String nombreRevista;
    
    /**
     * Categoría de la revista (A1, A2, B, etc)
     */
    @Size(max = 10)
    @Column(name = "rev_categoria")
    @NotNull
    private String categoria;
    
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

    public String getTituloArticulo() {
        return tituloArticulo;
    }

    public void setTituloArticulo(String tituloArticulo) {
        this.tituloArticulo = tituloArticulo;
    }

    public String getNombreRevista() {
        return nombreRevista;
    }

    public void setNombreRevista(String nombreRevista) {
        this.nombreRevista = nombreRevista;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
