package DB;

import Model.Usuario;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase DAO (Data Access Object) que centraliza todas las operaciones
 * de acceso a datos relacionadas con la entidad Usuario.
 * Implementa el patrón Singleton.
 */
public class UsuarioDAO {

    /** Consulta para insertar un usuario */
    private static final String queryInsertar = """
            INSERT INTO usuario (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario)
            VALUES (?, ?, ?, ?, ?)
            """;

    /** Consulta para modificar un usuario por su ID */
    private static final String queryModificarPorId = """
            UPDATE usuario 
            SET correo_electronico = ?, contrasena = ?, nombre = ?, fecha_nacimiento = ?, tipo_usuario = ?
            WHERE id_usuario = ?
            """;

    /** Consulta para eliminar un usuario por su ID */
    private static final String queryEliminarPorId = """
            DELETE FROM usuario 
            WHERE id_usuario = ?
            """;

    /** Consulta para obtener todos los usuarios */
    private static final String queryRegistros = """
            SELECT *
            FROM usuario
            """;

    /** Consulta para buscar un usuario por ID */
    private static final String queryRegistrosPorId = """
            SELECT *
            FROM USUARIO
            WHERE id_usuario = ?
            """;

    /** Consulta para buscar usuarios por correo electrónico */
    private static final String queryRegistrosPorCorreoElectronico = """
            SELECT * 
            FROM usuario
            WHERE correo_electronico = ?
            """;

    /** 
     * Nota: por motivos de seguridad no se realizan consultas por contraseña.
     */

    /** Consulta para buscar usuarios por nombre */
    private static final String queryRegistrosPorNombre = """
            SELECT * 
            FROM usuario 
            WHERE nombre = ?
            """;

    /** Consulta para buscar usuarios por fecha de nacimiento */
    private static final String queryRegistrosPorFechaNacimiento = """
            SELECT * 
            FROM usuario 
            WHERE fecha_nacimiento = ?
            """;

    /** Consulta para buscar usuarios por tipo de usuario */
    private static final String queryRegistrosPorTipoUsuario = """
            SELECT * 
            FROM usuario 
            WHERE tipo_usuario = ?
            """;

    private static volatile UsuarioDAO instance;
    private final Connection connection;

    /**
     * Constructor privado que inicializa la conexión a la base de datos.
     *
     * @throws SQLException si ocurre un error al obtener la conexión
     */
    private UsuarioDAO () throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     * Inserta un usuario en la base de datos.
     *
     * @param usuario objeto Usuario a insertar
     * @return true si la inserción se realizó correctamente, false en caso contrario
     * @throws SQLException si ocurre un error durante la inserción
     */
    public boolean crearUsuario (Usuario usuario) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(queryInsertar, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, usuario.getCorreo_electronico());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getFecha_nacimiento());
            ps.setString(5, usuario.getTipo_usuario());

            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    /**
     * Devuelve la instancia única de UsuarioDAO (patrón Singleton).
     *
     * @return instancia de UsuarioDAO
     * @throws SQLException si ocurre un error al crear la instancia
     */
    public static UsuarioDAO getInstance() throws SQLException {
        if (instance == null) {
            synchronized (UsuarioDAO.class) {
                if (instance == null) {
                    instance = new UsuarioDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return lista de usuarios o null si ocurre un error
     * @throws SQLException si ocurre un error en la consulta
     */
    public ArrayList<Usuario> getUsuarios () throws SQLException{
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

    /**
     * Obtiene usuarios filtrados por un campo y valor específicos.
     *
     * @param campo nombre del campo por el que se desea filtrar
     * @param valor valor del campo a buscar
     * @return lista de usuarios que cumplen el criterio o null si hay error
     * @throws SQLException si ocurre un error en la consulta
     */
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

    /**
     * Devuelve la consulta SQL correspondiente al campo indicado.
     *
     * @param campo nombre del campo
     * @return consulta SQL asociada o null si no existe
     */
    private static String getQuery(String campo) {
        String query = null;
        switch (campo) {
            case "id" -> query = queryRegistrosPorId;
            case "correo" -> query = queryRegistrosPorCorreoElectronico;
            case "nombre" -> query = queryRegistrosPorNombre;
            case "fecha nacimiento" -> query = queryRegistrosPorFechaNacimiento;
            case "tipo usuario" -> query = queryRegistrosPorTipoUsuario;
        }
        return query;
    }

    /**
     * Elimina un usuario de la base de datos a partir de su ID.
     *
     * @param idUsuario identificador del usuario
     * @return true si se eliminó correctamente, false si no se encontró
     * @throws SQLException si ocurre un error durante la eliminación
     */
    public boolean eliminarUsuario (int idUsuario) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(queryEliminarPorId)) {
            ps.setInt(1,idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza los datos de un usuario en la base de datos.
     *
     * @param usuario objeto Usuario con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     * @throws SQLException si ocurre un error durante la actualización
     */
    public boolean actualizarUsuario (Usuario usuario) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(queryModificarPorId)) {
            ps.setString(1,usuario.getCorreo_electronico());
            ps.setString(2,usuario.getContrasena());
            ps.setString(3,usuario.getNombre());
            ps.setString(4,usuario.getFecha_nacimiento());
            ps.setString(5,usuario.getTipo_usuario());
            ps.setInt(6,usuario.getId_usuario());

            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    /**
     * Convierte un ResultSet en una lista de objetos Usuario.
     *
     * @param rs resultado de una consulta SQL
     * @return lista de usuarios
     * @throws SQLException si ocurre un error al procesar el ResultSet
     */
    private ArrayList<Usuario> resultSetToUsuario (ResultSet rs) throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while (rs.next()) {
            usuarios.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("correo_electronico"),
                    rs.getString("contrasena"),
                    rs.getString("nombre"),
                    rs.getString("fecha_nacimiento"),
                    rs.getString("tipo_usuario")
            ));
        }
        return usuarios;
    }
}