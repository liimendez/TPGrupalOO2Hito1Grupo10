package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 * DAO generico: implementa el ABM (Alta, Baja, Modificacion) comun a
 * cualquier entidad. Cada DAO especifico (FestivalDAO, PersonalDAO, etc.)
 * extiende esta clase y solo agrega las consultas propias de su entidad.
 *
 * @param <T>  tipo de la entidad (ej: Festival)
 * @param <ID> tipo de la clave primaria (ej: Long)
 */
public abstract class GenericDAO<T, ID> {

	private final Class<T> entityClass;

	protected GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/** ALTA: inserta una entidad nueva. */
	public T agregar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(entidad);
			tx.commit();
			return entidad;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	/** MODIFICACION: actualiza una entidad existente. */
	public T modificar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			T actualizado = session.merge(entidad);
			tx.commit();
			return actualizado;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	/** BAJA: elimina una entidad a partir de su id. */
	public void eliminar(ID id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			T entidad = session.get(entityClass, id);
			if (entidad != null) {
				session.remove(entidad);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	/** Busca una entidad por su id (no modifica nada). */
	public T buscarPorId(ID id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.get(entityClass, id);
		} finally {
			session.close();
		}
	}

	/** Lista todas las entidades de este tipo. */
	public List<T> listarTodos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery("from " + entityClass.getSimpleName(), entityClass).list();
		} finally {
			session.close();
		}
	}
}
