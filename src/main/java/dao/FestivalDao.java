package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.Festival;
import datos.UnidadVenta;

public class FestivalDao {

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

    public int agregar(Festival festival) {
        int id = 0;

        try {
            iniciaOperacion();

            id = Integer.parseInt(session.save(festival).toString());

            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }

    public Festival traer(long idFestival) {
        Festival festival = null;

        try {
            iniciaOperacion();

            festival = (Festival) session.get(Festival.class, idFestival);

        } finally {
            session.close();
        }

        return festival;
    }

    public List<UnidadVenta> traerUnidades(long idFestival) {

        List<UnidadVenta> lista = new ArrayList<UnidadVenta>();

        try {
            iniciaOperacion();

            Query<UnidadVenta> query = session.createQuery("from UnidadVenta u where u.festival.id = :idFestival",UnidadVenta.class);

            query.setParameter("idFestival", idFestival);

            lista = query.getResultList();

        } finally {
            session.close();
        }

        return lista;
    }
}
