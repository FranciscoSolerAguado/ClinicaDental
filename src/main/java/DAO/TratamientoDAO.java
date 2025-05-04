package DAO;

import baseDatos.ConnectionDB;
import model.TipoTratamiento;
import model.Tratamiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO {
    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_ALL = "SELECT * FROM Tratamiento";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Tratamiento WHERE nombrePaciente = ?";
    private final static String SQL_INSERT = "INSERT INTO Tratamiento (tipoTratamiento, nombrePaciente, descripcion, precio) VALUES(?, ?, ?, ?)";
    private final static String SQL_UPDATE_DESCRIPCION = "UPDATE Tratamiento SET descripcion = ? WHERE idTratamiento = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_SELECT_BY_DENTISTA =
            "SELECT * " +
                    "FROM Tratamiento " +
                    "WHERE idTratamiento IN (" +
                    "    SELECT idTratamiento " +
                    "    FROM TratamientoDentista " +
                    "    WHERE idDentista = ?" +
                    ")";
    private final static String SQL_SELECT_BY_PACIENTE =
            "SELECT * " +
                    "FROM Tratamiento " +
                    "WHERE idTratamiento IN (" +
                    "    SELECT idTratamiento " +
                    "    FROM TratamientoPaciente " + // Cambiar a la tabla correcta
                    "    WHERE idPaciente = ?" +
                    ")";

    /**
     * Version lazy para obtener todos los tratamientos en un list
     *
     * @return un list de citas
     */

    public static List<Tratamiento> findAll() {
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setTipoTratamiento(TipoTratamiento.valueOf(rs.getString("tipoTratamiento")));
                tratamiento.setNombrePaciente(rs.getString("nombrePaciente"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public static List<Tratamiento> findAllEager(){
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setTipoTratamiento(TipoTratamiento.valueOf(rs.getString("tipoTratamiento")));
                tratamiento.setNombrePaciente(rs.getString("nombrePaciente"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

                //Version EAGER
                tratamiento.setDentista(DentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    /**
     * Devuelve la lista de tratamientos de un dentista según su Id, en versión Lazy
     *
     * @param idDentistaBuscado
     * @return tratamientos
     */
    public static List<Tratamiento> findTratamientosByDentista(int idDentistaBuscado) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_DENTISTA)) {
            pst.setInt(1, idDentistaBuscado);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setTipoTratamiento(TipoTratamiento.valueOf(rs.getString("tipoTratamiento")));
                tratamiento.setNombrePaciente(rs.getString("nombrePaciente"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamientos;
    }

    /**
     * Devuelve la lista de tratamientos de un paciente según su Id, en versión Lazy
     *
     * @param idPacienteBuscado
     * @return tratamientos
     */
    public static List<Tratamiento> findTratamientosByPaciente(int idPacienteBuscado) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_PACIENTE)) {
            pst.setInt(1, idPacienteBuscado);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setTipoTratamiento(TipoTratamiento.valueOf(rs.getString("tipoTratamiento")));
                tratamiento.setNombrePaciente(rs.getString("nombrePaciente"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamientos;
    }

    public static Tratamiento findByIdEager(int idTratamiento) {
        Tratamiento tratamiento = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setTipoTratamiento(TipoTratamiento.valueOf(rs.getString("tipoTratamiento")));
                tratamiento.setNombrePaciente(rs.getString("nombrePaciente"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

                // Cargar el dentista asociado (versión EAGER)
                tratamiento.setDentista(DentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamiento;
    }

    public static List<Tratamiento> findByNameEager(String nombrePaciente) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_NAME)) {
            pst.setString(1, nombrePaciente);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setTipoTratamiento(TipoTratamiento.valueOf(rs.getString("tipoTratamiento")));
                tratamiento.setNombrePaciente(rs.getString("nombrePaciente"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

                //Cargar el dentista asociado (versión EAGER)
                tratamiento.setDentista(DentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamientos;
    }

    public static void insert(Tratamiento tratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, tratamiento.getTipoTratamiento().toString());
            pst.setString(2, tratamiento.getNombrePaciente());
            pst.setString(3, tratamiento.getDescripcion());
            pst.setDouble(4, tratamiento.getPrecio());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el tratamiento", e);
        }
    }

    public static void updateDescripcion(int idTratamiento, String descripcion) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_DESCRIPCION)) {



            pst.setString(1, descripcion);
            pst.setInt(2, idTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la descripción del tratamiento", e);
        }
    }

    public static void deleteById(int idTratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idTratamiento);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new SQLException("No existe un tratamiento con id: " + idTratamiento);
                }
            }

            pst.setInt(1, idTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el tratamiento con id: " + idTratamiento, e);
        }
    }
}
