## ClinicaDental

Proyecto final de programación 1ºDAM sobre una clínica dental.

### Descripción

Este proyecto implementa un sistema de gestión para una clínica dental, permitiendo manejar información sobre dentistas, pacientes, tratamientos y sus relaciones. Está desarrollado en Java utilizando Maven como herramienta de construcción.

### Estructura del proyecto

El proyecto está organizado en los siguientes paquetes:

---

##### **`model`** Contiene las clases principales del modelo de datos, que representan las entidades principales de la clínica dental:

- **`Persona`**: Clase base que representa una persona con atributos como:
  - `nombre`, `DNI` y `teléfono`.
- **`Dentista`**: Extiende `Persona` y añade atributos específicos como:
  - `especialidad`, `número de colegiado`, `edad` y `fecha de nacimiento`.
  - Relación con tratamientos a través de una lista de `Tratamiento`.
- **`Paciente`**: Extiende `Persona` y añade atributos como:
  - `alergias`, `edad`, `fecha de nacimiento` y una lista de `TratamientoPaciente`.
- **`Tratamiento`**: Representa un tratamiento dental con detalles como:
  - `descripción`, `precio`, `dentista asociado` y `ID`.
- **`TratamientoPaciente`**: Relaciona tratamientos con pacientes, incluyendo:
  - `detalles`, `fecha del tratamiento`, `paciente asociado` y `tratamiento asociado`.

---

##### **`controller`** Este paquete contiene las clases responsables de manejar la lógica de la interfaz de usuario y coordinar las interacciones entre la vista y el modelo:

- **`DentistaController`**: Gestiona la vista de dentistas, permitiendo:
  - Cargar, mostrar, editar y eliminar dentistas.
- **`DentistaFormController`**: Maneja el formulario para crear o editar dentistas.
- **`InicioController`**: Controla la pantalla de inicio, incluyendo la configuración inicial.
- **`MainController`**: Permite la navegación entre las diferentes vistas de la aplicación.
- **`PacienteController`**: Gestiona la vista de pacientes, permitiendo:
  - Cargar, mostrar, editar y eliminar pacientes, además de mostrar sus tratamientos.
- **`PacienteFormController`**: Maneja el formulario para crear o editar pacientes.
- **`PacienteTratamientoController`**: Controla la vista de tratamientos asociados a un paciente.
- **`TratamientoController`**: Gestiona la vista de tratamientos, permitiendo:
  - Cargar, mostrar, editar y eliminar tratamientos.
- **`TratamientoFormController`**: Maneja el formulario para crear o editar tratamientos.
- **`TratamientosPacientesController`**: Gestiona la vista de tratamientos asignados a pacientes.
- **`TratamientosPacientesFormController`**: Maneja el formulario para asignar tratamientos a pacientes.

---

##### **`DAO`** Este paquete contiene las clases responsables de la interacción con la base de datos, implementando operaciones CRUD para las entidades del modelo:

- **`DentistaDAO`**:
  - Métodos para buscar, insertar, actualizar y eliminar dentistas.
  - Incluye métodos específicos como buscar dentistas por tratamiento.
- **`PacienteDAO`**:
  - Métodos para buscar, insertar, actualizar y eliminar pacientes.
  - Incluye métodos específicos como buscar pacientes por nombre o ID.
- **`TratamientoDAO`**:
  - Métodos para buscar, insertar, actualizar y eliminar tratamientos.
  - Incluye métodos específicos como buscar tratamientos por descripción o dentista.
- **`TratamientoPacienteDAO`**:
  - Métodos para buscar, insertar, actualizar y eliminar relaciones entre tratamientos y pacientes.

---


### Diagrama de clases

El proyecto incluye tres diagramas de clases (`Diagrama_Clases_Model.puml`, `Diagrama_Clases_Controller.puml`, `Diagrama_Clases_DAO.puml`) que muestran las relaciones entre las clases de los paquetes `model`, `controller` y `DAO`.

### Tecnologías utilizadas

- **Lenguajes**: Java, JavaFX
- **Gestor de dependencias**: Maven
- **Herramientas adicionales**: ChatGPT, PlantUML, W3Schools y YouTube

### Autor

Hecho por Francisco J. Soler Aguado.
