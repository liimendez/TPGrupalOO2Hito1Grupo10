package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.Plato;
import datos.UnidadVenta;

public class UnidadVentaDao {

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
 
    public int agregar(UnidadVenta unidadVenta) {
        int id = 0;

        try {
            iniciaOperacion();

            id = Integer.parseInt(session.save(unidadVenta).toString());

            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }

    public UnidadVenta traer(long idUnidadVenta) {
        UnidadVenta unidadVenta = null;

        try {
            iniciaOperacion();

            unidadVenta = (UnidadVenta) session.get(UnidadVenta.class, idUnidadVenta);

        } finally {
            session.close();
        }

        return unidadVenta;
    }

    public List<Plato> traerPlatos(long idUnidadVenta) {

        List<Plato> lista = new ArrayList<Plato>();

        try {
            iniciaOperacion();

            Query<Plato> query = session.createQuery("from Plato p where p.unidadVenta.id = :idUnidadVenta", Plato.class);

            query.setParameter("idUnidadVenta", idUnidadVenta);

            lista = query.getResultList();

        } finally {
            session.close();
        }

        return lista;
    }
}
