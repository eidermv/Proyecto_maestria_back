package co.edu.unicauca.gestordocumental.model.seguimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "tipo_seguimiento")
public class TipoSeguimiento {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_seguimientor")
    private int id_tipo_seguimiento;
	
	@Column(name = "nombre")
    private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdTipoSeguimiento() {
		return id_tipo_seguimiento;
	}

	public void setIdTipoSeguimiento(int id_tipo_seguimiento) {
		this.id_tipo_seguimiento = id_tipo_seguimiento;
	}
}
