
package datos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Pedido {

    private Long id;
    private LocalDate fechaTransaccion;
    private Festival festival;
    private UnidadVenta unidadVenta;

    private Set<DetallePedido> detalles = new HashSet<>();

    
    protected Pedido() {
        // Constructor vacio requerido por Hibernate
    }

    public Pedido(LocalDate fechaTransaccion) {
    	
        this.fechaTransaccion = fechaTransaccion;
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

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Pedido [fechaTransaccion=" + fechaTransaccion + ", total=" + calcularTotal() + "]";
    }
}

