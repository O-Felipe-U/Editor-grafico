package triangulo;

import java.awt.Color;
import java.awt.Graphics;

import reta.FiguraRetas;

/**
 * Desenha um triangulo a partir de tres pontos escolhidos pelo usuario.
 * Os tres lados reutilizam o algoritmo midpoint implementado para retas.
 */
public class FiguraTriangulos {

    public static void desenharTriangulo(Graphics g,
                                         int x1, int y1,
                                         int x2, int y2,
                                         int x3, int y3,
                                         String label, int esp, Color cor) {
        FiguraRetas.desenharReta(g, x1, y1, x2, y2, "", esp, cor);
        FiguraRetas.desenharReta(g, x2, y2, x3, y3, "", esp, cor);
        FiguraRetas.desenharReta(g, x3, y3, x1, y1, "", esp, cor);

        if (label != null && !label.isEmpty()) {
            int centroX = (x1 + x2 + x3) / 3;
            int centroY = (y1 + y2 + y3) / 3;
            g.drawString(label, centroX, centroY);
        }
    }

    /**
     * Sobrecarga mantida para compatibilidade com o codigo anterior.
     * Gera um triangulo isosceles a partir de dois cantos opostos.
     */
    public static void desenharTriangulo(Graphics g, int x1, int y1, int x2, int y2,
                                         String label, int esp, Color cor) {
        int xMin = Math.min(x1, x2);
        int xMax = Math.max(x1, x2);
        int yMin = Math.min(y1, y2);
        int yMax = Math.max(y1, y2);
        int xMeio = (xMin + xMax) / 2;

        desenharTriangulo(g,
                xMeio, yMin,
                xMin, yMax,
                xMax, yMax,
                label, esp, cor);
    }
}
