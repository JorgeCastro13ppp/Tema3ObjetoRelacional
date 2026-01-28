package ejemploEx;

/*
 * Importamos las clases necesarias de JDBC:
 * - Connection: representa una conexión activa con la base de datos.
 * - DriverManager: se encarga de localizar el driver JDBC adecuado
 *   y establecer la conexión.
 * - SQLException: excepción que se lanza cuando ocurre cualquier
 *   problema relacionado con la base de datos.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Clase de utilidad (Utility Class) para la gestión de la conexión
 * a la base de datos PostgreSQL.
 *
 * Esta clase centraliza los datos de conexión y proporciona un
 * método estático para obtener una conexión reutilizable desde
 * cualquier parte del programa.
 *
 * No se crean objetos de esta clase, se usa directamente.
 */
public class DBUtil {

    /*
     * URL de conexión JDBC.
     *
     * Formato:
     * jdbc:postgresql://servidor:puerto/nombreBD
     *
     * - jdbc: indica que se usa JDBC
     * - postgresql: driver de PostgreSQL
     * - localhost: servidor donde está la BD
     * - 5432: puerto por defecto de PostgreSQL
     * - bdgym_jorge: nombre de la base de datos
     */
    private static final String URL =
            "jdbc:postgresql://localhost:5432/bdgym_jorge";

    /*
     * Usuario de acceso a la base de datos.
     * Se declara como constante porque no debe modificarse.
     */
    private static final String USER = "postgres";

    /*
     * Contraseña del usuario de la base de datos.
     * En proyectos reales no se suele hardcodear,
     * pero para prácticas y exámenes es totalmente válido.
     */
    private static final String PASSWORD = "toor";

    /*
     * Método estático que devuelve una conexión activa
     * a la base de datos.
     *
     * - Es static para poder llamarlo sin crear un objeto DBUtil.
     * - Devuelve un objeto Connection, que es la base de JDBC.
     * - Lanza SQLException para que el método que lo invoque
     *   decida cómo gestionar el error (buena práctica).
     */
    public static Connection getConexion() throws SQLException {

        /*
         * DriverManager.getConnection:
         * 1. Localiza el driver JDBC de PostgreSQL.
         * 2. Intenta conectarse a la base de datos indicada en la URL.
         * 3. Autentica usando el usuario y contraseña.
         * 4. Devuelve un objeto Connection si todo va bien.
         *
         * Si ocurre cualquier error, lanza una SQLException.
         */
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
