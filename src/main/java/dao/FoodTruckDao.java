package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.FoodTruck;

public class FoodTruckDao {

    private static Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos", he);
    }

    public int agregar(FoodTruck foodTruck) {
        int id = 0;

        try {
            iniciaOperacion();

            id = Integer.parseInt(session.save(foodTruck).toString());

            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }

    public FoodTruck traer(long idFoodTruck) {
        FoodTruck foodTruck = null;

        try {
            iniciaOperacion();

            foodTruck = (FoodTruck) session.get(FoodTruck.class, idFoodTruck);

        } finally {
            session.close();
        }

        return foodTruck;
    }
}
