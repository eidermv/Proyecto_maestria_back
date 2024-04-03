package co.edu.unicauca.gestordocumental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "publicacion_capitulo_libro")
@NamedQueries({
    @NamedQuery(name = "PublicacionCapituloLibro.findByIdPublicacion", query = "SELECT pcl FROM PublicacionCapituloLibro pcl WHERE pcl.publicacion.id =:idPublicacion")
})
public class PublicacionCapituloLibro implements Serializable {
    
    /**
     * Identificador de la publicación en la tabla capitulo libro
     */
    @Id
    @Column(name = "cap_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * ISBN del libro
     */
    @Size(max = 40)
    @Column(name = "cap_isbn")
    // @NotNull
    private String isbn;
    
    /**
     * Titulo del libro donde se publicó el capítulo
     */
    @Size(max = 250)
    @Column(name = "cap_titulo_libro")
    @NotNull
    private String tituloLibro;
    
    /**
     * Título del capítulo del libro
     */
    @Size(max = 200)
    @Column(name = "cap_titulo_capitulo_libro")
    @NotNull
    private String tituloCapituloLibro;
    
    /**
     * Editorial quien publicó el libro
     */
    @Size(max = 50)
    @Column(name = "cap_editorial")
    @NotNull
    private String editorial;
    
    @OneToOne
    private Publicacion publicacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public String getTituloCapituloLibro() {
        return tituloCapituloLibro;
    }

    public void setTituloCapituloLibro(String tituloCapituloLibro) {
        this.tituloCapituloLibro = tituloCapituloLibro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
