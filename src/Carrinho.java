import java.util.List;
import java.util.stream.Collectors;

public class Carrinho {
    private List<Produto> produtos;
    private Double valorTotal;
    private Boolean CPFNota;

    public Carrinho(List<Produto> produtos, Boolean CPFNota) {
        this.produtos = produtos;
        this.CPFNota = CPFNota;
        this.valorTotal = calculaValorTotal();
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
        Double valor = produtos.stream()
                .map(x -> x.valorTotal)
                .reduce(0.00, Double::sum);

        return valor;
    }
}
