package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import datos.Festival;
import datos.Pedido;
import datos.UnidadVenta;
import negocio.PedidoABM;

public class TestReporteDeRecaudaciones {
    public static void main(String[] args) {
    	
        PedidoABM abm = PedidoABM.getInstancia();
        Set<Pedido> set = abm.traerPedidosOrdenadosParaCorte();
        
        if (set.isEmpty()) {
            System.out.println("No hay pedidos");
            return;
        }
     // es un cu de reportes por festival y unidad de venta 
        
        
        List<Pedido> pedidos = new ArrayList<>(set);
        int i = 0;
        double totalGeneral = 0;

        System.out.println("REPORTE DE RECAUDACIONES ");

        while (i < pedidos.size()) {
            Festival festivalActual = pedidos.get(i).getFestival();
            double totalFestival = 0;
            System.out.println("\n==================================================");
            System.out.println("FESTIVAL: " + festivalActual.getNombre() + " (ID: " + festivalActual.getId() + ")");
            System.out.println("==================================================");

            do {
                UnidadVenta unidadActual = pedidos.get(i).getUnidadVenta();
                double totalUnidad = 0;
                
                
                UnidadVenta real = (UnidadVenta) Hibernate.unproxy(unidadActual);
                System.out.println("\n  -> Unidad Venta: " + unidadActual.getId() + " - " + real.getClass().getSimpleName());

                do {
                    Pedido p = pedidos.get(i);
                    double totalPedido = p.calcularTotal();
                    totalUnidad += totalPedido;
                    System.out.println("      Pedido ID: " + p.getId() + " | Cajero: " + p.getCajero().getNombre() + " | Total: $" + totalPedido);
                    i++;
                } while (i < pedidos.size() 
                        && pedidos.get(i).getFestival().getId().equals(festivalActual.getId())
                        && pedidos.get(i).getUnidadVenta().getId() == unidadActual.getId());

                System.out.println("     TOTAL UNIDAD " + unidadActual.getId() + ": $" + totalUnidad);
                totalFestival += totalUnidad;

            } while (i < pedidos.size() && pedidos.get(i).getFestival().getId().equals(festivalActual.getId()));

            System.out.println("\n  TOTAL FESTIVAL " + festivalActual.getNombre() + ": $" + totalFestival);
            totalGeneral += totalFestival;
        }

        System.out.println("\n**************************************************");
        System.out.println("TOTAL GENERAL RECAUDADO: $" + totalGeneral);
        System.out.println("**************************************************");
    }
}