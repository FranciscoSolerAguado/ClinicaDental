package DAO;

import baseDatos.ConnectionDB;
import exceptions.CitaNoEncontradaException;
import model.Cita;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Cita WHERE idCita = ?";
    private final static String SQL_ALL = "SELECT * FROM Cita";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Cita WHERE idCita = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Cita WHERE nombrePaciente = ?";
    private final static String SQL_INSERT = "INSERT INTO Cita (idPaciente, idCita, paciente, dentista, fecha) VALUES(?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE_FECHA = "UPDATE Cita SET fecha = ? WHERE idCita = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Cita WHERE idCita = ?";

    /**
     * Version lazy para obtener todas las citas en un list
     * @return un list de citas
     */

    public static List<Cita> findAll(){
        List<Cita> citas = new ArrayList<Cita>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("idCita"));
                cita.setIdPaciente(rs.getInt("idPaciente"));
                cita.setIdDentista(rs.getInt("idCita"));
                cita.setNombrePaciente(rs.getString("nombrePaciente"));
                cita.setNombreDentista(rs.getString("nombreDentista"));
                cita.setFecha(rs.getString("fecha"));

                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    /**
     * Version EAGER que almacena el dentista y el paciente
     * @return
     */

    public static List<Cita> findAllEager(){
        List<Cita> citas = new ArrayList<Cita>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Cita cita = new Cita();
                int idCita = rs.getInt("idCita");
                cita.setIdPaciente(rs.getInt("idPaciente"));
                cita.setIdDentista(rs.getInt("idCita"));
                cita.setNombrePaciente(rs.getString("nombrePaciente"));
                cita.setNombreDentista(rs.getString("nombreDentista"));
                cita.setFecha(rs.getString("fecha"));

                // Cargar el paciente y el dentista asociados (versión EAGER)
                cita.setDentista(DentistaDAO.findDentistaByCita(idCita));
                cita.setPaciente(PacienteDAO.findPacienteByCita(idCita));

                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public static Cita findByIdEager(int idCita) {
        Cita cita = null;
        try (Connection con = ConnectionDB.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idCita);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                cita = new Cita();
                cita.setIdCita(rs.getInt("idCita"));
                cita.setIdPaciente(rs.getInt("idPaciente"));
                cita.setIdDentista(rs.getInt("idCita"));
                cita.setNombrePaciente(rs.getString("nombrePaciente"));
                cita.setNombreDentista(rs.getString("nombreDentista"));
                cita.setFecha(rs.getString("fecha"));

                // Cargar el paciente y el dentista asociados (versión EAGER)
                cita.setDentista(DentistaDAO.findDentistaByCita(idCita));
                cita.setPaciente(PacienteDAO.findPacienteByCita(idCita));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cita;
    }

    public static List<Cita> findByNameEager(String nombrePaciente) {
        List<Cita> citas = new ArrayList<>();
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_NAME)) {
            pst.setString(1, nombrePaciente);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                int idCita = rs.getInt("idCita");
                cita.setIdCita(idCita);
                cita.setIdPaciente(rs.getInt("idPaciente"));
                cita.setIdDentista(rs.getInt("idCita"));
                cita.setNombrePaciente(rs.getString("nombrePaciente"));
                cita.setNombreDentista(rs.getString("nombreDentista"));
                cita.setFecha(rs.getString("fecha"));

                // Cargar el paciente y el dentista asociados (versión EAGER)
                cita.setDentista(DentistaDAO.findDentistaByCita(idCita));
                cita.setPaciente(PacienteDAO.findPacienteByCita(idCita));

                citas.add(cita);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return citas;
    }

    public static void insert(Cita cita) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setInt(1, cita.getIdPaciente());
            pst.setInt(2, cita.getIdDentista());
            pst.setString(3, cita.getNombrePaciente());
            pst.setString(4, cita.getNombreDentista());
            pst.setString(5, cita.getFecha());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar la cita", e);
        }
    }

    public static void deleteById(int idCita) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idCita);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new CitaNoEncontradaException("La cita con id " + idCita + " no existe.");
                }
            }

            pst.setInt(1, idCita);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la cita con id: " + idCita, e);
        }
    }

    public static void updateFecha(int idCita, String nuevaFecha) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_FECHA)) {

            checkStmt.setInt(1, idCita);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new CitaNoEncontradaException("La cita con id " + idCita + " no existe.");
                }
            }

            pst.setString(1, nuevaFecha);
            pst.setInt(2, idCita);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la fecha de la cita con id: " + idCita, e);
        }
    }
}
