INSERT INTO clientes (dni, nombre, apellido, direccion, fecha_nacimiento, numero_carnet, tipo, fecha_expedicion, puntos) 
VALUES ('12345678A', 'Juan', 'Pérez García', 'Calle Mayor 1, Madrid', '1975-05-15', '12345678A', 'B', '1995-06-20', 15);
INSERT INTO clientes (dni, nombre, apellido, direccion, fecha_nacimiento, numero_carnet, tipo, fecha_expedicion, puntos) 
VALUES ('87654321B', 'Lucía', 'López Martín', 'Av. Castellana 200, Madrid', '2005-03-10', '87654321B', 'B', '2023-09-01', 8);
INSERT INTO clientes (dni, nombre, apellido, direccion, fecha_nacimiento, numero_carnet, tipo, fecha_expedicion, puntos) 
VALUES ('B11223344', 'Transportes Rápidos S.L.', NULL, 'Polígono Industrial Sur, Nave 3', NULL, NULL, NULL, NULL, NULL);
INSERT INTO clientes (dni, nombre, apellido, direccion, fecha_nacimiento, numero_carnet, tipo, fecha_expedicion, puntos) 
VALUES ('55667788C', 'Carlos', 'Ruiz Sanchez', 'Plaza España 5, Valencia', '1994-01-20', '55667788C', 'B', '2018-02-15', 12);

INSERT INTO coberturas (nombre, descripcion, precio_base, factor_riesgo) 
VALUES ('Responsabilidad Civil', 'Cobertura obligatoria por ley', 200.00, 0.00);
INSERT INTO coberturas (nombre, descripcion, precio_base, factor_riesgo) 
VALUES ('Asistencia en Viaje', 'Grúa km 0 y asistencia al conductor', 45.00, 0.00);
INSERT INTO coberturas (nombre, descripcion, precio_base, factor_riesgo) 
VALUES ('Lunas', 'Reparación y sustitución de lunas', 60.00, 0.00);
INSERT INTO coberturas (nombre, descripcion, precio_base, factor_riesgo) 
VALUES ('Robo e Incendio', 'Indemnización por robo o incendio total', 50.00, 0.03);
INSERT INTO coberturas (nombre, descripcion, precio_base, factor_riesgo) 
VALUES ('Todo Riesgo', 'Daños propios con franquicia', 100.00, 0.05);


INSERT INTO polizas (numero_poliza, fecha_inicio, fecha_fin, estado, matricula, marca_modelo, caballos, precio, tomador_id, conductor_id, propietario_id) 
VALUES ('POL-2024-001', '2024-01-01', '2025-01-01', 'VIGENTE', '1234-KLT', 'Ford Fiesta', 90, 350.00, 1, 1, 1);
INSERT INTO polizas (numero_poliza, fecha_inicio, fecha_fin, estado, matricula, marca_modelo, caballos, precio, tomador_id, conductor_id, propietario_id) 
VALUES ('POL-2024-002', '2024-02-15', '2025-02-15', 'VIGENTE', '9988-ZZZ', 'BMW Serie 3', 190, 1200.50, 1, 2, 1);
INSERT INTO polizas (numero_poliza, fecha_inicio, fecha_fin, estado, matricula, marca_modelo, caballos, precio, tomador_id, conductor_id, propietario_id) 
VALUES ('POL-2024-003', '2023-06-01', '2024-06-01', 'VIGENTE', '4567-BBB', 'Renault Kangoo', 110, 450.00, 3, 4, 3);
INSERT INTO polizas (numero_poliza, fecha_inicio, fecha_fin, estado, matricula, marca_modelo, caballos, precio, tomador_id, conductor_id, propietario_id) 
VALUES ('POL-2023-999', '2022-01-01', '2023-01-01', 'CANCELADA', '0000-AAA', 'Seat Panda', 55, 150.00, 1, 1, 1);

INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (1, 1);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (1, 2);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (2, 1);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (2, 2);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (2, 3);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (2, 4);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (2, 5);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (3, 1);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (3, 2);
INSERT INTO poliza_coberturas (poliza_id, cobertura_id) VALUES (3, 3);