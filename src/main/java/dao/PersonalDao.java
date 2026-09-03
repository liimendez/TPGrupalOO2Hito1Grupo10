package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.Personal;
import datos.UnidadVenta;

public class PersonalDao {
	
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
    
    
    public int agregar(Personal personal) {
    	
        int id = 0;
        try {
        	
            iniciaOperacion();
            id = Integer.parseInt(session.save(personal).toString());
            tx.commit();

        } catch (HibernateException he) {
        	
            manejaExcepcion(he);

        } finally {
        	
            session.close();
        }

        return id;
    }
    
    
    public Personal traer(long idPersonal) {
        Personal personal = null;

        try {
            iniciaOperacion();

            personal = (Personal) session.get(Personal.class, idPersonal);

        } finally {
            session.close();
        }

        return personal;
    }
}
