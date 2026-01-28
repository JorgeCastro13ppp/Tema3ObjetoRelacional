package ejemploEx;

/*
 * Importamos las clases necesarias para trabajar con JDBC
 * y con la entrada de datos por teclado.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*
 * Clase AltaService.
 *
 * Esta clase se encarga exclusivamente de las operaciones
 * de ALTA (inserción de datos) en la base de datos:
 *  - Alta de clientes
 *  - Alta de entrenadores
 *
 * No contiene menú principal ni conexión directa,
 * delega la conexión en DBUtil.
 */
public class AltaService {

    /*
     * Método que muestra el submenú de altas.
     * Recibe un Scanner para reutilizar el mismo
     * objeto Scanner creado en el main.
     */
    public static void menuAlta(Scanner sc) {

        // Mostramos el menú de altas
        System.out.println("\n--- ALTA ---");
        System.out.println("1. Alta cliente");
        System.out.println("2. Alta entrenador");
        System.out.print("Opción: ");

        /*
         * Leemos la opción introducida por el usuario.
         * Se usa nextLine() para evitar problemas con el buffer.
         */
        int opcion = Integer.parseInt(sc.nextLine());

        /*
         * Switch moderno (Java 14+):
         * Según la opción seleccionada, se llama
         * al método correspondiente.
         */
        switch (opcion) {

            // Alta de cliente
            case 1 -> altaCliente(sc);

            // Alta de entrenador
            case 2 -> altaEntrenador(sc);

            // Cualquier otro valor
            default -> System.out.println("Opción incorrecta");
        }
    }

    /*
     * Método privado para dar de alta un cliente.
     *
     * Es privado porque solo se utiliza dentro
     * de esta clase (encapsulación).
     */
    private static void altaCliente(Scanner sc) {

        /*
         * try-with-resources:
         * - Obtiene la conexión desde DBUtil.
         * - Garantiza que la conexión se cierre automáticamente.
         */
        try (Connection conn = DBUtil.getConexion()) {

            // Pedimos los datos del cliente por consola
            System.out.print("DNI: ");
            String dni = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine();

            System.out.print("Número cliente: ");
            int num = Integer.parseInt(sc.nextLine());

            /*
             * Sentencia SQL de inserción.
             * Se utilizan ? como marcadores de parámetros
             * para evitar inyección SQL.
             *
             * El campo rutinas es un array de texto,
             * por lo que se inicializa vacío.
             */
            String sql = """
                INSERT INTO cliente (dni, nombre, apellidos, numerocliente, rutinas)
                VALUES (?, ?, ?, ?, ?)
            """;

            // Preparamos la sentencia SQL
            PreparedStatement pst = conn.prepareStatement(sql);

            // Asignamos los valores a cada parámetro
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setString(3, apellidos);
            pst.setInt(4, num);

            /*
             * Creamos un array vacío de tipo TEXT
             * para inicializar el campo rutinas.
             * String[] rutinas = {};
				pst.setArray(5, conn.createArrayOf("text", rutinas));
				
				ARRAY DE TIPO COMPUESTO 
				Object[] salarios = {...};
				pst.setArray(1, conn.createArrayOf("salario", salarios));

             */
            pst.setArray(5, conn.createArrayOf("text", new String[]{}));

            // Ejecutamos la inserción
            pst.executeUpdate();

            System.out.println("Cliente insertado");

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error SQL
            e.printStackTrace();
        }
    }

    /*
     * Método privado para dar de alta un entrenador.
     *
     * Inserta un registro en la tabla entrenador,
     * incluyendo un tipo compuesto (salario).
     */
    private static void altaEntrenador(Scanner sc) {

        /*
         * try-with-resources para gestionar automáticamente
         * el cierre de la conexión.
         */
        try (Connection conn = DBUtil.getConexion()) {

            // Pedimos los datos del entrenador
            System.out.print("DNI: ");
            String dni = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine();

            System.out.print("Especialidad: ");
            String esp = sc.nextLine();

            System.out.print("Cuota base: ");
            int cuota = Integer.parseInt(sc.nextLine());

            System.out.print("Comisión: ");
            double com = Double.parseDouble(sc.nextLine());

            /*
             * Inserción en la tabla entrenador.
             *
             * El campo salario es un tipo compuesto,
             * por lo que se inserta usando ROW(cuota, comision).
             * 
             * SI HUBIERA MÁS DE UN TIPO COMPUESTO (EJEMPLO)
             * CREATE TABLE entrenador (
			    dni TEXT,
			    salario salario,
			    direccion direccion
				);

				INSERT INTO entrenador (dni, salario, direccion)
				VALUES (?, ROW(?, ?), ROW(?, ?, ?, ?));

             */
            String sql = """
                INSERT INTO entrenador (dni, nombre, apellidos, especialidad, salario)
                VALUES (?, ?, ?, ?, ROW(?, ?))
            """;

            // Preparamos la sentencia
            PreparedStatement pst = conn.prepareStatement(sql);

            // Asignamos los valores a los parámetros
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setString(3, apellidos);
            pst.setString(4, esp);
            pst.setInt(5, cuota);
            pst.setDouble(6, com);

            // Ejecutamos la inserción
            pst.executeUpdate();

            System.out.println("Entrenador insertado");

        } catch (SQLException e) {
            // Capturamos y mostramos errores SQL
            e.printStackTrace();
        }
    }
}
