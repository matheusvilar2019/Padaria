import java.util.List;
import java.util.stream.Collectors;

public class Carrinho {
    private List<Produto> produtos;
    private Double valorTotal;
    private Boolean notaFiscal;

    public Carrinho(List<Produto> produtos, Boolean notaFiscal) {
        this.produtos = produtos;
        this.notaFiscal = notaFiscal;
        this.valorTotal = calculaValorTotal();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public boolean getNotaFiscal() {
        return notaFiscal;
    }

    public Double calculaValorTotal() {
        Double valor = produtos.stream()
                .map(x -> x.valorTotal)
                .reduce(0.00, Double::sum);

        return valor;
    }
}
