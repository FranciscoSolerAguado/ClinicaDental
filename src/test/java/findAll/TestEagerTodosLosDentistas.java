package findAll;

import DAO.DentistaDAO;
import model.Dentista;
import utils.LoggerUtil;

import java.util.List;
import java.util.logging.Logger;

public class TestEagerTodosLosDentistas {
    public static void main(String[] args) {
        Logger logger = LoggerUtil.getLogger();
        logger.info("🧪 Test de búsqueda de todos los dentistas iniciado");

        DentistaDAO dentistaDAO = DentistaDAO.getInstance();
        List<Object> dentistas = dentistaDAO.findAllEager();

        try {
            if (dentistas.isEmpty()) {
                logger.info("❌ No se encontraron dentistas.");
                System.out.println("No se encontraron dentistas.");
            } else {
                for (Object obj : dentistas) {
                    Dentista dentista = (Dentista) obj;
                    System.out.println(dentista);
                }
                logger.info("✅ Se encontraron " + dentistas.size() + " dentistas.");
            }
        } catch (RuntimeException e) {
            logger.severe("❌ Error durante la búsqueda de dentistas: " + e.getMessage());
            System.out.println("Error al buscar dentistas: " + e.getMessage());
        }
    }
}
