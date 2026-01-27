package ejemploEx;

import java.util.Scanner;

public class MainGimnasio {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- GIMNASIO ---");
            System.out.println("1. Alta");
            System.out.println("2. Carga de datos");
            System.out.println("3. Gestión de rutinas");
            System.out.println("4. Gestión de salarios");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> AltaService.menuAlta(sc);
                case 2 -> CargaDatosService.menuCarga(sc);
                case 3 -> RutinasService.menuRutinas(sc);
                case 4 -> SalariosService.menuSalarios(sc);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción incorrecta");
            }

        } while (opcion != 0);

        sc.close();
    }
}

