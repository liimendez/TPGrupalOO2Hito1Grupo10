package dao;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Cajero;

public class CajeroDao {
    
    private Session session;
    private Transaction tx;
    private static CajeroDao instancia = null;

    protected CajeroDao() {}

    public static CajeroDao getInstancia() {
        if (instancia == null) {
            instancia = new CajeroDao();
        }
        return instancia;
    }
    
    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }
    
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a cajeroDao", he);
    }
    
    public int agregar(Cajero cajero) {
        int id = 0;
        try {
            iniciaOperacion();
            id = Integer.parseInt(session.save(cajero).toString());
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            session.close();
        }
        return id;
    }
    
    public Cajero traer(long id) {
        Cajero c = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cajero c inner join fetch c.unidadAsignada where c.id = :id";
            c = (Cajero) session.createQuery(hql)
                                 .setParameter("id", id)
                                 .uniqueResult();
        } finally {
            session.close();
        }
        return c;
    }

    public Set<Cajero> traerTodos() {
        Set<Cajero> set = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
       
            String hql = "from Cajero c inner join fetch c.unidadAsignada uv inner join fetch uv.festival order by c.id";
            List<Cajero> lista = session.createQuery(hql, Cajero.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
    }
   // trae solo cajeros ordenado por apellido
    public Set<Cajero> traerCajerosOrdenados() {
        Set<Cajero> set = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cajero c order by c.apellido asc, c.nombre asc";
            List<Cajero> lista = session.createQuery(hql, Cajero.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
    }

    public void actualizar(Cajero objeto) {
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

    public void eliminar(Cajero objeto) {
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


    // la recaudacion se calcula a partir de la suma de los subtotales de los detalles pedidos de una unidad de venta. global 
    public double calcularRecaudacionPorCajero(long idCajero) {

        double recaudacion = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // DetallePedido ya tiene el subtotal calculado
            String hql = "select coalesce(sum(d.subtotal), 0) " +
                         "from Pedido p " +
                         "inner join p.cajero c " +
                         "inner join p.detalles d " +
                         "where c.id = :idCajero";

            Double result = session.createQuery(hql, Double.class)
                                  .setParameter("idCajero", idCajero)
                                  .uniqueResult();

            if (result != null) {
                recaudacion = result;
            }

        } finally {
            session.close();
        }

        return recaudacion;
    }

    // calcula la recaudacion de los cajeros y la fecha 
    public double calcularRecaudacionPorCajeroPorFecha(long idCajero, LocalDate fecha) {
        double total = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT coalesce(SUM(d.cantidad * d.precioUnitario), 0) " +
                         "FROM Cajero c " +
                         "INNER JOIN c.unidadAsignada uv " +
                         "INNER JOIN uv.pedidos p " +
                         "INNER JOIN p.detalles d " +
                         "WHERE c.id = :idCajero AND p.fechaTransaccion = :fecha";
            
            Query<Double> q = session.createQuery(hql, Double.class);
            q.setParameter("idCajero", idCajero);
            q.setParameter("fecha", fecha);
            total = q.uniqueResult();
        } finally {
            session.close();
        }
        return total;
    } 
    
 // Cajero que mas recaudo de forma global
    public List<Cajero> traerCajeroQueMasRecaudo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT c FROM Cajero c " +
                         "INNER JOIN c.unidadAsignada uv " +
                         "INNER JOIN uv.pedidos p " +
                         "INNER JOIN p.detalles d " +
                         "GROUP BY c.id ORDER BY SUM(d.cantidad * d.precioUnitario) DESC";
            Query<Cajero> q = session.createQuery(hql, Cajero.class);
            q.setMaxResults(1);
            return q.getResultList();
        } finally { session.close(); }
    }

    public List<Cajero> traerCajeroQueMasRecaudoPorFecha(LocalDate fecha) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT c FROM Cajero c " +
                         "INNER JOIN c.unidadAsignada uv " +
                         "INNER JOIN uv.pedidos p " +
                         "INNER JOIN p.detalles d " +
                         "WHERE p.fechaTransaccion = :fecha " +
                         "GROUP BY c.id ORDER BY SUM(d.cantidad * d.precioUnitario) DESC";
            Query<Cajero> q = session.createQuery(hql, Cajero.class);
            q.setParameter("fecha", fecha);
            q.setMaxResults(1);
            return q.getResultList();
        } finally { session.close(); }
    }
    
    
    
    
    
    
    
    
    
    
}