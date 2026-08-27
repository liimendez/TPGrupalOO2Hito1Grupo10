package datos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "unidad_venta")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UnidadVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "nombre_comercial", nullable = false)
	protected String nombreComercial;

	@Column(name = "superficie_m2", nullable = false)
	protected double superficieM2;

	@Column(name = "codigo_unico", nullable = false, unique = true, length = 10)
	protected String codigoUnico;

	@ManyToOne
	@JoinColumn(name = "festival_id")
	protected Festival festival;

	@ManyToOne
	@JoinColumn(name = "responsable_id")
	protected Personal responsable;

	@OneToMany(mappedBy = "unidadAsignada", cascade = CascadeType.ALL)
	protected List<Personal> staff = new ArrayList<>();

	@OneToMany(mappedBy = "unidadVenta", cascade = CascadeType.ALL)
	protected List<Plato> platosOfrecidos = new ArrayList<>();

	protected UnidadVenta() {
		// Constructor vacio requerido por Hibernate
	}

	public UnidadVenta(String nombreComercial, double superficieM2, String codigoUnico, Festival festival,
			Personal responsable) {
		if (!validarCodigo(codigoUnico)) {
			throw new IllegalArgumentException("El codigo unico debe tener exactamente 10 caracteres alfanumericos");
		}
		this.nombreComercial = nombreComercial;
		this.superficieM2 = superficieM2;
		this.codigoUnico = codigoUnico;
		this.festival = festival;
		this.responsable = responsable;
	}

	/**
	 * Logica de validacion propia del codigo unico: exactamente 10 caracteres
	 * alfanumericos (letras y numeros, sin espacios ni simbolos).
	 */
	public static boolean validarCodigo(String codigo) {
		return codigo != null && codigo.matches("^[a-zA-Z0-9]{10}$");
	}

	public void asignarStaff(Personal empleado) {
		staff.add(empleado);
		empleado.setUnidadAsignada(this);
	}

	public void agregarPlato(Plato plato) {
		platosOfrecidos.add(plato);
		plato.setUnidadVenta(this);
	}

	public Long getId() {
		return id;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public double getSuperficieM2() {
		return superficieM2;
	}

	public void setSuperficieM2(double superficieM2) {
		this.superficieM2 = superficieM2;
	}

	public String getCodigoUnico() {
		return codigoUnico;
	}

	public void setCodigoUnico(String codigoUnico) {
		if (!validarCodigo(codigoUnico)) {
			throw new IllegalArgumentException("El codigo unico debe tener exactamente 10 caracteres alfanumericos");
		}
		this.codigoUnico = codigoUnico;
	}

	public Festival getFestival() {
		return festival;
	}

	public void setFestival(Festival festival) {
		this.festival = festival;
	}

	public Personal getResponsable() {
		return responsable;
	}

	public void setResponsable(Personal responsable) {
		this.responsable = responsable;
	}

	public List<Personal> getStaff() {
		return staff;
	}

	public List<Plato> getPlatosOfrecidos() {
		return platosOfrecidos;
	}

	@Override
	public String toString() {
		return "UnidadVenta [nombreComercial=" + nombreComercial + ", codigoUnico=" + codigoUnico + "]";
	}
}
