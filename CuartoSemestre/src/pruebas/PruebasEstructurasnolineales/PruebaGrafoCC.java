package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructurasnolineales.GrafoEstatico;

public class PruebaGrafoCC {
    public static void main (String args[]){
        // Creando un grafo no dirigido
        GrafoEstatico grafo = new GrafoEstatico(4);
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.agregarArista("A","B");
        grafo.agregarArista("B","A");
        grafo.agregarArista("A","D");
        grafo.agregarArista("D","A");
        grafo.agregarArista("B","C");
        grafo.agregarArista("C","B");
        grafo.agregarArista("D","C");
        grafo.agregarArista("C","D");
        grafo.agregarArista("C","A");
        grafo.agregarArista("A","C");

        grafo.imprimirComponentesConexos("A");

        SalidaPorDefecto.consola("Eliminando las aristas de A-D Y D-C \n");
        GrafoEstatico grafo1 = new GrafoEstatico(4);

        grafo1.agregarVertice("A");
        grafo1.agregarVertice("B");
        grafo1.agregarVertice("C");
        grafo1.agregarVertice("D");

        grafo1.agregarArista("A","B");
        grafo1.agregarArista("B","A");
        grafo1.agregarArista("B","C");
        grafo1.agregarArista("C","B");
        grafo1.agregarArista("C","A");
        grafo1.agregarArista("A","C");

        grafo1.imprimirComponentesConexos("A");


        // Creando un grafo dirigido

        GrafoEstatico grafoDirigido = new GrafoEstatico(5);

        grafoDirigido.agregarVertice("A");
        grafoDirigido.agregarVertice("B");
        grafoDirigido.agregarVertice("C");
        grafoDirigido.agregarVertice("D");
        grafoDirigido.agregarVertice("E");

        grafoDirigido.agregarArista("A","B");
        grafoDirigido.agregarArista("A","E");
        grafoDirigido.agregarArista("B","D");
        grafoDirigido.agregarArista("B","E");
        grafoDirigido.agregarArista("C","B");
        grafoDirigido.agregarArista("C","A");
        grafoDirigido.agregarArista("D","A");
        grafoDirigido.agregarArista("D","C");
        grafoDirigido.agregarArista("E","D");
        grafoDirigido.agregarArista("E","C");

        grafoDirigido.imprimirComponentesFuertes("A");

        SalidaPorDefecto.consola("Eliminando las aristas de E-A, E-B, E-C: \n");

        GrafoEstatico grafoDirigido1 = new GrafoEstatico(5);

        grafoDirigido1.agregarVertice("A");
        grafoDirigido1.agregarVertice("B");
        grafoDirigido1.agregarVertice("C");
        grafoDirigido1.agregarVertice("D");
        grafoDirigido1.agregarVertice("E");

        grafoDirigido1.agregarArista("A","B");
        grafoDirigido1.agregarArista("B","D");
        grafoDirigido1.agregarArista("C","B");
        grafoDirigido1.agregarArista("C","A");
        grafoDirigido1.agregarArista("D","A");
        grafoDirigido1.agregarArista("D","C");
        grafoDirigido1.agregarArista("E","D");

        grafoDirigido1.imprimirComponentesFuertes("B");
    }
}
