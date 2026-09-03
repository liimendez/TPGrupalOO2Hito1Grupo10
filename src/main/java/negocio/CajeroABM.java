package negocio;

import dao.CajeroDao;
import datos.Cajero;

public class CajeroABM {
	
	private CajeroDao dao = new CajeroDao();
	
	
	public int agregar(Cajero cajero) {
		
        return dao.agregar(cajero);
    }

	
    public Cajero traer(long idCajero) {
    	
        return dao.traer(idCajero);
    }
}
