package negocio;

import java.util.Set;
import java.util.List;
import dao.UnidadVentaDao;
import datos.Festival;
import datos.FoodTruck;
import datos.Personal;
import datos.Plato;
import datos.PuestoDesarmable;
import datos.UnidadVenta;

public class UnidadVentaABM {

	private static UnidadVentaABM instancia = null;
    private UnidadVentaDao dao = UnidadVentaDao.getInstancia();

    protected UnidadVentaABM() {}

    public static UnidadVentaABM getInstancia() {
        if (instancia == null) {
            instancia = new UnidadVentaABM();
        }
        return instancia;
    }
    
    public UnidadVenta traer(long id) {
        return dao.traer(id);
    }
    
    public Set<UnidadVenta> traerTodas() {
        return dao.traerTodas();
    }

    public UnidadVenta traerPorCodigoUnico(String codigo) {
        return dao.traerPorCodigoUnico(codigo);
    }

    public Set<UnidadVenta> traerPorFestival(Festival festival) {
        return dao.traerPorFestival(festival);
    }

    public long agregar(UnidadVenta unidad) {
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
    // metodo para traer todos los platos de uv 
    public Set<Plato> traerPlatosDeUnidadVentaLista(Long idUnidad) throws Exception {
        if (idUnidad == null) throw new Exception("ID nulo");
        return dao.traerPlatosPorUnidad(idUnidad);
    }
    //------------------------------------------------------------------------------------------------------------
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

    // ---cU ---

    public Set<UnidadVenta> traerPorFestivalOrdenadasPorSuperficie(long idFestival) throws Exception {
        if (idFestival <= 0) throw new Exception("Id de festival invalido");
        return dao.traerPorFestivalOrdenadasPorSuperficie(idFestival);
    }

    public Set<UnidadVenta> traerConMinimoPlatos(int minimo) throws Exception {
        if (minimo < 0) throw new Exception("El minimo no puede ser negativo");
        return dao.traerConMinimoPlatos(minimo);
    }

    public Set<UnidadVenta> traerOrdenadasPorFestival() throws Exception {
        Set<UnidadVenta> lista = dao.traerOrdenadasPorFestival();
        if (lista == null || lista.isEmpty()) throw new Exception("No hay unidades de venta registradas");
        return lista;
    }

    public Set<UnidadVenta> traerOrdenadasPorMayorSuperficie() throws Exception {
        Set<UnidadVenta> lista = dao.traerOrdenadasPorMayorSuperficie();
        if (lista == null || lista.isEmpty()) throw new Exception("No hay unidades de venta registradas");
        return lista;
    }
    
	// CONSULTA 1
	public List<PuestoDesarmable> traerPuestosPorTiempoMontaje(Festival festival, int desde, int hasta) {
		return dao.traerPuestosPorTiempoMontaje(festival, desde, hasta);
	}

	// CONSULTA 2
	public List<FoodTruck> traerFoodTrucksConConexionElectrica(Festival festival) {
		return dao.traerFoodTrucksConConexionElectrica(festival);
	}
    
    
    
    
    
    
    
    
    
    
    
    

}