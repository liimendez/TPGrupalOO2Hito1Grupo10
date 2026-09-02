package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Plato;

public class PlatoDao {

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

    public int agregar(Plato plato) {
        int id = 0;

        try {
            iniciaOperacion();

            id = Integer.parseInt(session.save(plato).toString());

            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }

    public Plato traer(long idPlato) {
        Plato plato = null;

        try {
            iniciaOperacion();

            plato = (Plato) session.get(Plato.class, idPlato);

        } finally {
            session.close();
        }

        return plato;
    }
}
