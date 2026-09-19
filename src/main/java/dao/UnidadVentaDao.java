package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import datos.Festival;
import datos.FoodTruck;
import datos.Plato;
import datos.PuestoDesarmable;
import datos.UnidadVenta;
import datos.Personal;

public class UnidadVentaDao {

    private Session session;
    private Transaction tx;

    private static UnidadVentaDao instancia = null;

    protected UnidadVentaDao() {}

    public static UnidadVentaDao getInstancia() {

        if (instancia == null) {
            instancia = new UnidadVentaDao();
        }

        return instancia;
    }

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de datos de UnidadVenta", he);
    }

    private Set<UnidadVenta> ejecutarConsultaLista(String hql, Map<String, Object> parametros) {

        Set<UnidadVenta> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<UnidadVenta> query = session.createQuery(hql, UnidadVenta.class);

            if (parametros != null) {
                for (Map.Entry<String, Object> e : parametros.entrySet()) {
                    query.setParameter(e.getKey(), e.getValue());
                }
            }

            set = new LinkedHashSet<>(query.getResultList());

        } finally {
            session.close();
        }

        return set;
    }

    public UnidadVenta traer(long id) {

        UnidadVenta obj = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "select distinct u from UnidadVenta u " +
                         "inner join fetch u.festival " +
                         "inner join fetch u.platosOfrecidos " +
                         "inner join fetch u.staff " +
                         "where u.id = :id";

            obj = session.createQuery(hql, UnidadVenta.class)
                         .setParameter("id", id)
                         .uniqueResult();

        } finally {
            session.close();
        }

        return obj;
    }

    public Set<UnidadVenta> traerTodas() {

        String hql = "select distinct u from UnidadVenta u " +
                     "inner join fetch u.staff " +
                     "inner join fetch u.platosOfrecidos " + 
                     "inner join fetch u.festival " +
                     "inner join fetch u.responsable order by u.id";

        return ejecutarConsultaLista(hql, null);
    }

    public UnidadVenta traerPorCodigoUnico(String codigoUnico) {

        UnidadVenta obj = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<UnidadVenta> query = session.createQuery("from UnidadVenta u where u.codigoUnico = :codigo", UnidadVenta.class);
            query.setParameter("codigo", codigoUnico);
            obj = query.uniqueResult();

        } finally {
            session.close();
        }

        return obj;
    }

    public Set<UnidadVenta> traerPorFestival(Festival festival) {

        String hql = "from UnidadVenta u where u.festival.id = :idFestival";

        return ejecutarConsultaLista(hql, Map.of("idFestival", festival.getId()));
    }

    public Set<UnidadVenta> traerOrdenadasPorFestival() {

        String hql = "select distinct u from UnidadVenta u " +
                     "inner join fetch u.festival f " +
                     "inner join fetch u.responsable " +
                     "order by f.id asc, u.id asc";

        return ejecutarConsultaLista(hql, null);
    }

    public Set<UnidadVenta> traerOrdenadasPorMayorSuperficie() {

        String hql = "select distinct u from UnidadVenta u " +
                     "inner join fetch u.festival " +
                     "order by u.superficieM2 desc";

        return ejecutarConsultaLista(hql, null);
    }

    public Set<UnidadVenta> traerPorFestivalOrdenadasPorSuperficie(long idFestival) {

        String hql = "select distinct u from UnidadVenta u " +
                     "inner join fetch u.festival f " +
                     "where f.id = :idFestival " +
                     "order by u.superficieM2 desc";

        return ejecutarConsultaLista(hql, Map.of("idFestival", idFestival));
    }

    public Set<UnidadVenta> traerConMinimoPlatos(int minimoPlatos) {

        String hql = "select distinct u from UnidadVenta u " +
                     "inner join fetch u.festival " +
                     "inner join fetch u.platosOfrecidos " +
                     "where size(u.platosOfrecidos) >= :min " +
                     "order by size(u.platosOfrecidos) desc";

        return ejecutarConsultaLista(hql, Map.of("min", minimoPlatos));
    }

    public Set<Plato> traerPlatosPorUnidad(Long idUnidad) {

        Set<Plato> set;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "from Plato p where p.unidadVenta.id = :idUnidad order by p.nombre";
            Query<Plato> query = session.createQuery(hql, Plato.class);
            query.setParameter("idUnidad", idUnidad);
            set = new LinkedHashSet<>(query.getResultList());

        } finally {
            session.close();
        }

        return set;
    }

    public long agregar(UnidadVenta objeto) {

        long id = 0;

        try {
            iniciaOperacion();
            id = (Long) session.save(objeto);
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;

        } finally {
            session.close();
        }

        return id;
    }

    public void actualizar(UnidadVenta objeto) {

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

    public void eliminar(UnidadVenta objeto) {

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
    
    public Set<UnidadVenta> traerOrdenadasPorMenorSuperficie() {

        String hql = "select distinct u from UnidadVenta u " +
                     "inner join fetch u.festival " +
                     "order by u.superficieM2 asc";

        return ejecutarConsultaLista(hql, null);
    }
    
	// CONSULTA 1: Trae todos los PuestoDesarmable de un Festival que su tiempo de montaje(tiempoMontajeMin) este en un rango indicado.
	public List<PuestoDesarmable> traerPuestosPorTiempoMontaje(Festival festival, int desde, int hasta) {

		List<PuestoDesarmable> lista = null;

		try {

			iniciaOperacion();

			String hql = "FROM PuestoDesarmable p " + "INNER JOIN FETCH p.festival f " + "WHERE f = :festival "
					+ "AND p.tiempoMontajeMin BETWEEN :desde AND :hasta";

			Query<PuestoDesarmable> query = session.createQuery(hql, PuestoDesarmable.class);

			query.setParameter("festival", festival);
			query.setParameter("desde", desde);
			query.setParameter("hasta", hasta);

			lista = query.getResultList();

		} finally {
			session.close();
		}

		return lista;
	}

	// CONSULTA 2: Trae todos los FoodTruck de un Festival dependiendo si requiere conexion electrica o no(requiereConexionElectrica = TRUE or FALSE).
	public List<FoodTruck> traerFoodTrucksConConexionElectrica(Festival festival, boolean requiereConexionElectrica) {

		List<FoodTruck> lista = null;

		try {

			iniciaOperacion();

			String hql = "FROM FoodTruck f " + "INNER JOIN FETCH f.festival festival " + "WHERE festival = :festival "
					+ "AND f.requiereConexionElectrica = :requiereConexionElectrica";

			Query<FoodTruck> query = session.createQuery(hql, FoodTruck.class);

			query.setParameter("festival", festival);
			query.setParameter("requiereConexionElectrica", requiereConexionElectrica);

			lista = query.getResultList();

		} finally {
			session.close();
		}

		return lista;
	}

	// CONSULTA 3: Trae todos los FoodTruck de un Festival que ofrezcan al menos un Plato con precio de venta menor o igual a un valor indicado (precioMaximo)
		//             y al traer esos FoodTruck solo mostrara los platos que no superen dicho valor indicado(precioMaximo).
	public List<FoodTruck> traerFoodTrucksPorFestivalYPrecioPlatoMax(Festival festival, double precioMaximo) {

		List<FoodTruck> lista = null;

		try {

			iniciaOperacion();

			String hql = "SELECT DISTINCT f FROM FoodTruck f " + "INNER JOIN FETCH f.festival festival "
					+ "INNER JOIN FETCH f.platosOfrecidos plato " + "WHERE festival = :festival "
					+ "AND plato.precioVenta <= :precioMaximo";

			Query<FoodTruck> query = session.createQuery(hql, FoodTruck.class);

			query.setParameter("festival", festival);
			query.setParameter("precioMaximo", precioMaximo);

			lista = query.getResultList();

		} finally {
			session.close();
		}

		return lista;
	}
}