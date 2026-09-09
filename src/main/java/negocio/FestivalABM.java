package negocio;

import java.time.LocalDate;
import java.util.Set;
import dao.FestivalDao;
import datos.Festival;
import datos.UnidadVenta;

public class FestivalABM {

    private static FestivalABM instancia = null;
    private FestivalDao dao = FestivalDao.getInstancia();

    protected FestivalABM() {}

    public static FestivalABM getInstancia() {
        if (instancia == null) {
            instancia = new FestivalABM();
        }
        return instancia;
    }

    public Festival traer(long idFestival) {
        return dao.traer(idFestival);
    }

    public long agregar(String nombre, String temporada, LocalDate fechaInicio, LocalDate fechaFin) {
        Festival festival = new Festival(nombre, temporada, fechaInicio, fechaFin);
        return dao.agregar(festival);
    }

    public long agregar(Festival festival) {
        return dao.agregar(festival);
    }

    public Set<Festival> traerTodas() {
        return dao.traerTodas();
    }

    public Set<Festival> traerTodos() {
        return traerTodas();
    }

    public Set<UnidadVenta> traerUnidades(long idFestival) {
        return dao.traerUnidades(idFestival);
    }

    public void actualizar(Festival f) {
        dao.actualizar(f);
    }

    public void eliminar(long id) {
        Festival f = dao.traer(id);
        if (f != null) {
            dao.eliminar(f);
        }
    }
}