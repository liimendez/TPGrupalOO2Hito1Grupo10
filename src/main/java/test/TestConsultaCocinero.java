package test;

import datos.Cocinero;
import negocio.CocineroABM;

public class TestConsultaCocinero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CocineroABM cocineroAbm = new CocineroABM();
		
		Cocinero cocinero = cocineroAbm.traer(2L);
		
		System.out.println(cocinero);
	}

}
