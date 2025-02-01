import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class testeNotaFiscal {
    @Test
    public void preencheValores() {
        String nomeCliente = "Matheus";
        String CPFCliente = "123.456.789-00";
        boolean CPFNota = true;
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 0.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = produtos.stream()
                .map(x -> x.valorTotal)
                .reduce(0.00, Double::sum);
        Double pago = 100.00;
        Double troco = pago - total;

        NotaFiscal notaFiscal = new NotaFiscal(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);

        assertEquals(nomeCliente, notaFiscal.getNomeCliente());
        assertEquals(CPFCliente, notaFiscal.getCPFCliente());
        assertEquals(CPFNota, notaFiscal.getCPFNota());
        assertEquals(produtos, notaFiscal.getProdutos());
    }

    @Test
    public void deveGerarNota() {

        String nomeCliente = "Matheus";
        String CPFCliente = "123.456.789-00";
        boolean CPFNota = true;
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 0.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = produtos.stream()
                .map(x -> x.valorTotal)
                .reduce(0.00, Double::sum);
        Double pago = 300.00;
        Double troco = pago - total;

        NotaFiscal notaFiscal = new NotaFiscal(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);

        String nota = notaFiscal.geraNotaFiscal();

        System.out.println(nota);
    }
}
