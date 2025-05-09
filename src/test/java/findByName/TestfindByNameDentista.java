package findByName;

import DAO.DentistaDAO;
import model.Dentista;

public class TestfindByNameDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();

        Dentista dentista = dentistaDAO.findByNameEager("Lucía Fernández López");
        if (dentista != null) {
            System.out.println(dentista);
        } else {
            System.out.println("Dentista no encontrado");
        }
    }
}
