package datos;

public class DetallePedido {

	private Long id;
	private int cantidad;
	private Pedido pedido;
	private Plato plato;
	private double subtotal;

	protected DetallePedido() {
		// Constructor vacio requerido por Hibernate
	}

	public DetallePedido(Pedido pedido, Plato plato, int cantidad) {
<<<<<<< HEAD
	    this.pedido = pedido;
	    this.plato = plato;
	    this.cantidad = cantidad;
	    this.subtotal = calcularSubtotal();
=======
		this.pedido = pedido;
		this.plato = plato;
		this.cantidad = cantidad;
>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
	}

	public double calcularSubtotal() {
		return plato.getPrecioVenta() * cantidad;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	
	@Override
	public String toString() {
		return "DetallePedido [plato=" + plato.getNombre() + ", cantidad=" + cantidad + "]";
	}
}
