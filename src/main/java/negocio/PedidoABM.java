package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.PedidoDao;
import datos.Cajero;
import datos.DetallePedido;
import datos.Festival;
import datos.Pedido;
import datos.Plato;
import datos.UnidadVenta;

public class PedidoABM {

    private static PedidoABM instancia = null;
    private PedidoDao dao = PedidoDao.getInstancia();

    protected PedidoABM() {}

    public static PedidoABM getInstancia() {

        if (instancia == null) {
            instancia = new PedidoABM();
        }

        return instancia;
    }
 
    // --------------------------------------------------
    // ALTA - Crea pedido con 1 detalle
    // --------------------------------------------------

    public long agregar(LocalDate fecha, UnidadVenta uv, Cajero cajero, Plato plato, int cantidad) {

        Pedido p = new Pedido();
        p.setFechaTransaccion(fecha);
        p.setUnidadVenta(uv);
        p.setCajero(cajero);
        p.setDetalles(new HashSet<>());

        DetallePedido d = new DetallePedido();
        d.setPlato(plato);
        d.setCantidad(cantidad);
        d.setPedido(p);
      

        p.getDetalles().add(d);

        return dao.agregar(p);
    }

    // ALTA - Ya armado
    public Long agregar(Pedido pedido) {

        return dao.agregar(pedido);
    }

    // --------------------------------------------------
    // TRAER
    // --------------------------------------------------

    public Pedido traer(long idPedido) {

        return dao.traer(idPedido);
    }

    public Set<Pedido> traerTodas() {

        return dao.traerTodas();
    }

    public Set<Pedido> traerPorUnidadVenta(long idUnidad) {

        return dao.traerPorUnidadVenta(idUnidad);
    }

    public Set<Pedido> traerPorCajero(long idCajero) {

        return dao.traerPorCajero(idCajero);
    }

    public Set<Pedido> traerPedidosOrdenadosParaCorte() {

        return dao.traerPedidosOrdenadosParaCorte();
    }

    // --------------------------------------------------
    // MODIFICACION Y BAJA
    // --------------------------------------------------

    public void actualizar(Pedido p) {

        dao.actualizar(p);
    }

    public void eliminar(Pedido p) {

        dao.eliminar(p);
    }
    
    
 
 // --- RELACIONES QUE USA EL REPORTE DE RECAUDACIONES ---

 // muchos pedidos se hacen en un festival : Pedido -> Festival 
 // muchos pedidos se hacen en una unidad de venta : Pedido -> UnidadVenta
 // muchos pedidos los cobra un cajero : Pedido -> Cajero 
 // un pedido tiene muchos detalles : Pedido -> DetallePedido 
 // muchos detalles apuntan a un plato : DetallePedido -> Plato
 // muchas unidades de venta pertenecen a un festival : UnidadVenta -> Festival 
 // un cajero esta asignado a una unidad de venta : Cajero -> UnidadVenta 
 // herencia: PuestoDesarmable y FoodTruck heredan de UnidadVenta : UnidadVenta <|-- PuestoDesarmable / FoodTruck
    
 public void generarReporteRecaudaciones() {
        
        Set<Pedido> set = dao.traerPedidosOrdenadosParaCorte(); // ordenados por festival y unidad
        
        if (set.isEmpty()) {
            System.out.println("No hay pedidos");
            return;
        }

        List<Pedido> pedidos = new ArrayList<>(set);
        int i = 0;
        double totalGeneral = 0;

        System.out.println("REPORTE DE RECAUDACIONES");

        while (i < pedidos.size()) {
            Festival festivalActual = pedidos.get(i).getFestival();
            double totalFestival = 0;
            
            System.out.println("\n==================================================");
            System.out.println("FESTIVAL: " + festivalActual.getNombre() + " (ID: " + festivalActual.getId() + ")");
            System.out.println("==================================================");

            do {
                UnidadVenta unidadActual = pedidos.get(i).getUnidadVenta();
                double totalUnidad = 0;
                
                System.out.println("\n  -> Unidad Venta: " + unidadActual.getId() + " - " + unidadActual.getClass().getSimpleName());

                do {
                    Pedido p = pedidos.get(i);
                    double totalPedido = p.calcularTotal();
                    totalUnidad += totalPedido;
                    System.out.println("      Pedido ID: " + p.getId() + " | Cajero: " + p.getCajero().getNombre() + " | Total: $" + totalPedido);
                    i++;
                } while (i < pedidos.size() 
                        && pedidos.get(i).getFestival().getId().equals(festivalActual.getId())
                        && pedidos.get(i).getUnidadVenta().getId() == unidadActual.getId());

                System.out.println("     TOTAL UNIDAD " + unidadActual.getId() + ": $" + totalUnidad);
                totalFestival += totalUnidad;

            } while (i < pedidos.size() && pedidos.get(i).getFestival().getId().equals(festivalActual.getId()));

            System.out.println("\n  TOTAL FESTIVAL " + festivalActual.getNombre() + ": $" + totalFestival);
            totalGeneral += totalFestival;
        }

        System.out.println("\n**************************************************");
        System.out.println("TOTAL GENERAL RECAUDADO: $" + totalGeneral);
        System.out.println("**************************************************");
    }
    
    
   /////////////////////metodos para el reporte /////////////////////////////////////////////////////////////
    // corregido
    
