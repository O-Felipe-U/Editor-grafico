package ponto;

public class Ponto {
    private double x;
    private double y;


    /**
     * Construtor do Ponto mais generico
     */
    public Ponto()
    {
        setX(0);
        setY(0);
    }

    /**
    * Construtor do Ponto mais generico
    */
    public Ponto(double x1,double y1)
    {
        setX(x1);
        setY(y1);
    }
    
    /**
     * Define o valor de x
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * Define o valor de y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Retorna x
     * @return valor de x
     */
    public double getX() {
        return x;
    }

    /**
     * Retorna y
     * @return valor de y
     */
    public double getY() {
        return y;
    }

    public double calcularDistancia(Ponto p) {
    
        double d = Math.sqrt(Math.pow(p.getY()-getY(), 2) + Math.pow(p.getX()-getX(), 2));
        return(d);

    }
    
    /**
     * Imprime as coordenadas no formato [x , y]
     *
     * @return representa o ponto
     */
    public String toString()
    {
        return "Ponto [ " + getX()+" , "+ getY() + "]" ;
    }

}
