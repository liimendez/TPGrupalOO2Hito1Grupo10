package negocio;

import java.time.LocalDate;

import dao.CajeroDao;
import datos.Cajero;
import datos.Cajero.Turno;

public class CajeroABM {
	
	private CajeroDao dao = new CajeroDao();
	
	
	public int agregar(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
	        LocalDate fechaDeIngreso, double sueldoBase, Turno turno) {
		
		Cajero cajero = new Cajero(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase, turno);
		
        return dao.agregar(cajero);
    }

	
    public Cajero traer(long idCajero) {
    	
        return dao.traer(idCajero);
    }
}
