package ponto;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Desenha um Ponto. E o unico primitivo que precisa de apenas
 * 1 clique (nao 2) para ser definido: o proprio ponto clicado.
 * 
 * @author Julio Arakaki 
 * @version 20260823
 */
public class FiguraPontos {

    public static void desenharPonto(Graphics g, int x, int y, String label, int esp, Color cor) {
        Color corOriginal = g.getColor();
        g.setColor(cor);

        int tam = Math.max(1, esp);
        g.fillOval(x - tam / 2, y - tam / 2, tam, tam);

        if (label != null && !label.isEmpty()) {
            g.drawString(label, x + tam, y);
        }
        g.setColor(corOriginal);
    }
}
