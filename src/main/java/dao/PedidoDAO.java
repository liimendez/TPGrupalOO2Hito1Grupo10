package dao;

import datos.Pedido;

public class PedidoDAO extends GenericDAO<Pedido, Long> {

	public PedidoDAO() {
		super(Pedido.class);
	}
}
