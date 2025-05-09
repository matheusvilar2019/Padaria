package Service;

import Models.Registro;
import Models.Produto;
import Repository.ArquivoUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static Util.ExcecaoUtil.erro;

public class FluxoDeCaixaService {
    static LocalDate dataInicial;
    static LocalDate dataFinal;
    static String operador;
    static String diretorio = "arquivo/fluxoDeCaixa.txt";

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

            resString.append(String.format("=====================\n"));
            resString.append(String.format("       05/2025       \n"));
            resString.append(String.format("=====================\n"));

            if (operador != null) {
                resString.append(String.format("Operador: %s\n", operador));
            }

            for (Registro r : fluxoDeCaixa) {
                resString.append(formatarRegistro(r));
            }
            // resString.append(String.format("2000 - Pão        - R$1000,00\n"));
            // resString.append(String.format("100  - Leite      - R$500,00\n"));
            // resString.append(String.format("50   - Manteiga   - R$500,00\n"));
            // resString.append(String.format("Total: R$2000,00\n"));

            System.out.println(resString);
        } catch (Exception e){
            throw erro(e);
        }
    }

    public static String formatarRegistro(Registro r) {
        Double valor = r.getPrecoUnitario() * r.getQtdProduto();
        return String.format("%-5.1f %-12s  R$%-5.2f\n", r.getQtdProduto(), r.getNomeProduto(), valor);
    }

    public static List<Registro> fluxoDeCaixa() {
        try {
            ArquivoUtil arquivo = new ArquivoUtil();
            List<String> registrosImportados = arquivo.lerArquivo(diretorio);
            return gerarObjeto(registrosImportados);
        } catch (Exception e) {
            throw erro(e);
        }
    }

    private static List<Registro> gerarObjeto(List<String> listaEntrada) {
        List<Registro> registros = listaEntrada.stream()
                .map(FluxoDeCaixaService::converterParaRegistro)
                //.map(this::converterParaRegistro)
                .filter(registro -> registro != null)
                .collect(Collectors.toList());

        return registros;
    }

    private static Registro converterParaRegistro(String linha) {
        try {
            String[] arr = linha.split(";");
            if (arr.length < 6) return null;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(arr[0], formatter);
            Double qtdProduto = Double.parseDouble(arr[1]);
            int idProduto = Integer.parseInt(arr[2]);
            String nomeProduto = arr[3];
            Double precoUnitario = Double.parseDouble(arr[4]);
            String operador = arr[5];

            return new Registro(data, qtdProduto, idProduto, nomeProduto, precoUnitario, operador);
        } catch (Exception e) {
            System.out.println("Erro ao converter linha: " + linha + " -> " + e.getMessage());
            return null;
        }
    }

    public static void salvar(Map<Integer, Produto> produtos, String operador) {
        try {
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
