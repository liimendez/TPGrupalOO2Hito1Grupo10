package test;

import java.time.LocalDate;

import datos.Festival;
import datos.Pedido;
import datos.Plato;
import datos.UnidadVenta;
import negocio.FestivalABM;
import negocio.PedidoABM;
import negocio.PlatoABM;
import negocio.UnidadVentaABM;

public class TestAgregarPedido {

    public static void main(String[] args) {

        PlatoABM platoABM = new PlatoABM();
        PedidoABM pedidoABM = new PedidoABM();
        FestivalABM festivalABM = new FestivalABM();
        UnidadVentaABM unidadVentaABM = new UnidadVentaABM();

        // Traemos un plato existente
        Plato plato = platoABM.traer(1L);

        // Traemos un festival existente
        Festival festival = festivalABM.traer(1L);

        // Traemos una unidad de venta existente
        UnidadVenta unidadVenta = unidadVentaABM.traer(1L);

        // Creamos el pedido
        Pedido pedido = new Pedido(LocalDate.now());
        pedido.setFestival(festival);
        pedido.setUnidadVenta(unidadVenta);

        // Agregamos el detalle al pedido
        pedido.agregarDetalle(plato, 2);

        // Guardamos el pedido
        Long id = pedidoABM.agregar(pedido);

        System.out.println("Pedido agregado con ID: " + id);
        System.out.println("Total del pedido: " + pedido.calcularTotal());
    }
}