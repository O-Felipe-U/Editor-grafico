import java.awt.Color;

/**
 * Guarda todas as informacoes necessarias para desenhar novamente um
 * primitivo grafico (ponto, reta, circulo, retangulo ou triangulo) que ja
 * foi desenhado na tela.
 *
 * Cada figura desenhada pelo usuario vira um objeto FiguraDesenhada, que e
 * guardado em uma EDL (lista) dentro do PainelDesenho. Assim, a tela pode
 * ser inteiramente redesenhada a qualquer momento - por exemplo, apos um
 * "Limpar" seguido de um "Redesenhar".
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class FiguraDesenhada {

    // tipo do primitivo (PONTO, RETA, CIRCULO, RETANGULO ou TRIANGULO)
    private TipoPrimitivo tipo;

    // Para PONTO: (x1, y1) e a posicao do ponto (x2, y2 nao sao usados).
    // Para RETA/CIRCULO/RETANGULO/TRIANGULO: (x1, y1) e (x2, y2) sao os
    // dois pontos usados para construir a figura (1o e 2o clique do mouse)
    private int x1, y1, x2, y2;

    private String nome;
    private int esp;
    private Color cor;

    /**
     * Constroi o registro de uma figura ja desenhada
     *
     * @param tipo tipo do primitivo
     * @param x1 coordenada x do 1o ponto
     * @param y1 coordenada y do 1o ponto
     * @param x2 coordenada x do 2o ponto (nao usado quando tipo == PONTO)
     * @param y2 coordenada y do 2o ponto (nao usado quando tipo == PONTO)
     * @param nome nome da figura
     * @param esp espessura/diametro usado no desenho
     * @param cor cor da figura
     */
    public FiguraDesenhada(TipoPrimitivo tipo, int x1, int y1, int x2, int y2, String nome, int esp, Color cor) {
        this.tipo = tipo;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.nome = nome;
        this.esp = esp;
        this.cor = cor;
    }

    public TipoPrimitivo getTipo() {
        return tipo;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public String getNome() {
        return nome;
    }

    public int getEsp() {
        return esp;
    }

    public Color getCor() {
        return cor;
    }
}
