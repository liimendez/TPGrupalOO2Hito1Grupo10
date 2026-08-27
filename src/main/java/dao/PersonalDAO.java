package dao;

import org.hibernate.Session;

import datos.Personal;
import util.HibernateUtil;

public class PersonalDAO extends GenericDAO<Personal, Long> {

	public PersonalDAO() {
		super(Personal.class);
	}

	/**
	 * Busca un empleado por dni. Como Personal es abstracta (herencia JOINED),
	 * Hibernate resuelve solo si el resultado es un Cocinero o un Cajero.
	 */
	public Personal buscarPorDni(String dni) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery("from Personal p where p.dni = :dni", Personal.class)
					.setParameter("dni", dni)
					.uniqueResult();
		} finally {
			session.close();
		}
	}

	// listarTodos() ya viene heredado de GenericDAO y devuelve todo el personal
	// (Cocineros y Cajeros mezclados) de forma polimorfica: es el caso de uso de
	// herencia que ya usabamos en Main.
}
