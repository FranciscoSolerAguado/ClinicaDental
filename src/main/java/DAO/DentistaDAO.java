package DAO;

import baseDatos.ConnectionDB;
import exceptions.DentistaNoEncontradoException;
import model.Dentista;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDAO {
    private static DentistaDAO instance;
    private TratamientoDAO tratamientoDAO;

    private DentistaDAO() {

    }

    public static DentistaDAO getInstance() {
        if (instance == null) {
            instance = new DentistaDAO();
        }
        return instance;
    }

    public TratamientoDAO getTratamientoDAO() {
        if (tratamientoDAO == null) {
            tratamientoDAO = TratamientoDAO.getInstance();
        }
        return tratamientoDAO;
    }

    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Dentista WHERE idDentista = ?";
    private final static String SQL_ALL = "SELECT * FROM Dentista";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Dentista WHERE idDentista = ?";
    private final static String SQL_FIND_BY_DNI = "SELECT * FROM Dentista WHERE dni = ?";
    private final static String SQL_FIND_BY_NCOLEGIADO = "SELECT * FROM Dentista WHERE nColegiado = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Dentista WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Dentista (nombre, dni,nColegiado, especialidad, telefono, fechaNacimiento, edad) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Dentista SET nombre = ?, dni = ?, telefono = ?, fechaNacimiento = ?, edad = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_NAME = "UPDATE Dentista SET nombre = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_DNI = "UPDATE Dentista SET dni = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_ESPECIALIDAD = "UPDATE Dentista SET especialidad = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_TELEFONO = "UPDATE Dentista SET telefono = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_FECHA_NACIMIENTO = "UPDATE Dentista SET fechaNacimiento = ? WHERE idDentista = ?";
    private final static String SQL_UPDATE_EDAD = "UPDATE Dentista SET edad = ? WHERE idDentista = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Dentista WHERE idDentista = ?";
    private final static String SQL_DELETE_BY_DNI = "DELETE FROM Dentista WHERE dni = ?";
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
                    "    FROM Tratamiento " +
                    "    WHERE idTratamiento = ?" +
                    ")";

    /**
     * Version lazy para obtener todos los dentistas en un list
     *
     * @return un list de dentistas
     */
    public List<Dentista> findAll() {
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
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
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
    public List<Dentista> findAllEager() {

        if (tratamientoDAO == null) {
            tratamientoDAO = getTratamientoDAO();
        }

        List<Dentista> dentistas = new ArrayList<>();

        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Dentista dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));


                // Versión EAGER
                int idDentista = dentista.getIdDentista();
                dentista.setTratamientosDentista(tratamientoDAO.findTratamientosByDentista(idDentista));
                dentistas.add(dentista);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentistas;
    }

    public Dentista findDentistaByCita(int idCita) {
        Dentista dentista = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_CITA)) {
            pst.setInt(1, idCita);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public Dentista findDentistaByTratamiento(int idTratamiento) {
        Dentista dentista = null;
        try (java.sql.PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(SQL_SELECT_BY_TRATAMIENTO)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public Dentista findByIdEager(int idDentista) {

        if (tratamientoDAO == null) {
            tratamientoDAO = getTratamientoDAO();
        }

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
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                dentista.setTratamientosDentista(tratamientoDAO.findTratamientosByDentista(idDentista));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public Dentista findByNameEager(String nombre) {
        if (tratamientoDAO == null) {
            tratamientoDAO = getTratamientoDAO();
        }
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
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                dentista.setTratamientosDentista(tratamientoDAO.findTratamientosByDentista(dentista.getIdDentista()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public Dentista findByDNIEager (String dni) {
        if (tratamientoDAO == null) {
            tratamientoDAO = getTratamientoDAO();
        }
        Dentista dentista = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_DNI)) {
            pst.setString(1, dni);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                dentista.setTratamientosDentista(tratamientoDAO.findTratamientosByDentista(dentista.getIdDentista()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public Dentista findByNColegiadoEager (String nColegiado) {
        if (tratamientoDAO == null) {
            tratamientoDAO = getTratamientoDAO();
        }
        Dentista dentista = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_NCOLEGIADO)) {
            pst.setString(1, nColegiado);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dentista = new Dentista();
                dentista.setIdDentista(rs.getInt("idDentista"));
                dentista.setNombre(rs.getString("nombre"));
                dentista.setDni(rs.getString("dni"));
                dentista.setnColegiado(rs.getString("nColegiado"));
                dentista.setEspecialidad(rs.getString("especialidad"));
                dentista.setTelefono(rs.getInt("telefono"));
                dentista.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dentista.setEdad(rs.getInt("edad"));

                // Cargar los tratamientos asociados (versión EAGER)
                dentista.setTratamientosDentista(tratamientoDAO.findTratamientosByDentista(dentista.getIdDentista()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dentista;
    }

    public void insert(Dentista dentista) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, dentista.getNombre());
            pst.setString(2, dentista.getDni());
            pst.setString(3, dentista.getnColegiado());
            pst.setString(4, dentista.getEspecialidad());
            pst.setInt(5, dentista.getTelefono());
            pst.setString(6, java.sql.Date.valueOf(dentista.getFechaNacimiento()).toString());
            pst.setInt(7, dentista.getEdad());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el dentista", e);
        }
    }

    public void update (Dentista dentista) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

            checkStmt.setInt(1, dentista.getIdDentista());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + dentista.getIdDentista() + " no existe.");
                }
            }
            pst.setString(1, dentista.getNombre());
            pst.setString(2, dentista.getDni());
            pst.setInt(3, dentista.getTelefono());
            pst.setString(4, dentista.getnColegiado());
            pst.setString(5, dentista.getEspecialidad());
            pst.setString(6, java.sql.Date.valueOf(dentista.getFechaNacimiento()).toString());
            pst.setInt(7, dentista.getEdad());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el dentista", e);
        }
    }

    public void updateNombre(int idDentista, String nombre) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_NAME)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setString(1, nombre);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el nombre del dentista", e);
        }
    }

    public void updateDni(int idDentista, String dni) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_DNI)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setString(1, dni);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el DNI del dentista", e);
        }
    }

    public void updateEspecialidad(int idDentista, String especialidad) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_ESPECIALIDAD)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setString(1, especialidad);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la especialidad del dentista", e);
        }
    }

    public void updateTelefono(int idDentista, int telefono) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_TELEFONO)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setInt(1, telefono);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el teléfono del dentista", e);
        }
    }

    public void updateFechaNacimiento(int idDentista, String fechaNacimiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_FECHA_NACIMIENTO)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setString(1, fechaNacimiento);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la fecha de nacimiento del dentista", e);
        }
    }

    public void updateEdad(int idDentista, int edad) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE_EDAD)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setInt(1, edad);
            pst.setInt(2, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la edad del dentista", e);
        }
    }

    public void deleteById(int idDentista) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setInt(1, idDentista);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el dentista con id: " + idDentista, e);
        }
    }

    public void deleteByDni(String dni) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_DNI)) {

            checkStmt.setString(1, dni);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con DNI " + dni + " no existe.");
                }
            }

            pst.setString(1, dni);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el dentista con DNI: " + dni, e);
        }
    }

}
