package findById;

import DAO.TratamientoDAO;
import model.Tratamiento;

public class TestFindByIDTratamiento {
    public static void main(String[] args) {

        int id = 1;

        Tratamiento tratamiento = TratamientoDAO.findByIdEager(id);

        if (tratamiento != null) {
            System.out.println("Tratamiento encontrado: " + tratamiento);
        } else {
            System.out.println("Tratamiento no encontrado con ID: " + id);
        }
    }
}
