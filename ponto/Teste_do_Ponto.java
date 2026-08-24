package ponto;

 

public class Teste_do_Ponto {
    
    public static void main(){
        Ponto P = new Ponto();

        P.setX(5);
        P.setY(10);
    
        Ponto y = new Ponto();

        y.setX(5);
        y.setY(5);
        
        System.out.println("" + P);
        
        double a = P.calcularDistancia(y);
        System.out.println("" + a);
    } 
}
