package update;

import DAO.TratamientoDAO;

public class TestUpdateTratamiento {
    public static void main(String[] args) {
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();

        int idTratamiento = 9;
        String descripcionNueva = "Puente fijo de tres piezas en zona posterior para reemplazar pieza ausente.";

        try{
            tratamientoDAO.updateDescripcion(idTratamiento, descripcionNueva);
            System.out.println("Tratamiento actualizado correctamente.");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
