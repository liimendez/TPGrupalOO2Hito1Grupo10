package dao;

import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Festival;
import datos.UnidadVenta;

public class FestivalDao {

    private Session session;
    private Transaction tx;
    private static FestivalDao instancia = null;

    protected FestivalDao() {}

    public static FestivalDao getInstancia() {
        if (instancia == null) {
            instancia = new FestivalDao();
        }
        return instancia;
    }

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos de Festival", he);
    }

    public long agregar(Festival festival) {
        long id = 0;
        try {
            iniciaOperacion();
            id = (Long) session.save(festival);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            session.close();
        }
        return id;
    }

    public Festival traer(long idFestival) {
        Festival festival = null;
        try {
            iniciaOperacion();
            festival = session.get(Festival.class, idFestival);
        } finally {
            session.close();
        }
        return festival;
    }

    public Set<Festival> traerTodas() {
        Set<Festival> set = null;
        try {
            iniciaOperacion();
            Query<Festival> query = session.createQuery("from Festival f order by f.id", Festival.class);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public Set<UnidadVenta> traerUnidades(long idFestival) {
        Set<UnidadVenta> set = null;
        try {
            iniciaOperacion();
            Query<UnidadVenta> query = session.createQuery("from UnidadVenta u where u.festival.id = :idFestival order by u.id", UnidadVenta.class);
            query.setParameter("idFestival", idFestival);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public void actualizar(Festival objeto) {
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

    public void eliminar(Festival objeto) {
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