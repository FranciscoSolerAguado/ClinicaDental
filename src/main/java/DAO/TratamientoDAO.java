package DAO;

import baseDatos.ConnectionDB;
import exceptions.PacienteNoEncontradoException;
import exceptions.TratamientoNoEncontradoException;
import interfaces.CRUDGenericoBBDD;
import model.Tratamiento;
import utils.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TratamientoDAO implements CRUDGenericoBBDD<Tratamiento> {
    private static TratamientoDAO instance;
    private final DentistaDAO dentistaDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    private TratamientoDAO() {
        this.dentistaDAO = DentistaDAO.getInstance();
    }

    public static TratamientoDAO getInstance() {
        if (instance == null) {
            instance = new TratamientoDAO();
        }
        return instance;
    }

    /**
     * Consultas SQL
     */
    private final static String SQL_CHECK = "SELECT COUNT(*) FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_ALL = "SELECT * FROM Tratamiento";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_FIND_BY_DESCRIPCION = "SELECT * FROM Tratamiento WHERE descripcion = ?";
    private final static String SQL_FIND_DESCRIPCION_BY_ID = "SELECT descripcion FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_INSERT = "INSERT INTO Tratamiento (descripcion, precio, idDentista) VALUES(?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Tratamiento SET descripcion = ?, precio = ?, idDentista = ? WHERE idTratamiento = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM Tratamiento WHERE idTratamiento = ?";
    private final static String SQL_SELECT_BY_DENTISTA = "SELECT * FROM Tratamiento WHERE idDentista = ?";

    /**
     * Version lazy para obtener todos los tratamientos en un list
     *
     * @return un list de citas
     */
@Override
public List<Object> findAll() {
    List<Object> tratamientos = new ArrayList<>();
    List<Integer> dentistaIds = new ArrayList<>(); // Lista temporal para almacenar los IDs de dentistas

    try (Connection con = ConnectionDB.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(SQL_ALL)) {

        while (rs.next()) {
            Tratamiento tratamiento = new Tratamiento();
            tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
            tratamiento.setDescripcion(rs.getString("descripcion"));
            tratamiento.setPrecio(rs.getDouble("precio"));
            dentistaIds.add(rs.getInt("idDentista")); // Guardar el ID del dentista temporalmente
            tratamientos.add(tratamiento); // Agregar el tratamiento a la lista
        }

    } catch (SQLException e) {
        logger.severe("Error al obtener todos los tratamientos: " + e.getMessage());
        throw new RuntimeException(e);
    }

    /**
     * Me estaba dando el error Operation not allowed after ResultSet que ocurre porque el
     * ResultSet se está cerrando antes de que se complete su uso, después de eliminar el idDentista,
     * asi que se ha creado una lista temporal dentistaIds para almacenar los ids de los dentistas
     * Esta es la única solución que he encontrado para evitar el error
     */
    // Asignar los dentistas después de cerrar el ResultSet
    for (int i = 0; i < tratamientos.size(); i++) {
        Tratamiento tratamiento = (Tratamiento) tratamientos.get(i);
        int idDentista = dentistaIds.get(i);
        tratamiento.setDentista(dentistaDAO.findById(idDentista));
    }

    return tratamientos;
}

    /**
     * Version EAGER para obtener todos los tratamientos en un list
     *
     * @return un list de tratamientos
     */
    @Override
    public List<Object> findAllEager() {
        List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
        Connection con = ConnectionDB.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_ALL);
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setDentista(dentistaDAO.findById(rs.getInt("idDentista")));

                //Version EAGER
                tratamiento.setDentista(dentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));

                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener todos los tratamientos: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>(tratamientos);
    }

    /**
     * Devuelve un tratamiento según su id, en versión Lazy
     *
     * @param idTratamiento el id del tratamiento a buscar
     * @return tratamiento o null si no existe
     */
    public Tratamiento findById(int idTratamiento) {
        Tratamiento tratamiento = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));

            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el tratamiento por ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return tratamiento;
    }

    /**
     * Devuelve un tratamiento según su descripción, en versión EAGER
     *
     * @param descripcion la descripción del tratamiento
     * @return tratamiento o null si no existe
     */
    public Tratamiento findByDescripcionEager(String descripcion) {
        Tratamiento tratamiento = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_DESCRIPCION)) {
            pst.setString(1, descripcion);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                tratamiento.setDentista(dentistaDAO.findById(rs.getInt("idDentista")));

                // Cargar el dentista asociado (versión EAGER)
                tratamiento.setDentista(dentistaDAO.findDentistaByTratamiento(tratamiento.getIdTratamiento()));
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el tratamiento por descripción: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return tratamiento;
    }

    /**
     * Devuelve una descripción de un tratamiento según su id
     *
     * @param idTratamiento el id del tratamiento a buscar
     * @return tratamiento o null si no existe
     */
    public String findDescripcionById(int idTratamiento) {
        String descripcion = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_DESCRIPCION_BY_ID)) {
            pst.setInt(1, idTratamiento);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                descripcion = rs.getString("descripcion");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener la descripción del tratamiento por ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return descripcion;
    }

    /**
     * Devuelve la lista de tratamientos de un dentista según su Id, en versión Lazy
     *
     * @param idDentistaBuscado
     * @return tratamientos
     */
