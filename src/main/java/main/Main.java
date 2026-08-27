package main;

import java.time.LocalDate;
import java.util.List;

import dao.FestivalDAO;
import dao.PedidoDAO;
import dao.PersonalDAO;
import dao.PlatoDAO;
import dao.UnidadVentaDAO;
import datos.Cajero;
import datos.Cajero.Turno;
import datos.Cocinero;
import datos.Festival;
import datos.FoodTruck;
import datos.Pedido;
import datos.Personal;
import datos.Plato;
import datos.PuestoDesarmable;
import util.HibernateUtil;

public class Main {

	private static final FestivalDAO festivalDAO = new FestivalDAO();
	private static final PersonalDAO personalDAO = new PersonalDAO();
	private static final UnidadVentaDAO unidadVentaDAO = new UnidadVentaDAO();
	private static final PlatoDAO platoDAO = new PlatoDAO();
	private static final PedidoDAO pedidoDAO = new PedidoDAO();

	public static void main(String[] args) {
		cargarDatosDePrueba();
		demoAbm();
		casoDeUsoHerencia();
		casoDeUsoUnoAMuchos();
		HibernateUtil.shutdown();
	}

	/**
	 * Inserta datos de prueba usando los DAO (metodo agregar = ALTA). Se corre
	 * una sola vez, con la base vacia.
	 */
	private static void cargarDatosDePrueba() {
		Festival festival = festivalDAO.agregar(new Festival("Sabores de Verano", "Verano 2026",
				LocalDate.of(2026, 1, 10), LocalDate.of(2026, 2, 10)));

		Cocinero cocinero = new Cocinero("Ana", "Perez", "30111222", LocalDate.of(1990, 5, 3),
				LocalDate.of(2024, 1, 15), 500000, "Parrilla", 80000);
		personalDAO.agregar(cocinero);

		Cajero cajero = new Cajero("Luis", "Gomez", "28999111", LocalDate.of(1988, 8, 20),
				LocalDate.of(2023, 6, 1), 450000, Turno.MANIANA);
		personalDAO.agregar(cajero);

		FoodTruck foodTruck = new FoodTruck("El Fueguito", 18.5, "FT2026ABCD", festival, cocinero,
				"AB123CD", true);
		foodTruck.asignarStaff(cocinero);
		unidadVentaDAO.agregar(foodTruck);

		PuestoDesarmable puesto = new PuestoDesarmable("Dulce Rincon", 12.0, "PD2026XYZ1", festival, cajero,
				2, 45);
		puesto.asignarStaff(cajero);
		unidadVentaDAO.agregar(puesto);

		Plato choripan = new Plato("Choripan", 5000, 2200);
		foodTruck.agregarPlato(choripan);
		platoDAO.agregar(choripan);

		Plato alfajor = new Plato("Alfajor artesanal", 2500, 900);
		puesto.agregarPlato(alfajor);
		platoDAO.agregar(alfajor);

		Pedido pedido = new Pedido(LocalDate.now(), festival, foodTruck);
		pedido.agregarDetalle(choripan, 3);
		pedidoDAO.agregar(pedido); // cascade = ALL persiste tambien el DetallePedido

		System.out.println("Datos de prueba cargados correctamente.");
	}

	/**
	 * ABM en accion: alta, modificacion y baja usando PersonalDAO, sobre un
	 * Cocinero de prueba que se crea y se borra en la misma corrida (para no
	 * ensuciar la base con datos que despues haya que limpiar a mano).
	 */
	private static void demoAbm() {
		System.out.println("\n--- Demo ABM (PersonalDAO) ---");

		// ALTA
		Cocinero nuevo = new Cocinero("Sofia", "Ramirez", "40222333", LocalDate.of(1995, 3, 12),
				LocalDate.of(2025, 2, 1), 520000, "Reposteria", 60000);
		personalDAO.agregar(nuevo);
		System.out.println("Alta -> " + nuevo);

		// MODIFICACION
		nuevo.setSueldoBase(560000);
		personalDAO.modificar(nuevo);
		Personal actualizado = personalDAO.buscarPorId(nuevo.getId());
		System.out.println("Modificacion -> nuevo sueldo base leido de la BD: " + actualizado.getSueldoBase());

		// BAJA
		personalDAO.eliminar(nuevo.getId());
		Personal borrado = personalDAO.buscarPorId(nuevo.getId());
		System.out.println("Baja -> se elimino a Sofia Ramirez. Busqueda posterior devuelve: " + borrado);
	}

	/**
	 * Caso de uso 1 - Herencia: PersonalDAO.listarTodos() trae todo el personal
	 * de forma polimorfica. Hibernate resuelve automaticamente si cada registro
	 * es un Cocinero o un Cajero (estrategia JOINED) y calcularSueldo() ejecuta
	 * la implementacion correspondiente a cada subclase.
	 */
	private static void casoDeUsoHerencia() {
		List<Personal> empleados = personalDAO.listarTodos();
		System.out.println("\n--- Caso de uso: herencia (Personal) ---");
		for (Personal p : empleados) {
			System.out.println(p.getClass().getSimpleName() + " - " + p.getNombre() + " " + p.getApellido()
					+ " - sueldo calculado: " + p.calcularSueldo());
		}
	}

	/**
	 * Caso de uso 2 - Uno a Muchos: dado un festival, UnidadVentaDAO.listarPorFestival()
	 * trae todas las unidades de venta habilitadas para el (Festival 1 --- * UnidadVenta).
	 */
	private static void casoDeUsoUnoAMuchos() {
		Festival festival = festivalDAO.buscarPorNombre("Sabores de Verano");
		System.out.println("\n--- Caso de uso: uno a muchos (Festival -> UnidadVenta) ---");
		if (festival != null) {
			System.out.println("Festival: " + festival.getNombre());
			unidadVentaDAO.listarPorFestival(festival.getId()).forEach(u -> System.out
					.println("  Unidad: " + u.getNombreComercial() + " (" + u.getClass().getSimpleName() + ")"));
		}
	}
}
