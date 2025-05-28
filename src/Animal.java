import java.util.List;
import java.util.Random;

public abstract class Animal implements Ator {
    private static final Random rand = Randomizador.obterRandom();

    private int idade;
    private boolean vivo;
    private Localizacao localizacao;
    private Campo campo;

    public Animal(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        vivo = true;
        idade = 0;
        if (idadeAleatoria) {
            idade = rand.nextInt(obterIdadeMaxima());
        }
        this.campo = campo;
        definirLocalizacao(localizacao);
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        incrementarIdade();
        if (estaVivo()) {
            reproduzir(novosAtores);            
            Localizacao novaLocalizacao = campo.localizacaoVizinhaLivre(localizacao);
            if (novaLocalizacao != null) {
                definirLocalizacao(novaLocalizacao);
            } else {
                morrer();
            }
        }
    }

    @Override
    public boolean estaAtivo() {
        return vivo;
    }

    public boolean estaVivo() {
        return vivo;
    }

    protected void morrer() {
        vivo = false;
        if (localizacao != null) {
            campo.limpar(localizacao);
            localizacao = null;
            campo = null;
        }
    }

    protected void incrementarIdade() {
        idade++;
        if (idade > obterIdadeMaxima()) {
            morrer();
        }
    }

    protected void reproduzir(List<Ator> novosAtores) {
        List<Localizacao> locaisLivres = campo.localizacoesVizinhasLivres(localizacao);
        int nascimentos = procriar();
        for (int n = 0; n < nascimentos && locaisLivres.size() > 0; n++) {
            Localizacao local = locaisLivres.remove(0);
            Animal filhote = criarNovoFilhote(false, campo, local);
            novosAtores.add(filhote); // Filhotes adicionados como Ator
        }
    }

    protected int procriar() {
        int nascimentos = 0;
        if (podeProcriar() && rand.nextDouble() <= obterProbabilidadeReproducao()) {
            nascimentos = rand.nextInt(obterTamanhoMaximoNinhada()) + 1;
        }
        return nascimentos;
    }

    private boolean podeProcriar() {
        return idade >= obterIdadeReproducao();
    }

    public Localizacao obterLocalizacao() {
        return localizacao;
    }

    protected void definirLocalizacao(Localizacao novaLocalizacao) {
        if (localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.colocar(this, novaLocalizacao);
    }

    public Campo obterCampo() {
        return campo;
    }

    protected abstract int obterIdadeMaxima();

    protected abstract Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao);

    protected abstract int obterIdadeReproducao();

    protected abstract double obterProbabilidadeReproducao();

    protected abstract int obterTamanhoMaximoNinhada();
}