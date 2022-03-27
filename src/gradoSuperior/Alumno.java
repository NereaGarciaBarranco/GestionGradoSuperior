package gradoSuperior;

public class Alumno implements Comparable <Alumno>{
	private int nip;
	private String apellido;
	private String nombre;
	
	public Alumno (int nip, String apellido, String nombre) {
		this.nip = nip;
		this.apellido = apellido;
		this.nombre = nombre;
	}
	
	public Alumno() {
		nip = 0;
		apellido = "";
		nombre = "";
	}

	public int getNip() {
		return nip;
	}
	
	public void setNip(int nip) {
		this.nip = nip;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nip + " " + apellido + ", " + nombre;
	}

	@Override
	public int compareTo(Alumno o) {
		if (this.apellido.compareTo(o.apellido) >= 1) {
			return 1;
		} else if (this.apellido.compareTo(o.apellido) <= 1) {
			return -1;
		} else {
			return 0;
		}		
	}
}
