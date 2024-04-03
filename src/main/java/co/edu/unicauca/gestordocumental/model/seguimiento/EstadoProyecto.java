package co.edu.unicauca.gestordocumental.model.seguimiento;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table (name = "estado_proyecto")
public class EstadoProyecto {
	@Id
    @Column(name="id_estado_proyecto", updatable = false, unique = true)
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
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
