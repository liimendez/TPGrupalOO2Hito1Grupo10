package negocio;

import java.util.Set;
import dao.DetallePedidoDao;
import datos.DetallePedido;
import datos.Pedido;
import datos.Plato;

public class DetallePedidoABM {

    private static DetallePedidoABM instancia = null;
    private DetallePedidoDao dao = DetallePedidoDao.getInstancia();

    protected DetallePedidoABM() {}

    public static DetallePedidoABM getInstancia() {
        if (instancia == null) {
            instancia = new DetallePedidoABM();
        }
        return instancia;
    }

    public Long agregar(Pedido pedido, Plato plato, int cantidad) {
        DetallePedido detallePedido = new DetallePedido(pedido, plato, cantidad);
        return dao.agregar(detallePedido);
    }

    // Sobrecarga
    public Long agregar(DetallePedido detallePedido) {
        return dao.agregar(detallePedido);
    }

    public DetallePedido traer(long id) {
        return dao.traer(id);
    }

    public Set<DetallePedido> traerTodas() {
        return dao.traerTodas();
    }

    public Set<DetallePedido> traerTodos() {
        return traerTodas();
    }

    public void actualizar(DetallePedido d) {
        dao.actualizar(d);
    }

    public void eliminar(long id) {
        DetallePedido d = dao.traer(id);
        if (d != null) {
            dao.eliminar(d);
        }
    }
}