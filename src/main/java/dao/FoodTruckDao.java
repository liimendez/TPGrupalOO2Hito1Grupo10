package dao;

import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
        try {
            iniciaOperacion();
            foodTruck = session.get(FoodTruck.class, idFoodTruck);
        } finally {
            session.close();
        }
        return foodTruck;
    }

    public Set<FoodTruck> traerTodas() {
        Set<FoodTruck> set = null;
        try {
            iniciaOperacion();
            Query<FoodTruck> query = session.createQuery("from FoodTruck f order by f.id", FoodTruck.class);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    public Set<FoodTruck> traer() {
        return traerTodas();
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