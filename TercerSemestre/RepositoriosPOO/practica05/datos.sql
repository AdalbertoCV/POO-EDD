USE controlconcursos_ad2021;
DELETE FROM persona_34152734;
DELETE FROM datos_estudiante_34152734;
DELETE FROM equipo_34152734;

INSERT INTO persona_34152734 VALUES('34152734@uaz.edu.mx','Adalberto','Cerrillo Vazquez','M','Cerrada de copal','Villas de Guadalupe',32056,32,'98612','4921226114','2002-04-23',1,'Estudiante');
INSERT INTO persona_34152734 VALUES('mguadalupe_carrillo@hotmail.com','María Guadalupe','Carrillo Adame','F','Calle Felices','La condesa',32056,32,'98615','4920000000','2002-06-17',1,'Estudiante');
INSERT INTO persona_34152734 VALUES('jorgechavezzarate@gmail.com','Jorge Eduardo','Chavez Zarate','M','Calle Nezahualcoyotl','Reformas',32056,32,'97666','4928965089','2002-09-12',1,'Estudiante');
INSERT INTO persona_34152734 VALUES('rsolis@uaz.edu.mx','Roberto','Solis Robles','M','Calle Esperanza','Olivas',32056,32,'89543','4929111109',"1980-01-10",1,'Profesor');

INSERT INTO datos_estudiante_34152734 VALUES('34152734@uaz.edu.mx','Ing.Software','2020-08-26','2025-05-26');
INSERT INTO datos_estudiante_34152734 VALUES('mguadalupe_carrillo@hotmail.com','Ing.Software','2020-08-26','2025-05-26');
INSERT INTO datos_estudiante_34152734 VALUES('jorgechavezzarate@gmail.com','Ing.Software','2020-08-26','2025-05-26');

INSERT INTO equipo_34152734 VALUES(7,'Team Pro','rsolis@uaz.edu.mx','34152734@uaz.edu.mx','mguadalupe_carrillo@hotmail.com','jorgechavezzarate@gmail.com',NULL,1);

SELECT email_persona, nombre_persona, apellidos_persona, sexo_persona, tipo_persona FROM persona_34152734;
SELECT * FROM datos_estudiante_34152734;
SELECT * FROM equipo_34152734;