package findAll;

import DAO.TratamientoDAO;
import model.Tratamiento;

import java.util.List;

public class TestEagerTodosLosTratamientos {
    public static void main(String[] args) {
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
        List<Object> tratamientos = tratamientoDAO.findAllEager();

        for (Object tratamiento : tratamientos) {
            System.out.println("Tratamiento: " + tratamiento);
        }
    }
}
