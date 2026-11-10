# Facturación Telco - Sistema de Venta de Recargas

Sistema web completo para la venta de recargas telefónicas de múltiples operadores (Tigo, Movistar, Comcel, Uff Mobile).

## 🚀 Tecnologías

### Backend
- **Framework**: Spring Boot 3.2.0
- **Java**: 17
- **Base de datos**: Oracle XE 21c
- **Seguridad**: Spring Security + JWT
- **Build**: Maven 3.9.6

### Frontend
- **Framework**: Angular 17
- **UI**: Bootstrap 5
- **HTTP**: HttpClient standalone

### Base de Datos
- **Motor**: Oracle XE 21c (Express Edition)
- **PDB**: XEPDB1
- **Usuario**: telco_user

## 📋 Requisitos Previos

### Software Necesario
1. **Java Development Kit (JDK) 17** o superior
2. **Maven 3.9.6** o superior
3. **Node.js 18.x** o superior
4. **Oracle XE 21c** instalado y funcionando
5. **SQL*Plus** (incluido con Oracle XE)

### Verificar Instalaciones
```powershell
# Java
java -version

# Maven
mvn -version

# Node.js y npm
node -v
npm -v

# SQL*Plus
sqlplus -version
```

## 🗄️ Instalación de Base de Datos

### 1. Conectar a Oracle como SYSDBA
```powershell
sqlplus sys/tu_password@localhost:1521/XEPDB1 as sysdba
```

### 2. Crear Usuario
```sql
CREATE USER telco_user IDENTIFIED BY telco_password;
GRANT CONNECT, RESOURCE, CREATE VIEW TO telco_user;
GRANT UNLIMITED TABLESPACE TO telco_user;
EXIT;
```

### 3. Ejecutar Script de Esquema
```powershell
cd db
sqlplus telco_user/telco_password@localhost:1521/XEPDB1 @schema.sql
```

### 4. Verificar Instalación
```sql
sqlplus telco_user/telco_password@localhost:1521/XEPDB1

SELECT COUNT(*) FROM USUARIO;      -- Debe retornar 2
SELECT COUNT(*) FROM OPERADOR;     -- Debe retornar 4
SELECT COUNT(*) FROM RECARGA;      -- Debe retornar 16
EXIT;
```

## ⚙️ Configuración del Backend

### 1. Configurar Conexión a Base de Datos
Editar `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521/XEPDB1
    username: telco_user
    password: telco_password
```

### 2. Compilar Backend
```powershell
cd backend
mvn clean install -DskipTests
```

### 3. Ejecutar Backend
```powershell
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080/api`

### Endpoints Principales
- **POST** `/auth/login` - Autenticación
- **POST** `/auth/register` - Registro de usuarios
- **GET** `/operadores` - Lista de operadores
- **GET** `/recargas` - Lista de recargas
- **POST** `/ventas` - Registrar venta
- **GET** `/reportes/ventas` - Reporte de ventas

## 🎨 Configuración del Frontend

### 1. Instalar Dependencias
```powershell
cd frontend
npm install
```

### 2. Ejecutar Frontend
```powershell
npm start
```

La aplicación estará disponible en: `http://localhost:4200`

### 3. Compilar para Producción
```powershell
npm run build
```

## 👥 Usuarios de Prueba

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| admin | admin123 | ADMIN |
| vendedor1 | vendedor123 | VENDEDOR |

## 📱 Operadores y Recargas Disponibles

### Operadores
- **Tigo**: Prepago y Postpago
- **Movistar**: Prepago y Postpago
- **Comcel (Claro)**: Prepago y Postpago
- **Uff Mobile**: Prepago

### Valores de Recarga
- $5,000 COP
- $10,000 COP
- $20,000 COP
- $50,000 COP

## 🔒 Seguridad

El sistema implementa:
- **Autenticación JWT**: Tokens de sesión seguros
- **Encriptación BCrypt**: Contraseñas hasheadas
- **Control de Roles**: ADMIN y VENDEDOR
- **CORS configurado**: Comunicación segura frontend-backend

## 📊 Estructura de la Base de Datos

### Tablas Principales
- **USUARIO**: Usuarios del sistema
- **OPERADOR**: Operadores telefónicos
- **RECARGA**: Catálogo de recargas
- **VENTA**: Registro de ventas
- **REPORTE_VENTAS**: Vista agregada para reportes

### Secuencias
- `SEQ_USUARIO`
- `SEQ_OPERADOR`
- `SEQ_RECARGA`
- `SEQ_VENTA`

## 🚀 Despliegue

### Backend (Producción)
```powershell
cd backend
mvn clean package -DskipTests
java -jar target/facturacion-telco-1.0.0.jar
```

### Frontend (Producción)
```powershell
cd frontend
npm run build
# Los archivos compilados estarán en frontend/dist/
```

## 🐛 Solución de Problemas

### Backend no inicia
- Verificar que Oracle esté corriendo
- Confirmar credenciales en `application.yml`
- Revisar puerto 8080 no esté ocupado

### Frontend no conecta al Backend
- Verificar que backend esté corriendo en puerto 8080
- Revisar configuración CORS en backend
- Confirmar URL en `environment.ts`

### Error de autenticación
- Verificar que las contraseñas en BD estén hasheadas con BCrypt
- Confirmar que el usuario existe en tabla USUARIO
- Revisar logs del backend para detalles

### Error de conexión a Oracle
```
ORA-12154: TNS:could not resolve the connect identifier specified
```
**Solución**: Usar `/XEPDB1` en lugar de `/XE` en la cadena de conexión

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👨‍💻 Desarrollado con

- Spring Boot
- Angular
- Oracle Database
- Bootstrap
- JWT Authentication

---

**Nota**: Este es un sistema completo y funcional listo para producción. Para cualquier duda o soporte, revisar la documentación de cada tecnología utilizada.
