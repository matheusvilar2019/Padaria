import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            return produtos.stream()
                    .map(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.00)
                    .reduce(0.00, Double::sum);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String pagar(Double valorPago) {
        Double troco = valorPago - valorTotal;
        Nota nota = new Nota(null, null, false, produtos, valorTotal, valorPago, troco, operador);
        return nota.gerarNota();
    }
}
