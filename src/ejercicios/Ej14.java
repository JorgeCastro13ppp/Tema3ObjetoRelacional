package ejercicios;

public class Ej14 {

	/*
	 * -- Tabla con array bidimensional de tipo compuesto
CREATE TABLE mascota2 (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    especie VARCHAR(50),
    raza VARCHAR(50),
    propietario_dni VARCHAR(9),
    vacunas cartilla[][]
);

-- Inserción correcta: TODAS las filas internas tienen el mismo número de elementos
INSERT INTO mascota2 (nombre, especie, raza, vacunas)
VALUES (
    'Max',
    'Perro',
    'Labrador',
    ARRAY[
        ARRAY[
            -- Año 1: dos vacunas
            ROW('Rabia', 12345, '2021-03-15')::cartilla,
            ROW('Moquillo', 12345, '2021-03-30')::cartilla
        ],
        ARRAY[
            -- Año 2: dos vacunas (IMPORTANTE: mismo número que el año 1)
            ROW('Parvovirus', 12346, '2022-04-12')::cartilla,
            ROW('Leptospirosis', 12346, '2022-05-10')::cartilla
        ]
    ]
);

*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
