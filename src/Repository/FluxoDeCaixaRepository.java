package Repository;

import Models.Produto;
import Models.Registro;
import Util.FluxoDeCaixaUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static Util.ExcecaoUtil.erro;
import static Util.FluxoDeCaixaUtil.*;

public class FluxoDeCaixaRepository {
    static String diretorio = "arquivo/fluxoDeCaixa.txt";

    public static List<Registro> importarRegistros(ArquivoUtil arquivo) {
        List<String> registrosString = arquivo.lerArquivo(diretorio);

        return registrosString.stream()
                .map(FluxoDeCaixaUtil::parseRegistro)
                //.map(this::converterParaRegistro)
                .filter(registro -> registro != null)
                .collect(Collectors.toList());
    }

    public static void salvar(Map<Integer, Produto> produtos, String operador, ArquivoUtil importaArquivo) {
        try {
            List<String> registrosAntigos = importaArquivo.lerArquivo(diretorio);
            List<String> registrosNovos = registrosParaTexto(produtos, operador);
            String resultado = gerarTextoFinal(registrosAntigos, registrosNovos);

            importaArquivo.exportar(resultado, diretorio);
        } catch (Exception e) {
            throw erro(e);
        }
    }
}
