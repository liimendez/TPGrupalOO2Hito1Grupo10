package test;

import java.util.List;

import datos.Festival;
import datos.FoodTruck;
import datos.Plato;
import negocio.FestivalABM;
import negocio.UnidadVentaABM;

public class TestConsultaPrecioMaxFT {

	public static void main(String[] args) {

		FestivalABM festivalABM = FestivalABM.getInstancia();
		UnidadVentaABM unidadVentaABM = UnidadVentaABM.getInstancia();

		long idFestival = 2L;

		// precioMaximo
		double precioMaximo = 7500.0;

		try {
			Festival festival = festivalABM.traer(idFestival);
			List<FoodTruck> foodTrucks = unidadVentaABM.traerFoodTrucksPorFestivalYPrecioPlatoMax(festival, precioMaximo);

			System.out.println("CONSULTA 3 - Trae todos los FoodTruck de un Festival que ofrezcan al menos un Plato con precio de venta menor o igual a un valor indicado (precioMaximo)\n"
							+ "y al traer esos FoodTruck solo mostrara los platos que no superen dicho valor indicado(precioMaximo)\n");
			System.out.println("UnidadVenta <----- PuestoDesarmable | Herencia");
			System.out.println("UnidadVenta(FoodTruck) (1) ----- (0...*) Plato | Uno a Muchos\n");
			System.out.println("------------------------------------------");

			System.out.println("Festival: " + festival.getNombre());
			System.out.println("precioMaximo: $" + precioMaximo);

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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/*
		 * // Mostrar los datos utilizando el toString() for (FoodTruck ft : foodTrucks)
		 * { System.out.printf("\n%s\n", ft); System.out.println("  Platos ofrecidos:");
		 * for (Plato plato : ft.getPlatosOfrecidos()) {
		 * System.out.printf("   - %s: $%.2f\n", plato.getNombre(),
		 * plato.getPrecioVenta()); } }
		 */
	}
}