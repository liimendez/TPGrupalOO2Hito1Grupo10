package negocio;

import java.time.LocalDate;

import dao.CocineroDao;
import datos.Cocinero;

public class CocineroABM {
	
	private CocineroDao dao = new CocineroDao();

	
	public int agregar(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento, LocalDate fechaDeIngreso, double sueldoBase, String especialidad, double plusCategoria) {
		
		Cocinero cocinero = new Cocinero(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase, especialidad, plusCategoria);
		
		return dao.agregar(cocinero);
	}
	
	
    public Cocinero traer(long idCocinero) {
    	
        return dao.traer(idCocinero);
    }
    
    
}
