package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.ArbolExpParentesis;

public class PruebaABExpParentesis {
    public static void main (String args[]){
        ArbolExpParentesis arbol = new ArbolExpParentesis();
        SalidaPorDefecto.consola("Prueba 1: \n");
        SalidaPorDefecto.consola("Expresion:  (((z^n)-(e/g))+(b*d)) \n");
        arbol.generarArbolExpresion("(((z^n)-(e/g))+(b*d))");
        SalidaPorDefecto.consola("Imprimiendo recorridos: \n");
        SalidaPorDefecto.consola("Inorden: ");
        arbol.inorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Preorden: ");
        arbol.preorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Postorden: ");
        arbol.postorden();
        SalidaPorDefecto.consola("\n");

        ArbolExpParentesis arbol1 = new ArbolExpParentesis();
        SalidaPorDefecto.consola("Prueba 2: \n");
        SalidaPorDefecto.consola("Expresion:  ((a+b)-(c*d))  \n");
        arbol1.generarArbolExpresion("((a+b)-(c*d))");
        SalidaPorDefecto.consola("Imprimiendo recorridos: \n");
        SalidaPorDefecto.consola("Inorden: ");
        arbol1.inorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Preorden: ");
        arbol1.preorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Postorden: ");
        arbol1.postorden();
        SalidaPorDefecto.consola("\n");
    }
}
