## ClinicaDental

Proyecto final de programación 1ºDAM sobre una clínica dental.

### Descripción

Este proyecto implementa un sistema de gestión para una clínica dental, permitiendo manejar información sobre dentistas, pacientes, tratamientos y sus relaciones. Está desarrollado en Java utilizando Maven como herramienta de construcción.

### Estructura del proyecto

El proyecto está organizado en los siguientes paquetes:

- **`model`**: Contiene las clases principales del modelo de datos, como:
  - `Persona`: Clase base que representa una persona con atributos como nombre, DNI y teléfono.
  - `Dentista`: Extiende `Persona` y añade atributos específicos como especialidad y número de colegiado.
  - `Paciente`: Extiende `Persona` y añade atributos como alergias y tratamientos asociados.
  - `Tratamiento`: Representa un tratamiento dental con detalles como descripción, precio y dentista asociado.
  - `TratamientoPaciente`: Relaciona tratamientos con pacientes, incluyendo detalles específicos.

### Diagrama de clases

El proyecto incluye tres diagramas de clases (`Diagrama_Clases_Model.puml` `Diagrama_Clases_Controller.puml` `Diagrama_Clases_DAO.puml`) que muestran las relaciones entre las clases de los paquetes `model`, `controller` y `DAO`


### Tecnologías utilizadas

- **Lenguajes**: Java, JavaFX
- **Gestor de dependencias**: Maven
- **Herramientas adicionales**: ChatGPT , PlantUML , W3Schools y Youtube

### Autor

Hecho por Francisco J. Soler Aguado.
