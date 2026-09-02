package test;

import datos.PuestoDesarmable;
import negocio.PuestoDesarmableABM;

// Consulta un PuestoDesarmable mediante su ID.
// PuestoDesarmable -> UnidadVenta: Herencia.

public class TestConsultaPuestoDesarmable {

    public static void main(String[] args) {

        PuestoDesarmableABM abm = new PuestoDesarmableABM();

        PuestoDesarmable puesto = abm.traer(2L);

        System.out.println(puesto);
    }
}
