package DAO;

import baseDatos.ConnectionDB;
import exceptions.DentistaNoEncontradoException;
import interfaces.CRUDGenericoBBDD;
import model.Dentista;
import utils.LoggerUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DentistaDAO implements CRUDGenericoBBDD<Dentista> {
    private static DentistaDAO instance;
    private TratamientoDAO tratamientoDAO;
    private static final Logger logger = LoggerUtil.getLogger();

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

    /**
     * Consultas SQL
     */
    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Dentista WHERE idDentista = ?";
    private final static String SQL_ALL = "SELECT * FROM Dentista";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Dentista WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO Dentista (nombre, dni,nColegiado, especialidad, telefono, fechaNacimiento, edad) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Dentista SET nombre = ?, dni = ?, telefono = ?, nColegiado = ?, especialidad = ?, fechaNacimiento = ?, edad = ? WHERE idDentista = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Dentista WHERE idDentista = ?";
    private final static String SQL_SELECT_BY_TRATAMIENTO = "SELECT * FROM Dentista WHERE idDentista IN (SELECT idDentista FROM Tratamiento WHERE idTratamiento = ?)";


    /**
     * Version lazy para obtener todos los dentistas en un list
     *
     * @return un list de dentistas
     */
    @Override
    public List<Object> findAll() {
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
            logger.severe("Error al obtener todos los dentistas: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>(dentistas);
    }

    /**
     * Version EAGER de obtener todos los dentistas, esta muestra los tratamientos de cada dentista
     *
     * @return la lista de todos los dentistas de la BBDD.
     */
    @Override
    public List<Object> findAllEager() {

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
            logger.severe("Error al obtener todos los dentistas: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return new ArrayList<>(dentistas);
    }

    /**
     * Busca un dentista por el del ID del tratamiento.
     *
     * @param idTratamiento el ID del tratamiento por el que se busca el dentista.
     * @return el dentista encontrado o null si no se encuentra.
     */
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
            logger.severe("Error al buscar dentista por tratamiento: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return dentista;
    }

    /**
     * VERSION EAGER
     * Busca un dentista por su nombre.
     *
     * @param nombre el nombre del dentista a buscar.
     * @return el dentista encontrado o null si no se encuentra.
     */
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
            logger.severe("Error al buscar dentista por nombre: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return dentista;
    }

    /**
     * Metodo para insertar un dentista en la base de datos.
     *
     * @param dentista el dentista a insertar.
     *
     */
    @Override
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
            logger.severe("Error al insertar dentista: " + e.getMessage());
            throw new RuntimeException("Error al insertar el dentista", e);
        }
    }

    /**
     * Metodo para actualizar un dentista en la base de datos.
     *
     * @param idDentista el ID del dentista a actualizar.
     * @param dentista   el dentista con los nuevos datos.
     */
    @Override
    public void update(int idDentista, Dentista dentista) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

            // Verificar si el dentista existe
            checkStmt.setInt(1, idDentista);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new DentistaNoEncontradoException("El dentista con id " + idDentista + " no existe.");
                }
            }

            pst.setString(1, dentista.getNombre());
            pst.setString(2, dentista.getDni());
            pst.setInt(3, dentista.getTelefono());
            pst.setString(4, dentista.getnColegiado());
            pst.setString(5, dentista.getEspecialidad());
            pst.setDate(6, java.sql.Date.valueOf(dentista.getFechaNacimiento()));
            pst.setInt(7, dentista.getEdad());
            pst.setInt(8, idDentista);

            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al actualizar dentista: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el dentista", e);
        }
    }

    /**
     * Metodo para eliminar un dentista de la base de datos.
     *
     * @param idDentista el ID del dentista a eliminar.
     */
    @Override
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
            logger.severe("Error al eliminar dentista: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el dentista con id: " + idDentista, e);
        }
    }
}
