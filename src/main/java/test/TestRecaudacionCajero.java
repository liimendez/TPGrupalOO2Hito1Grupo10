package test;

import datos.Cajero;
import negocio.CajeroABM;

public class TestRecaudacionCajero {

    public static void main(String[] args) {

        CajeroABM abm = CajeroABM.getInstancia();
        
        System.out.println("========== RECAUDACION POR CAJERO ==========\n");
        for (Cajero c : abm.traerTodas()) {
            try {
                double recaudacion = abm.calcularRecaudacion(c.getId());
            
                System.out.println("Cajero: " + c.getNombre() + " " + c.getApellido() + 
                                   " | ID: " + c.getId() + 
                                   " | Turno: " + c.getTurno() + 
                                   " | Unidad: " + c.getUnidadAsignada().getNombreComercial() +
                                   " | Recaudación: $" + recaudacion);
            } catch (Exception e) {
                System.out.println("Error con cajero ID: " + c.getId());
                e.printStackTrace();
            }
        }
    }
}