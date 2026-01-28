package ejemploEx;

/*
 * IMPORTACIONES PARA LECTURA DE FICHEROS
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * IMPORTACIONES PARA LECTURA DE FICHEROS JSON
 */
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * IMPORTACIONES JDBC
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * IMPORTACIONES PARA JSON (librería org.json)
 * En un examen basta con explicar que se usaría una librería JSON.
 */
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * IMPORTACIÓN DE SCANNER PARA EL MENÚ
 */
import java.util.Scanner;

/*
 * Clase CargaDatosService.
 *
 * Esta clase se encarga de la carga de datos desde ficheros externos:
 *  - Entrenadores desde un fichero de texto (TXT)
 *  - Clientes desde un fichero JSON
 *
 * La clase contiene:
 *  - Un menú de selección
 *  - Un método para cada tipo de carga
 */
public class CargaDatosService {

    /*
     * Muestra el submenú de carga de datos y
     * llama al método correspondiente.
     */
    public static void menuCarga(Scanner sc) {

        System.out.println("\n--- CARGA DE DATOS ---");
        System.out.println("1. Cargar clientes (JSON)");
        System.out.println("2. Cargar entrenadores (TXT)");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        /*
         * Switch moderno (Java 14+).
         * Cada opción ejecuta la carga real del fichero.
         */
        switch (opcion) {
            case 1 -> cargarClientesJSON();
            case 2 -> cargarEntrenadoresTXT();
            default -> System.out.println("Opción incorrecta");
        }
    }

    /*
     * Carga entrenadores desde un fichero de texto.
     *
     * Formato del fichero entrenadores.txt:
     * dni;nombre;apellidos;especialidad;cuota;comision;
     *
     * El último ';' genera un campo vacío extra,
     * que simplemente ignoramos.
     */
    public static void cargarEntrenadoresTXT() {

        String sql = """
            INSERT INTO entrenador (dni, nombre, apellidos, especialidad, salario)
            VALUES (?, ?, ?, ?, ROW(?, ?))
        """;

        /*
         * try-with-resources:
         * - Abre conexión a la BD
         * - Abre el fichero TXT
         * - Prepara la sentencia SQL
         *
         * Todos los recursos se cierran automáticamente.
         */
        try (
            Connection conn = DBUtil.getConexion();
            BufferedReader br = new BufferedReader(new FileReader("entrenadores.txt"));
            PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            String linea;

            // Leemos el fichero línea a línea
            while ((linea = br.readLine()) != null) {

                // Separar campos por ';'
                String[] datos = linea.split(";");

                /*
                 * IMPORTANTE:
                 * Si la línea termina en ';', split genera un campo vacío final.
                 * No pasa nada mientras usemos solo los índices necesarios.
                 */

                pst.setString(1, datos[0]); // dni
                pst.setString(2, datos[1]); // nombre
                pst.setString(3, datos[2]); // apellidos
                pst.setString(4, datos[3]); // especialidad
                pst.setInt(5, Integer.parseInt(datos[4])); // cuota base
                pst.setDouble(6, Double.parseDouble(datos[5])); // comisión

                pst.executeUpdate();
            }

            System.out.println("Entrenadores cargados desde TXT");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Carga clientes desde un fichero JSON.
     *
     * El fichero clientes.json contiene un array de objetos,
     * donde cada objeto representa un cliente.
     */
    public static void cargarClientesJSON() {

        String sql = """
            INSERT INTO cliente (dni, nombre, apellidos, numerocliente, rutinas)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
            Connection conn = DBUtil.getConexion();
            PreparedStatement pst = conn.prepareStatement(sql)
        ) {

            /*
             * Leemos el contenido completo del fichero JSON
             * como una cadena de texto.
             */
            String contenido = Files.readString(Path.of("clientes.json"));

            // Convertimos el texto en un JSONArray
            JSONArray clientes = new JSONArray(contenido);

            // Recorremos el array de clientes
            for (int i = 0; i < clientes.length(); i++) {

                JSONObject c = clientes.getJSONObject(i);

                pst.setString(1, c.getString("dni"));
                pst.setString(2, c.getString("nombre"));
                pst.setString(3, c.getString("apellidos"));
                pst.setInt(4, c.getInt("numerocliente"));

                /*
                 * El campo rutinas es un array JSON,
                 * que convertimos a un array de String.
                 */
                JSONArray rutinasJson = c.getJSONArray("rutinas");
                String[] rutinas = new String[rutinasJson.length()];

                for (int j = 0; j < rutinasJson.length(); j++) {
                    rutinas[j] = rutinasJson.getString(j);
                }

                /*
                 * Convertimos el array de Java a un array SQL
                 * para poder insertarlo en PostgreSQL.
                 */
                pst.setArray(5, conn.createArrayOf("text", rutinas));

                pst.executeUpdate();
            }

            System.out.println("Clientes cargados desde JSON");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
