package co.edu.unicauca.gestordocumental.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "practica_docente")
@NamedQueries({
    @NamedQuery(name = "PracticaDocente.findByIdEstudiante", query = "SELECT pd FROM PracticaDocente pd WHERE pd.estudiante.id =:idEstudiante"),
    @NamedQuery(name = "PracticaDocente.findByIdPracticaDocente", query = "SELECT pd FROM PracticaDocente pd WHERE pd.id =:idPracticaDocente")
})
public class PracticaDocente implements Serializable {
    
    /**
     * Estados válidos de una práctica docente
     */
    public static final String ESTADO_POR_VERIFICAR = "Por verificar";    
    public static final String ESTADO_APROBADO = "Aprobado";    
    public static final String ESTADO_RECHAZADO = "Rechazado";
    
    /**
     * Identificador de la práctica docente en la base de datos
     */
    @Id
    @Column(name = "prac_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Tipo de práctica docente que el estudiante realizó
     */
    @Column(name = "prac_tipo_practica_docente")
    @NotNull
    @Size(max = 100)
    private String tipoPracticaDocente;
    
    /**
     * Fecha cuando se registró la practica docente en el sistema
     */
    @Column(name = "prac_fecha_registro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Bogota")
    @NotNull
    private Date fechaRegistro;
    
    /**
     * Fecha cuando se inició la práctica docente
     */
    @Column(name = "prac_fecha_inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    /**
     * Fecha cuando se finalizó la práctica docente
     */
    @Column(name = "prac_fecha_fin")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    
    /**
     * Cantidad de horas de la práctica docente
     */
    @Column(name = "prac_horas")
    @NotNull
    private Integer horas;
    
    /**
     * Estado de la práctica docente en el sistema
     */
    @Column(name = "prac_estado")
    @NotNull
    @Size(max = 20)
    private String estado;
    
    /**
     * Creditos asignados por el coordinador a la práctica docente
     */
    @Column(name = "prac_creditos")
    @NotNull
    private Integer creditos;
    
    /**
     * Observación hecha por el coordinador al registro de práctica docente
     */
    @Column(name = "prac_observacion")
    @Size(max = 300)
    private String observacion;
    
    /**
     * Estudiante que realizó la práctica docente
     */
    @OneToOne
    private Estudiante estudiante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoPracticaDocente() {
        return tipoPracticaDocente;
    }

    public void setTipoPracticaDocente(String tipoPracticaDocente) {
        this.tipoPracticaDocente = tipoPracticaDocente;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
