package negocio;

import java.time.LocalDate;
import java.util.List;

import dao.PedidoDao;
import datos.Festival;
import datos.Pedido;
import datos.UnidadVenta;

public class PedidoABM {

    PedidoDao dao = new PedidoDao();

    // con unidadVenta
    public Long agregar(LocalDate fechaTransaccion, Festival festival, UnidadVenta unidadVenta) {
        Pedido pedido = new Pedido(fechaTransaccion);
        pedido.setFestival(festival);
        pedido.setUnidadVenta(unidadVenta);
        return dao.agregar(pedido);
    }

    public Pedido traer(long idPedido) {
        return dao.traer(idPedido);
    }

    public Long agregar(Pedido pedido) {
        return dao.agregar(pedido);
    }
    
    public List<Pedido> traerTodas() {
        return dao.traerTodas();
    }

    public List<Pedido> traerPorUnidadVenta(long idUnidad) {
        return dao.traerPorUnidadVenta(idUnidad);
    }

    public void actualizar(Pedido p) {
        dao.actualizar(p);
    }

    public void eliminar(Pedido p) {
        dao.eliminar(p);
    }
  

  
    
    
    
}