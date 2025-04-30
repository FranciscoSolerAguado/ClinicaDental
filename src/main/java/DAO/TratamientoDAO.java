package DAO;

public class TratamientoDAO {
    private final static String SQL_ALL = "SELECT * FROM Tratamiento";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Tramiento WHERE idTratamiento = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Tratamiento WHERE nombrePaciente = ?";
    private final static String SQL_INSERT = "INSERT INTO Tratamiento (tipoTratamiento, nombrePaciente, descripcion, precio) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE INTO";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Tratamiento WHERE idTratamiento = ?";
}
