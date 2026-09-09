package negocio;

import java.util.Set;
import dao.PlatoDao;
import datos.Plato;
import datos.UnidadVenta;

public class PlatoABM {

    private static PlatoABM instancia = null;
    private PlatoDao dao = PlatoDao.getInstancia();

    protected PlatoABM() {}

    public static PlatoABM getInstancia() {
        if (instancia == null) {
            instancia = new PlatoABM();
        }
        return instancia;
    }

    // --- AGREGAR --- sin una unidad de venta 
    public long agregar(String nombre, double precioVenta, double costoProduccion) {
        Plato plato = new Plato(nombre, precioVenta, costoProduccion);
        return dao.agregar(plato);
    }

    // agregar con unidad de venta             
    public long agregar(String nombre, double precioVenta, double costoProduccion, UnidadVenta unidadVenta) {
        Plato plato = new Plato(nombre, precioVenta, costoProduccion);
        plato.setUnidadVenta(unidadVenta);
        return dao.agregar(plato);
    }

    public long agregar(Plato plato) {
        return dao.agregar(plato);
    }

    // --- TRAER ---
    public Plato traer(long idPlato) {
        return dao.traer(idPlato);
    }

    public Set<Plato> traerTodas() {
        return dao.traerTodas();
    }

    public Set<Plato> traerTodos() {
        return traerTodas();
    }

    public Set<Plato> traerPorUnidadVenta(long idUnidadVenta) {
        return dao.traerPorUnidadVenta(idUnidadVenta);
    }

  
    public void actualizar(Plato plato) {
        dao.actualizar(plato);
    }

    public void eliminar(long id) {
        Plato p = dao.traer(id);
        if (p != null) {
            dao.eliminar(p);
        }
    }

    public void eliminar(Plato plato) {
        dao.eliminar(plato);
    }
}