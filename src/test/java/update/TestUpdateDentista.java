package update;

import DAO.DentistaDAO;

public class TestUpdateDentista {
    public static void main(String[] args) {
        int idDentista = 15;
        int telefonoNuevo = 123466789;


        try{
            DentistaDAO.updateTelefono(idDentista, telefonoNuevo);
            System.out.println("El teléfono del dentista con ID " + idDentista + " se ha actualizado a " + telefonoNuevo);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
