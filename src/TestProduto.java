import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestProduto {
    @Test
    public void preencheValores() {

        Produto produto = new Produto("Pão", 10.00, 1.00);

        assertEquals("Pão", produto.getNome());
        assertEquals((Double) 10.00, produto.getPrecoUnitario());
        assertEquals((Double) 1.00, produto.getPeso());
        assertEquals((Double) 10.00, produto.getValorTotal());
    }


    @Test
    public void valorTotalComPesoZero() {
        Produto produto = new Produto("Biscoito", 4.00, 0.00);
        assertEquals((Double) 4.00, produto.getValorTotal());
    }

    @Test
    public void valorTotalComPesoPositivo() {
        Produto produto = new Produto("Queijo", 50.00, 5.00);
        assertEquals((Double) 250.00, produto.getValorTotal());
    }

    @Test
    public void valorTotalComPesoNegativo() {
        Produto produto = new Produto("Queijo", 50.00, -2.00);
        assertEquals((Double) (-100.00), produto.getValorTotal());
    }
}
