package negocio;

import java.util.List;

import dao.UnidadVentaDao;
import datos.Plato;
import datos.UnidadVenta;

public class UnidadVentaABM {

    private UnidadVentaDao dao = new UnidadVentaDao();

    public int agregar(UnidadVenta unidadVenta) {
        return dao.agregar(unidadVenta);
    }

    public UnidadVenta traer(long idUnidadVenta) {
        return dao.traer(idUnidadVenta);
    }

    public List<Plato> traerPlatos(long idUnidadVenta) {
        return dao.traerPlatos(idUnidadVenta);
    }
}
