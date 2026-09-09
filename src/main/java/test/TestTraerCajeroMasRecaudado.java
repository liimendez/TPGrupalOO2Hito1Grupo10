package test;

import datos.Cajero;
import negocio.CajeroABM;

public class TestTraerCajeroMasRecaudado {
    public static void main(String[] args) {
        CajeroABM abm = CajeroABM.getInstancia();    
        
        
        //CU 1: BUSCO EL CAJERO QUE MAS RECAUDO Y LO MUESTRO... 
        //TRAIGO TODOS LOS CAJEROS POR CADA CAJERO SUS PEDIDOS 
        //SE CALCULA LA RECAUDACION Y COMPARO HASTA QUEDAR CON EL MAX QUE RECAUDO 
        try {
          // creo un objeto del tipo cajero y luego llamo al metodo q esta en su respectivo abm 
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
        } finally {
            dao.HibernateUtil.getSessionFactory().close();
        }
    }
}