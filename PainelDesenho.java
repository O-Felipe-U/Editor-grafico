import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cria desenhos de acordo com o tipo e eventos do mouse.
 *
 * Todos os primitivos sao construidos a partir dos pontos clicados
 * na tela: o PONTO precisa de 1 clique; os demais (Reta, Circulo,
 * Retangulo, Triangulo) precisam de 2 cliques (ponto1 e ponto2).
 * Cada figura concluida e guardada na lista "figuras", o que permite
 * que varias figuras fiquem acumuladas corretamente na tela.
 * 
 * @author Julio Arakaki 
 * @version 20260823
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    private JLabel msg;           // Label para mensagens
    private TipoPrimitivo tipo;   // Tipo do primitivo selecionado
    private Color corAtual;       // Cor atual do primitivo
    private int esp;              // Espessura atual do primitivo

    // Todas as figuras ja concluidas e desenhadas no painel
    private List<FiguraDesenhada> figuras = new ArrayList<>();

    // Guarda o 1o ponto clicado, aguardando o 2o (null = nenhum clique pendente)
    private Integer x1, y1;

    /**
     * Constroi o painel de desenho
     *
     * @param msg mensagem a ser escrita no rodape do painel
     * @param tipo tipo atual do primitivo
     * @param corAtual cor atual do primitivo
     * @param esp espessura atual do primitivo
     */
    public PainelDesenho(JLabel msg, TipoPrimitivo tipo, Color corAtual, int esp) {
        setTipo(tipo);
        setMsg(msg);
        setCorAtual(corAtual);
        setEsp(esp);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setTipo(TipoPrimitivo tipo) {
        this.tipo = tipo;
        cancelarSelecao(); // troca de tipo cancela um 1o clique pendente
    }

    public TipoPrimitivo getTipo() {
        return this.tipo;
    }

    public void setEsp(int esp) {
        this.esp = esp;
    }

    public int getEsp() {
        return this.esp;
    }

    public void setCorAtual(Color corAtual) {
        this.corAtual = corAtual;
    }

    public Color getCorAtual() {
        return this.corAtual;
    }

    public void setMsg(JLabel msg) {
        this.msg = msg;
    }

    public JLabel getMsg() {
        return this.msg;
    }

    /**
     * Remove todas as figuras ja desenhadas e limpa o painel.
     */
    public void limpar() {
        figuras.clear();
        cancelarSelecao();
        repaint();
    }

    private void cancelarSelecao() {
        x1 = null;
        y1 = null;
    }

    /**
     * Repinta todas as figuras acumuladas na lista.
     *
     * @param g biblioteca para desenhar em modo grafico
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (FiguraDesenhada f : figuras) {
            f.desenhar(g);
        }
    }

    /**
     * Evento: pressionar do mouse. PONTO conclui com 1 clique;
     * os demais tipos precisam de um 1o e um 2o clique.
     *
     * @param e dados do evento
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (tipo == null || tipo == TipoPrimitivo.NENHUM) {
            return;
        }

        if (tipo == TipoPrimitivo.PONTO) {
            figuras.add(new FiguraDesenhada(tipo, e.getX(), e.getY(), e.getX(), e.getY(), corAtual, esp));
            repaint();
            return;
        }

        if (x1 == null) {
            // 1o clique: apenas guarda o ponto, aguarda o 2o
            x1 = e.getX();
            y1 = e.getY();
        } else {
            // 2o clique: figura completa, adiciona a lista
            figuras.add(new FiguraDesenhada(tipo, x1, y1, e.getX(), e.getY(), corAtual, esp));
            cancelarSelecao();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Evento mouseMoved: escreve mensagem no rodape (x, y) do mouse,
     * indicando tambem se ha um 1o clique pendente aguardando o 2o.
     *
     * @param e dados do evento do mouse
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        String aguardando = (x1 != null) ? " - aguardando 2\u00ba ponto..." : "";
        this.msg.setText("(" + e.getX() + ", " + e.getY() + ") - " + getTipo() + aguardando);
    }
}
