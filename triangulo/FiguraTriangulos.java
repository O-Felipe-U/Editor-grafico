package triangulo;
import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;

/**
 * Contem metodos para desenhar figuras com triangulo.
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class FiguraTriangulos {

    /**
     * Desenha um triangulo na tela, a partir dos 2 pontos do retangulo
     * envolvente
     *
     * @param g biblioteca grafica para desenhar elementos graficos
     * @param p1 1o ponto (canto superior-esquerdo do retangulo envolvente)
     * @param p2 2o ponto (canto inferior-direito do retangulo envolvente)
     * @param nome nome do triangulo
     * @param esp espessura do traco
     * @param cor cor do triangulo
     */
    public static void desenharTriangulo(Graphics g, Ponto p1, Ponto p2, String nome, int esp, Color cor) {
        TrianguloGr t = new TrianguloGr(p1, p2, cor, nome, esp);
        t.desenharTriangulo(g);
    }

    /**
     * Desenha varios triangulos na tela com cores diferentes
     *
     * @param g biblioteca grafica para desenhar elementos graficos
     * @param qtde quantidade de triangulos
     * @param esp espessura do traco
     */
    public static void desenharTriangulos(Graphics g, int qtde, int esp) {

        for (int i = 0; i < qtde; i++) {
            int x1 = (int) (Math.random() * 701);
            int y1 = (int) (Math.random() * 701);
            int x2 = x1 + (int) (Math.random() * 100) + 10;
            int y2 = y1 + (int) (Math.random() * 100) + 10;

            // Cor (R, G e B) aleatorio
            Color cor = new Color((int) (Math.random() * 256),
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256));

            TrianguloGr t = new TrianguloGr(new Ponto(x1, y1), new Ponto(x2, y2), cor, "", esp);
            t.desenharTriangulo(g);
        }
    }
}
