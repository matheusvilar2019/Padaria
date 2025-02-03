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
                new Produto("Biscoito", 4.00, 1.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = produtos.stream()
                .map(x -> x.getValorTotal())
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
                new Produto("Biscoito", 4.00, 1.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = produtos.stream()
                .map(x -> x.getValorTotal())
                .reduce(0.00, Double::sum);
        Double pago = 300.00;
        Double troco = pago - total;

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);

        String notaImpressa = nota.gerarNota();

        System.out.println(notaImpressa);
    }

    @Test(expected = NullPointerException.class)
    public void produtosNulos() {

        String nomeCliente = "Matheus";
        String CPFCliente = "123.456.789-00";
        boolean CPFNota = true;
        Double total = 1.00;
        Double pago = 1.00;
        Double troco = 1.00;

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, null, total, pago, troco);
    }

    @Test(expected = NullPointerException.class)
    public void totalNulo() {

        String nomeCliente = "Matheus";
        String CPFCliente = "123.456.789-00";
        boolean CPFNota = true;
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 1.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = null;
        Double pago = 1.00;
        Double troco = 1.00;

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);
    }

    @Test(expected = NullPointerException.class)
    public void pagoNulo() {

        String nomeCliente = "Matheus";
        String CPFCliente = "123.456.789-00";
        boolean CPFNota = true;
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 1.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = 1.00;
        Double pago = null;
        Double troco = 1.00;

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);
    }

    @Test(expected = NullPointerException.class)
    public void trocoNulo() {

        String nomeCliente = "Matheus";
        String CPFCliente = "123.456.789-00";
        boolean CPFNota = true;
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 1.00),
                new Produto("Queijo", 50.00, 5.00)
        );
        Double total = 1.00;
        Double pago = 1.00;
        Double troco = null;

        Nota nota = new Nota(nomeCliente, CPFCliente, CPFNota, produtos, total, pago, troco);
    }
}
