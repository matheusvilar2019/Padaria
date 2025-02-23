import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Bem vindo à padaría");

        Map<Integer, Produto> produtosCadastrados = carregarProdutosCadastrados();
        List<Produto> produtosCarrinho = colocarProdutosCarrinho(produtosCadastrados);
        fecharCarrinho(produtosCarrinho);
    }

    public static Map<Integer, Produto> carregarProdutosCadastrados() {
        Map<Integer, Produto> produtosCadastro = new HashMap();

        produtosCadastro.put(1, new Produto("Pão Francês", 0.50, 0.00));
        produtosCadastro.put(2, new Produto("Leite", 5.00, 0.00));
        produtosCadastro.put(3, new Produto("Manteiga", 7.00, 0.00));
        produtosCadastro.put(4, new Produto("Sonho", 7.00, 0.00));

        return produtosCadastro;
    }

    public static List<Produto> colocarProdutosCarrinho(Map<Integer, Produto> produtosCadastro) {
        Scanner scanner = new Scanner(System.in);
        String resposta = "";
        Double quantidade = 0.00;
        boolean entradaValida = false;

        List<Produto> produtosCarrinho = new ArrayList<>();

        try {
            do {

                // Produto
                do {
                    try {
                        System.out.println("Digite o código do produto: ");
                        for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
                            Integer chave = entry.getKey();
                            Produto produto = entry.getValue();
                            System.out.println(chave + " - " + produto.getNome());
                        }
                        System.out.println("\nF = Fechar carrinho");

                        resposta = scanner.next();

                        if (resposta.equalsIgnoreCase("F")) {
                            // Verifica se existe produto adicionado
                            if (produtosCadastro.values().stream().anyMatch(produto ->  produto.getQuantidade() > 0.00)) break;

                            System.out.println("Escolha ao menos um produto: \n");
                            colocarProdutosCarrinho(produtosCadastro);
                        }
                        else if ((!resposta.equalsIgnoreCase("F")
                                && !isInteger(resposta))
                                || !produtosCadastro.containsKey(Integer.parseInt(resposta))) throw new IllegalArgumentException();

                        entradaValida = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Digite um valor válido\n");
                        scanner.next();
                        entradaValida = false;
                    }

                } while (!entradaValida);

                // Quantidade
                do {
                    if (resposta.equalsIgnoreCase("F")) break;

                    try {
                        System.out.println("Digite a quantidade:");
                        quantidade = scanner.nextDouble();
                        produtosCadastro.get(Integer.parseInt(resposta)).setQuantidade(quantidade);
                        resposta = "";

                        entradaValida = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Digite um valor válido\n");
                        scanner.next();
                        entradaValida = false;
                    }
                } while (!entradaValida);



            } while (!resposta.equalsIgnoreCase("F"));

            for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
                Produto produto = entry.getValue();
                if (produto.getQuantidade() > 0.00)
                    produtosCarrinho.add(produto);
            }

            for (Produto produto : produtosCarrinho) {
                System.out.println(produto.getNome() + " - quantidade: " + produto.getQuantidade());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return produtosCarrinho;
    }

    private static boolean isInteger(String str) {
        if (str == null || str.isBlank()) return false;

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void fecharCarrinho(List<Produto> produtosCarrinho) {
        Carrinho carrinho = new Carrinho(produtosCarrinho, false);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Valor total: " + carrinho.calculaValorTotal());

        System.out.println("\nDigite o valor do dinheiro dado pelo cliente: ");
        Double valorPago = scanner.nextDouble();

        String nota = carrinho.pagar(valorPago);

        System.out.println("\n");
        System.out.println(nota);
    }
}