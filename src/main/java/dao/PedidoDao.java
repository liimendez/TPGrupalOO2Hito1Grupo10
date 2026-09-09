package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Pedido;

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
        try {
            iniciaOperacion();
            pedido = session.get(Pedido.class, idPedido);
            if (pedido != null) {
                Hibernate.initialize(pedido.getUnidadVenta());
                Hibernate.initialize(pedido.getUnidadVenta().getFestival());
                Hibernate.initialize(pedido.getCajero()); 
                Hibernate.initialize(pedido.getDetalles());
                pedido.getDetalles().forEach(d -> Hibernate.initialize(d.getPlato()));
            }
        } finally {
            session.close();
        }
        return pedido;
    }

    public Set<Pedido> traerTodas() {
        Set<Pedido> set = null;
        try {
            iniciaOperacion();
            List<Pedido> lista = session.createQuery("from Pedido p order by p.id", Pedido.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
    }

    public Set<Pedido> traerPorUnidadVenta(long idUnidadVenta) {
        Set<Pedido> set = null;
        try {
            iniciaOperacion();
            String hql = "from Pedido p where p.unidadVenta.id = :idUnidad order by p.id";
            Query<Pedido> query = session.createQuery(hql, Pedido.class);
            query.setParameter("idUnidad", idUnidadVenta);
            List<Pedido> lista = query.list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
    }
    public Set<Pedido> traerPorCajero(long idCajero) {
        Set<Pedido> set = null;
        try {
            iniciaOperacion();
            String hql = "SELECT DISTINCT p FROM Pedido p " +
                         "LEFT JOIN FETCH p.detalles d " +
                         "LEFT JOIN FETCH d.plato " +
                         "JOIN FETCH p.cajero " +
                         "WHERE p.cajero.id = :idCajero order by p.id";
            Query<Pedido> query = session.createQuery(hql, Pedido.class);
            query.setParameter("idCajero", idCajero);
            set = new LinkedHashSet<>(query.list());
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            session.close();
        }
        return set;
    }
    public Set<Pedido> traerPorCajeroConDetalles(long idCajero) {
        try {
            iniciaOperacion();
            String hql = "SELECT DISTINCT p FROM Pedido p " +
                         "LEFT JOIN FETCH p.detalles d " +
                         "LEFT JOIN FETCH d.plato " +
                         "JOIN FETCH p.cajero " +
                         "WHERE p.cajero.id = :idCajero order by p.id";
            Query<Pedido> query = session.createQuery(hql, Pedido.class);
            query.setParameter("idCajero", idCajero);
            Set<Pedido> set = new LinkedHashSet<>(query.list());
            tx.commit();
            return set;
        } finally {
            session.close();
        }
    }
    
    public Set<Pedido> traerPedidosOrdenadosParaCorte() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT DISTINCT p FROM Pedido p " +
                         "JOIN FETCH p.unidadVenta uv " +
                         "JOIN FETCH uv.festival f " +
                         "JOIN FETCH p.cajero " +
                         "LEFT JOIN FETCH p.detalles d " +
                         "LEFT JOIN FETCH d.plato " +
                         "ORDER BY f.id, uv.id";
            List<Pedido> lista = session.createQuery(hql, Pedido.class).getResultList();
            return new java.util.LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
    }

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
}