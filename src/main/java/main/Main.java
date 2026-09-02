package main;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.HibernateUtil;
import datos.Cajero;
import datos.Cajero.Turno;
import datos.Cocinero;
import datos.Festival;
import datos.FoodTruck;
import datos.Pedido;
import datos.Personal;
import datos.Plato;
import datos.PuestoDesarmable;
import datos.UnidadVenta;

public class Main {

	public static void main(String[] args) {
		cargarDatosDePrueba();
		casoDeUsoHerenciaPersonal();
		casoDeUsoHerenciaUnidadVenta();
		casoDeUsoUnoAMuchos();
		casoDeUsoPedido();
		HibernateUtil.shutdown();
	}

	/**
	 * Inserta datos de prueba: un festival, dos unidades de venta (una de cada
	 * subtipo), dos empleados (uno de cada subtipo), sus platos, y un pedido
	 * con detalle.
	 */
	private static void cargarDatosDePrueba() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Festival festival = new Festival("Sabores de Verano", "Verano 2026",
					LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10));
			session.persist(festival);

			Cocinero cocinero = new Cocinero("Maru", "Botana", "30111522", LocalDate.of(1967, 5, 3),
					LocalDate.of(2024, 1, 15), 500000, "Cocina", 90000);
			session.persist(cocinero);

			Cajero cajero = new Cajero("Luis", "Gomez", "28999111", LocalDate.of(1988, 8, 20),
					LocalDate.of(2023, 6, 1), 450000, Turno.MANIANA);
			session.persist(cajero);

			FoodTruck foodTruck = new FoodTruck("El Fueguito", 18.5, "FT2026ABCD", festival, cocinero,
					"AB123CD", true);
			foodTruck.asignarStaff(cocinero);
			session.persist(foodTruck);

			PuestoDesarmable puesto = new PuestoDesarmable("Dulce Rincon", 12.0, "PD2026XYZ1", festival, cajero,
					2, 45);
			puesto.asignarStaff(cajero);
			session.persist(puesto);

			Plato choripan = new Plato("Choripan", 5000, 2200);
			foodTruck.agregarPlato(choripan);
			session.persist(choripan);

			Plato alfajor = new Plato("Alfajor artesanal", 2500, 900);
			puesto.agregarPlato(alfajor);
			session.persist(alfajor);

			Pedido pedido = new Pedido(LocalDate.now(), festival, foodTruck);
			session.persist(pedido);
			pedido.agregarDetalle(choripan, 3);
			session.persist(pedido);

			tx.commit();
			System.out.println("Datos de prueba cargados correctamente.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/** Recorre Personal de forma polimórfica: Hibernate resuelve si es Cocinero o Cajero. */
	private static void casoDeUsoHerenciaPersonal() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			List<Personal> empleados = session.createQuery("from Personal", Personal.class).list();
			System.out.println("\n--- Herencia: Personal (Cocinero / Cajero) ---");
			for (Personal p : empleados) {
				System.out.println(p.getClass().getSimpleName() + " - " + p.getNombre() + " " + p.getApellido()
						+ " - sueldo calculado: " + p.calcularSueldo());
			}
		} finally {
			session.close();
		}
	}

	/** Recorre UnidadVenta de forma polimórfica: Hibernate resuelve si es FoodTruck o PuestoDesarmable. */
	private static void casoDeUsoHerenciaUnidadVenta() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			List<UnidadVenta> unidades = session.createQuery("from UnidadVenta", UnidadVenta.class).list();
			System.out.println("\n--- Herencia: UnidadVenta (FoodTruck / PuestoDesarmable) ---");
			for (UnidadVenta u : unidades) {
				System.out.println(u.getClass().getSimpleName() + " - " + u.getNombreComercial()
						+ " - código: " + u.getCodigoUnico() + " - responsable: "
						+ (u.getResponsable() != null ? u.getResponsable().getNombre() : "sin asignar"));
			}
		} finally {
			session.close();
		}
	}

	/** Dado un festival, recorre sus unidades de venta (Festival 1 --- * UnidadVenta). */
	private static void casoDeUsoUnoAMuchos() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Festival festival = session
					.createQuery("from Festival f where f.nombre = :nombre", Festival.class)
					.setParameter("nombre", "Sabores de Verano")
					.uniqueResult();
			System.out.println("\n--- Uno a muchos: Festival -> UnidadVenta ---");
			if (festival != null) {
				System.out.println("Festival: " + festival.getNombre());
				festival.getUnidadesVenta().forEach(u -> System.out
						.println("  Unidad: " + u.getNombreComercial() + " (" + u.getClass().getSimpleName() + ")"));
			}
		} finally {
			session.close();
		}
	}

	/** Recorre los pedidos de un festival junto con su detalle y total. */
	private static void casoDeUsoPedido() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Festival festival = session
					.createQuery("from Festival f where f.nombre = :nombre", Festival.class)
					.setParameter("nombre", "Sabores de Verano")
					.uniqueResult();
			System.out.println("\n--- Pedidos del festival ---");
			if (festival != null) {
				for (Pedido pedido : festival.getPedidos()) {
					System.out.println(pedido);
					pedido.getDetalles().forEach(d -> System.out.println("  Detalle: " + d.getCantidad() + " x "
							+ d.getPlato().getNombre() + " = " + d.calcularSubtotal()));
				}
			}
		} finally {
			session.close();
		}
	}
}