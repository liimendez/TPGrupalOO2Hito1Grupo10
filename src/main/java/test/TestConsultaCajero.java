package test;

import datos.Cajero;
import negocio.CajeroABM;

public class TestConsultaCajero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CajeroABM cajeroAbm = new CajeroABM();
		
		Cajero cajero = cajeroAbm.traer(2L);
		
		System.out.println(cajero);
	}

}
