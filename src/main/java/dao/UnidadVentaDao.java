package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.UnidadVenta;
import datos.Festival;

public class UnidadVentaDao {

    private static Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de datos de UnidadVenta", he);
    }

    public UnidadVenta traer(long id) {
        UnidadVenta obj = null;
        try {
            iniciaOperacion();
            obj = session.get(UnidadVenta.class, id);
            // inicializamos colecciones para evitar LazyInitialization
            if(obj != null) {
                obj.getStaff().size();
                obj.getPlatosOfrecidos().size();
            }
        } finally {
            session.close();
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public List<UnidadVenta> traerTodas() {
        List<UnidadVenta> lista = null;
        try {
            iniciaOperacion();
            String hql = "select distinct u from UnidadVenta u " +
                         "left join fetch u.staff " +
                         "left join fetch u.platosOfrecidos " + 
                         "left join fetch u.festival " +
                         "left join fetch u.responsable " +
                         "order by u.id";
            Query<UnidadVenta> query = session.createQuery(hql, UnidadVenta.class);
            lista = query.list();
        } finally {
            session.close();
        }
        return lista;
    }

    public UnidadVenta traerPorCodigoUnico(String codigoUnico) {
        UnidadVenta obj = null;
        try {
            iniciaOperacion();
            Query<UnidadVenta> query = session.createQuery("from UnidadVenta u where u.codigoUnico = :codigo", UnidadVenta.class);
            query.setParameter("codigo", codigoUnico);
            obj = query.uniqueResult();
        } finally {
            session.close();
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public List<UnidadVenta> traerPorFestival(Festival festival) {
        List<UnidadVenta> lista = null;
        try {
            iniciaOperacion();
            Query<UnidadVenta> query = session.createQuery("from UnidadVenta u where u.festival.id = :idFestival");
            query.setParameter("idFestival", festival.getId());
            lista = query.list();
        } finally {
            session.close();
        }
        return lista;
    }

    public long agregar(UnidadVenta objeto) {
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

    public void actualizar(UnidadVenta objeto) {
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

    public void eliminar(UnidadVenta objeto) {
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
