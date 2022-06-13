package pruebas.PruebasEstructurasnolineales;

import entradasalida.SalidaPorDefecto;
import estructuraslineales.ListaDinamica;
import estructurasnolineales.GrafoDinamico;

public class PruebaPrim {
    public static void main (String args[]){
        GrafoDinamico grafo = new GrafoDinamico();

        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarVertice(4);
        grafo.agregarVertice(5);

        grafo.agregarArista(1,2,1.0);
        grafo.agregarArista(1,3,4.0);
        grafo.agregarArista(2,4,6.0);
        grafo.agregarArista(2,3,3.0);
        grafo.agregarArista(3,5,2.0);
        grafo.agregarArista(3,4,4.0);
        grafo.agregarArista(4,5,5.0);

        grafo.imprimir();

        SalidaPorDefecto.consola("Prueba algoritmo de prim:\n");
        ListaDinamica prim = grafo.Prim();
        prim.imprimir();

        SalidaPorDefecto.consola("\n\nPrueba no.2: \n");

        // Segunda prueba

        GrafoDinamico grafo2 = new GrafoDinamico();
        grafo2.agregarVertice("A");
        grafo2.agregarVertice("B");
        grafo2.agregarVertice("C");
        grafo2.agregarVertice("D");
        grafo2.agregarVertice("E");
        grafo2.agregarVertice("F");
        grafo2.agregarVertice("G");

        grafo2.agregarArista("A","B",7.0);
        grafo2.agregarArista("A","D",5.0);
        grafo2.agregarArista("B","C",8.0);
        grafo2.agregarArista("B","D",9.0);
        grafo2.agregarArista("B","E",7.0);
        grafo2.agregarArista("C","E",5.0);
        grafo2.agregarArista("D","E",15.0);
        grafo2.agregarArista("D","F",6.0);
        grafo2.agregarArista("A","B",7.0);
        grafo2.agregarArista("E","F",8.0);
        grafo2.agregarArista("E","G",9.0);
        grafo2.agregarArista("F","G",11.0);

        grafo2.imprimir();

        SalidaPorDefecto.consola("Prueba algoritmo de prim:\n");
        ListaDinamica prim2 = grafo2.Prim();
        prim2.imprimir();

    }
}
