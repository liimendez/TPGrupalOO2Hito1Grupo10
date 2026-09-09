package datos;

import java.time.LocalDate;

public class Cajero extends Personal {
	
	private Turno turno;
	//private double recaudacionTotal; // no deberia ser un atributo de cajero ya que su recaudacion depende de la cantidad de pedidos que 
	//tenga su unidad de venta a la cual fue asignado... 

	public enum Turno {
		MANIANA, NOCHE
	}

	public Cajero() {
	}

	public Cajero(String nombre, String apellido, String dni, LocalDate fechaDeNacimiento,
	        LocalDate fechaDeIngreso, double sueldoBase, Turno turno, UnidadVenta unidadAsignada) {
	    super(nombre, apellido, dni, fechaDeNacimiento, fechaDeIngreso, sueldoBase);
	    this.turno = turno;
	    this.unidadAsignada = unidadAsignada;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	@Override
	public double calcularSueldo() {
		return sueldoBase;
	}

    @Override
    public String toString() {
        return "cajero : "+super.toString()+ 
               ", turno=" + turno + 
               ", unidad=" + (unidadAsignada != null ? unidadAsignada.getId() : "SIN UNIDAD") + "]";
    }
}
