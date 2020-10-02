package co.edu.unicauca.gestordocumental.model;

import co.edu.unicauca.gestordocumental.model.seguimiento.Persona;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findByCodigo", query = "SELECT e FROM Estudiante e WHERE e.codigo =:codigo"),
    @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.persona.nombres LIKE CONCAT('%',:nombre,'%') OR e.persona.apellidos LIKE CONCAT('%',:nombre,'%')"),
    @NamedQuery(name = "Estudiante.findByCorreo", query = "SELECT e FROM Estudiante e WHERE e.persona.correo =:correo"),
    @NamedQuery(name = "Estudiante.findByUsuario", query = "SELECT e FROM Estudiante e, Usuario u WHERE u.persona = e.persona and u =:usuario"),
    @NamedQuery(name = "Estudiante.findByMatch", query = "SELECT e FROM Estudiante e WHERE e.persona.nombres LIKE CONCAT('%',:match,'%') OR e.persona.apellidos LIKE CONCAT('%',:match,'%') OR e.codigo LIKE CONCAT('%',:match,'%')")
})
public class Estudiante extends Persona implements Serializable {
    
    /**
     * Identificador del estudiante
     */
    
    @Column(name = "est_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    private int id;
    
    /**
     * Código del estudiante
     */
    @Size(max = 20)
    @Column(name = "est_codigo", unique = true)
    @NotNull
    private String codigo;
    
    /**
     * Nombres del estudiante
     */
    @Size(max = 40)
    @Column(name = "est_nombres")
    @NotNull
    private String nombres;
    
    /**
     * Apellidos del estudiante
     */
    @Size(max = 40)
    @Column(name = "est_apellidos")
    @NotNull
    private String apellidos;
    
    /**
     * Correo electrónico del estudiante
     */
    @Size(max = 40)
    @Column(name = "est_correo")
    @NotNull
    private String correo;
  
    /**
     * Cohorte año y semestre en el que ingresó el estudiante
     */
    @Column(name = "est_cohorte")
    @NotNull
    private Integer cohorte;
    
    /**
     * Semestre en el que ingresó el estudiante ej. 2018-1
     */
    @Column(name = "est_semestre")
    @NotNull
    private Integer semestre;
    
    /**
     * El estado del estudiante en el sistema de gestión documental
     */
    @Size(max = 20)
    @Column(name = "est_estado")
    @NotNull
    private String estado;
    
    /**
     * Los creditos que tiene el estudiante
     */
    @Column(name = "est_creditos")
    @NotNull
    private Integer creditos;
    
    /**
     * Si el estudiante ingresa por maestría o por doctorado
     */
    @Column(name = "est_pertenece")
    @NotNull
    private String pertenece;
    
    /**
     * El tutor del estudiante
     */
    @ManyToOne
    private Tutor tutor;
    
    /**
     * El usuario con el que accede al sistema el estudiante

    @JsonIgnore
    @OneToOne
    private Usuario usuario;
*/

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    public Estudiante() {
    }

    public Estudiante(String codigo, String nombres, String apellidos, 
            String correo, Integer cohorte, Integer semestre, String estado, 
            Integer creditos, String pertenece, Tutor tutor) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.cohorte = cohorte;
        this.semestre = semestre;
        this.estado = estado;
        this.creditos = creditos;
        this.pertenece = pertenece;
        this.tutor = tutor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Integer getCohorte() {
        return cohorte;
    }

    public void setCohorte(Integer cohorte) {
        this.cohorte = cohorte;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getPertenece() {
        return pertenece;
    }

    public void setPertenece(String pertenece) {
        this.pertenece = pertenece;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    /*
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    */

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}