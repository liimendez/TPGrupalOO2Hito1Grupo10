package test;

import datos.UnidadVenta;
import negocio.PlatoABM;
import negocio.UnidadVentaABM;

// Agrega 2 Platos asociados a una UnidadVenta.
// Relación Uno a Muchos entre UnidadVenta y Plato.

public class TestAgregarPlato {

    public static void main(String[] args) {

        UnidadVentaABM unidadABM = new UnidadVentaABM();
        PlatoABM platoABM = new PlatoABM();

        UnidadVenta unidadVenta = unidadABM.traer(1L);

        int idPlato1 = platoABM.agregar("Hamburguesa", 8000, 4000, unidadVenta);
        int idPlato2 = platoABM.agregar("Fideos", 3000, 1900, unidadVenta);

        System.out.println("Platos agregados correctamente.");
        System.out.println("ID Platos: " + idPlato1 + ", " + idPlato2);
    }
}
