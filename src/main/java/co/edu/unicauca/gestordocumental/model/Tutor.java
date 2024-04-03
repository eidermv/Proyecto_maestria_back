package co.edu.unicauca.gestordocumental.model;

import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import com.fasterxml.jackson.annotation.JsonIgnore;

import co.edu.unicauca.gestordocumental.model.seguimiento.TipoTutor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tutor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tutor.findByNombre", query = "SELECT t FROM Tutor t WHERE concat(t.persona.nombres, ' ', t.persona.apellidos) =:nombre"),
    @NamedQuery(name = "Tutor.findByIdentificacion", query = "SELECT t FROM Tutor t WHERE t.identificacion =:identificacion"),
    @NamedQuery(name = "Tutor.findAllByNombre", query = "SELECT t FROM Tutor t WHERE concat(t.persona.nombres, ' ', t.persona.apellidos) LIKE CONCAT('%',:nombre,'%')")
})
public class Tutor implements Serializable {
    
    /**
     * Id del tutor
     */

    @Id
    @Column(name = "id_tutor", updatable = false, unique = true)
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name="native",strategy="native")
    @NotNull
    private int id_tutor;

    @Size(max = 20)
    @Column(name = "identificacion")
    @NotNull
    private String identificacion;
    
    @Size(max = 50)
    @Column(name = "nombres")
    @NotNull
    private String nombres;
    
    @Size(max = 50)
    @Column(name = "apellidos")
    @NotNull
    private String apellidos;
    
    @Column(name = "correo")
    @NotNull
    private String correo;
    
    @Column(name = "telefono")
    private long telefono;
    
    @Column(name = "departamento")
    private String departamento;
    
    @Column(name = "grupo_investigacion")
    private String grupo_investigacion;
    
    @OneToOne
    @JoinColumn(name = "id_tipo_tutor")
    private TipoTutor tipo_tutor;
    
    @Column(name = "universidad")
    private String universidad;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Estudiante> estudiantes;
    /*
    @JsonIgnore
    @OneToOne
    private Usuario usuario;
     */

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    public Tutor() {
    }

    public Tutor(String nombre) {
        this.nombres = nombre;
    }

    public int getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(int id) {
        this.id_tutor = id;
    }

    public String getNombre() {
        return nombres;
    }

    public void setNombre(String nombre) {
        this.nombres = nombre;
    }
    
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

	public String getApellido() {
		return apellidos;
	}

	public void setApellido(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getGrupoInvestigacion() {
		return grupo_investigacion;
	}

	public void setGrupoInvestigacion(String grupo_investigacion) {
		this.grupo_investigacion = grupo_investigacion;
	}

	public TipoTutor getTipoTutor() {
		return tipo_tutor;
	}

	public void setTipoTutor(TipoTutor tipo_tutor) {
		this.tipo_tutor = tipo_tutor;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
