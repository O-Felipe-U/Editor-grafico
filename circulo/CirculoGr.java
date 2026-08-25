package circulo;

import java.awt.Color;
import java.awt.Graphics;

import ponto.Ponto;
import ponto.PontoGr;

/**
 * Implementacao da classe circulo grafico.
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class CirculoGr extends Circulo {

    Color corCirculo = Color.BLACK;   // cor do circulo
    String nomeCirculo = "";          // nome do circulo
    Color corNomeCirculo = Color.BLACK;
    int espCirculo = 1;               // espessura (diametro do "pincel")

    /**
     * Constroi um circulo grafico a partir do centro e de um ponto na borda
     *
     * @param centro ponto central do circulo
     * @param borda ponto sobre a borda (define o raio)
     * @param cor cor do circulo
     * @param nome nome do circulo
     * @param esp espessura do traco
     */
    public CirculoGr(Ponto centro, Ponto borda, Color cor, String nome, int esp) {
        super(centro, borda);
        setCorCirculo(cor);
        setNomeCirculo(nome);
        setEspCirculo(esp);
    }

    /**
     * Constroi um circulo grafico a partir do centro e de um ponto na borda
     *
     * @param centro ponto central do circulo
     * @param borda ponto sobre a borda (define o raio)
     * @param cor cor do circulo
     */
    public CirculoGr(Ponto centro, Ponto borda, Color cor) {
        super(centro, borda);
        setCorCirculo(cor);
        setNomeCirculo("");
    }

    /**
     * Altera a cor do circulo
     *
     * @param cor nova cor
     */
    public void setCorCirculo(Color cor) {
        this.corCirculo = cor;
    }

    /**
     * Retorna a cor do circulo
     *
     * @return cor do circulo
     */
    public Color getCorCirculo() {
        return this.corCirculo;
    }

    /**
     * Altera o nome do circulo
     *
     * @param nome novo nome
     */
    public void setNomeCirculo(String nome) {
        this.nomeCirculo = nome;
    }

    /**
     * Retorna o nome do circulo
     *
     * @return nome do circulo
     */
    public String getNomeCirculo() {
        return this.nomeCirculo;
    }

    /**
     * Altera a espessura do traco do circulo
     *
     * @param esp nova espessura
     */
    public void setEspCirculo(int esp) {
        this.espCirculo = esp;
    }

    /**
     * Retorna a espessura do traco do circulo
     *
     * @return espessura do traco
     */
    public int getEspCirculo() {
        return this.espCirculo;
    }

    /**
     * Retorna a cor do nome do circulo
     *
     * @return cor do nome
     */
    public Color getCorNomeCirculo() {
        return this.corNomeCirculo;
    }

    /**
     * Altera a cor do nome do circulo
     *
     * @param cor nova cor do nome
     */
    public void setCorNomeCirculo(Color cor) {
        this.corNomeCirculo = cor;
    }

    /**
     * Desenha o circulo grafico usando o ALGORITMO DO PONTO MEDIO PARA
     * CIRCULOS (Midpoint Circle Algorithm - a versao "circular" do mesmo
     * algoritmo do ponto medio usado em RetaGr para retas).
     *
     * Ideia geral: em vez de calcular x = raio*cos(t), y = raio*sin(t) para
     * varios angulos (o que usa numeros "double" e funcoes trigonometricas
     * caras), o algoritmo aproveita a SIMETRIA do circulo: cada ponto
     * calculado em UM oitavo (1/8) do circulo pode ser espelhado para
     * gerar automaticamente os outros 7 pontos simetricos. Assim, so
     * precisamos calcular os pontos de x=0 (topo) ate x=y (a diagonal a
     * 45 graus), usando apenas somas e comparacoes de inteiros.
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharCirculo(Graphics g) {

        // desenha o nome do circulo ao lado do centro
        g.setColor(getCorNomeCirculo());
        g.drawString(getNomeCirculo(), (int) getCentro().getX() + getEspCirculo(), (int) getCentro().getY());

        // coordenadas inteiras do centro e o raio (arredondado)
        int xc = (int) getCentro().getX();
        int yc = (int) getCentro().getY();
        int r = (int) Math.round(getRaio());

        // ---------- ALGORITMO DO PONTO MEDIO PARA CIRCULOS ----------

        // comeca no topo do circulo: x=0, y=raio
        int x = 0;
        int y = r;

        // "p" e o parametro de decisao (equivalente ao "erro" da reta):
        // indica se o ponto medio entre os dois pixels candidatos esta
        // dentro ou fora do circulo real. Valor inicial classico: 1 - r
        int p = 1 - r;

        // desenha o primeiro conjunto de 8 pontos simetricos (x=0)
        desenharOitoSimetricos(g, xc, yc, x, y);

        // percorre somente 1/8 do circulo (enquanto x for menor que y);
        // os outros 7/8 sao obtidos por simetria dentro do metodo abaixo
        while (x < y) {
            x++; // sempre avanca uma casa no eixo x

            if (p < 0) {
                // ponto medio esta DENTRO do circulo -> o proximo pixel
                // ainda esta "perto", escolhemos o pixel do LESTE (E),
                // sem descer no eixo y
                p = p + 2 * x + 1;
            } else {
                // ponto medio esta FORA do circulo -> precisamos nos
                // aproximar do centro, escolhemos o pixel do SUDESTE (SE),
                // descendo tambem no eixo y
                y--;
                p = p + 2 * (x - y) + 1;
            }

            // a cada novo (x, y) calculado, espelha para os 8 octantes
            desenharOitoSimetricos(g, xc, yc, x, y);
        }
    }

    /**
     * Desenha os 8 pontos simetricos de um circulo a partir de um unico
     * ponto (x, y) calculado no primeiro oitavo do circulo.
     *
     * @param g contexto grafico
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param x deslocamento x (em relacao ao centro) calculado pelo algoritmo
     * @param y deslocamento y (em relacao ao centro) calculado pelo algoritmo
     */
    private void desenharOitoSimetricos(Graphics g, int xc, int yc, int x, int y) {
        plotar(g, xc + x, yc + y);
        plotar(g, xc - x, yc + y);
        plotar(g, xc + x, yc - y);
        plotar(g, xc - x, yc - y);
        plotar(g, xc + y, yc + x);
        plotar(g, xc - y, yc + x);
        plotar(g, xc + y, yc - x);
        plotar(g, xc - y, yc - x);
    }

    /**
     * Desenha um unico pixel/ponto do circulo na tela
     *
     * @param g contexto grafico
     * @param x coordenada x do pixel
     * @param y coordenada y do pixel
     */
    private void plotar(Graphics g, int x, int y) {
        PontoGr ponto = new PontoGr(x, y, getCorCirculo(), getEspCirculo());
        ponto.desenharPonto(g);
    }
}
