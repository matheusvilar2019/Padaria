package Models;

import java.time.LocalDate;

public class Registro {
    private LocalDate data;
    private Double qtdProduto;
    private int idProduto;
    private String nomeProduto;
    private Double precoUnitario;
    private String operador;

    public Registro(LocalDate data, Double qtdProduto, int idProduto, String nomeProduto, Double precoUnitario, String operador) {
        this.data = data;
        this.qtdProduto = qtdProduto;
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.operador = operador;
    }

    public LocalDate getData() {
        return data;
    }

    public Double getQtdProduto() {
        return qtdProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public String getOperador() {
        return operador;
    }
}
