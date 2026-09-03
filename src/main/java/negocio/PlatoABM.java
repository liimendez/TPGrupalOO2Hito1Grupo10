package negocio;

import dao.PlatoDao;
import datos.Plato;
import datos.UnidadVenta;

public class PlatoABM {

    PlatoDao dao = new PlatoDao();

    // sin unidadVenta
    public int agregar(String nombre, double precioVenta, double costoProduccion) {

        Plato plato = new Plato(nombre, precioVenta, costoProduccion);

        return dao.agregar(plato);
    }

    // con unidadVenta
    public int agregar(String nombre, double precioVenta, double costoProduccion, UnidadVenta unidadVenta) {

        Plato plato = new Plato(nombre, precioVenta, costoProduccion);

        plato.setUnidadVenta(unidadVenta);

        return dao.agregar(plato);
    }

    public Plato traer(long idPlato) {
        return dao.traer(idPlato);
    }
}
