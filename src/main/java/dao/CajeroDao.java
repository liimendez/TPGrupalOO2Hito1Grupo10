package dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.Cajero;

public class CajeroDao {
	
	
    private Session session;
    private Transaction tx;
    private static CajeroDao instancia = null;

    protected CajeroDao() {}

    public static CajeroDao getInstancia() {
        if (instancia == null) {
            instancia = new CajeroDao();
        }
        return instancia;
    }
    
    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }
    
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a cajeroDao", he);
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
    
    public Cajero traer(long id) {
        Cajero c = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cajero c inner join fetch c.unidadAsignada where c.id = :id";
            c = (Cajero) session.createQuery(hql)
                                 .setParameter("id", id)
                                 .uniqueResult();
        } finally {
            session.close();
        }
        return c;
    }


    public Set<Cajero> traerTodos() {
        Set<Cajero> set = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cajero c inner join fetch c.unidadAsignada uv inner join fetch uv.festival order by c.id";
            List<Cajero> lista = session.createQuery(hql, Cajero.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
    }


    public Set<Cajero> traerCajerosOrdenados() {
        Set<Cajero> set = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Cajero c order by c.apellido asc, c.nombre asc";
            List<Cajero> lista = session.createQuery(hql, Cajero.class).list();
            set = new LinkedHashSet<>(lista);
        } finally {
            session.close();
        }
        return set;
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

    // la recaudacion se calcula a partir de la suma de los subtotales de los detalles pedidos de una unidad de venta.
    public double calcularRecaudacionPorCajero(long idCajero) {
        double total = 0;
        try {
            iniciaOperacion();
            // 1. Busco en que unidad esta el cajero
            String hqlUnidad = "select c.unidadAsignada.id from Cajero c where c.id = :idCajero";
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