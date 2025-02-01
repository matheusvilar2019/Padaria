import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotaFiscal {
    private String nomeCliente;
    private String CPFCliente;
    private boolean CPFNota;
    private List<Produto> produtos;
    private Double total;
    private Double pago;
    private Double troco;

    private String nomeLoja = "Padaria Pão Quente";
    private String endereco = "Rua das Rosquinhas, 123 - Centro";
    private String telefone = "(11) 98765-4321";
    private String cnpj = "12.345.678/0001-99";
    private String operador = "João Silva";

    public NotaFiscal(String nomeCliente, String CPFCliente, boolean CPFNota, List<Produto> produtos, Double total, Double pago, Double troco) {
        this.nomeCliente = nomeCliente;
        this.CPFCliente = CPFCliente;
        this.CPFNota = CPFNota;
        this.produtos = produtos;
        this.total = total;
        this.pago = pago;
        this.troco = troco;
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

    public String geraNotaFiscal() {
        StringBuilder nota = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = LocalDateTime.now().format(formatter);

        nota.append("=======================================\n");
        nota.append(String.format("       %s\n", nomeLoja.toUpperCase()));
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
            nota.append(String.format("%-14s %5.2f  R$%-5.2f  R$%-5.2f\n",
                    produto.nome, produto.quantidade, produto.precoUnitario, produto.valorTotal));
        }

        nota.append("---------------------------------------\n");
        nota.append(String.format("TOTAL:                         R$%.2f\n", total));
        nota.append(String.format("Valor Pago:                    R$%.2f\n", pago));
        nota.append(String.format("Troco:                         R$%.2f\n", troco));
        nota.append("=======================================\n");
        nota.append("      Obrigado pela preferência!\n");
        nota.append("=======================================\n");

        return nota.toString();
    }
}