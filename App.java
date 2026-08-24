/**
 * Aplicacao para testar primitivos graficos.
 *  - Ponto (1 clique), Reta, Circulo, Retangulo e Triangulo (2 cliques)
 *  - Reta e Circulo usam o algoritmo "midpoint"
 *  - Retangulo e Triangulo sao compostos por retas (midpoint)
 *  
 * @author Julio Arakaki 
 * @version 20260823
 */
public class App {
    public static void main(String args[]) {
        // Cria e define dimensao da janela (em pixels)
        new Gui(700, 600); 
    }
}
