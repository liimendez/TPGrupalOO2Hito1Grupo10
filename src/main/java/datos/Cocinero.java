package datos;

import java.time.LocalDate;

public class Cocinero extends Personal {

	//private Long id;
	private String especialidad;

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
	/*
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
*/
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
	    return super.toString() + " [dni=" + dni + ", especialidad=" + especialidad + ", plusCategoria=" + plusCategoria + "]";
	}
}
