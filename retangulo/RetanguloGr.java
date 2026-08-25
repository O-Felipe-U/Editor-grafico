package retangulo;

import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;
import reta.RetaGr;

/**
 * Implementacao da classe retangulo grafico.
 *
 * Um retangulo grafico e desenhado como 4 retas (lados), reaproveitando
 * o RetaGr (e portanto o algoritmo do ponto medio) ja implementado no
 * pacote "reta" - assim os 4 lados sao desenhados exatamente da mesma
 * forma que qualquer outra reta do projeto.
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class RetanguloGr extends Retangulo {

    Color corRetangulo = Color.BLACK;
    String nomeRetangulo = "";
    Color corNomeRetangulo = Color.BLACK;
    int espRetangulo = 1;

    /**
     * Constroi um retangulo grafico a partir de dois cantos opostos
     *
     * @param p1 1o canto do retangulo
     * @param p2 2o canto do retangulo (oposto a p1)
     * @param cor cor do retangulo
     * @param nome nome do retangulo
     * @param esp espessura do traco
     */
    public RetanguloGr(Ponto p1, Ponto p2, Color cor, String nome, int esp) {
        super(p1, p2);
        setCorRetangulo(cor);
        setNomeRetangulo(nome);
        setEspRetangulo(esp);
    }

    /**
     * Altera a cor do retangulo
     *
     * @param cor nova cor
     */
    public void setCorRetangulo(Color cor) {
        this.corRetangulo = cor;
    }

    /**
     * Retorna a cor do retangulo
     *
     * @return cor do retangulo
     */
    public Color getCorRetangulo() {
        return this.corRetangulo;
    }

    /**
     * Altera o nome do retangulo
     *
     * @param nome novo nome
     */
    public void setNomeRetangulo(String nome) {
        this.nomeRetangulo = nome;
    }

    /**
     * Retorna o nome do retangulo
     *
     * @return nome do retangulo
     */
    public String getNomeRetangulo() {
        return this.nomeRetangulo;
    }

    /**
     * Altera a espessura do traco do retangulo
     *
     * @param esp nova espessura
     */
    public void setEspRetangulo(int esp) {
        this.espRetangulo = esp;
    }

    /**
     * Retorna a espessura do traco do retangulo
     *
     * @return espessura do traco
     */
    public int getEspRetangulo() {
        return this.espRetangulo;
    }

    /**
     * Desenha o retangulo grafico como 4 retas (lados), cada uma desenhada
     * pelo algoritmo do ponto medio ja implementado em RetaGr.desenharReta().
     *
     * A partir dos dois cantos opostos p1=(x1,y1) e p2=(x2,y2), os outros
     * dois cantos sao (x1,y2) e (x2,y1). Os 4 lados ligam:
     *   p1        -> (x2,y1)   [lado de cima]
     *   (x2,y1)   -> p2        [lado direito]
     *   p2        -> (x1,y2)   [lado de baixo]
     *   (x1,y2)   -> p1        [lado esquerdo]
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharRetangulo(Graphics g) {

        int x1 = (int) getP1().getX();
        int y1 = (int) getP1().getY();
        int x2 = (int) getP2().getX();
        int y2 = (int) getP2().getY();

        // desenha o nome do retangulo perto do canto p1
        g.setColor(getCorNomeRetangulo());
        g.drawString(getNomeRetangulo(), x1 + getEspRetangulo(), y1);

        // cada lado e uma RetaGr independente, com a mesma cor/espessura,
        // desenhada com o algoritmo do ponto medio (ja implementado em RetaGr)
        RetaGr ladoCima = new RetaGr(x1, y1, x2, y1, getCorRetangulo(), "", getEspRetangulo());
        RetaGr ladoDireito = new RetaGr(x2, y1, x2, y2, getCorRetangulo(), "", getEspRetangulo());
        RetaGr ladoBaixo = new RetaGr(x2, y2, x1, y2, getCorRetangulo(), "", getEspRetangulo());
        RetaGr ladoEsquerdo = new RetaGr(x1, y2, x1, y1, getCorRetangulo(), "", getEspRetangulo());

        ladoCima.desenharReta(g);
        ladoDireito.desenharReta(g);
        ladoBaixo.desenharReta(g);
        ladoEsquerdo.desenharReta(g);
    }

    /**
     * Altera a cor do nome do retangulo
     *
     * @param cor nova cor do nome
     */
    public void setCorNomeRetangulo(Color cor) {
        this.corNomeRetangulo = cor;
    }

    /**
     * Retorna a cor do nome do retangulo
     *
     * @return cor do nome
     */
    public Color getCorNomeRetangulo() {
        return this.corNomeRetangulo;
    }
}
