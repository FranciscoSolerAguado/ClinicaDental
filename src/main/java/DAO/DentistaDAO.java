package DAO;

import baseDatos.ConnectionDB;
import interfaces.CRUDGenericoBBDD;
import model.Dentista;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDAO {
    private final static String SQL_ALL = "SELECT * FROM Dentista";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Dentista WHERE idDentista = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Dentista WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Dentista (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE_NAME = "UPDATE Dentista SET nombre = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_DNI = "UPDATE Dentista SET dni = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_TELEFONO = "UPDATE Dentista SET telefono = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_FECHA_NACIMIENTO = "UPDATE Dentista SET fechaNacimiento = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_EDAD = "UPDATE Dentista SET edad = ? WHERE idDentista = ?";
//    private final static String SQL_DELETE_BY_ID = "DELETE FROM Dentista WHERE idDentista = ?";
//    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Dentista WHERE dni = ?";
    private final static String SQL_SELECT_BY_CITA =
            "SELECT * " +
                    "FROM Dentista " +
                    "WHERE idDentista IN (" +
                    "    SELECT idDentista " +
                    "    FROM Cita " +
                    "    WHERE idCita = ?" +
                    ")";
    private final static String SQL_SELECT_BY_TRATAMIENTO =
            "SELECT * " +
                    "FROM Dentista " +
                    "WHERE idDentista IN (" +
                    "    SELECT idDentista " +
                    "    FROM TratamientoDentista " +
                    "    WHERE idTratamiento = ?" +
                    ")";

    /**
     * Version lazy para obtener todos los dentistas en un list
     *
     * @return un list de dentistas
     */
    public static List<Dentista> findAll() {
        List<Dentista> dentistas = new ArrayList<Dentista>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Dentista dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento("fechaNacimiento");
                dentista.setEdad(rs.getInt("edad"));

                //Version LAZY
                dentistas.add(dentista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentistas;
    }

    /**
     * Version EAGER de obtener todos los dentistas, esta muestra los tratamientos de cada dentista
     *
     * @return la lista de todos los dentistas de la BBDD.
     */
    public static List<Dentista> findAllEager() {
        List<Dentista> dentistas = new ArrayList<>();

        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Dentista dentista = new Dentista();
                int idDentista = rs.getInt("idDentista");
                dentista.setIdDentista(idDentista);
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getString("fechaNacimiento"));
                dentista.setEdad(rs.getInt("edad"));

                // Versión EAGER
                dentista.setTratamientosDentista(TratamientoDAO.findTratamientosByDentista(idDentista));
                dentistas.add(dentista);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentistas;
    }

    public static Dentista findDentistaByCita(int idCita) {
        Dentista dentista = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_CITA)) {
            pst.setInt(1, idCita);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getString("fechaNacimiento"));
                dentista.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public static Dentista findDentistaByTratamiento(int idTratamiento) {
        Dentista dentista = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_TRATAMIENTO)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getString("fechaNacimiento"));
                dentista.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public static Dentista findByIdEager(int idDentista) {
        Dentista dentista = null;
        try (Connection con = ConnectionDB.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idDentista);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getString("fechaNacimiento"));
                dentista.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                dentista.setTratamientosDentista(TratamientoDAO.findTratamientosByDentista(idDentista));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public static Dentista findByNameEager(String nombre) {
        Dentista dentista = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_NAME)) {
            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getString("fechaNacimiento"));
                dentista.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                dentista.setTratamientosDentista(TratamientoDAO.findTratamientosByDentista(dentista.getIdDentista()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public static void insert(Dentista dentista) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, dentista.getNombre());
            pst.setString(2, dentista.getDni());
            pst.setInt(3, dentista.getTelefono());
            pst.setString(4, dentista.getFechaNacimiento());
            pst.setInt(5, dentista.getEdad());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el dentista", e);
        }
    }

    public static void updateNombre(int idDentista, String nombre) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_NAME)) {
            pst.setString(1, nombre);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el nombre del dentista", e);
        }
    }

    public static void updateDni(int idDentista, String dni) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_DNI)) {
            pst.setString(1, dni);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el DNI del dentista", e);
        }
    }

    public static void updateTelefono(int idDentista, int telefono) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_TELEFONO)) {
            pst.setInt(1, telefono);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el teléfono del dentista", e);
        }
    }

    public static void updateFechaNacimiento(int idDentista, String fechaNacimiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_FECHA_NACIMIENTO)) {
            pst.setString(1, fechaNacimiento);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la fecha de nacimiento del dentista", e);
        }
    }

    public static void updateEdad(int idDentista, int edad) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_EDAD)) {
            pst.setInt(1, edad);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la edad del dentista", e);
        }
    }
}
