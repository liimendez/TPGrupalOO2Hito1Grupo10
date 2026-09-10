package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

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
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // con fetch para no hacer N+1 cuando se llame a detalle.getPlato()
            String hql = "from DetallePedido d inner join fetch d.plato where d.id = :id";
            detallePedido = session.createQuery(hql, DetallePedido.class)
                                   .setParameter("id", id)
                                   .uniqueResult();

        } finally {
            session.close();
        }

        return detallePedido;
    }

    // utiliza lista del tipo set por el inner join fetch
    public Set<DetallePedido> traerTodas() {

        Set<DetallePedido> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from DetallePedido d inner join fetch d.plato p inner join fetch d.pedido order by d.id";
            List<DetallePedido> lista = session.createQuery(hql, DetallePedido.class).list();
            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
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