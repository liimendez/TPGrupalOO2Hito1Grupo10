package dao;

import java.util.List;

import org.hibernate.Session;

import datos.UnidadVenta;
import util.HibernateUtil;

public class UnidadVentaDAO extends GenericDAO<UnidadVenta, Long> {

	public UnidadVentaDAO() {
		super(UnidadVenta.class);
	}

	/** Busca una unidad de venta por su codigo unico. */
	public UnidadVenta buscarPorCodigo(String codigoUnico) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery("from UnidadVenta u where u.codigoUnico = :codigo", UnidadVenta.class)
					.setParameter("codigo", codigoUnico)
					.uniqueResult();
		} finally {
			session.close();
		}
	}

	/**
	 * Lista todas las unidades de venta de un festival dado, por id.
	 * Es el caso de uso de relacion uno a muchos (Festival -> UnidadVenta).
	 */
	public List<UnidadVenta> listarPorFestival(Long festivalId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session
					.createQuery("from UnidadVenta u where u.festival.id = :festivalId", UnidadVenta.class)
					.setParameter("festivalId", festivalId)
					.list();
		} finally {
			session.close();
		}
	}
}
