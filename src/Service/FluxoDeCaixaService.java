package Service;

import Models.Registro;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static Repository.FluxoDeCaixaRepository.importarRegistros;
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

    public static void exibir() {
        try {
            List<Registro> fluxoDeCaixa = fluxoDeCaixa();
            StringBuilder resString = new StringBuilder();
            DateTimeFormatter mesAnoFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

            Map<YearMonth, List<Registro>> registrosPorMes = fluxoDeCaixa.stream()
                            .collect(Collectors.groupingBy(
                                    r -> YearMonth.from(r.getData()),
                                    TreeMap::new,
                                    Collectors.toList()
                            ));

            for (Map.Entry<YearMonth, List<Registro>> entry : registrosPorMes.entrySet()) {
                YearMonth mesAno = entry.getKey();
                List<Registro> registros = entry.getValue();

                resString.append(String.format("=============================\n"));
                resString.append(String.format("           %s                \n", mesAno.format(mesAnoFormatter)));
                resString.append(String.format("=============================\n"));

                if (operador != null) {
                    resString.append(String.format("Operador: %s\n\n", operador));
                }

                resString.append("QTD   PROD          TOTAL    \n");
                resString.append("-----------------------------\n");

                for (Registro r : registros) {
                    resString.append(formatarRegistro(r));
                }

                double total = registros.stream()
                        .map(x -> (x.getPrecoUnitario() * x.getQtdProduto()))
                        .reduce(0.0, Double::sum);

                resString.append(String.format("%-19s R$%.2f", "Total:", total));
                resString.append("\n"); // linha em branco entre os meses
            }
            System.out.println(resString);
        } catch (Exception e){
            throw erro(e);
        } finally {
            operador = null;
            dataInicial = null;
            dataFinal = null;
        }
    }

    public static String formatarRegistro(Registro r) {
        Double valor = r.getPrecoUnitario() * r.getQtdProduto();
        return String.format("%-5.1f %-12s  R$%-5.2f\n", r.getQtdProduto(), r.getNomeProduto(), valor);
    }

    public static List<Registro> fluxoDeCaixa() {
        try {
            List<Registro> listaRegistros = importarRegistros();

            // Filtro: Operador
            if (operador != null) {
                listaRegistros = listaRegistros.stream()
                        .filter(x -> Objects.equals(x.getOperador(), operador))
                        .collect(Collectors.toList());
            }

            // Filtro: Data
            if (dataInicial != null && dataFinal != null) {
                listaRegistros = listaRegistros.stream()
                        .filter(x -> (x.getData().isAfter(dataInicial) && x.getData().isBefore(dataFinal)))
                        .collect(Collectors.toList());
            }

            return listaRegistros;
        } catch (Exception e) {
            throw erro(e);
        }
    }
}
