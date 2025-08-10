package View;

import Service.CarrinhoService;
import Service.OperadorService;

import java.util.Scanner;

public class MenuPrincipal {
    public static void exibir() {
        Scanner scanner = new Scanner(System.in);
        int respostaMenu = 0;

        if (OperadorService.operador.equals("")) OperadorService.escolher();

        System.out.println("Bem vindo, " + OperadorService.operador);

        System.out.println("1 - Carrinho");
        System.out.println("2 - Operador: Escolher");
        System.out.println("3 - Produtos: Gerenciar");
        System.out.println("4 - Fluxo de Caixa");

        respostaMenu = scanner.nextInt();

        switch (respostaMenu) {
            case 1:
                CarrinhoService.exibir();
                break;
            case 2:
                OperadorService.escolher();
                break;
            case 3:
                Produtos.exibir();
                break;
            case 4:
                FluxoDeCaixa.exibir();
                break;
        }
        exibir();
    }
}
