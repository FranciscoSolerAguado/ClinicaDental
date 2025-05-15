package DAO;

import baseDatos.ConnectionDB;
import exceptions.PacienteNoEncontradoException;
import exceptions.TratamientoPacienteNoEncontrado;
import model.TratamientoPaciente;
import utils.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TratamientoPacienteDAO {
    private static TratamientoPacienteDAO instance;
    private static final Logger logger = LoggerUtil.getLogger();

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
    private final static String SQL_UPDATE = "UPDATE TratamientoPaciente SET idPaciente = ?, idTratamiento = ?, detalles = ? WHERE idPaciente = ? AND idTratamiento = ?";
    private final static String SQL_SELECT_BY_PACIENTE = "SELECT * FROM TratamientoPaciente WHERE idPaciente = ?";
    private final static String SQL_DELETE = "DELETE FROM TratamientoPaciente WHERE idPaciente = ? AND idTratamiento = ? AND fechaTratamiento = ?";

   public void insert(TratamientoPaciente tratamientoPaciente) {
       try (Connection con = ConnectionDB.getConnection();
            PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
           pst.setInt(1, tratamientoPaciente.getIdPaciente());
           pst.setInt(2, tratamientoPaciente.getIdTratamiento());
           pst.setDate(3, Date.valueOf(tratamientoPaciente.getFechaTratamiento()));
           pst.setString(4, tratamientoPaciente.getDetalles());
           pst.executeUpdate();
       } catch (SQLException e) {
           logger.severe("Error al insertar el tratamiento-paciente: " + e.getMessage());
           throw new RuntimeException("Error al insertar el tratamiento-paciente", e);
       }
   }

    public void delete(int idPaciente, int idTratamiento, Date fechaTratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idPaciente);
            pst.setInt(2, idTratamiento);
            pst.setDate(3, fechaTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al eliminar el tratamiento-paciente: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el tratamiento-paciente", e);
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
            logger.severe("Error al obtener todos los tratamientos de pacientes: " + e.getMessage());
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
            logger.severe("Error al buscar tratamiento por ID: " + e.getMessage());
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
            logger.severe("Error al buscar tratamientos por paciente: " + e.getMessage());
            throw new RuntimeException("Error al buscar tratamientos por paciente", e);
        }
        return tratamientosPacientes;
    }

    public void update (TratamientoPaciente tratamientoPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
            pst.setInt(1, tratamientoPaciente.getIdPaciente());
            pst.setInt(2, tratamientoPaciente.getIdTratamiento());
            pst.setString(3, tratamientoPaciente.getDetalles());
            pst.setInt(4, tratamientoPaciente.getIdPaciente());
            pst.setInt(5, tratamientoPaciente.getIdTratamiento());
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al actualizar el tratamiento-paciente: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el tratamiento-paciente", e);
        }
    }

}
