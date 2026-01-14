package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ej4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:postgresql://localhost:5432/mascotas";
        String user = "postgres";
        String password = "toor";

        try {
            // Cargar driver
            Class.forName("org.postgresql.Driver");

            // Conectar
            Connection conn = DriverManager.getConnection(url, user, password);

            // Crear sentencia
            Statement stmt = conn.createStatement();

            // Ejecutar consulta
            ResultSet rs = stmt.executeQuery("SELECT * FROM mascota");

            // Mostrar resultados
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String especie = rs.getString("especie");
                String raza = rs.getString("raza");

                System.out.println(
                    "ID: " + id +
                    " | Nombre: " + nombre +
                    " | Especie: " + especie +
                    " | Raza: " + raza
                );
            }

            // Cerrar recursos
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
