package dao;

import org.hibernate.Session;

import datos.Festival;
import util.HibernateUtil;

public class FestivalDAO extends GenericDAO<Festival, Long> {

	public FestivalDAO() {
		super(Festival.class);
	}

	/** Busca un festival por nombre exacto. Devuelve null si no existe. */
	public Festival buscarPorNombre(String nombre) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery("from Festival f where f.nombre = :nombre", Festival.class)
					.setParameter("nombre", nombre)
					.uniqueResult();
		} finally {
			session.close();
		}
	}
}
