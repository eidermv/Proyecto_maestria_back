package co.edu.unicauca.gestordocumental.model.seguimiento;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table (name = "tipo_seguimiento")
public class TipoSeguimiento {
	@Id
    @Column(name="id_tipo_seguimiento", updatable = false, unique = true)
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
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
