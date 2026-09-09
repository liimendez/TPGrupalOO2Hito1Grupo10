
package datos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Pedido {

    private Long id;
    private LocalDate fechaTransaccion;
    private UnidadVenta unidadVenta;

    private Cajero cajero; // sera el encargado de recaudar el dinero 
    private Set<DetallePedido> detalles = new HashSet<>();

    
    public Pedido() {}

    public Pedido(LocalDate fechaTransaccion, UnidadVenta unidadVenta, Cajero cajero) {
        this.fechaTransaccion = fechaTransaccion;
        this.unidadVenta = unidadVenta;
        this.cajero = cajero;
    }

    public void agregarDetalle(Plato plato, int cantidad) {
        if (plato == null || cantidad <= 0) return;
        
        for (DetallePedido d : this.detalles) {
            if (d.getPlato() != null && plato.getId() != null 
                && d.getPlato().getId().equals(plato.getId())) {
                d.setCantidad(d.getCantidad() + cantidad);
                return;
            }
        }
        
        DetallePedido nuevo = new DetallePedido(plato, cantidad, this);
        this.detalles.add(nuevo);
    }

    public double calcularTotal() {
        return detalles.stream()
                .mapToDouble(d -> d.getPlato().getPrecioVenta() * d.getCantidad())
                .sum();
    }
    
    // getter de festival calculado, no guardado
    public Festival getFestival() {
        return unidadVenta != null ? unidadVenta.getFestival() : null;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(LocalDate fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public UnidadVenta getUnidadVenta() {
		return unidadVenta;
	}

	public void setUnidadVenta(UnidadVenta unidadVenta) {
		this.unidadVenta = unidadVenta;
	}

	public Set<DetallePedido> getDetalles() {
		return detalles;
	}

	public Cajero getCajero() {
		return cajero;
	}

	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}

	public void setDetalles(Set<DetallePedido> detalles) {
		this.detalles = detalles;
	}
	
	 @Override
	    public String toString() {
	        return "Pedido [id=" + id + ", fecha=" + fechaTransaccion + "]";
	    }
	
}

