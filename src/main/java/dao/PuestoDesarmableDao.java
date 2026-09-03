package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.PuestoDesarmable;

public class PuestoDesarmableDao {

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

    public int agregar(PuestoDesarmable puesto) {
        int id = 0;

        try {
            iniciaOperacion();

            id = Integer.parseInt(session.save(puesto).toString());

            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }

    public PuestoDesarmable traer(long idPuesto) {
        PuestoDesarmable puesto = null;

        try {
            iniciaOperacion();

            puesto = (PuestoDesarmable) session.get(PuestoDesarmable.class, idPuesto);

        } finally {
            session.close();
        }

        return puesto;
    }
}
