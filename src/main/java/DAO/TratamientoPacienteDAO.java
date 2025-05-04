package DAO;

import baseDatos.ConnectionDB;

import java.sql.*;

public class TratamientoPacienteDAO {
    private final static String SQL_INSERT = "INSERT INTO tratamientopaciente (idTratamiento, idPaciente) VALUES (?, ?)";

    public static void insert(int idTratamiento, int idPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setInt(1, idTratamiento);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en tratamientopaciente", e);
        }
    }
}
