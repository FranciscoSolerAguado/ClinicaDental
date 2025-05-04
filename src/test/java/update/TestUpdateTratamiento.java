package update;

import DAO.TratamientoDAO;

import java.sql.SQLException;

public class TestUpdateTratamiento {
    public static void main(String[] args) {
        int idTratamiento = 9;
        String descripcionNueva = "Puente fijo de tres piezas en zona posterior para reemplazar pieza ausente.";

        try{
            TratamientoDAO.updateDescripcion(idTratamiento, descripcionNueva);
            System.out.println("Tratamiento actualizado correctamente.");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
