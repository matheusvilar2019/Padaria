# Padaria
Projeto pessoal feito em console Java visando auxiliar nas tarefas de caixa. 

## Funcionalidades
Possui as seguintes funcionalidades:
- Carrinho
- Operador: Escolher
- Produtos: Gerenciar
- Fluxo de Caixa

## Diagrama de Classes
```mermaid
classDiagram
        Carrinho --> Produto : contém
    class Carrinho{
      -List<Produto> produtos
      +Carrinho(List<Produto> produtos)
      +List<Produto> getProdutos()
    }    
    class Padaria{
      +String final nome
      +String final endereco
      +String final telefone
      +String final cnpj
    }    
    class Produto{
      -String nome
      -Double precoUnitario
      -Double quantidade
      -Double valorTotal
      +Produto(String nome, Double precoUnitario, Double quantidade)
      +String getNome()
      +Double getPrecoUnitario()
      +Double getQuantidade()
      +void setQuantidade(Double)
      +Double getValorTotal()
      +Double validarValor(Double, String)
      -Double calcularSubTotal()
      +String toString()
    }
    class Registro{
      -LocalDate data
      -Double qtdProduto
      -int idProduto
      -String nomeProduto
      -Double precoUnitario
      -String operador
      +Registro(data, qtdProduto, idProduto, nomeProduto, precoUnitario, operador)
      +LocalDate getData()
      +Double getQtdProduto()
      +int getIdProduto()
      +String getNomeProduto()
      +Double getPrecoUnitario()
      +String getOperador()
    }
```
