package dao;

import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Plato;

public class PlatoDao {
	
    private Session session;
    private Transaction tx;
    private static PlatoDao instancia = null;

    protected PlatoDao() {}

    public static PlatoDao getInstancia() {
        if (instancia == null) {
            instancia = new PlatoDao();
        }
        return instancia;
    }
    
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

    public Set<Plato> traerTodas() {
        Set<Plato> set = null;
        try {
            iniciaOperacion();
            String hql = "from Plato p order by p.id";
            Query<Plato> query = session.createQuery(hql, Plato.class);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public Set<Plato> traer() {
        return traerTodas();
    }

    // trae una lista de platos por unidad de venta 
    public Set<Plato> traerPorUnidadVenta(long idUnidadVenta) {
        Set<Plato> set = null;
        try {
            iniciaOperacion();
            String hql = "from Plato p where p.unidadVenta.id = :idUnidad order by p.id";
            Query<Plato> query = session.createQuery(hql, Plato.class);
            query.setParameter("idUnidad", idUnidadVenta);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public long agregar(Plato objeto) {
        long id = 0;
        try {
            iniciaOperacion();
            id = (Long) session.save(objeto);
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
