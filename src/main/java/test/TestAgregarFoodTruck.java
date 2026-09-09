package test;

import negocio.FestivalABM;
import negocio.FoodTruckABM;
import datos.Festival;
import datos.FoodTruck;

public class TestAgregarFoodTruck {

    public static void main(String[] args) {
        try {
            FestivalABM festivalABM = FestivalABM.getInstancia();
            FoodTruckABM foodTruckABM = FoodTruckABM.getInstancia();

            Festival festival = festivalABM.traer(1L);
            if (festival == null) {
                System.out.println("No existe festival con ID 1");
                return;
            }

            FoodTruck foodTruck = new FoodTruck("FoodTruck Uno", 22, "FTNUEVO001", festival, null, "ABC123", true);

            long id = foodTruckABM.agregar(foodTruck);

            System.out.println("FoodTruck agregado correctamente.");
            System.out.println("ID FoodTruck: " + id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}