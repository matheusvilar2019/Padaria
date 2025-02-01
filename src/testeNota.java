import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class testeNota {
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

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);

        assertEquals(nomeCliente, nota.getNomeCliente());
        assertEquals(CPFCliente, nota.getCPFCliente());
        assertEquals(CPFNota, nota.getCPFNota());
        assertEquals(produtos, nota.getProdutos());
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

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);

        String notaImpressa = nota.geraNota();

        System.out.println(notaImpressa);
    }
}
