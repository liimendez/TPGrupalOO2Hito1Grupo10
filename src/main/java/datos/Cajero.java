package datos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cajero")
@PrimaryKeyJoinColumn(name = "id")
public class Cajero extends Personal {

	public enum Turno {
		MANIANA, NOCHE
	}

	@Column(name = "turno", nullable = false)
	private Turno turno;

	protected Cajero() {
		// Constructor vacio requerido por Hibernate
	}

	public Cajero(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
			LocalDate fechaDeIngreso, double sueldoBase, Turno turno) {
		super(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase);
		this.turno = turno;
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

	@Override
	public String toString() {
		return "Cajero [dni=" + dni + ", turno=" + turno + "]";
	}
}
