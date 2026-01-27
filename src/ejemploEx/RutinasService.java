package ejemploEx;

import java.sql.*;
import java.util.Scanner;

public class RutinasService {

    public static void menuRutinas(Scanner sc) {

        System.out.println("\n--- RUTINAS ---");
        System.out.println("1. Añadir rutina");
        System.out.println("2. Total rutinas cliente");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> anadirRutina(sc);
            case 2 -> totalRutinas(sc);
            default -> System.out.println("Opción incorrecta");
        }
    }

    private static void anadirRutina(Scanner sc) {

        try (Connection conn = DBUtil.getConexion()) {

            System.out.print("DNI cliente: ");
            String dni = sc.nextLine();
            System.out.print("Rutina: ");
            String rutina = sc.nextLine();

            String sql = """
                UPDATE cliente
                SET rutinas = array_append(rutinas, ?)
                WHERE dni = ?
            """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, rutina);
            pst.setString(2, dni);

            pst.executeUpdate();
            System.out.println("Rutina añadida");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void totalRutinas(Scanner sc) {

        try (Connection conn = DBUtil.getConexion()) {

            System.out.print("DNI cliente: ");
            String dni = sc.nextLine();

            String sql = "SELECT total_rutinas(?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Total rutinas: " + rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
