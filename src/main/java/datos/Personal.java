package datos;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(nullable = false)
	protected String nombre;

	@Column(nullable = false)
	protected String apellido;

	@Column(nullable = false, unique = true, length = 8)
	protected String dni;

	@Column(name = "fecha_de_nacimiento", nullable = false)
	protected LocalDate fechaDeNacimiento;

	@Column(name = "fecha_de_ingreso", nullable = false)
	protected LocalDate fechaDeIngreso;

	@Column(name = "sueldo_base", nullable = false)
	protected double sueldoBase;

	@ManyToOne
	@JoinColumn(name = "unidad_venta_id")
	protected UnidadVenta unidadAsignada;

	protected Personal() {
		// Constructor vacio requerido por Hibernate
	}

	public Personal(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
			LocalDate fechaDeIngreso, double sueldoBase) {
		if (Period.between(fechaDeNacimiento, LocalDate.now()).getYears() < 18) {
			throw new IllegalArgumentException("El personal debe ser mayor de edad");
		}
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.fechaDeIngreso = fechaDeIngreso;
		this.sueldoBase = sueldoBase;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDate getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public LocalDate getFechaDeIngreso() {
		return fechaDeIngreso;
	}

	public void setFechaDeIngreso(LocalDate fechaDeIngreso) {
		this.fechaDeIngreso = fechaDeIngreso;
	}

	public double getSueldoBase() {
		return sueldoBase;
	}

	public void setSueldoBase(double sueldoBase) {
		this.sueldoBase = sueldoBase;
	}

	public int getEdad() {
		return Period.between(fechaDeNacimiento, LocalDate.now()).getYears();
	}

	public boolean esMayorDeEdad() {
		return getEdad() >= 18;
	}

	public UnidadVenta getUnidadAsignada() {
		return unidadAsignada;
	}

	public void setUnidadAsignada(UnidadVenta unidadAsignada) {
		this.unidadAsignada = unidadAsignada;
	}

	public int getAntiguedad() {
		return Period.between(fechaDeIngreso, LocalDate.now()).getYears();
	}

	public abstract double calcularSueldo();

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Personal other = (Personal) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Personal [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaDeNacimiento="
				+ fechaDeNacimiento + ", fechaDeIngreso=" + fechaDeIngreso + ", sueldoBase=" + sueldoBase + "]";
	}
}
