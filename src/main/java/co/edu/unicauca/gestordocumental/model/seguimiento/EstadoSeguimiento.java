package co.edu.unicauca.gestordocumental.model.seguimiento;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "estado_seguimiento")
public class EstadoSeguimiento {
	@Id
    @Column(name="id_estado_seguimiento", updatable = false, unique = true)
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private int id_estado_seguimiento;
	
	@Column(name = "nombre")
    private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdEstadoSeguimiento() {
		return id_estado_seguimiento;
	}

	public void setIdEstadoSeguimiento(int id_estado_seguimiento) {
		this.id_estado_seguimiento = id_estado_seguimiento;
	}
}
