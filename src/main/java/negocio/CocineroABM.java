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

    // --------------------------------------------------
    // ALTA
    // --------------------------------------------------

    public long agregar(String nombre, String apellido, String dni, 
                        LocalDate fechaDeNacimiento, LocalDate fechaDeIngreso, 
                        double sueldoBase, String especialidad, double plusCategoria) throws Exception {
        
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("Nombre obligatorio");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("Apellido obligatorio");
        }

        if (dni == null || dni.length() < 7) {
            throw new Exception("DNI invalido");
        }

        if (fechaDeNacimiento == null || fechaDeNacimiento.isAfter(LocalDate.now())) {
            throw new Exception("Fecha nacimiento invalida");
        }

        if (fechaDeIngreso == null || fechaDeIngreso.isAfter(LocalDate.now())) {
            throw new Exception("Fecha ingreso invalida");
        }

        if (sueldoBase <= 0) {
            throw new Exception("Sueldo base debe ser > 0");
        }

        if (especialidad == null || especialidad.isEmpty()) {
            throw new Exception("Especialidad obligatoria");
        }
        
        Cocinero cocinero = new Cocinero(nombre, apellido, dni, fechaDeNacimiento, 
                                        fechaDeIngreso, sueldoBase, especialidad, plusCategoria);

        return dao.agregar(cocinero);
    }

    public long agregar(Cocinero cocinero) throws Exception {

        if (cocinero == null) {
            throw new Exception("Cocinero nulo");
        }

        return dao.agregar(cocinero);
    }

    // --------------------------------------------------
    // TRAER
    // --------------------------------------------------

    public Cocinero traer(long idCocinero) throws Exception {

        if (idCocinero <= 0) {
            throw new Exception("Id invalido");
        }

        Cocinero c = dao.traer(idCocinero);

        if (c == null) {
            throw new Exception("No existe cocinero con id: " + idCocinero);
        }

        return c;
    }

    public Set<Cocinero> traerTodas() {
        return dao.traerTodas();
    }

    // --------------------------------------------------
    // MODIFICACION Y BAJA
    // --------------------------------------------------

    public void actualizar(Cocinero c) throws Exception {

        if (c == null) {
            throw new Exception("Cocinero nulo para actualizar");
        }

        dao.actualizar(c);
    }

    public void eliminar(long id) throws Exception {

        Cocinero c = dao.traer(id);

        if (c == null) {
            throw new Exception("No existe cocinero con id: " + id + " para eliminar");
        }

        dao.eliminar(c);
    }

}