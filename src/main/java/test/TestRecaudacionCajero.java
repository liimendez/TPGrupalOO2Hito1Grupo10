package test;

import datos.Cajero;
import negocio.CajeroABM;

public class TestRecaudacionCajero {
    public static void main(String[] args) {
        CajeroABM abm = CajeroABM.getInstancia();
        
        for (Cajero c : abm.traerTodos()) {
            double recaudacion = abm.calcularRecaudacion(c.getId());
            
            System.out.println("Cajero: " + c.getNombre() + " " + c.getApellido() + 
                               " | ID: " + c.getId() + 
                               " | Turno: " + c.getTurno() + 
                               " | Unidad: " + c.getUnidadAsignada().getNombreComercial() +
                               " | Recaudación: $" + recaudacion);
        }
    }
}