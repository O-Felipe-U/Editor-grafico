package triangulo;
import ponto.Ponto;

/**
 * Triangulo matematico.
 *
 * Como um triangulo tem 3 vertices mas o projeto define as figuras a
 * partir de APENAS 2 PONTOS (para manter o mesmo padrao de clique usado em
 * reta/circulo/retangulo), o triangulo e construido assim:
 * - os 2 pontos informados formam o RETANGULO ENVOLVENTE da figura
 *   (p1 = canto superior-esquerdo, p2 = canto inferior-direito)
 * - a partir desse retangulo, calculamos um triangulo isosceles:
 *     va = base inferior esquerda  = (x1, y2)
 *     vb = base inferior direita   = (x2, y2)
 *     vc = apice (topo, centralizado) = ((x1+x2)/2, y1)
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class Triangulo {

    // Vertices do triangulo
    protected Ponto va, vb, vc;

    /**
     * Constroi um triangulo isosceles inscrito no retangulo envolvente
     * definido pelos pontos p1 (superior-esquerdo) e p2 (inferior-direito)
     *
     * @param p1 1o ponto (canto superior-esquerdo do retangulo envolvente)
     * @param p2 2o ponto (canto inferior-direito do retangulo envolvente)
     */
    public Triangulo(Ponto p1, Ponto p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        // base inferior: esquerda e direita, na altura de y2 (parte de baixo)
        this.va = new Ponto(x1, y2);
        this.vb = new Ponto(x2, y2);

        // apice: no topo (altura y1), centralizado entre x1 e x2
        this.vc = new Ponto((x1 + x2) / 2.0, y1);
    }

    /**
     * Constroi um triangulo a partir de coordenadas (int) dos 2 pontos
     * do retangulo envolvente
     *
     * @param x1 coordenada x do 1o ponto
     * @param y1 coordenada y do 1o ponto
     * @param x2 coordenada x do 2o ponto
     * @param y2 coordenada y do 2o ponto
     */
    public Triangulo(int x1, int y1, int x2, int y2) {
        this(new Ponto(x1, y1), new Ponto(x2, y2));
    }

    /**
     * Retorna o vertice A (base, esquerda)
     *
     * @return vertice A
     */
    public Ponto getVa() {
        return this.va;
    }

    /**
     * Retorna o vertice B (base, direita)
     *
     * @return vertice B
     */
    public Ponto getVb() {
        return this.vb;
    }

    /**
     * Retorna o vertice C (apice, topo)
     *
     * @return vertice C
     */
    public Ponto getVc() {
        return this.vc;
    }

    /**
     * Imprime o triangulo no formato: A: [x,y] B: [x,y] C: [x,y]
     *
     * @return string que representa o triangulo
     */
    public String toString() {
        return "A: " + getVa().toString() + " B: " + getVb().toString() + " C: " + getVc().toString();
    }
}
