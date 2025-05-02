package DAO;

import baseDatos.ConnectionDB;
import interfaces.CRUDGenericoBBDD;
import model.Dentista;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DentistaDAO {
    private final static String SQL_ALL = "SELECT * FROM Dentista";
//    private final static String SQL_FIND_BY_ID = "SELECT * FROM Dentista WHERE idDentista = ?";
//    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Dentista WHERE nombre = ?";
//    private final static String SQL_INSERT = "INSERT INTO Dentista (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?)";
//    private final static String SQL_UPDATE = "UPDATE INTO";
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
}
