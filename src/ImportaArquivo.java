import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportaArquivo {
    public List<Produto> importar() {
        final String diretorio = "arquivo/listaProdutos.txt";
        List<String> listaEntrada = lerArquivo(diretorio);

        return gerarObjeto(listaEntrada);
    }

    private List<String> lerArquivo(String diretorio) {
        List<String> lista = new ArrayList<>();

        try {
            lista = Files.readAllLines(Paths.get(diretorio));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Produto> gerarObjeto(List<String> listaEntrada) {
        List<Produto> produtos = listaEntrada.stream()
                .map(this::converterParaProduto)
                .filter(produto -> produto != null)
                .collect(Collectors.toList());

        return produtos;
    }

    private Produto converterParaProduto(String linha) {
        try {
            String[] arr = linha.split("\t");
            if (arr.length < 2) return null;

            String nome = arr[0];
            Double preco = Double.parseDouble(arr[1]);

            return new Produto(nome, preco, 0.00);
        } catch (Exception e) {
            System.out.println("Erro ao converter linha: " + linha + " -> " + e.getMessage());
            return null;
        }
    }
}
