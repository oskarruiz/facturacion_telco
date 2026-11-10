-- ============================================
-- Sistema de Facturación Telco
-- Base de Datos Oracle
-- ============================================

-- Eliminar tablas si existen
DROP TABLE REPORTE_VENTAS CASCADE CONSTRAINTS;
DROP TABLE VENTA CASCADE CONSTRAINTS;
DROP TABLE RECARGA CASCADE CONSTRAINTS;
DROP TABLE OPERADOR CASCADE CONSTRAINTS;
DROP TABLE USUARIO CASCADE CONSTRAINTS;

-- Eliminar secuencias si existen
DROP SEQUENCE SEQ_USUARIO;
DROP SEQUENCE SEQ_OPERADOR;
DROP SEQUENCE SEQ_RECARGA;
DROP SEQUENCE SEQ_VENTA;
DROP SEQUENCE SEQ_REPORTE;

-- ============================================
-- SECUENCIAS
-- ============================================
CREATE SEQUENCE SEQ_USUARIO START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_OPERADOR START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_RECARGA START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_VENTA START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_REPORTE START WITH 1 INCREMENT BY 1;

-- ============================================
-- TABLA USUARIO
-- ============================================
CREATE TABLE USUARIO (
    id_usuario NUMBER PRIMARY KEY,
    username VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    nombre VARCHAR2(100) NOT NULL,
    apellido VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    telefono VARCHAR2(20),
    rol VARCHAR2(20) NOT NULL CHECK (rol IN ('ADMIN', 'VENDEDOR')),
    fecha_creacion DATE DEFAULT SYSDATE,
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0, 1))
);

-- ============================================
-- TABLA OPERADOR
-- ============================================
CREATE TABLE OPERADOR (
    id_operador NUMBER PRIMARY KEY,
    nombre VARCHAR2(50) UNIQUE NOT NULL,
    codigo VARCHAR2(10) UNIQUE NOT NULL,
    descripcion VARCHAR2(255),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0, 1))
);

-- ============================================
-- TABLA RECARGA
-- ============================================
CREATE TABLE RECARGA (
    id_recarga NUMBER PRIMARY KEY,
    id_operador NUMBER NOT NULL,
    valor NUMBER(10,2) NOT NULL CHECK (valor > 0),
    descripcion VARCHAR2(255),
    comision NUMBER(10,2) DEFAULT 0 CHECK (comision >= 0),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0, 1)),
    CONSTRAINT fk_recarga_operador FOREIGN KEY (id_operador) 
        REFERENCES OPERADOR(id_operador)
);

-- ============================================
-- TABLA VENTA
-- ============================================
CREATE TABLE VENTA (
    id_venta NUMBER PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    id_recarga NUMBER NOT NULL,
    id_operador NUMBER NOT NULL,
    numero_celular VARCHAR2(15) NOT NULL,
    valor NUMBER(10,2) NOT NULL CHECK (valor > 0),
    comision NUMBER(10,2) DEFAULT 0 CHECK (comision >= 0),
    fecha_venta DATE DEFAULT SYSDATE,
    estado VARCHAR2(20) DEFAULT 'COMPLETADA' CHECK (estado IN ('COMPLETADA', 'PENDIENTE', 'FALLIDA')),
    referencia_transaccion VARCHAR2(100) UNIQUE,
    observaciones VARCHAR2(500),
    CONSTRAINT fk_venta_usuario FOREIGN KEY (id_usuario) 
        REFERENCES USUARIO(id_usuario),
    CONSTRAINT fk_venta_recarga FOREIGN KEY (id_recarga) 
        REFERENCES RECARGA(id_recarga),
    CONSTRAINT fk_venta_operador FOREIGN KEY (id_operador) 
        REFERENCES OPERADOR(id_operador)
);

-- ============================================
-- TABLA REPORTE_VENTAS (opcional, para cache)
-- ============================================
CREATE TABLE REPORTE_VENTAS (
    id_reporte NUMBER PRIMARY KEY,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    id_usuario NUMBER,
    id_operador NUMBER,
    total_ventas NUMBER(12,2) DEFAULT 0,
    cantidad_ventas NUMBER DEFAULT 0,
    total_comisiones NUMBER(12,2) DEFAULT 0,
    fecha_generacion DATE DEFAULT SYSDATE,
    CONSTRAINT fk_reporte_usuario FOREIGN KEY (id_usuario) 
        REFERENCES USUARIO(id_usuario),
    CONSTRAINT fk_reporte_operador FOREIGN KEY (id_operador) 
        REFERENCES OPERADOR(id_operador)
);

-- ============================================
-- ÍNDICES
-- ============================================
CREATE INDEX idx_usuario_username ON USUARIO(username);
CREATE INDEX idx_usuario_email ON USUARIO(email);
CREATE INDEX idx_venta_usuario ON VENTA(id_usuario);
CREATE INDEX idx_venta_operador ON VENTA(id_operador);
CREATE INDEX idx_venta_fecha ON VENTA(fecha_venta);
CREATE INDEX idx_venta_estado ON VENTA(estado);
CREATE INDEX idx_recarga_operador ON RECARGA(id_operador);
CREATE INDEX idx_recarga_activo ON RECARGA(activo);

-- ============================================
-- DATOS INICIALES
-- ============================================

