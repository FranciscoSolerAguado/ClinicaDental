import DAO.TratamientoDAO;
import model.Tratamiento;

import java.util.List;

public class TestLazyTodosLosTratamientos {
    public static void main(String[] args) {
        List<Tratamiento> tratamientos = TratamientoDAO.findAll();

        for (Tratamiento tratamiento : tratamientos){
            System.out.println(tratamiento);
        }
    }
}