public List<Tratamiento> findTratamientosByDentista(int idDentistaBuscado) {
    List<Tratamiento> tratamientos = new ArrayList<>();
    String query = "SELECT * FROM Tratamiento WHERE idDentista = ?";
    List<Integer> dentistaIds = new ArrayList<>();

    try (Connection con = ConnectionDB.getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {
        pst.setInt(1, idDentistaBuscado);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("idTratamiento"));
                tratamiento.setDescripcion(rs.getString("descripcion"));
                tratamiento.setPrecio(rs.getDouble("precio"));
                dentistaIds.add(rs.getInt("idDentista")); // Guardar idDentista temporalmente
                tratamientos.add(tratamiento);
            }
        }
    } catch (SQLException e) {
        logger.severe("Error al obtener tratamientos por dentista: " + e.getMessage());
        throw new RuntimeException(e);
    }

    /**
     * Me estaba dando mismo que en el findAll
     * error Operation not allowed after ResultSet que ocurre porque el
     * ResultSet se está cerrando antes de que se complete su uso, después de eliminar el idDentista,
     * asi que se ha creado una lista temporal dentistaIds para almacenar los ids de los dentistas
     * Esta es la única solución que he encontrado para evitar el error
     */
    // Asignar los dentistas después de cerrar el ResultSet
    for (int i = 0; i < tratamientos.size(); i++) {
        int idDentista = dentistaIds.get(i);
        tratamientos.get(i).setDentista(dentistaDAO.findById(idDentista));
    }

    return tratamientos;
}


    /**
     * Metodo para insertar un tratamiento
     * @param tratamiento el tratamiento a insertar con los datos necesarios
     */
    @Override
    public void insert(Tratamiento tratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, tratamiento.getDescripcion());
            pst.setDouble(2, tratamiento.getPrecio());
            pst.setInt(3, tratamiento.getDentista().getIdDentista());
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al insertar el tratamiento: " + e.getMessage());
            throw new RuntimeException("Error al insertar el tratamiento", e);
        }
    }

    /**
     * Metodo para actualizar un tratamiento
     * @param idTratamiento el id del tratamiento a actualizar
     * @param tratamiento el tratamiento con los datos a actualizar
     */
    @Override
    public void update(int idTratamiento, Tratamiento tratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
            checkStmt.setInt(1, idTratamiento);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new PacienteNoEncontradoException("El paciente con idPaciente " + idTratamiento + " no existe.");
                }
            }
            pst.setString(1, tratamiento.getDescripcion());
            pst.setDouble(2, tratamiento.getPrecio());
            pst.setInt(3, tratamiento.getDentista().getIdDentista());
            pst.setInt(4, idTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al actualizar el tratamiento: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el tratamiento", e);
        }
    }

    /**
     * Metodo para eliminar un tratamiento
     * @param idTratamiento el id del tratamiento a eliminar
     */
    @Override
    public void deleteById(int idTratamiento) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(SQL_CHECK);
             PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_ID)) {

            checkStmt.setInt(1, idTratamiento);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    throw new TratamientoNoEncontradoException("No existe un tratamiento con id: " + idTratamiento);
                }
            }

            pst.setInt(1, idTratamiento);
            pst.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error al eliminar el tratamiento: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el tratamiento con id: " + idTratamiento, e);
        }
    }
}
