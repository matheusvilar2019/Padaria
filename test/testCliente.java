import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testCliente {
    @Test
    public void preencheCliente() {

        Cliente cliente = new Cliente("Matheus", "123.456.789-10");

        Assert.assertEquals("Matheus", cliente.getNome());
        Assert.assertEquals("123.456.789-10", cliente.getCPF());
    }
}
