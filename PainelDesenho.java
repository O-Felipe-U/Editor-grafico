import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ponto.FiguraPontos;
import ponto.Ponto;
import reta.FiguraRetas;
import circulo.FiguraCirculos;
import retangulo.FiguraRetangulos;
import triangulo.FiguraTriangulos;

/**
 * Cria desenhos de acordo com o tipo e eventos do mouse
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    JLabel msg;           // Label para mensagens
    TipoPrimitivo tipo; // Tipo do primitivo
    Color corAtual;       // Cor atual do primitivo
    int esp;         // Diametro do ponto
          

    // Para ponto
    int x, y;

    // Para reta / circulo / retangulo / triangulo (todos usam 2 pontos: 1o e 2o clique)
    int x1, y1, x2, y2;

    // selecionar primeiro click do mouse
    boolean primeiraVez = true;

    // Lista (EDL) com todas as figuras atualmente desenhadas na tela.
    // Cada primitivo concluido (ponto, ou 2o clique de reta/circulo/
    // retangulo/triangulo) vira um FiguraDesenhada guardado aqui, e o
    // paintComponent percorre essa lista para redesenhar tudo.
    private EDL<FiguraDesenhada> desenhosAtuais = new EDL<>();

    // Lista (EDL) que guarda o "retrato" dos desenhos no momento em que o
    // botao "Limpar" foi clicado, para que o botao "Redesenhar" possa
    // trazer essas figuras de volta para a tela.
    private EDL<FiguraDesenhada> desenhosSalvos = new EDL<>();


    /**
     * Constroi o painel de desenho
     *
     * @param msg mensagem a ser escrita no rodape do painel
     * @param tipo tipo atual do primitivo
     * @param corAtual cor atual do primitivo
     * @param esp espessura atual do primitivo
     */
    public PainelDesenho(JLabel msg, TipoPrimitivo tipo, Color corAtual, int esp){
        setTipo(tipo);
        setMsg(msg);
        setCorAtual(corAtual);
        setEsp(esp);

        // Adiciona "ouvidor" de eventos de mouse
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);

    }

    /**
     * Altera o tipo atual do primitivo
     *
     * @param tipo tipo do primitivo
     */
    public void setTipo(TipoPrimitivo tipo){
        this.tipo = tipo;
    }

    /**
     * Retorna o tipo do primitivo
     *
     * @return tipo do primitivo
     */
    public TipoPrimitivo getTipo(){
        return this.tipo;
    }

    /**
     * Altera a espessura do primitivo
     *
     * @param esp espessura do primitivo
     */
    public void setEsp(int esp){
        this.esp = esp;
    }

    /**
     * Retorna a espessura do primitivo
     *
     * @return espessura do primitivo
     */
    public int getEsp(){
        return this.esp;
    }

    /**
     * Altera a cor atual do primitivo
     *
     * @param corAtual cor atual do primitivo
     */
    public void setCorAtual(Color corAtual){
        this.corAtual = corAtual;
    }

    /**
     * retorna a cor atual do primitivo
     *
     * @return cor atual do primitivo
     */
    public Color getCorAtual(){
        return this.corAtual;
    }

    /**
     * Altera a msg a ser apresentada no rodape
     *
     * @param msg mensagem a ser apresentada
     */
    public void setMsg(JLabel msg){
        this.msg = msg;
    }

    /**
     * Retorna a mensagem
     *
     * @return mensagem as ser apresentada no rodape
     */
    public JLabel getMsg(){
        return this.msg;
    }

    /**
     * Metodo chamado quando o paint eh acionado.
     *
     * Limpa o fundo do painel (super.paintComponent) e redesenha TODAS as
     * figuras guardadas em desenhosAtuais - e assim que o "Redesenhar"
     * consegue trazer varias figuras de volta de uma so vez.
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // limpa o fundo do painel antes de redesenhar
        for (int i = 0; i < desenhosAtuais.tamanho(); i++) {
            desenharFigura(g, desenhosAtuais.obter(i));
        }
    }

    
    /**
     * Evento: pressionar do mouse
     *
     * @param e dados do evento
     */
    public void mousePressed(MouseEvent e) { 
        if (tipo == TipoPrimitivo.PONTO){

            x = e.getX();
            y = e.getY();

            // guarda a figura concluida na lista e manda redesenhar tudo
            desenhosAtuais.inserir(new FiguraDesenhada(TipoPrimitivo.PONTO, x, y, 0, 0, "", getEsp(), getCorAtual()));
            repaint();
        } else if (tipo == TipoPrimitivo.RETA
                || tipo == TipoPrimitivo.CIRCULO
                || tipo == TipoPrimitivo.RETANGULO
                || tipo == TipoPrimitivo.TRIANGULO){
            // Reta, Circulo, Retangulo e Triangulo sao todos construidos
            // a partir de 2 pontos (1o e 2o clique do mouse)
            if (primeiraVez == true) {
                x1 = (int)e.getX();
                y1 = (int)e.getY();
                primeiraVez = false;
            } else {
                x2 = (int)e.getX();
                y2 = (int)e.getY();
                primeiraVez = true;

            }
        }
    }     

    public void mouseReleased(MouseEvent e) {
        System.out.println("RELEASED\n\n");
        
        
        // guarda a figura concluida na lista e manda redesenhar tudo
        desenhosAtuais.inserir(new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual()));
        repaint();

    }           

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        //tenho que guardar a posição anterior para desenhar e apagar.
        
        System.out.println("DRAGGED\n");        
        
        x2 = (int)e.getX();
        y2 = (int)e.getY();
        desenhosAtuais.inserir(new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual()));
        repaint();
        
        
    }

    /**
     * Evento mouseMoved: escreve mensagem no rodape (x, y) do mouse
     *
     * @param e dados do evento do mouse
     */
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getTipo());
    }

    /**
     * Desenha uma unica figura (registro FiguraDesenhada) chamando a
     * classe "Figura*" correta de acordo com o tipo do primitivo.
     *
     * @param g biblioteca para desenhar em modo grafico
     * @param f figura (com todos os dados) a ser desenhada
     */
    private void desenharFigura(Graphics g, FiguraDesenhada f){
        switch (f.getTipo()) {
            case PONTO:
                FiguraPontos.desenharPonto(g, f.getX1(), f.getY1(), f.getNome(), f.getEsp(), f.getCor());
                break;

            case RETA:
                FiguraRetas.desenharReta(g, f.getX1(), f.getY1(), f.getX2(), f.getY2(), f.getNome(), f.getEsp(), f.getCor());
                break;

            case CIRCULO:
                // 1o clique = centro; 2o clique = ponto na borda (define o raio)
                FiguraCirculos.desenharCirculo(g, new Ponto(f.getX1(), f.getY1()), new Ponto(f.getX2(), f.getY2()), f.getNome(), f.getEsp(), f.getCor());
                break;

            case RETANGULO:
                // (x1,y1) e (x2,y2) sao dois cantos opostos do retangulo
                FiguraRetangulos.desenharRetangulo(g, new Ponto(f.getX1(), f.getY1()), new Ponto(f.getX2(), f.getY2()), f.getNome(), f.getEsp(), f.getCor());
                break;

            case TRIANGULO:
                // (x1,y1) e (x2,y2) definem o retangulo envolvente do triangulo
                FiguraTriangulos.desenharTriangulo(g, new Ponto(f.getX1(), f.getY1()), new Ponto(f.getX2(), f.getY2()), f.getNome(), f.getEsp(), f.getCor());
                break;

            default:
                break;
        }
    }

    /**
     * Chamado pelo botao "Limpar": guarda uma copia dos desenhos atuais na
     * EDL desenhosSalvos (para permitir "Redesenhar" depois) e em seguida
     * esvazia a tela.
     */
    public void limparTela(){
        desenhosSalvos = desenhosAtuais.copiar(); // guarda o "retrato" atual
        desenhosAtuais.limpar();                  // esvazia o que esta na tela
        primeiraVez = true;                       // cancela qualquer figura pela metade
        repaint();
    }

    /**
     * Chamado pelo botao "Redesenhar": recupera as figuras guardadas em
     * desenhosSalvos (no ultimo "Limpar") e as traz de volta para a tela.
     */
    public void redesenhar(){
        if (!desenhosSalvos.estaVazia()){
            desenhosAtuais = desenhosSalvos.copiar();
            repaint();
        }
    }
}
