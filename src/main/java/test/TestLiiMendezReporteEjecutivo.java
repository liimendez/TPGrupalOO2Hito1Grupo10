package test;

import datos.Cajero;
import datos.Festival;
import datos.Plato;
import datos.UnidadVenta;
import negocio.PedidoABM;

public class TestLiiMendezReporteEjecutivo {
    public static void main(String[] args) {
        PedidoABM abm = PedidoABM.getInstancia();
        
        //CU-01: Generar Reporte Ejecutivo de Recaudación - Festival TOP
       // Es un Caso de Uso de Consulta / Reporte Gerencial.
        
        // corregido sin usar id, se pasa el objeto directamente por parametro
        
        Festival festivalTop = abm.traerFestivalQueMasRecaudo();
        double recFestival = abm.traerRecaudacionDeFestival(festivalTop);

        UnidadVenta unidadTop = abm.traerUnidadQueMasRecaudoEnFestival(festivalTop);
        double recUnidad = abm.traerRecaudacionUnidadEnFestival(festivalTop, unidadTop);

        Plato masVendido = abm.traerPlatoMasVendidoEnFestival(festivalTop);
        Plato masRentable = abm.traerPlatoMasRentableEnFestival(festivalTop);

        Cajero cajeroTop = abm.traerCajeroQueMasRecaudoEnFestival(festivalTop);
        double recCajero = abm.calcularRecaudacionPorCajeroEnFestival(cajeroTop, festivalTop);

       
        String linea = "════════════════════════════════════════════════════════════";
        System.out.println("\n" + linea);
        System.out.println("          REPORTE EJECUTIVO - EPICENTRO GOURMET");
        System.out.println(linea);

        System.out.printf(" %-20s : %s (ID: %d)%n", "Festival TOP", festivalTop.getNombre(), festivalTop.getId());
        System.out.printf(" %-20s : $%,.2f%n", "Recaudación Festival", recFestival);
        System.out.println(" ────────────────────────────────────────────────────────────");
        System.out.printf(" %-20s : %s (ID: %d)%n", "Unidad TOP", unidadTop.getNombreComercial(), unidadTop.getId());
        System.out.printf(" %-20s : %s%n", "Tipo Unidad", unidadTop.getClass().getSimpleName());
        System.out.printf(" %-20s : $%,.2f%n", "Recaudación Unidad", recUnidad);
        System.out.println(" ────────────────────────────────────────────────────────────");
        System.out.printf(" %-20s : %s (ID: %d)%n", "Plato + Vendido", masVendido.getNombre(), masVendido.getId());
        System.out.printf(" %-20s : $%,.2f c/u%n", "Precio Venta", masVendido.getPrecioVenta());
        System.out.printf(" %-20s : %s (ID: %d)%n", "Plato + Rentable", masRentable.getNombre(), masRentable.getId());
        System.out.println(" ────────────────────────────────────────────────────────────");
        System.out.printf(" %-20s : %s %s (ID: %d)%n", "Cajero TOP", cajeroTop.getNombre(), cajeroTop.getApellido(), cajeroTop.getId());
        System.out.printf(" %-20s : $%,.2f%n", "Recaudado por Cajero", recCajero);
        System.out.println(linea);
        System.out.printf(" TOTAL GENERAL FESTIVAL : $%,.2f%n", recFestival);
        System.out.println(linea + "\n");
    }
}