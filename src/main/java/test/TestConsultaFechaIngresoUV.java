package test;

import java.time.LocalDate;
import java.util.List;

import datos.FoodTruck;
import datos.Personal;
import datos.PuestoDesarmable;
import datos.UnidadVenta;
import negocio.UnidadVentaABM;

public class TestConsultaFechaIngresoUV {

	public static void main(String[] args) throws Exception {

		UnidadVentaABM unidadVentaABM = UnidadVentaABM.getInstancia();

		// Fecha que indica desde cuando mostrar el Personal que ingreso a su respectiva UnidadVenta
		LocalDate fechaDesde = LocalDate.of(2026, 8, 8);

		try {
			List<UnidadVenta> unidadesVenta = unidadVentaABM.traerUnidadesVentaPorFechaIngresoPersonal(fechaDesde);

			System.out.println("CONSULTA 4 - Trae todo el personal (Cajero o Cocinero) de una UnidadVenta (FoodTruck o PuestoDesarmable) que ingresó a partir de una fecha indicada(fechaDesde)\n");
			System.out.println("UnidadVenta <----- FoodTruck | Herencia");
			System.out.println("UnidadVenta <----- PuestoDesarmable | Herencia");
			System.out.println("UnidadVenta (1) ----- (0...*) Personal | Uno a Muchos");
			System.out.println("Personal <----- Cajero | Herencia");
			System.out.println("Personal <----- Cocinero | Herencia\n");
			System.out.println("------------------------------------------");
			System.out.println("Fecha de ingreso desde: " + fechaDesde);

			// Mostrar los datos mediante getters
			for (UnidadVenta uv : unidadesVenta) {

				System.out.println("------------------------------------------");

				// isntanceof para diferenciar que subclase es + el casteo para acceder a los datos de las subclases
				if (uv instanceof FoodTruck) {
					FoodTruck ft = (FoodTruck) uv;
					System.out.println("Tipo de UnidadVenta: FoodTruck");
				} else if (uv instanceof PuestoDesarmable) {
					PuestoDesarmable pd = (PuestoDesarmable) uv;
					System.out.println("Tipo de UnidadVenta: PuestoDesarmable");
				}

				System.out.println("ID: " + uv.getId());
				System.out.println("Nombre comercial: " + uv.getNombreComercial());
				System.out.println("Código único: " + uv.getCodigoUnico());

				// Personal asignado a la UnidadVenta que cumple la condición de la fecha
				System.out.println("Personal que ingresó a partir del " + fechaDesde + ":");
				for (Personal p : uv.getStaff()) {
					if (!p.getFechaDeIngreso().isBefore(fechaDesde)) {
						System.out.println("  - " + p.getNombre() + " " + p.getApellido() + ", DNI: " + p.getDni() + ", Fecha de Ingreso: " + p.getFechaDeIngreso());
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}