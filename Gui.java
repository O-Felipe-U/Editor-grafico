import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
/**
 * Cria a interface com o usuario (GUI)
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
class Gui extends JFrame {
    // Tipo Atual de primitivo
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM;

    // Cor atual
    private Color corAtual = Color.BLACK;

    // Espessura atual do primitivo
    private int espAtual = 1;

    // Componentes de GUI
    // barra de menu (inserir componente)
    private JToolBar barraComandos = new JToolBar();

    // mensagens
    private JLabel msg = new JLabel("Msg: ");

    // Painel de desenho
    private PainelDesenho areaDesenho = new PainelDesenho(msg, tipoAtual, corAtual, 10);

    // Botoes
    private JButton jbPonto = new JButton("Ponto");
    private JButton jbReta = new JButton("Reta");
    private JButton jbCirculo = new JButton("Circulo");
    private JButton jbRetangulo = new JButton("Retangulo");
    private JButton jbTriangulo = new JButton("Triangulo");
    private JButton jbLimpar = new JButton("Limpar");
    private JButton jbRedesenhar = new JButton("Redesenhar");
    private JButton jbCor = new JButton("Cor");
    private JButton jbSair = new JButton("Sair");

    // CheckBoxes para escolher quais tipos de primitivo serao redesenhados
    private JCheckBox jcbPonto = new JCheckBox("Ponto", true);
    private JCheckBox jcbReta = new JCheckBox("Reta", true);
    private JCheckBox jcbCirculo = new JCheckBox("Circulo", true);
    private JCheckBox jcbRetangulo = new JCheckBox("Retangulo", true);
    private JCheckBox jcbTriangulo = new JCheckBox("Triangulo", true);

    // Botoes para persistencia em arquivo JSON (salvar/abrir os desenhos)
    private JButton jbSalvar = new JButton("Salvar");
    private JButton jbAbrir = new JButton("Abrir");

    // Entrada (slider) para definir espessura dos primitivos
    private JLabel jlEsp = new JLabel("   Espessura: " + String.format("%-5s", 1));
    private JSlider jsEsp = new JSlider(1, 50, 1);

    /**
     * Verifica se o arquivo indicado tem extensao ".json" (a comparacao
     * ignora maiusculas/minusculas). Usado pelo botao "Abrir" para evitar
     * a tentativa de carregar um arquivo que nao seja um JSON valido.
     *
     * @param arquivo arquivo a ser validado
     * @return true se o nome do arquivo termina com ".json", false caso
     *         contrario
     */
    private boolean validaArquivoJson(File arquivo) {
        String nome = arquivo.getName().toLowerCase();
        return nome.endsWith(".json");
    }

    /**
     * Constroi a GUI
     *
     * @param larg largura da janela
     * @param alt altura da janela
     */
    public Gui(int larg, int alt) {
        /**
         * Definicoes de janela
         */
        super("Testa Primitivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);
        setVisible(true);
        setResizable(false);

        // Adicionando os componentes
        barraComandos.add(jbPonto);
        barraComandos.add(jbReta);
        barraComandos.add(jbCirculo);
        barraComandos.add(jbRetangulo); // Botao de Retangulo
        barraComandos.add(jbTriangulo); // Botao de Triangulo
        barraComandos.add(jbLimpar); // Botao de Limpar
        barraComandos.add(jbRedesenhar); // Botao de Redesenhar (traz de volta o ultimo desenho limpo)
        //barraComandos.add(jbCor); // Botao de Cores

        barraComandos.add(jbSalvar); // Botao de Salvar (persistencia em JSON)
        barraComandos.add(jbAbrir); // Botao de Abrir (carrega desenhos de um arquivo JSON)

        barraComandos.add(jlEsp); // Label para espessura
        barraComandos.add(jsEsp);    // Slider para espacamento
        areaDesenho.setEsp(espAtual); // define a espessura inicial
        barraComandos.add(jbSair); // Botao de Cores

        // adiciona os componentes com os respectivos layouts
        add(barraComandos, BorderLayout.NORTH);
        add(areaDesenho, BorderLayout.CENTER);
        add(msg, BorderLayout.SOUTH);

        // Adiciona "tratador" ("ouvidor") de eventos para 
        // cada componente
        jbPonto.addActionListener(e -> {
            tipoAtual = TipoPrimitivo.PONTO;
            areaDesenho.setTipo(tipoAtual);
        });        
        jbReta.addActionListener(e -> {
            tipoAtual = TipoPrimitivo.RETA;
            areaDesenho.setTipo(tipoAtual);
        });        
        jbCirculo.addActionListener(e -> {
            tipoAtual = TipoPrimitivo.CIRCULO;
            areaDesenho.setTipo(tipoAtual);
        });
        jbRetangulo.addActionListener(e -> {
            tipoAtual = TipoPrimitivo.RETANGULO;
            areaDesenho.setTipo(tipoAtual);
        });
        jbTriangulo.addActionListener(e -> {
            tipoAtual = TipoPrimitivo.TRIANGULO;
            areaDesenho.setTipo(tipoAtual);
        });
        jbLimpar.addActionListener(e -> {
            // guarda os desenhos atuais (em uma EDL) e limpa a tela;
            // o botao "Redesenhar" recupera esses desenhos depois
            areaDesenho.limparTela();
            jsEsp.setValue(1); // inicia slider (necessario para limpar ultimo primitivoda tela)
        });
        jbRedesenhar.addActionListener(e -> {
            // Abre uma janela para escolher quais tipos de primitivo serao redesenhados
            JPanel painelSelecao = new JPanel(new GridLayout(0, 1));
            painelSelecao.add(jcbPonto);
            painelSelecao.add(jcbReta);
            painelSelecao.add(jcbCirculo);
            painelSelecao.add(jcbRetangulo);
            painelSelecao.add(jcbTriangulo);

            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    painelSelecao,
                    "Selecionar primitivos",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (opcao == JOptionPane.OK_OPTION) {
                areaDesenho.redesenharSelecionados(
                        jcbPonto.isSelected(),
                        jcbReta.isSelected(),
                        jcbCirculo.isSelected(),
                        jcbRetangulo.isSelected(),
                        jcbTriangulo.isSelected()
                );
            }
        });
        jbCor.addActionListener(e -> {
            Color c = JColorChooser.showDialog(null, "Escolha uma cor", msg.getForeground()); 
            if (c != null){ 
                corAtual = c; // pega do chooserColor 
            }
            areaDesenho.setCorAtual(corAtual); // cor atual
        });  
        jsEsp.addChangeListener(e -> {
            espAtual = jsEsp.getValue();
            jlEsp.setText("   Espessura: " + String.format("%-5s", espAtual));
            areaDesenho.setEsp(espAtual);        
        });        

        // Botao "Salvar": abre um seletor de arquivo e grava os desenhos
        // atuais em um arquivo JSON no caminho escolhido pelo usuario
        jbSalvar.addActionListener(e -> {
            JFileChooser seletor = new JFileChooser();
            seletor.setFileFilter(new FileNameExtensionFilter("Arquivos JSON (*.json)", "json"));
            seletor.setSelectedFile(new File(""));
            int opcao = seletor.showSaveDialog(this);
            if (opcao == JFileChooser.APPROVE_OPTION) {
                String caminho = seletor.getSelectedFile().getAbsolutePath();
                areaDesenho.salvarEmArquivo(caminho);
            }
        });

        // Botao "Abrir": abre um seletor de arquivo e carrega os desenhos
        // guardados no arquivo JSON escolhido pelo usuario, substituindo
        // o que estiver atualmente na tela. Antes de tentar carregar, e
        // validado se o arquivo escolhido tem extensao ".json" - caso
        // contrario, uma mensagem avisa o usuario e o carregamento nao
        // e realizado.
        jbAbrir.addActionListener(e -> {
            JFileChooser seletor = new JFileChooser();
            seletor.setFileFilter(new FileNameExtensionFilter("Arquivos JSON (*.json)", "json"));
            int opcao = seletor.showOpenDialog(this);
            if (opcao == JFileChooser.APPROVE_OPTION) {
                File arquivo = seletor.getSelectedFile();
                if (validaArquivoJson(arquivo)) {
                    areaDesenho.carregarDeArquivo(arquivo.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(this,
                            "O arquivo selecionado nao e um arquivo JSON (.json).\nEscolha um arquivo com essa extensao.",
                            "Arquivo invalido",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jbSair.addActionListener(e -> {
            System.exit(0);
        });        
    }
}
