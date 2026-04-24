package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a la base de datos.
 * Implementa un patrón Singleton para asegurar una única conexión activa.
 */
public class DBConnection {

    /** URL de conexión a la base de datos MySQL */
    private static final String URL = "jdbc:mysql://localhost:3306/db_u5";

    /** Usuario de la base de datos */
    private static final String usuario = "root";

    /** Contraseña de la base de datos */
    private static final String contrasena = "";

    private static Connection connection;

    /**
     * Constructor privado para evitar la instanciación de la clase.
     */
    private DBConnection (){
    }

    /**
     * Obtiene la conexión a la base de datos.
     * Si no existe, crea una nueva instancia de conexión.
     *
     * @return objeto {@link Connection} activo
     * @throws SQLException si ocurre un error al establecer la conexión
     */
    public static Connection getConnection() throws SQLException{
        if (connection == null){
            synchronized (DBConnection.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection(URL, usuario, contrasena);
                }
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión actual con la base de datos si existe.
     * En caso de error, muestra un mensaje por consola.
     */
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