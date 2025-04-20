package Service;

import java.util.Scanner;

public class OperadorService {
    public static String operador = "";

    public static void escolherOperador() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do operador: ");
        operador = scanner.next();
        clear();
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
