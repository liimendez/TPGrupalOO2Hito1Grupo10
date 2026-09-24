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

    // --------------------------------------------------
    // ALTA
    // --------------------------------------------------

    public long agregar(String nombre, String temporada, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {

        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }

        if (temporada == null || temporada.isEmpty()) {
            throw new Exception("Temporada obligatoria");
        }

        if (fechaInicio == null || fechaFin == null) {
            throw new Exception("Fechas obligatorias");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new Exception("Fecha fin debe ser despues de fecha inicio");
        }

        Festival festival = new Festival(nombre, temporada, fechaInicio, fechaFin);

        return dao.agregar(festival);
    }

    public long agregar(Festival festival) throws Exception {

        if (festival == null) {
            throw new Exception("Festival nulo");
        }

        return dao.agregar(festival);
    }

    // --------------------------------------------------
    // TRAER
    // --------------------------------------------------

    public Festival traer(long idFestival) {
        return dao.traer(idFestival);
    }
    
    public Set<Festival> traerTodas() {
        return dao.traerTodas();
    }

    public Set<UnidadVenta> traerUnidades(long idFestival) throws Exception {

        if (idFestival <= 0) {
            throw new Exception("Id festival invalido");
        }

        return dao.traerUnidades(idFestival);
    }

    // --------------------------------------------------
    // MODIFICACION Y BAJA
    // --------------------------------------------------

    public void actualizar(Festival f) throws Exception {

        if (f == null) {
            throw new Exception("Festival nulo para actualizar");
        }

        dao.actualizar(f);
    }

    public void eliminar(long id) throws Exception {

        Festival f = dao.traer(id);

        if (f == null) {
            throw new Exception("No existe Festival con id: " + id + " para eliminar");
        }

        dao.eliminar(f);
    }
    
    
  //------------------------------------------------------------------------------------------------------------------------
  //-------------------------------------------TestFrancoHegele-------------------------------------------------------------
  //------------------------------------------------------------------------------------------------------------------------  
    
    public Festival traerFestival(String nombre)throws Exception {
    	
    	Festival retorno = dao.traerFestival(nombre);
    	
    	if(retorno == null) {
    		throw new Exception("\n\nERROR: El nombre de ese festival no existe!!!");
    	}
    	
    	return retorno;
    }
  

}