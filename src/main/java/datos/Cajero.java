package datos;

import java.time.LocalDate;

public class Cajero extends Personal {
	

	//private Long id;
	private Turno turno;
	private double recaudacionTotal;

	public enum Turno {
		MANIANA, NOCHE
	}


	protected Cajero() {
		// Constructor vacio requerido por Hibernate
	}


	public Cajero(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
	        LocalDate fechaDeIngreso, double sueldoBase, Turno turno) {
	    super(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase);
	    this.turno = turno;
	}

	/* 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
*/
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
	
	public double getRecaudacionTotal() {
		return recaudacionTotal;
	}

	public void setRecaudacionTotal(double recaudacionTotal) {
		this.recaudacionTotal = recaudacionTotal;
	}

	
	@Override
	public String toString() {
	    return super.toString()+ "Cajero recaudacionTotal=" + recaudacionTotal
	           + ", turno=" + turno + "]";
	}


	
}
