package findAll;

import DAO.DentistaDAO;
import model.Dentista;

import java.util.List;

public class TestEagerTodosLosDentistas {
    public static void main(String[] args) {
        List<Dentista> dentistas = DentistaDAO.findAllEager();

        for (Dentista dentista : dentistas){
            System.out.println(dentista);
        }
    }
}
