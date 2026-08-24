package triangulo;

import java.awt.Color;
import java.awt.Graphics;

import reta.FiguraRetas;

/**
 * Desenha um Triangulo a partir de 2 pontos. Os pontos definem o
 * retangulo envolvente (bounding box); o triangulo e isosceles,
 * com um vertice no topo-meio e a base nos dois cantos inferiores
 * desse retangulo. Reaproveita o algoritmo de reta (midpoint) para
 * desenhar os 3 lados.
 * 
 * @author Julio Arakaki 
 * @version 20260823
 */
public class FiguraTriangulos {

    public static void desenharTriangulo(Graphics g, int x1, int y1, int x2, int y2, String label, int esp, Color cor) {
        int xMin = Math.min(x1, x2);
        int xMax = Math.max(x1, x2);
        int yMin = Math.min(y1, y2);
        int yMax = Math.max(y1, y2);
        int xMeio = (xMin + xMax) / 2;

        // vertices do triangulo
        int vx1 = xMeio, vy1 = yMin; // topo
        int vx2 = xMin, vy2 = yMax;  // inferior esquerdo
        int vx3 = xMax, vy3 = yMax;  // inferior direito

        FiguraRetas.desenharReta(g, vx1, vy1, vx2, vy2, "", esp, cor);
        FiguraRetas.desenharReta(g, vx2, vy2, vx3, vy3, "", esp, cor);
        FiguraRetas.desenharReta(g, vx3, vy3, vx1, vy1, "", esp, cor);

        if (label != null && !label.isEmpty()) {
            g.drawString(label, xMeio, yMin - 5);
        }
    }
}
