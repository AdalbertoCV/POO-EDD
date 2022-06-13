package Examen1.Pruebas;

import Examen1.Programa1.Balanceo;
import entradasalida.EntradaPorDefecto;
import entradasalida.SalidaPorDefecto;

public class PruebaBalanceo {
    public static void main (String args[]){
        int opcion=0;
        while (opcion<1 || opcion>2){
            SalidaPorDefecto.consola("Seleccione una opcion: \n");
            SalidaPorDefecto.consola("(1) Leer expresion desde teclado \n");
            SalidaPorDefecto.consola("(2) Leer archivo \n");
            opcion = EntradaPorDefecto.consolaInt();
        }

        if (opcion ==1){
            Balanceo balanceo = new Balanceo();
            SalidaPorDefecto.consola(balanceo.balanceoExpresion());
        }else{
            if (opcion ==2){
                Balanceo balanceo = new Balanceo("src\\Examen1\\Programa1\\class1.txt");
                balanceo.balanceoArchivo();
            }
        }
    }
}
