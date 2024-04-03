package co.edu.unicauca.gestordocumental.model.seguimiento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="persona")
public class Persona {

    @Id
    @Column(name = "id_persona", updatable = false, unique = true)
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native",strategy="native")
    @NotNull
    private int id_persona;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo")
    private String correo;

    /*@OneToOne
    @JoinColumn(name = "tipo_usu_id")
    private TipoUsuario tipoUsuario;*/
/*
    @ManyToMany
    @JoinTable(
            name = "grupo_tipo_usuario",
            joinColumns = { @JoinColumn(name = "id_persona") },
            inverseJoinColumns = { @JoinColumn(name = "tipo_usu_id")}
    )
    public Set<TipoUsuario> tiposUsuario;

    public Persona() {
        this.tiposUsuario = new HashSet<>();
    }
*/

    public int getIdPersona() {
        return id_persona;
    }

    public void setIdPersona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /*public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }*/
/*
    public void addTipoUsuario(TipoUsuario tipoUsuario)
    {
        this.tiposUsuario.add(tipoUsuario);
    }

    public void setTiposUsuario(Set<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }

    public ArrayList getTiposUsuario()
    {
        System.out.println("rol consultado " + this.tiposUsuario.stream().findFirst().get().getNombre());
        ArrayList<TipoUsuario> r = new ArrayList();
        r.addAll(this.tiposUsuario);
        return r;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }*/
}
