
package co.edu.unicauca.gestordocumental.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Integer id;
    
    @Size(max = 30)
    @Column(name = "usu_usuario")
    @NotNull
    private String usuario;
    
    @Size(max = 64)
    @Column(name = "usu_contrasena")
    @NotNull
    private String contrasena;
    
    @Column(name = "usu_estado")
    @NotNull
    private Boolean estado;
    
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
        name = "grupo_tipo_usuario", 
        joinColumns = { @JoinColumn(name = "usu_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "tipo_usu_id")}
    )
    private Set<TipoUsuario> tiposUsuario;

    public Usuario() {
        this.tiposUsuario = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Boolean getEstado() {
        return estado;
    }
    
    public ArrayList getTiposUsuario()
    {
        ArrayList<TipoUsuario> r = new ArrayList();
        r.addAll(tiposUsuario);
        return r;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public void addTipoUsuario(TipoUsuario tipoUsuario)
    {
        this.tiposUsuario.add(tipoUsuario);
    }

    public void setTiposUsuario(Set<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }
    
}
