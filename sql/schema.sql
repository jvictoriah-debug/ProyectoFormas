-- Esquema de la base de datos del Proyecto Formas.
-- El programa crea las tablas automáticamente al iniciar; este script es
-- para crearlas manualmente si se prefiere.

CREATE DATABASE IF NOT EXISTS formas;
USE formas;

-- Figuras planas: tipo + hasta tres medidas.
--   CIRCULO    -> medida1 = radio
--   RECTANGULO -> medida1 = base,           medida2 = altura
--   CUADRADO   -> medida1 = lado
--   ROMBO      -> medida1 = diagonal mayor, medida2 = diagonal menor
--   EQUILATERO / ISOSCELES / ESCALENO -> medida1..3 = lados
CREATE TABLE IF NOT EXISTS figura (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    tipo    VARCHAR(20) NOT NULL,
    medida1 DOUBLE NOT NULL,
    medida2 DOUBLE NULL,
    medida3 DOUBLE NULL
);

-- Cuerpos sólidos: se guardan las medidas de las figuras que los componen.
--   CUBO      -> medida1 = lado de la cara cuadrada
--   CILINDRO  -> medida1 = radio de la base, medida2 = altura
--   TETRAEDRO -> medida1 = lado de la cara equilátera
--   ESFERA    -> medida1 = radio
CREATE TABLE IF NOT EXISTS cuerpo (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    tipo    VARCHAR(20) NOT NULL,
    medida1 DOUBLE NOT NULL,
    medida2 DOUBLE NULL
);
