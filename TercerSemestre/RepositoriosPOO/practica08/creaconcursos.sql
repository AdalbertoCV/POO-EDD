DROP DATABASE IF EXISTS controlconcursos;
CREATE DATABASE controlconcursos DEFAULT COLLATE 'utf8_spanish_ci';
DROP USER IF EXISTS 'IngSW'@'localhost';
CREATE USER 'IngSW'@'localhost' IDENTIFIED BY 'UAZsw2021';
GRANT ALL ON controlconcursos.* TO 'IngSW'@'localhost';

USE controlconcursos;

CREATE TABLE IF NOT EXISTS municipio (
	id_municipio INT PRIMARY KEY,
	nombre_municipio VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS entidad  (
	id_entidad SMALLINT PRIMARY KEY,
	nombre_entidad VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS concurso (
	id_concurso INT PRIMARY KEY AUTO_INCREMENT,
	nombre_concurso VARCHAR(100) NOT NULL,
	fecha_concurso DATE NOT NULL,
	fecha_inicio_registro DATE NOT NULL,
	fecha_fin_registro DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS institucion (
	id_institucion INT PRIMARY KEY AUTO_INCREMENT,
	nombre_institucion VARCHAR(100) NOT NULL,
	nombre_corto_institucion VARCHAR(20) NOT NULL,
	url_institucion VARCHAR(200) NOT NULL,
	calle_num_institucion VARCHAR (50) NOT NULL,
	colonia_institucion VARCHAR(50),
	id_municipio_institucion INT NOT NULL,
	id_entidad_institucion SMALLINT NOT NULL,
	codpostal_institucion CHAR(5),
	telefono_institucion CHAR(10)
);

CREATE TABLE IF NOT EXISTS persona (
	email_persona CHAR(40) PRIMARY KEY,
	nombre_persona VARCHAR(30) NOT NULL,
	apellidos_persona VARCHAR(40) NOT NULL,
	sexo_persona ENUM ('M','F') NOT NULL,
	calle_num_persona VARCHAR (50) NOT NULL,
	colonia_persona VARCHAR(50),
	id_municipio_persona INT NOT NULL,
	id_entidad_persona SMALLINT NOT NULL,
	codpostal_persona CHAR(5),
	telefono_persona CHAR(10) NOT NULL,
	fecha_nac_persona DATE NOT NULL,
	id_institucion_persona INT NOT NULL,
	tipo_persona ENUM ("Estudiante","Profesor") NOT NULL,
	INDEX (id_institucion_persona)
);

CREATE TABLE IF NOT EXISTS datos_estudiante (
	email_estudiante CHAR(40) PRIMARY KEY,
	carrera_estudiante CHAR(50) NOT NULL,
	fecha_inicio_carrera DATE NOT NULL,
	fecha_esperada_terminacion DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS equipo (
	id_equipo INT PRIMARY KEY AUTO_INCREMENT,
	nombre_equipo VARCHAR(40) NOT NULL,
	email_coach CHAR(40) NOT NULL,
	email_concursante1 CHAR(40) NOT NULL,
	email_concursante2 CHAR(40) NOT NULL,
	email_concursante3 CHAR(40) NOT NULL,
	email_concursante_reserva CHAR(40),
	id_institucion_equipo INT NOT NULL,
	INDEX (id_institucion_equipo)	
);

CREATE TABLE IF NOT EXISTS sede (
	id_sede INT PRIMARY KEY AUTO_INCREMENT,
	nombre_sede VARCHAR(50) NOT NULL,
	id_institucion_sede INT NOT NULL,
	email_director_sede CHAR(40) NOT NULL,
	url_sede VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS sede_concurso (
	id_sede_concurso INT PRIMARY KEY AUTO_INCREMENT,	
	id_sede INT NOT NULL,
	id_concurso INT NOT NULL,	
	INDEX (id_concurso),
	INDEX (id_sede)
);

CREATE TABLE IF NOT EXISTS equipos_sede_concurso (
	id_equipo_sede_concurso INT PRIMARY KEY AUTO_INCREMENT,
	id_equipo INT NOT NULL,
	id_sede_concurso INT NOT NULL,	
	INDEX (id_sede_concurso)
);
