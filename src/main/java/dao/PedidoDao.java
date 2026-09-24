package dao;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Cajero;
import datos.Festival;
import datos.Pedido;
import datos.Plato;
import datos.UnidadVenta;

public class PedidoDao {

    private Session session;
    private Transaction tx;

    private static PedidoDao instancia = null;

    public static PedidoDao getInstancia() {

        if (instancia == null) {
            instancia = new PedidoDao();
        }

        return instancia;
    }

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa PedidoDao", he);
    }

    public Long agregar(Pedido pedido) {

        Long id = null;

        try {
            iniciaOperacion();
            session.persist(pedido);
            tx.commit();
            id = pedido.getId();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }

    public Pedido traer(long idPedido) {

        Pedido pedido = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from Pedido p inner join fetch p.unidadVenta uv " +
                         "inner join fetch uv.festival " +
                         "inner join fetch p.cajero " +
                         "inner join fetch p.detalles d " +
                         "inner join fetch d.plato " +
                         "where p.id = :id";

            pedido = session.createQuery(hql, Pedido.class)
                            .setParameter("id", idPedido)
                            .uniqueResult();

        } finally {
            session.close();
        }

        return pedido;
    }

    public Set<Pedido> traerTodas() {

        Set<Pedido> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from Pedido p order by p.id";
            List<Pedido> lista = session.createQuery(hql, Pedido.class).list();
            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
    }

    public Set<Pedido> traerPorUnidadVenta(long idUnidadVenta) {

        Set<Pedido> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from Pedido p inner join fetch p.unidadVenta uv where uv.id = :idUnidad order by p.id";

            List<Pedido> lista = session.createQuery(hql, Pedido.class)
                                       .setParameter("idUnidad", idUnidadVenta)
                                       .list();

            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
    }

    public Set<Pedido> traerPorCajero(long idCajero) {

        Set<Pedido> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "select distinct p from Pedido p " +
                         "inner join fetch p.cajero c " +
                         "inner join fetch p.detalles d " +
                         "inner join fetch d.plato " +
                         "where c.id = :idCajero order by p.id";

            List<Pedido> lista = session.createQuery(hql, Pedido.class)
                                       .setParameter("idCajero", idCajero)
                                       .list();

            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
    }

    public Set<Pedido> traerPedidosOrdenadosParaCorte() {

        Set<Pedido> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "select distinct p from Pedido p " +
                         "inner join fetch p.unidadVenta uv " +
                         "inner join fetch uv.festival f " +
                         "inner join fetch p.cajero " +
                         "inner join fetch p.detalles d " +
                         "inner join fetch d.plato " +
                         "order by f.id, uv.id";

            List<Pedido> lista = session.createQuery(hql, Pedido.class).list();
            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
    }
