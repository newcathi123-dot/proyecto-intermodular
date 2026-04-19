package DB;

import Model.Usuario;
import java.sql.*;
import java.util.ArrayList;

// Centralizamos todas las operaciones de acceso a los datos de usuario
public class UsuarioDAO {
    // Consulta para insertar un usuario
    private static final String queryInsertar = """
            INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario)
            VALUES (?, ?, ?, ?, ?)
            """;

    // Consulta para modificar a un usuario por su ID
    private static final String queryModificarPorId = """
            UPDATE usuario 
            SET correo_electronico = ?, contrasena = ?, nombre = ?, fecha_nacimiento = ?, tipo_usuario = ?
            WHERE id_usuario = ?
            """;

    // Consulta para eliminar un usuario por su ID
    private static final String queryEliminarPorId = """
            DELETE FROM usuario 
            WHERE id_usuario = ?
            """;

    // Consulta que muestra un listado con todos los usuarios
    private static final String queryRegistros = """
            SELECT *
            FROM usuario
            """;

    // Consulta que busca por iD
    private static final String queryRegistrosPorId = """
            SELECT *
            FROM USUARIO
            WHERE id_usuario = ?
            """;

    // Consulta que muestra los registros según el correo electrónico
    private static final String queryRegistrosPorCorreoElectronico = """
            SELECT * 
            FROM usuario
            WHERE correo_electronico = ?
            """;

    /*He pensado que por motivos de seguridad no se debería hacer una query de
    la contraseña*/

    // Consulta que muestra los registros según el nombre
    private static final String queryRegistrosPorNombre = """
            SELECT * 
            FROM usuario 
            WHERE nombre = ?
            """;

    // Consulta que muestra los registros según la fecha de nacimiento
    private static final String queryRegistrosPorFechaNacimiento = """
            SELECT * 
            FROM usuario 
            WHERE fecha_nacimiento = ?
            """;

    // Consulta que muestra los registros según el tipo de usuario
    private static final String queryRegistrosPorTipoUsuario = """
            SELECT * 
            FROM usuario 
            WHERE tipo_usuario = ?
            """;

    private static volatile UsuarioDAO instance;

    private final Connection connection;

    private UsuarioDAO () throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    // Inserta un usuario en la base de datos
    public boolean crearUsuario (Usuario usuario) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(queryInsertar,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getCorreo_electronico());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getFecha_nacimiento());
            ps.setString(5, usuario.getTipo_usuario());

            // Lanza la consulta al la base de datos
           int filasInsertadas = ps.executeUpdate();
           return filasInsertadas > 0;
        }
    }

    // Devuelve la instancia de la conexión si ya existe y si no la crea
    public static UsuarioDAO getInstance() throws  SQLException {
        if (instance == null) {
            synchronized (UsuarioDAO.class) {
                if (instance == null) {
                    instance = new UsuarioDAO();
                }
            }
        }
        return instance;
    }

    // Función que muestra todos los usuarios
    public ArrayList<Usuario> getUsuarios () throws  SQLException{
        ArrayList<Usuario> usuario = new ArrayList<>();
        try {
            Statement sentencia = connection.createStatement();
            return resultSetToUsuario(sentencia.executeQuery(queryRegistros));
        }
        catch (SQLException e) {
            System.out.println("Error al realizar la consulta");
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Función hace la consulta dependiendo del campo y valor introducidos
    public ArrayList<Usuario> getUsuariosPorCampo (String campo, String valor) throws SQLException{
        String query = getQuery(campo);
        try (PreparedStatement ps = connection.prepareStatement(query)){
            if (campo.equals("id")) {
                ps.setInt(1, Integer.parseInt(valor));
            }
            else {
                ps.setString(1, valor );
            }
            return resultSetToUsuario(ps.executeQuery());
        }
        catch (SQLException e) {
            System.out.println("Error al realizar la consulta");
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String getQuery(String campo) {
        String query = null;
        switch (campo) {
            case "id" -> {
                query = queryRegistrosPorId;
            }
            case "correo" -> {
                query = queryRegistrosPorCorreoElectronico;
            }
            case "nombre" -> {
                query = queryRegistrosPorNombre;
            }
            case "fecha nacimiento" -> {
                query = queryRegistrosPorFechaNacimiento;
            }
            case "tipo usuario" -> {
                query = queryRegistrosPorTipoUsuario;
            }
        }
        return query;
    }

    // Función que elimina un usuario dado un id
    public boolean eliminarUsuario (int idUsuario) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(queryEliminarPorId)) {
            ps.setInt(1,idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // Función que actualiza un usuario
    public boolean actualizarUsuario (Usuario usuario) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(queryModificarPorId)) {
            ps.setString(1,usuario.getCorreo_electronico());
            ps.setString(2,usuario.getContrasena());
            ps.setString(3,usuario.getNombre());
            ps.setString(4,usuario.getFecha_nacimiento());
            ps.setString(5,usuario.getTipo_usuario());
            ps.setInt(6,usuario.getId_usuario());

            System.out.println(ps);

            // Lanza la consulta al la base de datos
            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    // Transforma un resultset a un array de usuario para el manejo en la aplicación de java
    private ArrayList<Usuario> resultSetToUsuario (ResultSet rs) throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while (rs.next()) {
            usuarios.add(new Usuario(rs.getInt("id_usuario"),rs.getString("correo_electronico"), rs.getString("contrasena"),rs.getString("nombre"), rs.getString("fecha_nacimiento"), rs.getString("tipo_usuario")));
        }
        return usuarios;
    }
}

