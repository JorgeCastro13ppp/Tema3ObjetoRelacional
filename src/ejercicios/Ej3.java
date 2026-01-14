package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Ej3 {

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
	            System.out.print("Nombre: ");
	            String nombre = sc.nextLine();

	            System.out.print("Especie: ");
	            String especie = sc.nextLine();

	            System.out.print("Raza: ");
	            String raza = sc.nextLine();

	            // SQL con parámetros
	            String sql = "INSERT INTO mascota (nombre, especie, raza) VALUES (?, ?, ?)";

	            PreparedStatement pst = conn.prepareStatement(sql);
	            pst.setString(1, nombre);
	            pst.setString(2, especie);
	            pst.setString(3, raza);

	            pst.executeUpdate();

	            System.out.println("Mascota insertada correctamente");

	            pst.close();
	            conn.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        sc.close();
	    }
	}


