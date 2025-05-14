package utils;

import view.MenuVista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilidades {
//    // Instancia del Scanner para leer la entrada del usuario
//    public static Scanner sc = new Scanner(System.in);
//    private static final String REGEX_DNI = "^[0-9]{8}[A-HJ-NP-TV-Z]$";
//    private static final String REGEX_N_COLEGIADO = "^[0-9]{5}-[A-Z]$";
//    private static final String REGEX_TELEFONO = "^[67][0-9]{8}$";
//
//    /**
//     * Lee un número entero desde la consola con validación.
//     * Si el usuario introduce un valor no válido, se muestra un mensaje de error y se solicita nuevamente el número.
//     *
//     * @param mensaje El mensaje que se muestra al usuario antes de pedir el número.
//     * @return El número entero introducido por el usuario.
//     */
//    public static int leeEntero(String mensaje) {
//        Scanner teclado = new Scanner(System.in);
//        int numero = 0;
//        boolean valido = false;
//
//        while (!valido) {
//            try {
//                System.out.print(mensaje);
//                numero = teclado.nextInt();  // Intenta convertir la entrada en un número entero.
//                valido = true;
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Ingresa un número entero válido.");
//            }catch (InputMismatchException e){
//                System.out.println("Error: Ingresa un número entero");
//            }
//        }
//        return numero;
//    }
//
//    /**
//     * Lee un número decimal desde la consola con validación.
//     * Si el usuario introduce un valor no válido, se muestra un mensaje de error y se solicita nuevamente el número.
//     *
//     * @param mensaje El mensaje que se muestra al usuario antes de pedir el número.
//     * @return El número decimal (double) introducido por el usuario.
//     */
//    public static double leeDouble(String mensaje) {
//        Scanner teclado = new Scanner(System.in);
//        double numero = 0;
//        boolean valido = false;
//
//        while (!valido) {
//            try {
//                System.out.print(mensaje);
//                numero = teclado.nextDouble();
//                valido = true;
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Ingresa un número válido.");  // Muestra mensaje de error si la entrada no es un número válido.
//            }
//        }
//        return numero;
//    }
//
//    /**
//     * Pide una cadena de texto al usuario.
//     *
//     * @param msn : el mensaje que se muestra al usuario.
//     * @return la cadena introducida por el usuario.
//     */
//    public static String pideString(String msn) {
//        Scanner teclado = new Scanner(System.in);
//        MenuVista.muestraMensaje(msn);
//        String cadena = null;
//        cadena = teclado.nextLine(); // Lee una palabra
//        return cadena;
//    }
//
//    /**
//     * Pide al usuario que introduzca una fecha por texto con el siguiente formato: (yyyy-MM-dd)
//     *
//     * @param msn mensaje que vamos a mostrar al usuario
//     * @return fecha introducida por el usuario en formato LocalDate, o null si no se ha introducido una fecha correcta.
//     */
//    public static LocalDate pideFecha(String msn) {
//        Scanner teclado = new Scanner(System.in);
//        LocalDate fecha = null;
//        boolean fechaCorrecta = false;
//        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        while (!fechaCorrecta) {
//            MenuVista.muestraMensaje(msn);
//            String fechaString = teclado.nextLine();
//
//            try {
//                fecha = LocalDate.parse(fechaString, formatoFecha);
//                fechaCorrecta = true;
//                MenuVista.muestraMensaje("Fecha introducida: " + fecha);
//            } catch (DateTimeParseException e) {
//                MenuVista.muestraMensaje("Formato de fecha incorrecto. Debe ser yyyy-MM-dd");
//            }
//        }
//        return fecha;
//    }
//
//    /**
//     * Pide al usuario que introduzca un DNI con el siguiente formato: 12345678Z
//     *
//     * @param msn mensaje que vamos a mostrar al usuario
//     * @return dni introducido por el usuario en formato String, o null si no se ha introducido un DNI correcto.
//     */
//    public static String pideDNI(String msn) {
//        String dni = null;
//        Pattern pattern = Pattern.compile(REGEX_DNI);
//        boolean valido = false;
//
//        do {
//            System.out.print(msn);
//            dni = sc.nextLine().toUpperCase(); // Convertir a mayúsculas por si escriben en minúscula
//            Matcher matcher = pattern.matcher(dni);
//
//            if (matcher.matches()) {
//                valido = true;
//            } else {
//                System.out.println("❌ DNI incorrecto. Debe tener 8 dígitos seguidos de una letra válida (ej: 12345678Z).");
//            }
//
//        } while (!valido);
//
//        return dni;
//    }
//
//    /**
//     * Pide al usuario que introduzca un número de colegiado con el siguiente formato: 12345-X
//     *
//     * @param mensaje mensaje que vamos a mostrar al usuario
//     * @return numero de colegiado introducido por el usuario en formato String, o null si no se ha introducido un número correcto.
//     */
//    public static String pideNColegiado(String mensaje) {
//        String numero;
//        boolean valido = false;
//
//        do {
//            System.out.print(mensaje);
//            numero = sc.nextLine().toUpperCase(); // Convertir a mayúsculas por si escriben minúscula
//
//            if (numero.matches(REGEX_N_COLEGIADO)) {
//                valido = true;
//            } else {
//                System.out.println("❌ Formato incorrecto. Debe ser 5 dígitos seguidos de '-' y una letra mayúscula. Ejemplo: 12345-X");
//            }
//        } while (!valido);
//
//        return numero;
//    }
//
//    public static int pideTelefono(String mensaje) {
//        Scanner teclado = new Scanner(System.in);
//        String telefono;
//        boolean valido = false;
//
//        do {
//            System.out.print(mensaje);
//            telefono = teclado.nextLine();
//
//            if (telefono.matches("^[67][0-9]{8}$")) {
//                valido = true;
//            } else {
//                System.out.println("❌ Teléfono incorrecto. Debe tener 9 cifras y comenzar por 6 o 7.");
//            }
//        } while (!valido);
//
//        return Integer.parseInt(telefono);
//    }
}

