package CajeroMySQL;
import java.util.Scanner;

public class funciones {
	static Scanner input = new Scanner(System.in);
	
	public static String recibirOpcion2() {
		String decision;
		do {
			System.out.println("Ingresar opci�n");
			System.out.println("(a)Retirar dinero");
			System.out.println("(b)Depositar dinero");
			System.out.println("(c)Consultar saldo actual");
			System.out.println("(d)Transferencia de dinero");
			System.out.println("(e)Salir");
			System.out.println("******************************");
			decision = input.next();
			decision = decision.toLowerCase();
			if (!(decision.equals("a") || decision.equals("b") || decision.equals("c") || decision.equals("d") || decision.equals("e"))) {
				System.out.println("Error en opci�n ingresada. Volver a elegir.");
			}
		} while(!(decision.equals("a") || decision.equals("b") || decision.equals("c") || decision.equals("d") || decision.equals("e")));
		return decision;
	}
	
	public static String recibirOpcion1() {
		String decision;
		do {
			System.out.println("Ingresar opci�n");
			System.out.println("(a)Iniciar sesi�n");
			System.out.println("(b)Registrar nuevo usuario");
			System.out.println("(c)Salir");
			System.out.println("******************************");
			decision = input.next();
			decision = decision.toLowerCase();
			if (!(decision.equals("a") || decision.equals("b") || decision.equals("c"))) {
				System.out.println("Error en opci�n ingresada. Volver a elegir.");
			}
		} while(!(decision.equals("a") || decision.equals("b") || decision.equals("c")));
		return decision;
		
	}
	
	public static void crearUsuario() {
		System.out.println("Ingrese su nombre");
		String nombreCrear = input.next();
		
		System.out.println("Ingrese su apellido");
		String apellidoCrear = input.next();
		
		System.out.println("Ingrese su DNI");
		int dniCrear = input.nextInt();
		
		System.out.println("Ingrese su nueva contraseña");
		String passwordCrear = input.next();
		
		Persona.crearCuenta(nombreCrear, apellidoCrear, dniCrear, passwordCrear);
	}	
}