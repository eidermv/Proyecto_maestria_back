
package co.edu.unicauca.gestordocumental.model;

import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findByNombre", query = "SELECT t FROM TipoUsuario t WHERE t.nombre =:nombre")
})
public class TipoUsuario implements Serializable 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_usu_id")
    private long id;
    
    @Size(max = 30)
    @Column(name = "tipo_usu_nombre")
    @NotNull
    private String nombre;

    @ManyToMany(mappedBy = "tiposUsuario", fetch = FetchType.EAGER)
    private Set<Usuario> usuarios = new HashSet<>();

     public TipoUsuario () {

     }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
