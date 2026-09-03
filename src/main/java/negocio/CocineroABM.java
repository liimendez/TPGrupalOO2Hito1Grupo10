package negocio;

import dao.CocineroDao;
import datos.Cocinero;

public class CocineroABM {
	
	private CocineroDao dao = new CocineroDao();
	
	
	public int agregar(Cocinero cocinero) {
		
        return dao.agregar(cocinero);
    }

    public Cocinero traer(long idCocinero) {
    	
        return dao.traer(idCocinero);
    }
}
