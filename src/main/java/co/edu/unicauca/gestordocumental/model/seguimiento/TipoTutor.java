package co.edu.unicauca.gestordocumental.model.seguimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "tipo_tutor")
public class TipoTutor {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_tutor")
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
