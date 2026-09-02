package test;

import java.time.LocalDate;

import negocio.FestivalABM;

// Agrega 3 Festivales a la base de datos en MySQL.
// Creación y persistencia de la entidad Festival en MySQL

public class TestAgregarFestival {

    public static void main(String[] args) {

        FestivalABM abm = new FestivalABM();

        int idFestival1 = abm.agregar("Festival Gourmet", "Primavera", LocalDate.of(2026, 1, 9), LocalDate.of(2026, 1, 19));
        int idFestival2 = abm.agregar("Festival de la Milanesa", "Invierno",LocalDate.of(2026, 4, 10), LocalDate.of(2026, 4, 20));
        int idFestival3 = abm.agregar("Festival del Asado", "Verano",LocalDate.of(2026, 7, 15), LocalDate.of(2026, 7, 25));

        System.out.println("Festivales agregados correctamente.");
        System.out.println("ID Festivales: " + idFestival1 + ", " + idFestival2 + ", " + idFestival3);
    }
}
