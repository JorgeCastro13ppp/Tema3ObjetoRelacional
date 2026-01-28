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
