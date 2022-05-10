package CajeroMySQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/banco";
	private static final String USUARIO = "root";
	private static final String CLAVE = "1234";
	
	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}
	
	public Connection conectar() {
		Connection conexion = null;
		try {
			 // com.mysql.cj.jdbc.Driver   com.mysql.jdbc.Driver
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
			
			System.out.println("CONEXIÓN OK");
		} catch (SQLException e) {
			System.out.println("ERROR EN LA CONEXIÓN");
			e.printStackTrace();
		}
		return conexion;
	}
}
