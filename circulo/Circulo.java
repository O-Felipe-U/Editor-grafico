package circulo;
import ponto.Ponto;

/**
 * Circulo matematico.
 *
 * Um circulo e definido a partir de DOIS PONTOS:
 * - o 1o ponto (centro) e o centro do circulo
 * - o 2o ponto (borda) e um ponto qualquer sobre a borda (define o raio,
 *   pela distancia entre os dois pontos)
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class Circulo {

    // Atributos do circulo
    protected Ponto centro;
    protected double raio;

    /**
     * Constroi um circulo a partir do centro e de um ponto na borda.
     * O raio e calculado como a distancia entre os dois pontos.
     *
     * @param centro ponto central do circulo
     * @param borda ponto sobre a borda do circulo
     */
    public Circulo(Ponto centro, Ponto borda) {
        setCentro(centro);
        setRaio(centro.calcularDistancia(borda));
    }

    /**
     * Constroi um circulo a partir de coordenadas (int) do centro e da borda
     *
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param xb coordenada x do ponto da borda
     * @param yb coordenada y do ponto da borda
     */
    public Circulo(int xc, int yc, int xb, int yb) {
        this(new Ponto(xc, yc), new Ponto(xb, yb));
    }

    /**
     * Constroi um circulo diretamente a partir do centro e do raio
     *
     * @param centro ponto central do circulo
     * @param raio raio do circulo
     */
    public Circulo(Ponto centro, double raio) {
        setCentro(centro);
        setRaio(raio);
    }

    /**
     * Constroi um circulo com dados de outro (externo)
     *
     * @param c circulo externo
     */
    public Circulo(Circulo c) {
        setCentro(new Ponto(c.getCentro()));
        setRaio(c.getRaio());
    }

    /**
     * Altera o centro do circulo
     *
     * @param centro novo centro
     */
    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    /**
     * Retorna o centro do circulo
     *
     * @return centro do circulo
     */
    public Ponto getCentro() {
        return this.centro;
    }

    /**
     * Altera o raio do circulo
     *
     * @param raio novo raio
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }

    /**
     * Retorna o raio do circulo
     *
     * @return raio do circulo
     */
    public double getRaio() {
        return this.raio;
    }

    /**
     * Imprime o circulo no formato: Centro: [x, y] Raio: r
     *
     * @return string que representa o circulo
     */
    public String toString() {
        return "Centro: " + getCentro().toString() + " Raio: " + getRaio();
    }
}
