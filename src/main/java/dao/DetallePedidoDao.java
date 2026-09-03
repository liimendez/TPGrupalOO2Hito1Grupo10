package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.DetallePedido;

public class DetallePedidoDao {

    private static Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {

        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();

    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {

        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos", he);

    }

    public Long agregar(DetallePedido detallePedido) {

        Long id = null;

        try {

            iniciaOperacion();

            id = (Long) session.save(detallePedido);

            tx.commit();

        } catch (HibernateException he) {

            manejaExcepcion(he);

        } finally {

            session.close();

        }

        return id;
    }

    public DetallePedido traer(long id) {

        DetallePedido detallePedido = null;

        try {

            iniciaOperacion();

            detallePedido = (DetallePedido)
                    session.get(DetallePedido.class, id);

        } finally {

            session.close();

        }

        return detallePedido;
    }
}
