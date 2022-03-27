package gradoSuperior;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Este programa simula un interprete de ordenes para la gestion de 
 * los alumnos, asignaturas y matriculas de un Grado Superior.
 * 
 * @Author: Nerea García Barranco
 */

public class Main {
	/**
	 * Pre: ---
	 * Post: Este metodo main se encarga de obtener los datos necesarios
	 * para el funcionamiento del terminal y de leer los comandos que 
	 * introduzca el operador hasta que decida apagar el programa.
	 */
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();
		ArrayList<Matricula> matriculas = new ArrayList<Matricula>();
		// Obtenemos todos los datos necesarios de los ficheros correspondientes
		obtenerAlumnos(alumnos);
		obtenerAsignaturas(asignaturas);
		obtenerMatriculas(matriculas);
		mostrarMenu();
		// Leemos los comandos que introduce el operador hasta que escriba "fin"
		while(true) {		
			System.out.print("orden>");
			String comando = entrada.nextLine();
			String[] partesComando = comando.split(" ");
			if (partesComando[0].equals("fin")) {
				break;
			} else {
				interpretarComando(partesComando, matriculas, asignaturas, alumnos);
			}		
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo muestra por pantalla el mensaje inicial del terminal
	 */
	public static void mostrarMenu() {
		System.out.println("¡Bienvenido al Sistema de Gestion del Grado Superior!");
		System.out.println("Si no recuerda el funcionamiento introduzca el comando [help]");
		System.out.println("Recuerda que este interprete es sensible a minusculas y mayusculas");
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo se encarga de recibir el comando e interpretarlo de la manera
	 * correcta llamando a los metodos necesarios. Tambien muestra un mensaje de alerta
	 * si el usuario se equivoca a la hora de introducir los comandos y sugiere que
	 * mire la ayuda del terminal.
	 */
	public static void interpretarComando(String [] partesComando, 
			ArrayList<Matricula> matriculas,ArrayList<Asignatura> asignaturas, 
			ArrayList<Alumno> alumnos) {		
		if (partesComando[0].equals("matriculas")) {
			numeroMatriculas(matriculas);
		} else if(partesComando[0].equals("asignaturas")) {
 			comandoAsignaturas(partesComando, matriculas, asignaturas, alumnos);		
		} else if (partesComando[0].equals("alumnos")) {
			comandoAlumnos(partesComando, matriculas, asignaturas, alumnos);
		} else if (partesComando[0].equals("eliminar")) {
			comandoEliminar(partesComando, matriculas, asignaturas, alumnos);	
		} else if (partesComando[0].equals("matricular")) {
			comandoMatricular(partesComando, matriculas, asignaturas, alumnos);	
		} else if (partesComando[0].equals("help")) {
			mostrarAyuda();
		} else if (partesComando[0].equals("actualizar")){
			actualizarFicheroMatriculas(matriculas);
		} else {
			System.out.println("Introduce una orden correcta");
			System.out.println("Escribe el comando [help] para obtener ayuda");
		}
	}
	
	/**
	 * Pre: --- 
	 * Post: Este metodo muestra por pantalla el numero de matriculas
	 * existentes en el momento de la consulta. 
	 */
	private static void numeroMatriculas(ArrayList<Matricula> matriculas) {
		System.out.println("Actualmente hay registradas " 
							+ matriculas.size() + " matriculas");
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para dar una salida correcta a la entrada de un
	 * comando que comience por [asignaturas].
	 */
	private static void comandoAsignaturas(String[] partesComando, ArrayList<Matricula> matriculas,
			ArrayList<Asignatura> asignaturas, ArrayList<Alumno> alumnos) {
		// Si han puesto el comando sin NIP
		if(partesComando.length == 1) {
			System.out.println("Falta el nip del alumno a buscar");
		} else {
			try {
				// Si no han indicado el orden se ordenaran por codigo
				if (partesComando.length == 2) {
					int nip = Integer.parseInt(partesComando[1]);
					asignaturasPorAlumno(nip, "C", matriculas, asignaturas, alumnos);
				} // Si se indica el orden se manda por parametro
				else if (partesComando.length == 3) {
					int nip = Integer.parseInt(partesComando[1]);
					asignaturasPorAlumno(nip, partesComando[2], matriculas, asignaturas, alumnos); 
				} else {
					System.out.println("Introduce una orden correcta");
				}
			} catch (Exception e) {
				System.out.println("Introduce una orden correcta");
			}
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para dar una salida correcta a la entrada de un
	 * comando que comience por [alumnos].
	 */
	private static void comandoAlumnos(String[] partesComando, ArrayList<Matricula> matriculas,
			ArrayList<Asignatura> asignaturas, ArrayList<Alumno> alumnos) {
		if (partesComando.length == 1) {
			System.out.println("Falta el codigo de la asignatura");
		} else {
			try {
				// Si no se indica el orden deseado se ordenaran numericamente
				if (partesComando.length == 2) {
					int codigo = Integer.parseInt(partesComando[1]);
					alumnosPorAsignatura(codigo, "N", matriculas, alumnos, asignaturas);
				} // Si se indica el orden deseado se manda por parametro 
				else if (partesComando.length == 3) {
					int codigo = Integer.parseInt(partesComando[1]);
					alumnosPorAsignatura(codigo, partesComando[2], matriculas, alumnos, asignaturas);
				} else {
					System.out.println("Introduce una orden correcta");
				}
			} catch (Exception e) {
				System.out.println("Introduce una orden correcta");
			}
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para dar una salida correcta a la entrada de un
	 * comando que comience por [eliminar].
	 */
	private static void comandoEliminar(String[] partesComando, ArrayList<Matricula> matriculas,
			ArrayList<Asignatura> asignaturas, ArrayList<Alumno> alumnos) {
		if (partesComando.length == 1) {
			System.out.println("Faltan los argumentos del comando");
		} // Si solo se indica el NIP del alumno 
		else if (partesComando.length == 2) {
			int parametro = Integer.parseInt(partesComando[1]);
			eliminarMatriculasAlumno(parametro, matriculas, asignaturas, alumnos); 				
		} // Si se incluye lista de matriculas concretas 
		else if (partesComando.length > 2) {
			try {
				int parametro = Integer.parseInt(partesComando[1]);
				ArrayList<Integer> codigos = new ArrayList<Integer>();
				/*
				 * Este bucle introduce todos los codigos introducidos
				 * en el comando en un vector para poder ser enviados 
				 * por parametro al metodo necesario.
				 */
				for (int i = 2; i < partesComando.length; i++) {
					codigos.add(Integer.parseInt(partesComando[i]));
				}
				eliminarMatriculasAlumnosConcretas(parametro, codigos, asignaturas, matriculas, alumnos);
			} catch (Exception e) {
				System.out.println("Introduce una orden correcta");
			}
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para dar una salida correcta a la entrada de un
	 * comando que comience por [matricular].
	 */
	private static void comandoMatricular(String[] partesComando, ArrayList<Matricula> matriculas,
			ArrayList<Asignatura> asignaturas, ArrayList<Alumno> alumnos) {
		if (partesComando.length < 3) {
			System.out.println("El comando esta incompleto");
		} else {
			try {
				int parametro = Integer.parseInt(partesComando[1]);
				ArrayList<Integer> codigos = new ArrayList<Integer>();
				/*
				 * Este bucle introduce todos los codigos introducidos
				 * en el comando en un vector para poder ser enviados 
				 * por parametro al metodo necesario.
				 */
				for (int i = 2; i < partesComando.length; i++) {
					codigos.add(Integer.parseInt(partesComando[i]));
				}
				matricular(parametro, codigos, matriculas, alumnos, asignaturas);
			} catch (Exception e) {
				System.out.println("Introduce una orden correcta");
			}
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo muestra por pantalla la ayuda necesaria para
	 * emplear el terminal. 
	 */
	public static void mostrarAyuda() {
		System.out.println("Estos son los comandos que puedes utilizar:");
		System.out.println("¡Cuidado! El terminal es sensible a mayusculas y minusculas");
		System.out.println("      1. matriculas");
		System.out.println("          Informa del numero total de matriculas existentes");
		System.out.println("      2. asignaturas [nip] [A | C]");
		System.out.println("          Informa de las asignaturas de un alumno en concreto mediante su NIP");
		System.out.println("                - A: las asignaturas aparecen ordenadas alfabeticamente");
		System.out.println("                - C: las asignaturas aparecen ordenadas por codigo");
		System.out.println("      3. alumnos [codigo_asignatura] [A | N]");
		System.out.println("          Lista los alumnos matriculados en la asignatura que tiene dicho codigo");
		System.out.println("                - A: los alumnos aparecen ordenados alfabeticamente");
		System.out.println("                - N: los alumnos aparecen ordenados por su NIP");
		System.out.println("      4. eliminar");
		System.out.println("           4.1. eliminar [nip] = elimina todas las asignaturas de dicho alumno");
		System.out.println("           4.2. eliminar [nip] [asignatura/s] = elimina una lista de asignaturas concretas");
		System.out.println("                 (las asignaturas se introducen separadas por espacios)");
		System.out.println("      5. matricular [nip] [asignatura/s]");
		System.out.println("           Matricula al alumno con dicho nip en una o varias asignaturas");
		System.out.println("           (las asignaturas se introducen separadas por espacios)");
		System.out.println("      6. actualizar");
		System.out.println("           Actualiza los cambios realizados en las matriculas en el fichero");
		System.out.println("      7. fin");
		System.out.println("           Apaga el programa");		
	}
	 
	/**
	 * Pre: ---
	 * Post: Este metodo recibe un vector de objetos de tipo Asignatura y lo 
	 * rellena a partir de los datos existentes en un fichero .txt
	 */
	public static ArrayList<Asignatura> obtenerAsignaturas
		(ArrayList<Asignatura> asignaturas) {
		String ruta = "C:\\Users\\Nerea\\Desktop\\asignaturas.txt";
		File fichero = new File (ruta);
		Scanner scanner;
		try {
			scanner = new Scanner(fichero);
			// Mientras el fichero tenga lineas
			while (scanner.hasNextLine()) {
				String asignatura = scanner.nextLine();		
				String[] datos = asignatura.split(" ");
				// Almacenamos los datos que nos interesan
				int codigo = Integer.parseInt(datos[0]);
				String nombreAsignatura = "";
				// Concatenamos el nombre de las asignaturas
				for (int j = 4; j < datos.length; j++) {
					nombreAsignatura = nombreAsignatura + datos[j] + " ";
				}
				Asignatura a = new Asignatura(codigo,nombreAsignatura);
				asignaturas.add(a);			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return asignaturas;
	}

	/**
	 * Pre: ---
	 * Post: Este metodo recibe un vector de objetos de tipo matricula y lo 
	 * rellena mediante la lectura de un archivo binario (.dat)
	 */
	public static ArrayList<Matricula> obtenerMatriculas
		(ArrayList<Matricula> matriculas) {
		try {
			String nombre = "C:\\Users\\Nerea\\Desktop\\matriculas.dat";
			DataInputStream f = new DataInputStream(new FileInputStream(nombre));
			try {
				while(true) {
					// Cada matricula se compone de un nip y un codigo de asignatura
					int nipAlumno = f.readInt();
					int codigoAsignatura = f.readInt();
					Matricula matricula = new Matricula (nipAlumno, codigoAsignatura);
					matriculas.add(matricula);
				}
			} catch(EOFException e) {}
			f.close();
		} catch(FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en operacion de E/S con el fichero");
		}
		return matriculas;
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo recibe un vector de objetos de tipo alumno y los rellena
	 * mediante la lectura de un archivo de tipo .csv
	 */
	public static ArrayList<Alumno> obtenerAlumnos(ArrayList<Alumno> alumnos) {
		String ruta = "C:\\Users\\Nerea\\Desktop\\alumnos.csv";
		File fichero = new File (ruta);
		Scanner scanner;
		try {
			scanner = new Scanner(fichero);
			int contador = 0;
			while (scanner.hasNextLine()) {
				String alumno = scanner.nextLine();
				// Evitamos la cabecera del .csv
				if (contador != 0) {
					String[] datos = alumno.split(";");
					// Almacenamos los datos en un objeto de tipo Alumno
					int nip = Integer.parseInt(datos[0]);
					String apellidos = datos[1];
					String nombre = datos[2];
					Alumno a = new Alumno(nip, apellidos, nombre);
					alumnos.add(a);
				}
				contador ++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return alumnos;
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para comprobar si el alumno cuyo [nip] se 
	 * recibe por parametro se encuentra en un vector de objetos de tipo
	 * Alumno. 
	 */
	private static boolean isNipExiste(ArrayList<Alumno> alumnos, int nip) {
		boolean existe = false;
		for (int i = 0; i < alumnos.size(); i++) {
			if (alumnos.get(i).getNip() == nip) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			System.out.println("Este codigo no pertenece a ningun alumno del curso");
		}
		return existe;	
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para comprobar si la asignatura cuyo [codigo]
	 * se recibe por parametro se encuentra en un vector de objetos de tipo 
	 * Asignatura.
	 */
	private static boolean isAsignaturaExiste(ArrayList<Asignatura> asignaturas, int codigo) {
		boolean existe = false;
		for (int i = 0; i < asignaturas.size(); i++) {
			if (asignaturas.get(i).getCodigoAsignatura() == codigo) {
				existe = true;
				break;
			}
		}
		return existe;	
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo devuelve [true] si en el vector de matriculas hay
	 * alguna guardada.
	 */
	private static boolean isMatriculasExisten(ArrayList<Matricula> matriculas) {
		boolean existe = false;
		if(matriculas.size() > 0) {
			existe = true;
		}
		if (!existe) {
			System.out.println("No hay matriculas guardadas, no se puede realizar la operacion");
		}
		return existe;
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo comprueba si un alumno esta matriculado en alguna
	 * matricula y devuelve [true] si lo esta.
	 */
	private static boolean isAlumnoTieneMatriculas(ArrayList<Matricula> matriculas,
			int nipAlumno) {
		boolean existe = false;
		for (int i = 0; i < matriculas.size(); i++) {
			if(matriculas.get(i).getNipAlumno() == nipAlumno) {
				existe = true;
			}
		}
		if (!existe) {
			System.out.println("Este alumno no esta matriculado en ninguna "
					+ "asignatura, no se puede realizar la operacion");
		}
		return existe;
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo comprueba si el alumno que se recibe por parametro
	 * existe. En ese caso devuelve las asignaturas en las que esta matriculado
	 * (en orden alfabetico si el comando incluye el argumento A o
	 * en orden numerico si incluye el argumento C o ninguno).
	 */
	private static void asignaturasPorAlumno(int nip, String orden, 
			ArrayList<Matricula> matriculas, ArrayList<Asignatura> asignaturas, 
			ArrayList<Alumno> alumnos) {
		// Primero comprobamos que el alumno este dado de alta en el curso
		if (!isNipExiste(alumnos, nip)) {
			return;
		} 
		// Comprobamos que el criterio de ordenacion sea correcto
		if (!(orden.equals("A") || orden.equals("C"))) {
			System.out.println("Introduce un argumento valido [A | C]");
			return;
		}
		// Ordenamos las asignaturas por el orden elegido por el operador
		if (orden.equals("A")) {
			Collections.sort(asignaturas);
		} else if (orden.equals("C")) {
			ordenarAsignaturasNumericamente(asignaturas);
		}
		// Mostramos las asignaturas de ese alumno
		mostrarAsignaturas(nip, matriculas, asignaturas);
	}
	
	/**
	 * Pre: ---
	 * Post:Este metodo muestra por pantalla el codigo y el nombre las asignaturas
	 * de un alumno en concreto recibiendo su NIP como parametro.
	 */
	private static void mostrarAsignaturas(int nip, ArrayList<Matricula> matriculas,
			ArrayList<Asignatura> asignaturas) {
		// Primero comprobamos si tiene matriculas
		if (isAlumnoTieneMatriculas(matriculas, nip)) {
			// Recorremos las asignaturas
			for (int i = 0; i < asignaturas.size(); i++) {
				// Recorremos las matriculas
				for (int j = 0; j < matriculas.size(); j++) {
					// Si la asignatura esta comprobamos si esta vinculada al nip del alumno
					if (asignaturas.get(i).getCodigoAsignatura() == matriculas.get(j).getCodigoAsignatura()
							&& matriculas.get(j).getNipAlumno() == nip) {
						// Si lo esta mostramos por pantalla la asignatura
						System.out.println(asignaturas.get(i).toString());
					}
				}
			}
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo ordena un vector de objetos de tipo Asignatura
	 * por su codigo de asignatura (de menor a mayor).
	 */
	private static ArrayList <Asignatura> ordenarAsignaturasNumericamente 
	(ArrayList <Asignatura> asignaturas) {
		for (int i = 0; i < asignaturas.size(); i++) {
			Asignatura auxiliar = new Asignatura();
			for (int j = 0; j < asignaturas.size(); j++) {
				if (asignaturas.get(i).getCodigoAsignatura() 
						< asignaturas.get(j).getCodigoAsignatura()) {
					auxiliar = asignaturas.get(i);
					asignaturas.set(i, asignaturas.get(j));
					asignaturas.set(j, auxiliar);
				}
			}
		}
		return asignaturas;
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo comprueba si la asignatura que se recibe por parametro
	 * existe. En ese caso devuelve los alumnos que estan matriculados en dicha
	 * asignatura (en orden alfabetico si el comando incluye el argumento A o
	 * en orden numerico si incluye el argumento N o ninguno).
	 */
	private static void alumnosPorAsignatura(int codigoAsignatura, String orden, 
			ArrayList<Matricula> matriculas, ArrayList<Alumno> alumnos, 
			ArrayList<Asignatura> asignaturas) {
		// Primero comprobamos que ese codigo pertenezca a una asignatura del plan
		if (!isAsignaturaExiste(asignaturas, codigoAsignatura)) {
			System.out.println("Este codigo no pertenece a ninguna asignatura del plan");
			return;
		} 
		// Comprobamos que el criterio de ordenacion sea correcto
		if (!(orden.equals("A") || orden.equals("N"))) {
			System.out.println("Introduce un argumento valido [A | N]");
			return;
		} 
		// Ordenamos los alumnos por el orden elegido por el operador
		if (orden.equals("A")) {
			// Ordenamos las alumnos alfabeticamente por apellido
			Collections.sort(alumnos);
		} else if (orden.equals("C")) {
			ordenarAlumnosNumericamente(alumnos);
		}
		mostrarAlumnos(codigoAsignatura, matriculas, alumnos);	
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo muestra los alumnos que estan matriculados en una 
	 * asignatura en concreto cuyo codigo se recibe por parametro.
	 */
	private static void mostrarAlumnos(int codigoAsignatura, ArrayList<Matricula> matriculas,
			ArrayList<Alumno> alumnos) {
		boolean existe = false;
		// Recorremos los alumnos
		for (int i = 0; i < alumnos.size(); i++) {
			// Recorremos las matriculas buscando coincidencias
			for (int j = 0; j < matriculas.size(); j++) {
				/*
				 * Si el codigo de la asignatura recibido por parametro es el mismo
				 * que el de una matricula se muestra por pantalla.
				 */
				if (matriculas.get(j).getCodigoAsignatura() == codigoAsignatura 
					&& alumnos.get(i).getNip() == matriculas.get(j).getNipAlumno()) {
					System.out.println(alumnos.get(i).toString());
					existe = true;
				}
			}
		}
		// Si no ha habido ningun resultado se muestra este mensaje 
		if (!existe) {
			System.out.println("Esta asignatura no tiene alumnos matriculados");
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo ordena un vector de objetos de tipo Alumno por su NIP
	 * (de menor a mayor)
	 */
	private static ArrayList <Alumno> ordenarAlumnosNumericamente (ArrayList <Alumno> alumnos) {
		for (int i = 0; i < alumnos.size(); i++) {
			Alumno auxiliar = new Alumno();
			for (int j = 0; j < alumnos.size(); j++) {
				if (alumnos.get(i).getNip() < alumnos.get(j).getNip()) {
					auxiliar = alumnos.get(i);
					alumnos.set(i, alumnos.get(j));
					alumnos.set(j, auxiliar);
				}
			}
		}
		return alumnos;
	}
		
	/**
	 * Pre: ---
	 * Post: Este metodo borra todas las matriculas de un alumno cuyo identificador
	 * se recibe por parametro. 
	 */
	private static void eliminarMatriculasAlumno(int identificador, 
			ArrayList<Matricula> matriculas,ArrayList<Asignatura> asignaturas, 
			ArrayList<Alumno> alumnos) {
		// Comprobamos si existe alguna matricula
		if (!isMatriculasExisten(matriculas)) {
			return;
		}
		// Si el codigo no pertenece a ningun alumno se alerta
		if (!isNipExiste(alumnos, identificador)) {
			return;
		}
		// Si el codigo pertenece a un alumno se busca si tiene matriculas	
		if (isAlumnoTieneMatriculas(matriculas, identificador)) {
			System.out.println("El alumno con codigo " + identificador 
				+ " ha sido eliminado de las siguientes asignaturas:");
			for (int i = 0; i < matriculas.size(); i++) {
				if (matriculas.get(i).getNipAlumno() == identificador) {
					// Este bucle sirve para obtener el nombre de la asignatura
					for (int j = 0; j < asignaturas.size(); j++) {
						if (asignaturas.get(j).getCodigoAsignatura() 
								== matriculas.get(i).getCodigoAsignatura()) {
							// Primero escribimos que matricula vamos a borrar
							System.out.println(asignaturas.get(j).toString());
							// Eliminamos la matricula
							matriculas.remove(i);
							/*
							 * Restamos 1 al iterador de las matriculas porque
							 * al borrarse una se desplazan todas una posicion
							 * hacia la izquierda.
							 */
							i--;
							break;
						}
					}
				}
			}
		} 
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo borra varias matriculas de un alumno en concreto
	 * cuyo identificador recibe por parametro.
	 */
	public static void eliminarMatriculasAlumnosConcretas (int identificador, 
			ArrayList<Integer> codigos, ArrayList<Asignatura> asignaturas, 
			ArrayList<Matricula> matriculas, ArrayList<Alumno> alumnos) {
		boolean matriculado = false;
		// Comprobamos si existe alguna matricula
		if (!isMatriculasExisten(matriculas)) {
			return;
		}
		// Comprobamos si existe el alumno
		if(!isNipExiste(alumnos, identificador)) {
			return;
		}
		// Comprobamos si tiene alguna matricula
		if(isAlumnoTieneMatriculas(matriculas, identificador)) {
			// Buscamos si alguna matricula coincide con alguno de los codigos
			for (int i = 0; i < codigos.size(); i++) {
				for (int j = 0; j < matriculas.size(); j++) {	
					// Si coinciden el nip y el codigo de la asignatura
					if (matriculas.get(j).getNipAlumno() == identificador) {
						if (matriculas.get(j).getCodigoAsignatura() == codigos.get(i)) {
							matriculado = true;
							// Conseguimos el nombre de la asignatura
							for (int k = 0; k < asignaturas.size(); k++) {
								if (asignaturas.get(k).getCodigoAsignatura() 
										== matriculas.get(j).getCodigoAsignatura()) {
									// Mostramos que se ha borrado dicha asignatura
									System.out.print("Ha sido eliminado correctamente de: ");
									System.out.println(asignaturas.get(k).toString());
									// La borramos
									matriculas.remove(j);
									break;
								}
							}			
						}
					}
				}
				// Si no esta matriculado en esa asignatura
				if(!matriculado) {
					if (isAsignaturaExiste(asignaturas, codigos.get(i))) {
						System.out.println("El alumno no esta matriculado en la asignatura " 
								+ codigos.get(i));
					} else {
						System.out.println("La asignatura " + codigos.get(i) +" no existe");
					}
				}
				matriculado = false;
			}		
		}
	}
	
	/**
	 * Pre: ---
	 * Post: Este metodo sirve para matricular a un alumno en unas asignaturas
	 * concretas. Se comprueba que no este matriculado previamente y si el codigo
	 * de la asignatura pertenece al plan de estudios antes de realizarlo.
	 * Una vez realizada la operacion muestra un mensaje con el numero de 
	 * matriculas realizadas con exito.
	 */
	public static void matricular(int identificador, ArrayList<Integer> codigos,
			ArrayList<Matricula> matriculas, ArrayList<Alumno> alumnos,
			ArrayList<Asignatura> asignaturas) {
		// Buscamos si ese alumno esta registrado en la titulacion
		if (!isNipExiste(alumnos, identificador)) {
			return;
		} 
		boolean existeMatricula = false;
		int contadorMatriculas = 0;
		for (int i = 0; i < codigos.size(); i++) {
			if (isAsignaturaExiste (asignaturas, codigos.get(i))) {
				for (int j = 0; j < asignaturas.size(); j++) {
					if (codigos.get(i) == asignaturas.get(j).getCodigoAsignatura()) {
						for (int k = 0; k < matriculas.size(); k++) {
							// Si coinciden tanto el nip como el codigo ya esta matriculado
							if(matriculas.get(k).getCodigoAsignatura() 
									== asignaturas.get(j).getCodigoAsignatura() &&
									matriculas.get(k).getNipAlumno() == identificador) {
								existeMatricula = true;
								System.out.println("Este alumno ya esta matriculado"
										+ " en la asignatura " + codigos.get(i));
								break;
							} 
						}
					}
				}
				// Si no existe procedemos a registrar la matricula
				if (!existeMatricula) {
					contadorMatriculas++;
					Matricula matricula = new Matricula(identificador, codigos.get(i));
					matriculas.add(matricula);
					}
				} else {
				System.out.println("La asignatura con el codigo " + codigos.get(i)
						+ " no existe en este plan de estudios");
			}	
		}
		System.out.println("El alumno con NIP " + identificador + " ha sido matriculado"
				+ " en " + contadorMatriculas + " asignaturas");
	}

	/**
	 * Pre: ---
	 * Post: Este metodo se encarga de actualizar el fichero binario en el 
	 * que se almacenan las matriculas. Esta compuesto por tuplas conformadas
	 * por el NIP de un alumno y el codigo de una asignatura.
	 */
	public static void actualizarFicheroMatriculas(ArrayList <Matricula> matriculas) {
   	try {
	   	String nombre = "C:\\Users\\Nerea\\Desktop\\matriculas.dat";
	   	DataOutputStream f = new DataOutputStream(new FileOutputStream(nombre));
		// Se escriben los datos de cada una de las matriculas en el fichero [f]
		for (int i = 0; i < matriculas.size(); i++) {
			try {
				f.writeInt(matriculas.get(i).getNipAlumno());
				f.writeInt(matriculas.get(i).getCodigoAsignatura());
			} catch (IOException e) {
				System.out.println("Se ha actualizado el fichero");
			}	
		}
		f.close();
		} catch(FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en operacion de E/S con el fichero ");
		}
	}
}
