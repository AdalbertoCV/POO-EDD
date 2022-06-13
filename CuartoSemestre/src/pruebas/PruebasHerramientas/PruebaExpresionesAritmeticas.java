package pruebas.PruebasHerramientas;

import entradasalida.SalidaPorDefecto;
import herramientas.matematicas.*;

public class PruebaExpresionesAritmeticas {
    public static void main(String args[]){
        SalidaPorDefecto.consola("La prueba de evaluación de la expresión postfija a b c d - * e f ^ / + es "+
                ExpresionesAritmeticas.evaluarPostfija("2231-*22^/+"));
    }
}
