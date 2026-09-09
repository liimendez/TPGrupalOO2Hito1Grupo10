package dao;

import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.PuestoDesarmable;

public class PuestoDesarmableDao {

    private Session session;
    private Transaction tx;
    private static PuestoDesarmableDao instancia = null;

    protected PuestoDesarmableDao() {}

    public static PuestoDesarmableDao getInstancia() {
        if (instancia == null) {
            instancia = new PuestoDesarmableDao();
        }
        return instancia;
    }

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos de PuestoDesarmable", he);
    }

    public long agregar(PuestoDesarmable puesto) {
        long id = 0;
        try {
            iniciaOperacion();
            id = (Long) session.save(puesto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            session.close();
        }
        return id;
    }

    public PuestoDesarmable traer(long idPuesto) {
        PuestoDesarmable puesto = null;
        try {
            iniciaOperacion();
            puesto = session.get(PuestoDesarmable.class, idPuesto);
        } finally {
            session.close();
        }
        return puesto;
    }

    public Set<PuestoDesarmable> traerTodas() {
        Set<PuestoDesarmable> set = null;
        try {
            iniciaOperacion();
            Query<PuestoDesarmable> query = session.createQuery("from PuestoDesarmable p order by p.id", PuestoDesarmable.class);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public Set<PuestoDesarmable> traer() {
        return traerTodas();
    }

    public void actualizar(PuestoDesarmable objeto) {
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

    public void eliminar(PuestoDesarmable objeto) {
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