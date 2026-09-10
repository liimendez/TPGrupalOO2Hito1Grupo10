package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import datos.Cajero;
import datos.Pedido;
import datos.Plato;
import datos.UnidadVenta;
import negocio.CajeroABM;
import negocio.PedidoABM;
import negocio.PlatoABM;
import negocio.UnidadVentaABM;

public class TestAgregarPedido {
    public static void main(String[] args) {
    	// agrega 108 pedidos 
        PedidoABM pedidoABM = PedidoABM.getInstancia();
        UnidadVentaABM unidadABM = UnidadVentaABM.getInstancia();
        PlatoABM platoABM = PlatoABM.getInstancia();
        CajeroABM cajeroABM = CajeroABM.getInstancia();

        List<UnidadVenta> unidades = new ArrayList<>(unidadABM.traerTodas());
        List<Plato> platos = new ArrayList<>(platoABM.traerTodas());
        List<Cajero> cajeros = new ArrayList<>(cajeroABM.traerTodas());

        System.out.println("Unidades: " + unidades.size() + " | Platos: " + platos.size() + " | Cajeros: " + cajeros.size());

        if (cajeros.isEmpty()) {
            System.out.println("No hay cajeros, corre TestAgregarCajero primero");
            return;
        }

        Random rand = new Random();
        int pedidosPorUnidad = 3;

        for (UnidadVenta unidad : unidades) {
            for (int i = 0; i < pedidosPorUnidad; i++) {
                Cajero cajero = cajeros.get(rand.nextInt(cajeros.size()));
                
                Pedido pedido = new Pedido(LocalDate.now().minusDays(rand.nextInt(5)), unidad, cajero);

                int cantPlatos = rand.nextInt(3) + 1;
                for (int j = 0; j < cantPlatos; j++) {
                    Plato plato = platos.get(rand.nextInt(platos.size()));
                    int cantidad = rand.nextInt(2) + 1;
                    pedido.agregarDetalle(plato, cantidad);
                }

                long id = pedidoABM.agregar(pedido);
                System.out.println("Pedido " + id + " -> Unidad " + unidad.getId() + " Cajero " + cajero.getId() + " (" + cajero.getNombre() + ")");
            }
        }
        System.out.println("\n¡Listo! " + (unidades.size() * pedidosPorUnidad) + " pedidos creados.");
    }
}