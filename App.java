import javax.swing.SwingUtilities;

/**
 * Aplicacao para testar os cinco primitivos graficos e a ED.
 *
 * Ponto: 1 clique.
 * Reta, Circulo e Retangulo: 2 cliques.
 * Triangulo: 3 cliques.
 *
 * Reta e Circulo usam algoritmo midpoint; Retangulo e Triangulo reutilizam
 * o algoritmo de reta para compor seus lados.
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui(1000, 700));
    }
}
