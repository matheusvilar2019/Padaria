package Service;

import java.util.Scanner;

public class OperadorService {
    public static String operador = "";

    public static void escolherOperador() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do operador: ");
        operador = scanner.next();
    }
}
