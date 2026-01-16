package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuEj13 {

    // ---------- DATOS DE CONEXIÓN ----------
    private static final String URL = "jdbc:postgresql://localhost:5432/mascotas";
    private static final String USER = "postgres";
    private static final String PASSWORD = "toor";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja");
            System.out.println("4. Aplicar vacuna");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> menuAlta(sc);
                case 2 -> menuModificar(sc);
                case 3 -> menuBaja(sc);
                case 4 -> aplicarVacuna(sc);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        sc.close();
    }

    // ---------- CONEXIÓN ----------
    private static Connection getConexion() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ---------- SUBMENÚ ALTA ----------
    public static void menuAlta(Scanner sc) {

        System.out.println("\n--- ALTA ---");
        System.out.println("1. Veterinario");
        System.out.println("2. Propietario");
        System.out.println("3. Mascota");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> insertarVeterinario(sc);
            case 2 -> insertarPropietario(sc);
            case 3 -> insertarMascota(sc);
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción incorrecta");
        }
    }
    
    public static void menuBaja(Scanner sc) {

        System.out.println("\n--- BAJA ---");
        System.out.println("1. Mascota");
        System.out.println("2. Propietario");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> bajaMascota(sc);
            case 2 -> bajaPropietario(sc);
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción incorrecta");
        }
    }
    
    public static void menuModificar(Scanner sc) {

        System.out.println("\n--- MODIFICAR ---");
        System.out.println("1. Propietario (dirección)");
        System.out.println("2. Mascota (datos básicos)");
        System.out.println("0. Volver");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> modificarPropietario(sc);
            case 2 -> modificarMascota(sc);
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción incorrecta");
        }
    }



    // ---------- EJERCICIO 8 ----------
    public static void insertarVeterinario(Scanner sc) {
        try (Connection conn = getConexion()) {

            System.out.print("DNI: ");
            String dni = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Número de colegiado: ");
            int numColegiado = Integer.parseInt(sc.nextLine());

            String sql = "INSERT INTO veterinario (dni, nombre, numcolegiado) VALUES (?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setInt(3, numColegiado);

            pst.executeUpdate();
            System.out.println("Veterinario insertado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- EJERCICIO 10 ----------
    public static void insertarPropietario(Scanner sc) {
        try (Connection conn = getConexion()) {

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

            String direccion = "('" + calle + "', " + numero + ", '" + poblacion + "', '" + cp + "')";

            String sql = "INSERT INTO propietario (dni, nombre, direccion) VALUES (?, ?, " + direccion + ")";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);

            pst.executeUpdate();
            System.out.println("Propietario insertado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- EJERCICIO 12 ----------
    public static void insertarMascota(Scanner sc) {
        try (Connection conn = getConexion()) {

            System.out.print("Nombre mascota: ");
            String nombre = sc.nextLine();

            System.out.print("Especie: ");
            String especie = sc.nextLine();

            System.out.print("Raza: ");
            String raza = sc.nextLine();

            System.out.print("DNI propietario: ");
            String dniPropietario = sc.nextLine();

            // Vacuna
            System.out.print("Nombre vacuna: ");
            String vacuna = sc.nextLine();

            System.out.print("Num colegiado: ");
            int colegiado = Integer.parseInt(sc.nextLine());

            LocalDate fecha = null;
            while (fecha == null) {
                System.out.print("Fecha vacuna (YYYY-MM-DD): ");
                try {
                    fecha = LocalDate.parse(sc.nextLine());
                } catch (DateTimeParseException e) {
                    System.out.println("Formato incorrecto");
                }
            }

            String vacunas = "ARRAY[('" + vacuna + "', " + colegiado + ", '" + fecha + "')]::cartilla[]";

            String sql = "INSERT INTO mascota (nombre, especie, raza, dni_propietario, vacuna) "
                       + "VALUES (?, ?, ?, ?, " + vacunas + ")";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, especie);
            pst.setString(3, raza);
            pst.setString(4, dniPropietario);

            pst.executeUpdate();
            System.out.println("Mascota insertada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void bajaMascota(Scanner sc) {

        try (Connection conn = getConexion()) {

            System.out.print("ID de la mascota a borrar: ");
            int id = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM mascota WHERE id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            int filas = pst.executeUpdate();

            if (filas > 0) {
                System.out.println("Mascota eliminada correctamente");
            } else {
                System.out.println("No existe ninguna mascota con ese ID");
            }

            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void bajaPropietario(Scanner sc) {

        try (Connection conn = getConexion()) {

            System.out.print("DNI del propietario a borrar: ");
            String dni = sc.nextLine();

            // 1. Borrar mascotas del propietario
            String sqlMascotas = "DELETE FROM mascota WHERE dni_propietario = ?";
            PreparedStatement pstMascotas = conn.prepareStatement(sqlMascotas);
            pstMascotas.setString(1, dni);
            pstMascotas.executeUpdate();

            // 2. Borrar propietario
            String sqlProp = "DELETE FROM propietario WHERE dni = ?";
            PreparedStatement pstProp = conn.prepareStatement(sqlProp);
            pstProp.setString(1, dni);

            int filas = pstProp.executeUpdate();

            if (filas > 0) {
                System.out.println("Propietario y sus mascotas eliminados");
            } else {
                System.out.println("No existe ningún propietario con ese DNI");
            }

            pstMascotas.close();
            pstProp.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modificarPropietario(Scanner sc) {

        try (Connection conn = getConexion()) {

            System.out.print("DNI del propietario: ");
            String dni = sc.nextLine();

            System.out.print("Nueva calle: ");
            String calle = sc.nextLine();

            System.out.print("Nuevo número: ");
            int numero = Integer.parseInt(sc.nextLine());

            System.out.print("Nueva población: ");
            String poblacion = sc.nextLine();

            System.out.print("Nuevo CP: ");
            String cp = sc.nextLine();

            String direccion = "('" + calle + "', " + numero + ", '" + poblacion + "', '" + cp + "')";

            String sql = "UPDATE propietario SET direccion = " + direccion + " WHERE dni = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dni);

            int filas = pst.executeUpdate();

            if (filas > 0) {
                System.out.println("Dirección del propietario actualizada");
            } else {
                System.out.println("No existe ningún propietario con ese DNI");
            }

            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void modificarMascota(Scanner sc) {

        try (Connection conn = getConexion()) {

            System.out.print("ID de la mascota: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Nueva especie: ");
            String especie = sc.nextLine();

            System.out.print("Nueva raza: ");
            String raza = sc.nextLine();

            String sql = "UPDATE mascota SET nombre = ?, especie = ?, raza = ? WHERE id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, especie);
            pst.setString(3, raza);
            pst.setInt(4, id);

            int filas = pst.executeUpdate();

            if (filas > 0) {
                System.out.println("Mascota actualizada correctamente");
            } else {
                System.out.println("No existe ninguna mascota con ese ID");
            }

            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    public static void aplicarVacuna(Scanner sc) {

        try (Connection conn = getConexion()) {

            System.out.print("ID de la mascota: ");
            int idMascota = Integer.parseInt(sc.nextLine());

            System.out.print("Nombre vacuna: ");
            String nombreVacuna = sc.nextLine();

            System.out.print("Número de colegiado veterinario: ");
            int colegiado = Integer.parseInt(sc.nextLine());

            LocalDate fecha = null;
            while (fecha == null) {
                System.out.print("Fecha vacuna (YYYY-MM-DD): ");
                try {
                    fecha = LocalDate.parse(sc.nextLine());
                } catch (DateTimeParseException e) {
                    System.out.println("Formato incorrecto");
                }
            }

            // Construimos la vacuna como tipo compuesto
            String vacuna = "('" + nombreVacuna + "', " + colegiado + ", '" + fecha + "')::cartilla";

            String sql = "UPDATE mascota "
                       + "SET vacuna = array_append(vacuna, " + vacuna + ") "
                       + "WHERE id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idMascota);

            int filas = pst.executeUpdate();

            if (filas > 0) {
                System.out.println("Vacuna aplicada correctamente");
            } else {
                System.out.println("No existe ninguna mascota con ese ID");
            }

            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