// actualizacion y baja 
    public void actualizar(Pedido objeto) {

        try {
            iniciaOperacion();
            session.update(objeto);
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;

        } finally {
            session.close();
        }
    }

    public void eliminar(Pedido objeto) {

        try {
            iniciaOperacion();
            session.delete(objeto);
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;

        } finally {
            session.close();
        }
    }
    //--------------------------------------------------------------------------------------------------------
    
    
    // =========================================================
    // REPORTE EJECUTIVO -
    // muchos pedidos se hacen en un festival : Pedido -> Festival 
    // muchas unidades de venta pertenecen a un festival : UnidadVenta -> Festival 
    // muchos pedidos los cobra un cajero : Pedido -> Cajero
    // un pedido tiene muchos detalles : Pedido -> DetallePedido 
    // muchos detalles apuntan a un plato : DetallePedido -> Plato 
    // =========================================================

    public Festival traerFestivalQueMasRecaudo() {
        Festival festival = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select uv.festival from Pedido ped " +
                         "inner join ped.unidadVenta uv " +
                         "inner join ped.detalles det inner join det.plato pla " +
                         "group by uv.festival.id order by sum(det.cantidad * pla.precioVenta) desc";
            festival = session.createQuery(hql, Festival.class).setMaxResults(1).uniqueResult();
        } finally {
            session.close();
        }
        return festival;
    }

    public double calcularRecaudacionPorFestival(long idFestival) {
        Double total = 0.0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select sum(det.cantidad * pla.precioVenta) from Pedido ped " +
                         "inner join ped.unidadVenta uv inner join ped.detalles det inner join det.plato pla " +
                         "where uv.festival.id = :idFestival";
            total = session.createQuery(hql, Double.class).setParameter("idFestival", idFestival).uniqueResult();
        } finally {
            session.close();
        }
        return total != null ? total : 0;
    }

    public Object[] traerUnidadQueMasRecaudoEnFestival(long idFestival) {
        Object[] data = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select ped.unidadVenta, sum(det.cantidad * pla.precioVenta) from Pedido ped " +
                         "inner join ped.unidadVenta uv inner join ped.detalles det inner join det.plato pla " +
                         "where uv.festival.id = :idFestival " +
                         "group by ped.unidadVenta.id order by sum(det.cantidad * pla.precioVenta) desc";
            data = session.createQuery(hql, Object[].class).setParameter("idFestival", idFestival).setMaxResults(1).uniqueResult();
        } finally {
            session.close();
        }
        return data;
    }

    public double calcularRecaudacionUnidadEnFestival(long idFestival, long idUnidad) {
        Double total = 0.0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select sum(det.cantidad * pla.precioVenta) from Pedido ped " +
                         "inner join ped.unidadVenta uv inner join ped.detalles det inner join det.plato pla " +
                         "where uv.festival.id = :idFestival and ped.unidadVenta.id = :idUnidad";
            total = session.createQuery(hql, Double.class).setParameter("idFestival", idFestival).setParameter("idUnidad", idUnidad).uniqueResult();
        } finally {
            session.close();
        }
        return total != null ? total : 0;
    }

    public Plato traerPlatoMasVendidoEnFestival(long idFestival) {
        Plato plato = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select det.plato from Pedido ped inner join ped.unidadVenta uv inner join ped.detalles det " +
                         "where uv.festival.id = :idFestival " +
                         "group by det.plato.id order by sum(det.cantidad) desc";
            plato = session.createQuery(hql, Plato.class).setParameter("idFestival", idFestival).setMaxResults(1).uniqueResult();
        } finally {
            session.close();
        }
        return plato;
    }

    public Plato traerPlatoMasRentableEnFestival(long idFestival) {
        Plato plato = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select det.plato from Pedido ped inner join ped.unidadVenta uv inner join ped.detalles det inner join det.plato pla " +
                         "where uv.festival.id = :idFestival " +
                         "group by det.plato.id order by sum(det.cantidad * pla.precioVenta) desc";
            plato = session.createQuery(hql, Plato.class).setParameter("idFestival", idFestival).setMaxResults(1).uniqueResult();
        } finally {
            session.close();
        }
        return plato;
    }

    public Cajero traerCajeroQueMasRecaudoEnFestival(long idFestival) {
        Cajero cajero = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select ped.cajero from Pedido ped inner join ped.unidadVenta uv inner join ped.detalles det inner join det.plato pla " +
                         "where uv.festival.id = :idFestival " +
                         "group by ped.cajero.id order by sum(det.cantidad * pla.precioVenta) desc";
            cajero = session.createQuery(hql, Cajero.class).setParameter("idFestival", idFestival).setMaxResults(1).uniqueResult();
        } finally {
            session.close();
        }
        return cajero;
    } 

    public double calcularRecaudacionPorCajeroEnFestival(long idCajero, long idFestival) {
        Double total = 0.0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select sum(det.cantidad * pla.precioVenta) from Pedido ped " +
                         "inner join ped.unidadVenta uv inner join ped.detalles det inner join det.plato pla " +
                         "where ped.cajero.id = :idCajero and uv.festival.id = :idFestival";
            total = session.createQuery(hql, Double.class).setParameter("idCajero", idCajero).setParameter("idFestival", idFestival).uniqueResult();
        } finally {
            session.close();
        }
        return total != null ? total : 0;
    }
    
 
//------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------TestFrancoHegele-------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------  
     
    public UnidadVenta traerUnidadVentaMasRecaudadoraEnRangoFestival(Festival festival, LocalDate fechaDesde, LocalDate fechaHasta) {
            
        UnidadVenta unidad = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select uv from Pedido ped " +
                         "inner join ped.unidadVenta uv " +
                         "inner join uv.festival f " +
                         "inner join ped.detalles det " +
                         "inner join det.plato pla " +
                         "where f = :festival " + 
                         "and :fechaDesde >= f.fechaInicio " + 
                         "and :fechaHasta <= f.fechaFin " +
                         "and ped.fechaTransaccion between :fechaDesde and :fechaHasta " +
                         "group by uv.id, uv.nombreComercial " + 
                         "order by sum(det.cantidad * pla.precioVenta) desc";
                         
            unidad = session.createQuery(hql, UnidadVenta.class)
                    .setParameter("festival", festival)
                    .setParameter("fechaDesde", fechaDesde)
                    .setParameter("fechaHasta", fechaHasta)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
        return unidad;
    } 
    
    
    public double calcularRecaudacionEntreDosFechas(Festival festival, LocalDate fechaDesde, LocalDate fechaHasta) {
        
    	Double total = 0.0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	String hql = "select sum(det.cantidad * pla.precioVenta) from Pedido ped " +
                    "inner join ped.unidadVenta uv " +
                    "inner join uv.festival f " +
                    "inner join ped.detalles det " +
                    "inner join det.plato pla " +
                    "where f = :festival " +
                    "and f.fechaInicio <= :fechaDesde " +
                    "and f.fechaFin >= :fechaHasta " +
                    "and ped.fechaTransaccion between :fechaDesde and :fechaHasta " +
                    "group by uv " + 
                    "order by sum(det.cantidad * pla.precioVenta) desc"; 
            
            total = session.createQuery(hql, Double.class)
                    .setParameter("festival", festival)
                    .setParameter("fechaDesde", fechaDesde)
                    .setParameter("fechaHasta", fechaHasta)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
        return total;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}