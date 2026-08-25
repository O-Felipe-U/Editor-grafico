package circulo;
import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;

/**
 * Contem metodos para desenhar figuras com circulo.
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class FiguraCirculos {

    /**
     * Desenha um circulo na tela, a partir do centro e de um ponto na borda
     * (2 pontos, como as demais figuras do projeto)
     *
     * @param g biblioteca grafica para desenhar elementos graficos
     * @param centro ponto central do circulo
     * @param borda ponto sobre a borda do circulo (define o raio)
     * @param nome nome do circulo
     * @param esp espessura do traco
     * @param cor cor do circulo
     */
    public static void desenharCirculo(Graphics g, Ponto centro, Ponto borda, String nome, int esp, Color cor) {
        CirculoGr c = new CirculoGr(centro, borda, cor, nome, esp);
        c.desenharCirculo(g);
    }

    /**
     * Desenha varios circulos na tela com cores diferentes (uso similar ao
     * desenharPontos/desenharRetas)
     *
     * @param g biblioteca grafica para desenhar elementos graficos
     * @param qtde quantidade de circulos
     * @param esp espessura do traco
     */
    public static void desenharCirculos(Graphics g, int qtde, int esp) {

        for (int i = 0; i < qtde; i++) {
            int xc = (int) (Math.random() * 801);
            int yc = (int) (Math.random() * 801);
            int r = (int) (Math.random() * 100) + 10;

            // Cor (R, G e B) aleatorio
            Color cor = new Color((int) (Math.random() * 256),
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256));

            CirculoGr c = new CirculoGr(new Ponto(xc, yc), new Ponto(xc + r, yc), cor, "", esp);
            c.desenharCirculo(g);
        }
    }
}
