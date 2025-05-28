import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Um modelo simples de uma raposa.
 * Raposas envelhecem, se movem, comem coelhos e morrem.
 * 
 * @author David J. Barnes
 * Traduzido por Julio César Alves
 * @version 2025.05.24
 */
public class Raposa extends Animal {
    private static final int IDADE_REPRODUCAO = 15;
    private static final int IDADE_MAXIMA = 150;
    private static final double PROBABILIDADE_REPRODUCAO = 0.08;
    private static final int TAMANHO_MAXIMO_NINHADA = 2;
    private static final int VALOR_COMIDA_COELHO = 9;
    private static final int NIVEL_COMIDA_MAXIMO = 20;
    private static final Random rand = Randomizador.obterRandom();

    private int nivelComida;

    public Raposa(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        super(idadeAleatoria, campo, localizacao);
        if (idadeAleatoria) {
            nivelComida = rand.nextInt(VALOR_COMIDA_COELHO);
        } else {
            nivelComida = VALOR_COMIDA_COELHO;
        }
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        incrementarIdade();
        incrementarFome();
        if (estaVivo()) {
            reproduzir(novosAtores);
            Localizacao novaLocalizacao = buscarComida();
            if (novaLocalizacao == null) {
                novaLocalizacao = obterCampo().localizacaoVizinhaLivre(obterLocalizacao());
            }
            if (novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
            } else {
                morrer();
            }
        }
    }

    private void incrementarFome() {
        nivelComida--;
        if (nivelComida <= 0) {
            morrer();
        }
    }

    private Localizacao buscarComida() {
        List<Localizacao> vizinhas = obterCampo().localizacoesVizinhas(obterLocalizacao());
        Iterator<Localizacao> it = vizinhas.iterator();
        while (it.hasNext()) {
            Localizacao onde = it.next();
            Object animal = obterCampo().obterObjetoEm(onde);
            if (animal instanceof Coelho) {
                Coelho coelho = (Coelho) animal;
                if (coelho.estaVivo()) {
                    coelho.morrer();
                    nivelComida += VALOR_COMIDA_COELHO;
                    if (nivelComida > NIVEL_COMIDA_MAXIMO) {
                        nivelComida = NIVEL_COMIDA_MAXIMO;
                    }
                    return onde;
                }
            }
        }
        return null;
    }

    @Override
    protected Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        return new Raposa(false, campo, localizacao);
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