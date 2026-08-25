/**
 * Tipos de primitivos suportados pelo editor grafico.
 *
 * NENHUM e TODOS sao estados auxiliares da interface. Os demais valores
 * representam os primitivos que podem ser criados e armazenados na ED.
 */
public enum TipoPrimitivo {
    NENHUM("Nenhum", 0),
    PONTO("Ponto", 1),
    RETA("Reta", 2),
    CIRCULO("Circulo", 2),
    RETANGULO("Retangulo", 2),
    TRIANGULO("Triangulo", 3),
    TODOS("Todos", 0);

    private final String descricao;
    private final int cliquesNecessarios;

    TipoPrimitivo(String descricao, int cliquesNecessarios) {
        this.descricao = descricao;
        this.cliquesNecessarios = cliquesNecessarios;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCliquesNecessarios() {
        return cliquesNecessarios;
    }

    /**
     * Indica se o valor representa um primitivo que pode ser armazenado.
     */
    public boolean ehPrimitivoDesenhavel() {
        return this != NENHUM && this != TODOS;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
