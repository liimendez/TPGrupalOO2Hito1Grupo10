package test;

import java.util.List;

import datos.Festival;
import datos.FoodTruck;
import negocio.FestivalABM;
import negocio.UnidadVentaABM;

public class TestConsultaElectricidad {

	public static void main(String[] args) {

		FestivalABM festivalABM = FestivalABM.getInstancia();
		UnidadVentaABM unidadVentaABM = UnidadVentaABM.getInstancia();

		long idFestival = 2L;

		Festival festival = festivalABM.traer(idFestival);

		if (festival == null) {
			System.out.println("No existe el festival con el id " + idFestival);
			return;
		}

		// CONSULTA
		List<FoodTruck> foodTrucks = unidadVentaABM.traerFoodTrucksConConexionElectrica(festival);

		System.out.println("CONSULTA 2 - Trae todos los FoodTruck de un Festival que requieren conexión eléctrica(requiereConexionElectrica = true).");

		System.out.println("------------------------------------------");

		System.out.println("Festival: " + festival.getNombre());
		System.out.println("requiereConexionElectrica: TRUE");

		if (foodTrucks.isEmpty()) {
			System.out.println("No se encontraron Food Trucks que requieran conexión eléctrica");
			return;
		}

		// Mostrar los datos mediante getters
		for (FoodTruck foodTruck : foodTrucks) {

			System.out.println("------------------------------------------");

			System.out.println("ID: " + foodTruck.getId());
			System.out.println("Nombre comercial: " + foodTruck.getNombreComercial());
			System.out.println("Código único: " + foodTruck.getCodigoUnico());
			System.out.println("Patente: " + foodTruck.getPatente());
			System.out.println("Conexión eléctrica: " + foodTruck.isRequiereConexionElectrica());
		}

		/*
		// Mostrar los datos utilizando el toString()
		for (FoodTruck foodTruck : foodTrucks) {
			System.out.printf("\n%s\n", foodTruck);
		} 
		*/
	}
}