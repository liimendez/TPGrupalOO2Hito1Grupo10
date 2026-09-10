package negocio;

import java.util.Set;

import dao.PuestoDesarmableDao;
import datos.PuestoDesarmable;

public class PuestoDesarmableABM {

    private static PuestoDesarmableABM instancia = null;
    private PuestoDesarmableDao dao = PuestoDesarmableDao.getInstancia();

    protected PuestoDesarmableABM() {}

    public static PuestoDesarmableABM getInstancia() {

        if (instancia == null) {
            instancia = new PuestoDesarmableABM();
        }

        return instancia;
    }

    public long agregar(PuestoDesarmable puesto) {

        return dao.agregar(puesto);
    }

    public PuestoDesarmable traer(long idPuesto) {

        return dao.traer(idPuesto);
    }

    public Set<PuestoDesarmable> traerTodas() {

        return dao.traerTodas();
    }

    public void actualizar(PuestoDesarmable puesto) {

        dao.actualizar(puesto);
    }

    public void eliminar(long id) {

        PuestoDesarmable p = dao.traer(id);

        if (p != null) {
            dao.eliminar(p);
        }
    }

}