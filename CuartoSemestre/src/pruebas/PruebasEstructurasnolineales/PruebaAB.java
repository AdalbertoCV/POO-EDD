package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.ArbolBinario;

public class PruebaAB {
    public  static  void  main(String argumentos[]){
        ArbolBinario arbol=new ArbolBinario();
        arbol.generarArbol();
        SalidaPorDefecto.consola("Arbol inorden: ");
        arbol.inorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Arbol Preorden: ");
        arbol.preorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Arbol Postorden: ");
        arbol.postorden();
        SalidaPorDefecto.consola("\n");
        arbol.extraerOperandosYOperadores();
        arbol.imprimirOperadores();
        arbol.imprimirOperandos();
        arbol.cambiarOperandos();
        arbol.generarNuevoArbol();
        SalidaPorDefecto.consola("Arbol Nuevo inorden: ");
        arbol.inorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Arbol Nuevo Preorden: ");
        arbol.preorden();
        SalidaPorDefecto.consola("\n");
        SalidaPorDefecto.consola("Arbol Nuevo Postorden: ");
        arbol.postorden();
        SalidaPorDefecto.consola("\n");

        SalidaPorDefecto.consola("Altura del arbol: " + arbol.altura()+"\n");
        arbol.alturaNodo("z");
        SalidaPorDefecto.consola("Prueba obtener altura de nodo Z: " + arbol.alturaNodo("z")+"\n");
        SalidaPorDefecto.consola("Prueba obtener altura de nodo /: " + arbol.alturaNodo("/")+"\n");
        SalidaPorDefecto.consola("Prueba obtener tipo de nodo del nodo ^: \n");
        arbol.tipoNodo("^");
        SalidaPorDefecto.consola("Prueba obtener tipo de nodo del nodo +: \n");
        arbol.tipoNodo("+");
        SalidaPorDefecto.consola("Prueba obtener tipo de nodo del nodo 3: \n");
        arbol.tipoNodo("3");
        SalidaPorDefecto.consola("Prueba para saber si el nodo / tiene hermano:\n");
        arbol.tieneHermano("/");
        SalidaPorDefecto.consola("Prueba para saber si el nodo + tiene hermano:\n");
        arbol.tieneHermano("+");
        arbol.cantidadPorNivel();
    }
}
