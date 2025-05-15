package DAO;

import baseDatos.ConnectionDB;
import exceptions.PacienteNoEncontradoException;
import interfaces.CRUDGenericoBBDD;
import model.Paciente;
import utils.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PacienteDAO implements CRUDGenericoBBDD<Paciente> {
    private static PacienteDAO instance;
    private TratamientoDAO tratamientoDAO;
    private TratamientoPacienteDAO tratamientoPacienteDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    private PacienteDAO() {
        this.tratamientoPacienteDAO = TratamientoPacienteDAO.getInstance();
    }

    public static PacienteDAO getInstance() {
        if (instance == null) {
            instance = new PacienteDAO();
        }
        return instance;
    }

    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_ALL = "SELECT * FROM Paciente";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_FIND_BY_DNI = "SELECT * FROM Paciente WHERE dni = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Paciente WHERE nombre = ?";
    private final static String SQL_FIND_NAME_BY_ID = "SELECT nombre FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_INSERT = "INSERT INTO Paciente (nombre, dni, telefono, alergias, fechaNacimiento, edad) VALUES(?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Paciente SET nombre = ?, dni = ?, telefono = ?, alergias = ?, fechaNacimiento = ?, edad = ? WHERE idPaciente = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Paciente WHERE idPaciente = ?";
    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Paciente WHERE dni = ?";
    private final static String SQL_SELECT_BY_TRATAMIENTO = "SELECT * FROM Paciente WHERE idPaciente IN (SELECT idPaciente FROM TratamientoDentista WHERE idTratamiento = ?)";

    /**
     * Version lazy para obtener todos los pacientes en un list
     *
     * @return un list de pacientes
     */
    @Override
    public List<Object> findAll() {
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
                paciente.setAlergias(rs.getString("alergias"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                paciente.setEdad(rs.getInt("edad"));

                //VERSION LAZY
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener todos los pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>(pacientes);
    }

    /**
     * Version EAGER de obtener todos los pacientes, esta muestra los tratamientos de cada paciente
     *
     * @return la lista de todos los pacientes de la BBDD.
     */
    @Override
    public List<Object> findAllEager() {
        List<Paciente> pacientes = new ArrayList<>();


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
                paciente.setAlergias(rs.getString("alergias"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                paciente.setEdad(rs.getInt("edad"));

                int idPaciente = paciente.getIdPaciente();

                // Versión EAGER
                paciente.setTratamientosPaciente(tratamientoPacienteDAO.findTratamientosByPaciente(idPaciente));
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener todos los pacientes: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return new ArrayList<>(pacientes);
    }

    public String findNameById(int idPaciente) {
        String nombre = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_FIND_NAME_BY_ID)) {
            pst.setInt(1, idPaciente);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el nombre del paciente por ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return nombre;
    }

    public Paciente findPacienteByTratamiento(int idTratamiento) {
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
                paciente.setAlergias(rs.getString("alergias"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                paciente.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el paciente por tratamiento: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return paciente;
    }

    public Paciente findByIdEager(int idPaciente) {
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
                paciente.setAlergias(rs.getString("alergias"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                paciente.setEdad(rs.getInt("edad"));

                // Versión EAGER
                paciente.setTratamientosPaciente(tratamientoPacienteDAO.findTratamientosByPaciente(paciente.getIdPaciente()));
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el paciente por ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return paciente;
    }

    public Paciente findByNameEager(String nombre) {
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
                paciente.setAlergias(rs.getString("alergias"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                paciente.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                paciente.setTratamientosPaciente(tratamientoPacienteDAO.findTratamientosByPaciente(paciente.getIdPaciente()));
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el paciente por nombre: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return paciente;
    }

    public Paciente findByDNIEager(String dni) {
        Paciente paciente = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_DNI)) {
            pst.setString(1, dni);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getInt("telefono"));
                paciente.setAlergias(rs.getString("alergias"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                paciente.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                paciente.setTratamientosPaciente(tratamientoPacienteDAO.findTratamientosByPaciente(paciente.getIdPaciente()));
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el paciente por DNI: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return paciente;
    }

    @Override
    public void insert(Paciente paciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, paciente.getNombre());
            pst.setString(2, paciente.getDni());
            pst.setInt(3, paciente.getTelefono());
            pst.setString(4, paciente.getAlergias());
            pst.setString(5, java.sql.Date.valueOf(paciente.getFechaNacimiento()).toString());
            pst.setInt(6, paciente.getEdad());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el paciente", e);
        }
    }

    @Override
    public void update(int idPaciente, Paciente paciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

            // Verificar si el paciente existe
            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new PacienteNoEncontradoException("El paciente con id " + idPaciente + " no existe.");
                }
            }

            // Asignar los parámetros en el orden correcto
            pst.setString(1, paciente.getNombre());
            pst.setString(2, paciente.getDni());
            pst.setInt(3, paciente.getTelefono());
            pst.setString(4, paciente.getAlergias());
            pst.setDate(5, java.sql.Date.valueOf(paciente.getFechaNacimiento()));
            pst.setInt(6, paciente.getEdad());
            pst.setInt(7, idPaciente);

            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al actualizar paciente: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el paciente", e);
        }
    }

    @Override
    public void deleteById(int idPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new PacienteNoEncontradoException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }

            pst.setInt(1, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al eliminar el paciente: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el paciente con id: " + idPaciente, e);
        }
    }

    public void deleteByDni(String dni) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_DNI)) {

            checkStmt.setString(1, dni);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new PacienteNoEncontradoException("El paciente con DNI " + dni + " no existe.");
                }
            }

            pst.setString(1, dni);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al eliminar el paciente: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el paciente con DNI: " + dni, e);
        }
    }
}
