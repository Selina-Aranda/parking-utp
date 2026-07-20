CREATE DATABASE IF NOT EXISTS parkingdb;
USE parkingdb;

CREATE TABLE IF NOT EXISTS alumnos (
    id_alumnos INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    carrera VARCHAR(60),
    estado ENUM('Activo','Inactivo') DEFAULT 'Activo'
);

CREATE TABLE IF NOT EXISTS tipo_vehiculo (
    id_tipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS vehiculo (
    id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    id_alumnos INT NOT NULL UNIQUE,
    id_tipo INT NOT NULL,
    otro_tipo VARCHAR(50),
    FOREIGN KEY(id_alumnos) REFERENCES alumnos(id_alumnos),
    FOREIGN KEY(id_tipo) REFERENCES tipo_vehiculo(id_tipo)
);

CREATE TABLE IF NOT EXISTS parqueadero (
    id_parqueadero INT AUTO_INCREMENT PRIMARY KEY,
    ubicacion VARCHAR(50),
    capacidad INT
);

CREATE TABLE IF NOT EXISTS espacio (
    id_espacio INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10),
    id_parqueadero INT,
    estado ENUM('Disponible','Ocupado') DEFAULT 'Disponible',
    FOREIGN KEY(id_parqueadero) REFERENCES parqueadero(id_parqueadero)
);

CREATE TABLE IF NOT EXISTS registro (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    id_vehiculo INT NOT NULL,
    id_espacio INT NOT NULL,
    fecha_ingreso DATETIME NOT NULL,
    fecha_salida DATETIME NULL,
    estado ENUM('Estacionado','Finalizado') DEFAULT 'Estacionado',
    FOREIGN KEY(id_vehiculo) REFERENCES vehiculo(id_vehiculo),
    FOREIGN KEY(id_espacio) REFERENCES espacio(id_espacio)
);

INSERT INTO alumnos
(codigo, nombres, apellidos, correo, carrera, estado)
VALUES
('U23253935','Selina Cristal','Aranda Chacon','U23253935@utp.edu.pe','Ingeniería de Software','Activo');

INSERT INTO alumnos
(codigo, nombres, apellidos, correo, carrera, estado)
VALUES
('U22302061','Ricardo Imanol','Aguilar Pumajulca','U22302061@utp.edu.pe','Ingeniería de Sistemas','Activo');

INSERT INTO tipo_vehiculo(nombre) VALUES
('Bicicleta'),
('Scooter Electrico'),
('Bicicleta Electrica'),
('Moto Electrica'),
('Otro');

INSERT INTO vehiculo (id_alumnos,id_tipo,otro_tipo) VALUES (1,1,NULL);

INSERT INTO parqueadero
(ubicacion,capacidad)
VALUES
('Zona A', 50),
('Zona B', 50);

INSERT INTO espacio (codigo, id_parqueadero, estado)
VALUES
-- Zona A
('A01',1,'Disponible'),
('A02',1,'Disponible'),
('A03',1,'Disponible'),
('A04',1,'Disponible'),
('A05',1,'Disponible'),
('A06',1,'Disponible'),
('A07',1,'Disponible'),
('A08',1,'Disponible'),
('A09',1,'Disponible'),
('A10',1,'Disponible'),
('A11',1,'Disponible'),
('A12',1,'Disponible'),
('A13',1,'Disponible'),
('A14',1,'Disponible'),
('A15',1,'Disponible'),
('A16',1,'Disponible'),
('A17',1,'Disponible'),
('A18',1,'Disponible'),
('A19',1,'Disponible'),
('A20',1,'Disponible'),
('A21',1,'Disponible'),
('A22',1,'Disponible'),
('A23',1,'Disponible'),
('A24',1,'Disponible'),
('A25',1,'Disponible'),
('A26',1,'Disponible'),
('A27',1,'Disponible'),
('A28',1,'Disponible'),
('A29',1,'Disponible'),
('A30',1,'Disponible'),
('A31',1,'Disponible'),
('A32',1,'Disponible'),
('A33',1,'Disponible'),
('A34',1,'Disponible'),
('A35',1,'Disponible'),
('A36',1,'Disponible'),
('A37',1,'Disponible'),
('A38',1,'Disponible'),
('A39',1,'Disponible'),
('A40',1,'Disponible'),
('A41',1,'Disponible'),
('A42',1,'Disponible'),
('A43',1,'Disponible'),
('A44',1,'Disponible'),
('A45',1,'Disponible'),
('A46',1,'Disponible'),
('A47',1,'Disponible'),
('A48',1,'Disponible'),
('A49',1,'Disponible'),
('A50',1,'Disponible'),

-- Zona B
('B01',2,'Disponible'),
('B02',2,'Disponible'),
('B03',2,'Disponible'),
('B04',2,'Disponible'),
('B05',2,'Disponible'),
('B06',2,'Disponible'),
('B07',2,'Disponible'),
('B08',2,'Disponible'),
('B09',2,'Disponible'),
('B10',2,'Disponible'),
('B11',2,'Disponible'),
('B12',2,'Disponible'),
('B13',2,'Disponible'),
('B14',2,'Disponible'),
('B15',2,'Disponible'),
('B16',2,'Disponible'),
('B17',2,'Disponible'),
('B18',2,'Disponible'),
('B19',2,'Disponible'),
('B20',2,'Disponible'),
('B21',2,'Disponible'),
('B22',2,'Disponible'),
('B23',2,'Disponible'),
('B24',2,'Disponible'),
('B25',2,'Disponible'),
('B26',2,'Disponible'),
('B27',2,'Disponible'),
('B28',2,'Disponible'),
('B29',2,'Disponible'),
('B30',2,'Disponible'),
('B31',2,'Disponible'),
('B32',2,'Disponible'),
('B33',2,'Disponible'),
('B34',2,'Disponible'),
('B35',2,'Disponible'),
('B36',2,'Disponible'),
('B37',2,'Disponible'),
('B38',2,'Disponible'),
('B39',2,'Disponible'),
('B40',2,'Disponible'),
('B41',2,'Disponible'),
('B42',2,'Disponible'),
('B43',2,'Disponible'),
('B44',2,'Disponible'),
('B45',2,'Disponible'),
('B46',2,'Disponible'),
('B47',2,'Disponible'),
('B48',2,'Disponible'),
('B49',2,'Disponible'),
('B50',2,'Disponible');

INSERT INTO registro
(id_vehiculo,id_espacio,fecha_ingreso,fecha_salida,estado)
VALUES
(1,10,'2026-06-28 08:15:00','2026-06-28 12:30:00','Finalizado');
