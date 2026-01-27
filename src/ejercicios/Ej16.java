package ejercicios;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ej16 {

    // -------- DATOS DE CONEXIÓN --------
    private static final String URL = "jdbc:postgresql://localhost:5432/mascotas";
    private static final String USER = "postgres";
    private static final String PASSWORD = "toor";

    /*
     * FUNCIONES EN POSTGRESQL
     *
     * CREATE OR REPLACE FUNCTION total_mascotas()
     * RETURNS INTEGER AS $$
     * BEGIN
     *     RETURN (SELECT COUNT(*) FROM mascota);
     * END;
     * $$ LANGUAGE plpgsql;
     *
     *
     * CREATE OR REPLACE FUNCTION poner_vacuna(
     *     p_id INTEGER,
     *     p_nombre_vacuna TEXT,
     *     p_num_colegiado INTEGER,
     *     p_fecha DATE
     * )
     * RETURNS VOID AS $$
     * BEGIN
     *     UPDATE mascota
     *     SET vacuna = array_append(
     *         vacuna,
     *         ROW(p_nombre_vacuna, p_num_colegiado, p_fecha)::cartilla
     *     )
     *     WHERE id = p_id;
     * END;
     * $$ LANGUAGE plpgsql;
     * 
     * C:\Windows\system32>"C:\Program Files\PostgreSQL\17\bin\pg_dump.exe" ^
		 -U postgres ^
		 -F c ^
		 -d mascotas ^
		 -f C:\backup_mascotas.backup
		
		C:\Windows\system32>"C:\Program Files\PostgreSQL\17\bin\pg_dump.exe" -U postgres -d mascotas > C:\backup_mascotas.sql
		Contraseña:

     * 
     */

    // -------- MAIN CON MENÚ --------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- EJERCICIO 16 ---");
            System.out.println("1. Mostrar número total de mascotas");
            System.out.println("2. Poner vacuna a una mascota");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> mostrarTotalMascotas();
                case 2 -> ponerVacuna(sc);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        sc.close();
    }

    // -------- CONEXIÓN --------
    private static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // -------- FUNCIÓN 1: TOTAL DE MASCOTAS --------
    private static void mostrarTotalMascotas() {

        String sql = "SELECT total_mascotas()";

        try (Connection conn = getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                System.out.println("Número total de mascotas: "
                        + rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------- FUNCIÓN 2: PONER VACUNA --------
    private static void ponerVacuna(Scanner sc) {

        System.out.print("ID de la mascota: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nombre vacuna: ");
        String nombreVacuna = sc.nextLine();

        System.out.print("Número de colegiado: ");
        int colegiado = Integer.parseInt(sc.nextLine());

        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = sc.nextLine();

        String sql = "SELECT poner_vacuna(?, ?, ?, ?)";

        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.setString(2, nombreVacuna);
            pst.setInt(3, colegiado);
            pst.setDate(4, Date.valueOf(fecha));

            pst.execute();
            System.out.println("Vacuna aplicada correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
