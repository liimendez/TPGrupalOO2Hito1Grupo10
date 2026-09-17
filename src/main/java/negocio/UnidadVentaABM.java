package negocio;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;

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

        if (u != null) {
            dao.eliminar(u);
        }
    }

    // -----------------------------------------------------------------------------------------------------
    // metodo para traer todos los platos de uv 
    public Set<Plato> traerPlatosDeUnidadVentaLista(Long idUnidad) {

        if (idUnidad == null) {
            throw new IllegalArgumentException("ID nulo");
        }

        return dao.traerPlatosPorUnidad(idUnidad);
    }
    //------------------------------------------------------------------------------------------------------------

    public void asignarResponsable(long idUnidad, Personal responsable) {

        UnidadVenta unidad = dao.traer(idUnidad);

        if (unidad == null) {
            throw new IllegalArgumentException("Unidad no existe");
        }
        
        unidad.setResponsable(responsable);

        // El responsable tambien es parte del staff
        unidad.asignarStaff(responsable); 
        
        dao.actualizar(unidad);
    }

    public void asignarStaff(long idUnidad, Personal empleado) {

        UnidadVenta unidad = dao.traer(idUnidad);

        if (unidad == null) {
            throw new IllegalArgumentException("Unidad no existe");
        }

        unidad.asignarStaff(empleado);
        dao.actualizar(unidad);
    }

    // ---cU ---

    public Set<UnidadVenta> traerPorFestivalOrdenadasPorSuperficie(long idFestival) {

        if (idFestival <= 0) {
            throw new IllegalArgumentException("Id de festival invalido");
        }

        return dao.traerPorFestivalOrdenadasPorSuperficie(idFestival);
    }

    public Set<UnidadVenta> traerConMinimoPlatos(int minimo) {

        if (minimo < 0) {
            throw new IllegalArgumentException("El minimo no puede ser negativo");
        }

        return dao.traerConMinimoPlatos(minimo);
    }

    public Set<UnidadVenta> traerOrdenadasPorFestival() {

        return dao.traerOrdenadasPorFestival();
    }

    public Set<UnidadVenta> traerOrdenadasPorMayorSuperficie() {

        return dao.traerOrdenadasPorMayorSuperficie();
    }
    
	// CONSULTA 1
	public List<PuestoDesarmable> traerPuestosPorTiempoMontaje(Festival festival, int desde, int hasta) throws Exception {
		
		if (festival == null) {
	        throw new Exception("ERROR: El festival ingresado no puede ser nulo");
	    }

	    if (desde < 0) {
	        throw new Exception("ERROR: El valor 'desde' no puede ser negativo");
	    }

	    if (hasta < desde) {
	        throw new Exception("ERROR: El tiempo de 'hasta' debe ser mayor o igual al tiempo 'desde'");
	    }
		return dao.traerPuestosPorTiempoMontaje(festival, desde, hasta);
	}

	// CONSULTA 2
	public List<FoodTruck> traerFoodTrucksConConexionElectrica(Festival festival) {
		return dao.traerFoodTrucksConConexionElectrica(festival);
	}
	
	// CONSULTA 3
	public List<FoodTruck> traerFoodTrucksPorFestivalYPrecioPlato(Festival festival, double precioMinimo) {
		return dao.traerFoodTrucksPorFestivalYPrecioPlato(festival, precioMinimo);
	}
	
	// CONSULTA 4
	public List<UnidadVenta> traerUnidadesVentaPorFechaIngresoPersonal(LocalDate fechaDesde) {
		return dao.traerUnidadesVentaPorFechaIngresoPersonal(fechaDesde);
	}
	
	//-------------------------------------------------------------------------------------------
	// muchos pedidos se hacen en un festival : Pedido -> Festival (ManyToOne)
	// muchas unidades de venta pertenecen a un festival : UnidadVenta -> Festival (ManyToOne)
	// una unidad tiene muchos platos : UnidadVenta -> Plato (ManyToMany)

	public UnidadVenta traerUnidadConMayorSuperficie() {
	    Set<UnidadVenta> ordenadas = dao.traerOrdenadasPorMayorSuperficie();
	    if (ordenadas == null || ordenadas.isEmpty()) {
	        return null;
	    }
	    return ordenadas.iterator().next(); // la primera porque ya viene order by superficie desc
	}


	public Set<UnidadVenta> traerOrdenadasPorMenorSuperficie() {
	    return dao.traerOrdenadasPorMenorSuperficie(); // ya viene order by superficie asc
	}

	public UnidadVenta traerUnidadConMenorSuperficie() {
	    // muchas unidades de venta pertenecen a un festival : UnidadVenta -> Festival 
	    Set<UnidadVenta> ordenadas = dao.traerOrdenadasPorMenorSuperficie();
	    if (ordenadas == null || ordenadas.isEmpty()) {
	        return null;
	    }
	    return ordenadas.iterator().next(); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}