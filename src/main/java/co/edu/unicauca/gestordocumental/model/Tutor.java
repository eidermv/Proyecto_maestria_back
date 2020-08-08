package co.edu.unicauca.gestordocumental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tutor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tutor.findByNombre", query = "SELECT t FROM Tutor t WHERE t.nombre =:nombre"),
    @NamedQuery(name = "Tutor.findAllByNombre", query = "SELECT t FROM Tutor t WHERE t.nombre LIKE CONCAT('%',:nombre,'%')")
})
public class Tutor implements Serializable {
    
    /**
     * Identificador del tutor
     */
    @Id
    @Column(name = "tut_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    private int id;
    
    @Size(max = 50)
    @Column(name = "tut_nombre",unique = true)
    private String nombre;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tutor")
    private List<Estudiante> estudiantes;

    public Tutor() {
    }

    public Tutor(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
