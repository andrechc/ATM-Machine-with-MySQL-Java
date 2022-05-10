package CajeroMySQL;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String password, opcion;
		int dni;
		int monto;
		boolean sesionIniciada = false, salir = false, salirCuenta = false;
		Persona userLoged = new Persona();
			
		do {
			// RECIBIR PANEL OPCIONES
			opcion = funciones.recibirOpcion1();
			
			switch (opcion) {
			// VERIFICACION USUARIO
			case "a":
				System.out.println("Ingresar dni");
				dni = input.nextInt();
				System.out.println("Ingresar contrase�a");
				password = input.next();				
				sesionIniciada = userLoged.verificarUsuario(dni, password);				
				break;
				
			case "b":
				// FUNCION CREAR USUARIO.
				funciones.crearUsuario();
				break;
				
			case "c":
				// TERMINAR PROCESO
				salir = true;
			}
			
			if (sesionIniciada) {
				do {
					// RECIBIR PANEL OPCIONES
					opcion = funciones.recibirOpcion2();
					
					switch (opcion) {
					case "a":
						// EXTRAER MONTO DE SALDO
						System.out.println("Ingresar monto a retirar:");
						monto = input.nextInt();
						userLoged.Extraer(monto);
						break;
					case "b":
						// DEPOSITAR MONTO A SALDO
						System.out.println("Ingresar monto a depositar:");
						monto = input.nextInt();
						userLoged.Depositar(monto);
						break;
						
					case "c":
						// VER SALDO
						userLoged.ConsultarSaldo();
						break;
						
					case "d":
						// TRANSFERIR MONTO A OTRO USUARIO DE LA DB(mysql)
						System.out.println("Ingresar dni de la persona a transferir");
						dni = input.nextInt();
						System.out.println("Ingresar monto a transferir");
						monto = input.nextInt();
						userLoged.transferirMonto(monto, dni);
						break;
						
					case "e":
						// CERRAR SESIÓN -> DEVUELVE AL MENU PRINCIPAL
						salirCuenta = true;
						sesionIniciada = false;
						break;
						
					}
				} while (salirCuenta == false);
			}
			
		} while (salir == false);
	}
}
