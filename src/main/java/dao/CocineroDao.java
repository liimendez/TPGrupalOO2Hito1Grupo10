package dao;

import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Cocinero;

public class CocineroDao {
	
    private Session session;
    private Transaction tx;
    private static CocineroDao instancia = null;

    protected CocineroDao() {}

    public static CocineroDao getInstancia() {
        if (instancia == null) {
            instancia = new CocineroDao();
        }
        return instancia;
    }
    
    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos de Cocinero", he);
    }
    
    public long agregar(Cocinero cocinero) {
        long id = 0;
        try {
            iniciaOperacion();
            id = (Long) session.save(cocinero);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            session.close();
        }
        return id;
    }
    
    public Cocinero traer(long idCocinero) {
        Cocinero cocinero = null;
        try {
            iniciaOperacion();
            cocinero = session.get(Cocinero.class, idCocinero);
        } finally {
            session.close();
        }
        return cocinero;
    }

    public Set<Cocinero> traerTodas() {
        Set<Cocinero> set = null;
        try {
            iniciaOperacion();
            Query<Cocinero> query = session.createQuery("from Cocinero c order by c.id", Cocinero.class);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public Set<Cocinero> traer() {
        return traerTodas();
    }

    public void actualizar(Cocinero objeto) {
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

    public void eliminar(Cocinero objeto) {
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