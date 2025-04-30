package DAO;

public class CitaDAO {
    private final static String SQL_ALL = "SELECT * FROM Cita";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cita WHERE idCita = ?";
    private final static String SQL_INSERT = "INSERT INTO Cita (idPaciente, idDentista, paciente, dentista, fecha) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE INTO";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Cita WHERE idCita = ?";
}
