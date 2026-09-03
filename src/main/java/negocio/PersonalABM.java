package negocio;

import dao.PersonalDao;
import datos.Personal;


public class PersonalABM {
	
	private PersonalDao dao = new PersonalDao();
	
	
	public int agregar(Personal personal) {
		
        return dao.agregar(personal);
    }

    public Personal traer(long idPersonal) {
    	
        return dao.traer(idPersonal);
    }
    

  
	
}
