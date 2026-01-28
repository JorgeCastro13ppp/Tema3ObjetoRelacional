package ejemploEx;

/*
 * Importamos todas las clases necesarias de JDBC:
 * - Connection: conexión con la base de datos
 * - PreparedStatement: ejecución de SQL con parámetros
 * - ResultSet: lectura de resultados de consultas
 * - SQLException: gestión de errores SQL
 */
import java.sql.*;

/*
 * Importamos Scanner para leer datos desde la consola.
 */
import java.util.Scanner;

/*
 * Clase RutinasService.
 *
 * Esta clase se encarga de la gestión de rutinas de los clientes:
 *  - Añadir una rutina a un cliente
 *  - Consultar el número total de rutinas de un cliente
 *
 * Se trabaja directamente con:
 *  - Arrays en PostgreSQL
 *  - Funciones almacenadas en la base de datos
 */
public class RutinasService {

    /*
     * Muestra el submenú de gestión de rutinas
     * y dirige la ejecución al método correspondiente.
     */
    public static void menuRutinas(Scanner sc) {

        // Mostramos el menú de rutinas
        System.out.println("\n--- RUTINAS ---");
        System.out.println("1. Añadir rutina");
        System.out.println("2. Total rutinas cliente");
        System.out.print("Opción: ");

        /*
         * Leemos la opción seleccionada por el usuario.
         */
        int opcion = Integer.parseInt(sc.nextLine());

        /*
         * Switch moderno (Java 14+).
         * Cada opción llama a la operación correspondiente.
         */
        switch (opcion) {

            // Añadir una rutina a un cliente
            case 1 -> anadirRutina(sc);

            // Consultar el total de rutinas de un cliente
            case 2 -> totalRutinas(sc);

            // Opción no válida
            default -> System.out.println("Opción incorrecta");
        }
    }

    /*
     * Método que añade una nueva rutina al array de rutinas
     * de un cliente existente.
     *
     * Utiliza la función array_append de PostgreSQL.
     */
    private static void anadirRutina(Scanner sc) {

        /*
         * try-with-resources:
         * - Obtiene la conexión desde DBUtil
         * - Garantiza el cierre automático de la conexión
         */
        try (Connection conn = DBUtil.getConexion()) {

            // Solicitamos los datos necesarios
            System.out.print("DNI cliente: ");
            String dni = sc.nextLine();

            System.out.print("Rutina: ");
            String rutina = sc.nextLine();

            /*
             * Sentencia SQL:
             * array_append añade un elemento al final
             * del array rutinas sin sobrescribir su contenido.
             */
            String sql = """
                UPDATE cliente
                SET rutinas = array_append(rutinas, ?)
                WHERE dni = ?
            """;

            // Preparamos la sentencia SQL
            PreparedStatement pst = conn.prepareStatement(sql);

            // Asignamos los valores a los parámetros
            pst.setString(1, rutina);
            pst.setString(2, dni);

            // Ejecutamos la actualización
            pst.executeUpdate();

            System.out.println("Rutina añadida");

        } catch (SQLException e) {
            // Capturamos errores SQL
            e.printStackTrace();
        }
    }

    /*
     * Método que muestra el número total de rutinas
     * de un cliente utilizando una función almacenada.
     */
    private static void totalRutinas(Scanner sc) {

        /*
         * try-with-resources para gestionar automáticamente
         * el cierre de la conexión.
         */
        try (Connection conn = DBUtil.getConexion()) {

            // Solicitamos el DNI del cliente
            System.out.print("DNI cliente: ");
            String dni = sc.nextLine();

            /*
             * Llamada a la función almacenada total_rutinas,
             * que devuelve el número de elementos del array rutinas.
             */
            String sql = "SELECT total_rutinas(?)";

            // Preparamos la llamada a la función
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);

            // Ejecutamos la consulta
            ResultSet rs = pst.executeQuery();

            /*
             * Si la función devuelve un resultado,
             * lo mostramos por pantalla.
             */
            if (rs.next()) {
                System.out.println("Total rutinas: " + rs.getInt(1));
            }

        } catch (SQLException e) {
            // Capturamos errores SQL
            e.printStackTrace();
        }
    }
}
