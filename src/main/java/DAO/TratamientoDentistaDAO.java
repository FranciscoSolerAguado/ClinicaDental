package DAO;

import baseDatos.ConnectionDB;
import model.Cita;
import model.TratamientoDentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDentistaDAO {
    private final static String SQL_INSERT = "INSERT INTO tratamientodentista (idTratamiento, idDentista) VALUES (?, ?)";
    private final static String SQL_ALL = "SELECT * FROM tratamientodentista";

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

    public static List<TratamientoDentista> findAll(){
        List<TratamientoDentista> tratamientosDentistas = new ArrayList<TratamientoDentista>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                TratamientoDentista tratamientoDentista = new TratamientoDentista();
                tratamientoDentista.setIdDentista(rs.getInt("idDentista"));
                tratamientoDentista.setIdTratamiento(rs.getInt("idTratamiento"));

                tratamientosDentistas.add(tratamientoDentista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientosDentistas;
    }
}
