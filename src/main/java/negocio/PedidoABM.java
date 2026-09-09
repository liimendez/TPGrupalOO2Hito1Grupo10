package negocio;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import dao.PedidoDao;
import datos.Cajero;
import datos.DetallePedido;
import datos.Pedido;
import datos.Plato;
import datos.UnidadVenta;

public class PedidoABM {
   
    private static PedidoABM instancia = null;
    private PedidoDao dao = PedidoDao.getInstancia();

    protected PedidoABM() {}

    public static PedidoABM getInstancia() {
        if (instancia == null) {
            instancia = new PedidoABM();
        }
        return instancia;
    }

    public long agregar(LocalDate fecha, UnidadVenta uv, Cajero cajero, Plato plato, int cantidad) {
        Pedido p = new Pedido();
        p.setFechaTransaccion(fecha);
        p.setUnidadVenta(uv);
        p.setCajero(cajero);
        p.setDetalles(new HashSet<>());
        
        DetallePedido d = new DetallePedido();
        d.setPlato(plato);
        d.setCantidad(cantidad);
        d.setPedido(p);
        
        p.getDetalles().add(d);
        
        return dao.agregar(p);
    }

    // Agregar ya armado con detalles
    public Long agregar(Pedido pedido) {
        return dao.agregar(pedido);
    }

    public Pedido traer(long idPedido) {
        return dao.traer(idPedido);
    }

    public Set<Pedido> traerTodas() {
        return dao.traerTodas();
    }

    public Set<Pedido> traerPorUnidadVenta(long idUnidad) {
        return dao.traerPorUnidadVenta(idUnidad);
    }

    public Set<Pedido> traerPorCajero(long idCajero) {
        return dao.traerPorCajero(idCajero);
    }

    public Set<Pedido> traerPedidosOrdenadosParaCorte() {
        return dao.traerPedidosOrdenadosParaCorte();
    }

    public void actualizar(Pedido p) {
        dao.actualizar(p);
    }

    public void eliminar(Pedido p) {
        dao.eliminar(p);
    }
}