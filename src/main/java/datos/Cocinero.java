package datos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cocinero")
@PrimaryKeyJoinColumn(name = "id")
public class Cocinero extends Personal {

	@Column(name = "especialidad", nullable = false)
	private String especialidad;

	@Column(name = "plus_categoria", nullable = false)
	private double plusCategoria;

	protected Cocinero() {
		// Constructor vacio requerido por Hibernate
	}

	public Cocinero(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
			LocalDate fechaDeIngreso, double sueldoBase, String especialidad, double plusCategoria) {
		super(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase);
		this.especialidad = especialidad;
		this.plusCategoria = plusCategoria;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public double getPlusCategoria() {
		return plusCategoria;
	}

	public void setPlusCategoria(double plusCategoria) {
		this.plusCategoria = plusCategoria;
	}

	@Override
	public double calcularSueldo() {
		return sueldoBase + plusCategoria;
	}

	@Override
	public String toString() {
		return "Cocinero [dni=" + dni + ", especialidad=" + especialidad + ", plusCategoria=" + plusCategoria + "]";
	}
}
