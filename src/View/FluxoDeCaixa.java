package View;

import Models.Produto;
import Repository.ProdutoRepository;
import Service.CarrinhoService;
import Service.FluxoDeCaixaService;
import Service.OperadorService;

import java.util.List;
import java.util.Scanner;

public class FluxoDeCaixa {
    public static void exibir() {
        try {
            Scanner scanner = new Scanner(System.in);
            int respostaMenu;

            System.out.println("1 - Período: Escolher");
            System.out.println("2 - Operador: Escolher");
            System.out.println("3 - Exibir Fluxo de Caixa");

            respostaMenu = scanner.nextInt();

            switch (respostaMenu) {
                case 1:
                    FluxoDeCaixaService.escolherPeriodo();
                    break;
                case 2:
                    FluxoDeCaixaService.escolherOperador();
                    break;
                case 3:
                    Produtos.exibir();
                    break;
            }
            Principal.menu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
