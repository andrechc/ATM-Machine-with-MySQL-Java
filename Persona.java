package CajeroMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Persona {
	public static Scanner input = new Scanner(System.in);
	public static Conexion conexion = new Conexion();
	public static Connection cn = null;
	public static Statement stm = null;
	public static ResultSet rs = null;
	
	private String nombre;
	private String apellido;
	private int dni;
	private String password;
	private int saldo;	
	
	public static void crearCuenta(String nombre, String apellido, int dni, String password) {
		int saldoInicial = 0;
		try {
			cn = conexion.conectar();
			stm = cn.createStatement(); 
			
			try {
				PreparedStatement a = cn.prepareStatement("INSERT INTO banco.usuarios VALUES('"+ dni + "','" + nombre + "','" + apellido + "','" + password + "','" + saldoInicial +"')");
				a.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}		
				if (stm != null) {
					stm.close();
				}
				if (cn != null) {
					cn.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void verUsuarios() {
		String code = "SELECT * FROM banco.usuarios";
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(code); 
			
			while(rs.next()) {
				String contacto = rs.getString(1);
				int numero = rs.getInt(2);	
				System.out.println(contacto + " | " + numero);	
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}		
				if (stm != null) {
					stm.close();
				}
				if (cn != null) {
					cn.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
	}
	
	public boolean verificarUsuario(int dniUsuario, String passwordUsuario) {
		boolean usuarioLogeado = false;
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM banco.usuarios;"); 
			
			while(rs.next() && usuarioLogeado != true) {
				int dniGet = rs.getInt(1);
				String nombreGet = rs.getString(2);
				String passwordGet = rs.getString(4);
				
				if(dniUsuario == dniGet && passwordUsuario.equals(passwordGet)) {
					this.dni = dniGet;
					this.nombre = nombreGet;
					this.apellido = rs.getString(3);
					this.password = rs.getString(4);
					this.saldo = rs.getInt(5);
					usuarioLogeado = true;		
					System.out.println("SESIÓN INICIADA. Bienvenido " + nombre);
					System.out.println("******************************");
				}
			} if (usuarioLogeado == false) {
				System.out.println("DNI ó CONTRASEÑA incorrectos.");
				System.out.println("******************************");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}		
				if (stm != null) {
					stm.close();
				}
				if (cn != null) {
					cn.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return usuarioLogeado;
	}
	
	public boolean Depositar(int monto) {
		boolean transaccionRealizada = false;
		if(monto > 0) {
			this.saldo += monto;
			try {
				cn = conexion.conectar();
				stm = cn.createStatement();
				rs = stm.executeQuery("SELECT * FROM banco.usuarios"); 				
				while(rs.next()) {
					int dniGet = rs.getInt(1);					
					if(dni == dniGet) {					
						try {
							PreparedStatement a = cn.prepareStatement("UPDATE usuarios set saldo='" + this.saldo + "' WHERE dni='" + dni + "'");
							a.executeUpdate(); 							
							System.out.println("Transacción realizada con ÉXITO. Nuevo saldo de $" +  this.saldo);
							System.out.println("******************************");
						} catch (Exception e) {
							System.out.println(e);
						}
						transaccionRealizada = true;
					}
				} if (transaccionRealizada == false) {
					System.out.println("Transaccion no realizada");
					System.out.println("******************************");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("No puede depositar un monto negativo.");
			System.out.println("******************************");
		}
		
		return transaccionRealizada; 
	}
	
	public boolean Extraer(int monto) {
		boolean transaccionRealizada = false;
		if(monto < this.saldo) {
			this.saldo -= monto;
			try {
				cn = conexion.conectar();
				stm = cn.createStatement();
				rs = stm.executeQuery("SELECT * FROM banco.usuarios"); 				
				while(rs.next()) {
					int dniGet = rs.getInt(1);					
					if(dni == dniGet) {					
						try {
							PreparedStatement a = cn.prepareStatement("UPDATE usuarios set saldo='" + this.saldo + "' WHERE dni='" + dni + "'");
							a.executeUpdate(); 							
							System.out.println("Transacción realizada con ÉXITO. Nuevo saldo de $" +  this.saldo);
							System.out.println("******************************");
						} catch (Exception e) {
							System.out.println(e);
						}
						transaccionRealizada = true;
					}
				} if (transaccionRealizada == false) {
					System.out.println("Transaccion no realizada");
					System.out.println("******************************");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("No tiene fondos suficientes para realizar esa transacción.");
			System.out.println("******************************");
		}		
		return transaccionRealizada; 
	}
	
	public static boolean modificarSaldo(int dni, int nuevoSaldo) {
		boolean transaccionRealizada = false;
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM banco.usuarios"); 
			
			while(rs.next()) {
				int dniGet = rs.getInt(1);
				
				if(dni == dniGet) {
					System.out.println("Contacto encontrado");
					String nombreEncontrado = rs.getString(2);
					String apellidoEncontrado = rs.getString(3);
					System.out.println(nombreEncontrado + " | " + apellidoEncontrado);	
					try {
						PreparedStatement a = cn.prepareStatement("UPDATE contactos set saldo='" + nuevoSaldo + "' WHERE contacto='" + dni + "'");
						a.executeUpdate(); 		
					} catch (Exception e) {
						System.out.println(e);
					}
					transaccionRealizada = true;
				}
			} if (transaccionRealizada == false) {
				System.out.println("Transaccion no realizada");
				System.out.println("******************************");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return transaccionRealizada; 
	}
	
	public boolean EncontrarUsuario(int dniUsuario) {
		boolean usuarioEncontrado = false;
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM banco.usuarios;"); 
			
			while(rs.next() && usuarioEncontrado != true) {
				int dniGet = rs.getInt(1);
				String nombreGet = rs.getString(2);
				
				if(dniUsuario == dniGet) {
					this.dni = dniGet;
					this.nombre = nombreGet;
					this.apellido = rs.getString(3);
					this.password = rs.getString(4);
					this.saldo = rs.getInt(5);
					usuarioEncontrado = true;		
					System.out.println("USUARIO ENCONTRADO " + nombre + " " + apellido);
					System.out.println("******************************");
				}
			} if (usuarioEncontrado == false) {
				System.out.println("DNI no encontrado.");
				System.out.println("******************************");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}		
				if (stm != null) {
					stm.close();
				}
				if (cn != null) {
					cn.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return usuarioEncontrado;
	}
	
	public void transferirMonto(int monto, int dniRecibidor) {
		boolean transaccionRealizada = false;
		if(monto <= this.saldo && monto > 0) {			
				try {
					cn = conexion.conectar();
					stm = cn.createStatement();
					rs = stm.executeQuery("SELECT * FROM banco.usuarios"); 				
					while(rs.next()) {
						int dniGet = rs.getInt(1);					
						if(dniRecibidor == dniGet) {	
							this.saldo -= monto;
							int saldoGet = rs.getInt(5);
							try {
								PreparedStatement a = cn.prepareStatement("UPDATE usuarios set saldo='" + (saldoGet + monto) + "' WHERE dni='" + dniGet + "'");
								a.executeUpdate(); 							
								System.out.println("Transacción realizada con ÉXITO. Nuevo saldo de $" +  this.saldo);
								System.out.println("******************************");
							} catch (Exception e) {
								System.out.println(e);
							}
							transaccionRealizada = true;
						}
					} if (transaccionRealizada == false) {
						System.out.println("Transaccion no realizada");
						System.out.println("******************************");
					}
				} catch (Exception e) {
					System.out.println(e);
				}			
		} else {
			System.out.println("fondos INSUFICIENTES.");
			System.out.println("******************************");
		}
	}
	
	public void ConsultarSaldo() {
		System.out.println("Su saldo actual es de $" + saldo + ".");
		System.out.println("******************************");
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", password=" + password
				+ ", saldo=" + saldo + "]";
	}
	
}
