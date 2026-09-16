package datos;



import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public abstract class UnidadVenta {
	
	protected Long id;
	protected String nombreComercial;
	protected double superficieM2;
	protected String codigoUnico;
	protected Festival festival;
	protected Personal responsable;

	protected Set<Pedido> pedidos = new HashSet<>();
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
// aca se valida que el codigo sea de 10 digitos y que sea alfanumerico 
	public static boolean validarCodigo(String codigo) {
		return codigo != null && codigo.matches("^[a-zA-Z0-9]{10}$");
	}
//aca se puede asignar algun empleado ya sea cajero o cocinero, pero el objeto tiene que estar creado... 
	public void asignarStaff(Personal empleado) {
		staff.add(empleado);
		empleado.setUnidadAsignada(this);
	}
// aca se agrega el plato a la unidad de venta... 
	public void agregarPlato(Plato plato) {
		platosOfrecidos.add(plato); // lo agrego a la lista 
		plato.setUnidadVenta(this); //y aca que es de tal unidad... 
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

	public Set<Pedido> getPedidos() {
		return pedidos;
	}


	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
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

	public Set<Personal> getStaff() {
	    return staff;
	}

	public void setStaff(Set<Personal> staff) {
	    this.staff = staff;
	}

	public void setResponsable(Personal responsable) {
		this.responsable = responsable;
	}
	
	
	public Set<Plato> getPlatosOfrecidos() {
	    return platosOfrecidos;
	}

	public void setPlatosOfrecidos(Set<Plato> platosOfrecidos) {
	    this.platosOfrecidos = platosOfrecidos;
	}

	@Override
	public String toString() {
	    return "ID: " + id
	            + " | Nombre: " + nombreComercial
	            + " | Código: " + codigoUnico
	            + " | Superficie: " + superficieM2 + " m²"
	            + " | Festival: " + festival.getId();
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UnidadVenta other = (UnidadVenta) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public String mostrarDetalleConPlatos() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("=== ").append(nombreComercial).append(" ===\n");
	    sb.append("Codigo: ").append(codigoUnico).append("\n");
	    sb.append("Superficie: ").append(superficieM2).append(" m2\n");
	    sb.append("Festival: ").append(festival != null ? festival.getNombre() : "N/A").append("\n");

	    try {
	        sb.append("------ PLATOS (").append(platosOfrecidos.size()).append(") ------\n");
	        for (Plato p : platosOfrecidos) {
	            sb.append("  - ").append(p.getNombre()).append(" $").append(p.getPrecioVenta()).append("\n");
	        }
	    } catch (org.hibernate.LazyInitializationException e) {
	        sb.append("------ PLATOS: No inicializados (usar fetch join) ------\n");
	    }
	    return sb.toString();
	}
}
