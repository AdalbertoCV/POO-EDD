package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.*;

public class PruebaABB {
    public static void main(String []argumentos){
        ArbolBinarioBusqueda arbol=new ArbolBinarioBusqueda();

        arbol.agregar(12);
        arbol.agregar(9);
        arbol.agregar(5);
        arbol.agregar(7);
        arbol.agregar(15);
        arbol.agregar(17);
        arbol.agregar(1);
        arbol.agregar(13);
        arbol.agregar(9);

        SalidaPorDefecto.consola("Prueba postorden no recursivo: ");
        arbol.postordenNoRecursivo();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba recorrido por amplitud usando cola: ");
        arbol.recorridoAmplitudCola();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Prueba recorrido por amplitud usando pila: ");
        arbol.recorridoAmplitudPila();
        SalidaPorDefecto.consola("\n");
    }
}
