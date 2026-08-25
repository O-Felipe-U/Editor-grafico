package circulo;

import ponto.Ponto;

/**
 * Testa a classe Circulo.
 *
 * @author Julio Arakaki
 * @version 20220815
 */
public class TestaCirculo {
    public static void main(String args[]) {
        Circulo c = new Circulo(new Ponto(10, 10), new Ponto(20, 10));
        System.out.println("Circulo: " + c);
    }
}
