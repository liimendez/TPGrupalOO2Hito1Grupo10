package test; 

import datos.Cajero;
import negocio.CajeroABM;

public class TestTraerCajeroMasRecaudado {
    public static void main(String[] args) {
        CajeroABM abm = CajeroABM.getInstancia();    
        
        try {
            Cajero max = abm.traerCajeroQueMasRecaudo();
            
            if (max != null) {
                double recaudacion = abm.calcularRecaudacion(max.getId());
                System.out.println("----------------------------------");
                System.out.println("Cajero que MAS recaudo:");
                System.out.println("Nombre: " + max.getNombre() + " " + max.getApellido());
                System.out.println("ID: " + max.getId());
                System.out.println("Unidad: " + max.getUnidadAsignada().getNombreComercial() + " | ID: " + max.getUnidadAsignada().getId());
                System.out.println("Festival: " + max.getUnidadAsignada().getFestival().getNombre());
                System.out.println("Recaudacion: $" + recaudacion);
                System.out.println("----------------------------------");
            } else {
                System.out.println("No hay pedidos cargados");
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("========== RANKING PARA VERIFICAR ==========");
        for (Cajero c : abm.traerTodas()) {
            try {
				System.out.println(c.getNombre() + " " + c.getApellido() + " ID:" + c.getId() + " -> $" + abm.calcularRecaudacion(c.getId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        System.out.println("============================================");
        
        
        
        
        
        
        
    }
}