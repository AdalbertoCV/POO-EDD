USE controlconcursos_ad2021;
DROP USER IF EXISTS U_34152734@'localhost';
DROP TABLE IF EXISTS persona_34152734;
DROP TABLE IF EXISTS datos_estudiante_34152734;
DROP TABLE IF EXISTS equipo_34152734;

CREATE USER 'U_34152734'@'localhost' IDENTIFIED BY 'UAZsw2021';
GRANT ALL PRIVILEGES ON controlconcursos_ad2021 TO 'U_34152734'@'localhost';

CREATE TABLE persona_34152734(
email_persona CHAR(40) PRIMARY KEY,
nombre_persona VARCHAR(30) NOT NULL,
apellidos_persona VARCHAR(40) NOT NULL,
sexo_persona ENUM('M','F') NOT NULL,
calle_num_persona VARCHAR(50) NOT NULL,
colonia_persona VARCHAR(50) NOT NULL,
id_municipio_persona INT(11) NOT NULL,
id_entidad_persona SMALLINT(6),
codpostal_persona CHAR(5),
telefono_persona CHAR(10) NOT NULL,
fecha_nac_persona DATE NOT NULL,
id_institucion_persona INT(11) NOT NULL,
tipo_persona ENUM('Estudiante','Profesor') NOT NULL
);

CREATE TABLE datos_estudiante_34152734(
email_estudiante CHAR(40) NOT NULL,
carrera_estudiante CHAR(50) NOT NULL,
fecha_inicio_carrera DATE NOT NULL,
fecha_esperada_terminacion DATE NOT NULL
);

CREATE TABLE equipo_34152734(
id_equipo INT(11) PRIMARY KEY AUTO_INCREMENT,
nombre_equipo VARCHAR(40) NOT NULL,
email_coach CHAR(40) NOT NULL,
email_concursante1 CHAR(40) NOT NULL,
email_concursante2 CHAR(40) NOT NULL,
email_concursante3 CHAR(40) NOT NULL,
email_concursante_reserva CHAR(40),
id_institucion_equipo INT(11) NOT NULL
);

SHOW TABLES LIKE '%34152734';
SELECT user,host FROM mysql.user;