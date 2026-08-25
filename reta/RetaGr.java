package reta;
import java.awt.Color;
import java.awt.Graphics;

import ponto.PontoGr;

/**
 * Implementacao da classe reta grafica.
 *
 * @author Julio Arakaki
 * @version 1.0 - 24/08/2020
 */
public class RetaGr extends Reta{
    // Atributos da reta grafica
    Color corReta = Color.BLACK;   // cor da reta
    String nomeReta = ""; // nome da reta
    Color corNomeReta  = Color.BLACK;
    int espReta = 1; // espessura da reta

    // Construtores
    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da reta
     * @param nome String. Nome da reta
     * @param esp int. Espessura da reta
     */
    public RetaGr(int x1, int y1, int x2, int y2, Color cor, String nome, int esp){
        super (x1, y1, x2, y2);
        setCorReta(cor);
        setNomeReta(nome);
        setEspReta(esp);
    }    

    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da reta
     */
    public RetaGr(int x1, int y1, int x2, int y2, Color cor){
        super (x1, y1, x2, y2);
        setCorReta(cor);
        setNomeReta("");
    }   

    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     * @param cor Color. Cor da reta
     * @param esp int. Espessura da reta
     */
    public RetaGr(int x1, int y1, int x2, int y2, Color cor, int esp){
        super (x1, y1, x2, y2);
        setCorReta(cor);
        setNomeReta("");
        setEspReta(esp);
    }   

    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param x1 int. Coordenada x1
     * @param y1 int. Coordenada y1
     * @param x2 int. Coordenada x2
     * @param y2 int. Coordenada y2
     */
    public RetaGr(int x1, int y1, int x2, int y2){
        super (x1, y1, x2, y2);
        setCorReta(Color.black);
        setNomeReta("");
    }   

    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     */
    public RetaGr(PontoGr p1, PontoGr p2){
        super(p1, p2);
        setCorReta(Color.black);
        setNomeReta("");
    }    

    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     * @param cor Color. Cor da reta
     */
    public RetaGr(PontoGr p1, PontoGr p2, Color cor){
        super(p1, p2);
        setCorReta(cor);
        setNomeReta("");
    }    

    /**
     * RetaGr - Constroi uma reta grafica
     *
     * @param p1 PontoGr. Ponto grafico p1 (x1, y1)
     * @param p2 PontoGr. Ponto grafico p2 (x2, y2)
     * @param cor Color. Cor da reta
     * @param nome String. Nome da reta
     */
    public RetaGr(PontoGr p1, PontoGr p2, Color cor, String str){
        super(p1, p2);
        setCorReta(cor);
        setNomeReta(str);
    }    

    /**
     * Altera a cor da reta.
     *
     * @param cor Color. Cor da reta.
     */
    public void setCorReta(Color cor) {
        this.corReta = cor;
    }

    /**
     * Altera o nome da reta.
     *
     * @param str String. Nome da reta.
     */
    public void setNomeReta(String str) {
        this.nomeReta = str;
    }

    /**
     * Altera a espessura da reta.
     *
     * @param esp int. Espessura da reta.
     */
    public void setEspReta(int esp) {
        this.espReta = esp;
    }

    /**
     * Retorna a espessura da reta.
     *
     * @return int. Espessura da reta.
     */
    public int getEspReta() {
        return(this.espReta);
    }

    /**
     * Retorna a cor da reta.
     *
     * @return Color. Cor da reta.
     */
    public Color getCorReta() {
        return corReta;
    }

    /**
     * Retorna o nome da reta.
     *
     * @return String. Nome da reta.
     */
    public String getNomeReta() {
        return nomeReta;
    }

    /**
     * @return the corNomeReta
     */
    public Color getCorNomeReta() {
        return corNomeReta;
    }

    /**
     * @param corNomeReta the corNomeReta to set
     */
    public void setCorNomeReta(Color corNomeReta) {
        this.corNomeReta = corNomeReta;
    }

    /**
     * Desenha a reta grafica usando o ALGORITMO DO PONTO MEDIO (Midpoint Line
     * Algorithm, tambem conhecido como algoritmo de Bresenham).
     *
     * Ideia geral do algoritmo:
     * Em vez de calcular y = m*x + b para cada x (o que exige numeros
     * "double" e arredondamentos), o algoritmo do ponto medio decide, usando
     * somente numeros inteiros, se o proximo pixel deve "andar" apenas no
     * eixo dominante (o eixo que varia mais) ou se deve andar tambem no
     * outro eixo. Essa decisao e feita comparando a posicao do "ponto
     * medio" entre os dois pixels candidatos com a reta real: se o ponto
     * medio estiver de um lado da reta, escolhe-se um pixel; se estiver do
     * outro lado, escolhe-se o outro.
     *
     * A versao abaixo trata TODOS os casos (retas horizontais, verticais e
     * com qualquer inclinacao, "subindo" ou "descendo") de uma unica vez,
     * usando as variaveis sx/sy (sinal do passo) e trocando dx/dy conforme
     * qual eixo varia mais - por isso funciona em qualquer um dos 8
     * octantes.
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharReta(Graphics g){

        // desenha o nome da reta ao lado do ponto inicial (p1)
        g.setColor(getCorNomeReta());
        g.drawString(getNomeReta(), (int)getP1().getX() + getEspReta(), (int)getP1().getY());

        // coordenadas inteiras dos pontos inicial (1) e final (2) da reta
        int x1 = (int) getP1().getX();
        int y1 = (int) getP1().getY();
        int x2 = (int) getP2().getX();
        int y2 = (int) getP2().getY();

        // ---------- ALGORITMO DO PONTO MEDIO (MIDPOINT / BRESENHAM) ----------

        // dx e dy = quanto a reta percorre em x e em y (sempre positivo,
        // pois so nos interessa a "distancia" percorrida em cada eixo)
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        // sx e sy = sentido (direcao) do passo em cada eixo: +1 se estamos
        // andando para a direita/baixo, -1 se estamos andando para a
        // esquerda/cima. Isso permite desenhar em qualquer direcao, nao so
        // da esquerda para a direita.
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        // "erro" representa a posicao do ponto medio em relacao a reta real.
        // Comeca com (dx - dy): se dx > dy a reta e "mais horizontal" (o
        // eixo x domina), se dy > dx a reta e "mais vertical" (o eixo y
        // domina).
        int erro = dx - dy;

        // posicao atual do pixel sendo desenhado, comeca no ponto inicial
        int x = x1;
        int y = y1;

        while (true) {
            // desenha o pixel atual como um PontoGr (respeita cor e
            // espessura da reta, igual a um "pincel" percorrendo o caminho)
            PontoGr ponto = new PontoGr(x, y, getCorReta(), getEspReta());
            ponto.desenharPonto(g);

            // condicao de parada: chegamos exatamente no ponto final
            if (x == x2 && y == y2) {
                break;
            }

            // "erro2" = 2 * erro, apenas para comparar sem usar fracoes
            // (assim o algoritmo trabalha somente com numeros inteiros)
            int erro2 = 2 * erro;

            // se o ponto medio esta "acima/abaixo" o suficiente do eixo
            // dominante, avancamos um passo em x
            if (erro2 > -dy) {
                erro -= dy;
                x += sx;
            }

            // se o ponto medio tambem exige avancar no outro eixo,
            // avancamos um passo em y
            if (erro2 < dx) {
                erro += dx;
                y += sy;
            }
        }
    }
}
