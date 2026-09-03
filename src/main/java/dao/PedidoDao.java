package dao;
<<<<<<< HEAD
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import datos.Pedido;
public class PedidoDao {
    private static Session session;
    private Transaction tx;
=======

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.Pedido;

public class PedidoDao {
	
	private static Session session;
    private Transaction tx;

    
>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }
<<<<<<< HEAD
=======

>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos", he);
    }
<<<<<<< HEAD
    public Long agregar(Pedido pedido) {
        Long id = null;
        try {
            iniciaOperacion();
            id = (Long) session.save(pedido);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            session.close();
        }
        return id;
    }
    public Pedido traer(long idPedido) {
        Pedido pedido = null;
        try {
            iniciaOperacion();
            pedido = (Pedido) session.get(Pedido.class, idPedido);
            if (pedido != null) {
                Hibernate.initialize(pedido.getFestival());
                Hibernate.initialize(pedido.getUnidadVenta());
                Hibernate.initialize(pedido.getDetalles());
                pedido.getDetalles().forEach(d -> Hibernate.initialize(d.getPlato()));
            }
        } finally {
            session.close();
        }
        return pedido;
    }
}
=======
    
    
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
>>>>>>> e72ed5073ac1c7caed7fd8222f66461d9040c85e
