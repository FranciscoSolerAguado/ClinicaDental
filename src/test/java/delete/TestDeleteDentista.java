package delete;

import DAO.DentistaDAO;

public class TestDeleteDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();
        int idDentista = 16 ;

        try {
            dentistaDAO.deleteById(idDentista);
            System.out.println("Dentista eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
