package DAO;

import baseDatos.ConnectionDB;
import exceptions.PacienteNoEncontradoException;
import model.TratamientoPaciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoPacienteDAO {
    private static TratamientoPacienteDAO instance;

    private TratamientoPacienteDAO() {
    }

    public static TratamientoPacienteDAO getInstance() {
        if (instance == null) {
            instance = new TratamientoPacienteDAO();
        }
        return instance;
    }

    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_ALL = "SELECT * FROM tratamientopaciente";
    private final static String SQL_INSERT = "INSERT INTO tratamientopaciente (idPaciente, idTratamiento, fechaTratamiento, detalles) VALUES (?, ?, ?, ?)";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM tratamientopaciente WHERE idTratamiento = ?";
    private final static String SQL_UPDATE_BY_ID_PACIENTE = "UPDATE tratamientopaciente SET fechaTratamiento = ?, detalles = ? WHERE idPaciente = ?";
    private final static String SQL_UPDATE_BY_ID_TRATAMIENTO = "UPDATE tratamientopaciente SET idPaciente = ?, fechaTratamiento = ?, detalles = ? WHERE idTratamiento = ?";
    private final static String SQL_SELECT_BY_PACIENTE = "SELECT * FROM TratamientoPaciente WHERE idPaciente = ?";

    public void insert(int idTratamiento, int idPaciente, String fechaTratamiento, String detalles) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setInt(1, idTratamiento);
            pst.setInt(2, idPaciente);
            pst.setString(3, fechaTratamiento);
            pst.setString(4, detalles);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en tratamientopaciente", e);
        }
    }

    public List<TratamientoPaciente> findAll() {
        List<TratamientoPaciente> tratamientosPacientes = new ArrayList<TratamientoPaciente>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                TratamientoPaciente tratamientoPaciente = new TratamientoPaciente();
                tratamientoPaciente.setIdPaciente(rs.getInt("idPaciente"));
                tratamientoPaciente.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamientoPaciente.setFechaTratamiento(rs.getDate("fechaTratamiento").toLocalDate());
                tratamientoPaciente.setDetalles(rs.getString("detalles"));

                tratamientosPacientes.add(tratamientoPaciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientosPacientes;
    }

    public TratamientoPaciente findById(int idTratamiento) {
        TratamientoPaciente tratamientoPaciente = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tratamientoPaciente = new TratamientoPaciente();
                tratamientoPaciente.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamientoPaciente.setIdPaciente(rs.getInt("idPaciente"));
                tratamientoPaciente.setFechaTratamiento(rs.getDate("fechaTratamiento").toLocalDate());
                tratamientoPaciente.setDetalles(rs.getString("detalles"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tratamiento por ID", e);
        }
        return tratamientoPaciente;
    }

    public List<TratamientoPaciente> findTratamientosByPaciente(int idPacienteBuscado) {
        List<TratamientoPaciente> tratamientosPacientes = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_PACIENTE)) {
            pst.setInt(1, idPacienteBuscado);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TratamientoPaciente tratamientoPaciente = new TratamientoPaciente();
                tratamientoPaciente.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamientoPaciente.setIdPaciente(rs.getInt("idPaciente"));
                tratamientoPaciente.setFechaTratamiento(rs.getDate("fechaTratamiento").toLocalDate());
                tratamientoPaciente.setDetalles(rs.getString("detalles"));
                tratamientosPacientes.add(tratamientoPaciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tratamientos por paciente", e);
        }
        return tratamientosPacientes;
    }

    public void updateByIdPaciente(int idPaciente, TratamientoPaciente tratamientoPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_ID_PACIENTE)) {

            checkStmt.setInt(1, idPaciente);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new PacienteNoEncontradoException("El paciente con idPaciente " + idPaciente + " no existe.");
                }
            }
            pst.setInt(1, tratamientoPaciente.getIdTratamiento());
            pst.setString(2, tratamientoPaciente.getFechaTratamiento().toString());
            pst.setString(3, tratamientoPaciente.getDetalles());
            pst.setInt(4, idPaciente);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tratamiento por ID de paciente", e);
        }
    }

    public void updateByIdTratamiento(int idTratamiento, TratamientoPaciente tratamientoPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_BY_ID_TRATAMIENTO)) {

            checkStmt.setInt(1, idTratamiento);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new PacienteNoEncontradoException("El paciente con idPaciente " + idTratamiento + " no existe.");
                }
            }
            pst.setInt(1, tratamientoPaciente.getIdPaciente());
            pst.setString(2, tratamientoPaciente.getFechaTratamiento().toString());
            pst.setString(3, tratamientoPaciente.getDetalles());
            pst.setInt(4, tratamientoPaciente.getIdPaciente());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tratamiento por ID de paciente", e);
        }
    }
}
