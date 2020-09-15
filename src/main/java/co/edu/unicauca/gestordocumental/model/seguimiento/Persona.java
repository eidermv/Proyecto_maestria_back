package co.edu.unicauca.gestordocumental.model.seguimiento;

import co.edu.unicauca.gestordocumental.model.TipoUsuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="persona")
public class Persona {

    @Id
    @Column(name = "id_persona")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    private int id_persona;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo")
    private String correo;

    @OneToOne
    @JoinColumn(name = "tipo_usu_id")
    private TipoUsuario tipoUsuario;


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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
