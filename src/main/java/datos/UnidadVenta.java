package datos;

//import java.util.ArrayList;
//import java.util.List;

import java.util.HashSet;
import java.util.Set;

public abstract class UnidadVenta {
	
	protected Long id;
	protected String nombreComercial;
	protected double superficieM2;
	protected String codigoUnico;
	protected Festival festival;
	protected Personal responsable;
	//protected List<Personal> staff = new ArrayList<>();
	//protected List<Plato> platosOfrecidos = new ArrayList<>();
	protected Set<Personal> staff = new HashSet<>();
	protected Set<Plato> platosOfrecidos = new HashSet<>();

	protected UnidadVenta() {
		// Constructor vacio requerido por Hibernate
	}


	public UnidadVenta(String nombreComercial, double superficieM2, String codigoUnico, Festival festival,
	        Personal responsable) {
	    this.nombreComercial = nombreComercial;
	    this.superficieM2 = superficieM2;
	    setCodigoUnico(codigoUnico);
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

	public void setId(Long id) {
		this.id = id;
	}

   /*
	public void setStaff(List<Personal> staff) {
		this.staff = staff;
	}


	public void setPlatosOfrecidos(List<Plato> platosOfrecidos) {
		this.platosOfrecidos = platosOfrecidos;
	} */
	
	public Set<Personal> getStaff() {
	    return staff;
	}

	public void setStaff(Set<Personal> staff) {
	    this.staff = staff;
	}

	public void setResponsable(Personal responsable) {
		this.responsable = responsable;
	}
	
	/*
	public List<Personal> getStaff() {
		return staff;
	}

	public List<Plato> getPlatosOfrecidos() {
		return platosOfrecidos;
	} */
	
	public Set<Plato> getPlatosOfrecidos() {
	    return platosOfrecidos;
	}

	public void setPlatosOfrecidos(Set<Plato> platosOfrecidos) {
	    this.platosOfrecidos = platosOfrecidos;
	}

	@Override
	public String toString() {
		return "UnidadVenta [nombreComercial=" + nombreComercial + ", codigoUnico=" + codigoUnico + "]";
	}
}
