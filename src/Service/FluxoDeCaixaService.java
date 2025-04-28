package Service;

import Models.Produto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static Util.ExcecaoUtil.erro;

public class FluxoDeCaixaService {
    static LocalDate dataInicial;
    static LocalDate dataFinal;
    static String operador;

    public static void escolherOperador() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Operador: ");
            operador = scanner.nextLine();
        } catch (Exception e){
            throw erro(e);
        }
    }

    public static void escolherPeriodo() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Data inicial (dd/mm/aaaa): ");
            dataInicial = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("Data final (dd/mm/aaaa): ");
            dataFinal = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida: " + e.getMessage());
        } catch (Exception e) {
            throw erro(e);
        }
    }

    public static void salvar(Map<Integer, Produto> produtos, String operador) {
        try {
            String texto;
            Dat
            for (Produto produto : produtos) {
                texto =
            }
        } catch (Exception e) {
            throw erro(e);
        }
    }


}
