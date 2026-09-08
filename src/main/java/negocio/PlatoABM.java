package negocio;

import java.util.List;
import dao.PlatoDao;
import datos.Plato;
import datos.UnidadVenta;

public class PlatoABM {

    private PlatoDao dao = new PlatoDao();

    // --- AGREGAR --- sin una unidad de venta 
    public int agregar(String nombre, double precioVenta, double costoProduccion) {
        Plato plato = new Plato(nombre, precioVenta, costoProduccion);
        return dao.agregar(plato);
    }
      // agregar con unidad de venta             
    public int agregar(String nombre, double precioVenta, double costoProduccion, UnidadVenta unidadVenta) {
        Plato plato = new Plato(nombre, precioVenta, costoProduccion);
        plato.setUnidadVenta(unidadVenta);
        return dao.agregar(plato);
    }

    public int agregar(Plato plato) {
        return dao.agregar(plato);
    }

    // --- TRAER ---
    public Plato traer(long idPlato) {
        return dao.traer(idPlato);
    }

    public List<Plato> traerTodas() {
        return dao.traerTodas();
    }

  
    public List<Plato> traerPorUnidadVenta(long idUnidadVenta) {
        return dao.traerPorUnidadVenta(idUnidadVenta);
    }

    // --- ACTUALIZAR ---
    public void actualizar(Plato plato) {
        dao.actualizar(plato);
    }

    // --- ELIMINAR --- por id o por objeto 
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