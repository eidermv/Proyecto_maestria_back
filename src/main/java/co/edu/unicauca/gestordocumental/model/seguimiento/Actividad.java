package co.edu.unicauca.gestordocumental.model.seguimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "actividad")
public class Actividad {
/*
 * id_actividad
semana
fecha_inicio (obligatorio)
fecha_entrega
entregas
compromisos (obligatorio)
cumplido (si - 1, no - 0), por defecto no
id_seguimiento
visibilidad (si - 1 o no - 0) - para hacer visible al coordinador, por defecto no

 */
	
	@Id
    @Column(name = "id_actividad")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    private int id_actividad;
	
	@Column(name = "semana")
    private String semana;
	
	@Column(name = "fecha_inicio")
	@NotNull
    private Date fecha_inicio;
	
	@Column(name = "fecha_entrega")
    private Date fecha_entrega;
	
	@Column(name = "entregas")
    private String entregas;
	
	@Column(name = "compromisos")
	@NotNull
    private String compromisos;
	
	// 0 - no, 1 - si
	@Column(name = "cumplida")
    private int cumplida;
	
	@OneToOne
    @JoinColumn(name="id_seguimiento")
    private Seguimiento seguimiento;
	
	// 0 - no, 1 - si
	@Column(name = "visible")
	private int visible;

	public int getIdActividad() {
		return id_actividad;
	}

	public void setIdActividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}

	public String getSemana() {
		return semana;
	}

	public void setSemana(String semana) {
		this.semana = semana;
	}

	public Date getFechaInicio() {
		return fecha_inicio;
	}

	public void setFechaInicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFechaEntrega() {
		return fecha_entrega;
	}

	public void setFechaEntrega(Date fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getEntregas() {
		return entregas;
	}

	public void setEntregas(String entregas) {
		this.entregas = entregas;
	}

	public String getCompromisos() {
		return compromisos;
	}

	public void setCompromisos(String compromisos) {
		this.compromisos = compromisos;
	}

	public int getCumplida() {
		return cumplida;
	}

	public void setCumplida(int cumplida) {
		this.cumplida = cumplida;
	}

	public Seguimiento getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(Seguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}
	
}
