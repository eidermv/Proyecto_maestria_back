package co.edu.unicauca.gestordocumental.model.seguimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "estado_proyecto")
public class EstadoProyecto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_estado_proyecto")
    private int id_estado_proyecto;
	
	@Column(name = "nombre")
    private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdEstadoSeguimiento() {
		return id_estado_proyecto;
	}

	public void setIdEstadoSeguimiento(int estado_proyecto) {
		this.id_estado_proyecto = id_estado_proyecto;
	}
}
