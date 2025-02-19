import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Bem vindo à padaría");

        Map<Integer, Produto> produtosCadastro = new HashMap();

        produtosCadastro.put(1, new Produto("Pão Francês", 0.50, 0.00));
        produtosCadastro.put(2, new Produto("Leite", 5.00, 0.00));
        produtosCadastro.put(3, new Produto("Manteiga", 7.00, 0.00));
        produtosCadastro.put(4, new Produto("Sonho", 7.00, 0.00));

        
        List<Produto> produtosCarrinho = menu(produtosCadastro);

        for (Produto produto : produtosCarrinho) {
            System.out.println(produto.getNome() + " - Qtd: " + produto.getQuantidade());
        }
    }

    public static List<Produto> menu(Map<Integer, Produto> produtosCadastro) {
        Scanner scanner = new Scanner(System.in);
        String resposta;
        Double qtd;

        List<Produto> produtosCarrinho = new ArrayList<>();

        do {
            System.out.println("Digite o código do produto: ");
            for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
                Integer chave = entry.getKey();
                Produto produto = entry.getValue();
                System.out.println(chave + " - " + produto.getNome());
            }
            System.out.println("\nS = Sair");

            resposta = scanner.next();

            if (resposta.equalsIgnoreCase("S")) break;

            System.out.println("Digite a quantidade:");
            qtd = scanner.nextDouble();

            produtosCadastro.get(Integer.parseInt(resposta)).setQuantidade(qtd);
            resposta = "";


        } while (!resposta.equalsIgnoreCase("S"));

        for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
            Produto produto = entry.getValue();
            if (produto.getQuantidade() > 0.00)
                produtosCarrinho.add(produto);
        }

        return produtosCarrinho;
    }
}