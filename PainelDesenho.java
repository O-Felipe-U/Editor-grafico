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
    int esp;              // Diametro do ponto



    // Para ponto
    int x, y;

    // Para reta / circulo / retangulo / triangulo (todos usam 2 pontos: clique inicial e posicao atual do arrasto)
    int x1, y1, x2, y2;

    // Lista (EDL) com todas as figuras JA CONCLUIDAS e atualmente desenhadas na tela.
    // Cada primitivo concluido (ponto, ou o "soltar" do mouse de reta/circulo/
    // retangulo/triangulo) vira um FiguraDesenhada guardado aqui, e o
    // paintComponent percorre essa lista para redesenhar tudo.
    private EDL<FiguraDesenhada> desenhosAtuais = new EDL<>();

    // Lista (EDL) que guarda o "retrato" dos desenhos no momento em que o
    // botao "Limpar" foi clicado, para que o botao "Redesenhar" possa
    // trazer essas figuras de volta para a tela.
    private EDL<FiguraDesenhada> desenhosSalvos = new EDL<>();

    // Figura "em andamento": a previa que aparece enquanto o mouse esta
    // sendo arrastado (do 1o clique ate o momento atual do arrasto).
    // Fica FORA da lista desenhosAtuais - so entra na lista quando o
    // mouse e solto. Enquanto for null, nao ha arrasto em curso.
    private FiguraDesenhada figuraEmAndamento = null;


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
     * Limpa o fundo do painel (super.paintComponent), redesenha TODAS as
     * figuras ja concluidas guardadas em desenhosAtuais, e por cima delas
     * desenha a figuraEmAndamento (a previa do arrasto atual, se houver).
     * Como isso acontece a cada repaint(), a previa "acompanha" o mouse
     * sem deixar rastro e sem apagar o que ja estava desenhado.
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // limpa o fundo do painel antes de redesenhar
        for (int i = 0; i < desenhosAtuais.tamanho(); i++) {
            desenharFigura(g, desenhosAtuais.obter(i));
        }

        // desenha por cima a figura que ainda esta sendo arrastada (previa)
        if (figuraEmAndamento != null) {
            desenharFigura(g, figuraEmAndamento);
        }
    }

    
    /**
     * Evento: pressionar do mouse.
     *
     * PONTO e concluido de imediato (nao precisa de arrasto).
     * Para as demais formas, este e o "1o clique": guarda (x1,y1) e
     * comeca a previa (figuraEmAndamento) com x2=y2=x1,y1 - ela sera
     * atualizada a cada mouseDragged.
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
            // Reta, Circulo, Retangulo e Triangulo sao construidos por
            // arrasto: o clique inicial define (x1,y1)
            x1 = e.getX();
            y1 = e.getY();
            x2 = x1;
            y2 = y1;

            // cria a previa (ainda nao entra em desenhosAtuais)
            figuraEmAndamento = new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual());
            repaint();
        }
    }     

    /**
     * Evento: soltar o mouse.
     *
     * Finaliza a figura em andamento (usando a posicao final do mouse) e
     * SO ENTAO ela entra em desenhosAtuais, permanentemente, junto com
     * tudo que ja tinha sido desenhado antes.
     *
     * @param e dados do evento
     */
    public void mouseReleased(MouseEvent e) { 
        if (figuraEmAndamento != null) {
            x2 = e.getX();
            y2 = e.getY();

            // agora sim: a figura concluida entra na lista definitiva
            desenhosAtuais.inserir(new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual()));

            // limpa a previa - o arrasto acabou
            figuraEmAndamento = null;
            repaint();
        }
    }           

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Evento: arrastar o mouse (botao pressionado + movimento).
     *
     * Apenas ATUALIZA a previa (figuraEmAndamento) com a posicao atual do
     * mouse e manda repintar. Como a previa nao esta em desenhosAtuais,
     * nada e "empilhado" na lista - so o desenho final, em mouseReleased.
     *
     * @param e dados do evento
     */
    public void mouseDragged(MouseEvent e) {
        if (figuraEmAndamento != null) {
            x2 = e.getX();
            y2 = e.getY();
            figuraEmAndamento = new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual());
            repaint();
        }
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
                // 1o clique = centro; posicao do arrasto/2o clique = ponto na borda (define o raio)
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
        figuraEmAndamento = null;                 // cancela qualquer arrasto pela metade
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
