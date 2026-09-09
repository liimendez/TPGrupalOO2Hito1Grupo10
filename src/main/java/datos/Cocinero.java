package datos;

import java.time.LocalDate;

public class Cocinero extends Personal {

	private String especialidad;
	private double plusCategoria;

	public Cocinero() {
	}

	public Cocinero(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
			LocalDate fechaDeIngreso, double sueldoBase, String especialidad, double plusCategoria) {
		super(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase);
		this.especialidad = especialidad;
		this.plusCategoria = plusCategoria;
	}
	
	// NUEVO: metodo para calcular el plus de los cocineros... 
	private double calcularPlusSegunEspecialidad() {
		if (especialidad == null) return 0;
		switch (especialidad.toLowerCase().trim()) {
			case "parrilla":
			case "parrillero":
				return sueldoBase * 0.20;
			case "sushi":
			case "wok":
				return sueldoBase * 0.25; 
			case "pizzas":
			case "pastas":
				return sueldoBase * 0.15;
			case "panaderia":
			case "postres":
			case "cocina fria":
				return sueldoBase * 0.10;
			case "fritura":
				return sueldoBase * 0.05;
			case "vegano":
				return sueldoBase * 0.18;
			default:
				return sueldoBase * 0.10;
		}
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
		this.plusCategoria = calcularPlusSegunEspecialidad(); // se actualiza solo
	}

	public double getPlusCategoria() {
		return plusCategoria;
	}

	public void setPlusCategoria(double plusCategoria) {
		this.plusCategoria = plusCategoria;
	}

	@Override
	public double calcularSueldo() {
	
		if (plusCategoria == 0) {
			plusCategoria = calcularPlusSegunEspecialidad();
		}
		return sueldoBase + plusCategoria;
	}

	@Override
	public String toString() {
	    return "cocinero: "+super.toString() + " [dni=" + dni + ", especialidad=" + especialidad + ", plusCategoria=" + plusCategoria + "] Sueldo Final: " + calcularSueldo();
	}
}