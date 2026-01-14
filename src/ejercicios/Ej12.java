package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/*
 * En PostgreSQL, los arrays de tipos compuestos se insertan construyendo la estructura completa en SQL,
 * ya que JDBC no permite mapear directamente tipos compuestos complejos.
 */
public class Ej12 {

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

            // 3. Datos de la mascota
            System.out.print("Nombre mascota: ");
            String nombre = sc.nextLine();

            System.out.print("Especie: ");
            String especie = sc.nextLine();

            System.out.print("Raza: ");
            String raza = sc.nextLine();

            System.out.print("DNI propietario: ");
            String dniPropietario = sc.nextLine();

            // ---------- VACUNA 1 ----------
            System.out.print("Nombre vacuna 1: ");
            String vacuna1 = sc.nextLine();

            System.out.print("Num colegiado veterinario vacuna 1: ");
            int col1 = Integer.parseInt(sc.nextLine());

            LocalDate fechaVacuna1 = null;
            boolean fechaCorrecta1 = false;

            while (!fechaCorrecta1) {
                System.out.print("Fecha vacuna 1 (YYYY-MM-DD): ");
                String fechaTexto = sc.nextLine();
                try {
                    fechaVacuna1 = LocalDate.parse(fechaTexto);
                    fechaCorrecta1 = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha incorrecta. Formato válido: YYYY-MM-DD");
                }
            }

            // ---------- VACUNA 2 ----------
            System.out.print("Nombre vacuna 2: ");
            String vacuna2 = sc.nextLine();

            System.out.print("Num colegiado veterinario vacuna 2: ");
            int col2 = Integer.parseInt(sc.nextLine());

            LocalDate fechaVacuna2 = null;
            boolean fechaCorrecta2 = false;

            while (!fechaCorrecta2) {
                System.out.print("Fecha vacuna 2 (YYYY-MM-DD): ");
                String fechaTexto = sc.nextLine();
                try {
                    fechaVacuna2 = LocalDate.parse(fechaTexto);
                    fechaCorrecta2 = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha incorrecta. Formato válido: YYYY-MM-DD");
                }
            }

            // 4. Construir ARRAY de tipo compuesto cartilla
            String vacunas = "ARRAY["
                    + "('" + vacuna1 + "', " + col1 + ", '" + fechaVacuna1 + "'),"
                    + "('" + vacuna2 + "', " + col2 + ", '" + fechaVacuna2 + "')"
                    + "]::cartilla[]";

            // 5. SQL
            String sql = "INSERT INTO mascota (nombre, especie, raza, dni_propietario, vacuna) "
                       + "VALUES (?, ?, ?, ?, " + vacunas + ")";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, especie);
            pst.setString(3, raza);
            pst.setString(4, dniPropietario);

            // 6. Ejecutar
            pst.executeUpdate();

            System.out.println(" Mascota con vacunas insertada correctamente");

            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
	}

}