 public Festival traerFestivalQueMasRecaudo() {
	    return dao.traerFestivalQueMasRecaudo();
	}

	public double traerRecaudacionDeFestival(Festival festival) {
	    if (festival == null) throw new IllegalArgumentException("Festival nulo");
	    return dao.calcularRecaudacionPorFestival(festival.getId());
	}

	public UnidadVenta traerUnidadQueMasRecaudoEnFestival(Festival festival) {
	    if (festival == null) throw new IllegalArgumentException("Festival nulo");
	    Object[] data = dao.traerUnidadQueMasRecaudoEnFestival(festival.getId());
	    return data!= null? (UnidadVenta) data[0] : null;
	}

	public double traerRecaudacionUnidadEnFestival(Festival festival, UnidadVenta unidad) {
	    if (festival == null || unidad == null) throw new IllegalArgumentException("Parametros nulos");
	    return dao.calcularRecaudacionUnidadEnFestival(festival.getId(), unidad.getId());
	}

	public double traerRecaudacionDeUnidadTopEnFestival(Festival festival) {
	    if (festival == null) throw new IllegalArgumentException("Festival nulo");
	    Object[] data = dao.traerUnidadQueMasRecaudoEnFestival(festival.getId());
	    if (data!= null && data[1]!= null) {
	        return (Double) data[1];
	    }
	    return 0;
	}

	public Plato traerPlatoMasVendidoEnFestival(Festival festival) {
	    if (festival == null) throw new IllegalArgumentException("Festival nulo");
	    return dao.traerPlatoMasVendidoEnFestival(festival.getId());
	}

	public Plato traerPlatoMasRentableEnFestival(Festival festival) {
	    if (festival == null) throw new IllegalArgumentException("Festival nulo");
	    return dao.traerPlatoMasRentableEnFestival(festival.getId());
	}

	public Cajero traerCajeroQueMasRecaudoEnFestival(Festival festival) {
	    if (festival == null) throw new IllegalArgumentException("Festival nulo");
	    return dao.traerCajeroQueMasRecaudoEnFestival(festival.getId());
	}
 
    public double calcularRecaudacionPorCajeroEnFestival(Cajero cajero, Festival festival) {
        if (cajero == null || festival == null) throw new IllegalArgumentException("Parametros nulos");
        return dao.calcularRecaudacionPorCajeroEnFestival(cajero.getId(), festival.getId());
    }
    
    
//------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------TestFrancoHegele-------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------  
    
    public UnidadVenta traerUnidadVentaMasRecaudadoraEnRangoFestival(Festival festival, LocalDate fechaDesde, LocalDate fechaHasta)throws Exception {
    	
    	if(fechaDesde.isBefore(festival.getFechaInicio()) || fechaHasta.isAfter(festival.getFechaFin()) ) {
    		
    		throw new Exception("\n\nERROR: las fechas ingresadas no coinciden con las fechas del festival!!!");
    	}
    	 
    	return dao.traerUnidadVentaMasRecaudadoraEnRangoFestival(festival, fechaDesde, fechaHasta);
    };
    
    
    public double calcularRecaudacionEntreDosFechas(Festival festival, LocalDate fechaDesde, LocalDate fechaHasta)throws Exception {
    	
    	if(fechaDesde.isBefore(festival.getFechaInicio()) || fechaHasta.isAfter(festival.getFechaFin()) ) {
    		
    		throw new Exception("\n\nERROR: las fechas ingresadas no coinciden con las fechas del festival!!!");
    	}
    	return dao.calcularRecaudacionEntreDosFechas(festival, fechaDesde, fechaHasta);
    }
}