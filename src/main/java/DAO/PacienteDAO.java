package DAO;

public class PacienteDAO  {
    private final static String SQL_ALL = "SELECT * FROM Paciente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Paciente WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Paciente (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE INTO";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Paciente WHERE dni = ?";

}
