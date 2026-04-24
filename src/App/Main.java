package App;

import Controller.AppController;

/**
 * Clase principal de la aplicación.
 * Contiene el punto de entrada (main) desde donde se inicia la ejecución.
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        new AppController().menuInicial();
    }
}