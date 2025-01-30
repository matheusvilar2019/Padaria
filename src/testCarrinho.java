import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class testCarrinho {
    @Test
    public void preencheCarrinho() {
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 0.00),
                new Produto("Queijo", 50.00, 5.00)
        );

        Carrinho carrinho = new Carrinho(produtos, false);
        Carrinho carrinho2 = new Carrinho(produtos, true);

        assertEquals(produtos, carrinho.getProdutos());
        assertEquals(false, carrinho.getNotaFiscal());
        assertEquals(true, carrinho2.getNotaFiscal());
    }

    @Test
    public void valorTotal() {
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 0.00),
                new Produto("Queijo", 50.00, 5.00)
        );

        Carrinho carrinho = new Carrinho(produtos, false);

        assertEquals((Double) 264.00, carrinho.getValorTotal());
    }

    @Test
    public void valorTotal2() {
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", -10.00, 1.00),
                new Produto("Biscoito", 4.00, 0.00),
                new Produto("Queijo", 50.00, 5.00)
        );

        Carrinho carrinho = new Carrinho(produtos, false);

        assertEquals((Double) 244.00, carrinho.getValorTotal());
    }

    @Test
    public void valorTotal3() {
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 0.00),
                new Produto("Queijo", -50.00, 5.00)
        );

        Carrinho carrinho = new Carrinho(produtos, false);

        assertEquals((Double) (-236.00), carrinho.getValorTotal());
    }
}
