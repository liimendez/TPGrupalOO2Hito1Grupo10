package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.FoodTruck;

public class FoodTruckDao {

    private Session session;
    private Transaction tx;

    private static FoodTruckDao instancia = null;

    protected FoodTruckDao() {}

    public static FoodTruckDao getInstancia() {

        if (instancia == null) {
            instancia = new FoodTruckDao();
        }

        return instancia;
    }

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos de FoodTruck", he);
    }

    public long agregar(FoodTruck foodTruck) {

        long id = 0;

        try {
            iniciaOperacion();
            id = (Long) session.save(foodTruck);
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;

        } finally {
            session.close();
        }

        return id;
    }

    public FoodTruck traer(long idFoodTruck) {

        FoodTruck foodTruck = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from FoodTruck f inner join fetch f.festival where f.id = :id";
            foodTruck = session.createQuery(hql, FoodTruck.class)
                               .setParameter("id", idFoodTruck)
                               .uniqueResult();

        } finally {
            session.close();
        }

        return foodTruck;
    }


    public Set<FoodTruck> traerTodas() {

        Set<FoodTruck> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from FoodTruck f inner join fetch f.festival order by f.id";
            List<FoodTruck> lista = session.createQuery(hql, FoodTruck.class).list();
            set = new LinkedHashSet<>(lista);

        } finally {
            session.close();
        }

        return set;
    }

    public void actualizar(FoodTruck objeto) {

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

    public void eliminar(FoodTruck objeto) {

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