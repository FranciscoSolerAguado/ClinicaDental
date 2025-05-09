package findById;

import DAO.DentistaDAO;
import model.Dentista;

public class TestFindByIDDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();
        int id = 10;
        Dentista dentista = dentistaDAO.findByIdEager(id);

        if (dentista != null) {
            System.out.println("Dentista encontrado: " + dentista);
        } else {
            System.out.println("Dentista no encontrado con ID: " + id);
        }
    }
}
