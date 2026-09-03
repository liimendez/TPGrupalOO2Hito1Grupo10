package test;

import java.time.LocalDate;

import datos.Cajero;
import negocio.CajeroABM;

public class TestAgregarCajero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CajeroABM cajeroAbm = new CajeroABM();
		
		Cajero cajero = new Cajero("Lucas", "Fernandez", "22222222", LocalDate.of(1995, 3, 18), LocalDate.now() ,600000, Cajero.Turno.MANIANA);
		int id = cajeroAbm.agregar(cajero);
		
		System.out.println("Cocinero agregado correctamente.");
        System.out.println("ID Cocinero: " + id);
		
	}

}
