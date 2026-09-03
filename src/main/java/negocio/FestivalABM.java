package negocio;

import java.time.LocalDate;
import java.util.List;

import dao.FestivalDao;
import datos.Festival;
import datos.UnidadVenta;

public class FestivalABM {

    FestivalDao dao = new FestivalDao();

    public Festival traer(long idFestival) {
        return dao.traer(idFestival);
    }

    public int agregar(String nombre, String temporada, LocalDate fechaInicio, LocalDate fechaFin) {

        Festival festival = new Festival(nombre, temporada, fechaInicio, fechaFin);
        
        return dao.agregar(festival);
    }

    public List<UnidadVenta> traerUnidades(long idFestival) {
        return dao.traerUnidades(idFestival);
    }
}
