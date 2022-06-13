package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaEstatica;
import estructurasnolineales.Matriz2;
import estructurasnolineales.Matriz3;

public class PruebaMatriz3 {
    public static  void main(String [] args){
        Matriz3 matriz=new Matriz3(4,3,5,0);

        matriz.imprimirXColumnas();

        SalidaPorDefecto.consola("\n");

        matriz.cambiar(2,1,1, 100);
        matriz.cambiar(1,0,2,200);

        matriz.imprimirXColumnas();

        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Obteniendo valores: " + matriz.obtener(2,1,1)+ "\n");
        SalidaPorDefecto.consola("Obteniendo valores: " + matriz.obtener(1,0,2)+ "\n");
        SalidaPorDefecto.consola("Obteniendo valores: " + matriz.obtener(100,0,2)+ "\n");

        //Probando el método que pasa unam atriz3 a matrices 2
        SalidaPorDefecto.consola("\nLas matrices2 obtenidas de la matriz3 son: \n");
        invocarMetodoParticion(matriz);
    }

    public static void invocarMetodoParticion(Matriz3 matrizAConvertir){
        ListaEstatica matrices2Obtenidas=matrizAConvertir.aMatriz2XColumnas();
        //Por cada celda del arreglo unidimensional de matrices2Obtenidas
        //recorremos e imprimimos
        for(int indiceMatriz2=0;indiceMatriz2<matrices2Obtenidas.numeroElementos(); indiceMatriz2++){
            Matriz2 matriz2=(Matriz2) matrices2Obtenidas.obtener(indiceMatriz2);
            matriz2.imprimirXRenglones();
            SalidaPorDefecto.consola("\n");
        }
    }
}
