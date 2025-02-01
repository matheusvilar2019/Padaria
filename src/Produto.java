public class Produto {
    String nome;
    Double precoUnitario;
    Double quantidade;
    Double valorTotal;

    public Produto(String nome, Double precoUnitario, Double quantidade) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.valorTotal = calcularSubTotal();
    }

    public String getNome() {
        return nome;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getPeso() {
        return quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    private Double calcularSubTotal() {
        if (quantidade == 0.00) {
            return precoUnitario;
        } else {
            return precoUnitario * quantidade;
        }
    }
}
