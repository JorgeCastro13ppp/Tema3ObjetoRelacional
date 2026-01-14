package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Ej10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String url = "jdbc:postgresql://localhost:5432/mascotas";
	        String user = "postgres";
	        String password = "toor";

	        Scanner sc = new Scanner(System.in);

	        try {
	            // 1. Cargar driver
	            Class.forName("org.postgresql.Driver");

	            // 2. Conectar
	            Connection conn = DriverManager.getConnection(url, user, password);

	            // 3. Pedir datos
	            System.out.print("DNI: ");
	            String dni = sc.nextLine();

	            System.out.print("Nombre: ");
	            String nombre = sc.nextLine();

	            System.out.print("Calle: ");
	            String calle = sc.nextLine();

	            System.out.print("Número: ");
	            int numero = Integer.parseInt(sc.nextLine());

	            System.out.print("Población: ");
	            String poblacion = sc.nextLine();

	            System.out.print("CP: ");
	            String cp = sc.nextLine();

	            // 4. Construir el tipo compuesto direccion
	            String direccion = "('" + calle + "', " + numero + ", '" + poblacion + "', '" + cp + "')";

	            // 5. SQL
	            String sql = "INSERT INTO propietario (dni, nombre, direccion) VALUES (?, ?, " + direccion + ")";

	            PreparedStatement pst = conn.prepareStatement(sql);
	            pst.setString(1, dni);
	            pst.setString(2, nombre);

	            // 6. Ejecutar
	            pst.executeUpdate();

	            System.out.println("Propietario insertado correctamente");

	            pst.close();
	            conn.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        sc.close();
	}

}
