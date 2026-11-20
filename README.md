# README
## AD05 - Notas: Tarea evaluativa

## Entorno fijado
Java 21, Maven 3.6.3 o posterior, Spring Boot 4.0.8, Oracle XE 21c.
Thymeleaf, Spring Data JPA y el driver ojdbc11 se resuelven con el BOM de Boot.
Bootstrap 5.3.8 se carga desde CDN: necesita Internet en el navegador para el estilo
completo y el menú plegable. Sin CDN, el CRUD sigue siendo HTML navegable.
## Preparación
Como administrador de Oracle conectado a XEPDB1, adapta la contraseña de
sql/00_usuario_oracle.sql y ejecútalo una vez. No en CDB$ROOT.
Conecta como AD05 y ejecuta una vez 
```
CREATE TABLE notebook (
    id NUMBER(19,0) PRIMARY KEY,
    name VARCHAR2(100 CHAR) NOT NULL,
    description VARCHAR2(200 CHAR) NOT NULL,
    subject VARCHAR2(60 CHAR),
    color NUMBER(5,0) NOT NULL,
    created DATE NOT NULL,
    favorite NUMBER(1) NOT NULL);
CREATE TABLE note (
    id NUMBER(19,0) PRIMARY KEY,
    title VARCHAR2(100 CHAR) NOT NULL,
    content VARCHAR2(1200 CHAR) NOT NULL,
    created DATE NOT NULL,
    notebook_id NUMBER(19,0) NOT NULL,
    CONSTRAINT fk_note_notebook FOREIGN KEY (notebook_id) REFERENCES notebook(id)
);
CREATE INDEX ix_note_notebook ON note(notebook_id);

-- Los ids 1 a 3 estan reservados para los ejemplos. Los nuevos empiezan en 100.
CREATE SEQUENCE notebook_seq START WITH 100 INCREMENT BY 1 NOCYCLE;
CREATE SEQUENCE note_seq START WITH 100 INCREMENT BY 1 NOCYCLE;
COMMIT;
```
con su esquema
vacío. 
Define DB_PASSWORD en las variables de entorno de la configuración de ejecución
del IDE. DB_USER y DB_URL son opcionales si coinciden con application.properties.
Importa como proyecto Maven y espera a que se descarguen las dependencias.
Ejecuta Ad05TeApplication o mvn spring-boot:run y abre http://localhost:8083/.
PowerShell (solo contraseña de práctica):
$env:DB_PASSWORD="tu_contrasena_de_practica"
mvn spring-boot:run
​
En Eclipse: Run Configurations > Java Application > CiclismoApplication > Environment.
Selecciona JDK 21. El mvn del terminal es una instalación de Maven independiente;
la integración Maven del IDE no garantiza que exista ese comando en el PATH.

## Decisiones de la demostración
Un nota siempre es parte de un cuaderno.
Eliminar un cuaderno elimina sus notas.
Eliminar una nota no elimina su cuaderno.
Cambiar de cuaderno conserva el id y los datos de la nota.
Una ficha recibida del formulario no se guarda directamente como entidad.
ddl-auto=validate, secuencias Oracle con INCREMENT BY 1 y allocationSize=1.
open-in-view=false: las consultas cargan el equipo necesario antes de la vista.
GET muestra la confirmación; POST ejecuta el borrado.
