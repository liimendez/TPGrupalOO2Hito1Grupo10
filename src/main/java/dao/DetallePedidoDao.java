package dao;

import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.DetallePedido;

public class DetallePedidoDao {

    private Session session;
    private Transaction tx;
    private static DetallePedidoDao instancia = null;

    protected DetallePedidoDao() {}

    public static DetallePedidoDao getInstancia() {
        if (instancia == null) {
            instancia = new DetallePedidoDao();
        }
        return instancia;
    }

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos de DetallePedido", he);
    }

    public Long agregar(DetallePedido detallePedido) {
        Long id = null;
        try {
            iniciaOperacion();
            id = (Long) session.save(detallePedido);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            session.close();
        }
        return id;
    }

    public DetallePedido traer(long id) {
        DetallePedido detallePedido = null;
        try {
            iniciaOperacion();
            detallePedido = session.get(DetallePedido.class, id);
        } finally {
            session.close();
        }
        return detallePedido;
    }

    public Set<DetallePedido> traerTodas() {
        Set<DetallePedido> set = null;
        try {
            iniciaOperacion();
            Query<DetallePedido> query = session.createQuery("from DetallePedido d order by d.id", DetallePedido.class);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public Set<DetallePedido> traer() {
        return traerTodas();
    }

    public void actualizar(DetallePedido objeto) {
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

    public void eliminar(DetallePedido objeto) {
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