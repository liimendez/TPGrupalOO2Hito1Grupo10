package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from PuestoDesarmable p inner join fetch p.festival where p.id = :id";
            puesto = session.createQuery(hql, PuestoDesarmable.class)
                            .setParameter("id", idPuesto)
                            .uniqueResult();

        } finally {
            session.close();
        }

        return puesto;
    }

    // Va en Set por el inner join fetch
    public Set<PuestoDesarmable> traerTodas() {

        Set<PuestoDesarmable> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from PuestoDesarmable p inner join fetch p.festival order by p.id";
            List<PuestoDesarmable> lista = session.createQuery(hql, PuestoDesarmable.class).list();
            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
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