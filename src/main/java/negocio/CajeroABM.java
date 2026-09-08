
package negocio;

import java.util.List;

import dao.CajeroDao;
import datos.Cajero;

public class CajeroABM {

    private CajeroDao dao = new CajeroDao();

    public Cajero traer(long id) {
        return dao.traer(id);
    }

    public List<Cajero> traerTodos() {
        return dao.traerTodos();
    }

    public List<Cajero> traer() {
        return traerTodos();
    }

    public int agregar(Cajero c) {
        return dao.agregar(c);
    }

    public void actualizar(Cajero c) {
        dao.actualizar(c);
    }

    public void eliminar(long id) {
        Cajero c = dao.traer(id);

        if (c != null) {
            dao.eliminar(c);
        }
    }

    public double calcularRecaudacion(long idCajero) {
        double total = dao.calcularRecaudacionPorCajero(idCajero);

        Cajero cajero = dao.traer(idCajero);

        if (cajero != null) {
            cajero.setRecaudacionTotal(total);
            dao.actualizar(cajero);
        }

        return total;
    }
}

