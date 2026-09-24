package test;

import java.time.LocalDate;

import datos.Festival;
import negocio.FestivalABM;
import negocio.PedidoABM;
import datos.UnidadVenta;

public class TestFrancoHegele {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TEST: FRANCO VALENTIN HEGELE
		// HICE UNA CONSULTA LA CUAL ME RETORNA LA UNIDAD DE VENTA QUE MAS RECAUDO ENTRE 2 FECHAS
		// ADEMAS HICE UNA CONSULTA QUE ME RETORNA EL TOTAL RECAUDADO ENTRE LAS 2 MISMAS FECHAS
		
		// Festival (1)-----(*) UnidadVenta.
		// UnidadVenta (1)-----(*) Pedido.
		// Pedido (1)-----(*) DetallePedido
		// DetallePedido (*)-----(1) Plato.		 
		
		 
		FestivalABM abmFestival = FestivalABM.getInstancia();		
		PedidoABM abmPedido = PedidoABM.getInstancia();
		
		UnidadVenta uv;
		
		Double recaudacion;
 
		try {
			
			Festival festival1 = abmFestival.traerFestival("Festival del Asado");
			Festival festival2 = abmFestival.traerFestival("Festival Gourmet");
			Festival festival3 = abmFestival.traerFestival("Festival de la Milanesa");
			Festival festival4 = abmFestival.traerFestival("Festival Vegano");
			
			uv = abmPedido.traerUnidadVentaMasRecaudadoraEnRangoFestival(festival1, LocalDate.of(2026, 1, 9), LocalDate.of(2026, 1, 10));
			System.out.println(
					"\n- FESTIVAL: " + festival1.getNombre() + 
					"\n- TEMPORADA: " + festival1.getTemporada() + "\n" + 
					"\n- UNIDAD DE VENTA: " + uv.getNombreComercial() + 
					"\n- CODIGO UNICO: " + uv.getCodigoUnico());
			
			recaudacion = abmPedido.calcularRecaudacionEntreDosFechas(festival1, LocalDate.of(2026, 1, 9), LocalDate.of(2026, 1, 10));
			System.out.println("- RECAUDACION: " + recaudacion); 
			
			
			System.out.println("\n ---------------------------------------------------------------------------------------------------- \n");
			
			
			uv = abmPedido.traerUnidadVentaMasRecaudadoraEnRangoFestival(festival2, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 20));
			System.out.println(
					"\n- FESTIVAL: " + festival2.getNombre() + 
					"\n- TEMPORADA: " + festival2.getTemporada() + "\n" +
					"\n- UNIDAD DE VENTA: " + uv.getNombreComercial() +  
					"\n- CODIGO UNICO: " + uv.getCodigoUnico());
			
			recaudacion = abmPedido.calcularRecaudacionEntreDosFechas(festival2, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 20));
			System.out.println("- RECAUDACION: " + recaudacion);
			
			
			System.out.println("\n ---------------------------------------------------------------------------------------------------- \n");
			
			
			uv = abmPedido.traerUnidadVentaMasRecaudadoraEnRangoFestival(festival3, LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 20));
			System.out.println(
					"\n- FESTIVAL: " + festival3.getNombre() + 
					"\n- TEMPORADA: " + festival3.getTemporada() + "\n" +
					"\n- UNIDAD DE VENTA: " + uv.getNombreComercial() + 
					"\n- CODIGO UNICO: " + uv.getCodigoUnico());
			
			recaudacion = abmPedido.calcularRecaudacionEntreDosFechas(festival3, LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 20));
			System.out.println("- RECAUDACION: " + recaudacion);
			
			
			System.out.println("\n ---------------------------------------------------------------------------------------------------- \n");
			
			
			uv = abmPedido.traerUnidadVentaMasRecaudadoraEnRangoFestival(festival4, LocalDate.of(2027, 4, 5), LocalDate.of(2027, 4, 15));
			System.out.println(
					"\n- FESTIVAL: " + festival4.getNombre() + 
					"\n- TEMPORADA: " + festival4.getTemporada() + "\n" +
					"\n- UNIDAD DE VENTA: " + uv.getNombreComercial() + 
					"\n- CODIGO UNICO: " + uv.getCodigoUnico());
			
			recaudacion = abmPedido.calcularRecaudacionEntreDosFechas(festival4, LocalDate.of(2027, 4, 5), LocalDate.of(2027, 4, 15));
			System.out.println("- RECAUDACION: " + recaudacion);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		
		
		
	} 

}
