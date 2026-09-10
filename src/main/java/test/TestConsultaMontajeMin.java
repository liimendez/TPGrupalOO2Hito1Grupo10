package test;

import java.util.List;

import datos.Festival;
import datos.PuestoDesarmable;
import negocio.FestivalABM;
import negocio.UnidadVentaABM;

public class TestConsultaMontajeMin {

	public static void main(String[] args) {

		FestivalABM festivalABM = FestivalABM.getInstancia();
		UnidadVentaABM unidadVentaABM = UnidadVentaABM.getInstancia();

		long idFestival = 1L;

		Festival festival = festivalABM.traer(idFestival);

		if (festival == null) {
			System.out.println("No existe el festival con el id " + idFestival);
			return;
		}

		// Rango tiempoMontajeMin
		int desde = 20;
		int hasta = 60;

		// CONSULTA
		List<PuestoDesarmable> puestos = unidadVentaABM.traerPuestosPorTiempoMontaje(festival, desde, hasta);

		System.out.println("CONSULTA 1 - Trae todos los PuestoDesarmable de un Festival que su tiempo de montaje(tiempoMontajeMin) este en un rango indicado.");
		System.out.println("------------------------------------------");

		System.out.println("Festival: " + festival.getNombre());
		System.out.println("Rango de tiempoMontajeMin: " + desde + " a " + hasta + " minutos");

		if (puestos.isEmpty()) {
			System.out.println("No se encontraron puestos desarmables.");
			return;
		}

		// Mostrar los datos mediante getters
		for (PuestoDesarmable puesto : puestos) {

			System.out.println("------------------------------------------");

			System.out.println("ID: " + puesto.getId());
			System.out.println("Nombre comercial: " + puesto.getNombreComercial());
			System.out.println("Código único: " + puesto.getCodigoUnico());
			System.out.println("Tiempo de montaje: " + puesto.getTiempoMontajeMin() + " minutos");
			System.out.println("Cantidad de carpas: " + puesto.getCantidadCarpas());
		}

		/*
		// Mostrar los datos utilizando el toString()
		for (PuestoDesarmable puesto : puestos) {
			System.out.printf("\n%s\n", puesto);
		}
		*/
	}
}