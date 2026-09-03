package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Cajero;

public class CajeroDao {
	
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
    
    
    public int agregar(Cajero cajero) {
    	
        int id = 0;
        try {
        	
            iniciaOperacion();
            id = Integer.parseInt(session.save(cajero).toString());
            tx.commit();

        } catch (HibernateException he) {
        	
            manejaExcepcion(he);
            
        } finally {
        	
            session.close();
        }
        return id;
    }
    
    
    public Cajero traer(long idCajero) {
        
    	Cajero cajero = null;

        try {
            iniciaOperacion();

            cajero = (Cajero) session.get(Cajero.class, idCajero);

        } finally {
            session.close();
        }

        return cajero;
    }
	
}
