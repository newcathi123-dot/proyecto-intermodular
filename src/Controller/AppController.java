package Controller;

import DB.DBConnection;
import java.util.Scanner;

/**
 * Controlador principal de la aplicación.
 * Gestiona el menú inicial y la navegación hacia otros controladores.
 */
public class AppController {

    private UsuarioController usuarioController;

    /**
     * Muestra el menú inicial por consola.
     */
    private void mostrarMenuInicial (){
        System.out.println("-- MENÚ INICIAL --");
        System.out.println("0. Salir.");
        System.out.println("1. Gestionar usuario.");
    }

    /**
     * Inicia la aplicación mostrando el menú principal y gestionando la navegación.
     * También se encarga de cerrar la conexión a la base de datos al finalizar.
     *
     * @return no devuelve ningún valor. Finaliza cuando el usuario selecciona salir.
     */
    public void menuInicial () {
        Scanner sc = new Scanner(System.in);
        int opcion;
        try {
            usuarioController = new UsuarioController();

            do {
                mostrarMenuInicial();
                opcion = sc.nextInt();
                switch (opcion) {
                    case 0 -> System.out.println("Saliendo del programa.");
                    case 1 -> usuarioController.iniciar();
                    default -> System.out.println("Error. Opción no válida.");
                }
            }
            while (opcion != 0);
        }
        catch (Exception e){
            System.out.println("No se ha podido crear la conexión a la base de datos");
        }

        DBConnection.cerrarConexion();
    }
}