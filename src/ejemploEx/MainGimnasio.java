package ejemploEx;

/*
 * Importamos la clase Scanner para poder leer datos
 * introducidos por el usuario desde la consola.
 */
import java.util.Scanner;

/*
 * Clase principal del programa.
 *
 * Contiene el método main y se encarga exclusivamente
 * de mostrar el menú principal y redirigir la ejecución
 * a las distintas partes de la aplicación.
 *
 * No contiene lógica de negocio, solo control del flujo.
 */
public class MainGimnasio {

    /*
     * Método main: punto de entrada de la aplicación.
     * Desde aquí se inicia el programa.
     */
    public static void main(String[] args) {

        /*
         * Creamos un objeto Scanner para leer la opción
         * que introduce el usuario por teclado.
         */
        Scanner sc = new Scanner(System.in);

        /*
         * Variable que almacenará la opción seleccionada
         * por el usuario en el menú.
         */
        int opcion;

        /*
         * Bucle do-while:
         * - Muestra el menú al menos una vez.
         * - Se repite mientras la opción no sea 0 (salir).
         */
        do {
            // Mostramos el menú principal
            System.out.println("\n--- GIMNASIO ---");
            System.out.println("1. Alta");
            System.out.println("2. Carga de datos");
            System.out.println("3. Gestión de rutinas");
            System.out.println("4. Gestión de salarios");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            /*
             * Leemos la opción del usuario como String
             * y la convertimos a entero.
             *
             * Se usa nextLine() para evitar problemas
             * con el buffer del Scanner.
             */
            opcion = Integer.parseInt(sc.nextLine());

            /*
             * Switch moderno (Java 14+):
             * Dependiendo del valor de la opción,
             * se llama al método correspondiente.
             */
            switch (opcion) {

                // Opción 1: menú de altas
                case 1 -> AltaService.menuAlta(sc);

                // Opción 2: carga de datos
                case 2 -> CargaDatosService.menuCarga(sc);

                // Opción 3: gestión de rutinas
                case 3 -> RutinasService.menuRutinas(sc);

                // Opción 4: gestión de salarios
                case 4 -> SalariosService.menuSalarios(sc);

                // Opción 0: salir del programa
                case 0 -> System.out.println("Saliendo...");

                // Cualquier otro valor
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        /*
         * Cerramos el Scanner para liberar recursos.
         * Es buena práctica cerrar siempre los recursos
         * que se abren.
         */
        sc.close();
    }
}
