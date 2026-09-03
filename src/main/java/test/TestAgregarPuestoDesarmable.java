package test;

import negocio.FestivalABM;
import negocio.PuestoDesarmableABM;
import datos.Festival;
import datos.PuestoDesarmable;

// Agrega un PuestoDesarmable asociado a un Festival.
// Creación de una subclase de UnidadVenta mediante herencia.
// El responsable quedo en null para demostrar que puede no estar asignado a la unidad.

public class TestAgregarPuestoDesarmable {

    public static void main(String[] args) {

        FestivalABM festivalABM = new FestivalABM();
        PuestoDesarmableABM puestoABM = new PuestoDesarmableABM();

        Festival festival = festivalABM.traer(1L);

        PuestoDesarmable puesto = new PuestoDesarmable("PuestoDesarmable", 18, "PNUEVO001A", festival, null, 3, 45);

        int id = puestoABM.agregar(puesto);

        System.out.println("Puesto desarmable agregado correctamente.");
        System.out.println("ID PuestoDesarmable: " + id);
    }
}
