package datos;

import java.time.LocalDate;

public class Cajero extends Personal {
	
	private Turno turno;
	private double recaudacionTotal;

	public enum Turno {
		MANIANA, NOCHE
	}

	public Cajero() {
	}

	public Cajero(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
	        LocalDate fechaDeIngreso, double sueldoBase, Turno turno, UnidadVenta unidadAsignada) {
	    super(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase);
	    this.turno = turno;
	    this.unidadAsignada = unidadAsignada; // ESTO TE FALTABA
	    this.recaudacionTotal = calcularRecaudacion();
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	@Override
	public double calcularSueldo() {
		return sueldoBase;
	}
	
	public double calcularRecaudacion() {

	    return this.recaudacionTotal;
	}
	public double getRecaudacionTotal() {
		return recaudacionTotal;
	}

	public void setRecaudacionTotal(double recaudacionTotal) {
		this.recaudacionTotal = recaudacionTotal;
	}
	
	@Override
	public String toString() {
	    return "cajero : "+super.toString()+ " recaudacionTotal=" + recaudacionTotal
	           + ", turno=" + turno + ", unidad=" + (unidadAsignada != null ? unidadAsignada.getId() : "SIN UNIDAD") + "]";
	}
}