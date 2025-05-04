package insert;

public class TestInsertTratamientoDentista {
    public static void main(String[] args) {
        int idTratamiento = 11;
        int idDentista = 15;

        DAO.TratamientoDentistaDAO.insert(idTratamiento, idDentista);

        System.out.println("Inserción exitosa en la tabla tratamientodentista");
    }
}
