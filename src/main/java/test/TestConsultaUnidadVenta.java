package test;

import java.util.List;

import datos.Plato;
import negocio.UnidadVentaABM;

// Consulta los Platos asociados a la UnidadVenta que su ID sea 1.
// UnidadVenta - Plato: Uno a Muchos.

public class TestConsultaUnidadVenta {

    public static void main(String[] args) {

        UnidadVentaABM abm = new UnidadVentaABM();

        List<Plato> platos = abm.traerPlatos(1L);

        for (Plato plato : platos) {
            System.out.println(plato);
        }
    }
}
