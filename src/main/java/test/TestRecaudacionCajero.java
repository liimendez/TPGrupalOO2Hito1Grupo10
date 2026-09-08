package test;
import negocio.CajeroABM;

public class TestRecaudacionCajero {
    public static void main(String[] args) {
        CajeroABM abm = new CajeroABM();
        for(long i=1; i<=40; i++) { 
            try {
                double total = abm.calcularRecaudacion(i);
                if(total>0) System.out.println("Cajero ID "+i+" -> $"+total);
            } catch(Exception e) {}
        }
        System.out.println("Recaudaciones actualizadas, refresca phpMyAdmin");
    }
}