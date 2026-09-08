package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
        throw new HibernateException("ERROR en capa de datos Plato", he);
    }

    public Plato traer(long id) {
        Plato obj = null;
        try {
            iniciaOperacion();
            obj = session.get(Plato.class, id);
        } finally {
            session.close();
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public List<Plato> traerTodas() {
        List<Plato> lista = null;
        try {
            iniciaOperacion();
            String hql = "from Plato p order by p.id";
            Query<Plato> query = session.createQuery(hql, Plato.class);
            lista = query.list();
        } finally {
            session.close();
        }
        return lista;
    }

    // trae una lista de platos por unidad de venta 
    @SuppressWarnings("unchecked")
    public List<Plato> traerPorUnidadVenta(long idUnidadVenta) {
        List<Plato> lista = null;
        try {
            iniciaOperacion();
            String hql = "from Plato p where p.unidadVenta.id = :idUnidad order by p.id";
            Query<Plato> query = session.createQuery(hql, Plato.class);
            query.setParameter("idUnidad", idUnidadVenta);
            lista = query.list();
        } finally {
            session.close();
        }
        return lista;
    }

    public int agregar(Plato objeto) {
        int id = 0;
        try {
            iniciaOperacion();
            id = ((Long) session.save(objeto)).intValue();
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            session.close();
        }
        return id;
    }

    public void actualizar(Plato objeto) {
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

    public void eliminar(Plato objeto) {
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
