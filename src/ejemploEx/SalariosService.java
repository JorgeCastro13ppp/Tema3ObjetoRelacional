package ejemploEx;


import java.sql.*;
import java.util.Scanner;

public class SalariosService {

    public static void menuSalarios(Scanner sc) {

        System.out.println("\n--- SALARIOS ---");
        System.out.println("1. Calcular salario entrenador");
        System.out.println("2. Subir cuota base 2%");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> calcularSalario(sc);
            case 2 -> subirCuota();
            default -> System.out.println("Opción incorrecta");
        }
    }

    private static void calcularSalario(Scanner sc) {

        try (Connection conn = DBUtil.getConexion()) {

            System.out.print("DNI entrenador: ");
            String dni = sc.nextLine();

            String sql = """
                SELECT (salario).cuota_base + (salario).comision
                FROM entrenador
                WHERE dni = ?
            """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Salario total: " + rs.getDouble(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void subirCuota() {

        try (Connection conn = DBUtil.getConexion();
             Statement st = conn.createStatement()) {

            st.execute("SELECT subir_cuota()");
            System.out.println("Cuota base aumentada un 2%");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

