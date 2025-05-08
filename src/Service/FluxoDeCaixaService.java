package Service;

import Models.Produto;
import Repository.ArquivoUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
            StringBuilder resString = new StringBuilder();

            resString.append(String.format("=====================\n"));
            resString.append(String.format("       05/2025       \n"));
            resString.append(String.format("=====================\n"));
            resString.append(String.format("Operador: %s\n", operador));
            resString.append(String.format("2000 - Pão        - R$1000,00\n"));
            resString.append(String.format("100  - Leite      - R$500,00\n"));
            resString.append(String.format("50   - Manteiga   - R$500,00\n"));
            resString.append(String.format("Total: R$2000,00\n"));

            System.out.println(resString);
        } catch (Exception e){
            throw erro(e);
        }
    }

    public static void salvar(Map<Integer, Produto> produtos, String operador) {
        try {
            String diretorio = "arquivo/fluxoDeCaixa.txt";
            ArquivoUtil importaArquivo = new ArquivoUtil();

            List<String> registrosAntigos = importaArquivo.lerArquivo(diretorio);
            List<String> registrosNovos = salvarString(produtos, operador);
            String resultado = juntaRegistros(registrosAntigos, registrosNovos);

            importaArquivo.exportar(resultado, diretorio);
        } catch (Exception e) {
            throw erro(e);
        }
    }

    public static List<String> salvarString(Map<Integer, Produto> produtos, String operador) {
        try {
            List<String> texto = new ArrayList<>();

            for (Map.Entry<Integer, Produto> produto : produtos.entrySet()) {
                texto.add(LocalDate.now() + ";" + produto.getValue().getQuantidade() + ";" + produto.getKey() + produto.getValue().getNome() + produto.getValue().getPrecoUnitario() + operador);
            }
            return texto;
        } catch (Exception e) {
            throw erro(e);
        }
    }

    public static String juntaRegistros(List<String> registrosAntigos, List<String> registrosNovos) {
        try {
            String resultado = "";

            List<String> resultadoLista = new ArrayList<>(registrosAntigos);
            resultadoLista.addAll(registrosNovos);

            for (String s : resultadoLista) {
                resultado += s + "\n";
            }
            return resultado;
        } catch (Exception e) {
            throw erro(e);
        }
    }
}
