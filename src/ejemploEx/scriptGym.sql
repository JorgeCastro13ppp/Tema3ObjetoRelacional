-- Crea la base de datos donde se almacenará toda la información
-- del sistema de gestión del gimnasio
CREATE DATABASE bdgym_jorge;


-- Tipo compuesto salario
-- Representa una estructura con varios campos relacionados
-- que se tratarán como una sola unidad lógica
CREATE TYPE salario AS (
    cuota_base INTEGER,   -- Salario fijo del entrenador
    comision DECIMAL      -- Comisión variable
);


-- Tabla base persona
-- Contiene los datos comunes a clientes y entrenadores
CREATE TABLE persona (
    dni TEXT PRIMARY KEY, -- Clave primaria
    nombre TEXT,
    apellidos TEXT
);


-- Tabla entrenador
-- Hereda los campos de persona (dni, nombre, apellidos)
-- y añade campos específicos
CREATE TABLE entrenador (
    especialidad TEXT, -- Tipo de entrenamiento
    salario salario    -- Tipo compuesto
) INHERITS (persona);


-- Tabla cliente
-- Hereda de persona y añade campos propios
CREATE TABLE cliente (
    numerocliente INTEGER, -- Identificador interno
    rutinas TEXT[]         -- Array de rutinas asignadas
) INHERITS (persona);


-- Función que devuelve el número total de rutinas
-- asociadas a un cliente concreto
CREATE OR REPLACE FUNCTION total_rutinas(p_dni TEXT)
RETURNS INTEGER AS $$
BEGIN
    RETURN (
        SELECT COALESCE(array_length(rutinas, 1), 0)
        FROM cliente
        WHERE dni = p_dni
    );
END;
$$ LANGUAGE plpgsql;


-- Función que incrementa la cuota base
-- de todos los entrenadores en un 2%
CREATE OR REPLACE FUNCTION subir_cuota()
RETURNS VOID AS $$
BEGIN
    UPDATE entrenador
    SET salario = (
        (salario).cuota_base * 1.02, -- nuevo valor
        (salario).comision            -- se mantiene
    );
END;
$$ LANGUAGE plpgsql;

-- Ejemplo de procedimiento para añadir una rutina (NO EN USO)
CREATE OR REPLACE PROCEDURE añadir_rutina(
    p_dni TEXT,
    p_rutina TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE cliente
    SET rutinas = array_append(rutinas, p_rutina)
    WHERE dni = p_dni;
END;
$$;

-- Repasemos las consultas de selección
SELECT * FROM persona;
-- Selecciones únicas
SELECT DISTINCT edad FROM personas;
-- Filtrar
SELECT * FROM persona WHERE población=’Millanes’;
SELECT * FROM persona WHERE población=’Millanes’ AND edad > 30;
SELECT * FROM personas WHERE edad BETWEEN 20 AND 30;
-- Búsqueda parcial
SELECT * FROM persona WHERE población LIKE ’%vera%’;
-- Ordenar
SELECT * FROM persona ORDER BY nombre ASC;
-- Limitar el resultado
SELECT * FROM persona ORDER BY edad ASC LIMIT 5;
-- Consultas de agregación (SUM, AVG …)
SELECT COUNT(*) FROM persona;
Combinar datos, Join
SELECT nombre FROM propietario
INNER JOIN veterinario ON propietario.dni = veterinario.dni;
-- Subconsultas
SELECT nombre, salario FROM empleado
WHERE salario = (SELECT MAX(salario) FROM empleado);
SELECT nombre, salario FROM empleado
WHERE salario IN (SELECT salario FROM empleados WHERE puesto =
'Desarrollador');
-- Con arrays
SELECT nombre, telefonos[1] AS telefono_principal
FROM persona;
SELECT nombre, array_length(telefonos, 1) AS num_telefonos
FROM persona;
-- Con datos de tipo compuesto o estructurado
SELECT nombre
FROM persona
WHERE direccion.poblacion = 'Saucedilla';
SELECT nombre, direccion.calle, telefonos[1] AS primer_telefono
FROM persona;

--  Procedimiento
CREATE OR REPLACE PROCEDURE nombre ( IN | OUT | INOUT
parametro TIPO )
-- Función
CREATE OR REPLACE FUNCTION nombre ( IN | OUT | INOUT
parametro TIPO )RETURNS tipo
-- Función: SELECT nombre();
-- Procedimiento: CALL nombre();
