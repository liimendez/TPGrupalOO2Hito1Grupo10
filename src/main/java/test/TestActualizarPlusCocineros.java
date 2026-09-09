package test;

import dao.CocineroDao;
import datos.Cocinero;

public class TestActualizarPlusCocineros {
    public static void main(String[] args) {
        
        CocineroDao dao = CocineroDao.getInstancia();
        
        System.out.println("Actualizando plus de cocineros...");
        
        for (Cocinero c : dao.traerTodas()) {
            // aca llamo el metodo, que llama a otro metodo para calcular el plus
            double sueldoFinal = c.calcularSueldo(); 
            
            // Lo guarda en la bd
            dao.actualizar(c);
            
            System.out.println(c.getEspecialidad() + " | Base: " + c.getSueldoBase() + " | Plus: " + c.getPlusCategoria() + " | Final: " + sueldoFinal);
        }
        
        System.out.println("LISTO! Ya podes correr de nuevo tu TestTraerCocinero");
    }
}