package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Cocinero;
import datos.FoodTruck;

public class CocineroDao {
	
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
    
    
    public int agregar(Cocinero cocinero) {
    	
        int id = 0;
        try {
        	
            iniciaOperacion();
            id = Integer.parseInt(session.save(cocinero).toString());
            tx.commit();

        } catch (HibernateException he) {
        	
            manejaExcepcion(he);
            
        } finally {
        	
            session.close();
        }
        return id;
    }

    
    
    public Cocinero traer(long idCocinero) {
        
    	Cocinero cocinero = null;

        try {
            iniciaOperacion();

            cocinero = (Cocinero) session.get(Cocinero.class, idCocinero);

        } finally {
            session.close();
        }

        return cocinero;
    }
    

}
