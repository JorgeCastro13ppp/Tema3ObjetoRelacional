package ejercicios;

public class Ej11 {
	
	//AÑADIR EL DNI DEL PROPIETARIO
	
	/*ALTER TABLE mascota
ADD COLUMN dni_propietario VARCHAR(9);
*/
	
	//AÑADIR LA COLUMNA VACUNA COMO ARRAY COMPUESTO
	
	/*ALTER TABLE mascota
ADD COLUMN vacuna cartilla[];
*/
	
	//INSERTAR DATOS
	
	/*INSERT INTO mascota (nombre, especie, raza, vacuna)
VALUES
('Max', 'Perro', 'Labrador',
 ARRAY[
   ('Rabia', 1234, '2024-03-15'),
   ('Leucemia', 12346, '2024-05-20')
 ]::cartilla_vacunacion[]
),
('Luna', 'Gato', 'Persa',
 ARRAY[
   ('Leucemia', 67890, '2024-06-10')
 ]::cartilla[]
),
('Rex', 'Perro', 'Pastor Alemán',
 ARRAY[
   ('Parvovirus', 1234, '2024-08-22')
 ]::cartilla[]
);
*/
	
	//VER TODAS LAS VACUNAS
	
	/*SELECT nombre, vacuna
FROM mascota;
*/
	//VER LA PRIMERA VACUNA DE CADA MASCOTA
	
	/*SELECT nombre, vacuna[1]
FROM mascota;
*/
	
	//ACCEDER A UN CAMPO INTERNO DEL TIPO COMPUESTO
	
	/*SELECT nombre, (vacuna[1]).nombre_vacuna
FROM mascota;
*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
