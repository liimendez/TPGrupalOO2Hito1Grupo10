package datos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_transaccion", nullable = false)
	private LocalDate fechaTransaccion;

	@ManyToOne
	@JoinColumn(name = "festival_id", nullable = false)
	private Festival festival;

	@ManyToOne
	@JoinColumn(name = "unidad_venta_id", nullable = false)
	private UnidadVenta unidadVenta;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetallePedido> detalles = new ArrayList<>();

	protected Pedido() {
		// Constructor vacio requerido por Hibernate
	}

	public Pedido(LocalDate fechaTransaccion, Festival festival, UnidadVenta unidadVenta) {
		this.fechaTransaccion = fechaTransaccion;
		this.festival = festival;
		this.unidadVenta = unidadVenta;
	}

	public void agregarDetalle(Plato plato, int cantidad) {
		DetallePedido detalle = new DetallePedido(this, plato, cantidad);
		detalles.add(detalle);
	}

	public double calcularTotal() {
		return detalles.stream()
				.mapToDouble(d -> d.getPlato().getPrecioVenta() * d.getCantidad())
				.sum();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(LocalDate fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public Festival getFestival() {
		return festival;
	}

	public void setFestival(Festival festival) {
		this.festival = festival;
	}

	public UnidadVenta getUnidadVenta() {
		return unidadVenta;
	}

	public void setUnidadVenta(UnidadVenta unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	@Override
	public String toString() {
		return "Pedido [fechaTransaccion=" + fechaTransaccion + ", unidadVenta=" + unidadVenta.getNombreComercial()
				+ ", total=" + calcularTotal() + "]";
	}
}
