package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Ej8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:postgresql://localhost:5432/mascotas";
        String user = "postgres";
        String password = "toor";

        Scanner sc = new Scanner(System.in);

        try {
            // Cargar driver
            Class.forName("org.postgresql.Driver");

            // Conectar
            Connection conn = DriverManager.getConnection(url, user, password);

            // Pedir datos
            System.out.print("DNI: ");
            String dni = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Número de colegiado: ");
            int numColegiado = Integer.parseInt(sc.nextLine());

            // SQL
            String sql = "INSERT INTO veterinario (dni, nombre, numcolegiado) VALUES (?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setInt(3, numColegiado);

            pst.executeUpdate();

            System.out.println("Veterinario insertado correctamente");

            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
	}

}
