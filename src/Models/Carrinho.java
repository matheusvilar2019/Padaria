package Models;

import Service.CarrinhoService;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Carrinho {
    private List<Produto> produtos;

    public Carrinho(List<Produto> produtos) {
        this.produtos = Objects.requireNonNull(produtos, "A lista de produtos não pode ser nula");
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
