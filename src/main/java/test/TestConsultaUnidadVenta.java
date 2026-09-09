package test;

import datos.UnidadVenta;
import negocio.UnidadVentaABM;

public class TestConsultaUnidadVenta {
    public static void main(String[] args) {
        UnidadVentaABM abm = UnidadVentaABM.getInstancia();
        try {
            UnidadVenta unidad = abm.traer(7L);
            
            System.out.println(unidad.mostrarDetalleConPlatos()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}