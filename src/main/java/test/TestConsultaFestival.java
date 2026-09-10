package test;

import java.util.Set;
import datos.UnidadVenta;
import negocio.UnidadVentaABM;

public class TestConsultaFestival {

    public static void main(String[] args) {

        UnidadVentaABM abm = UnidadVentaABM.getInstancia();

        // =====================================================
        // 1. ORDENADAS POR FESTIVAL
        // =====================================================
        // muchas unidades de venta pertenecen a un festival : UnidadVenta -> Festival (ManyToOne)
        System.out.println("\n========== UNIDADES ORDENADAS POR FESTIVAL ==========\n");
        Set<UnidadVenta> ordenadasPorFestival = abm.traerOrdenadasPorFestival();

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
        System.out.println("\n========== UNIDAD CON MAYOR SUPERFICIE ==========\n");
        UnidadVenta mayor = abm.traerUnidadConMayorSuperficie();

        if (mayor != null) {
            System.out.println("ID: " + mayor.getId());
            System.out.println("Nombre: " + mayor.getNombreComercial());
            System.out.println("Código: " + mayor.getCodigoUnico());
            System.out.println("Superficie: " + mayor.getSuperficieM2() + " m²");
            System.out.println("Festival: " + mayor.getFestival().getId());
        }

        // =====================================================
        // 3. UNIDAD CON MENOR SUPERFICIE
        // =====================================================
        System.out.println("\n========== UNIDAD CON MENOR SUPERFICIE ==========\n");
        UnidadVenta menor = abm.traerUnidadConMenorSuperficie();
        
        if (menor != null) {
            System.out.println("ID: " + menor.getId());
            System.out.println("Nombre: " + menor.getNombreComercial());
            System.out.println("Superficie: " + menor.getSuperficieM2() + " m²");
        }

        // =====================================================
        // 4. RANKING COMPLETO
        // =====================================================
        System.out.println("\n--- Ranking completo por superficie (mayor a menor) ---");
        Set<UnidadVenta> ranking = abm.traerOrdenadasPorMayorSuperficie();
        for (UnidadVenta u : ranking) {
            System.out.println(u.getNombreComercial() + " -> " + u.getSuperficieM2() + " m²");
        }
    }
}