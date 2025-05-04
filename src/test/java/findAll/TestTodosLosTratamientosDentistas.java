package findAll;

import DAO.TratamientoDentistaDAO;
import model.TratamientoDentista;

import java.util.List;

public class TestTodosLosTratamientosDentistas {
    public static void main(String[] args) {
        List<TratamientoDentista> tratamientos = TratamientoDentistaDAO.findAll();
        for (TratamientoDentista t : tratamientos) {
            System.out.println(t);
        }
    }
}
