public class Produto {
    String nome;
    Double precoUnitario;
    Double peso;
    Double valorTotal;

    public Produto(String nome, Double precoUnitario, Double peso) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.peso = peso;
        this.valorTotal = calculaValorTotal();
    }

    public String getNome() {
        return nome;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    private Double calculaValorTotal() {
        if (peso == 0.00) {
            return precoUnitario;
        } else {
            return precoUnitario * peso;
        }
    }
}
