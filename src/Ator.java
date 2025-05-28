import java.util.List;

public interface Ator {
    /**
     * Define o comportamento do ator para cada passo da simulação.
     * @param novosAtores Uma lista para armazenar os novos atores criados.
     */
    void agir(List<Ator> novosAtores);

    /**
     * Verifica se o ator está ativo.
     * @return true se o ator estiver ativo, false caso contrário.
     */
    boolean estaAtivo();
}