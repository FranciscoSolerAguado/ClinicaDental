package findAll;

import DAO.TratamientoDAO;
import model.Tratamiento;

import java.util.List;

public class TestLazyTodosLosTratamientos {
    public static void main(String[] args) {
        TratamientoDAO tratamientoDAO = TratamientoDAO.getInstance();
        List<Tratamiento> tratamientos = tratamientoDAO.findAll();

        for (Tratamiento tratamiento : tratamientos){
            System.out.println(tratamiento);
        }
    }
}
