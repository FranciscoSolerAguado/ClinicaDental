package update;

import DAO.DentistaDAO;

public class TestUpdateDentista {
    public static void main(String[] args) {
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();

        int idDentista = 10;
        int telefonoNuevo = 655098766
                ;


        try{
            dentistaDAO.updateTelefono(idDentista, telefonoNuevo);
            System.out.println("El teléfono del dentista con ID " + idDentista + " se ha actualizado a " + telefonoNuevo);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
