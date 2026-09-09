package test;

import datos.FoodTruck;
import negocio.FoodTruckABM;

// Consulta un FoodTruck mediante su ID. 
// FoodTruck -> UnidadVenta: Herencia.

public class TestConsultaFoodTruck {

    public static void main(String[] args) {

        FoodTruckABM abm = FoodTruckABM.getInstancia();

        FoodTruck foodTruck = abm.traer(21L); // fijarse si existe id 

        System.out.println(foodTruck);
    }
}
