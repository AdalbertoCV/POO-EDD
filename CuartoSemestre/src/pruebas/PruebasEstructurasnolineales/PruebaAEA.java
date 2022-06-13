package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.ArbolExpAritm;

public class PruebaAEA {
    public static  void main(String [] args){
        ArbolExpAritm arbol=new ArbolExpAritm();

        arbol.generarArbolPostfija("x4+568/*-");

        SalidaPorDefecto.consola("Imprimiendo el árbol: \n");
        SalidaPorDefecto.consola("Inorden: ");
        arbol.inorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Postorden: ");
        arbol.postorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Preorden: ");
        arbol.preorden();
        SalidaPorDefecto.consola("\n");
    }
}
