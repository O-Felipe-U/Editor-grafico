package retangulo;

import java.awt.Color;
import java.awt.Graphics;

import reta.FiguraRetas;

/**
 * Desenha um Retangulo a partir de 2 pontos: os dois pontos sao
 * os cantos opostos do retangulo. Reaproveita o algoritmo de reta
 * (midpoint) para desenhar os 4 lados.
 * 
 * @author Julio Arakaki 
 * @version 20260823
 */
public class FiguraRetangulos {

    public static void desenharRetangulo(Graphics g, int x1, int y1, int x2, int y2, String label, int esp, Color cor) {
        // lado superior
        FiguraRetas.desenharReta(g, x1, y1, x2, y1, "", esp, cor);
        // lado inferior
        FiguraRetas.desenharReta(g, x1, y2, x2, y2, "", esp, cor);
        // lado esquerdo
        FiguraRetas.desenharReta(g, x1, y1, x1, y2, "", esp, cor);
        // lado direito
        FiguraRetas.desenharReta(g, x2, y1, x2, y2, "", esp, cor);

        if (label != null && !label.isEmpty()) {
            g.drawString(label, Math.min(x1, x2), Math.min(y1, y2) - 5);
        }
    }
}
