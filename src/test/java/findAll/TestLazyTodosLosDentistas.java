package findAll;

import DAO.DentistaDAO;
import model.Dentista;

import java.util.List;

public class TestLazyTodosLosDentistas {
    public static void main(String[] args) {
        List<Dentista> dentistas = DentistaDAO.findAll();

        for (Dentista dentista : dentistas){
            System.out.println(dentista.toString());
        }
    }
}
