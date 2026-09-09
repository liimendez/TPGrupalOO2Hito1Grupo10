package test;

import java.time.LocalDate;
import negocio.CocineroABM;

public class TestAgregarCocinero {

    public static void main(String[] args) {
        try {
            CocineroABM cocineroABM = CocineroABM.getInstancia();
            
            long id = cocineroABM.agregar("Hernan", "Lopez", "11111111", LocalDate.of(1999, 2, 10), LocalDate.now(), 800000, "Parrillero", 100000);
            
            System.out.println("Cocinero agregado correctamente.");
            System.out.println("ID Cocinero: " + id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}