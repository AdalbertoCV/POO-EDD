package poo2.prac03;

/**
 * Esta clase implementa cinco operaciones basicas para realizar operaciones
 */
public class CalculadoraSimple {
    /**
     * Este metodo devuelve la suma de dos numeros a y b
     * @param a   Primer numero
     * @param b   Segundo numero
     * @return  a + b
     */
    public double suma(double a, double b) {
        double sumado = a + b;
        return sumado;
    }

    /**
     * Este metodo devuelve la resta de dos numeros a y b
     * @param a   Primer numero
     * @param b   Segundo numero
     * @return  a - b
     */
    public double resta(double a, double b) {
        double restado = a - b;
        return restado;
    }

    /**
     * Este metodo devuelve el producto de dos numeros a y b
     * @param a   Primer numero
     * @param b   Segundo numero
     * @return  a * b
     */
    public double mult(double a, double b) {
        double multiplicado = a*b;
        return multiplicado;
    }

    /**
     * Este metodo devuelve el cociente de la division de dos numeros a y b
     * @param a   Primer numero
     * @param b   Segundo numero
     * @return  a / b
     * @throws ArithmeticException  Si el numero b es cero indicando el mensaje "No se puede dividir entre cero"
     */
    public double divide(double a, double b)
            throws ArithmeticException   {
        double dividido;
        if (b!=0){
            dividido = a/b;
            return dividido;
        }
        else{
            throw new ArithmeticException("No se puede dividir entre cero");
        }
    }

    /**
     * Este metodo eleva un numero a a la potencia b
     * (NO ESTA PERMITIDO USAR FUNCIONES MATEMATICAS
     * PARA IMPLEMENTAR ESTE METODO)
     * @param a  Valor double que indica la base de la potencia
     * @param b  Valor entero que indica el exponente
     * @return   a elevado a la b
     */
    public double eleva(double a, int b) {
        double elevado = 1;
        if (b>0){
            for (int x=0;x<b;x++){
                elevado = elevado *a;
            }
            return elevado;
        }
        if (b==0){
            return 1.0;
        }
        if (b<0) {
            for (int y=0;y<(b*-1);y++){
                elevado = elevado / a;
            }
            return elevado;
        }
        return elevado;
    }

}
