package test;

import java.time.LocalDate;

import datos.Cajero;
import negocio.PedidoABM;

public class TestFrancoHegele {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TEST: FRANCO VALENTIN HEGELE
		// REALICE UNA CONSULTA LA CUAL CONSISTE EN TRAER EL CAJERO QUE MAS HAYA RECAUDADO ENTRE 2 FECHAS
		
		// Cajero (1)-----(*) Pedido.
		// Pedido (1)-----(*) DetallePedido.
		// DetallePedido (1)-----(*) Plato.
		
		PedidoABM abm = PedidoABM.getInstancia();
		
		try {
			Cajero cajero1;
			cajero1 = abm.traerCajeroQueMasRecaudoEntreFechas(LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 5));
			System.out.println("\n CAJERO 1 ---> " + cajero1.getNombre() + " " + cajero1.getApellido());
			double recaudancion1 = abm.calcularRecaudacionPorCajeroEntreDosFechas(cajero1.getId(), LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 5));
			System.out.println("\n RECAUDACION ---> " + recaudancion1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\n---------------------------------------------------------");
		
		try {
			Cajero cajero2;
			cajero2 = abm.traerCajeroQueMasRecaudoEntreFechas(LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 6));
			System.out.println("\n CAJERO 2 ---> " + cajero2.getNombre() + " " + cajero2.getApellido());
			double recaudancion2 = abm.calcularRecaudacionPorCajeroEntreDosFechas(cajero2.getId(), LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 6));
			System.out.println("\n RECAUDACION ---> " + recaudancion2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println("\n---------------------------------------------------------"); 
		
		try {
			Cajero cajero3;
			cajero3 = abm.traerCajeroQueMasRecaudoEntreFechas(LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 7));
			System.out.println("\n CAJERO  3 ---> " + cajero3.getNombre() + " " + cajero3.getApellido());
			double recaudancion3 = abm.calcularRecaudacionPorCajeroEntreDosFechas(cajero3.getId(), LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 7));
			System.out.println("\n RECAUDACION ---> " + recaudancion3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("\n---------------------------------------------------------");
		
		try {
			Cajero cajero4;
			cajero4 = abm.traerCajeroQueMasRecaudoEntreFechas(LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
			System.out.println("\n CAJERO  4 ---> " + cajero4.getNombre() + " " + cajero4.getApellido());
			double recaudancion4 = abm.calcularRecaudacionPorCajeroEntreDosFechas(cajero4.getId(), LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
			System.out.println("\n RECAUDACION ---> " + recaudancion4);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	} 

}
