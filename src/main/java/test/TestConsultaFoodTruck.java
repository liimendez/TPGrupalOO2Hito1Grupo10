package test;

import datos.FoodTruck;
import negocio.FoodTruckABM;

// Consulta un FoodTruck mediante su ID. 
// FoodTruck -> UnidadVenta: Herencia.

public class TestConsultaFoodTruck {

    public static void main(String[] args) {

        FoodTruckABM abm = new FoodTruckABM();

        FoodTruck foodTruck = abm.traer(1L);

        System.out.println(foodTruck);
    }
}
