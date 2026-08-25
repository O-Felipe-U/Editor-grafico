import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;

/**
 * Interface grafica do editor de primitivos.
 *
 * Permite selecionar a ferramenta, cor e espessura; limpar somente a tela;
 * e testar a ED redesenhando todos os primitivos ou apenas um tipo especifico.
 */
@SuppressWarnings("serial")
public class Gui extends JFrame {
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM;
    private Color corAtual = Color.BLACK;
    private int espAtual = 1;

    private final JToolBar barraComandos = new JToolBar();
    private final JLabel msg = new JLabel(" Selecione uma ferramenta.");
    private final PainelDesenho areaDesenho = new PainelDesenho(msg, tipoAtual, corAtual, espAtual);

    private final JButton jbPonto = new JButton("Ponto");
    private final JButton jbReta = new JButton("Reta");
    private final JButton jbCirculo = new JButton("Circulo");
    private final JButton jbRetangulo = new JButton("Retangulo");
    private final JButton jbTriangulo = new JButton("Triangulo");
    private final JButton jbLimpar = new JButton("Limpar tela");
    private final JButton jbCor = new JButton("Cor");
    private final JButton jbSair = new JButton("Sair");

    private final JLabel jlEsp = new JLabel(" Espessura: 1 ");
    private final JSlider jsEsp = new JSlider(1, 20, 1);

    private final JComboBox<TipoPrimitivo> cbRedesenhar = new JComboBox<>(new TipoPrimitivo[] {
            TipoPrimitivo.TODOS,
            TipoPrimitivo.PONTO,
            TipoPrimitivo.RETA,
            TipoPrimitivo.CIRCULO,
            TipoPrimitivo.RETANGULO,
            TipoPrimitivo.TRIANGULO
    });
    private final JButton jbRedesenhar = new JButton("Redesenhar");

    public Gui(int larg, int alt) {
        super("Editor Grafico - Primitivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        montarInterface();
        configurarEventos();

        setVisible(true);
    }

    private void montarInterface() {
        barraComandos.setFloatable(false);
        barraComandos.add(jbPonto);
        barraComandos.add(jbReta);
        barraComandos.add(jbCirculo);
        barraComandos.add(jbRetangulo);
        barraComandos.add(jbTriangulo);
        barraComandos.addSeparator();
        //barraComandos.add(jbCor);
        barraComandos.add(jlEsp);
        jsEsp.setPreferredSize(new Dimension(100, 30));
        barraComandos.add(jsEsp);
        barraComandos.addSeparator();
        barraComandos.add(jbLimpar);
        barraComandos.addSeparator();
        barraComandos.add(jbSair);

        JPanel painelRedesenho = new JPanel();
        painelRedesenho.add(new JLabel("Redesenhar da ED:"));
        painelRedesenho.add(cbRedesenhar);
        painelRedesenho.add(jbRedesenhar);

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(barraComandos, BorderLayout.NORTH);
        topo.add(painelRedesenho, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);
        add(areaDesenho, BorderLayout.CENTER);
        add(msg, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        jbPonto.addActionListener(e -> selecionarTipo(TipoPrimitivo.PONTO));
        jbReta.addActionListener(e -> selecionarTipo(TipoPrimitivo.RETA));
        jbCirculo.addActionListener(e -> selecionarTipo(TipoPrimitivo.CIRCULO));
        jbRetangulo.addActionListener(e -> selecionarTipo(TipoPrimitivo.RETANGULO));
        jbTriangulo.addActionListener(e -> selecionarTipo(TipoPrimitivo.TRIANGULO));

        jbLimpar.addActionListener(e -> areaDesenho.limpar());

        jbRedesenhar.addActionListener(e -> {
            TipoPrimitivo filtro = (TipoPrimitivo) cbRedesenhar.getSelectedItem();
            areaDesenho.redesenhar(filtro);
        });

        jbCor.addActionListener(e -> {
            Color escolhida = JColorChooser.showDialog(this, "Escolha uma cor", corAtual);
            if (escolhida != null) {
                corAtual = escolhida;
                areaDesenho.setCorAtual(corAtual);
                jbCor.setForeground(corAtual);
            }
        });

        jsEsp.addChangeListener(e -> {
            espAtual = jsEsp.getValue();
            jlEsp.setText(" Espessura: " + espAtual + " ");
            areaDesenho.setEsp(espAtual);
        });

        jbSair.addActionListener(e -> dispose());
    }

    private void selecionarTipo(TipoPrimitivo tipo) {
        tipoAtual = tipo;
        areaDesenho.setTipo(tipoAtual);
    }
}
