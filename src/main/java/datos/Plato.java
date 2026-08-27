package datos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "plato")
public class Plato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(name = "precio_venta", nullable = false)
	private double precioVenta;

	@Column(name = "costo_produccion", nullable = false)
	private double costoProduccion;

	@ManyToOne
	@JoinColumn(name = "unidad_venta_id")
	private UnidadVenta unidadVenta;

	protected Plato() {
		// Constructor vacio requerido por Hibernate
	}

	public Plato(String nombre, double precioVenta, double costoProduccion) {
		this.nombre = nombre;
		this.precioVenta = precioVenta;
		this.costoProduccion = costoProduccion;
	}

	public double calcularGanancia() {
		return precioVenta - costoProduccion;
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

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(double costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public UnidadVenta getUnidadVenta() {
		return unidadVenta;
	}

	public void setUnidadVenta(UnidadVenta unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	@Override
	public String toString() {
		return "Plato [nombre=" + nombre + ", precioVenta=" + precioVenta + ", costoProduccion=" + costoProduccion
				+ "]";
	}
}
