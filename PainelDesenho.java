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
 * Painel responsavel por receber os cliques do usuario, criar os primitivos,
 * armazena-los na ED e desenhar somente os elementos atualmente visiveis.
 */
@SuppressWarnings("serial")
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {
    private JLabel msg;
    private TipoPrimitivo tipo;
    private Color corAtual;
    private int esp;

    /** Estrutura de dados permanente do editor. */
    private final RepositorioPrimitivos repositorio = new RepositorioPrimitivos();

    /**
     * Lista apenas de exibicao. O botao Limpar esvazia esta lista, mas nunca a ED.
     */
    private final List<PrimitivoArmazenado> visiveis = new ArrayList<>();

    /** Pontos temporarios do primitivo que ainda esta sendo construido. */
    private final List<Integer> xsPendentes = new ArrayList<>();
    private final List<Integer> ysPendentes = new ArrayList<>();

    public PainelDesenho(JLabel msg, TipoPrimitivo tipo, Color corAtual, int esp) {
        setBackground(Color.WHITE);
        setTipo(tipo);
        setMsg(msg);
        setCorAtual(corAtual);
        setEsp(esp);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setTipo(TipoPrimitivo tipo) {
        this.tipo = tipo == null ? TipoPrimitivo.NENHUM : tipo;
        cancelarSelecao();
        atualizarMensagem("Ferramenta: " + this.tipo);
    }

    public TipoPrimitivo getTipo() {
        return tipo;
    }

    public void setEsp(int esp) {
        this.esp = Math.max(1, esp);
    }

    public int getEsp() {
        return esp;
    }

    public void setCorAtual(Color corAtual) {
        this.corAtual = corAtual == null ? Color.BLACK : corAtual;
    }

    public Color getCorAtual() {
        return corAtual;
    }

    public void setMsg(JLabel msg) {
        this.msg = msg;
    }

    public JLabel getMsg() {
        return msg;
    }

    public RepositorioPrimitivos getRepositorio() {
        return repositorio;
    }

    /**
     * Limpa SOMENTE a tela. A ED continua intacta, como pede o enunciado.
     */
    public void limpar() {
        visiveis.clear();
        cancelarSelecao();
        repaint();
        atualizarMensagem("Tela limpa. A ED continua com " + repositorio.quantidadeTotal() + " primitivo(s).");
    }

    /**
     * Redesenha na tela os primitivos armazenados que correspondem ao filtro.
     */
    public void redesenhar(TipoPrimitivo filtro) {
        visiveis.clear();
        visiveis.addAll(repositorio.filtrar(filtro));
        cancelarSelecao();
        repaint();

        String nomeFiltro = filtro == null ? TipoPrimitivo.TODOS.toString() : filtro.toString();
        atualizarMensagem("Redesenho: " + nomeFiltro + " - " + visiveis.size()
                + " visivel(is) / " + repositorio.quantidadeTotal() + " na ED.");
    }

    private void cancelarSelecao() {
        xsPendentes.clear();
        ysPendentes.clear();
    }

    private void atualizarMensagem(String texto) {
        if (msg != null) {
            msg.setText(" " + texto);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PrimitivoArmazenado primitivo : visiveis) {
            primitivo.desenhar(g);
        }
    }

    /**
     * Registra um clique. Quando a quantidade de pontos necessaria para a
     * ferramenta atual e atingida, o primitivo e criado e adicionado a ED.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (tipo == null || !tipo.ehPrimitivoDesenhavel()) {
            atualizarMensagem("Selecione Ponto, Reta, Circulo, Retangulo ou Triangulo.");
            return;
        }

        xsPendentes.add(e.getX());
        ysPendentes.add(e.getY());

        int necessarios = tipo.getCliquesNecessarios();
        if (xsPendentes.size() < necessarios) {
            atualizarMensagem(tipo + ": ponto " + xsPendentes.size() + "/" + necessarios
                    + " registrado. Aguardando proximo clique.");
            return;
        }

        int[] xs = new int[necessarios];
        int[] ys = new int[necessarios];
        for (int i = 0; i < necessarios; i++) {
            xs[i] = xsPendentes.get(i);
            ys[i] = ysPendentes.get(i);
        }

        FiguraDesenhada figura = new FiguraDesenhada(tipo, xs, ys, corAtual, esp);
        PrimitivoArmazenado armazenado = repositorio.adicionar(figura);
        visiveis.add(armazenado);

        cancelarSelecao();
        repaint();
        atualizarMensagem(tipo + " armazenado. Total na ED: " + repositorio.quantidadeTotal() + ".");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int feitos = xsPendentes.size();
        String aguardando = "";
        if (tipo != null && tipo.ehPrimitivoDesenhavel() && feitos > 0) {
            aguardando = " - pontos: " + feitos + "/" + tipo.getCliquesNecessarios();
        }
        atualizarMensagem("(" + e.getX() + ", " + e.getY() + ") - " + tipo + aguardando
                + " - ED: " + repositorio.quantidadeTotal());
    }

    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
}
