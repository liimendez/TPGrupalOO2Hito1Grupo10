package test;

import negocio.FestivalABM;
import negocio.FoodTruckABM;
import datos.Festival;
import datos.FoodTruck;

// Agrega un FoodTruck asociado a un Festival a la base de datos en MySQL..
// Creación de una subclase de UnidadVenta mediante herencia.
 
public class TestAgregarFoodTruck {

    public static void main(String[] args) {

        FestivalABM festivalABM = new FestivalABM();
        FoodTruckABM foodTruckABM = new FoodTruckABM();

        Festival festival = festivalABM.traer(1L);

        FoodTruck foodTruck = new FoodTruck("FoodTruck Uno", 22, "FTNUEVO001", festival, null, "ABC123", true);

        int id = foodTruckABM.agregar(foodTruck);

        System.out.println("FoodTruck agregado correctamente.");
        System.out.println("ID FoodTruck: " + id);
    }
}
