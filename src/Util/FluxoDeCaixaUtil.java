package Util;

import Models.Produto;
import Models.Registro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Util.ExcecaoUtil.erro;

public class FluxoDeCaixaUtil {
    public static Registro parseRegistro(String linha) {
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

    public static List<String> registrosParaTexto(Map<Integer, Produto> produtos, String operador) {
        try {
            List<String> texto = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String data = LocalDateTime.now().format(formatter);

            for (Map.Entry<Integer, Produto> produto : produtos.entrySet()) {
                texto.add(data + ";" + produto.getValue().getQuantidade() + ";" + produto.getKey() + ";"
                        + produto.getValue().getNome() + ";" + produto.getValue().getPrecoUnitario() + ";" + operador);
            }
            return texto;
        } catch (Exception e) {
            throw erro(e);
        }
    }

    public static String montarTextoFinal(List<String> registrosAntigos, List<String> registrosNovos) {
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
