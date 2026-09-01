package circulo;

import ponto.Ponto;

/**
 * Testa a classe Circulo.
 *
 * @author Felipe Estima Correia Urzi
 * @author Igor Dias da Silva
 * @author Pedro Henrique Freire
 * @author Thierry Nadjarian
 *
 * @version 20220815
 */
public class TestaCirculo {
    public static void main(String args[]) {
        Circulo c = new Circulo(new Ponto(10, 10), new Ponto(20, 10));
        System.out.println("Circulo: " + c);
    }
}
