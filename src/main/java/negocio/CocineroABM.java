package negocio;

import java.time.LocalDate;
import java.util.Set;
import dao.CocineroDao;
import datos.Cocinero;

public class CocineroABM {
	
    private static CocineroABM instancia = null;
    private CocineroDao dao = CocineroDao.getInstancia();

    protected CocineroABM() {}

    public static CocineroABM getInstancia() {
        if (instancia == null) {
            instancia = new CocineroABM();
        }
        return instancia;
    }

    public long agregar(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento, LocalDate fechaDeIngreso, double sueldoBase, String especialidad, double plusCategoria) {
        Cocinero cocinero = new Cocinero(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase, especialidad, plusCategoria);
        return dao.agregar(cocinero);
    }

    // Sobrecarga 
    public long agregar(Cocinero cocinero) {
        return dao.agregar(cocinero);
    }
	
    public Cocinero traer(long idCocinero) {
        return dao.traer(idCocinero);
    }

    public Set<Cocinero> traerTodas() {
        return dao.traerTodas();
    }

    public Set<Cocinero> traerTodos() {
        return traerTodas();
    }

    public void actualizar(Cocinero c) {
        dao.actualizar(c);
    }

    public void eliminar(long id) {
        Cocinero c = dao.traer(id);
        if (c != null) {
            dao.eliminar(c);
        }
    }
}