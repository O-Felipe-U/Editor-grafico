package reta;
import java.awt.Color;
import java.awt.Graphics;
import ponto.PontoGr;

/**
 * Escreva uma descrição da classe RetaGr aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class RetaGr extends Reta
{
    // Atributos da reta grafica
    Color corReta = Color.BLACK;   // cor da reta
    String nomeReta = ""; // nome da reta
    Color corNomeReta  = Color.BLACK;
    int espReta = 1; // espessura da reta
    
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
    public RetaGr(int x1, int y1, int x2, int y2, Color cor,String nome, int esp){
        super (x1, y1, x2, y2);
        setCorReta(cor);
        setNomeReta(nome);
        setEspReta(esp);
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
     * Desenha reta grafica utilizando a equacao da reta: y = mx + b
     *
     * @param g Graphics. Classe com os metodos graficos do Java - FALTA ALTERAR PARA ALGORITMO MIDPOINT
     */
    public void desenharReta(Graphics g){

        // calcula m e b da equacao da reta y = mx + b

        // Variaveis auxiliares
        PontoGr ponto; 
        double x, y;

        double cIni, cFim;

        // desenha nome do ponto
        g.setColor(getCorNomeReta());
        g.drawString(getNomeReta(), (int)getP1().getX() + getEspReta(), (int)getP1().getY());

        if(p1.getX() == p2.getX()) { // reta vertical
            if (p1.getY() < p2.getY()){ // Caso 1: y1 < y2
                cIni = p1.getY();
                cFim = p2.getY();
            } else { // Caso 2: y1 > y2
                cIni = p2.getY();
                cFim = p1.getY();            
            }
            // percorre de y1 ate y2. 
            for(y = cIni; y <= cFim; y++){ 
                // x1 e x2 são iguais
                x = p1.getX(); // ou x = p2.getX()

                // Define ponto grafico
                ponto = new PontoGr((int)x, (int)y, getCorReta(), getEspReta());

                // Desenha ponto grafico
                ponto.desenharPonto(g);
            }

        } else { // outras retas
                
            double m = calcularM();
            double b = calcularB();


        if (p1.getX() < p2.getX()){ // Caso 1: x1 < x2
            cIni = p1.getX();
            cFim = p2.getX();
        } else { // Caso 2: x1 > x2
            cIni = p2.getX();
            cFim = p1.getX();            
        }
        
        // percorre de x1 ate x2. 
        // y e´ calculado pela equacao: y = mx + b
        for(x = cIni; x <= cFim; x++){ 
            // Calculo de y pela equacao da reta
            y = (m*x + b);
        
            // Define ponto grafico
            ponto = new PontoGr((int)x, (int)y, getCorReta(), getEspReta());
        
            // Desenha ponto grafico
            ponto.desenharPonto(g);
        }
        }
    }
    
}