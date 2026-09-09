package dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import datos.UnidadVenta;
import datos.Festival;
import datos.Plato;
import datos.PuestoDesarmable;
import datos.FoodTruck;

public class UnidadVentaDao {

    private Session session;
    private Transaction tx;
    private static UnidadVentaDao instancia = null;

    protected UnidadVentaDao() {}

    public static UnidadVentaDao getInstancia() {
        if (instancia == null) instancia = new UnidadVentaDao();
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
        Set<UnidadVenta> set = null;
        try {
            iniciaOperacion();
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
        try {
            iniciaOperacion();
            obj = session.get(UnidadVenta.class, id);
            if (obj != null) {
                Hibernate.initialize(obj.getFestival());
                Hibernate.initialize(obj.getPlatosOfrecidos());
                Hibernate.initialize(obj.getStaff());
            }
        } finally {
            session.close();
        }
        return obj;
    }

    public Set<UnidadVenta> traerTodas() {
        String hql = "select distinct u from UnidadVenta u " +
                     "left join fetch u.staff " +
                     "left join fetch u.platosOfrecidos " + 
                     "left join fetch u.festival " +
                     "left join fetch u.responsable order by u.id";
        return ejecutarConsultaLista(hql, null);
    }

    public UnidadVenta traerPorCodigoUnico(String codigoUnico) {
        UnidadVenta obj = null;
        try {
            iniciaOperacion();
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
                     "left join fetch u.festival f " +
                     "left join fetch u.responsable " +
                     "order by f.id asc, u.id asc";
        return ejecutarConsultaLista(hql, null);
    }

    public Set<UnidadVenta> traerOrdenadasPorMayorSuperficie() {
        String hql = "select distinct u from UnidadVenta u " +
                     "left join fetch u.festival " +
                     "order by u.superficieM2 desc";
        return ejecutarConsultaLista(hql, null);
    }

    public Set<UnidadVenta> traerPorFestivalOrdenadasPorSuperficie(long idFestival) {
        String hql = "select distinct u from UnidadVenta u " +
                     "left join fetch u.festival f " +
                     "where f.id = :idFestival " +
                     "order by u.superficieM2 desc";
        return ejecutarConsultaLista(hql, Map.of("idFestival", idFestival));
    }

    public Set<UnidadVenta> traerConMinimoPlatos(int minimoPlatos) {
        String hql = "select distinct u from UnidadVenta u " +
                     "left join fetch u.festival " +
                     "left join fetch u.platosOfrecidos " +
                     "where size(u.platosOfrecidos) >= :min " +
                     "order by size(u.platosOfrecidos) desc";
        return ejecutarConsultaLista(hql, Map.of("min", minimoPlatos));
    }

    public Set<Plato> traerPlatosPorUnidad(Long idUnidad) {
        Set<Plato> set = null;
        try {
            iniciaOperacion();
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
    
    // consultas de santi 
 // CONSULTA 1: PuestoDesarmable por tiempo de montaje
    public Set<PuestoDesarmable> traerPuestosPorTiempoMontaje(Festival festival, int desde, int hasta) {
        Set<PuestoDesarmable> set = null;
        try {
            iniciaOperacion();
            String hql = "FROM PuestoDesarmable p WHERE p.festival.id = :idFestival AND p.tiempoMontajeMin BETWEEN :desde AND :hasta";
            Query<PuestoDesarmable> query = session.createQuery(hql, PuestoDesarmable.class);
            query.setParameter("idFestival", festival.getId());
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }

    // CONSULTA 2: FoodTruck con conexion electrica
    public Set<FoodTruck> traerFoodTrucksConConexionElectrica(Festival festival) {
        Set<FoodTruck> set = null;
        try {
            iniciaOperacion();
            String hql = "FROM FoodTruck ft WHERE ft.festival.id = :idFestival AND ft.requiereConexionElectrica = true";
            Query<FoodTruck> query = session.createQuery(hql, FoodTruck.class);
            query.setParameter("idFestival", festival.getId());
            set = new LinkedHashSet<>(query.getResultList());
        } finally {
            session.close();
        }
        return set;
    }}