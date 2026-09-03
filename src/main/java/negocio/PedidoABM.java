package negocio;

import java.time.LocalDate;

import dao.PedidoDao;
import datos.Festival;
import datos.Pedido;
import datos.UnidadVenta;

public class PedidoABM {
	
	PedidoDao dao = new PedidoDao();
	
    // con unidadVenta
    public int agregar(LocalDate fechaTransaccion, Festival festival, UnidadVenta unidadVenta) {

        Pedido pedido = new Pedido(fechaTransaccion);
        pedido.setFestival(festival);
        pedido.setUnidadVenta(unidadVenta);

        return dao.agregar(pedido); 
    }

    
    public Pedido traer(long idPedido) {
    	
        return dao.traer(idPedido);
    }
	
	
}
