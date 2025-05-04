package DAO;

import baseDatos.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TratamientoDentistaDAO {
    private final static String SQL_INSERT = "INSERT INTO tratamientodentista (idTratamiento, idDentista) VALUES (?, ?)";

    public static void insert(int idTratamiento, int idDentista) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setInt(1, idTratamiento);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en tratamientodentista", e);
        }
    }
    // Implement the methods to interact with the database here
}
