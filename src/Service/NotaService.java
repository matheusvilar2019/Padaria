package Service;

import Models.Padaria;
import Models.Produto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotaService {
    public static String gerar(Map<Integer, Produto> produtos, Double total, Double pago, String operador) {
        try {
            StringBuilder nota = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHora = LocalDateTime.now().format(formatter);
            Double troco = pago - total;

            nota.append("=======================================\n");
            nota.append(String.format("          %s\n", Padaria.nome.toUpperCase()));
            nota.append(String.format(" %s\n", Padaria.endereco));
            nota.append(String.format(" Tel: %s\n", Padaria.telefone));
            nota.append(String.format(" CNPJ: %s\n", Padaria.cnpj));
            nota.append("=======================================\n");
            nota.append(String.format("Data/Hora: %s\n", dataHora));
            nota.append(String.format("Operador: %s\n", operador));
            nota.append("---------------------------------------\n");
            nota.append("ITEM            QTD  UNIT    TOTAL\n");
            nota.append("---------------------------------------\n");

            for (Map.Entry<Integer, Produto> produto : produtos.entrySet()) {
                nota.append(formatarProduto(produto.getValue()));
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

    private static String formatarProduto(Produto produto) {
        if (produto == null) return "";
        return String.format("%-14s %5.2f  R$%-5.2f  R$%-5.2f\n",
                produto.getNome(), produto.getQuantidade(), produto.getPrecoUnitario(), produto.getValorTotal());
    }
}