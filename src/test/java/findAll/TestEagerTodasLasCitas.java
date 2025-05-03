package findAll;

import DAO.CitaDAO;
import model.Cita;

import java.util.List;

public class TestEagerTodasLasCitas {
    public static void main(String[] args) {
        List<Cita> citas = CitaDAO.findAllEager();

        for (Cita cita : citas) {
            System.out.println(cita);
        }
    }
}
