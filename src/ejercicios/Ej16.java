package ejercicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ej16 {
	/*
	 * CREATE OR REPLACE FUNCTION total_mascotas()
RETURNS INTEGER AS $$
BEGIN
    RETURN (SELECT COUNT(*) FROM mascota);
END;
$$ LANGUAGE plpgsql;

	 * */
	
	/*
	 * CREATE OR REPLACE FUNCTION poner_vacuna(
    p_id INTEGER,
    p_nombre_vacuna TEXT,
    p_num_colegiado INTEGER,
    p_fecha DATE
)
RETURNS VOID AS $$
BEGIN
    UPDATE mascota
    SET vacuna = array_append(
        vacuna,
        ROW(p_nombre_vacuna, p_num_colegiado, p_fecha)::cartilla
    )
    WHERE id = p_id;
END;
$$ LANGUAGE plpgsql;

SELECT poner_vacuna(1, 'Rabia', 999, '2025-01-10');

	 * */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
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
