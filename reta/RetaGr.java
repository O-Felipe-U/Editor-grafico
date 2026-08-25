package reta;

import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;

/**
 * Versao grafica de Reta. O desenho utiliza o mesmo algoritmo midpoint da
 * classe FiguraRetas para manter uma unica implementacao do algoritmo.
 */
public class RetaGr extends Reta {
    private Color corReta = Color.BLACK;
    private String nomeReta = "";
    private Color corNomeReta = Color.BLACK;
    private int espReta = 1;

    public RetaGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp) {
        super(x1, y1, x2, y2);
        setCorReta(cor);
        setNomeReta(nome);
        setEspReta(esp);
    }

    public RetaGr(Ponto p1, Ponto p2, Color cor, String nome, int esp) {
        super(p1, p2);
        setCorReta(cor);
        setNomeReta(nome);
        setEspReta(esp);
    }

    public void setCorReta(Color cor) {
        corReta = cor == null ? Color.BLACK : cor;
    }

    public void setNomeReta(String nome) {
        nomeReta = nome == null ? "" : nome;
    }

    public void setEspReta(int esp) {
        espReta = Math.max(1, esp);
    }

    public int getEspReta() { return espReta; }
    public Color getCorReta() { return corReta; }
    public String getNomeReta() { return nomeReta; }
    public Color getCorNomeReta() { return corNomeReta; }
    public void setCorNomeReta(Color cor) { corNomeReta = cor == null ? Color.BLACK : cor; }

    public void desenharReta(Graphics g) {
        FiguraRetas.desenharReta(
                g,
                (int) getP1().getX(), (int) getP1().getY(),
                (int) getP2().getX(), (int) getP2().getY(),
                nomeReta, espReta, corReta
        );
    }
}
