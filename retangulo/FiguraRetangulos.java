package retangulo;
import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;

/**
 * Contem metodos para desenhar figuras com retangulo.
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class FiguraRetangulos {

    /**
     * Desenha um retangulo na tela, a partir de dois cantos opostos
     *
     * @param g biblioteca grafica para desenhar elementos graficos
     * @param p1 1o canto do retangulo
     * @param p2 2o canto do retangulo (oposto a p1)
     * @param nome nome do retangulo
     * @param esp espessura do traco
     * @param cor cor do retangulo
     */
    public static void desenharRetangulo(Graphics g, Ponto p1, Ponto p2, String nome, int esp, Color cor) {
        RetanguloGr r = new RetanguloGr(p1, p2, cor, nome, esp);
        r.desenharRetangulo(g);
    }

    /**
     * Desenha varios retangulos na tela com cores diferentes
     *
     * @param g biblioteca grafica para desenhar elementos graficos
     * @param qtde quantidade de retangulos
     * @param esp espessura do traco
     */
    public static void desenharRetangulos(Graphics g, int qtde, int esp) {

        for (int i = 0; i < qtde; i++) {
            int x1 = (int) (Math.random() * 701);
            int y1 = (int) (Math.random() * 701);
            int x2 = x1 + (int) (Math.random() * 100) + 10;
            int y2 = y1 + (int) (Math.random() * 100) + 10;

            // Cor (R, G e B) aleatorio
            Color cor = new Color((int) (Math.random() * 256),
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256));

            RetanguloGr r = new RetanguloGr(new Ponto(x1, y1), new Ponto(x2, y2), cor, "", esp);
            r.desenharRetangulo(g);
        }
    }
}
