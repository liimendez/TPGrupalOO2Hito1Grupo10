package negocio;
<<<<<<< HEAD
import java.time.LocalDate;
=======

import java.time.LocalDate;

>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
import dao.PedidoDao;
import datos.Festival;
import datos.Pedido;
import datos.UnidadVenta;
<<<<<<< HEAD
=======

>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
public class PedidoABM {
	
	PedidoDao dao = new PedidoDao();
	
    // con unidadVenta
<<<<<<< HEAD
    public Long agregar(LocalDate fechaTransaccion, Festival festival, UnidadVenta unidadVenta) {
        Pedido pedido = new Pedido(fechaTransaccion);
        pedido.setFestival(festival);
        pedido.setUnidadVenta(unidadVenta);
        return dao.agregar(pedido); 
    }
=======
    public int agregar(LocalDate fechaTransaccion, Festival festival, UnidadVenta unidadVenta) {

        Pedido pedido = new Pedido(fechaTransaccion);
        pedido.setFestival(festival);
        pedido.setUnidadVenta(unidadVenta);

        return dao.agregar(pedido); 
    }

>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
    
    public Pedido traer(long idPedido) {
    	
        return dao.traer(idPedido);
    }
	
<<<<<<< HEAD
    public Long agregar(Pedido pedido) {
        return dao.agregar(pedido);
    }
}
=======
	
}
>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
