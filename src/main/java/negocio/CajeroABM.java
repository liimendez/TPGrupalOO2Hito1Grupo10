package negocio;

import java.util.ArrayList;
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

    public Cajero traer(long id) {
        return dao.traer(id);
    }

  
    public Set<Cajero> traerTodas() {
        return dao.traerTodos();
    }

    public Set<Cajero> traerTodos() {
        return dao.traerTodos();
    }

    public long agregar(Cajero c) {
        return dao.agregar(c);
    }

    public void actualizar(Cajero c) {
        dao.actualizar(c);
    }

    public void eliminar(long id) {
        Cajero c = dao.traer(id);
        if (c != null) {
            dao.eliminar(c);
        }
    }

    public double calcularRecaudacion(long cajeroId) {
      
        Set<Pedido> pedidos = pedidoDao.traerPorCajeroConDetalles(cajeroId);
        return pedidos.stream().mapToDouble(Pedido::calcularTotal).sum();
    }
   
 
    public Cajero traerCajeroQueMasRecaudo() {
        Set<Pedido> setPedidos = pedidoDao.traerPedidosOrdenadosParaCorte();
        if (setPedidos == null || setPedidos.isEmpty()) return null;
        
        List<Pedido> lista = new ArrayList<>(setPedidos);

        if (lista.isEmpty()) return null;

        int i = 0;
        double maxRecaudacion = -1;
        Long idUnidadMax = -1L;

        while (i < lista.size()) {
            Long idFestivalActual = lista.get(i).getUnidadVenta().getFestival().getId();

            while (i < lista.size() && lista.get(i).getUnidadVenta().getFestival().getId().equals(idFestivalActual)) {
                Long idUnidadActual = lista.get(i).getUnidadVenta().getId();
                double totalUnidad = 0;

                while (i < lista.size() 
                        && lista.get(i).getUnidadVenta().getFestival().getId().equals(idFestivalActual)
                        && lista.get(i).getUnidadVenta().getId().equals(idUnidadActual)) {
                    
                    totalUnidad += lista.get(i).calcularTotal();
                    i++;
                }

                if (totalUnidad > maxRecaudacion) {
                    maxRecaudacion = totalUnidad;
                    idUnidadMax = idUnidadActual;
                }
            }
        }

        Set<Cajero> cajeros = this.traerTodos();
        for (Cajero c : cajeros) {
            if (c.getUnidadAsignada() != null && c.getUnidadAsignada().getId().equals(idUnidadMax)) {
                return c;
            }
        }
        return null;
    }
}