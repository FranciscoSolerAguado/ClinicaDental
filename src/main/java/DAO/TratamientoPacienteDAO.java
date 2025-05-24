package DAO;

import baseDatos.ConnectionDB;
import model.Paciente;
import model.Tratamiento;
import model.TratamientoPaciente;
import utils.LoggerUtil;

import java.lang.ref.PhantomReference;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TratamientoPacienteDAO {
    private static TratamientoPacienteDAO instance;
    private PacienteDAO pacienteDAO;
    private final TratamientoDAO tratamientoDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    private TratamientoPacienteDAO() {
        this.tratamientoDAO = TratamientoDAO.getInstance();// Inicialización de pacienteDAO
        this.pacienteDAO = PacienteDAO.getInstance(); // Inicialización de tratamientoDAO
    }

    public static TratamientoPacienteDAO getInstance() {
        if (instance == null) {
            instance = new TratamientoPacienteDAO();
        }
        return instance;
    }


    public void initialize(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    /**
     * Consultas SQL
     */
    private final static String SQL_ALL = "SELECT * FROM tratamientopaciente";
    private final static String SQL_INSERT = "INSERT INTO tratamientopaciente (idPaciente, idTratamiento, fechaTratamiento, detalles) VALUES (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE TratamientoPaciente SET idPaciente = ?, idTratamiento = ?, detalles = ? WHERE idPaciente = ? AND idTratamiento = ?";
    private final static String SQL_SELECT_BY_PACIENTE = "SELECT * FROM TratamientoPaciente WHERE idPaciente = ?";
    private final static String SQL_DELETE = "DELETE FROM TratamientoPaciente WHERE idPaciente = ? AND idTratamiento = ? AND fechaTratamiento = ?";

    /**
     * Metodo para insertar un tratamiento-paciente
     *
     * @param tratamientoPaciente El tratamiento-paciente a insertar
     */
    public void insert(TratamientoPaciente tratamientoPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setInt(1, tratamientoPaciente.getPaciente().getIdPaciente());
            pst.setInt(2, tratamientoPaciente.getTratamiento().getIdTratamiento());
            pst.setDate(3, Date.valueOf(tratamientoPaciente.getFechaTratamiento()));
            pst.setString(4, tratamientoPaciente.getDetalles());
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al insertar el tratamiento-paciente: " + e.getMessage());
            throw new RuntimeException("Error al insertar el tratamiento-paciente", e);
        }
    }

    /**
     * Metodo para eliminar un tratamiento-paciente
     *
     * @param idPaciente       El id del paciente
     * @param idTratamiento    El id del tratamiento
     * @param fechaTratamiento La fecha del tratamiento
     */
    public void delete(int idPaciente, int idTratamiento, Date fechaTratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idPaciente);
            pst.setInt(2, idTratamiento);
            pst.setDate(3, fechaTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al eliminar el tratamiento-paciente: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el tratamiento-paciente", e);
        }
    }


    /**
     * Metodo para obtener todos los tratamientos de pacientes
     *
     * @return Una lista con todos los tratamientos de pacientes
     */
