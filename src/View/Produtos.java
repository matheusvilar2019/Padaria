package View;

import Repository.ArquivoUtil;
import Repository.ProdutoRepository;

import java.util.Scanner;

public class Produtos {
    public static void exibir() {
        try {
            int respostaMenu;
            Scanner scanner = new Scanner(System.in);

            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Alterar Produto");
            System.out.println("3 - Remover Produto");

            respostaMenu = scanner.nextInt();

            switch (respostaMenu) {
                case 1:
                    ProdutoRepository.cadastrarProduto();
                    break;
                case 2:
                    ProdutoRepository.alterarProduto();
                    break;
                case 3:
                    ProdutoRepository.removerProduto();
                    break;
            }
            exportarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void exportarArquivo() {
        ArquivoUtil arquivo = new ArquivoUtil();
        arquivo.exportar(ProdutoRepository.produtosCadastrados);
    }
}
