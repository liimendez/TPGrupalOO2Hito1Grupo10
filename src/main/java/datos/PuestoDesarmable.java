package datos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "puesto_desarmable")
@PrimaryKeyJoinColumn(name = "id")
public class PuestoDesarmable extends UnidadVenta {

	@Column(name = "cantidad_carpas", nullable = false)
	private int cantidadCarpas;

	@Column(name = "tiempo_montaje_min", nullable = false)
	private int tiempoMontajeMin;

	protected PuestoDesarmable() {
		// Constructor vacio requerido por Hibernate
	}

	public PuestoDesarmable(String nombreComercial, double superficieM2, String codigoUnico, Festival festival,
			Personal responsable, int cantidadCarpas, int tiempoMontajeMin) {
		super(nombreComercial, superficieM2, codigoUnico, festival, responsable);
		this.cantidadCarpas = cantidadCarpas;
		this.tiempoMontajeMin = tiempoMontajeMin;
	}

	public int getCantidadCarpas() {
		return cantidadCarpas;
	}

	public void setCantidadCarpas(int cantidadCarpas) {
		this.cantidadCarpas = cantidadCarpas;
	}

	public int getTiempoMontajeMin() {
		return tiempoMontajeMin;
	}

	public void setTiempoMontajeMin(int tiempoMontajeMin) {
		this.tiempoMontajeMin = tiempoMontajeMin;
	}

	@Override
	public String toString() {
		return "PuestoDesarmable [cantidadCarpas=" + cantidadCarpas + ", tiempoMontajeMin=" + tiempoMontajeMin + "]";
	}
}
