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

    // --------------------------------------------------
    // ALTA
    // --------------------------------------------------

    public Long agregar(Pedido pedido, Plato plato, int cantidad) throws Exception {

        if (pedido == null) {
            throw new Exception("Pedido nulo");
        }

        if (plato == null) {
            throw new Exception("Plato nulo");
        }

        if (cantidad <= 0) {
            throw new Exception("Cantidad debe ser > 0");
        }

        DetallePedido detallePedido = new DetallePedido(pedido, plato, cantidad);

        return dao.agregar(detallePedido);
    }

    public Long agregar(DetallePedido detallePedido) throws Exception {

        if (detallePedido == null) {
            throw new Exception("Detalle nulo");
        }

        if (detallePedido.getCantidad() <= 0) {
            throw new Exception("Cantidad debe ser > 0");
        }

        return dao.agregar(detallePedido);
    }

    // --------------------------------------------------
    // TRAER
    // --------------------------------------------------

    public DetallePedido traer(long id) throws Exception {

        if (id <= 0) {
            throw new Exception("Id invalido");
        }

        DetallePedido d = dao.traer(id);

        if (d == null) {
            throw new Exception("No existe DetallePedido con id: " + id);
        }

        return d;
    }


    public Set<DetallePedido> traerTodas() {
        return dao.traerTodas();
    }

    // --------------------------------------------------
    // MODIFICACION Y BAJA
    // --------------------------------------------------

    public void actualizar(DetallePedido d) throws Exception {

        if (d == null) {
            throw new Exception("Detalle nulo para actualizar");
        }

        dao.actualizar(d);
    }

    public void eliminar(long id) throws Exception {

        DetallePedido d = dao.traer(id);

        if (d == null) {
            throw new Exception("No existe DetallePedido con id: " + id + " para eliminar");
        }

        dao.eliminar(d);
    }

}