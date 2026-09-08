package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

    @SuppressWarnings("unchecked")
    public List<Cajero> traerTodos() {
        List<Cajero> lista = null;
        try {
            iniciaOperacion();
            String hql = "from Cajero c order by c.id";
            lista = session.createQuery(hql, Cajero.class).list();
        } finally {
            session.close();
        }
        return lista;
    }

    public void actualizar(Cajero objeto) {
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

    public void eliminar(Cajero objeto) {
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

    // esto es para calcular la recaudacion 
    //el cajero esta asignado a una unidad de venta 
    public double calcularRecaudacionPorCajero(long idCajero) {
        double total = 0;
        try {
            iniciaOperacion();
            // 1. Busco en que unidad está el cajero
            String hqlUnidad = "select p.unidadAsignada.id from Personal p where p.id = :idCajero";
            Query<Long> qUnidad = session.createQuery(hqlUnidad, Long.class);
            qUnidad.setParameter("idCajero", idCajero);
            Long idUnidad = qUnidad.uniqueResult();

            if (idUnidad != null) {
                // 2. Sumo todos los detalles de los pedidos de esa unidad
                String hqlSuma = "select coalesce(sum(d.subtotal), 0) from DetallePedido d where d.pedido.unidadVenta.id = :idUnidad";
                Query<Double> qSuma = session.createQuery(hqlSuma, Double.class);
                qSuma.setParameter("idUnidad", idUnidad);
                total = qSuma.uniqueResult();
            }
        } finally {
            session.close();
        }
        return total;
    }
}