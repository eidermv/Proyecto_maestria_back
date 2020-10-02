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

INSERT INTO tipo_seguimiento (id_tipo_seguimientor, nombre) VALUES(1, 'Propuesta');
INSERT INTO tipo_seguimiento (id_tipo_seguimientor, nombre) VALUES(2, 'Tesis');

INSERT INTO persona (id_persona, apellidos, correo, nombres) VALUES(1, 'admin', 'admin', 'admin');

INSERT INTO usuario (usu_id, usu_contrasena, usu_estado, usu_usuario, id_persona) VALUES(1, '$2a$10$bTX5wXpWYx7eeyCW9kX/1OakqzivAo8/RZ9WjYW4tuRHuY5pScHLS', 1, 'admin', 1);
