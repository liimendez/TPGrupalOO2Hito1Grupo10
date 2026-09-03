
package negocio;

import dao.DetallePedidoDao;
import datos.DetallePedido;
import datos.Pedido;
import datos.Plato;

public class DetallePedidoABM {

    DetallePedidoDao dao = new DetallePedidoDao();

    public Long agregar(Pedido pedido, Plato plato, int cantidad) {

        DetallePedido detallePedido =
                new DetallePedido(pedido, plato, cantidad);

        return dao.agregar(detallePedido);
    }

    public DetallePedido traer(long id) {
        return dao.traer(id);
    }
}


