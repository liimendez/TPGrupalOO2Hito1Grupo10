 package datos;


public class FoodTruck extends UnidadVenta {

	private String patente;
	private boolean requiereConexionElectrica;

	protected FoodTruck() {
		// Constructor vacio requerido por Hibernate
	}

	public FoodTruck(String nombreComercial, double superficieM2, String codigoUnico, Festival festival,
			Personal responsable, String patente, boolean requiereConexionElectrica) {
		super(nombreComercial, superficieM2, codigoUnico, festival, responsable);
		this.patente = patente;
		this.requiereConexionElectrica = requiereConexionElectrica;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public boolean isRequiereConexionElectrica() {
		return requiereConexionElectrica;
	}

	public void setRequiereConexionElectrica(boolean requiereConexionElectrica) {
		this.requiereConexionElectrica = requiereConexionElectrica;
	}

	@Override
	public String toString() {
	    return super.toString() + "FoodTruck [patente=" + patente
	            + ", requiereConexionElectrica=" + requiereConexionElectrica + "]";
	}
}
