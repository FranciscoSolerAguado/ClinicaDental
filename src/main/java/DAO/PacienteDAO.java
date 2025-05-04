package DAO;

import baseDatos.ConnectionDB;
import model.Paciente;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_ALL = "SELECT * FROM Paciente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Paciente WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Paciente (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE_NAME = "UPDATE Paciente SET nombre = ? WHERE idPaciente = ?";
    private final static String SQL_UPDATE_DNI = "UPDATE Paciente SET dni = ? WHERE idPaciente = ?";
    private final static String SQL_UPDATE_TELEFONO = "UPDATE Paciente SET telefono = ? WHERE idPaciente = ?";
    private final static String SQL_UPDATE_FECHA_NACIMIENTO = "UPDATE Paciente SET fechaNacimiento = ? WHERE idPaciente = ?";
    private final static String SQL_UPDATE_EDAD = "UPDATE Paciente SET edad = ? WHERE idPaciente = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Paciente WHERE dni = ?";
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
            while (rs.next()) {
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
            while (rs.next()) {
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

    public static void insert(Paciente paciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, paciente.getNombre());
            pst.setString(2, paciente.getDni());
            pst.setInt(3, paciente.getTelefono());
            pst.setString(4, paciente.getFechaNacimiento());
            pst.setInt(5, paciente.getEdad());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el paciente", e);
        }
    }

    public static void updateNombre(int idPaciente, String nombre) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_NAME)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setString(1, nombre);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el nombre del paciente", e);
        }
    }

    public static void updateDni(int idPaciente, String dni) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_DNI)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setString(1, dni);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el DNI del paciente", e);
        }
    }

    public static void updateTelefono(int idPaciente, int telefono) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_TELEFONO)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setInt(1, telefono);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el teléfono del paciente", e);
        }
    }

    public static void updateFechaNacimiento(int idPaciente, String fechaNacimiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_FECHA_NACIMIENTO)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setString(1, fechaNacimiento);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la fecha de nacimiento del paciente", e);
        }
    }

    public static void updateEdad(int idPaciente, int edad) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_EDAD)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setInt(1, edad);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la edad del paciente", e);
        }
    }

    public static void deleteById(int idPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setInt(1, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el paciente con id: " + idPaciente, e);
        }
    }

    public static void deleteByDni(String dni) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_DNI)) {

            checkStmt.setString(1, dni);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new RuntimeException("El paciente con DNI " + dni + " no existe.");
                }
            }

            pst.setString(1, dni);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el paciente con DNI: " + dni, e);
        }
    }
}
