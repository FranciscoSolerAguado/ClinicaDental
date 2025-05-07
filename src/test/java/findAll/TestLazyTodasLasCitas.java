package findAll;

import java.util.List;

public class TestLazyTodasLasCitas {
    public static void main(String[] args) {
        List<Cita> citas = CitaDAO.findAll();

        for (Cita cita : citas){
            System.out.println(cita);
        }
    }
}
