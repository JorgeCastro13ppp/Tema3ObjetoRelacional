package ejemploEx;

import java.util.Scanner;

public class CargaDatosService {

    public static void menuCarga(Scanner sc) {

        System.out.println("\n--- CARGA DE DATOS ---");
        System.out.println("1. Cargar clientes (JSON)");
        System.out.println("2. Cargar entrenadores (TXT)");
        System.out.print("Opción: ");

        int opcion = Integer.parseInt(sc.nextLine());

        switch (opcion) {
            case 1 -> System.out.println("Carga JSON (simulada)");
            case 2 -> System.out.println("Carga TXT (simulada)");
            default -> System.out.println("Opción incorrecta");
        }
    }
}

