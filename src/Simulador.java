import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulador {
    private static final int LARGURA_PADRAO = 120;
    private static final int COMPRIMENTO_PADRAO = 80;

    private List<Ator> atores; // Alterado de List<Animal> para List<Ator>
    private Campo campo;
    private int passo;
    private List<VisaoSimulador> visoes;

    public Simulador() {
        this(COMPRIMENTO_PADRAO, LARGURA_PADRAO);
    }

    public Simulador(int comprimento, int largura) {
        if (largura <= 0 || comprimento <= 0) {
            System.out.println("As dimensões devem ser >= zero.");
            System.out.println("Usando valores padrões.");
            comprimento = COMPRIMENTO_PADRAO;
            largura = LARGURA_PADRAO;
        }

        atores = new ArrayList<>();
        campo = new Campo(comprimento, largura);

        visoes = new ArrayList<>();

        VisaoSimulador visao = new VisaoDeGrade(comprimento, largura, this);
        GeradorDePopulacoes.definirCores(visao);
        visoes.add(visao);

        visao = new VisaoDeGrafico(800, 400, 500);
        GeradorDePopulacoes.definirCores(visao);
        visoes.add(visao);

        visao = new VisaoDeTexto();
        visoes.add(visao);

        reiniciar();
    }

    public void executarSimulacaoLonga() {
        simular(4000, 0);
    }

    public void simular(int numPassos, int atraso) {
        for (int passo = 1; passo <= numPassos && visoes.get(0).ehViavel(campo); passo++) {
            simularUmPasso();
            if (atraso > 0) {
                pausar(atraso);
            }
        }
        reabilitarOpcoesVisoes();
    }

    public void simularUmPasso() {
        passo++;

        List<Ator> novosAtores = new ArrayList<>();

        for (Iterator<Ator> it = atores.iterator(); it.hasNext(); ) {
            Ator ator = it.next();
            ator.agir(novosAtores);
            if (!ator.estaAtivo()) {
                it.remove();
            }
        }

        atores.addAll(novosAtores);

        atualizarVisoes();
    }

    public void reiniciar() {
        passo = 0;
        atores.clear();
        for (VisaoSimulador visao : visoes) {
            visao.reiniciar();
        }

        GeradorDePopulacoes.povoar(campo, atores);

        atualizarVisoes();
        reabilitarOpcoesVisoes();
    }

    private void atualizarVisoes() {
        for (VisaoSimulador visao : visoes) {
            visao.mostrarStatus(passo, campo);
        }
    }

    private void reabilitarOpcoesVisoes() {
        for (VisaoSimulador visao : visoes) {
            visao.reabilitarOpcoes();
        }
    }

    private void pausar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException ie) {
            // Do nothing
        }
    }
}