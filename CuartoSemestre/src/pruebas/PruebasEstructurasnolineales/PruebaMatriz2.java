package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import enumerados.TipoColumna;
import enumerados.TipoRenglon;
import estructuraslineales.ListaEstatica;
import estructurasnolineales.Matriz2;
import estructurasnolineales.Matriz3;

public class PruebaMatriz2 {
    public static void main(String args[]){
        Matriz2 matriz = new Matriz2(3,5,0);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba cambiar elemento 2: "+ matriz.cambiar(2,3,2)+ "\n");
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba traspuesta matriz 1: \n");
        //matriz.traspuesta();
        matriz.imprimirXRenglones();
        //matriz.traspuesta();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba clonar matriz 1: \n");
        Matriz2 matriz2 = matriz.clonar();
        matriz2.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba igualdad de matricez 1 y 2: "+matriz.esIgual(matriz2) + "\n");
        Matriz2 matriz3 = new Matriz2(5,6);
        SalidaPorDefecto.consola("Prueba vector columna: " + matriz3.vectorColumna(2,0) + "\n");
        matriz3.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        Matriz2 matriz4 = new Matriz2(5,6);
        SalidaPorDefecto.consola("Prueba vector renglon: " + matriz4.vectorRenglon(3,0)+"\n");
        matriz4.vectorRenglon(3,0);
        matriz4.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba redefinir matriz: "+ matriz4.redefinir(matriz) +"\n" );
        matriz4.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        ListaEstatica lista = new ListaEstatica(5);
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);
        lista.agregar(4);
        lista.agregar(5);
        SalidaPorDefecto.consola("Prueba agregar renglon: " + matriz.agregarRenglon(lista)+"\n");
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        lista.eliminar(0);
        SalidaPorDefecto.consola("Prueba agregar columna: " + matriz.agregarColumna(lista)+"\n");
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        Matriz2 matriz5 = matriz.clonar();
        SalidaPorDefecto.consola("Probando agregar matriz por renglones: "+matriz.agregarMatrizXRenglon(matriz5)+"\n");
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        Matriz2 matriz6 = matriz.clonar();
        SalidaPorDefecto.consola("Probando agregar matriz por columnas: "+ matriz.agregarMatrizXColumna(matriz6)+"\n");
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando transformar a vector columna: \n");
        Matriz2 matrizAVector = new Matriz2(3,3,"X");
        SalidaPorDefecto.consola("Matriz original: \n");
        matrizAVector.imprimirXRenglones();
        Matriz2 vectorColumna = matrizAVector.aVectorColumna();
        SalidaPorDefecto.consola("Vector columna: \n");
        vectorColumna.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando transformar a vector renglon: \n");
        Matriz2 vectorRenglon = matrizAVector.aVectorRenglon();
        vectorRenglon.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando eliminar columna izquierda matriz: \n");
        SalidaPorDefecto.consola("Matriz original: \n");
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Con columna eliminada: \n");
        matriz.eliminarColumna(TipoColumna.IZQUIERDA);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando eliminar columna derecha matriz: \n");
        matriz.eliminarColumna(TipoColumna.DERECHA);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando eliminar renglon superior: \n");
        matriz.eliminarRenglon(TipoRenglon.SUPERIOR);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando eliminar renglon inferior: \n");
        matriz.eliminarRenglon(TipoRenglon.INFERIOR);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando eliminar renglon 2: \n");
        matriz.eliminarRenglon(2);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Probando eliminar Columna 1: \n");
        matriz.eliminarColumna(1);
        matriz.imprimirXRenglones();
        SalidaPorDefecto.consola("\n");
        Matriz2 matrizAgregar1 = new Matriz2(5,2,0);
        Matriz2 matrizAgregar2 = new Matriz2(5,3,0);
        Matriz2 matrizAgregar3 = new Matriz2(5,4,0);
        ListaEstatica listaMatrices = new ListaEstatica(3);
        listaMatrices.agregar(matrizAgregar1);
        listaMatrices.agregar(matrizAgregar2);
        listaMatrices.agregar(matrizAgregar3);
        SalidaPorDefecto.consola("Probando convertir a matriz3: \n");
        Matriz3 matriz3d = matriz.aMatriz3(listaMatrices);
        matriz3d.imprimirXColumnas();
    }
}
