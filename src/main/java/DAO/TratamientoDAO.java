package DAO;

import baseDatos.ConnectionDB;
import exceptions.TratamientoNoEncontradoException;
import model.Tratamiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO {
    private static TratamientoDAO instance;
    private final DentistaDAO dentistaDAO;

    private TratamientoDAO() {
        this.dentistaDAO = DentistaDAO.getInstance();
    }

    public static TratamientoDAO getInstance() {
        if (instance == null) {
            instance = new TratamientoDAO();
        }
        return instance;
    }

    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_ALL = "SELECT * FROM Tratamiento";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_INSERT = "INSERT INTO Tratamiento (descripcion, precio, idDentista) VALUES(?, ?, ?)";
    private final static String SQL_UPDATE_DESCRIPCION = "UPDATE Tratamiento SET descripcion = ? WHERE idTratamiento = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_SELECT_BY_DENTISTA = "SELECT * FROM Tratamiento WHERE idDentista = ?";
    private final static String SQL_SELECT_BY_PACIENTE = "SELECT * FROM TratamientoPaciente WHERE idPaciente = ?";

    /**
     * Version lazy para obtener todos los tratamientos en un list
     *
     * @return un list de citas
     */

    public List<Tratamiento> findAll() {
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setIdDentista(rs.getInt("idDentista"));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }

    public List<Tratamiento> findAllEager() {
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setIdDentista(rs.getInt("idDentista"));

                //Version EAGER
                tratamiento.setDentista(dentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));

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
    public List<Tratamiento> findTratamientosByDentista(int idDentistaBuscado) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_DENTISTA)) {
            pst.setInt(1, idDentistaBuscado);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setIdDentista(rs.getInt("idDentista"));

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
    public List<Tratamiento> findTratamientosByPaciente(int idPacienteBuscado) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_PACIENTE)) {
            pst.setInt(1, idPacienteBuscado);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setIdDentista(rs.getInt("idDentista"));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamientos;
    }

    public Tratamiento findByIdEager(int idTratamiento) {
        Tratamiento tratamiento = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setIdDentista(rs.getInt("idDentista"));

                // Cargar el dentista asociado (versión EAGER)
                tratamiento.setDentista(dentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamiento;
    }


    public void insert(Tratamiento tratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, tratamiento.getDescripcion());
            pst.setDouble(2, tratamiento.getPrecio());
            pst.setInt(3, tratamiento.getIdDentista());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el tratamiento", e);
        }
    }

    public void updateDescripcion(int idTratamiento, String descripcion) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_DESCRIPCION)) {


            pst.setString(1, descripcion);
            pst.setInt(2, idTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la descripción del tratamiento", e);
        }
    }

    public void deleteById(int idTratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idTratamiento);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new TratamientoNoEncontradoException("No existe un tratamiento con id: " + idTratamiento);
                }
            }

            pst.setInt(1, idTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el tratamiento con id: " + idTratamiento, e);
        }
    }
}
