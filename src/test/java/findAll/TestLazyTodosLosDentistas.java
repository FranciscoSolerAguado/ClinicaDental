package findAll;

import DAO.DentistaDAO;
import model.Dentista;

import java.util.List;

public class TestLazyTodosLosDentistas {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();
        List<Dentista> dentistas = dentistaDAO.findAll();

        for (Dentista dentista : dentistas){
            System.out.println(dentista.toString());
        }
    }
}
