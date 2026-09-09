package negocio;

import java.util.Set;
import dao.FoodTruckDao;
import datos.FoodTruck;

public class FoodTruckABM {

    private static FoodTruckABM instancia = null;
    private FoodTruckDao dao = FoodTruckDao.getInstancia();

    protected FoodTruckABM() {}

    public static FoodTruckABM getInstancia() {
        if (instancia == null) {
            instancia = new FoodTruckABM();
        }
        return instancia;
    }

    public long agregar(FoodTruck foodTruck) {
        return dao.agregar(foodTruck);
    }

    public FoodTruck traer(long idFoodTruck) {
        return dao.traer(idFoodTruck);
    }

    public Set<FoodTruck> traerTodas() {
        return dao.traerTodas();
    }

    public Set<FoodTruck> traerTodos() {
        return traerTodas();
    }

    public void actualizar(FoodTruck f) {
        dao.actualizar(f);
    }

    public void eliminar(long id) {
        FoodTruck f = dao.traer(id);
        if (f != null) {
            dao.eliminar(f);
        }
    }
}