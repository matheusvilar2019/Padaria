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
        System.out.println("3 - Produtos: Gerenciar");

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
                gerenciarProdutos();
                menu();
        }
    }

    private static void escolherOperador() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do operador: ");
        operador = scanner.next();
    }

    private static void gerenciarProdutos() {
        try {
            int respostaMenu;
            Scanner scanner = new Scanner(System.in);

            System.out.println("1 - Adicionar Produto");
            System.out.println("2 - Alterar Produto");
            System.out.println("3 - Remover Produto");

            respostaMenu = scanner.nextInt();

            switch (respostaMenu) {
                case 1:
                    adicionarProduto();
                    break;
                case 2:
                    alterarProduto();
                    break;
                case 3:
                    removerProduto();
                    break;
            }

            exportarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void adicionarProduto() {
        boolean entradaValida = false;
        while(!entradaValida) {
            try {
                Scanner scanner = new Scanner(System.in);
                Integer maiorChave = 0;

                System.out.println("========================");
                System.out.println("===== NOVO PRODUTO =====");
                System.out.println("========================");

                System.out.println("Digite o nome: ");
                String nome = scanner.nextLine();

                System.out.println("Digite o valor: ");
                Double precoUnitario = scanner.nextDouble();

                for (Map.Entry<Integer, Produto> entry : produtosCadastrados.entrySet()) {
                    if (entry.getKey() > maiorChave) {
                        maiorChave = entry.getKey();
                    }
                }

                produtosCadastrados.put(maiorChave + 1, new Produto(nome, precoUnitario, 0.00));
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite um valor válido:\n");
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        } while (!entradaValida);
    }

    private static void alterarProduto() {
        Boolean entradaValida = false;
        while(!entradaValida) {
            try {
                System.out.println("=========================");
                System.out.println("==== ALTERAR PRODUTO ====");
                System.out.println("=========================");

                for (Map.Entry<Integer, Produto> entry : produtosCadastrados.entrySet()) {
                    System.out.println(String.format("%d - %s - R$%.2f" ,entry.getKey(), entry.getValue().getNome(), entry.getValue().getPrecoUnitario()));
                }

                System.out.println();
                System.out.println("Id: ");
                Scanner scanner = new Scanner(System.in);
                int id = Integer.parseInt(scanner.nextLine());
                if (!produtoExiste(id)) throw new NumberFormatException();;

                System.out.println("Nome: ");
                String nome = scanner.nextLine();

                System.out.println("Preço: ");
                Double preco = Double.parseDouble(scanner.nextLine());


                produtosCadastrados.put(id, new Produto(nome, preco, 0.00));
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor válido:\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void removerProduto() {
        Boolean entradaValida = false;
        while(!entradaValida) {
            try {
                System.out.println("=========================");
                System.out.println("==== REMOVER PRODUTO ====");
                System.out.println("=========================");

                for (Map.Entry<Integer, Produto> entry : produtosCadastrados.entrySet()) {
                    System.out.println(String.format("%d - %s - R$%.2f" ,entry.getKey(), entry.getValue().getNome(), entry.getValue().getPrecoUnitario()));
                }

                System.out.println();
                System.out.println("Id: ");

                Scanner scanner = new Scanner(System.in);
                int id = scanner.nextInt();
                if (!produtoExiste(id)) throw new InputMismatchException();

                produtosCadastrados.remove(id);
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite um valor válido:\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Boolean produtoExiste(int id) {
        for (Map.Entry<Integer, Produto> entry : produtosCadastrados.entrySet()) {
            if (entry.getKey() == id) return true;
        }
        return false;
    }

    private static void exportarArquivo() {
        Arquivo arquivo = new Arquivo();
        arquivo.exportar(produtosCadastrados);
    }

    public static Map<Integer, Produto> carregarProdutos() {
        Map<Integer, Produto> produtos = new HashMap();

        //Importa produtos via arquivo de texto
        Arquivo importaArquivo = new Arquivo();
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

    private static void fecharCarrinho(List<Produto> produtosCarrinho) {
        Carrinho carrinho = new Carrinho(produtosCarrinho, false, operador);
        Scanner scanner = new Scanner(System.in);
        Double valorPago = 0.00;
        boolean carrinhoFechado = false;

        while(!carrinhoFechado) {
            exibirResumoCarrinho(carrinho);
            switch(menuPagamento()) {
                case 1:
                    //pagar
                    System.out.println("\nDinheiro dado pelo cliente: ");
                    valorPago = scanner.nextDouble();

                    if (valorPago < carrinho.getValorTotal()) System.out.println("Valor insuficiente!\n");
                    else carrinhoFechado = true;
                    break;
                case 2: //Remover produto
                    carrinho.removerProduto();
                    if (produtosCarrinho.isEmpty()) return;
                    continue;
                case 3: //Cancelar compra
                    produtosCadastrados = carregarProdutos(); // Limpa Lista
                    System.out.println("Compra cancelada");
                    return;
                default:
                    System.out.println("Escolha uma opção valida");
            }
        }

        String nota = carrinho.pagar(valorPago);
        System.out.println("\n");
        System.out.println(nota);
    }

    private static void exibirResumoCarrinho(Carrinho carrinho) {
        System.out.println("=======================================");
        System.out.println("               PAGAMENTO               ");
        System.out.println("=======================================");

        for (Produto produto : carrinho.getProdutos()) {
            System.out.println(String.format("%s - Qtd: %.2f - R$%.2f", produto.getNome(), produto.getQuantidade(), produto.getValorTotal()));
        }
        System.out.println("Valor total: " + carrinho.getValorTotal());
    }

    private static int menuPagamento() {
        int resposta = 0;
        Scanner scanner = new Scanner(System.in);

        try {
            //menu
            System.out.println("1 - Pagar");
            System.out.println("2 - Remover produto");
            System.out.println("3 - Cancelar compra");
            resposta = scanner.nextInt();
        } catch (IllegalArgumentException e) {
            System.out.println("Digite um valor válido\n");
            scanner.next();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return resposta;
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
}