import java.awt.Color;
import java.awt.Graphics;

import circulo.FiguraCirculos;
import ponto.FiguraPontos;
import reta.FiguraRetas;
import retangulo.FiguraRetangulos;
import triangulo.FiguraTriangulos;

/**
 * Representa um primitivo grafico concluido.
 *
 * A classe guarda o tipo, os pontos de construcao, a cor e a espessura.
 * Ela tambem sabe redesenhar o primitivo delegando ao algoritmo do pacote
 * correspondente. Isso permite que o mesmo objeto seja armazenado na ED e
 * redesenhado quantas vezes forem necessarias.
 */
public class FiguraDesenhada {
    private final TipoPrimitivo tipo;
    private final int[] xs;
    private final int[] ys;
    private final Color cor;
    private final int esp;

    /**
     * Cria uma figura a partir de um ou mais pontos.
     */
    public FiguraDesenhada(TipoPrimitivo tipo, int[] xs, int[] ys, Color cor, int esp) {
        if (tipo == null || !tipo.ehPrimitivoDesenhavel()) {
            throw new IllegalArgumentException("Tipo invalido para uma figura desenhada.");
        }
        if (xs == null || ys == null || xs.length != ys.length) {
            throw new IllegalArgumentException("Coordenadas invalidas.");
        }
        if (xs.length != tipo.getCliquesNecessarios()) {
            throw new IllegalArgumentException(
                    tipo + " precisa de " + tipo.getCliquesNecessarios() + " ponto(s)."
            );
        }

        this.tipo = tipo;
        this.xs = xs.clone();
        this.ys = ys.clone();
        this.cor = cor == null ? Color.BLACK : cor;
        this.esp = Math.max(1, esp);
    }

    public TipoPrimitivo getTipo() {
        return tipo;
    }

    public Color getCor() {
        return cor;
    }

    public int getEspessura() {
        return esp;
    }

    public int getQuantidadePontos() {
        return xs.length;
    }

    public int getX(int indice) {
        return xs[indice];
    }

    public int getY(int indice) {
        return ys[indice];
    }

    /**
     * Desenha esta figura usando o algoritmo implementado para o seu tipo.
     */
    public void desenhar(Graphics g) {
        switch (tipo) {
            case PONTO:
                FiguraPontos.desenharPonto(g, xs[0], ys[0], "", esp, cor);
                break;
            case RETA:
                FiguraRetas.desenharReta(g, xs[0], ys[0], xs[1], ys[1], "", esp, cor);
                break;
            case CIRCULO:
                FiguraCirculos.desenharCirculo(g, xs[0], ys[0], xs[1], ys[1], "", esp, cor);
                break;
            case RETANGULO:
                FiguraRetangulos.desenharRetangulo(g, xs[0], ys[0], xs[1], ys[1], "", esp, cor);
                break;
            case TRIANGULO:
                FiguraTriangulos.desenharTriangulo(
                        g,
                        xs[0], ys[0],
                        xs[1], ys[1],
                        xs[2], ys[2],
                        "", esp, cor
                );
                break;
            default:
                throw new IllegalStateException("Tipo nao desenhavel: " + tipo);
        }
    }
}
