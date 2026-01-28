package ejemploEx;

/*
 * Importamos las clases necesarias de JDBC:
 * - Connection: conexión con la base de datos
 * - PreparedStatement: ejecución de consultas con parámetros
 * - Statement: ejecución de sentencias sin parámetros
 * - ResultSet: lectura de resultados
 * - SQLException: gestión de errores SQL
 */
import java.sql.*;

/*
 * Importamos Scanner para leer datos introducidos
 * por el usuario desde la consola.
 */
import java.util.Scanner;

/*
 * Clase SalariosService.
 *
 * Esta clase se encarga de la gestión de salarios
 * de los entrenadores:
 *  - Calcular el salario total de un entrenador
 *  - Aumentar la cuota base un 2% usando una función almacenada
 *
 * Se trabaja directamente con:
 *  - Tipos compuestos en PostgreSQL
 *  - Funciones almacenadas
 */
public class SalariosService {

    /*
     * Muestra el submenú de gestión de salarios
     * y redirige a la opción seleccionada.
     */
    public static void menuSalarios(Scanner sc) {

        // Mostramos el menú de salarios
        System.out.println("\n--- SALARIOS ---");
        System.out.println("1. Calcular salario entrenador");
        System.out.println("2. Subir cuota base 2%");
        System.out.print("Opción: ");

        /*
         * Leemos la opción seleccionada por el usuario.
         */
        int opcion = Integer.parseInt(sc.nextLine());

        /*
         * Switch moderno (Java 14+).
         * Cada opción ejecuta la funcionalidad correspondiente.
         */
        switch (opcion) {

            // Calcular el salario total de un entrenador
            case 1 -> calcularSalario(sc);

            // Subir la cuota base de todos los entrenadores un 2%
            case 2 -> subirCuota();

            // Opción no válida
            default -> System.out.println("Opción incorrecta");
        }
    }

    /*
     * Calcula el salario total de un entrenador concreto.
     *
     * El salario se compone de:
     *  - cuota_base
     *  - comision
     *
     * Ambos campos pertenecen al tipo compuesto salario.
     */
    private static void calcularSalario(Scanner sc) {

        /*
         * try-with-resources:
         * - Obtiene la conexión a la base de datos
         * - Garantiza el cierre automático de la conexión
         */
        try (Connection conn = DBUtil.getConexion()) {

            // Pedimos el DNI del entrenador
            System.out.print("DNI entrenador: ");
            String dni = sc.nextLine();

            /*
             * Consulta SQL:
             * Accedemos a los campos del tipo compuesto salario
             * utilizando la notación (salario).campo
             */
            String sql = """
                SELECT (salario).cuota_base + (salario).comision
                FROM entrenador
                WHERE dni = ?
            """;

            // Preparamos la sentencia SQL
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);

            // Ejecutamos la consulta
            ResultSet rs = pst.executeQuery();

            /*
             * Si existe un resultado, mostramos el salario total
             */
            if (rs.next()) {
                System.out.println("Salario total: " + rs.getDouble(1)+" €");
            }

        } catch (SQLException e) {
            // Capturamos errores relacionados con la base de datos
            e.printStackTrace();
        }
    }

    /*
     * Aumenta la cuota base de todos los entrenadores un 2%.
     *
     * Esta operación se realiza mediante una función almacenada
     * en la base de datos llamada subir_cuota().
     */
    private static void subirCuota() {

        /*
         * try-with-resources:
         * - Abre la conexión
         * - Crea un Statement (no necesita parámetros)
         */
        try (Connection conn = DBUtil.getConexion();
             Statement st = conn.createStatement()) {

            /*
             * Llamada a la función almacenada subir_cuota().
             *
             * La lógica de negocio se ejecuta en la base de datos,
             * no en Java.
             */
            st.execute("SELECT subir_cuota()");

            System.out.println("Cuota base aumentada un 2%");

        } catch (SQLException e) {
            // Capturamos errores SQL
            e.printStackTrace();
        }
    }
}
