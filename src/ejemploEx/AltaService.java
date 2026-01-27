package ejemploEx;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AltaService {

    public static void menuAlta(Scanner sc) {

        System.out.println("\n--- ALTA ---");
        System.out.println("1. Alta cliente");
        System.out.println("2. Alta entrenador");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> altaCliente(sc);
            case 2 -> altaEntrenador(sc);
            default -> System.out.println("Opción incorrecta");
        }
    }

    private static void altaCliente(Scanner sc) {

        try (Connection conn = DBUtil.getConexion()) {

            System.out.print("DNI: ");
            String dni = sc.nextLine();
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine();
            System.out.print("Número cliente: ");
            int num = Integer.parseInt(sc.nextLine());

            String sql = """
                INSERT INTO cliente (dni, nombre, apellidos, numerocliente, rutinas)
                VALUES (?, ?, ?, ?, ?)
            """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setString(3, apellidos);
            pst.setInt(4, num);
            pst.setArray(5, conn.createArrayOf("text", new String[]{}));

            pst.executeUpdate();
            System.out.println("Cliente insertado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void altaEntrenador(Scanner sc) {

        try (Connection conn = DBUtil.getConexion()) {

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

            String sql = """
                INSERT INTO entrenador (dni, nombre, apellidos, especialidad, salario)
                VALUES (?, ?, ?, ?, ROW(?, ?))
            """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setString(3, apellidos);
            pst.setString(4, esp);
            pst.setInt(5, cuota);
            pst.setDouble(6, com);

            pst.executeUpdate();
            System.out.println("Entrenador insertado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

