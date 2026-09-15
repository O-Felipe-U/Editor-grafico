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

    // distancia minima (em pixels) que o mouse precisa se afastar do
    // ponto inicial para contar como "arrasto de verdade". Sem isso, o
    // tremor natural da mao entre pressionar e soltar (1-2 pixels) ja
    // fazia todo clique ser tratado como arrasto.
    private static final int LIMIAR_ARRASTO = 4;

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

    // Figura "em andamento": a previa que aparece enquanto o usuario esta
    // definindo a forma (seja arrastando o mouse, seja no intervalo entre
    // o 1o e o 2o clique). Fica FORA da lista desenhosAtuais - so entra na
    // lista quando a figura e finalizada. Enquanto for null, nao ha nada
    // em andamento.
    private FiguraDesenhada figuraEmAndamento = null;

    // true assim que o mouse se MOVE com o botao pressionado (prova de
    // que o usuario esta arrastando, e nao apenas clicando)
    private boolean houveArrasto = false;

    // true quando o usuario soltou o mouse SEM arrastar (so um clique) e
    // o painel esta esperando o 2o clique para fechar a figura - modo
    // "clique-clique" (estilo ferramentas antigas de desenho)
    private boolean aguardandoSegundoClique = false;


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
     * PONTO e concluido de imediato (nao precisa de arrasto nem 2o clique).
     *
     * Para as demais formas, este metodo decide entre dois casos:
     * - Se aguardandoSegundoClique == true: este e o "2o clique" do modo
     *   clique-clique (usuario tinha clicado e solto sem arrastar, e
     *   moveu o mouse ate aqui). FECHA a figura agora.
     * - Caso contrario: este e um clique NOVO (1o clique de uma figura).
     *   Guarda (x1,y1), zera houveArrasto e comeca a previa.
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

            if (aguardandoSegundoClique) {
                // este clique FECHA a figura do modo clique-clique
                x2 = e.getX();
                y2 = e.getY();
                desenhosAtuais.inserir(new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual()));

                figuraEmAndamento = null;
                aguardandoSegundoClique = false;
                repaint();
            } else {
                // este clique COMECA uma figura nova (1o clique)
                x1 = e.getX();
                y1 = e.getY();
                x2 = x1;
                y2 = y1;
                houveArrasto = false;

                // cria a previa (ainda nao entra em desenhosAtuais)
                figuraEmAndamento = new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual());
                repaint();
            }
        }
    }     

    /**
     * Evento: soltar o mouse.
     *
     * So faz algo se houver uma figura em andamento E nao estivermos ja
     * esperando o 2o clique (ou seja, so reage ao soltar do PRIMEIRO
     * clique).
     *
     * - Se houveArrasto == true: o usuario arrastou de verdade -> finaliza
     *   a figura agora mesmo, igual ao comportamento "estilo Paint".
     * - Se houveArrasto == false: foi so um clique seco (sem mover) ->
     *   NAO finaliza ainda. Liga aguardandoSegundoClique e deixa a previa
     *   viva; ela vai seguir o mouse (via mouseMoved) ate o 2o clique.
     *
     * @param e dados do evento
     */
    public void mouseReleased(MouseEvent e) { 
        if (figuraEmAndamento != null && !aguardandoSegundoClique) {
            if (houveArrasto) {
                // arrasto completo: finaliza agora
                x2 = e.getX();
                y2 = e.getY();
                desenhosAtuais.inserir(new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual()));

                figuraEmAndamento = null;
                repaint();
            } else {
                // clique seco: entra no modo clique-clique, esperando o 2o clique
                aguardandoSegundoClique = true;
            }
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
     * So age se houver figura em andamento e NAO estivermos no modo
     * clique-clique (esse modo e controlado por mouseMoved, nao por
     * mouseDragged). So marca houveArrasto = true quando o mouse ja se
     * afastou mais que LIMIAR_ARRASTO pixels do ponto inicial - isso
     * evita que o tremor natural da mao (1-2 pixels) seja confundido com
     * um arrasto de verdade, o que impediria o modo clique-clique de
     * funcionar. A previa, porem, e sempre atualizada, arrasto "de
     * verdade" ou nao.
     *
     * @param e dados do evento
     */
    public void mouseDragged(MouseEvent e) {
        if (figuraEmAndamento != null && !aguardandoSegundoClique) {
            x2 = e.getX();
            y2 = e.getY();

            if (Math.abs(x2 - x1) > LIMIAR_ARRASTO || Math.abs(y2 - y1) > LIMIAR_ARRASTO) {
                houveArrasto = true;
            }

            figuraEmAndamento = new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual());
            repaint();
        }
    }

    /**
     * Evento mouseMoved: escreve mensagem no rodape (x, y) do mouse e,
     * se estivermos no modo clique-clique (aguardando o 2o clique), faz a
     * previa "seguir" o cursor ate o usuario clicar de novo.
     *
     * @param e dados do evento do mouse
     */
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("("+e.getX() + ", " + e.getY() + ") - " + getTipo());

        if (aguardandoSegundoClique && figuraEmAndamento != null) {
            x2 = e.getX();
            y2 = e.getY();
            figuraEmAndamento = new FiguraDesenhada(tipo, x1, y1, x2, y2, "", getEsp(), getCorAtual());
            repaint();
        }
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
        figuraEmAndamento = null;                 // cancela qualquer figura pela metade
        aguardandoSegundoClique = false;          // cancela o modo clique-clique, se estava ativo
        houveArrasto = false;
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