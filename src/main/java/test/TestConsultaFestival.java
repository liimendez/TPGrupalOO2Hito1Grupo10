package test;

import java.util.List;

import datos.UnidadVenta;
import negocio.FestivalABM;


// Consulta las UnidadesVenta asociadas al Festival que su ID sea 1.
// Festival - UnidadVenta: Uno a Muchos.
 
public class TestConsultaFestival {

    public static void main(String[] args) {

        FestivalABM abm = new FestivalABM();

        List<UnidadVenta> unidades = abm.traerUnidades(1L);

        for (UnidadVenta unidad : unidades) {
            System.out.println(unidad);
        }
    }
}
