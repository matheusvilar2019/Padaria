import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Arquivo {
    private final String diretorio = "arquivo/listaProdutos.txt";

    public List<Produto> importar() {
        List<String> listaEntrada = lerArquivo(diretorio);
        return gerarObjeto(listaEntrada);
    }

    public void exportar(Map<Integer, Produto> produtos) {
        try {
            String textoArquivo = gerarString(produtos);
            Files.writeString(Paths.get(diretorio), textoArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String gerarString(Map<Integer, Produto> produtos) {
        StringBuilder texto = new StringBuilder();
        int maxKey = produtos.keySet().stream().max(Integer::compareTo).orElse(0);

        for (Map.Entry<Integer, Produto> produto : produtos.entrySet()) {
            texto.append(produto.getValue().getNome() + "\t" + produto.getValue().getPrecoUnitario());
            if (produto.getKey() < maxKey) texto.append("\n");
        }

        return texto.toString();
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
