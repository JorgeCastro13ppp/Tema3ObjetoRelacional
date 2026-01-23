package ejercicios;

import java.sql.*;
import java.util.Scanner;

public class Ej15 {

    private static final String URL = "jdbc:postgresql://localhost:5432/mascotas";
    private static final String USER = "postgres";
    private static final String PASSWORD = "toor";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CONSULTAS ---");
            System.out.println("1. Listar mascotas con sus vacunas");
            System.out.println("2. Mascotas de un propietario (DNI)");
            System.out.println("3. Perros vacunados de moquillo");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> listarMascotasVacunas();
                case 2 -> mascotasPorPropietario(sc);
                case 3 -> perrosVacunadosMoquillo();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        sc.close();
    }

    private static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private static void listarMascotasVacunas() {

        String sql = """
            SELECT m.nombre, m.especie,
                   v.nombre_vacuna, v.numero_colegiado, v.fecha_vacunacion
            FROM mascota m,
                 unnest(m.vacuna) AS v
        """;

        try (Connection conn = getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getString("nombre") + " (" +
                        rs.getString("especie") + ") -> " +
                        rs.getString("nombre_vacuna") + " | " +
                        rs.getDate("fecha_vacunacion")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void mascotasPorPropietario(Scanner sc) {

        System.out.print("DNI del propietario: ");
        String dni = sc.nextLine();

        String sql = "SELECT nombre, especie FROM mascota WHERE dni_propietario = ?";

        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, dni);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString("nombre") + " - " +
                        rs.getString("especie")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*SELECT DISTINCT m.nombre
FROM mascota m
JOIN unnest(m.vacuna) AS v ON true
WHERE LOWER(m.especie) LIKE '%perro%'
AND LOWER(v.nombre_vacuna) LIKE '%moquillo%';
*/
    
    private static void perrosVacunadosMoquillo() {

        String sql = """
            SELECT DISTINCT m.nombre
            FROM mascota m
            JOIN unnest(m.vacuna) AS v ON true
            WHERE LOWER(m.especie) LIKE '%perro%'
            AND LOWER(v.nombre_vacuna) LIKE '%moquillo%'
        """;

        try (Connection conn = getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean hayResultados = false;

            while (rs.next()) {
                hayResultados = true;
                System.out.println("Perro vacunado de moquillo: "
                        + rs.getString("nombre"));
            }

            if (!hayResultados) {
                System.out.println("No hay perros vacunados de moquillo.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

    


