package DAO;

import baseDatos.ConnectionDB;
import model.TratamientoPaciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoPacienteDAO {
    private final static String SQL_ALL = "SELECT * FROM tratamientopaciente";
    private final static String SQL_INSERT = "INSERT INTO tratamientopaciente (idTratamiento, idPaciente) VALUES (?, ?)";

    public void insert(int idTratamiento, int idPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setInt(1, idTratamiento);
            pst.setInt(2, idPaciente);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en tratamientopaciente", e);
        }
    }

    public List<TratamientoPaciente> findAll(){
        List<TratamientoPaciente> tratamientosPacientes = new ArrayList<TratamientoPaciente>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                TratamientoPaciente tratamientoPaciente = new TratamientoPaciente();
                tratamientoPaciente.setIdPaciente(rs.getInt("idPaciente"));
                tratamientoPaciente.setIdTratamiento(rs.getInt("idTratamiento"));

                tratamientosPacientes.add(tratamientoPaciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientosPacientes;
    }
}
