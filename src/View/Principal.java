package View;

import Models.Produto;
import Repository.ProdutoRepository;
import Service.CarrinhoService;
import Service.OperadorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        int respostaMenu = 0;

        if (OperadorService.operador.equals("")) OperadorService.escolherOperador();

        System.out.println("Bem vindo, " + OperadorService.operador);

        System.out.println("1 - Carrinho");
        System.out.println("2 - Operador: Escolher");
        System.out.println("3 - Produtos: Gerenciar");

        respostaMenu = scanner.nextInt();

        switch (respostaMenu) {
            case 1:
                List<Produto> produtosCarrinho = CarrinhoService.colocarProdutosCarrinho(ProdutoRepository.produtosCadastrados);
                if (!produtosCarrinho.isEmpty()) Service.CarrinhoService.fecharCarrinho(produtosCarrinho);
                menu();
                break;
            case 2:
                OperadorService.escolherOperador();
                menu();
                break;
            case 3:
                Produtos.exibir();
                menu();
        }
    }
}
