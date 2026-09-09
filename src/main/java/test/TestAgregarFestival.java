package test;

import java.time.LocalDate;
import negocio.FestivalABM;

public class TestAgregarFestival {

    public static void main(String[] args) {
        try {
            FestivalABM abm = FestivalABM.getInstancia();

            long idFestival1 = abm.agregar("Festival Gourmet", "Primavera", 
                    LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 20));

            long idFestival2 = abm.agregar("Festival de la Milanesa", "Invierno",
                    LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 20));

            long idFestival3 = abm.agregar("Festival del Asado", "Verano",
                    LocalDate.of(2026, 1, 9), LocalDate.of(2026, 1, 19));

            long idFestival4 = abm.agregar("Festival Vegano", "Otoño",
                    LocalDate.of(2027, 4, 5), LocalDate.of(2027, 4, 15));

            System.out.println("Festivales agregados correctamente.");
            System.out.println("ID Festivales: " + idFestival1 + ", " + idFestival2 + ", " + idFestival3 + ", " + idFestival4);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}