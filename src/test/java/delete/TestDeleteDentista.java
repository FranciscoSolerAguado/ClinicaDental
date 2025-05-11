package delete;

import DAO.DentistaDAO;
import utils.LoggerUtil;

import java.util.logging.Logger;

public class TestDeleteDentista {
    public static void main(String[] args) {
        Logger logger = LoggerUtil.getLogger();
        DentistaDAO dentistaDAO = DentistaDAO.getInstance();
        int idDentista = 18 ;
        logger.info("🧪 Test de eliminación iniciado");


        try {
            dentistaDAO.deleteById(idDentista);
            System.out.println("Dentista eliminado correctamente.");
            logger.info("✅ Dentista eliminado correctamente.");
        } catch (RuntimeException e) {
            logger.severe("❌ Error durante el test: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
