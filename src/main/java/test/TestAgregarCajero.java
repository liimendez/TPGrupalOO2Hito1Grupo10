package test;

import java.time.LocalDate;
import datos.Cajero;
import datos.UnidadVenta;
import negocio.CajeroABM;
import negocio.UnidadVentaABM;

public class TestAgregarCajero {
    public static void main(String[] args) {
        try {
            CajeroABM cajeroAbm = CajeroABM.getInstancia();
            UnidadVentaABM uvAbm = UnidadVentaABM.getInstancia();

            UnidadVenta unidad = uvAbm.traer(7L);
            if (unidad == null) {
                System.out.println("No existe la unidad 7");
                return;
            }

            Cajero cajero = new Cajero("Lucas","Fernandez","22222222", LocalDate.of(1995, 3, 18), LocalDate.now(), 600000, Cajero.Turno.MANIANA, unidad );

          
            long id = cajeroAbm.agregar(cajero);
            
            System.out.println("Cajero agregado correctamente.");
            System.out.println("ID Cajero: " + id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}