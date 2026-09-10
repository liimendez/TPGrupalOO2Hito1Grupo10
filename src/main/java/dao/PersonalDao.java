package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Personal;

public class PersonalDao {

    private Session session;
    private Transaction tx;

    private static PersonalDao instancia = null;

    protected PersonalDao() {}

    public static PersonalDao getInstancia() {

        if (instancia == null) {
            instancia = new PersonalDao();
        }

        return instancia;
    }

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
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from Personal p inner join fetch p.unidadAsignada where p.id = :id";
            obj = session.createQuery(hql, Personal.class)
                         .setParameter("id", id)
                         .uniqueResult();

        } finally {
            session.close();
        }

        return obj;
    }

    // Va en Set por el inner join fetch
    public Set<Personal> traerTodas() {

        Set<Personal> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from Personal p inner join fetch p.unidadAsignada order by p.id";
            List<Personal> lista = session.createQuery(hql, Personal.class).list();
            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
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