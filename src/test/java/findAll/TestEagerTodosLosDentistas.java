package findAll;

import DAO.DentistaDAO;
import model.Dentista;

import java.util.List;

public class TestEagerTodosLosDentistas {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();
        List<Object> dentistas = dentistaDAO.findAllEager();

        for (Object dentista : dentistas){
            System.out.println(dentista);
        }
    }
}
