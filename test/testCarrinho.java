import Models.Carrinho;
import Models.Produto;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class testCarrinho {
    @Test
    public void preencheCarrinho() {
        List<Produto> produtos = Arrays.asList(
                new Produto("Pão", 10.00, 1.00),
                new Produto("Biscoito", 4.00, 1.00),
                new Produto("Queijo", 50.00, 5.00)
        );

        Carrinho carrinho = new Carrinho(produtos);
        Carrinho carrinho2 = new Carrinho(produtos);

        assertEquals(produtos, carrinho.getProdutos());
    }

    @Test(expected = NullPointerException.class)
    public void produtoNulo() {
        Carrinho carrinho = new Carrinho(null);
    }
}
