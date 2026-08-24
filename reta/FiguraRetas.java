package reta;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Desenha uma Reta a partir de 2 pontos (ponto1 e ponto2), usando
 * o algoritmo do ponto medio (midpoint / Bresenham).
 * 
 * @author Julio Arakaki 
 * @version 20260823
 */
public class FiguraRetas {

    public static void desenharReta(Graphics g, int x1, int y1, int x2, int y2, String label, int esp, Color cor) {
        Color corOriginal = g.getColor();
        g.setColor(cor);

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        // "steep" = reta mais inclinada que 45 graus: percorremos o
        // algoritmo trocando os papeis de x e y
        boolean steep = dy > dx;

        desenharMidpoint(g, x1, y1, x2, y2, sx, sy, esp, steep);

        if (label != null && !label.isEmpty()) {
            g.drawString(label, (x1 + x2) / 2, (y1 + y2) / 2);
        }
        g.setColor(corOriginal);
    }

    /**
     * Implementacao do algoritmo do ponto medio (midpoint) para retas.
     */
    private static void desenharMidpoint(Graphics g, int x1, int y1, int x2, int y2,
                                          int sx, int sy, int esp, boolean steep) {
        int px1 = steep ? y1 : x1;
        int py1 = steep ? x1 : y1;
        int px2 = steep ? y2 : x2;

        int passoX = steep ? sy : sx;
        int passoY = steep ? sx : sy;

        int dx = Math.abs(px2 - px1);
        int dy = Math.abs((steep ? x2 : y2) - py1);

        int d = 2 * dy - dx;
        int incrE = 2 * dy;
        int incrNE = 2 * (dy - dx);

        int x = px1;
        int y = py1;

        plotar(g, steep, x, y, esp);

        while (x != px2) {
            if (d <= 0) {
                d += incrE;
            } else {
                d += incrNE;
                y += passoY;
            }
            x += passoX;
            plotar(g, steep, x, y, esp);
        }
    }

    private static void plotar(Graphics g, boolean steep, int x, int y, int esp) {
        int tam = Math.max(1, esp);
        if (steep) {
            g.fillRect(y - tam / 2, x - tam / 2, tam, tam);
        } else {
            g.fillRect(x - tam / 2, y - tam / 2, tam, tam);
        }
    }
}
