package Controller;

import DB.UsuarioDAO;
import Model.Usuario;
import View.UsuarioView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioController {

    private final UsuarioView usuarioView;
    private final UsuarioDAO usuarioDAO;

    // Constructor que crea el controlador con sus dependencias
    public UsuarioController () throws SQLException {
        this.usuarioDAO = UsuarioDAO.getInstance();
        this.usuarioView = new UsuarioView();
    }

    // Lanza el menú que gestiona los usuarios
    public void iniciar (){
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            usuarioView.mostrarMenuUsuario();
            opcion = sc.nextInt();

            switch (opcion){
                case 0 -> {return;}
                case 1 -> altaUsuario();
                case 2 -> consultaUsuario();
                case 3 -> actualizarUsuario();
                case 4 -> borrarUsuario();
                default -> System.out.println("Error. Opción no válida.");
            }
        }
        while (opcion !=0);
    }

    // Llama al formulario de UsuarioView y el usuario creado lo pasa a UsuarioDAO (comunica backend y "frontend")
    private void altaUsuario (){
        try {
            usuarioDAO.crearUsuario(usuarioView.formularioUsuario());
        }
        catch (SQLException e) {
            System.out.println("No se ha podido insertar el usuario en la base de datos");
            System.out.println(e.getMessage());
        }
    }
    // Función que muestra el menú de consultas y ejecuta cada una de las acciones en función del input
    private void consultaUsuario (){
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            usuarioView.menuConsultasUsuario();
            opcion = sc.nextInt();

            switch (opcion){
                case 0 -> {return;}
                case 1 -> {
                    try {
                        ArrayList<Usuario> usuarios= usuarioDAO.getUsuarios();
                        usuarioView.mostrarUsuarios(usuarios);
                    }
                    catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        ArrayList<Usuario> usuarios = usuarioDAO.getUsuariosPorCampo("correo", usuarioView.pedirValor("correo"));
                        usuarioView.mostrarUsuarios(usuarios);
                    }
                    catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        ArrayList<Usuario> usuarios = usuarioDAO.getUsuariosPorCampo("nombre", usuarioView.pedirValor("nombre"));
                        usuarioView.mostrarUsuarios(usuarios);
                    }
                    catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        ArrayList<Usuario> usuarios = usuarioDAO.getUsuariosPorCampo("fecha nacimiento", usuarioView.pedirValor("fecha nacimiento"));
                        usuarioView.mostrarUsuarios(usuarios);
                    } catch (Exception e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        ArrayList<Usuario> usuarios = usuarioDAO.getUsuariosPorCampo("tipo usuario", usuarioView.pedirValor("tipo usuario"));
                        usuarioView.mostrarUsuarios(usuarios);
                    } catch (Exception e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        while (opcion != 0);
    }
    // Función que actualiza los campos de usuario
    private void actualizarUsuario () {
        Scanner sc = new Scanner(System.in);
        Usuario usuario = null;
        int opcion;

        Integer id = usuarioView.pedirId();
        try {
            usuario = usuarioDAO.getUsuariosPorCampo("id",id.toString()).getFirst();
        }
        catch (SQLException e) {
            System.out.println("No se ha podido realizar la consulta.");
            System.out.println(e.getMessage());
            return;
        }

        do {
            usuarioView.menuActualizarUsuario();
            opcion = sc.nextInt();

            switch (opcion) {
                case 0 -> {return;}
                case 1 -> {
                    usuario.setCorreo_electronico(usuarioView.pedirValor("correo electrónico"));
                    try {
                        usuarioDAO.actualizarUsuario(usuario);
                    } catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    usuario.setContrasena(usuarioView.pedirValor("contraseña"));
                    try {
                        usuarioDAO.actualizarUsuario(usuario);
                    } catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    usuario.setNombre(usuarioView.pedirValor("nombre"));
                    try {
                        usuarioDAO.actualizarUsuario(usuario);
                    } catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    usuario.setFecha_nacimiento(usuarioView.pedirValor("fecha de nacimiento"));
                    try {
                        usuarioDAO.actualizarUsuario(usuario);
                    } catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
                case 5 -> {
                    usuario.setTipo_usuario(usuarioView.pedirValor("tipo de usuario"));
                    try {
                        usuarioDAO.actualizarUsuario(usuario);
                    } catch (SQLException e) {
                        System.out.println("No se ha podido realizar la consulta");
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        while (opcion != 0);
    }
    // Función que elimina usuario dada una id
    private void borrarUsuario () {
        try {
            if ( usuarioDAO.eliminarUsuario(usuarioView.pedirId())) {
                System.out.println("El usuario se ha eliminado correctamente.");
            }
            else {
                System.out.println("No se ha encontrado el usuario");
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar el usuario.");
            System.out.println(e.getMessage());
        }
    }
}
