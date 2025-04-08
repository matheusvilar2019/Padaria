import java.sql.SQLOutput;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static String operador = "";
    static Map<Integer, Produto> produtosCadastrados = carregarProdutos();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        int respostaMenu = 0;


        if (operador.equals("")) escolherOperador();

        System.out.println("Bem vindo, " + operador);

        System.out.println("1 - Carrinho");
        System.out.println("2 - Operador: Escolher");
        System.out.println("3 - Produtos: Incluir novos");

        respostaMenu = scanner.nextInt();

        switch (respostaMenu) {
            case 1:
                List<Produto> produtosCarrinho = colocarProdutosCarrinho(produtosCadastrados);
                if (!produtosCarrinho.isEmpty()) fecharCarrinho(produtosCarrinho);
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
        String nome;
        Double precoUnitario;
        Integer maiorChave = 0;
        boolean entradaValida;

        do {
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.println("========================");
                System.out.println("===== NOVO PRODUTO =====");
                System.out.println("========================");

                System.out.println("Digite o nome: ");
                nome = scanner.next();

                System.out.println("Digite o valor: ");
                precoUnitario = scanner.nextDouble();

                for (Map.Entry<Integer, Produto> entry : produtosCadastrados.entrySet()) {
                    if (entry.getKey() > maiorChave) {
                        maiorChave = entry.getKey();
                    }
                }

                produtosCadastrados.put(maiorChave + 1, new Produto(nome, precoUnitario, 0.00));

                entradaValida = true;
            } catch (InputMismatchException e) {
                entradaValida = false;
                System.out.println("Digite um valor válido:\n");
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        } while (!entradaValida);
    }

    public static Map<Integer, Produto> carregarProdutos() {
        Map<Integer, Produto> produtos = new HashMap();

        //Importa produtos via arquivo de texto
        ImportaArquivo importaArquivo = new ImportaArquivo();
        List<Produto> listaEntrada = importaArquivo.importar();

        //adicionar listaEntrada ao mapTeste
        int maxKey = produtos.keySet().stream().max(Integer::compareTo).orElse(0) + 1;

        for (Produto produto : listaEntrada) {
            produtos.put(maxKey++, produto);
        }

        return produtos;
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
                if (resposta.equalsIgnoreCase("C")) {
                    produtosCadastrados = carregarProdutos(); // Limpa Lista
                    return produtosCarrinho = new ArrayList<>();
                }
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

            System.out.println("=======================================");
            System.out.println("               PAGAMENTO               ");
            System.out.println("=======================================");

            for (Produto produto : produtosCarrinho) {
                System.out.println(String.format("%s - Qtd: %.2f - R$%.2f", produto.getNome(), produto.getQuantidade(), produto.getValorTotal()));
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
                    System.out.println(String.format("%d - %s - R$%.2f", chave, produto.getNome(), produto.getPrecoUnitario()));
                }
                System.out.println("\nF = Fechar carrinho\nC = Cancelar carrinho");

                resposta = scanner.next();

                // Valida respostas
                if (resposta.equalsIgnoreCase("F")) {
                    // Verifica se existe produto adicionado
                    if (produtosCadastro.values().stream().anyMatch(produto ->  produto.getQuantidade() > 0.00)) break;

                    System.out.println("Escolha ao menos um produto: \n");
                    entradaValida = false;
                }
                else if (resposta.equalsIgnoreCase("C")) {
                    System.out.println("Carrinho cancelado!");
                }
                else if (( (!resposta.equalsIgnoreCase("F") || !resposta.equalsIgnoreCase("C") )
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

        System.out.println("\nDinheiro dado pelo cliente: ");
        Double valorPago = scanner.nextDouble();

        String nota = carrinho.pagar(valorPago);

        System.out.println("\n");
        System.out.println(nota);
    }
}