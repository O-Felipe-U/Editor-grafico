package retangulo;
import ponto.Ponto;

/**
 * Retangulo matematico.
 *
 * Um retangulo e definido a partir de DOIS PONTOS que representam dois
 * cantos opostos (ex.: superior-esquerdo e inferior-direito).
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class Retangulo {

    // Atributos do retangulo: dois cantos opostos
    public Ponto p1, p2;

    /**
     * Constroi um retangulo a partir de dois cantos opostos (int)
     *
     * @param x1 coordenada x do 1o canto
     * @param y1 coordenada y do 1o canto
     * @param x2 coordenada x do 2o canto
     * @param y2 coordenada y do 2o canto
     */
    public Retangulo(int x1, int y1, int x2, int y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }

    /**
     * Constroi um retangulo a partir de dois pontos (externos)
     *
     * @param p1 1o canto do retangulo
     * @param p2 2o canto do retangulo (oposto a p1)
     */
    public Retangulo(Ponto p1, Ponto p2) {
        setP1(p1);
        setP2(p2);
    }

    /**
     * Constroi um retangulo com dados de outro (externo)
     *
     * @param r retangulo externo
     */
    public Retangulo(Retangulo r) {
        setP1(r.getP1());
        setP2(r.getP2());
    }

    /**
     * Altera valor de p1
     *
     * @param p novo valor de p1
     */
    public void setP1(Ponto p) {
        this.p1 = p;
    }

    /**
     * Altera valor de p2
     *
     * @param p novo valor de p2
     */
    public void setP2(Ponto p) {
        this.p2 = p;
    }

    /**
     * Retorna p1
     *
     * @return valor de p1
     */
    public Ponto getP1() {
        return this.p1;
    }

    /**
     * Retorna p2
     *
     * @return valor de p2
     */
    public Ponto getP2() {
        return this.p2;
    }

    /**
     * Calcula a largura do retangulo (diferenca absoluta entre os x)
     *
     * @return largura do retangulo
     */
    public double calcularLargura() {
        return Math.abs(getP2().getX() - getP1().getX());
    }

    /**
     * Calcula a altura do retangulo (diferenca absoluta entre os y)
     *
     * @return altura do retangulo
     */
    public double calcularAltura() {
        return Math.abs(getP2().getY() - getP1().getY());
    }

    /**
     * Imprime o retangulo no formato: P1: [x1,y1] P2: [x2,y2]
     *
     * @return string que representa o retangulo
     */
    public String toString() {
        return "P1: " + getP1().toString() + " P2: " + getP2().toString()
                + " (largura=" + calcularLargura() + ", altura=" + calcularAltura() + ")";
    }
}
