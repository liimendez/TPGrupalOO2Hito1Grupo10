package test;

import negocio.FestivalABM;
import negocio.PuestoDesarmableABM;
import datos.Festival;
import datos.PuestoDesarmable;

public class TestAgregarPuestoDesarmable {

    public static void main(String[] args) {
        try {
            FestivalABM festivalABM = FestivalABM.getInstancia();
            PuestoDesarmableABM puestoABM = PuestoDesarmableABM.getInstancia();

            Festival festival = festivalABM.traer(1L);
            if (festival == null) {
                System.out.println("No existe festival con ID 1");
                return;
            }

            PuestoDesarmable puesto = new PuestoDesarmable("PuestoDesarmable", 18, "PNUEVO001A", festival, null, 3, 45);

            long id = puestoABM.agregar(puesto);

            System.out.println("Puesto desarmable agregado correctamente.");
            System.out.println("ID PuestoDesarmable: " + id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}