package test;

import java.time.LocalDate;

import datos.Cocinero;
import negocio.CocineroABM;

public class TestAgregarCocinero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CocineroABM cocineroABM = new CocineroABM();
		
		Cocinero cocinero = new Cocinero("Hernan", "Lopez", "11111111", LocalDate.of(1999, 2, 10), LocalDate.now() ,800000, "Parrillero", 100000);
		int id = cocineroABM.agregar(cocinero);
		
		System.out.println("Cocinero agregado correctamente.");
        System.out.println("ID Cocinero: " + id);
        
        
	}	
}
