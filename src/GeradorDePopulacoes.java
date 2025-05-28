import java.util.List;
import java.util.Random;
import java.awt.Color;

public class GeradorDePopulacoes {
    private static final double PROBABILIDADE_CRIACAO_RAPOSA = 0.02;
    private static final double PROBABILIDADE_CRIACAO_COELHO = 0.08;

    public static void definirCores(VisaoSimulador visao) {
        visao.definirCor(Coelho.class, Color.ORANGE);
        visao.definirCor(Raposa.class, Color.BLUE);
    }

    public static void povoar(Campo campo, List<Ator> atores) {
        Random rand = Randomizador.obterRandom();
        campo.limpar();
        for (int linha = 0; linha < campo.obterComprimento(); linha++) {
            for (int coluna = 0; coluna < campo.obterLargura(); coluna++) {
                if (rand.nextDouble() <= PROBABILIDADE_CRIACAO_RAPOSA) {
                    Localizacao localizacao = new Localizacao(linha, coluna);
                    Raposa raposa = new Raposa(true, campo, localizacao);
                    atores.add(raposa);
                } else if (rand.nextDouble() <= PROBABILIDADE_CRIACAO_COELHO) {
                    Localizacao localizacao = new Localizacao(linha, coluna);
                    Coelho coelho = new Coelho(true, campo, localizacao);
                    atores.add(coelho);
                }
            }
        }
    }
}