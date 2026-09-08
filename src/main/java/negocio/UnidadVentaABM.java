package negocio;

import java.util.List;
import dao.UnidadVentaDao;
import datos.Festival;
import datos.Personal;
import datos.UnidadVenta;

public class UnidadVentaABM {

    private UnidadVentaDao dao = new UnidadVentaDao();

    public UnidadVenta traer(long id) {
        return dao.traer(id);
    }

    
    public List<UnidadVenta> traerTodas() {
        return dao.traerTodas();
    }

    public UnidadVenta traerPorCodigoUnico(String codigo) {
        return dao.traerPorCodigoUnico(codigo);
    }

    public List<UnidadVenta> traerPorFestival(Festival festival) {
        return dao.traerPorFestival(festival);
    }

    public long agregar(UnidadVenta unidad) {
        // Validacion que ya tenes en la entidad
        if (!UnidadVenta.validarCodigo(unidad.getCodigoUnico())) {
            throw new IllegalArgumentException("El codigo unico debe tener exactamente 10 caracteres alfanumericos");
        }
        // Evitar duplicado
        if (dao.traerPorCodigoUnico(unidad.getCodigoUnico()) != null) {
            throw new IllegalArgumentException("Ya existe una unidad con codigo: " + unidad.getCodigoUnico());
        }
        return dao.agregar(unidad);
    }

    public void actualizar(UnidadVenta unidad) {
        dao.actualizar(unidad);
    }

    public void eliminar(long id) {
        UnidadVenta u = dao.traer(id);
        if (u != null) dao.eliminar(u);
    }

    // -----------------------------------------------------------------------------------------------------

    public void asignarResponsable(long idUnidad, Personal responsable) {
        UnidadVenta unidad = dao.traer(idUnidad);
        if (unidad == null) throw new IllegalArgumentException("Unidad no existe");
        
        unidad.setResponsable(responsable);
        // El responsable tambien es parte del staff
        unidad.asignarStaff(responsable); 
        
        dao.actualizar(unidad);
    }

    public void asignarStaff(long idUnidad, Personal empleado) {
        UnidadVenta unidad = dao.traer(idUnidad);
        if (unidad == null) throw new IllegalArgumentException("Unidad no existe");

        unidad.asignarStaff(empleado);
        dao.actualizar(unidad);
    }
}