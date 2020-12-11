INSERT INTO tipo_usuario (tipo_usu_id, tipo_usu_nombre) VALUES(1, 'Coordinador');
INSERT INTO tipo_usuario (tipo_usu_id, tipo_usu_nombre) VALUES(2, 'Estudiante');
INSERT INTO tipo_usuario (tipo_usu_id, tipo_usu_nombre) VALUES(3, 'Tutor');

INSERT INTO estado_proyecto (id_estado_proyecto, nombre) VALUES(1, 'Desarrollo');
INSERT INTO estado_proyecto (id_estado_proyecto, nombre) VALUES(2, 'Revision');
INSERT INTO estado_proyecto (id_estado_proyecto, nombre) VALUES(3, 'Aprobado');

INSERT INTO estado_seguimiento (id_estado_seguimiento, nombre) VALUES(1, 'Aceptado');
INSERT INTO estado_seguimiento (id_estado_seguimiento, nombre) VALUES(2, 'Espera');
INSERT INTO estado_seguimiento (id_estado_seguimiento, nombre) VALUES(3, 'Rechazado');

INSERT INTO tipo_tutor (id_tipo_tutor, nombre) VALUES(1, 'Interno');
INSERT INTO tipo_tutor (id_tipo_tutor, nombre) VALUES(2, 'Externo');

INSERT INTO tipo_seguimiento (id_tipo_seguimiento, nombre) VALUES(1, 'Propuesta');
INSERT INTO tipo_seguimiento (id_tipo_seguimiento, nombre) VALUES(2, 'Tesis');

INSERT INTO persona (id_persona, apellidos, correo, nombres) VALUES(1, 'admin', 'admin', 'admin');

INSERT INTO usuario (usu_id, usu_contrasena, usu_estado, usu_usuario, id_persona) VALUES(1, '$2a$10$bTX5wXpWYx7eeyCW9kX/1OakqzivAo8/RZ9WjYW4tuRHuY5pScHLS', 1, 'admin', 1);

INSERT INTO grupo_tipo_usuario (usu_id, tipo_usu_id) VALUES(1, 1);

INSERT INTO persona (id_persona, apellidos, correo, nombres) VALUES(2, 'cobos', 'cobos', 'carlos');

INSERT INTO tutor (id_tutor, apellidos, correo, departamento, grupo_investigacion, identificacion, nombres, telefono, universidad, id_persona, id_tipo_tutor) VALUES(1, 'cobos', 'cobos', 'fiet', 'fiet', 123, 'carlos', 300000000, 'unicauca', 2, 1);


INSERT INTO usuario (usu_id, usu_contrasena, usu_estado, usu_usuario, id_persona) VALUES(2, '$2a$10$bTX5wXpWYx7eeyCW9kX/1OakqzivAo8/RZ9WjYW4tuRHuY5pScHLS', 1, 'cobos', 2);

INSERT INTO grupo_tipo_usuario (usu_id, tipo_usu_id) VALUES(2, 3);

INSERT INTO persona(id_persona, apellidos, correo, nombres) VALUES(3, 'mono', 'mono', 'mono');

INSERT INTO estudiante(est_id, est_apellidos, est_codigo, est_cohorte, est_correo, est_creditos, est_estado, est_nombres, est_pertenece, est_semestre, id_persona, tutor_id_tutor) VALUES(1, 'mono', '46102035', 1, 'mono', 12, 'ni idea', 'mono', 'mono', 10, 3, 1);

INSERT INTO seguimiento (id_seguimiento, codirector, cohorte, nombre, objetivo_general, objetivos_especificos, id_estado_proyecto, id_estado_seguimiento, est_id, id_tipo_seguimiento, id_tutor) VALUES(1, 'sss', '2019', 'ssss', 'ssss', '', 1, 1, 1, 2, 1);

INSERT INTO actividad (id_actividad, compromisos, cumplida, entregas, fecha_entrega, fecha_inicio, semana, visible, id_seguimiento) VALUES(1, 'ninguno', 1, 'no', '2010-01-12 00:00:00.000', '2010-01-12 00:00:00.000', '1', 0, 1);
