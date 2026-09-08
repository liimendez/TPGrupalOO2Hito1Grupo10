package dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Pedido;

public class PedidoDao {

    private Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos", he);
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
                Hibernate.initialize(pedido.getFestival());
                Hibernate.initialize(pedido.getUnidadVenta());
                Hibernate.initialize(pedido.getDetalles());
                pedido.getDetalles().forEach(d -> Hibernate.initialize(d.getPlato()));
            }
        } finally {
            session.close();
        }
        return pedido;
    }

    @SuppressWarnings("unchecked")
    public List<Pedido> traerTodas() {
        List<Pedido> lista = null;
        try {
            iniciaOperacion();
            lista = session.createQuery("from Pedido p order by p.id", Pedido.class).list();
        } finally {
            session.close();
        }
        return lista;
    }

    public List<Pedido> traerPorUnidadVenta(long idUnidadVenta) {
        List<Pedido> lista = null;
        try {
            iniciaOperacion();
            String hql = "from Pedido p where p.unidadVenta.id = :idUnidad order by p.id";
            Query<Pedido> query = session.createQuery(hql, Pedido.class);
            query.setParameter("idUnidad", idUnidadVenta);
            lista = query.list();
        } finally {
            session.close();
        }
        return lista;
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