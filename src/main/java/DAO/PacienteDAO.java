package DAO;

import baseDatos.ConnectionDB;
import model.Paciente;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private final static String SQL_ALL = "SELECT * FROM Paciente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Paciente WHERE nombre = ?";
//    private final static String SQL_INSERT = "INSERT INTO Paciente (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?)";
//    private final static String SQL_UPDATE = "UPDATE INTO";
//    private final static String SQL_DELETE_BY_ID = "DELETE FROM Paciente WHERE idPaciente = ?";
//    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Paciente WHERE dni = ?";
    private final static String SQL_SELECT_BY_CITA =
            "SELECT * " +
                    "FROM Paciente " +
                    "WHERE idPaciente IN (" +
                    "    SELECT idPaciente " +
                    "    FROM Cita " +
                    "    WHERE idCita = ?" +
                    ")";
    private final static String SQL_SELECT_BY_TRATAMIENTO =
            "SELECT * " +
                    "FROM Paciente " +
                    "WHERE idPaciente IN (" +
                    "    SELECT idPaciente " +
                    "    FROM TratamientoDentista " +
                    "    WHERE idTratamiento = ?" +
                    ")";

    /**
     * Version lazy para obtener todos los pacientes en un list
     *
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

    /**
     * Version EAGER de obtener todos los pacientes, esta muestra los tratamientos de cada paciente
     *
     * @return la lista de todos los pacientes de la BBDD.
     */

    public static List<Paciente> findAllEager() {
        List<Paciente> pacientes = new ArrayList<>();

        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Paciente paciente = new Paciente();
                int idPaciente = rs.getInt("idPaciente");
                paciente.setIdPaciente(idPaciente);
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                paciente.setEdad(rs.getInt("edad"));

                // Versión EAGER
                paciente.setTratamientosPaciente(TratamientoDAO.findTratamientosByPaciente(idPaciente));
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pacientes;

    }

    public static Paciente findPacienteByCita(int idCita) {
        Paciente paciente = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_CITA)) {
            pst.setInt(1, idCita);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                paciente.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paciente;
    }

    public static Paciente findPacienteByTratamiento(int idTratamiento) {
        Paciente paciente = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_TRATAMIENTO)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                paciente.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paciente;
    }

    public static Paciente findByIdEager(int idPaciente) {
        Paciente paciente = null;
        try (Connection con = ConnectionDB.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idPaciente);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                paciente.setEdad(rs.getInt("edad"));

                // Versión EAGER
                paciente.setTratamientosPaciente(TratamientoDAO.findTratamientosByPaciente(idPaciente));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paciente;
    }

    public static Paciente findByNameEager(String nombre) {
        Paciente paciente = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_NAME)) {
            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                paciente.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                paciente.setTratamientosPaciente(TratamientoDAO.findTratamientosByPaciente(paciente.getIdPaciente()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paciente;
    }


}
