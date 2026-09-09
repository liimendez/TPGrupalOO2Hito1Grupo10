package test;

import java.util.Set;
import datos.UnidadVenta;
import negocio.UnidadVentaABM;

public class TestConsultaFestival {

    public static void main(String[] args) {

        try {
            UnidadVentaABM unidadVentaABM = UnidadVentaABM.getInstancia();

            // =====================================================
            // 1. ORDENADAS POR FESTIVAL
            // =====================================================
       
            
            //  lo traigo ordenado desde el HQL: order by f.id 
            Set<UnidadVenta> ordenadasPorFestival = unidadVentaABM.traerOrdenadasPorFestival();

            if (ordenadasPorFestival.isEmpty()) {
                System.out.println("No hay unidades de venta cargadas.");
                return;
            }
            System.out.println("\n========== UNIDADES ORDENADAS POR FESTIVAL ==========\n");
            for (UnidadVenta unidad : ordenadasPorFestival) {
                System.out.println(
                        "Festival: " + unidad.getFestival().getId()
                        + " | ID: " + unidad.getId()
                        + " | Nombre: " + unidad.getNombreComercial()
                        + " | Superficie: " + unidad.getSuperficieM2() + " m²"
                );
            }

            // =====================================================
            // 2. UNIDAD CON MAYOR SUPERFICIE
            // =====================================================
            System.out.println("\n========== UNIDADES ORDENADAS POR MAYOR SUPERFICIE ==========\n");

            // Ya viene ordenado desde el HQL: order by u.superficieM2 desc
            Set<UnidadVenta> ordenadasPorSuperficie = unidadVentaABM.traerOrdenadasPorMayorSuperficie();
            
            // Como LinkedHashSet mantiene el orden del HQL, la primera es la mayor
            UnidadVenta mayorSuperficie = ordenadasPorSuperficie.iterator().next();

            System.out.println("ID: " + mayorSuperficie.getId());
            System.out.println("Nombre: " + mayorSuperficie.getNombreComercial());
            System.out.println("Código: " + mayorSuperficie.getCodigoUnico());
            System.out.println("Superficie: " + mayorSuperficie.getSuperficieM2() + " m²");
            System.out.println("Festival: " + mayorSuperficie.getFestival().getId());

          
            System.out.println("\n--- Ranking completo por superficie ---");
            for (UnidadVenta u : ordenadasPorSuperficie) {
                 System.out.println(u.getNombreComercial() + " -> " + u.getSuperficieM2() + " m²");
            }

        } catch (Exception e) {
            System.out.println("Error al realizar las consultas.");
            e.printStackTrace();
        }
    }
}