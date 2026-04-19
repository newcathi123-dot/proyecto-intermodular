package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // URL de conexión para MySQL, usuario y contraseña
    private static final String URL = "jdbc:mysql://localhost:3306/db_u5";
    private static final String usuario = "root";
    private static final String contrasena = "";

    private static Connection connection;

    private DBConnection (){
    }

    // Crea una instancia de la conexión, si ya existe la devuelve
    public static Connection getConnection() throws SQLException{
        if (connection == null){
            synchronized (DBConnection.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection(URL,usuario, contrasena);
                }
            }
        }
        return connection;
    }

    // Cierra la conexión con la bd
    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            }
            catch (SQLException e){
                System.err.println("No se ha podido cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
