package Models;

import java.time.LocalDate;

public class FluxoDeCaixa {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String operador;

    public FluxoDeCaixa(LocalDate dataInicial, LocalDate dataFinal) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public String getOperador() {
        return operador;
    }
}
