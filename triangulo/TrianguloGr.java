package triangulo;

import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;
import reta.RetaGr;

/**
 * Implementacao da classe triangulo grafico.
 *
 * Assim como o RetanguloGr, o triangulo grafico e desenhado como 3 retas
 * (lados), reaproveitando o RetaGr (e portanto o algoritmo do ponto medio)
 * ja implementado no pacote "reta".
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class TrianguloGr extends Triangulo {

    Color corTriangulo = Color.BLACK;
    String nomeTriangulo = "";
    Color corNomeTriangulo = Color.BLACK;
    int espTriangulo = 1;

    /**
     * Constroi um triangulo grafico a partir dos 2 pontos do retangulo
     * envolvente
     *
     * @param p1 1o ponto (canto superior-esquerdo do retangulo envolvente)
     * @param p2 2o ponto (canto inferior-direito do retangulo envolvente)
     * @param cor cor do triangulo
     * @param nome nome do triangulo
     * @param esp espessura do traco
     */
    public TrianguloGr(Ponto p1, Ponto p2, Color cor, String nome, int esp) {
        super(p1, p2);
        setCorTriangulo(cor);
        setNomeTriangulo(nome);
        setEspTriangulo(esp);
    }

    /**
     * Altera a cor do triangulo
     *
     * @param cor nova cor
     */
    public void setCorTriangulo(Color cor) {
        this.corTriangulo = cor;
    }

    /**
     * Retorna a cor do triangulo
     *
     * @return cor do triangulo
     */
    public Color getCorTriangulo() {
        return this.corTriangulo;
    }

    /**
     * Altera o nome do triangulo
     *
     * @param nome novo nome
     */
    public void setNomeTriangulo(String nome) {
        this.nomeTriangulo = nome;
    }

    /**
     * Retorna o nome do triangulo
     *
     * @return nome do triangulo
     */
    public String getNomeTriangulo() {
        return this.nomeTriangulo;
    }

    /**
     * Altera a espessura do traco do triangulo
     *
     * @param esp nova espessura
     */
    public void setEspTriangulo(int esp) {
        this.espTriangulo = esp;
    }

    /**
     * Retorna a espessura do traco do triangulo
     *
     * @return espessura do traco
     */
    public int getEspTriangulo() {
        return this.espTriangulo;
    }

    /**
     * Altera a cor do nome do triangulo
     *
     * @param cor nova cor do nome
     */
    public void setCorNomeTriangulo(Color cor) {
        this.corNomeTriangulo = cor;
    }

    /**
     * Retorna a cor do nome do triangulo
     *
     * @return cor do nome
     */
    public Color getCorNomeTriangulo() {
        return this.corNomeTriangulo;
    }

    /**
     * Desenha o triangulo grafico como 3 retas (lados: A-B, B-C e C-A),
     * cada uma desenhada pelo algoritmo do ponto medio ja implementado em
     * RetaGr.desenharReta().
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharTriangulo(Graphics g) {

        int xa = (int) getVa().getX();
        int ya = (int) getVa().getY();
        int xb = (int) getVb().getX();
        int yb = (int) getVb().getY();
        int xc = (int) getVc().getX();
        int yc = (int) getVc().getY();

        // desenha o nome do triangulo perto do apice (vc)
        g.setColor(getCorNomeTriangulo());
        g.drawString(getNomeTriangulo(), xc + getEspTriangulo(), yc);

        // cada lado e uma RetaGr independente, desenhada com o algoritmo
        // do ponto medio (ja implementado em RetaGr)
        RetaGr ladoAB = new RetaGr(xa, ya, xb, yb, getCorTriangulo(), "", getEspTriangulo());
        RetaGr ladoBC = new RetaGr(xb, yb, xc, yc, getCorTriangulo(), "", getEspTriangulo());
        RetaGr ladoCA = new RetaGr(xc, yc, xa, ya, getCorTriangulo(), "", getEspTriangulo());

        ladoAB.desenharReta(g);
        ladoBC.desenharReta(g);
        ladoCA.desenharReta(g);
    }
}
