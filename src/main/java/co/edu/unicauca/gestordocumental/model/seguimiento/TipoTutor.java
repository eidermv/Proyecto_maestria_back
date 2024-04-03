package co.edu.unicauca.gestordocumental.model.seguimiento;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table (name = "tipo_tutor")
public class TipoTutor {
	@Id
	@Column(name = "id_tipo_tutor", updatable = false, unique = true)
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private int id_tipo_tutor;
	
	@Column(name = "nombre")
    private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdTipoTutor() {
		return id_tipo_tutor;
	}

	public void setIdTipoTutor(int id_tipo_tutor) {
		this.id_tipo_tutor = id_tipo_tutor;
	}
	
	

}
