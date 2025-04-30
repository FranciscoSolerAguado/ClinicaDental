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
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Dentista WHERE idDentista = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Dentista WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Dentista (nombre, dni, telefono, fechaNacimiento, edad) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE INTO";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Dentista WHERE idDentista = ?";
    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Dentista WHERE dni = ?";

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

                dentista.setDentistas(new ArrayList<Dentista>());
                dentistas.add(dentista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentistas;
    }
}
