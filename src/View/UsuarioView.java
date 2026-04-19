package View;

import Model.Usuario;

import java.util.ArrayList;
import java.util.Scanner;

// Vista de consola para la gestión de usuarios
public class UsuarioView {
    public void mostrarMenuUsuario (){
        System.out.println("-- MENÚ USUARIO --");
        System.out.println("0. Volver al menú inicial.");
        System.out.println("1. Insertar usuario.");
        System.out.println("2. Consultar usuarios.");
        System.out.println("3. Actualizar usuarios.");
        System.out.println("4. Borrar usuario.");
    }

    public void menuConsultasUsuario (){
        System.out.println("-- MENÚ CONSULTAS USUARIO --");
        System.out.println("0. Volver al menú de usuario.");
        System.out.println("1. Consultar todos los usuarios.");
        System.out.println("2. Consultar por correo electrónico.");
        System.out.println("3. Consultar por nombre.");
        System.out.println("4. Consultar por fecha de nacimiento.");
        System.out.println("5. Consultar por tipo de usuario.");
    }

    public void menuActualizarUsuario() {
        System.out.println("-- MENÚ ACTUALIZAR USUARIO --");
        System.out.println("0. Volver al menú de usuario.");
        System.out.println("1. Actualizar correo electrónico.");
        System.out.println("2. Actualizar contraseña.");
        System.out.println("3. Actualizar nombre.");
        System.out.println("4. Actualizar fecha de nacimiento.");
        System.out.println("5. Actualizar tipo de usuario");
    }

    public Usuario formularioUsuario () {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresa tu correo electrónico: ");
        String correo_electronico = sc.nextLine();
        System.out.println("Ingresa una contraseña: ");
        String contrasena = sc.nextLine();
        System.out.println("Ingresa un nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Ingresa tu fecha de nacimiento en formato AAAA/MM/DD: ");
        String fecha_nacimiento = sc.nextLine();
        System.out.println("Indica tu tipo de usuario: ");
        String tipo_usuario = sc.nextLine();

        Usuario usuario = new Usuario(0,correo_electronico,contrasena,nombre, fecha_nacimiento, tipo_usuario);
        return usuario;
    }

    public void mostrarUsuarios (ArrayList<Usuario> usuarios) {
        if (usuarios.size() == 0) {
            System.out.println("No hay datos que mostrar");
            return;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioActual = usuarios.get(i);
            System.out.println(usuarioActual.getId_usuario() + " " + usuarioActual.getCorreo_electronico() + " " + usuarioActual.getContrasena() + " " + usuarioActual.getNombre() + " " + usuarioActual.getFecha_nacimiento() + " " + usuarioActual.getTipo_usuario());
        }
    }

    /**
     * Funcion encargada de pedir un valor al usuario
     * @param campo: Nomrbe de la columna de la bd correpsondiente al campo a pedir
     * @return El input introducido por el usuario
     */
    public String pedirValor (String campo){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un valor para '" + campo + "': ");

        String valor = sc.nextLine();
        return valor;
    }

    public int pedirId (){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una ID: ");

        int id = sc.nextInt();
        return id;
    }
}
