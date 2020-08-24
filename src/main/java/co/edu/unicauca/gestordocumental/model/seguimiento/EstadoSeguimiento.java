package co.edu.unicauca.gestordocumental.model.seguimiento;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_estado_seguimiento")
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
