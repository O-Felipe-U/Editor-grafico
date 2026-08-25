package circulo;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Desenha um Circulo a partir de 2 pontos: ponto1 = centro,
 * ponto2 = define o raio (distancia entre os dois pontos).
 * Usa o algoritmo do ponto medio (midpoint) para circunferencia,
 * com simetria de 8 partes (octantes).
 * 
 * @author Julio Arakaki 
 * @version 20260823
 */
public class FiguraCirculos {

    public static void desenharCirculo(Graphics g, int xc, int yc, int xr, int yr, String label, int esp, Color cor) {
        Color corOriginal = g.getColor();
        g.setColor(cor);

        int raio = (int) Math.round(Math.hypot(xr - xc, yr - yc));
        desenharMidpoint(g, xc, yc, raio, esp);

        if (label != null && !label.isEmpty()) {
            g.drawString(label, xc + raio, yc);
        }
        g.setColor(corOriginal);
    }

    /**
     * Implementacao do algoritmo do ponto medio (midpoint) para circulos.
     */
    private static void desenharMidpoint(Graphics g, int xc, int yc, int raio, int esp) {
        int x = 0;
        int y = raio;
        int d = 1 - raio;

        plotarOctantes(g, xc, yc, x, y, esp);

        while (x < y) {
            x++;
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                y--;
                d += 2 * (x - y) + 1;
            }
            plotarOctantes(g, xc, yc, x, y, esp);
        }
    }

    private static void plotarOctantes(Graphics g, int xc, int yc, int x, int y, int esp) {
        plotar(g, xc + x, yc + y, esp);
        plotar(g, xc - x, yc + y, esp);
        plotar(g, xc + x, yc - y, esp);
        plotar(g, xc - x, yc - y, esp);
        plotar(g, xc + y, yc + x, esp);
        plotar(g, xc - y, yc + x, esp);
        plotar(g, xc + y, yc - x, esp);
        plotar(g, xc - y, yc - x, esp);
    }

    private static void plotar(Graphics g, int x, int y, int esp) {
        int tam = Math.max(1, esp);
        g.fillRect(x - tam / 2, y - tam / 2, tam, tam);
    }
}
