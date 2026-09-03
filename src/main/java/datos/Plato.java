package datos;

public class Plato {

	private Long id;
	private String nombre;
	private double precioVenta;
	private double costoProduccion;
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


	public void setId(Long id) {
		this.id = id;
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
