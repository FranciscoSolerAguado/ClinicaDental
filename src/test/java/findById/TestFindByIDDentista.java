package findById;

import DAO.DentistaDAO;
import model.Dentista;

public class TestFindByIDDentista {
    public static void main(String[] args) {
        int id = 1;
        Dentista dentista = DentistaDAO.findByIdEager(id);

        if (dentista != null) {
            System.out.println("Dentista encontrado: " + dentista);
        } else {
            System.out.println("Dentista no encontrado con ID: " + id);
        }
    }
}
