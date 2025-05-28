import java.util.HashMap;

public class VisaoDeTexto implements VisaoSimulador {
    private EstatisticasCampo estatisticas;

    /**
     * Construtor da visão de texto.
     */
    public VisaoDeTexto() {
        estatisticas = new EstatisticasCampo();
    }

    @Override
    public void definirCor(Class<?> classeAnimal, java.awt.Color cor) {
        // Não implementado para visão de texto.
    }

    @Override
    public boolean ehViavel(Campo campo) {
        return estatisticas.ehViavel(campo);
    }

    @Override
    public void mostrarStatus(int passo, Campo campo) {
        estatisticas.reiniciar();
        for (int linha = 0; linha < campo.obterComprimento(); linha++) {
            for (int coluna = 0; coluna < campo.obterLargura(); coluna++) {
                Object animal = campo.obterObjetoEm(linha, coluna);
                if (animal != null) {
                    estatisticas.incrementarContagem(animal.getClass());
                }
            }
        }
        estatisticas.finalizarContagem();
        System.out.println("Passo " + passo + " - " + estatisticas.obterDetalhesPopulacao(campo));
    }

    @Override
    public void reiniciar() {
        estatisticas.reiniciar();
    }

    @Override
    public void reabilitarOpcoes() {
        // Não implementado para visão de texto.
    }
}