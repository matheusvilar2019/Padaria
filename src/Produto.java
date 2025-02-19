import java.util.Objects;

public class Produto {
    private String nome;
    private Double precoUnitario;
    private Double quantidade;
    private Double valorTotal;

    public Produto(String nome, Double precoUnitario, Double quantidade) {
        this.nome = Objects.requireNonNull(nome, "O nome não pode ser nulo");
        this.precoUnitario = validarValor(precoUnitario, "O preço unitário não pode ser nulo ou zero");
        this.quantidade = quantidade;
        //this.quantidade = validarValor(quantidade, "A quantidade não pode ser nula ou zero");
        this.valorTotal = calcularSubTotal();
    }

    public Double validarValor(Double valor, String mensagemErro) {
        if (valor == null || valor == 0.00) {
            throw new IllegalArgumentException(mensagemErro);
        }
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    private Double calcularSubTotal() {
        return precoUnitario * quantidade;
    }
}
