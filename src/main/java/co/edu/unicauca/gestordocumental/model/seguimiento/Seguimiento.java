package co.edu.unicauca.gestordocumental.model.seguimiento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.gestordocumental.model.Estudiante;
import co.edu.unicauca.gestordocumental.model.Tutor;

@Entity
@Table(name = "seguimiento")
@SqlResultSetMapping(
		name="seguimientoPorTutor",
		columns={
				@ColumnResult(name="id_seguimiento"),
				@ColumnResult(name="codirector"),
				@ColumnResult(name="cohorte"),
				@ColumnResult(name="nombre"),
				@ColumnResult(name="objetivo_general"),
				@ColumnResult(name="objetivos_especificos"),
				@ColumnResult(name="id_estado_proyecto"),
				@ColumnResult(name="id_estado_seguimiento"),
				@ColumnResult(name="id_tipo_seguimiento"),
				@ColumnResult(name="est_id"),
				@ColumnResult(name="id_tutor")


		}
		)

@NamedNativeQuery(
		name = "Seguimiento.seguimientosPorTutos",
		query = "SELECT id_seguimiento, codirector, cohorte, nombre, objetivo_general, objetivos_especificos, id_estado_proyecto, id_estado_seguimiento, id_tipo_seguimiento, est_id, id_tutor FROM Seguimiento WHERE id_tutor=:id_tutor",
		resultSetMapping = "seguimientoPorTutor"
)/**/
public class Seguimiento {
/*	id_seguimiento
	nombre (obligatorio)
	id_tutor
	id_estudiante
	objetivos (obligatorio)
	objetivos_especificos
	id_estado_proyecto
	id_tipo_seguimiento
	id_estado_seguimiento*/
	
	@Id
    @Column(name = "id_seguimiento")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    private int id_seguimiento;
    
    @Column(name = "nombre")
    @NotNull
    private String nombre;

    @OneToOne
    @JoinColumn(name = "id_tutor")
    private Tutor tutor;

    @Column(name = "codirector")
    private String codirector;
/*
	@OneToOne
	@JoinColumn(name = "id_tutor_codirector")
	private Tutor codirector;
    */
    @OneToOne
    @JoinColumn(name = "est_id")
    private Estudiante estudiante;

	@Column(name = "cohorte")
	private String cohorte;
    
    @Column(name = "objetivo_general")
    @NotNull
    private String objetivo_general;
    
    @Column(name = "objetivos_especificos")
    private String objetivos_especificos;
    
    @OneToOne
    @JoinColumn(name = "id_estado_proyecto")
    private EstadoProyecto estado_proyecto;
    
    @OneToOne
    @JoinColumn(name = "id_tipo_seguimiento")
    private TipoSeguimiento tipo_seguimiento;
    
    @OneToOne
    @JoinColumn(name = "id_estado_seguimiento")
    private EstadoSeguimiento estado_seguimiento;

	public int getIdSeguimiento() {
		return id_seguimiento;
	}

	public void setIdSeguimiento(int id_seguimiento) {
		this.id_seguimiento = id_seguimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public String getObjetivoGeneral() {
		return objetivo_general;
	}

	public void setObjetivoGeneral(String objetivo_general) {
		this.objetivo_general = objetivo_general;
	}

	public String getObjetivosEspecificos() {
		return objetivos_especificos;
	}

	public void setObjetivosEspecificos(String objetivos_especificos) {
		this.objetivos_especificos = objetivos_especificos;
	}

	public EstadoProyecto getEstadoProyecto() {
		return estado_proyecto;
	}

	public void setEstadoProyecto(EstadoProyecto estado_proyecto) {
		this.estado_proyecto = estado_proyecto;
	}

	public TipoSeguimiento getTipoSeguimiento() {
		return tipo_seguimiento;
	}

	public void setTipoSeguimiento(TipoSeguimiento tipo_seguimiento) {
		this.tipo_seguimiento = tipo_seguimiento;
	}

	public EstadoSeguimiento getEstadoSeguimiento() {
		return estado_seguimiento;
	}

	public void setEstadoSeguimiento(EstadoSeguimiento estado_seguimiento) {
		this.estado_seguimiento = estado_seguimiento;
	}


	public String getCodirector() {
		return codirector;
	}

	public void setCodirector(String codirector) {
		this.codirector = codirector;
	}

}
