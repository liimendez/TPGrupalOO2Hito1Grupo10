package negocio;

import dao.PuestoDesarmableDao;
import datos.PuestoDesarmable;

public class PuestoDesarmableABM {

    PuestoDesarmableDao dao = new PuestoDesarmableDao();

    public int agregar(PuestoDesarmable puesto) {
        return dao.agregar(puesto);
    }

    public PuestoDesarmable traer(long idPuesto) {
        return dao.traer(idPuesto);
    }
}
