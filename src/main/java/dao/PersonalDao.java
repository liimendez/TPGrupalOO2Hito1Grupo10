package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Personal;

public class PersonalDao {
    private static Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en capa de datos Personal", he);
    }

    public Personal traer(long id) {
        Personal obj = null;
        try {
            iniciaOperacion();
            obj = session.get(Personal.class, id);
        } finally {
            session.close();
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public List<Personal> traerTodas() {
        List<Personal> lista = null;
        try {
            iniciaOperacion();
      
            String hql = "select distinct p from Personal p left join fetch p.unidadAsignada order by p.id";
            Query<Personal> query = session.createQuery(hql, Personal.class);
            lista = query.list();
        } finally {
            session.close();
        }
        return lista;
    }

    public long agregar(Personal objeto) {
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

    public void actualizar(Personal objeto) {
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
    
    public void eliminar(Personal objeto) {
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