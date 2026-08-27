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
@Table(name = "detalle_pedido")
public class DetallePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int cantidad;

	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "plato_id", nullable = false)
	private Plato plato;

	protected DetallePedido() {
		// Constructor vacio requerido por Hibernate
	}

	public DetallePedido(Pedido pedido, Plato plato, int cantidad) {
		this.pedido = pedido;
		this.plato = plato;
		this.cantidad = cantidad;
	}

	public double calcularSubtotal() {
		return plato.getPrecioVenta() * cantidad;
	}

	public Long getId() {
		return id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	@Override
	public String toString() {
		return "DetallePedido [plato=" + plato.getNombre() + ", cantidad=" + cantidad + "]";
	}
}
