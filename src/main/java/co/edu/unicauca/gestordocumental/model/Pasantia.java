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
@Table(name = "pasantia")
@NamedQueries({
    @NamedQuery(name = "Pasantia.findByIdEstudiante", query = "SELECT p FROM Pasantia p WHERE p.estudiante.id =:idEstudiante"),
    @NamedQuery(name = "Pasantia.findByIdPasantia", query = "SELECT p FROM Pasantia p WHERE p.id =:idPasantia")
})
public class Pasantia implements Serializable {
    
    /**
     * Estados válidos de una pasantía
     */
    public static final String ESTADO_POR_VERIFICAR = "Por verificar";    
    public static final String ESTADO_APROBADO = "Aprobado";    
    public static final String ESTADO_RECHAZADO = "Rechazado";
    
    /**
     * Identificador de la práctica docente en la base de datos
     */
    @Id
    @Column(name = "pas_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Fecha cuando se registró la pasantía en el sistema
     */
    @Column(name = "pas_fecha_registro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="America/Bogota")
    @NotNull
    private Date fechaRegistro;
    
    /**
     * Fecha cuando se inició la pasantía
     */
    @Column(name = "pas_fecha_inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    @NotNull
    private Date fechaInicio;
    
    /**
     * Fecha cuando se finalizó la pasantía
     */
    @Column(name = "pas_fecha_fin")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Bogota")
    @NotNull
    private Date fechaFin;
    
    /**
     * Tipo de pasantía que el estudiante realizó
     */
    @Column(name = "pas_tipo_pasantia")
    @NotNull
    @Size(max = 100)
    private String tipoPasantia;
    
    /**
     * Nombre de la Universidad o institución donde realizó la pasantía
     */
    @Column(name = "pas_institucion")
    @NotNull
    @Size(max = 100)
    private String institucion;
    
    /**
     * La dependencia donde realizó la pasantía
     */
    @Column(name = "pas_dependencia")
    @NotNull
    @Size(max = 100)
    private String dependencia;
    
    /**
     * Nombre de la dependencia donde realizó la pasantía
     */
    @Column(name = "pas_nombre_dependencia")
    @NotNull
    @Size(max = 150)
    private String nombreDependencia;
    
    /**
     * Nombres y apellidos del responsable donde el estudiante realizó la pasantía
     */
    @Column(name = "pas_responsable")
    @NotNull
    @Size(max = 100)
    private String responsable;
    
    /**
     * Creditos otorgados por el coordinador a la pasantía
     */
    @Column(name = "pas_creditos")
    @NotNull
    private Integer creditos;
    
    /**
     * Estado de la pasantía
     */
    @Column(name = "pas_estado")
    @NotNull
    @Size(max = 20)
    private String estado;
    
    /**
     * Observación hecha por el coordinador al registro de pasntía
     */
    @Column(name = "pas_observacion")
    @Size(max = 300)
    private String observacion;
    
    @OneToOne
    private Estudiante estudiante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTipoPasantia() {
        return tipoPasantia;
    }

    public void setTipoPasantia(String tipoPasantia) {
        this.tipoPasantia = tipoPasantia;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getNombreDependencia() {
        return nombreDependencia;
    }

    public void setNombreDependencia(String nombreDependencia) {
        this.nombreDependencia = nombreDependencia;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
