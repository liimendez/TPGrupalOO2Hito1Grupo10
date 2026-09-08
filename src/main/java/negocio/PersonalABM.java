package negocio;

import java.util.List;
import dao.PersonalDao;
import datos.Personal;

public class PersonalABM {
    private PersonalDao dao = new PersonalDao();

    public Personal traer(long id) {
        return dao.traer(id);
    }

    // lo uso para traer todas las personas que existan en la bd 
    public List<Personal> traerTodas() {
        return dao.traerTodas();
    }

    public long agregar(Personal personal) {
        return dao.agregar(personal);
    }

    public void actualizar(Personal personal) {
        dao.actualizar(personal);
  
    }
    
    public void eliminar(long id) {
        Personal p = dao.traer(id); // 1. va a la BD y busca el ID 5
        if(p != null) {             // 2. si existe
            dao.eliminar(p);        // 3. lo borra
        }
    }
    // borra por objeto 
    public void eliminar(Personal personal) {
        dao.eliminar(personal); // directo lo borra
    }
    
}
