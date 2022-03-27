package gradoSuperior;

public class Asignatura implements Comparable <Asignatura>{
	private int codigoAsignatura;
	private String nombreAsignatura;
	
	public Asignatura (int codigoAsignatura, String nombreAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
		this.nombreAsignatura = nombreAsignatura;
	}
	
	public Asignatura (int codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
		nombreAsignatura = "";
	}
	
	public Asignatura () {
		codigoAsignatura = 0;
		nombreAsignatura = "";
	}
	
	public int getCodigoAsignatura() {
		return codigoAsignatura;
	}
	
	public void setCodigoAsignatura(int codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}
	
	public String getNombreAsignatura() {
		return nombreAsignatura;
	}
	
	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}
	
	@Override
	public String toString () {
		return codigoAsignatura + " " + nombreAsignatura;
	}

	@Override
	public int compareTo(Asignatura o) {
		if (this.nombreAsignatura.compareTo(o.nombreAsignatura) >= 1) {
			return 1;
		} else if (this.nombreAsignatura.compareTo(o.nombreAsignatura) <= 1) {
			return -1;
		} else return 0;
		
	}
}
