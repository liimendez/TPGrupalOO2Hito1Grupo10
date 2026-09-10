package test;

import datos.Cajero;
import datos.Festival;
import datos.Plato;
import datos.UnidadVenta;
import negocio.PedidoABM;

public class TestLiiMendezReporteEjecutivo {
    public static void main(String[] args) {
        PedidoABM abm = PedidoABM.getInstancia();

        Festival festivalTop = abm.traerFestivalQueMasRecaudo();
        double recFestival = abm.traerRecaudacionDeFestival(festivalTop.getId());

        UnidadVenta unidadTop = abm.traerUnidadQueMasRecaudoEnFestival(festivalTop.getId());
        double recUnidad = abm.traerRecaudacionDeUnidadTopEnFestival(festivalTop.getId());

        Plato masVendido = abm.traerPlatoMasVendidoEnFestival(festivalTop.getId());
        Plato masRentable = abm.traerPlatoMasRentableEnFestival(festivalTop.getId());

        Cajero cajeroTop = abm.traerCajeroQueMasRecaudoEnFestival(festivalTop.getId());
        double recCajero = abm.calcularRecaudacionPorCajeroEnFestival(cajeroTop.getId(), festivalTop.getId());

        System.out.println("========== REPORTE EJECUTIVO ==========");
        System.out.println("Festival TOP: " + festivalTop.getNombre() + " ID:" + festivalTop.getId() + " | Recaudacion: $" + recFestival);
        System.out.println("Unidad TOP: " + unidadTop.getNombreComercial() + " ID:" + unidadTop.getId() + " | Recaudacion: $" + recUnidad);
        System.out.println("Plato MAS VENDIDO (cantidad): " + masVendido.getNombre() + " ID:" + masVendido.getId());
        System.out.println("Plato MAS RENTABLE (cantidad por precio): " + masRentable.getNombre() + " ID:" + masRentable.getId());
        System.out.println("Cajero TOP en ese festival: " + cajeroTop.getNombre() + " " + cajeroTop.getApellido() + " ID:" + cajeroTop.getId() + " | Recaudo: $" + recCajero);
    }
}