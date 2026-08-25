package ponto;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Versao grafica de Ponto. Mantida para compatibilidade com RetaGr e com
 * exercicios anteriores do projeto.
 */
public class PontoGr extends Ponto {
    private Color corPonto = Color.BLACK;
    private int diametro = 1;

    public PontoGr() {
        super();
    }

    public PontoGr(int x, int y, Color cor, int diametro) {
        super(x, y);
        setCorPonto(cor);
        setDiametro(diametro);
    }

    public void setCorPonto(Color corPonto) {
        this.corPonto = corPonto == null ? Color.BLACK : corPonto;
    }

    public Color getCorPonto() {
        return corPonto;
    }

    public void setDiametro(int diametro) {
        this.diametro = Math.max(1, diametro);
    }

    public int getDiametro() {
        return diametro;
    }

    public void desenharPonto(Graphics g) {
        Color anterior = g.getColor();
        g.setColor(corPonto);
        g.fillOval((int) getX() - diametro / 2, (int) getY() - diametro / 2,
                diametro, diametro);
        g.setColor(anterior);
    }
}
