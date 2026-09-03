package test;

import datos.DetallePedido;
import datos.Pedido;
import negocio.PedidoABM;

public class TestConsultaPedido {

    public static void main(String[] args) {

        PedidoABM pedidoABM = new PedidoABM();

        // Traemos el pedido guardado
        Pedido pedido = pedidoABM.traer(2L);

        if (pedido == null) {
            System.out.println("No se encontró el pedido.");
            return;
        }

        System.out.println("Pedido ID: " + pedido.getId());
        System.out.println("Fecha: " + pedido.getFechaTransaccion());
        System.out.println("Festival: " + pedido.getFestival());
        System.out.println("Unidad de venta: " + pedido.getUnidadVenta());
        System.out.println("Total: " + pedido.calcularTotal());

        System.out.println("Detalles:");
        for (DetallePedido detalle : pedido.getDetalles()) {
            System.out.println(" - Plato: " + detalle.getPlato().getNombre()
                    + " | Cantidad: " + detalle.getCantidad()
                    + " | Subtotal: " + detalle.getSubtotal());
        }
    }
}