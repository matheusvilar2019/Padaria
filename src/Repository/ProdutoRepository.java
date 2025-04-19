package Repository;

import Models.Produto;

import java.util.*;

public class ProdutoRepository {
    public static Map<Integer, Produto> produtosCadastrados = carregarProdutos();

    public static Map<Integer, Produto> carregarProdutos() {
        Map<Integer, Produto> produtos = new HashMap<>();

        //Importa produtos via arquivo de texto
        ArquivoUtil importaArquivo = new ArquivoUtil();
        List<Produto> listaEntrada = importaArquivo.importar();

        //adicionar listaEntrada ao mapTeste
        int maxKey = produtos.keySet().stream().max(Integer::compareTo).orElse(0) + 1;

        for (Produto produto : listaEntrada) {
            produtos.put(maxKey++, produto);
        }

        return produtos;
    }

    public static void cadastrarProduto() {
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

    public static void alterarProduto() {
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

    public static void removerProduto() {
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
        for (Map.Entry<Integer, Produto> entry : ProdutoRepository.produtosCadastrados.entrySet()) {
            if (entry.getKey() == id) return true;
        }
        return false;
    }
}
