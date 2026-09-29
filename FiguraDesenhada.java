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
 * @version 20260922
 */
public class FiguraDesenhada {

    // tipo do primitivo (PONTO, RETA, CIRCULO, RETANGULO ou TRIANGULO)
    private TipoPrimitivo tipo;

    // Para PONTO: (x1, y1) e a posicao do ponto (x2, y2 nao sao usados).
    // Para RETA/CIRCULO/RETANGULO/TRIANGULO criados pelo editor:
    // (x1, y1) e (x2, y2) sao os dois pontos usados para construir a figura.
    private int x1, y1, x2, y2;

    // O arquivo JSON de referencia guarda triangulo com tres vertices.
    // Estes campos sao usados somente quando um triangulo e lido desse formato.
    // Para as demais figuras (e para triangulos criados normalmente no editor),
    // temTerceiroPonto permanece false e o comportamento antigo e preservado.
    private int x3, y3;
    private boolean temTerceiroPonto;

    private String nome;
    private int esp;
    private Color cor;

    /**
     * Constroi o registro de uma figura no formato original do projeto.
     */
    public FiguraDesenhada(TipoPrimitivo tipo, int x1, int y1, int x2, int y2,
                           String nome, int esp, Color cor) {
        this.tipo = tipo;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = 0;
        this.y3 = 0;
        this.temTerceiroPonto = false;
        this.nome = nome;
        this.esp = esp;
        this.cor = cor;
    }

    /**
     * Construtor usado ao abrir um triangulo que vem do JSON com p1, p2 e p3.
     * Mantem os tres vertices exatamente como estao no arquivo.
     */
    public FiguraDesenhada(TipoPrimitivo tipo,
                           int x1, int y1, int x2, int y2, int x3, int y3,
                           String nome, int esp, Color cor) {
        this(tipo, x1, y1, x2, y2, nome, esp, cor);
        this.x3 = x3;
        this.y3 = y3;
        this.temTerceiroPonto = true;
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

    public int getX3() {
        return x3;
    }

    public int getY3() {
        return y3;
    }

    public boolean temTerceiroPonto() {
        return temTerceiroPonto;
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
