package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // si Cocinero tiene unidadAsignada
            String hql = "from Cocinero c inner join fetch c.unidadAsignada where c.id = :id";
            cocinero = session.createQuery(hql, Cocinero.class)
                               .setParameter("id", idCocinero)
                               .uniqueResult();
        } catch (Exception e) {
            // fallback si Cocinero no tiene unidadAsignada
            Session s2 = HibernateUtil.getSessionFactory().openSession();
            try { cocinero = s2.get(Cocinero.class, idCocinero); } 
            finally { s2.close(); }
        } finally {
            session.close();
        }
        return cocinero;
    }

    public Set<Cocinero> traerTodas() {
        Set<Cocinero> set;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cocinero c inner join fetch c.unidadAsignada uv inner join fetch uv.festival order by c.id";
            List<Cocinero> lista = session.createQuery(hql, Cocinero.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
    }

    public Set<Cocinero> traerCocinerosOrdenados() {
        Set<Cocinero> set;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cocinero c order by c.apellido asc, c.nombre asc";
            List<Cocinero> lista = session.createQuery(hql, Cocinero.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
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