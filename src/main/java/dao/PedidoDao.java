package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Pedido;

public class PedidoDao {
	
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
    
    
    public int agregar(Pedido pedido) {
        
    	int id = 0;
        try {
        	
            iniciaOperacion();
            id = Integer.parseInt(session.save(pedido).toString());

            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);

        } finally {
            session.close();
        }

        return id;
    }
    
    

    public Pedido traer(long idPedido) {
    	
    	Pedido plato = null;
        try {
           
        	iniciaOperacion();
            plato = (Pedido) session.get(Pedido.class, idPedido);

        } finally {
        	
            session.close();
        }
        return plato;
    }
    
    
}
