import java.util.List;

/**
 * Um modelo simples de um coelho.
 * Coelhos envelhecem, se movem, se reproduzem e morrem.
 * 
 * @author David
 * Traduzido por Julio César Alves
 * @version 2025.05.24
 */
public class Coelho extends Animal {
    private static final int IDADE_REPRODUCAO = 5;
    private static final int IDADE_MAXIMA = 40;
    private static final double PROBABILIDADE_REPRODUCAO = 0.12;
    private static final int TAMANHO_MAXIMO_NINHADA = 4;

    public Coelho(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        super(idadeAleatoria, campo, localizacao);
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        incrementarIdade();
        if (estaVivo()) {
            reproduzir(novosAtores);
            Localizacao novaLocalizacao = obterCampo().localizacaoVizinhaLivre(obterLocalizacao());
            if (novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
            } else {
                morrer();
            }
        }
    }

    @Override
    protected Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        return new Coelho(false, campo, localizacao);
    }

    @Override
    protected int obterIdadeMaxima() {
        return IDADE_MAXIMA;
    }

    @Override
    protected int obterIdadeReproducao() {
        return IDADE_REPRODUCAO;
    }

    @Override
    protected double obterProbabilidadeReproducao() {
        return PROBABILIDADE_REPRODUCAO;
    }

    @Override
    protected int obterTamanhoMaximoNinhada() {
        return TAMANHO_MAXIMO_NINHADA;
    }
}