package negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import dao.CajeroDao;
import dao.PedidoDao;
import datos.Cajero;
import datos.Pedido;


public class CajeroABM {

    private static CajeroABM instancia = null;
    private CajeroDao dao = CajeroDao.getInstancia();
    private PedidoDao pedidoDao = PedidoDao.getInstancia();


    protected CajeroABM() {}

    public static CajeroABM getInstancia() {
        if (instancia == null) {
            instancia = new CajeroABM();
        }
        return instancia;
    }

    public Cajero traer(long id) throws Exception {
        Cajero c = dao.traer(id);
        if (c == null) throw new Exception("No existe cajero con id: " + id);
        return c;
    }

 // ordenado por id
    public Set<Cajero> traerTodas() {
        return dao.traerTodos();
    }


    public Set<Cajero> traerCajerosOrdenados() {
        return dao.traerCajerosOrdenados();
    }

    public long agregar(Cajero c) throws Exception {
        if (c.getNombre() == null || c.getApellido() == null) throw new Exception("Nombre y apellido obligatorios");
        if (c.getUnidadAsignada() == null) throw new Exception("Cajero debe tener unidad asignada");
        return dao.agregar(c);
    }

    public void actualizar(Cajero c) throws Exception {
        if (c == null) throw new Exception("Cajero nulo");
        dao.actualizar(c);
    }

    public void eliminar(long id) throws Exception {
        Cajero c = dao.traer(id);
        if (c == null) throw new Exception("No existe cajero para eliminar");
        dao.eliminar(c);
    }

   
    public double calcularRecaudacion(long cajeroId) throws Exception {
        if (cajeroId <= 0) throw new Exception("Id cajero invalido");
        return dao.calcularRecaudacionPorCajero(cajeroId);
    }

    public double calcularRecaudacionPorFecha(long cajeroId, LocalDate fecha) throws Exception {
        if (fecha == null) throw new Exception("Fecha nula");
        if (fecha.isAfter(LocalDate.now())) throw new Exception("Fecha futura no valida");
        return dao.calcularRecaudacionPorCajeroPorFecha(cajeroId, fecha);
    }
   
    public Cajero traerCajeroQueMasRecaudo() {
        Set<Cajero> cajeros = dao.traerTodos();
        if (cajeros == null || cajeros.isEmpty()) return null;

        Cajero maxCajero = null;
        double maxRecaudacion = -1;

        for (Cajero c : cajeros) {
            double rec = dao.calcularRecaudacionPorCajero(c.getId());
            if (rec > maxRecaudacion) {
                maxRecaudacion = rec;
                maxCajero = c;
            }
        } 
        return maxCajero;
    }


    
    

    
    
}