public List<TratamientoPaciente> findAll() {
    List<TratamientoPaciente> tratamientosPacientes = new ArrayList<>();
    List<Integer> pacienteIds = new ArrayList<>();
    List<Integer> tratamientoIds = new ArrayList<>();

    try (Connection con = ConnectionDB.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(SQL_ALL)) {

        while (rs.next()) {
            TratamientoPaciente tratamientoPaciente = new TratamientoPaciente();
            tratamientoPaciente.setFechaTratamiento(rs.getDate("fechaTratamiento").toLocalDate());
            tratamientoPaciente.setDetalles(rs.getString("detalles"));

            // Guardar IDs temporalmente
            pacienteIds.add(rs.getInt("idPaciente"));
            tratamientoIds.add(rs.getInt("idTratamiento"));

            tratamientosPacientes.add(tratamientoPaciente);
        }
    } catch (SQLException e) {
        logger.severe("Error al obtener todos los tratamientos de pacientes: " + e.getMessage());
        throw new RuntimeException("Error al obtener todos los tratamientos de pacientes", e);
    }

    /**
     * Error Operation not allowed after ResultSet que ocurre porque el
     * ResultSet se está cerrando antes de que se complete su uso, después de eliminar el idPaciente y idTratamiento,
     * asi que se ha creado una lista temporal pacienteIds y tratamientoIds para almacenar los ids de los pacientes y tratamientos
     * Esta es la única solución que he encontrado para evitar el error
     */
    // Asignar los objetos relacionados después de cerrar el ResultSet
    for (int i = 0; i < tratamientosPacientes.size(); i++) {
        TratamientoPaciente tratamientoPaciente = tratamientosPacientes.get(i);
        tratamientoPaciente.setPaciente(pacienteDAO.findByIdEager(pacienteIds.get(i)));
        tratamientoPaciente.setTratamiento(tratamientoDAO.findById(tratamientoIds.get(i)));
    }

    return tratamientosPacientes;
}

    /**
     * Metodo para buscar tratamientos por paciente
     *
     * @param idPacienteBuscado El id del paciente a buscar
     * @return Una lista con los tratamientos del paciente buscado
     */
public List<TratamientoPaciente> findTratamientosByPaciente(int idPacienteBuscado) {
    List<TratamientoPaciente> tratamientosPacientes = new ArrayList<>();
    List<Integer> tratamientoIds = new ArrayList<>(); // Lista temporal para almacenar los IDs de tratamientos

    try (Connection con = ConnectionDB.getConnection();
         PreparedStatement pst = con.prepareStatement(SQL_SELECT_BY_PACIENTE)) {
        pst.setInt(1, idPacienteBuscado);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                TratamientoPaciente tratamientoPaciente = new TratamientoPaciente();
                tratamientoPaciente.setFechaTratamiento(rs.getDate("fechaTratamiento").toLocalDate());
                tratamientoPaciente.setDetalles(rs.getString("detalles"));
                tratamientoIds.add(rs.getInt("idTratamiento")); // Guardar el ID del tratamiento
                tratamientosPacientes.add(tratamientoPaciente); // Agregar a la lista
            }
        }
    } catch (SQLException e) {
        logger.severe("Error al buscar tratamientos por paciente: " + e.getMessage());
        throw new RuntimeException("Error al buscar tratamientos por paciente", e);
    }

    /**
     * Error Operation not allowed after ResultSet que ocurre porque el
     * ResultSet se está cerrando antes de que se complete su uso, después de eliminar el idTratamiento,
     * asi que se ha creado una lista temporal tratamientosIds para almacenar los ids de los tratamientos
     * Esta es la única solución que he encontrado para evitar el error
     */
    // Asignar los tratamientos después de cerrar el ResultSet
    for (int i = 0; i < tratamientosPacientes.size(); i++) {
        Tratamiento tratamiento = tratamientoDAO.findById(tratamientoIds.get(i));
        tratamientosPacientes.get(i).setTratamiento(tratamiento);
        tratamientosPacientes.get(i).setPaciente(pacienteDAO.findByIdEager(idPacienteBuscado));
    }

    return tratamientosPacientes;
}

    /**
     * Metodo para actualizar un tratamiento-paciente
     *
     * @param tratamientoPaciente El tratamiento-paciente a actualizar con los datos ya pasados
     */
    public void update(TratamientoPaciente tratamientoPaciente) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
            pst.setInt(1, tratamientoPaciente.getPaciente().getIdPaciente());
            pst.setInt(2, tratamientoPaciente.getTratamiento().getIdTratamiento());
            pst.setString(3, tratamientoPaciente.getDetalles());
            pst.setInt(4, tratamientoPaciente.getPaciente().getIdPaciente());
            pst.setInt(5, tratamientoPaciente.getTratamiento().getIdTratamiento());
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al actualizar el tratamiento-paciente: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el tratamiento-paciente", e);
        }
    }

}
