package DAO;

import baseDatos.ConnectionDB;
import model.Paciente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO  {
    private final static String SQL_ALL = "SELECT * FROM Paciente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Paciente WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Paciente (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE INTO";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Paciente WHERE dni = ?";

    /**
     * Version lazy para obtener todos los pacientes en un list
     * @return un list de pacientes
     */
    public static List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<Paciente>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setFechaNacimiento("fechaNacimiento");
                paciente.setEdad(rs.getInt("edad"));

                paciente.setPacientes(new ArrayList<Paciente>());
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

}
