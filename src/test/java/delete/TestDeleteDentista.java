package delete;

import DAO.DentistaDAO;

public class TestDeleteDentista {
    public static void main(String[] args) {
        int idDentista = 15 ;

        try {
            // Llamar al método deleteById de DentistaDAO
            DentistaDAO.deleteById(idDentista);
            System.out.println("Dentista eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error al eliminar el dentista: " + e.getMessage());
        }
    }
}
