package Service;

import Models.Carrinho;
import Models.Produto;
import Repository.ArquivoUtil;
import Repository.FluxoDeCaixaRepository;
import Repository.ProdutoRepository;
import Util.Validador;

import java.util.*;

import static Service.OperadorService.operador;

public class CarrinhoService {
    public static void exibir() {
        Map<Integer, Produto> produtos = colocarProdutos(ProdutoRepository.produtosCadastrados);

        if (!produtos.isEmpty())
            fechar(produtos, new Carrinho(produtos.values().stream().toList()));
    }

    public static Map<Integer, Produto> colocarProdutos(Map<Integer, Produto> produtosCadastro) {
        Scanner scanner = new Scanner(System.in);
        String resposta = "";
        Double quantidade = 0.00;
        boolean entradaValida = false;

        Map<Integer, Produto> produtosCarrinho = new HashMap<>();

        try {
            do {
                // Escolhe produto
                resposta = Service.CarrinhoService.escolherProduto(produtosCadastro);
                if (resposta.equalsIgnoreCase("C")) {
                    ProdutoRepository.produtosCadastrados = ProdutoRepository.carregar(new ArquivoUtil()); // Limpa Lista
                    return produtosCarrinho = new HashMap<>();
                }
                if (resposta.equalsIgnoreCase("F")) break;

                // Escolhe quantidade
                quantidade = Service.CarrinhoService.escolherQuantidade();
                produtosCadastro.get(Integer.parseInt(resposta)).setQuantidade(quantidade);

            } while (!resposta.equalsIgnoreCase("F"));

            for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
                Produto produto = entry.getValue();
                if (produto.getQuantidade() > 0.00)
                    produtosCarrinho.put(entry.getKey(), produto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return produtosCarrinho;
    }

    public static String escolherProduto(Map<Integer, Produto> produtosCadastro) {
        Scanner scanner = new Scanner(System.in);
        boolean entradaValida;
        String resposta = "";

        do {
            try {
                exibeProdutosCadastrados(produtosCadastro);
                resposta = scanner.next();
                entradaValida = validaResposta(produtosCadastro, resposta);
            } catch (IllegalArgumentException e) {
                System.out.println("Digite um valor válido\n");
                scanner.next();
                entradaValida = false;
            }
        } while (!entradaValida);

        return resposta;
    }

    public static void exibeProdutosCadastrados(Map<Integer, Produto> produtosCadastro) {
        System.out.println("Digite o código do produto: ");
        for (Map.Entry<Integer, Produto> entry : produtosCadastro.entrySet()) {
            Integer chave = entry.getKey();
            Produto produto = entry.getValue();
            System.out.println(String.format("%d - %s - R$%.2f", chave, produto.getNome(), produto.getPrecoUnitario()));
        }
        System.out.println("\nF = Fechar carrinho\nC = Cancelar carrinho");
    }

    public static boolean validaResposta(Map<Integer, Produto> produtosCadastro, String resposta) {
        if (resposta.equalsIgnoreCase("F")) {
            // Verifica se existe produto adicionado
            if (produtosCadastro.values().stream().anyMatch(produto ->  produto.getQuantidade() > 0.00))
                return true;

            System.out.println("Escolha ao menos um produto: \n");
            return false;
        }
        else if (resposta.equalsIgnoreCase("C")) {
            System.out.println("Carrinho cancelado!");
        }
        else if (( (!resposta.equalsIgnoreCase("F") || !resposta.equalsIgnoreCase("C") )
                && !Validador.isInteger(resposta))
                || !produtosCadastro.containsKey(Integer.parseInt(resposta))) throw new IllegalArgumentException();

        return true;
    }

    public static Double escolherQuantidade() {
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

    public static void fechar(Map<Integer, Produto> produtosCarrinho, Carrinho carrinho) {
        boolean fechado = false;

        while(!fechado) {
            exibirResumo(carrinho);
            switch(menuPagamento()) {
                case 1:
                    fechado = pagar(produtosCarrinho);
                    break;
                case 2:
                    removerProduto(produtosCarrinho);
                    carrinho = new Carrinho(produtosCarrinho.values().stream().toList());
                    continue;
                case 3:
                    cancelar();
                    return;
                default:
                    System.out.println("Escolha uma opção valida");
            }
            if (produtosCarrinho.isEmpty()) return; // Produto removido
        }
    }

    public static boolean pagar(Map<Integer, Produto> produtosCarrinho) {
        Scanner scanner = new Scanner(System.in);
        Double valorPago = 0.00;

        System.out.println("\nDinheiro dado pelo cliente: ");
        valorPago = scanner.nextDouble();

        if (valorPago < calculaValorTotal(produtosCarrinho)) System.out.println("Valor insuficiente!\n");
        else {
            processarNota(produtosCarrinho, valorPago);
            return true;
        }

        return false;
    }

    public static void cancelar() {
        ProdutoRepository.produtosCadastrados = ProdutoRepository.carregar(new ArquivoUtil()); // Limpa Lista
        System.out.println("Compra cancelada");
    }

    public static void processarNota(Map<Integer, Produto> produtosCarrinho, Double valorPago) {
        String nota = NotaService.gerar(produtosCarrinho, calculaValorTotal(produtosCarrinho), valorPago, operador);
        FluxoDeCaixaRepository.salvar(produtosCarrinho, operador, new ArquivoUtil());
        System.out.println("\n");
        System.out.println(nota);
        ProdutoRepository.produtosCadastrados = ProdutoRepository.carregar(new ArquivoUtil()); // Limpa Lista
    }

    private static void exibirResumo(Carrinho carrinho) {
        System.out.println("=======================================");
        System.out.println("               PAGAMENTO               ");
        System.out.println("=======================================");

        for (Produto produto : carrinho.getProdutos()) {
            System.out.println(String.format("%s - Qtd: %.2f - R$%.2f", produto.getNome(), produto.getQuantidade(), produto.getValorTotal()));
        }
        System.out.println("Valor total: " + calculaValorTotal(carrinho.getProdutos()));
    }

    private static int menuPagamento() {
        int resposta = 0;
        Scanner scanner = new Scanner(System.in);

        try {
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

    public static void removerProduto(Map<Integer, Produto> produtos){
        Scanner scanner = new Scanner(System.in);
        int id;

        for (Map.Entry<Integer, Produto> produto : produtos.entrySet()) {
            System.out.println(produto.getKey() + " - " + produto.getValue().getNome());
        }

        System.out.println("Digite o número do produto:");
        id = scanner.nextInt();
        produtos.remove(id);
        System.out.println("Produto removido!");
        calculaValorTotal(produtos);
    }

    public static Double calculaValorTotal(Map<Integer, Produto> produtos) {
        return calculaValorTotal(produtos.values().stream().toList());
    }

    public static Double calculaValorTotal(List<Produto> produtos) {
        try {
            return produtos.stream()
                    .map(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.00)
                    .reduce(0.00, Double::sum);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
