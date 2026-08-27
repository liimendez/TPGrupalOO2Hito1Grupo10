package datos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "food_truck")
@PrimaryKeyJoinColumn(name = "id")
public class FoodTruck extends UnidadVenta {

	@Column(name = "patente", nullable = false, unique = true)
	private String patente;

	@Column(name = "requiere_conexion_electrica", nullable = false)
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
		return "FoodTruck [patente=" + patente + ", requiereConexionElectrica=" + requiereConexionElectrica + "]";
	}
}
