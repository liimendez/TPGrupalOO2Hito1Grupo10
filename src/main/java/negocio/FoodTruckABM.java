package negocio;

import dao.FoodTruckDao;
import datos.FoodTruck;

public class FoodTruckABM {

    FoodTruckDao dao = new FoodTruckDao();

    public int agregar(FoodTruck foodTruck) {
        return dao.agregar(foodTruck);
    }

    public FoodTruck traer(long idFoodTruck) {
        return dao.traer(idFoodTruck);
    }
}
