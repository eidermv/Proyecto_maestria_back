package co.edu.unicauca.gestordocumental.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "publicacion_libro")
@NamedQueries({
    @NamedQuery(name = "PublicacionLibro.findByIdPublicacion", query = "SELECT pl FROM PublicacionLibro pl WHERE pl.publicacion.id =:idPublicacion")
})
public class PublicacionLibro implements Serializable {
    
    /**
     * Identificador de la publicación en la tabla libro
     */
    @Id
    @Column(name = "lib_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * ISBN del libro
     */
    @Size(max = 40)
    @Column(name = "lib_isbn")
    // @NotNull
    private String isbn;
    
    /**
     * Titulo del libro
     */
    @Size(max = 250)
    @Column(name = "lib_titulo_libro")
    @NotNull
    private String tituloLibro;
    
    /**
     * Editorial del libro
     */
    @Size(max = 50)
    @Column(name = "lib_editorial")
    @NotNull
    private String editorial;
    
    /**
     * Pais de publicación del libro
     */
    @Size(max = 30)
    @Column(name = "lib_pais")
    @NotNull
    private String pais;
    
    /**
     * Ciudad de publicación del libro
     */
    @Size(max = 30)
    @Column(name = "lib_ciudad")
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

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
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
