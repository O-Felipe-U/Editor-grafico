import java.awt.Color;
import java.awt.Graphics;

import circulo.FiguraCirculos;
import ponto.FiguraPontos;
import reta.FiguraRetas;
import retangulo.FiguraRetangulos;
import triangulo.FiguraTriangulos;

/**
 * Representa UMA figura ja desenhada no painel: guarda o tipo do
 * primitivo, os dois pontos que a originaram (ponto1 e ponto2),
 * a cor e a espessura usadas. Sabe se autodesenhar delegando para
 * a classe do pacote correspondente ao tipo.
 *
 * Guardar as figuras (em vez de so os ultimos x,y usados) e o que
 * permite que varias figuras fiquem acumuladas corretamente na tela
 * e sejam redesenhadas sempre que o painel precisar repintar.
 *
 * @author Julio Arakaki 
 * @version 20260823
 */
public class FiguraDesenhada {

    private TipoPrimitivo tipo;
    private int x1, y1; // 1o ponto clicado
    private int x2, y2; // 2o ponto clicado (== 1o ponto, no caso do PONTO)
    private Color cor;
    private int esp;

    public FiguraDesenhada(TipoPrimitivo tipo, int x1, int y1, int x2, int y2, Color cor, int esp) {
        this.tipo = tipo;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.cor = cor;
        this.esp = esp;
    }

    /**
     * Desenha esta figura, delegando para a classe responsavel
     * pelo seu tipo de primitivo.
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void desenhar(Graphics g) {
        switch (tipo) {
            case PONTO:
                FiguraPontos.desenharPonto(g, x1, y1, "", esp, cor);
                break;
            case RETA:
                FiguraRetas.desenharReta(g, x1, y1, x2, y2, "", esp, cor);
                break;
            case CIRCULO:
                FiguraCirculos.desenharCirculo(g, x1, y1, x2, y2, "", esp, cor);
                break;
            case RETANGULO:
                FiguraRetangulos.desenharRetangulo(g, x1, y1, x2, y2, "", esp, cor);
                break;
            case TRIANGULO:
                FiguraTriangulos.desenharTriangulo(g, x1, y1, x2, y2, "", esp, cor);
                break;
            default:
                break;
        }
    }
}
