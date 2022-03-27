package gradoSuperior;

public class Matricula {
	private int nipAlumno;
	private int codigoAsignatura;
	
	public Matricula(int nipAlumno, int codigoAsignatura) {
		this.nipAlumno = nipAlumno;
		this.codigoAsignatura = codigoAsignatura;
	}
	
	public int getNipAlumno() {
		return nipAlumno;
	}
	
	public void setNipAlumno(int nipAlumno) {
		this.nipAlumno = nipAlumno;
	}
	
	public int getCodigoAsignatura() {
		return codigoAsignatura;
	}
	
	public void setCodigoAsignatura(int codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}
	
	@Override
	public String toString() {
		return nipAlumno + " " + codigoAsignatura;
	}
	

}
	