package DAO;

import baseDatos.ConnectionDB;
import model.TipoTratamiento;
import model.Tratamiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAO {
    private final static String SQL_ALL = "SELECT * FROM Tratamiento";
    //    private final static String SQL_FIND_BY_ID = "SELECT * FROM Tramiento WHERE idTratamiento = ?";
//    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Tratamiento WHERE nombrePaciente = ?";
//    private final static String SQL_INSERT = "INSERT INTO Tratamiento (tipoTratamiento, nombrePaciente, descripcion, precio) VALUES(?)";
//    private final static String SQL_UPDATE = "UPDATE INTO";
//    private final static String SQL_DELETE_BY_ID = "DELETE FROM Tratamiento WHERE idTratamiento = ?";
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
}
