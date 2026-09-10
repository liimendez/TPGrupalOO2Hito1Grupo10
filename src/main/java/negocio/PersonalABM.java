package negocio;

import java.util.Set;

import dao.PersonalDao;
import datos.Personal;

public class PersonalABM {

    private static PersonalABM instancia = null;
    private PersonalDao dao = PersonalDao.getInstancia();

    protected PersonalABM() {}

    public static PersonalABM getInstancia() {

        if (instancia == null) {
            instancia = new PersonalABM();
        }

        return instancia;
    }

    public Personal traer(long id) {

        return dao.traer(id);
    }

    // Trae todas las personas que existen en la bd
    public Set<Personal> traerTodas() {

        return dao.traerTodas();
    }

    public long agregar(Personal personal) {

        return dao.agregar(personal);
    }

    public void actualizar(Personal personal) {

        dao.actualizar(personal);
    }

    public void eliminar(long id) {

        Personal p = dao.traer(id);

        if (p != null) {
            dao.eliminar(p);
        }
    }

    public void eliminar(Personal personal) {

        dao.eliminar(personal);
    }

}
