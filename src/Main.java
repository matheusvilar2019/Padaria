import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static String operador = "";

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        int respostaMenu = 0;


        if (operador.equals("")) escolherOperador();

        System.out.println("Bem vindo, " + operador);

        System.out.println("1 - Carrinho");
        System.out.println("2 - Operador");
        System.out.println("3 - Adicionar produtos");

        respostaMenu = scanner.nextInt();

        switch (respostaMenu) {
            case 1:
                Map<Integer, Produto> produtosCadastrados = carregarProdutosCadastrados();
                List<Produto> produtosCarrinho = colocarProdutosCarrinho(produtosCadastrados);
                fecharCarrinho(produtosCarrinho);
                menu();
                break;
            case 2:
                escolherOperador();
                menu();
                break;
            case 3:
                adicionarProdutos();
                menu();
        }
    }

    private static void escolherOperador() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do operador: ");
        operador = scanner.next();
    }

    private static void adicionarProdutos() {
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

                // Escolhe produto
                resposta = escolherProduto(produtosCadastro);
                if (resposta.equalsIgnoreCase("F")) break;

                // Escolhe quantidade
                quantidade = escolherQuantidade();
                produtosCadastro.get(Integer.parseInt(resposta)).setQuantidade(quantidade);

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

    private static String escolherProduto(Map<Integer, Produto> produtosCadastro) {
        Scanner scanner = new Scanner(System.in);
        boolean entradaValida;
        String resposta = "";
        List<Produto> produtosCarrinho;

        do {
            try {

                // Exibe os produtos cadastrados
                System.out.println("Digite o código do produto: ");
                for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
                    Integer chave = entry.getKey();
                    Produto produto = entry.getValue();
                    System.out.println(chave + " - " + produto.getNome());
                }
                System.out.println("\nF = Fechar carrinho");

                resposta = scanner.next();

                // Valida respostas
                if (resposta.equalsIgnoreCase("F")) {
                    // Verifica se existe produto adicionado
                    if (produtosCadastro.values().stream().anyMatch(produto ->  produto.getQuantidade() > 0.00)) break;

                    System.out.println("Escolha ao menos um produto: \n");
                    entradaValida = false;
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

        return resposta;
    }

    private static Double escolherQuantidade() {
        Double quantidade = 0.00;
        boolean entradaValida;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println("Digite a quantidade:");
                quantidade = scanner.nextDouble();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite um valor válido\n");
                scanner.next();
                entradaValida = false;
            }
        } while (!entradaValida);

        return quantidade;
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
        Carrinho carrinho = new Carrinho(produtosCarrinho, false, operador);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Valor total: " + carrinho.calculaValorTotal());

        System.out.println("\nDigite o valor do dinheiro dado pelo cliente: ");
        Double valorPago = scanner.nextDouble();

        String nota = carrinho.pagar(valorPago);

        System.out.println("\n");
        System.out.println(nota);
    }
}