-- Usuarios (password es 'admin123' y 'vendedor123' hasheado con BCrypt)
INSERT INTO USUARIO (id_usuario, username, password, nombre, apellido, email, telefono, rol, activo)
VALUES (SEQ_USUARIO.NEXTVAL, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
        'Administrador', 'Sistema', 'admin@telco.com', '3001234567', 'ADMIN', 1);

INSERT INTO USUARIO (id_usuario, username, password, nombre, apellido, email, telefono, rol, activo)
VALUES (SEQ_USUARIO.NEXTVAL, 'vendedor1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
        'Juan', 'Pérez', 'vendedor1@telco.com', '3009876543', 'VENDEDOR', 1);

-- Operadores
INSERT INTO OPERADOR (id_operador, nombre, codigo, descripcion, activo)
VALUES (SEQ_OPERADOR.NEXTVAL, 'Tigo', 'TIG', 'Operador Tigo Colombia', 1);

INSERT INTO OPERADOR (id_operador, nombre, codigo, descripcion, activo)
VALUES (SEQ_OPERADOR.NEXTVAL, 'Movistar', 'MOV', 'Operador Movistar Colombia', 1);

INSERT INTO OPERADOR (id_operador, nombre, codigo, descripcion, activo)
VALUES (SEQ_OPERADOR.NEXTVAL, 'Comcel', 'COM', 'Operador Claro (Comcel) Colombia', 1);

INSERT INTO OPERADOR (id_operador, nombre, codigo, descripcion, activo)
VALUES (SEQ_OPERADOR.NEXTVAL, 'Uff', 'UFF', 'Operador Uff Móvil Colombia', 1);

-- Recargas para cada operador
-- Tigo
INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 1, 5000, 'Recarga Tigo $5.000', 200, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 1, 10000, 'Recarga Tigo $10.000', 400, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 1, 20000, 'Recarga Tigo $20.000', 800, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 1, 50000, 'Recarga Tigo $50.000', 2000, 1);

-- Movistar
INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 2, 5000, 'Recarga Movistar $5.000', 200, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 2, 10000, 'Recarga Movistar $10.000', 400, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 2, 20000, 'Recarga Movistar $20.000', 800, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 2, 50000, 'Recarga Movistar $50.000', 2000, 1);

-- Comcel
INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 3, 5000, 'Recarga Comcel $5.000', 200, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 3, 10000, 'Recarga Comcel $10.000', 400, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 3, 20000, 'Recarga Comcel $20.000', 800, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 3, 50000, 'Recarga Comcel $50.000', 2000, 1);

-- Uff
INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 4, 5000, 'Recarga Uff $5.000', 200, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 4, 10000, 'Recarga Uff $10.000', 400, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 4, 20000, 'Recarga Uff $20.000', 800, 1);

INSERT INTO RECARGA (id_recarga, id_operador, valor, descripcion, comision, activo)
VALUES (SEQ_RECARGA.NEXTVAL, 4, 50000, 'Recarga Uff $50.000', 2000, 1);

COMMIT;

-- ============================================
-- VISTAS PARA REPORTES
-- ============================================

-- Vista: Resumen de ventas por operador
CREATE OR REPLACE VIEW V_VENTAS_POR_OPERADOR AS
SELECT 
    o.id_operador,
    o.nombre AS operador,
    o.codigo,
    COUNT(v.id_venta) AS cantidad_ventas,
    SUM(v.valor) AS total_ventas,
    SUM(v.comision) AS total_comisiones,
    TO_CHAR(v.fecha_venta, 'YYYY-MM-DD') AS fecha
FROM OPERADOR o
LEFT JOIN VENTA v ON o.id_operador = v.id_operador
WHERE v.estado = 'COMPLETADA'
GROUP BY o.id_operador, o.nombre, o.codigo, TO_CHAR(v.fecha_venta, 'YYYY-MM-DD');

-- Vista: Resumen de ventas por vendedor
CREATE OR REPLACE VIEW V_VENTAS_POR_VENDEDOR AS
SELECT 
    u.id_usuario,
    u.username,
    u.nombre || ' ' || u.apellido AS nombre_completo,
    COUNT(v.id_venta) AS cantidad_ventas,
    SUM(v.valor) AS total_ventas,
    SUM(v.comision) AS total_comisiones,
    TO_CHAR(v.fecha_venta, 'YYYY-MM-DD') AS fecha
FROM USUARIO u
LEFT JOIN VENTA v ON u.id_usuario = v.id_usuario
WHERE v.estado = 'COMPLETADA'
GROUP BY u.id_usuario, u.username, u.nombre || ' ' || u.apellido, TO_CHAR(v.fecha_venta, 'YYYY-MM-DD');

-- Vista: Ventas detalladas
CREATE OR REPLACE VIEW V_VENTAS_DETALLADAS AS
SELECT 
    v.id_venta,
    v.referencia_transaccion,
    u.username AS vendedor,
    u.nombre || ' ' || u.apellido AS nombre_vendedor,
    o.nombre AS operador,
    o.codigo AS codigo_operador,
    v.numero_celular,
    r.descripcion AS tipo_recarga,
    v.valor,
    v.comision,
    v.fecha_venta,
    v.estado,
    v.observaciones
FROM VENTA v
INNER JOIN USUARIO u ON v.id_usuario = u.id_usuario
INNER JOIN OPERADOR o ON v.id_operador = o.id_operador
INNER JOIN RECARGA r ON v.id_recarga = r.id_recarga;
