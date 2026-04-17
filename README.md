# 📚 Library Management System

Sistema de gestión de biblioteca desarrollado en Java con interfaz gráfica (Swing), base de datos y arquitectura DAO.

---

## 🧩 Descripción

Este proyecto permite gestionar una biblioteca mediante una aplicación de escritorio.
Incluye funcionalidades completas para administrar:

* 👤 Usuarios
* 📖 Libros
* 🔄 Préstamos
* 🏷️ Categorías

Además, permite exportar toda la información a un archivo XML.

---

## 🛠️ Tecnologías utilizadas

* Swing (interfaz gráfica)
* JDBC (conexión a base de datos)
* MySQL / MariaDB (base de datos)
* JUnit (tests)
* XML (exportación de datos)

---

## 🧱 Arquitectura

El proyecto sigue una estructura basada en el patrón **DAO (Data Access Object)**:

* `dao` → acceso a datos
* `interfaces` → contratos de acceso
* `clases` → modelos (entidades)
* `ventanas` → interfaz gráfica
* `modelo` → conexión a base de datos
* `test` → pruebas unitarias

---

## 🚀 Funcionalidades

* CRUD completo de:

  * Usuarios
  * Libros
  * Préstamos
  * Categorías

* Validaciones de datos

* Procedimientos almacenados

* Interfaz gráfica intuitiva

* Exportación a XML (`biblioteca.xml`)

* Tests unitarios con JUnit

---

## 📂 Estructura del proyecto

```
src/
 ├── clases/
 ├── dao/
 ├── interfaces/
 ├── modelo/
 ├── ventanas/
 └── test/
```

---

## ⚙️ Configuración

### 1. Base de datos

Configura el archivo:

```
configClase.properties
```

Con tus datos:

```
Conn=jdbc:mysql://localhost:3306/tu_bd
DBUser=usuario
DBPass=contraseña
```

---

### 2. Ejecutar el proyecto

Ejecuta la clase:

```
VentanaMenu.java
```

---

### 3. Generar JavaDoc

```
javadoc -d doc src/**/*.java
```

---

## 🧪 Tests

El proyecto incluye pruebas unitarias para:

* Usuarios
* Libros
* Préstamos
* Categorías

Ubicadas en el paquete:

```
test/
```

---

## 👥 Reparto de tareas

El proyecto se ha desarrollado de forma colaborativa:

* **Trabajo en grupo**

  * Exportación XML
  * Acceso a base de datos (AccesoBD)

* **Diago**

  * Módulo de Préstamos

* **Kevin**

  * Módulo de Libros

* **Marcela**

  * Módulo de Categorías

* **Iker**

  * Módulo de Usuarios

---

## 📄 Exportación XML

El sistema genera un archivo:

```
biblioteca.xml
```

Que incluye:

* Usuarios
* Libros
* Préstamos
* Categorías

---

## 💡 Mejoras futuras

* Implementar arquitectura MVC completa
* Añadir autenticación de usuarios
* Mejorar validaciones
* Migrar a JavaFX
* API REST
