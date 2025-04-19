package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Nota {
    private String nomeCliente;
    private String CPFCliente;
    private boolean CPFNota;
    private List<Produto> produtos;
    private Double total;
    private Double pago;
    private Double troco;

    private static final String nomeLoja = "Padaria Pão Quente";
    private static final String endereco = "Rua das Rosquinhas, 123 - Centro";
    private static final String telefone = "(11) 98765-4321";
    private static final String cnpj = "12.345.678/0001-99";
    private String operador;

    public Nota(String nomeCliente, String CPFCliente, boolean CPFNota, List<Produto> produtos, Double total, Double pago, Double troco, String operador) {
        this.nomeCliente = nomeCliente;
        this.CPFCliente = CPFCliente;
        this.CPFNota = CPFNota;
        this.produtos = Objects.requireNonNull(produtos, "A lista de produtos não pode ser nula");
        this.total = Objects.requireNonNull(total, "O valor total não pode ser nulo");
        this.pago = Objects.requireNonNull(pago, "O valor pago não pode ser nulo");
        this.troco = Objects.requireNonNull(troco, "O valor do troco não pode ser nulo");
        this.operador = operador;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getCPFCliente() {
        return CPFCliente;
    }

    public boolean getCPFNota() {
        return CPFNota;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String gerarNota() {
        try {
            StringBuilder nota = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHora = LocalDateTime.now().format(formatter);

            nota.append("=======================================\n");
            nota.append(String.format("          %s\n", nomeLoja.toUpperCase()));
            nota.append(String.format(" %s\n", endereco));
            nota.append(String.format(" Tel: %s\n", telefone));
            nota.append(String.format(" CNPJ: %s\n", cnpj));
            nota.append("=======================================\n");
            nota.append(String.format("Data/Hora: %s\n", dataHora));
            nota.append(String.format("Operador: %s\n", operador));
            nota.append("---------------------------------------\n");
            nota.append("ITEM            QTD  UNIT    TOTAL\n");
            nota.append("---------------------------------------\n");

            for (Produto produto : produtos) {
                nota.append(formatarProduto(produto));
            }

            nota.append("---------------------------------------\n");
            nota.append(String.format("TOTAL:                         R$%.2f\n", total));
            nota.append(String.format("Valor Pago:                    R$%.2f\n", pago));
            nota.append(String.format("Troco:                         R$%.2f\n", troco));
            nota.append("=======================================\n");
            nota.append("      Obrigado pela preferência!\n");
            nota.append("=======================================\n");

            return nota.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar a nota", e);
        }

    }

    private String formatarProduto(Produto produto) {
        if (produto == null) return "";
        return String.format("%-14s %5.2f  R$%-5.2f  R$%-5.2f\n",
                produto.getNome(), produto.getQuantidade(), produto.getPrecoUnitario(), produto.getValorTotal());
    }
}