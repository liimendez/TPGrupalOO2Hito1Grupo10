package test;

import java.util.List;

import datos.Festival;
import datos.FoodTruck;
import datos.Plato;
import negocio.FestivalABM;
import negocio.UnidadVentaABM;

public class TestConsultaPrecioMinFT {

	public static void main(String[] args) {

		FestivalABM festivalABM = FestivalABM.getInstancia();
		UnidadVentaABM unidadVentaABM = UnidadVentaABM.getInstancia();

		long idFestival = 2L;

		Festival festival = festivalABM.traer(idFestival);

		if (festival == null) {
			System.out.println("No existe el festival con el id " + idFestival);
			return;
		}

		// precioMinimo
		double precioMinimo = 14000.0;

		// CONSULTA
		List<FoodTruck> foodTrucks = unidadVentaABM.traerFoodTrucksPorFestivalYPrecioPlato(festival, precioMinimo);

		System.out.println("CONSULTA 3 - Trae todos los FoodTruck de un Festival que ofrezcan al menos un Plato que su precio de venta sea mayor a un valor indicado(precioMinimo)\n"
				                         + "y al traer esos FoodTruck solo mostrara los platos que superen dicho valor indicado(precioMinimo)");
		System.out.println("------------------------------------------");

		System.out.println("Festival: " + festival.getNombre());
		System.out.println("precioMinimo: $" + precioMinimo);

		if (foodTrucks.isEmpty()) {
			System.out.println("No se encontraron food trucks con platos que superen el precio indicado.");
			return;
		}

		// Mostrar los datos mediante getters
		for (FoodTruck ft : foodTrucks) {

			System.out.println("------------------------------------------");

			System.out.println("ID: " + ft.getId());
			System.out.println("Nombre comercial: " + ft.getNombreComercial());
			System.out.println("Código único: " + ft.getCodigoUnico());
			System.out.println("Patente: " + ft.getPatente());
			System.out.println("Requiere conexión eléctrica: " + ft.isRequiereConexionElectrica());
			
			// Platos ofrecidos de cada FoodTruck que cumplan con la condicion
			System.out.println("Platos ofrecidos:");
			for (Plato plato : ft.getPlatosOfrecidos()) {
				System.out.println("  - " + plato.getNombre() + ": $" + plato.getPrecioVenta());
			}
		}

		/*
		// Mostrar los datos utilizando el toString()
		for (FoodTruck ft : foodTrucks) {
			System.out.printf("\n%s\n", ft);
			System.out.println("  Platos ofrecidos:");
			for (Plato plato : ft.getPlatosOfrecidos()) {
				System.out.printf("   - %s: $%.2f\n", plato.getNombre(), plato.getPrecioVenta());
			}
		}
		*/
	}
}