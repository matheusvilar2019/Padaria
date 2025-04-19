package Models;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Carrinho {
    private List<Produto> produtos;
    private Double valorTotal;
    private Boolean CPFNota;
    private String operador;

    public Carrinho(List<Produto> produtos, Boolean CPFNota, String operador) {
        this.produtos = Objects.requireNonNull(produtos, "A lista de produtos não pode ser nula");
        this.CPFNota = CPFNota;
        this.valorTotal = calculaValorTotal();
        this.operador = operador;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public boolean getCPFNota() {
        return CPFNota;
    }

    public Double calculaValorTotal() {
        try {
            valorTotal =  produtos.stream()
                    .map(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.00)
                    .reduce(0.00, Double::sum);

            return valorTotal;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String pagar(Double valorPago) {
        Double troco = valorPago - valorTotal;
        Nota nota = new Nota(null, null, false, produtos, valorTotal, valorPago, troco, operador);
        return nota.gerarNota();
    }

    public void removerProduto(){
        Scanner scanner = new Scanner(System.in);
        int id;

        for (Produto produto : produtos) {
            System.out.println(produtos.indexOf(produto) + 1 + " - " + produto.getNome());
        }

        System.out.println("Digite o número do produto:");
        id = scanner.nextInt() - 1;
        produtos.remove(id);
        calculaValorTotal();
        System.out.println("Models.Produto removido!");
    }
}
