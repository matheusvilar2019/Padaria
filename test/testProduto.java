import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testProduto {
    @Test
    public void preencheValores() {

        Produto produto = new Produto("Pão", 10.00, 1.00);

        Assert.assertEquals("Pão", produto.getNome());
        Assert.assertEquals((Double) 10.00, produto.getPrecoUnitario());
        Assert.assertEquals((Double) 1.00, produto.getQuantidade());
        Assert.assertEquals((Double) 10.00, produto.getValorTotal());
    }

    @Test(expected = NullPointerException.class)
    public void nomeNulo() {
        Produto produto = new Produto(null, 4.00, 0.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void precoUnitarioZero() {
        Produto produto = new Produto("Biscoito", 0.00, 1.00);
    }

    @Test
    public void valorTotalComPesoPositivo() {
        Produto produto = new Produto("Queijo", 50.00, 5.00);
        Assert.assertEquals((Double) 250.00, produto.getValorTotal());
    }

    @Test
    public void valorTotalComPesoNegativo() {
        Produto produto = new Produto("Queijo", 50.00, -2.00);
        Assert.assertEquals((Double) (-100.00), produto.getValorTotal());
    }
}
